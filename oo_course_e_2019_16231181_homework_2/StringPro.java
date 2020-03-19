import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringPro {
    private int status;
    private ArrayList al;

    public StringPro(String str) {
        String s1 = "((\\s*)sin(\\s*)\\((\\s*)x(\\s*)\\)"
                + "((\\s*)\\^(\\s*)(\\+|-)?(\\d+)(\\s*))?)";
        String s2 = "((\\s*)cos(\\s*)\\((\\s*)x(\\s*)\\)"
                + "((\\s*)\\^(\\s*)(\\+|-)?(\\d+)(\\s*))?)";
        String s3 = "((\\s*)x(\\s*)(\\^(\\s*)(\\+|-)?(\\d+)(\\s*))?)";
        String s4 = "((\\s*)(\\+|-)?(\\d+)(\\s*))";
        String str1 = "(" + s1 + "|" + s2 + "|" + s3 + "|" + s4 + ")";
        String str2 = "((\\+|-)(\\s*)(\\+|-)?(\\s*)" + str1
                + "(\\s*)(\\*(\\s*)" + str1 + "(\\s*))*(\\s*))";
        String str3 = "((\\s*)" + str2 + "(\\s*))*";
        Pattern pattern1 = Pattern.compile(str1);
        Pattern pattern2 = Pattern.compile(str2);
        Pattern pattern3 = Pattern.compile(str3);
        Matcher matcher = pattern3.matcher(str);
        if (matcher.matches()) {
            this.status = 1;
        } else {
            this.status = -1;
            return;
        }
        matcher = pattern2.matcher(str);
        this.al = new ArrayList();
        while (matcher.find()) {
            String tmp = matcher.group().replace(" ", "");
            tmp = tmp.replace("\t", "");
            this.al.add(tmp);
        }
        // System.out.println(this.al.toString());
    }

    private Term proCos(String s) {
        if (s.length() == 6) {
            return new Term(BigInteger.ONE, BigInteger.ZERO,
                    BigInteger.ZERO, BigInteger.ONE);
        }
        String ss = s.substring(7);
        return new Term(BigInteger.ONE, BigInteger.ZERO,
                BigInteger.ZERO, new BigInteger(ss));
    }

    private Term proSin(String s) {
        if (s.length() == 6) {
            return new Term(BigInteger.ONE, BigInteger.ZERO,
                    BigInteger.ONE, BigInteger.ZERO);
        }
        String ss = s.substring(7);
        return new Term(BigInteger.ONE, BigInteger.ZERO,
                new BigInteger(ss), BigInteger.ZERO);
    }

    private Term proDeg(String s) {
        if (s.length() == 1) {
            return new Term(BigInteger.ONE, BigInteger.ONE,
                    BigInteger.ZERO, BigInteger.ZERO);
        }
        String ss = s.substring(2);
        return new Term(BigInteger.ONE, new BigInteger(ss),
                BigInteger.ZERO, BigInteger.ZERO);
    }

    private Term proNum(String s) {
        return new Term(new BigInteger(s), BigInteger.ZERO,
                BigInteger.ZERO, BigInteger.ZERO);
    }

    private Term proTerm(String s) {
        int i = 0;
        Term t = new Term(0);
        // System.out.println(s);
        while (s.charAt(i) == '+' || s.charAt(i) == '-') {
            if (s.charAt(i) == '-') {
                t.addFactor(new BigInteger("-1"), BigInteger.ZERO,
                        BigInteger.ZERO, BigInteger.ZERO);
            }
            i++;
        }
        String ss = s.substring(i);
        // System.out.println(ss);
        String[] strs = ss.split("\\*");
        for (i = 0; i < strs.length; i++) {
            // System.out.print(strs[i] + "  ");
            if (strs[i].charAt(0) == 'c') {
                Term tmp = proCos(strs[i]);
                t.addFactor(tmp.getCoeff(), tmp.getDegree(),
                        tmp.getDegreeSin(), tmp.getDegreeCos());
            } else if (strs[i].charAt(0) == 's') {
                Term tmp = proSin(strs[i]);
                t.addFactor(tmp.getCoeff(), tmp.getDegree(),
                        tmp.getDegreeSin(), tmp.getDegreeCos());
            } else if (strs[i].charAt(0) == 'x') {
                Term tmp = proDeg(strs[i]);
                t.addFactor(tmp.getCoeff(), tmp.getDegree(),
                        tmp.getDegreeSin(), tmp.getDegreeCos());
            } else if (strs[i].charAt(0) == '+' || strs[i].charAt(0) == '-'
                    || Character.isDigit(strs[i].charAt(0))) {
                Term tmp = proNum(strs[i]);
                t.addFactor(tmp.getCoeff(), tmp.getDegree(),
                        tmp.getDegreeSin(), tmp.getDegreeCos());
            } else {
                System.out.print(strs[i]);
            }
        }
        return t;
    }

    public Poly proPoly() {
        Poly poly = new Poly();
        ArrayList tlist = this.al;
        for (int i = 0; i < tlist.size(); i++) {
            // System.out.print(tlist.get(i).toString());
            Term tmp = proTerm(tlist.get(i).toString());
            poly.addTerm(tmp.getCoeff(), tmp.getDegree(),
                    tmp.getDegreeSin(), tmp.getDegreeCos());
        }
        return poly;
    }

    public int getStatus() {
        return this.status;
    }
}
