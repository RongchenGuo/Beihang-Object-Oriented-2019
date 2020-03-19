import com.oocourse.TimableOutput;
import com.oocourse.elevator3.PersonRequest;

import java.util.ArrayList;

public class ConsumerA implements Runnable {
    private PublicPool pool;
    private PublicBoxA box;
    private int floor;
    private int direction;
    // up: 1
    // down: -1
    // stop: 0
    private ArrayList<PersonRequest> list;

    public ConsumerA(PublicPool pool, PublicBoxA box) {
        this.pool = pool;
        this.box = box;
        this.floor = 1;
        this.direction = -1;
        this.list = new ArrayList<>();
    }

    private void upstair() {
        if (floor == -1) {
            floor = 1;
        } else if (floor == 20) {
            System.out.print("already at the top floor!");
        } else {
            floor = floor + 1;
        }
    }

    private void downstair() {
        if (floor == 1) {
            floor = -1;
        } else if (floor == -3) {
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
                        "OUT-%d-%d-A", personId, toFloor));
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
            if (list.size() != 6) {
                list.add(req);
                int personId = req.getPersonId();
                int fromFloor = req.getFromFloor();
                TimableOutput.println(String.format(
                        "IN-%d-%d-A", personId, fromFloor));
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
            TimableOutput.println(String.format("CLOSE-%d-A", floor));
        }
        return 0;
    }

    private int openDoor() {
        TimableOutput.println(String.format("OPEN-%d-A", floor));
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
                    box.testEmpty();
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
                Thread.sleep(400);
                TimableOutput.println(String.format("ARRIVE-%d-A", floor));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}