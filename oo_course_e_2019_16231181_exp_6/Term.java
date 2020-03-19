import java.math.BigInteger;

public class Term {
    private BigInteger coeff;
    private BigInteger degree;

    public Term() {
        BigInteger coeff = new BigInteger("0");
        BigInteger degree = new BigInteger("0");
        this.coeff = coeff;
        this.degree = degree;
    }

    public Term(BigInteger coeff, BigInteger degree) {
        this.coeff = coeff;
        this.degree = degree;
    }

    public BigInteger getCoeff() {
        return this.coeff;
    }

    public BigInteger getDegree() {
        return this.degree;
    }
}
