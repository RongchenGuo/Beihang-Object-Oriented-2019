import com.oocourse.TimableOutput;
import com.oocourse.elevator2.PersonRequest;
import com.oocourse.elevator2.ElevatorInput;

import java.util.ArrayList;
import java.util.LinkedList;

public class PublicBox {
    private static LinkedList<PersonRequest> list = new LinkedList<>();

    public synchronized void increase(PersonRequest request) {
        // System.out.println("Producer");
        list.addLast(request);
        // list.notify();
        notifyAll();
    }

    public synchronized PersonRequest decrease() {
        while (list.size() == 0) {
            // System.out.println("nothing in queue");
            try {
                // list.wait();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        PersonRequest request = list.getFirst();
        list.removeFirst();
        notifyAll();
        return request;
    }

    public synchronized void testEmpty() {
        while (list.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll();
    }

    public synchronized boolean testNull() {
        if (list.size() == 1 && list.getFirst() == null) {
            notifyAll();
            return true;
        }
        notifyAll();
        return false;
    }

    public synchronized ArrayList<PersonRequest> search(int floor,
                                                        int direction) {
        ArrayList<PersonRequest> sat = new ArrayList<>();
        for (PersonRequest req : list) {
            // System.out.println("huh" + req);
            if (req == null) {
                break;
            }
            int ff = req.getFromFloor();
            int tf = req.getToFloor();
            if (ff == floor) {
                if ((tf - ff) > 0 && direction > 0) {
                    sat.add(req);
                } else if ((tf - ff) < 0 && direction < 0) {
                    sat.add(req);
                }
            }
        }
        notifyAll();
        return sat;
    }

    private ArrayList<PersonRequest> search1(int floor, int direction) {
        ArrayList<PersonRequest> sat = new ArrayList<>();
        for (PersonRequest req : list) {
            // System.out.println("huh" + req);
            if (req == null) {
                break;
            }
            int ff = req.getFromFloor();
            int tf = req.getToFloor();
            if (ff == floor) {
                if ((tf - ff) > 0 && direction > 0) {
                    sat.add(req);
                } else if ((tf - ff) < 0 && direction < 0) {
                    sat.add(req);
                }
            }
        }
        return sat;
    }

    private void remove(ArrayList<PersonRequest> sat) {
        for (PersonRequest req : sat) {
            list.removeFirstOccurrence(req);
        }
    }

    public synchronized boolean stillUp(int floor) {
        // System.out.println("stillUp");
        ArrayList<PersonRequest> sat = new ArrayList<>();
        for (PersonRequest req : list) {
            if (req == null) {
                break;
            }
            int ff = req.getFromFloor();
            if (ff > floor) {
                sat.add(req);
            }
        }
        notifyAll();
        return sat.size() != 0;
    }

    public synchronized boolean stillDown(int floor) {
        // System.out.println("stillDown");
        ArrayList<PersonRequest> sat = new ArrayList<>();
        for (PersonRequest req : list) {
            if (req == null) {
                break;
            }
            int ff = req.getFromFloor();
            if (ff < floor) {
                sat.add(req);
            }
        }
        notifyAll();
        return sat.size() != 0;
    }

    public synchronized ArrayList<PersonRequest> searchRemove(int floor,
                                                              int direction) {
        // System.out.println("searchRemove " + floor + "  " + direction);
        ArrayList<PersonRequest> sat = search1(floor, direction);
        remove(sat);
        notifyAll();
        return sat;
    }

    public static void main(String[] args) {
        TimableOutput.initStartTimestamp();
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        PublicBox box = new PublicBox();

        Producer pro = new Producer(box, elevatorInput);
        Consumer con = new Consumer(box);
        Thread t2 = new Thread(pro);
        Thread t1 = new Thread(con);
        t1.setPriority(1);
        t2.setPriority(10);
        t2.start();
        t1.start();
    }
}
