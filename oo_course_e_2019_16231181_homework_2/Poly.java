import java.util.ArrayList;
import java.math.BigInteger;

public class Poly {
    private ArrayList<Term> polynomial;

    public Poly() {
        ArrayList<Term> polynomial = new ArrayList<Term>();
        this.polynomial = polynomial;
    }

    public Poly addTerm(BigInteger coeff, BigInteger degree,
                        BigInteger degreeSin, BigInteger degreeCos) {
        Term addedTerm = new Term(coeff, degree, degreeSin, degreeCos);
        ArrayList<Term> l = this.polynomial;
        int i;
        for (i = 0; i < l.size(); i++) {
            BigInteger tmpDegree = l.get(i).getDegree();
            BigInteger tmpSin = l.get(i).getDegreeSin();
            BigInteger tmpCos = l.get(i).getDegreeCos();
            if (tmpDegree.compareTo(degree) == 0 &&
                    tmpSin.compareTo(degreeSin) == 0 &&
                    tmpCos.compareTo(degreeCos) == 0) {
                break;
            }
        }
        if (i == l.size()) {
            l.add(addedTerm);
        } else {
            BigInteger tmpCoeff = l.get(i).getCoeff();
            Term tempTerm = new Term(tmpCoeff.add(coeff),
                    degree, degreeSin, degreeCos);
            l.set(i, tempTerm);
        }
        this.polynomial = l;
        return this;
    }

    public Poly derivation() {
        Poly deri = new Poly();
        int length = this.polynomial.size();
        // System.out.println(this.polynomial.size());
        for (int i = 0; i < length; i++) {
            Term tmpTerm = this.polynomial.get(i);
            ArrayList<Term> tmpList = tmpTerm.derivative();
            // System.out.println(tmpList.size());
            for (int j = 0; j < tmpList.size(); j++) {
                BigInteger tmpCoeff = tmpList.get(j).getCoeff();
                BigInteger tmpDegree = tmpList.get(j).getDegree();
                BigInteger tmpSin = tmpList.get(j).getDegreeSin();
                BigInteger tmpCos = tmpList.get(j).getDegreeCos();
                deri.addTerm(tmpCoeff, tmpDegree, tmpSin, tmpCos);
            }
        }
        return deri;
    }

    private void print23(BigInteger coe, BigInteger cos) {
        int temp1 = coe.compareTo(BigInteger.ONE);
        if (temp1 == 0) {
            if (cos.compareTo(BigInteger.ONE) == 0) {
                System.out.print("cos(x)");
            } else {
                System.out.print("cos(x)^" + cos.toString());
            }
        } else {
            if (cos.compareTo(BigInteger.ONE) == 0) {
                System.out.print(coe.toString() + "*cos(x)");
            } else {
                System.out.print(coe.toString() + "*cos(x)^" + cos.toString());
            }
        }
    }

    private void print24(BigInteger coe, BigInteger sin) {
        int temp1 = coe.compareTo(BigInteger.ONE);
        if (temp1 == 0) {
            if (sin.compareTo(BigInteger.ONE) == 0) {
                System.out.print("sin(x)");
            } else {
                System.out.print("sin(x)^" + sin.toString());
            }
        } else {
            if (sin.compareTo(BigInteger.ONE) == 0) {
                System.out.print(coe.toString() + "*sin(x)");
            } else {
                System.out.print(coe.toString() + "*sin(x)^" + sin.toString());
            }
        }
    }

    private void print34(BigInteger coeff, BigInteger degree) {
        int temp1 = coeff.compareTo(BigInteger.ONE);
        if (temp1 == 0) {
            if (degree.compareTo(BigInteger.ONE) == 0) {
                System.out.print("x");
            } else {
                System.out.print("x^" + degree.toString());
            }
        } else {
            if (degree.compareTo(BigInteger.ONE) == 0) {
                System.out.print(coeff.toString() + "*x");
            } else {
                System.out.print(coeff.toString() + "*x^" + degree.toString());
            }
        }
    }

    private void print2(BigInteger coe, BigInteger sin, BigInteger cos) {
        int temp1 = coe.compareTo(BigInteger.ONE);
        int temp3 = sin.compareTo(BigInteger.ONE);
        int temp4 = cos.compareTo(BigInteger.ONE);
        if (temp1 == 0) {
            if (temp3 == 0 && temp4 == 0) {
                System.out.print("sin(x)*cos(x)");
            } else if (temp3 == 0) {
                System.out.print("sin(x)*cos(x)^" + cos.toString());
            } else if (temp4 == 0) {
                System.out.print("sin(x)^" + sin.toString() + "*cos(x)");
            } else {
                System.out.print("sin(x)^" + sin.toString());
                System.out.print("*cos(x)^" + cos.toString());
            }
        } else {
            if (temp3 == 0 && temp4 == 0) {
                System.out.print(coe.toString());
                System.out.print("*sin(x)*cos(x)");
            } else if (temp3 == 0) {
                System.out.print(coe.toString());
                System.out.print("*sin(x)*cos(x)^" + cos.toString());
            } else if (temp4 == 0) {
                System.out.print(coe.toString());
                System.out.print("*sin(x)^" + sin.toString() + "*cos(x)");
            } else {
                System.out.print(coe.toString());
                System.out.print("*sin(x)^" + sin.toString());
                System.out.print("*cos(x)^" + cos.toString());
            }
        }
    }

