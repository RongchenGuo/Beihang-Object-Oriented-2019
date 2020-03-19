import java.math.BigInteger;
import java.util.Scanner;

public class HomeworkFirst {

    private int pos;
    private int status;
    private BigInteger coeff;
    private BigInteger degree;
    private char op1;
    private char op2;
    private int flag;
    private String str;
    private Poly poly1;

    public HomeworkFirst(String str, Poly poly1) {
        this.pos = 0;
        this.status = 0;
        this.coeff = new BigInteger("0");
        this.degree = new BigInteger("0");
        this.op1 = '+';
        this.op2 = '+';
        this.flag = 0;
        this.str = str;
        this.poly1 = poly1;
    }

    public HomeworkFirst Case0(HomeworkFirst homework) {
        int i = homework.pos;
        char ch = homework.str.charAt(i);
        if (ch == ' ' || ch == '\t') {
            homework.status = 0;
        } else if (ch == '+' || ch == '-') {
            homework.status = 1;
            homework.op1 = ch;
        } else if (ch == 'x') {
            homework.status = 4;
            homework.coeff = new BigInteger("1");
        } else if (Character.isDigit(ch)) {
            homework.status = 2;
            homework.coeff = new BigInteger("0");
            while (Character.isDigit(homework.str.charAt(i))) {
                char tempChar = homework.str.charAt(i);
                String tempStr = String.valueOf(tempChar);
                BigInteger tempInt = new BigInteger(tempStr);
                BigInteger tenBigInt = new BigInteger("10");
                homework.coeff = homework.coeff.multiply(tenBigInt);
                homework.coeff = homework.coeff.add(tempInt);
                i++;
                if (i == homework.str.length()) {
                    break;
                }
            }
            i--;
            homework.pos = i;
        } else {
            homework.status = -1;
        }
        return homework;
    }

    public HomeworkFirst Case1(HomeworkFirst homework) {
        int i = homework.pos;
        char ch = homework.str.charAt(i);
        if (ch == ' ' || ch == '\t') {
            homework.status = 1;
        } else if (ch == '+' || ch == '-') {
            homework.status = 7;
            homework.op2 = ch;
        } else if (ch == 'x') {
            homework.status = 4;
            if (homework.op1 == '-') {
                homework.coeff = new BigInteger("-1");
            } else {
                homework.coeff = new BigInteger("1");
            }
        } else if (Character.isDigit(ch)) {
            homework.status = 2;
            homework.coeff = new BigInteger("0");
            while (Character.isDigit(homework.str.charAt(i))) {
                char tempChar = homework.str.charAt(i);
                String tempStr = String.valueOf(tempChar);
                BigInteger tempInt = new BigInteger(tempStr);
                BigInteger tenBigInt = new BigInteger("10");
                homework.coeff = homework.coeff.multiply(tenBigInt);
                homework.coeff = homework.coeff.add(tempInt);
                i++;
                if (i == homework.str.length()) {
                    break;
                }
            }
            i--;
            homework.pos = i;
            if (homework.op1 == '-') {
                BigInteger minusOneBigInt = new BigInteger("-1");
                homework.coeff = homework.coeff.multiply(minusOneBigInt);
            }
        } else {
            homework.status = -1;
        }
        return homework;
    }

    public HomeworkFirst Case2(HomeworkFirst homework) {
        int i = homework.pos;
        char ch = homework.str.charAt(i);
        if (ch == ' ' || ch == '\t') {
            homework.status = 2;
        } else if (ch == '*') {
            homework.status = 3;
        } else if (ch == '+' || ch == '-') {
            homework.status = 1;
            // 加入一项
            homework.poly1.addterm(coeff, new BigInteger("0"));
            homework.op1 = ch;
            homework.op2 = '+';
            homework.flag = 0;
            homework.coeff = new BigInteger("0");
            homework.degree = new BigInteger("0");
        } else {
            homework.status = -1;
        }
        return homework;
    }

    public HomeworkFirst Case3(HomeworkFirst homework) {
        int i = homework.pos;
        char ch = homework.str.charAt(i);
        if (ch == ' ' || ch == '\t') {
            homework.status = 3;
        } else if (ch == 'x') {
            homework.status = 4;
        } else {
            homework.status = -1;
        }
        return homework;
    }

    public HomeworkFirst Case4(HomeworkFirst homework) {
        int i = homework.pos;
        char ch = homework.str.charAt(i);
        if (ch == ' ' || ch == '\t') {
            homework.status = 4;
        } else if (ch == '^') {
            homework.status = 5;
        } else if (ch == '+' || ch == '-') {
            homework.status = 1;
            // 加入一项
            homework.poly1.addterm(homework.coeff, new BigInteger("1"));
            homework.op1 = ch;
            homework.op2 = '+';
            homework.flag = 0;
            homework.coeff = new BigInteger("0");
            homework.degree = new BigInteger("0");
        } else {
            homework.status = -1;
        }
        return homework;
    }

