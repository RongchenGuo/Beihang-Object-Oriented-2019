import java.math.BigInteger;
import java.util.ArrayList;

public class Term {
    private BigInteger coeff;
    private BigInteger degree;
    private BigInteger degreeSin;
    private BigInteger degreeCos;

    public Term() {
        this.coeff = new BigInteger("0");
        this.degree = new BigInteger("0");
        this.degreeSin = new BigInteger("0");
        this.degreeCos = new BigInteger("0");
    }

    public Term(int i) {
        this.coeff = BigInteger.ONE;
        this.degree = BigInteger.ZERO;
        this.degreeSin = BigInteger.ZERO;
        this.degreeCos = BigInteger.ZERO;
    }

    public Term(BigInteger coeff, BigInteger degree,
                BigInteger degreeSin, BigInteger degreeCos) {
        this.coeff = coeff;
        this.degree = degree;
        this.degreeSin = degreeSin;
        this.degreeCos = degreeCos;
    }

    public Term addFactor(BigInteger coeff, BigInteger degree,
                          BigInteger degreeSin, BigInteger degreeCos) {
        this.coeff = this.coeff.multiply(coeff);
        this.degree = this.degree.add(degree);
        this.degreeSin = this.degreeSin.add(degreeSin);
        this.degreeCos = this.degreeCos.add(degreeCos);
        return this;
    }

    public ArrayList<Term> derivative() {
        ArrayList<Term> termList = new ArrayList<Term>();
        if (this.coeff.compareTo(BigInteger.ZERO) == 0) {
            Term tmp = new Term();
            termList.add(tmp);
            return termList;
        }
        if (this.degree.compareTo(BigInteger.ZERO) != 0) {
            BigInteger coeff = this.coeff.multiply(this.degree);
            BigInteger degree = this.degree.subtract(BigInteger.ONE);
            BigInteger degreeSin = this.degreeSin;
            BigInteger degreeCos = this.degreeCos;
            Term tmp = new Term(coeff, degree, degreeSin, degreeCos);
            termList.add(tmp);
        }
        if (this.degreeSin.compareTo(BigInteger.ZERO) != 0) {
            BigInteger coeff = this.coeff.multiply(this.degreeSin);
            BigInteger degree = this.degree;
            BigInteger degreeSin = this.degreeSin.subtract(BigInteger.ONE);
            BigInteger degreeCos = this.degreeCos.add(BigInteger.ONE);
            Term tmp = new Term(coeff, degree, degreeSin, degreeCos);
            termList.add(tmp);
        }
        if (this.degreeCos.compareTo(BigInteger.ZERO) != 0) {
            BigInteger coeff = this.coeff.multiply(this.degreeCos);
            coeff = coeff.multiply(new BigInteger("-1"));
            BigInteger degree = this.degree;
            BigInteger degreeSin = this.degreeSin.add(BigInteger.ONE);
            BigInteger degreeCos = this.degreeCos.subtract(BigInteger.ONE);
            Term tmp = new Term(coeff, degree, degreeSin, degreeCos);
            termList.add(tmp);
        }
        return termList;
    }

    public BigInteger getCoeff() {
        return this.coeff;
    }

    public BigInteger getDegree() {
        return this.degree;
    }

    public BigInteger getDegreeSin() {
        return this.degreeSin;
    }

    public BigInteger getDegreeCos() {
        return this.degreeCos;
    }
}