    private void print3(BigInteger coe, BigInteger degree, BigInteger cos) {
        int temp1 = coe.compareTo(BigInteger.ONE);
        int temp2 = degree.compareTo(BigInteger.ONE);
        int temp4 = cos.compareTo(BigInteger.ONE);
        if (temp1 == 0) {
            if (temp2 == 0 && temp4 == 0) {
                System.out.print("x*cos(x)");
            } else if (temp2 == 0) {
                System.out.print("x*cos(x)^" + cos.toString());
            } else if (temp4 == 0) {
                System.out.print("x^" + degree.toString() + "*cos(x)");
            } else {
                System.out.print("x^" + degree.toString());
                System.out.print("*cos(x)^" + cos.toString());
            }
        } else {
            if (temp2 == 0 && temp4 == 0) {
                System.out.print(coe.toString());
                System.out.print("*x*cos(x)");
            } else if (temp2 == 0) {
                System.out.print(coe.toString());
                System.out.print("*x*cos(x)^" + cos.toString());
            } else if (temp4 == 0) {
                System.out.print(coe.toString());
                System.out.print("*x^" + degree.toString() + "*cos(x)");
            } else {
                System.out.print(coe.toString());
                System.out.print("*x^" + degree.toString());
                System.out.print("*cos(x)^" + cos.toString());
            }
        }
    }

    private void print4(BigInteger coe, BigInteger degree, BigInteger sin) {
        int temp1 = coe.compareTo(BigInteger.ONE);
        int temp2 = degree.compareTo(BigInteger.ONE);
        int temp3 = sin.compareTo(BigInteger.ONE);
        if (temp1 == 0) {
            if (temp2 == 0 && temp3 == 0) {
                System.out.print("x*sin(x)");
            } else if (temp2 == 0) {
                System.out.print("x*sin(x)^" + sin.toString());
            } else if (temp3 == 0) {
                System.out.print("x^" + degree.toString() + "*sin(x)");
            } else {
                System.out.print("x^" + degree.toString());
                System.out.print("*sin(x)^" + sin.toString());
            }
        } else {
            if (temp2 == 0 && temp3 == 0) {
                System.out.print(coe.toString());
                System.out.print("*x*sin(x)");
            } else if (temp2 == 0) {
                System.out.print(coe.toString());
                System.out.print("*x*sin(x)^" + sin.toString());
            } else if (temp3 == 0) {
                System.out.print(coe.toString());
                System.out.print("*x^" + degree.toString() + "*sin(x)");
            } else {
                System.out.print(coe.toString());
                System.out.print("*x^" + degree.toString());
                System.out.print("*sin(x)^" + sin.toString());
            }
        }
    }

