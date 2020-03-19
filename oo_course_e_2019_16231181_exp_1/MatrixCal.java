import java.util.Scanner;

public class MatrixCal {
    int[][] matrix1, matrix2, answer;
    int dim;
    char operator;

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Matrix m1 = new Matrix(keyboard.nextLine());
        String op;
        char operator = '\0';
        op = keyboard.nextLine();
        operator = op.charAt(0);
        if (m1.getStatus() == 0) {
            if (operator == 't') {
                System.out.print(m1.transpose());
            } else if (operator == '+') {
                Matrix m2 = new Matrix(keyboard.nextLine());
                if (m2.getStatus() == 0) {
                    if (m1.getOrder() != m2.getOrder()) {
                        System.out.println("Illegal Operation!");
                    } else {
                        System.out.print(m1.add(m2));
                    }
                } else if (m2.getStatus() == 1) {
                    System.out.println("Empty Matrix!");
                } else if (m2.getStatus() == 2){
                    System.out.println("Illegal Input!");
                }
            } else if (operator == '-') {
                Matrix m2 = new Matrix(keyboard.nextLine());
                if (m2.getStatus() == 0) {
                    if (m1.getOrder() != m2.getOrder()) {
                        System.out.println("Illegal Operation!");
                    } else {
                        System.out.print(m1.sub(m2));
                    }
                } else if (m2.getStatus() == 1) {
                    System.out.println("Empty Matrix!");
                } else if (m2.getStatus() == 2){
                    System.out.println("Illegal Input!");
                }
            } else if (operator == '*') {
                Matrix m2 = new Matrix(keyboard.nextLine());
                if (m2.getStatus() == 0) {
                    if (m1.getOrder() != m2.getOrder()) {
                        System.out.println("Illegal Operation!");
                    } else {
                        System.out.print(m1.multiply(m2));
                    }
                } else if (m2.getStatus() == 1) {
                    System.out.println("Empty Matrix!");
                } else if (m2.getStatus() == 2){
                    System.out.println("Illegal Input!");
                }
            } else {
                System.out.println("Illegal Input!");
            }
        } else if (m1.getStatus() == 1) {
            System.out.println("Empty Matrix!");
        } else if (m1.getStatus() == 2){
            System.out.println("Illegal Input!");
        }
        keyboard.close();
    }
}

