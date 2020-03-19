import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeworkThird {
    private String checkForm(String str) {
        String s = "(c(\\s+)o)|(o(\\s+)s)|(s(\\s+)i)" +
                "|(i(\\s+)n)|((\\d+)(\\s+)(\\d+))";
        Pattern pattern3 = Pattern.compile(s);
        Matcher matcher = pattern3.matcher(str);
        if (matcher.find()) {
            System.out.print("WRONG FORMAT!");
            System.exit(0);
        }
        s = "((\\+|-)(\\s*)(\\+|-)(\\s*)(\\+|-)(\\s+)(\\d+))" +
                "|((\\^|\\*)(\\s*)(\\+|-)(\\s+)(\\d+))";
        pattern3 = Pattern.compile(s);
        matcher = pattern3.matcher(str);
        if (matcher.find()) {
            System.out.print("WRONG FORMAT!");
            System.exit(0);
        }
        int i;
        for (i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == 'c' || ch == 'o' || ch == 's'
                    || ch == 'i' || ch == 'n' || ch == 'x') {
                continue;
            }
            if (ch == '*' || ch == '+' || ch == '-'
                    || ch == ' ' || ch == '\t' || ch == '^') {
                continue;
            }
            if (ch == '(' || ch == ')' || Character.isDigit(ch)) {
                continue;
            }
            break;
        }
        if (i != str.length()) {
            System.out.print("WRONG FORMAT!");
            System.exit(0);
        }
        String ss = str.replaceAll("\\s*", "");
        if (ss.length() == 0) {
            System.out.print("WRONG FORMAT!");
            System.exit(0);
        }
        s = "(\\+|-)*";
        pattern3 = Pattern.compile(s);
        matcher = pattern3.matcher(ss);
        if (matcher.matches()) {
            System.out.print("WRONG FORMAT!");
            System.exit(0);
        }
        return ("(" + ss + ")");
    }

    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            if (!in.hasNextLine()) {
                System.out.print("WRONG FORMAT!");
                return;
            }
            String str = in.nextLine();
            // String str = "((10))";
            HomeworkThird tt = new HomeworkThird();
            str = tt.checkForm(str);
            Expr expr = new Expr(str);
            String tmp = expr.deri();
            if (tmp.substring(1, tmp.length() - 1).length() == 0) {
                System.out.print("0");
            }
            System.out.print(tmp.substring(1, tmp.length() - 1));
        } catch (Exception e) {
            // System.out.println("soo");
            System.out.print("WRONG FORMAT!");
        }
    }
}
