import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeworkSecond {

    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            if (!in.hasNextLine()) {
                System.out.print("WRONG FORMAT!");
                return;
            }
            String str = in.nextLine();
            str = str + " ";
            String tmpStr = "(\\s*)(\\+|-)(\\s*)(\\+|-)(.*)";
            Pattern pattern = Pattern.compile(tmpStr);
            Matcher matcher = pattern.matcher(str);
            if (!matcher.matches()) {
                str = '+' + str;
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
                return;
            }
            StringPro stringpro = new StringPro(str);
            if (stringpro.getStatus() == -1) {
                System.out.print("WRONG FORMAT!");
            } else {
                Poly poly = stringpro.proPoly();
                // poly.printPoly();
                // System.out.println();
                Poly p2 = poly.derivation();
                p2.printPoly();
            }
        }
        catch (Exception e) {
            System.out.print("WRONG FORMAT!");
        }
    }
}
