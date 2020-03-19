import com.oocourse.elevator1.ElevatorInput;
import com.oocourse.elevator1.PersonRequest;

import java.io.IOException;

public class Producer implements Runnable {
    private PublicBox box;
    private ElevatorInput elevatorInput;

    public Producer(PublicBox box, ElevatorInput elevatorInput) {
        this.box = box;
        this.elevatorInput = elevatorInput;
    }

    public void run() {
        while (true) {
            PersonRequest request = elevatorInput.nextPersonRequest();
            if (request == null) {
                box.increase(request);
                break;
            } else {
                // a new valid request
                // System.out.println(request);
                box.increase(request);
            }
        }
        try {
            elevatorInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