    public HomeworkFirst Case5(HomeworkFirst homework) {
        int i = homework.pos;
        char ch = homework.str.charAt(i);
        if (ch == ' ' || ch == '\t') {
            homework.status = 5;
        } else if (ch == '+' || ch == '-') {
            homework.status = 8;
            homework.flag = 0;
            if (ch == '-') {
                homework.flag = 1;
            }
        } else if (Character.isDigit(ch)) {
            homework.status = 6;
            homework.degree = new BigInteger("0");
            while (Character.isDigit(homework.str.charAt(i))) {
                char tempChar = homework.str.charAt(i);
                String tempStr = String.valueOf(tempChar);
                BigInteger tempInt = new BigInteger(tempStr);
                BigInteger tenBigInt = new BigInteger("10");
                homework.degree = homework.degree.multiply(tenBigInt);
                homework.degree = homework.degree.add(tempInt);
                i++;
                if (i == homework.str.length()) {
                    break;
                }
            }
            i--;
            homework.pos = i;
            // 加入一项
            homework.poly1.addterm(coeff, degree);
            homework.op1 = '+';
            homework.op2 = '+';
            homework.flag = 0;
            homework.coeff = new BigInteger("0");
            homework.degree = new BigInteger("0");
        } else {
            homework.status = -1;
        }
        return homework;
    }

    public HomeworkFirst Case6(HomeworkFirst homework) {
        int i = homework.pos;
        char ch = homework.str.charAt(i);
        if (ch == ' ' || ch == '\t') {
            homework.status = 6;
        } else if (ch == '+' || ch == '-') {
            homework.status = 1;
            homework.op1 = ch;
        } else {
            homework.status = -1;
        }
        return homework;
    }

    public HomeworkFirst Case7(HomeworkFirst homework) {
        int i = homework.pos;
        char ch = homework.str.charAt(i);
        if (ch == ' ' || ch == '\t') {
            homework.status = 3;
            if (homework.op1 != homework.op2) {
                homework.coeff = new BigInteger("-1");
            } else {
                homework.coeff = new BigInteger("1");
            }
        } else if (ch == 'x') {
            homework.status = 4;
            if (homework.op1 != homework.op2) {
                homework.coeff = new BigInteger("-1");
            } else {
                homework.coeff = new BigInteger("1");
            }
        } else if (Character.isDigit(ch)) {
            homework.status = 2;
            homework.coeff = new BigInteger("0");
            while (Character.isDigit(homework.str.charAt(i))) {
                char tempChar = homework.str.charAt(i);
                String tempStr = String.valueOf(tempChar);
                BigInteger tempInt = new BigInteger(tempStr);
                BigInteger tenBigInt = new BigInteger("10");
                homework.coeff = homework.coeff.multiply(tenBigInt);
                homework.coeff = homework.coeff.add(tempInt);
                i++;
                if (i == homework.str.length()) {
                    break;
                }
            }
            i--;
            homework.pos = i;
            if (homework.op1 != homework.op2) {
                BigInteger minusOneBigInt = new BigInteger("-1");
                homework.coeff = homework.coeff.multiply(minusOneBigInt);
            }
        } else {
            homework.status = -1;
        }
        return homework;
    }

    public HomeworkFirst Case8(HomeworkFirst homework) {
        int i = homework.pos;
        char ch = homework.str.charAt(i);
        if (Character.isDigit(ch)) {
            homework.status = 6;
            homework.degree = new BigInteger("0");
            while (Character.isDigit(homework.str.charAt(i))) {
                char tempChar = homework.str.charAt(i);
                String tempStr = String.valueOf(tempChar);
                BigInteger tempInt = new BigInteger(tempStr);
                BigInteger tenBigInt = new BigInteger("10");
                homework.degree = homework.degree.multiply(tenBigInt);
                homework.degree = homework.degree.add(tempInt);
                i++;
                if (i == homework.str.length()) {
                    break;
                }
            }
            i--;
            homework.pos = i;
            if (homework.flag == 1) {
                BigInteger minusOneBigInt = new BigInteger("-1");
                homework.degree = homework.degree.multiply(minusOneBigInt);
            }
            // 加入一项
            homework.poly1.addterm(homework.coeff, homework.degree);
            homework.op1 = '+';
            homework.op2 = '+';
            homework.flag = 0;
            homework.coeff = new BigInteger("0");
            homework.degree = new BigInteger("0");
        } else {
            homework.status = -1;
        }
        return homework;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        str = str + " ";
        Poly poly1 = new Poly();
        HomeworkFirst homework = new HomeworkFirst(str, poly1);
        for (int i = 0; i < str.length(); i++) {
            homework.pos = i;
            switch (homework.status) {
                case 0:
                    homework.Case0(homework);
                    break;
                case 1:
                    homework.Case1(homework);
                    break;
                case 2:
                    homework.Case2(homework);
                    break;
                case 3:
                    homework.Case3(homework);
                    break;
                case 4:
                    homework.Case4(homework);
                    break;
                case 5:
                    homework.Case5(homework);
                    break;
                case 6:
                    homework.Case6(homework);
                    break;
                case 7:
                    homework.Case7(homework);
                    break;
                case 8:
                    homework.Case8(homework);
                    break;
                default:
                    homework.status = -1;
                    break;
            }
            i = homework.pos;
        }
        if (homework.status == 2) {
            homework.status = 6;
            // 加入一项
            homework.poly1.addterm(homework.coeff, new BigInteger("0"));
        } else if (homework.status == 4) {
            homework.status = 6;
            // 加入一项
            homework.poly1.addterm(homework.coeff, new BigInteger("1"));
        }
        if (homework.status != 6) {
            System.out.print("WRONG FORMAT!");
        } else {
            homework.poly1.derivation();
            homework.poly1.PrintPoly();
        }
    }
}
