public class Factor {
    private String str;
    private int pos;

    public Factor() {
        this.str = "1";
    }

    public Factor(String str) {
        this.str = str;
    }

    public int checkForm() {
        return 1;
    }

    public String deri() {
        return this.str;
    }

    public String getStr() {
        return this.str;
    }

    public int getPos() {
        return this.pos;
    }
}
