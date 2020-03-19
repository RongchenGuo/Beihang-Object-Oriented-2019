import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.isDigit;

public class Cos extends Factor {
    private int pos;
    private Factor factor;
    private BigInteger deg;
    private String str;

    public Cos(String str) {
        if (str.charAt(0) == 'c' && str.charAt(1) == 'o'
                && str.charAt(2) == 's' && str.charAt(3) == '(') {
            int i = 4;
            if (str.charAt(i) == '+' || str.charAt(i) == '-'
                    || isDigit(str.charAt(i))) {
                Const tmp = new Const(str.substring(i));
                this.factor = tmp;
                i += tmp.getPos();
            } else if (str.charAt(i) == 'x') {
                Power tmp = new Power(str.substring(i));
                this.factor = tmp;
                i += tmp.getPos();
            } else if (str.charAt(i) == 's') {
                Sin tmp = new Sin(str.substring(i));
                this.factor = tmp;
                i += tmp.getPos();
            } else if (str.charAt(i) == 'c') {
                Cos tmp = new Cos(str.substring(i));
                this.factor = tmp;
                i += tmp.getPos();
            } else if (str.charAt(i) == '(') {
                Expr tmp = new Expr(str.substring(i));
                this.factor = tmp;
                i += tmp.getPos();
            }
            if (str.charAt(i) == ')') {
                i++;
                if (str.charAt(i) == '^') {
                    i++;
                    int j = i;
                    if (str.charAt(i) == '+' || str.charAt(i) == '-'
                            || isDigit(str.charAt(i))) {
                        i++;
                    }
                    while (isDigit(str.charAt(i))) {
                        i++;
                    }
                    this.deg = new BigInteger(str.substring(j, i));
                    this.str = str.substring(0, i);
                    this.pos = i;
                } else {
                    this.deg = BigInteger.ONE;
                    this.str = str.substring(0, i);
                    this.pos = i;
                }
            } else {
                System.out.print("WRONG FORMAT!");
                System.exit(0);
            }
        } else {
            System.out.print("WRONG FORMAT!");
            System.exit(0);
        }
    }

    @Override
    public int checkForm() {
        String s = "cos\\((.)+\\)(\\^(\\+|-)?(\\d+))?";
        Pattern pattern3 = Pattern.compile(s);
        Matcher matcher = pattern3.matcher(str);
        if (matcher.matches()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String deri() {
        int len = this.str.length();
        int i = len - 1;
        while (this.str.charAt(i) != ')') {
            i--;
        }
        String ss = this.str.substring(4, i);
        BigInteger coeff = this.deg.multiply(new BigInteger("-1"));
        BigInteger degree = this.deg.subtract(BigInteger.ONE);
        if (this.deg.abs().compareTo(new BigInteger("10000")) > 0) {
            System.out.print("WRONG FORMAT!");
            System.exit(0);
        }
        if (degree.compareTo(BigInteger.ZERO) == 0) {
            if (this.factor.deri().length() == 1
                    && this.factor.deri().charAt(0) == '0') {
                return "0";
            } else if (this.factor.deri().length() == 1
                    && this.factor.deri().charAt(0) == '1') {
                return (coeff.toString() + "*sin(" + ss + ")");
            } else {
                return (coeff.toString() + "*sin(" + ss + ")*"
                        + this.factor.deri());
            }
        }
        if (degree.compareTo(BigInteger.ONE) == 0) {
            if (this.factor.deri().length() == 1
                    && this.factor.deri().charAt(0) == '0') {
                return "0";
            } else if (this.factor.deri().length() == 1
                    && this.factor.deri().charAt(0) == '1') {
                return (coeff.toString() + "*cos(" + ss + ")"
                        + "*sin(" + ss + ")");
            } else {
                return (coeff.toString() + "*cos(" + ss + ")"
                        + "*sin(" + ss + ")*" + this.factor.deri());
            }
        } else {
            if (this.factor.deri().length() == 1
                    && this.factor.deri().charAt(0) == '0') {
                return "0";
            } else if (this.factor.deri().length() == 1
                    && this.factor.deri().charAt(0) == '1') {
                return (coeff.toString() + "*cos(" + ss + ")^"
                        + degree.toString() + "*sin(" + ss + ")");
            } else {
                return (coeff.toString() + "*cos(" + ss + ")^"
                        + degree.toString()
                        + "*sin(" + ss + ")*" + this.factor.deri());
            }
        }
    }

    public int getPos() {
        return this.pos;
    }

    @Override
    public String getStr() {
        return this.str;
    }
}