    private void print0(BigInteger coe, BigInteger degree,
                        BigInteger sin, BigInteger cos) {
        int temp1 = coe.compareTo(BigInteger.ONE);
        int temp2 = degree.compareTo(BigInteger.ONE);
        int temp3 = sin.compareTo(BigInteger.ONE);
        int temp4 = cos.compareTo(BigInteger.ONE);
        if (temp1 == 0) {
            if (temp2 == 0 && temp3 == 0 && temp4 == 0) {
                System.out.print("x*sin(x)*cos(x)");
            } else if (temp2 == 0 && temp3 == 0) {
                System.out.print("x*sin(x)*cos(x)^" + cos.toString());
            } else if (temp2 == 0 && temp4 == 0) {
                System.out.print("x*sin(x)^" + sin.toString() + "*cos(x)");
            } else if (temp3 == 0 && temp4 == 0) {
                System.out.print("x^" + degree.toString() + "*sin(x)*cos(x)");
            } else if (temp2 == 0) {
                System.out.print("x*sin(x)^" + sin.toString());
                System.out.print("*cos(x)^" + cos.toString());
            } else if (temp3 == 0) {
                System.out.print("x^" + degree.toString());
                System.out.print("*sin(x)*cos(x)^" + cos.toString());
            } else if (temp4 == 0) {
                System.out.print("x^" + degree.toString());
                System.out.print("*sin(x)^" + sin.toString() + "*cos(x)");
            } else {
                System.out.print("x^" + degree.toString());
                System.out.print("*sin(x)^" + sin.toString());
                System.out.print("*cos(x)^" + cos.toString());
            }
        } else {
            if (temp2 == 0 && temp3 == 0 && temp4 == 0) {
                System.out.print(coe.toString() + "*x*sin(x)*cos(x)");
            } else if (temp2 == 0 && temp3 == 0) {
                System.out.print(coe.toString());
                System.out.print("*x*sin(x)*cos(x)^" + cos.toString());
            } else if (temp2 == 0 && temp4 == 0) {
                System.out.print(coe.toString());
                System.out.print("*x*sin(x)^" + sin.toString() + "*cos(x)");
            } else if (temp3 == 0 && temp4 == 0) {
                System.out.print(coe.toString());
                System.out.print("*x^" + degree.toString() + "*sin(x)*cos(x)");
            } else if (temp2 == 0) {
                System.out.print(coe.toString());
                System.out.print("*x*sin(x)^" + sin.toString());
                System.out.print("*cos(x)^" + cos.toString());
            } else if (temp3 == 0) {
                System.out.print(coe.toString());
                System.out.print("*x^" + degree.toString());
                System.out.print("*sin(x)*cos(x)^" + cos.toString());
            } else if (temp4 == 0) {
                System.out.print(coe.toString());
                System.out.print("*x^" + degree.toString());
                System.out.print("*sin(x)^" + sin.toString() + "*cos(x)");
            } else {
                System.out.print(coe.toString());
                System.out.print("*x^" + degree.toString());
                System.out.print("*sin(x)^" + sin.toString());
                System.out.print("*cos(x)^" + cos.toString());
            }
        }
    }

    private void printTerm(BigInteger coeff, BigInteger degree,
                           BigInteger degreeSin, BigInteger degreeCos) {
        int temp2 = degree.compareTo(BigInteger.ZERO);
        int temp3 = degreeSin.compareTo(BigInteger.ZERO);
        int temp4 = degreeCos.compareTo(BigInteger.ZERO);
        if (temp2 == 0 && temp3 == 0 && temp4 == 0) {
            System.out.print(coeff.toString());
            return;
        }
        if (temp2 == 0 && temp3 == 0) {
            print23(coeff, degreeCos);
            return;
        }
        if (temp2 == 0 && temp4 == 0) {
            print24(coeff, degreeSin);
            return;
        }
        if (temp3 == 0 && temp4 == 0) {
            print34(coeff, degree);
            return;
        }
        if (temp2 == 0) {
            print2(coeff, degreeSin, degreeCos);
            return;
        }
        if (temp3 == 0) {
            print3(coeff, degree, degreeCos);
            return;
        }
        if (temp4 == 0) {
            print4(coeff, degree, degreeSin);
            return;
        }
        print0(coeff, degree, degreeSin, degreeCos);
    }

    public void printPoly() {
        int flag = 0;
        // System.out.println(this.polynomial.size());
        for (int i = 0; i < this.polynomial.size(); i++) {
            // System.out.println(this.polynomial.get(i).getCoeff());
            // System.out.println(this.polynomial.get(i).getDegree());
            // System.out.println(this.polynomial.get(i).getDegreeSin());
            // System.out.println(this.polynomial.get(i).getDegreeCos());
            BigInteger temp1 = this.polynomial.get(i).getCoeff();
            BigInteger temp2 = this.polynomial.get(i).getDegree();
            BigInteger temp3 = this.polynomial.get(i).getDegreeSin();
            BigInteger temp4 = this.polynomial.get(i).getDegreeCos();
            if (temp1.compareTo(BigInteger.ZERO) == 0) {
                continue;
            }
            if (i == 0) {
                if (temp1.compareTo(BigInteger.ZERO) > 0) {
                    printTerm(temp1, temp2, temp3, temp4);
                    flag = 1;
                } else if (temp1.compareTo(BigInteger.ZERO) < 0) {
                    System.out.print('-');
                    printTerm(temp1.multiply(new BigInteger("-1")),
                            temp2, temp3, temp4);
                    flag = 1;
                }
            } else {
                if (temp1.compareTo(BigInteger.ZERO) > 0) {
                    System.out.print("+");
                    printTerm(temp1, temp2, temp3, temp4);
                    flag = 1;
                } else if (temp1.compareTo(BigInteger.ZERO) < 0) {
                    System.out.print('-');
                    printTerm(temp1.multiply(new BigInteger("-1")),
                            temp2, temp3, temp4);
                    flag = 1;
                }
            }
        }
        if (flag == 0) {
            System.out.print("0");
        }
    }
}
