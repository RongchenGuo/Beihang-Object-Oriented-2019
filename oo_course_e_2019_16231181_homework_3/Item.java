import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.isDigit;

public class Item {
    private ArrayList<Factor> factors;
    private int pos;
    private String str;

    public Item(String str) {
        // System.out.println(str);
        this.factors = new ArrayList<>();
        int i = 0;
        if (str.charAt(0) == '-') {
            Const tmp = new Const("-1");
            this.factors.add(tmp);
        } else if (str.charAt(0) != '+') {
            System.out.print("WRONG FORMAT!");
            System.exit(0);
        }
        if (str.charAt(1) == '-') {
            Const tmp = new Const("-1");
            this.factors.add(tmp);
            i = 1;
        } else if (str.charAt(1) == '+') {
            i = 1;
        }
        i++;
        while (true) {
            if (str.charAt(i) == '+' || str.charAt(i) == '-'
                    || isDigit(str.charAt(i))) {
                // System.out.println(str.substring(i));
                Const tmp = new Const(str.substring(i));
                this.factors.add(tmp);
                i += tmp.getPos();
            } else if (str.charAt(i) == 'x') {
                Power tmp = new Power(str.substring(i));
                this.factors.add(tmp);
                i += tmp.getPos();
            } else if (str.charAt(i) == 's') {
                Sin tmp = new Sin(str.substring(i));
                this.factors.add(tmp);
                i += tmp.getPos();
            } else if (str.charAt(i) == 'c') {
                Cos tmp = new Cos(str.substring(i));
                this.factors.add(tmp);
                i += tmp.getPos();
            } else if (str.charAt(i) == '(') {
                Expr tmp = new Expr(str.substring(i));
                this.factors.add(tmp);
                i += tmp.getPos();
            }
            if (i < str.length() && str.charAt(i) == '*') {
                i++;
                continue;
            } else if (i < str.length() && str.charAt(i) != '*') {
                break;
            } else {
                System.out.print("WRONG FORMAT!");
                System.exit(0);
            }
        }
        this.str = str.substring(0, i);
        this.pos = i;
    }

    public int getPos() {
        return this.pos;
    }

    public String deri() {
        ArrayList<String> deriFactors = new ArrayList<>();
        String tmpStr = "";
        for (int i = 0; i < this.factors.size(); i++) {
            String temp = this.factors.get(i).deri();
            String s = "(\\()+(\\))+";
            Pattern pattern = Pattern.compile(s);
            Matcher matcher = pattern.matcher(temp);
            if (matcher.matches()) {
                deriFactors.add("0");
            } else {
                deriFactors.add(temp);
            }
        }
        for (int i = 0; i < this.factors.size(); i++) {
            if (i == 0) {
                if (deriFactors.get(i).length() == 1
                        && deriFactors.get(i).charAt(0) == '0') {
                    continue;
                }
                tmpStr = tmpStr + deriFactors.get(i);
                for (int j = 0; j < this.factors.size(); j++) {
                    if (j != i) {
                        tmpStr = tmpStr + "*" + this.factors.get(j).getStr();
                    }
                }
            } else {
                if (deriFactors.get(i).charAt(0) == '0'
                        && deriFactors.get(i).length() == 1) {
                    continue;
                }
                if (deriFactors.get(i).charAt(0) == '-'
                        || deriFactors.get(i).charAt(0) == '+') {
                    tmpStr = tmpStr + deriFactors.get(i);
                    for (int j = 0; j < this.factors.size(); j++) {
                        if (j != i) {
                            tmpStr = tmpStr + "*"
                                    + this.factors.get(j).getStr();
                        }
                    }
                } else {
                    tmpStr = tmpStr + "+" + deriFactors.get(i);
                    for (int j = 0; j < this.factors.size(); j++) {
                        if (j != i) {
                            tmpStr = tmpStr + "*"
                                    + this.factors.get(j).getStr();
                        }
                    }
                }
            }
        }
        if (this.factors.size() == 1) {
            return tmpStr;
        } else {
            return tmpStr;
            // return ("(" + tmpStr + ")");
        }
    }

    public String getStr() {
        return this.str;
    }

    private String normalization(String str) {
        String s = "(\\()+(\\))+";
        Pattern pattern = Pattern.compile(s);
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return "0";
        } else {
            return str;
        }
    }
}
