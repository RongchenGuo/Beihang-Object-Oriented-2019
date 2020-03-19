import com.oocourse.elevator3.PersonRequest;
import java.util.ArrayList;

public class PublicPool {
    private static ArrayList<PersonRequest> pool = new ArrayList<>();
    private PublicBoxA boxA;
    private PublicBoxB boxB;
    private PublicBoxC boxC;
    private boolean already;

    public PublicPool(PublicBoxA a, PublicBoxB b, PublicBoxC c) {
        this.boxA = a;
        this.boxB = b;
        this.boxC = c;
        this.already = false;
    }

    public synchronized void increase(PersonRequest req) {
        // System.out.println(req);
        pool.add(req);
        notifyAll();
    }

    public synchronized void remove(PersonRequest req) {
        pool.remove(req);
        notifyAll();
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

    public void activate(int id) {
        // System.out.println("length of pool:" + pool.size());
        boolean flag = false;
        PersonRequest tmp = null;
        for (PersonRequest req : pool) {
            if (req == null) {
                continue;
            }
            if (req.getPersonId() == id) {
                // System.out.println("yes! " + id);
                flag = true;
                tmp = req;
                if (conditionA(req)) {
                    boxA.increase(req);
                } else if (conditionB(req)) {
                    boxB.increase(req);
                } else if (conditionC(req)) {
                    boxC.increase(req);
                }
                break;
            }
        }
        if (flag) {
            remove(tmp);
        }
        if (pool.size() == 1 && pool.get(0) == null) {
            if (!already) {
                boxA.increase(null);
                boxB.increase(null);
                boxC.increase(null);
            }
            already = true;
        }
    }
}
