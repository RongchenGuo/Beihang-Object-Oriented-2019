import java.math.BigInteger;

class Matrix {
    private BigInteger[][] mat;
    private int status;

    public Matrix() {
        mat = null;
        status = 0;
    }

    public Matrix(int order) {
        mat = new BigInteger[order][order];
        status = 0;
    }

    public Matrix(String str) {
        int order;

        String[] strs = str.split("[{},]");
        int i;
        for (i = 2; i < strs.length; i++) {
            if (!strs[i].equals(""))
                continue;
            else
                break;
        }
        order = i - 2;
        // System.out.println("order:" + order);
        if (order == 0) {
            status = 1;
        }
        else if (strs.length!=((order+2)*order)){
            status = 2;
        }
        else {
            status = 0;
            mat = new BigInteger[order][order];
            int j;
            for (i = 0; i < strs.length; i += 2 + order) {
                for (j = 0; j < order; j++) {
                    mat[i / (2 + order)][j] = new BigInteger(strs[i + 2 + j]);
                    // mat[i/(2+order)][j] = Integer.parseInt(strs[i+2+j]);
                }
            }
        }
    }

    public int getStatus() {
        return this.status;
    }

    public int getOrder() {
        return mat.length;
    }

    protected Matrix add(Matrix addThis) {
        int i, j, order;
        order = getOrder();
        Matrix temp = new Matrix(order);
        for (i = 0; i < order; i++) {
            for (j = 0; j < order; j++) {
                temp.mat[i][j] = mat[i][j].add(addThis.mat[i][j]);
            }
        }
        return temp;
    }

    protected Matrix sub(Matrix subThis) {
        int i, j, order;
        order = getOrder();
        Matrix temp = new Matrix(order);
        for (i = 0; i < order; i++) {
            for (j = 0; j < order; j++) {
                temp.mat[i][j] = mat[i][j].subtract(subThis.mat[i][j]);
            }
        }
        return temp;
    }

    protected Matrix transpose() {
        int order;
        order = getOrder();
        Matrix temp = new Matrix(order);
        int i, j;
        for (i = 0; i < order; i++) {
            for (j = 0; j < order; j++) {
                temp.mat[i][j] = mat[j][i];
            }
        }
        return temp;
    }

    protected Matrix multiply(Matrix multiplyThis) {
        int i, j, k, order;
        order = getOrder();
        Matrix temp = new Matrix(order);
        for (i = 0; i < order; i++) {
            for (j = 0; j < order; j++) {
                BigInteger element = new BigInteger("0");
                for (k = 0; k < order; k++) {
                    element = element.add(mat[i][k].multiply(multiplyThis.mat[k][j]));
                }
                temp.mat[i][j] = element;
            }
        }
        return temp;
    }

    public String toString() {
        String s = new String();
        int i, j, order;
        order = getOrder();
        for (i = 0; i < order; i++) {
            for (j = 0; j < order; j++) {
                s += mat[i][j].toString();
                // s += String.valueOf(mat[i][j]);
                s += '\t';
            }
            s = s + '\n';
        }
        return (s);
    }
}
