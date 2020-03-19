import com.oocourse.elevator3.ElevatorInput;
import com.oocourse.elevator3.PersonRequest;

import java.io.IOException;

public class Producer implements Runnable {
    private PublicPool pool;
    private PublicBoxA boxA;
    private PublicBoxB boxB;
    private PublicBoxC boxC;
    private ElevatorInput elevatorInput;

    public Producer(PublicPool p, PublicBoxA a, PublicBoxB b,
                    PublicBoxC c, ElevatorInput elevatorInput) {
        this.pool = p;
        this.boxA = a;
        this.boxB = b;
        this.boxC = c;
        this.elevatorInput = elevatorInput;
    }

    private boolean scopeA(int f) {
        return (f <= 1 || f >= 15);
    }

    private boolean scopeB(int f) {
        return (f >= -2 && f <= 15 && f != 3);
    }

    private boolean scopeC(int f) {
        return (f >= 1 && f <= 15 && f % 2 == 1);
    }

    private boolean conditionA(PersonRequest req) {
        int f = req.getFromFloor();
        int t = req.getToFloor();
        return (scopeA(f) && scopeA(t));
    }

    private boolean conditionB(PersonRequest req) {
        int f = req.getFromFloor();
        int t = req.getToFloor();
        return (scopeB(f) && scopeB(t));
    }

    private boolean conditionC(PersonRequest req) {
        int f = req.getFromFloor();
        int t = req.getToFloor();
        return (scopeC(f) && scopeC(t));
    }

    private boolean conditionAb(PersonRequest req) {
        int f = req.getFromFloor();
        int t = req.getToFloor();
        return (scopeA(f) && scopeB(t));
    }

    private boolean conditionAc(PersonRequest req) {
        int f = req.getFromFloor();
        int t = req.getToFloor();
        return (scopeA(f) && scopeC(t));
    }

    private boolean conditionBa(PersonRequest req) {
        int f = req.getFromFloor();
        int t = req.getToFloor();
        return (scopeB(f) && scopeA(t));
    }

    private boolean conditionBc(PersonRequest req) {
        int f = req.getFromFloor();
        int t = req.getToFloor();
        return (scopeB(f) && scopeC(t));
    }

    private boolean conditionCa(PersonRequest req) {
        int f = req.getFromFloor();
        int t = req.getToFloor();
        return (scopeC(f) && scopeA(t));
    }

    private boolean conditionCb(PersonRequest req) {
        int f = req.getFromFloor();
        int t = req.getToFloor();
        return (scopeC(f) && scopeB(t));
    }

    private void splitAc(PersonRequest req) {
        int f = req.getFromFloor();
        int t = req.getToFloor();
        int i = req.getPersonId();
        if (f <= 1) {
            boxA.increase(new PersonRequest(f, 1, i));
            pool.increase(new PersonRequest(1, t, i));
        } else {
            boxA.increase(new PersonRequest(f, 15, i));
            pool.increase(new PersonRequest(15, t, i));
        }
    }

    private void splitAb(PersonRequest req) {
        int f = req.getFromFloor();
        int t = req.getToFloor();
        int i = req.getPersonId();
        if (f <= 1) {
            boxA.increase(new PersonRequest(f, 1, i));
            pool.increase(new PersonRequest(1, t, i));
        } else {
            boxA.increase(new PersonRequest(f, 15, i));
            pool.increase(new PersonRequest(15, t, i));
        }
    }

    private void splitBc(PersonRequest req) {
        int f = req.getFromFloor();
        int t = req.getToFloor();
        int i = req.getPersonId();
        if (f < t) {
            boxB.increase(new PersonRequest(f, t - 2, i));
            pool.increase(new PersonRequest(t - 2, t, i));
        } else {
            boxB.increase(new PersonRequest(f, t + 2, i));
            pool.increase(new PersonRequest(t + 2, t, i));
        }
    }

    private void splitBa(PersonRequest req) {
        int f = req.getFromFloor();
        int t = req.getToFloor();
        int i = req.getPersonId();
        if (t <= 1) {
            boxB.increase(new PersonRequest(f, 1, i));
            pool.increase(new PersonRequest(1, t, i));
        } else {
            boxB.increase(new PersonRequest(f, 15, i));
            pool.increase(new PersonRequest(15, t, i));
        }
    }

    private void splitCa(PersonRequest req) {
        int f = req.getFromFloor();
        int t = req.getToFloor();
        int i = req.getPersonId();
        if (t <= 1) {
            boxC.increase(new PersonRequest(f, 1, i));
            pool.increase(new PersonRequest(1, t, i));
        } else {
            boxC.increase(new PersonRequest(f, 15, i));
            pool.increase(new PersonRequest(15, t, i));
        }
    }

    private void splitCb(PersonRequest req) {
        int f = req.getFromFloor();
        int t = req.getToFloor();
        int i = req.getPersonId();
        if (f < t) {
            boxC.increase(new PersonRequest(f, f + 2, i));
            pool.increase(new PersonRequest(f + 2, t, i));
        } else {
            boxC.increase(new PersonRequest(f, f - 2, i));
            pool.increase(new PersonRequest(f - 2, t, i));
        }
    }

    public void run() {
        while (true) {
            PersonRequest request = elevatorInput.nextPersonRequest();
            if (request == null) {
                pool.increase(request);
                break;
            } else {
                // a new valid request
                // System.out.println(request);
                if (conditionA(request)) {
                    boxA.increase(request);
                } else if (conditionB(request)) {
                    boxB.increase(request);
                } else if (conditionC(request)) {
                    boxC.increase(request);
                } else if (conditionAc(request)) {
                    splitAc(request);
                } else if (conditionAb(request)) {
                    splitAb(request);
                } else if (conditionBc(request)) {
                    splitBc(request);
                } else if (conditionBa(request)) {
                    splitBa(request);
                } else if (conditionCa(request)) {
                    splitCa(request);
                } else if (conditionCb(request)) {
                    splitCb(request);
                }
            }
        }
        try {
            elevatorInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
