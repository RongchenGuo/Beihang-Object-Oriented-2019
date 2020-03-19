import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expr extends Factor {
    private ArrayList<Item> items;
    private int pos;
    private String str;

    public Expr(String s) {
        String ss = s + " ";
        String tmpStr = "\\((\\+|-)(\\+|-)(.*)";
        Pattern pattern = Pattern.compile(tmpStr);
        Matcher matcher = pattern.matcher(ss);
        int flag = 0;
        if (!matcher.matches()) {
            // System.out.println("sljfl");
            ss = "(+" + ss.substring(1);
            flag = 1;
        }
        // System.out.println(ss);
        int i = 1;
        this.items = new ArrayList<>();
        while (i < ss.length()) {
            // System.out.println(ss.charAt(i));
            if (ss.charAt(i) == '+' | ss.charAt(i) == '-') {
                // System.out.println(ss.substring(i));
                Item tmp = new Item(ss.substring(i));
                this.items.add(tmp);
                i += tmp.getPos();
            } else if (ss.charAt(i) == ')') {
                i++;
                break;
            } else {
                System.out.print("WRONG FORMAT!");
                System.exit(0);
            }
        }
        this.str = ss.substring(0, i);
        if (flag == 1) {
            this.pos = i - 1;
        } else {
            this.pos = i;
        }
    }

    @Override
    public int checkForm() {
        String s = "\\((.)*\\)";
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
        String tmp = "(";
        for (int i = 0; i < items.size(); i++) {
            if (i == 0) {
                tmp = tmp + items.get(i).deri();
            } else {
                if (items.get(i).deri().length() != 0
                        && items.get(i).deri().charAt(0) == '-') {
                    tmp = tmp + items.get(i).deri();
                } else if (items.get(i).deri().length() != 0
                        && items.get(i).deri().charAt(0) == '+') {
                    tmp = tmp + items.get(i).deri();
                } else if (items.get(i).deri().length() != 0) {
                    tmp = tmp + "+" + items.get(i).deri();
                }
            }
        }
        tmp = tmp + ")";
        return tmp;
    }

    public int getPos() {
        return this.pos;
    }

    @Override
    public String getStr() {
        return this.str;
    }
}
