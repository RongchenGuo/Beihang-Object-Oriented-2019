import com.oocourse.TimableOutput;
import com.oocourse.elevator3.ElevatorInput;

public class HomeworkSeven {
    public static void main(String[] args) {
        TimableOutput.initStartTimestamp();
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        PublicBoxA boxA = new PublicBoxA();
        PublicBoxB boxB = new PublicBoxB();
        PublicBoxC boxC = new PublicBoxC();
        PublicPool pool = new PublicPool(boxA, boxB, boxC);

        Producer pro = new Producer(pool, boxA, boxB, boxC, elevatorInput);
        Thread t0 = new Thread(pro);
        t0.setPriority(10);
        ConsumerA conA = new ConsumerA(pool, boxA);
        Thread t1 = new Thread(conA);
        t1.setPriority(1);
        ConsumerB conB = new ConsumerB(pool, boxB);
        Thread t2 = new Thread(conB);
        t2.setPriority(1);
        ConsumerC conC = new ConsumerC(pool, boxC);
        Thread t3 = new Thread(conC);
        t3.setPriority(1);
        t0.start();
        t1.start();
        t2.start();
        t3.start();
    }
}
