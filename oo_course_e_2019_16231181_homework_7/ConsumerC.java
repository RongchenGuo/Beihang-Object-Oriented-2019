import com.oocourse.TimableOutput;
import com.oocourse.elevator3.PersonRequest;

import java.util.ArrayList;

public class ConsumerC implements Runnable {
    private PublicPool pool;
    private PublicBoxC box;
    private int floor;
    private int direction;
    // up: 1
    // down: -1
    // stop: 0
    private ArrayList<PersonRequest> list;

    public ConsumerC(PublicPool pool, PublicBoxC box) {
        this.pool = pool;
        this.box = box;
        this.floor = 1;
        this.direction = -1;
        this.list = new ArrayList<>();
    }

    private void upstair() {
        if (floor == 15) {
            System.out.print("already at the top floor!");
        } else {
            floor = floor + 1;
        }
    }

    private void downstair() {
        if (floor == 1) {
            System.out.print("already at the bottom floor!");
        } else {
            floor = floor - 1;
        }
    }

    private void changeDirection() {
        direction = direction * (-1);
    }

    private boolean someoneOut() {
        if (list.size() == 0) {
            return false;
        }
        for (PersonRequest req : list) {
            int tf = req.getToFloor();
            if (tf == floor) {
                return true;
            }
        }
        return false;
    }

    private void allOut() {
        ArrayList<PersonRequest> tmp = new ArrayList<>();
        for (PersonRequest req : list) {
            int tf = req.getToFloor();
            if (tf == floor) {
                tmp.add(req);
                int personId = req.getPersonId();
                int toFloor = req.getToFloor();
                TimableOutput.println(String.format(
                        "OUT-%d-%d-C", personId, toFloor));
                pool.activate(req.getPersonId());
            }
        }
        for (PersonRequest req : tmp) {
            list.remove(req);
        }
    }

    private boolean someIn(ArrayList<PersonRequest> sat) {
        return sat.size() != 0;
    }

    private void allIn(ArrayList<PersonRequest> sat) {
        for (PersonRequest req : sat) {
            if (list.size() != 7) {
                list.add(req);
                int personId = req.getPersonId();
                int fromFloor = req.getFromFloor();
                TimableOutput.println(String.format(
                        "IN-%d-%d-C", personId, fromFloor));
            } else {
                box.increase(req);
            }
        }
    }

    private int closeDoor(int flag) {
        if (flag == 1) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TimableOutput.println(String.format("CLOSE-%d-C", floor));
        }
        return 0;
    }

    private int openDoor() {
        TimableOutput.println(String.format("OPEN-%d-C", floor));
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public void run() {
        int flag = 0;
        try {
            // Thread.sleep(200);
            while (true) {
                if (someoneOut()) {
                    flag = openDoor();
                    allOut();
                }
                ArrayList<PersonRequest> t = box.search(floor, direction);
                if (someIn(t) && flag == 0) { flag = openDoor(); }
                t = box.searchRemove(floor, direction);
                allIn(t);
                if (list.size() == 0) {
                    // System.out.println("C3");
                    box.testEmpty();
                    // System.out.println("C4");
                    if (box.testNull()) {
                        closeDoor(flag);
                        break;
                    }
                    if (direction == -1) {
                        if (box.stillDown(floor)) {
                            flag = closeDoor(flag);
                            downstair();
                        } else {
                            changeDirection();
                            t = box.search(floor, direction);
                            if (someIn(t) && flag == 0) { flag = openDoor(); }
                            t = box.searchRemove(floor, direction);
                            allIn(t);
                            flag = closeDoor(flag);
                            upstair();
                        }
                    } else {
                        if (box.stillUp(floor)) {
                            flag = closeDoor(flag);
                            upstair();
                        } else {
                            changeDirection();
                            t = box.search(floor, direction);
                            if (someIn(t) && flag == 0) { flag = openDoor(); }
                            t = box.searchRemove(floor, direction);
                            allIn(t);
                            flag = closeDoor(flag);
                            downstair();
                        }
                    }
                } else {
                    flag = closeDoor(flag);
                    if (direction == -1) { downstair(); }
                    else { upstair(); }
                }
                Thread.sleep(600);
                TimableOutput.println(String.format("ARRIVE-%d-C", floor));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}