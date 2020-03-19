import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.isDigit;

public class Const extends Factor {
    private int pos;
    private String str;

    public Const(String str) {
        // System.out.println(str);
        if (str.charAt(0) == '+' || str.charAt(0) == '-'
                || isDigit(str.charAt(0))) {
            int i = 1;
            while (i < str.length() && isDigit(str.charAt(i))) {
                i++;
            }
            this.str = str.substring(0, i);
            // System.out.println(str.substring(0, i));
            this.pos = i;
            if (this.str.length() == 1) {
                if  (this.str.charAt(0) == '+' || this.str.charAt(0) == '-') {
                    System.out.print("WRONG FORMAT!");
                    System.exit(0);
                }
            }
        }
    }

    @Override
    public int checkForm() {
        String s = "(\\+|-)?(\\d+)";
        Pattern pattern = Pattern.compile(s);
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String deri() {
        return "0";
    }

    public int getPos() {
        return this.pos;
    }

    @Override
    public String getStr() {
        return this.str;
    }
}
