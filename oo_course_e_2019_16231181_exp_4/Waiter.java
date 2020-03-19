package demo2;

public class Waiter {
    private final int id = counter++;
    private static int counter = 1;

    public String toString() {
        if (id > 9) {
            return "" + id;
        }
        return "0" + id;
    }
}