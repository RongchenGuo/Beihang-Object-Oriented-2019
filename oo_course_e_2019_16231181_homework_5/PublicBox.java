import com.oocourse.TimableOutput;
import com.oocourse.elevator1.PersonRequest;
import com.oocourse.elevator1.ElevatorInput;

import java.util.LinkedList;

public class PublicBox {
    private static LinkedList<PersonRequest> list = new LinkedList<>();

    public synchronized void increase(PersonRequest request) {
        list.addLast(request);
        // list.notify();
        notify();
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
        // list.notify();
        notify();
        return request;
    }

    public static void main(String[] args) {
        TimableOutput.initStartTimestamp();
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        PublicBox box = new PublicBox();

        Producer pro = new Producer(box, elevatorInput);
        Consumer con = new Consumer(box);
        Thread t2 = new Thread(pro);
        Thread t1 = new Thread(con);
        t2.start();
        t1.start();
        // System.out.println("dkfhs");
        /* try {
            elevatorInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
