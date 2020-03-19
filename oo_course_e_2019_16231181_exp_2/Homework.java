import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Homework {
    public static void main(String[] args) {
        Developer p1 = new Developer();
        p1.setYears(5);
        p1.setNum(1);
        Developer p2 = new Developer();
        p2.setYears(1.2);
        p2.setNum(2);
        Finance p3 = new Finance();
        p3.setNum(3);
        Finance p4 = new Finance();
        p4.setNum(4);
        Market p5 = new Market();
        p5.setNum(5);
        Market p6 = new Market();
        p6.setNum(6);
        Seller p7 = new Seller();
        p7.setNum(7);
        Seller p8 = new Seller();
        p8.setNum(8);

        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        String[] strs = str.split("\\s+");
        int function = Integer.parseInt(strs[0]);
        int number = Integer.parseInt(strs[1]);
        int workhour = Integer.parseInt(strs[2]);
        int tmp1 = 0;
        double tmp2 = 0;
        if(function == 1) {
            if (number == 1) {
                tmp1 = p1.getLevel(workhour);
                tmp2 = p1.wholeSal(workhour);
            }
            else if (number == 2) {
                tmp1 = p2.getLevel(workhour);
                tmp2 = p2.wholeSal(workhour);
            }
            else if (number == 3) {
                tmp1 = p3.getLevel(workhour);
                tmp2 = p3.wholeSal(workhour);
            }
            else if (number == 4) {
                tmp1 = p4.getLevel(workhour);
                tmp2 = p4.wholeSal(workhour);
            }
            else if (number == 5) {
                tmp1 = p5.getLevel(workhour);
                tmp2 = p5.wholeSal(workhour);
            }
            else if (number == 6) {
                tmp1 = p6.getLevel(workhour);
                tmp2 = p6.wholeSal(workhour);
            }
            else if (number == 7) {
                workhour = Integer.parseInt(strs[3]);
                tmp1 = p7.getLevel(workhour);
                tmp2 = p7.wholeSal(workhour);
            }
            else if (number == 7) {
                workhour = Integer.parseInt(strs[3]);
                tmp1 = p7.getLevel(workhour);
                tmp2 = p7.wholeSal(workhour);
            }
            else {
                System.out.print("Invalid Query!");
                System.exit(0);
            }
            if (tmp1 == 1) {
                System.out.print("A ");
            }
            else {
                System.out.print("B ");
            }
            System.out.print(tmp2);
        } else {
            System.out.print("Reimbursement Refused!");
        }
    }
}
