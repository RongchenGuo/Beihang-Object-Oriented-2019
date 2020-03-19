import com.oocourse.TimableOutput;
import com.oocourse.elevator1.PersonRequest;

public class Consumer implements Runnable {
    private PublicBox box;
    private int floor;

    public Consumer(PublicBox box) {
        this.box = box;
        this.floor = 1;
    }

    public void run() {
        while (true) {
            PersonRequest request = box.decrease();
            if (request == null) {
                break;
            } else {
                // a new valid request
                // System.out.println(request);
                // TimableOutput.println(String.format("yes"));
                int fromFloor = request.getFromFloor();
                int toFloor = request.getToFloor();
                int personId = request.getPersonId();
                try {
                    long time1 = Math.abs(fromFloor - floor) * 500;
                    Thread.sleep(time1);
                    TimableOutput.println(String.format("OPEN-%d", fromFloor));
                    floor = fromFloor;
                    Thread.sleep(250);
                    TimableOutput.println(String.format(
                            "IN-%d-%d", personId, fromFloor));
                    Thread.sleep(250);
                    TimableOutput.println(String.format("CLOSE-%d", fromFloor));
                    long time2 = Math.abs(toFloor - floor) * 500;
                    Thread.sleep(time2);
                    TimableOutput.println(String.format("OPEN-%d", toFloor));
                    floor = toFloor;
                    Thread.sleep(250);
                    TimableOutput.println(String.format(
                            "OUT-%d-%d", personId, toFloor));
                    Thread.sleep(250);
                    TimableOutput.println(String.format("CLOSE-%d", toFloor));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
