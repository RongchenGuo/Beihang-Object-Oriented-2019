import java.math.BigInteger;
import java.util.ArrayList;

public class Poly {
    private ArrayList<Term> polynomial;

    public Poly() {
        ArrayList<Term> polynomial = new ArrayList<Term>();
        this.polynomial = polynomial;
    }

    public Poly addterm(BigInteger coeff, BigInteger degree) {
        Term addedTerm = new Term(coeff, degree);
        ArrayList<Term> tempPoly;
        tempPoly = this.polynomial;
        BigInteger tempDegree;
        int i;
        for (i = 0; i < this.polynomial.size(); i++) {
            tempDegree = tempPoly.get(i).getDegree();
            if (tempDegree.subtract(degree).intValue() == 0) {
                break;
            }
        }
        if (i == this.polynomial.size()) {
            tempPoly.add(addedTerm);
        } else {
            BigInteger tempCoeff = tempPoly.get(i).getCoeff();
            Term tempTerm = new Term((tempCoeff.add(coeff)), degree);
            tempPoly.set(i, tempTerm);
        }
        this.polynomial = tempPoly;
        return this;
    }

    public Poly derivation() {
        ArrayList<Term> newPolynomial = new ArrayList<Term>();
        int length = this.polynomial.size();
        BigInteger oneBigInt = new BigInteger("1");
        for (int i = 0; i < length; i++) {
            BigInteger tmpCoeff = this.polynomial.get(i).getCoeff();
            BigInteger tmpDegree = this.polynomial.get(i).getDegree();
            tmpCoeff = tmpCoeff.multiply(tmpDegree);
            tmpDegree = tmpDegree.subtract(oneBigInt);
            Term tmpTerm = new Term(tmpCoeff, tmpDegree);
            newPolynomial.add(tmpTerm);
        }
        this.polynomial = newPolynomial;
        return this;
    }

    public void PrintPoly() {
        int flag = 0;
        for (int i = 0; i < this.polynomial.size(); i++) {
            BigInteger temp1 = this.polynomial.get(i).getCoeff();
            BigInteger temp2 = this.polynomial.get(i).getDegree();
            if (temp1.intValue() > 0) {
                if (i == 0) {
                    if (temp2.intValue() == 1) {
                        System.out.print(temp1 + "*x");
                        flag = 1;
                    } else if (temp2.intValue() == 0) {
                        System.out.print(temp1 + "");
                        flag = 1;
                    } else {
                        System.out.print(temp1 + "*x^" + temp2);
                        flag = 1;
                    }
                } else {
                    if (temp2.intValue() == 1) {
                        System.out.print("+" + temp1 + "*x");
                        flag = 1;
                    } else if (temp2.intValue() == 0) {
                        System.out.print("+" + temp1 + "");
                        flag = 1;
                    } else {
                        System.out.print("+" + temp1 + "*x^" + temp2);
                        flag = 1;
                    }
                }
            } else if (temp1.intValue() < 0) {
                if (temp2.intValue() == 1) {
                    System.out.print(temp1 + "*x");
                    flag = 1;
                } else if (temp2.intValue() == 0) {
                    System.out.print(temp1 + "");
                    flag = 1;
                } else {
                    System.out.print(temp1 + "*x^" + temp2);
                    flag = 1;
                }
            }
        }
        if (flag == 0) {
            System.out.print("0");
        }
    }
}