import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.isDigit;

public class Power extends Factor {
    private int pos;
    private BigInteger deg;
    private String str;

    public Power(String str) {
        if (str.charAt(1) == '^') {
            int i = 2;
            if (str.charAt(i) == '+' || str.charAt(i) == '-'
                    || isDigit(str.charAt(i))) {
                i++;
                while (isDigit(str.charAt(i))) {
                    i++;
                }
                this.deg = new BigInteger(str.substring(2, i));
                this.str = str.substring(0, i);
                this.pos = i;
            } else {
                System.out.print("WRONG FORMAT!");
                System.exit(0);
            }
        } else {
            this.deg = BigInteger.ONE;
            this.str = str.substring(0, 1);
            this.pos = 1;
        }
    }

    @Override
    public int checkForm() {
        String s = "x(\\^(\\+|-)?(\\d+))?";
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
        BigInteger coeff = this.deg;
        BigInteger degree = this.deg.subtract(BigInteger.ONE);
        if (this.deg.abs().compareTo(new BigInteger("10000")) > 0) {
            System.out.print("WRONG FORMAT!");
            System.exit(0);
        }
        if (degree.compareTo(BigInteger.ZERO) == 0) {
            return coeff.toString();
        }
        if (degree.compareTo(BigInteger.ONE) == 0) {
            return (coeff.toString() + "*x");
        } else {
            return (coeff.toString() + "*x^" + degree.toString());
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
