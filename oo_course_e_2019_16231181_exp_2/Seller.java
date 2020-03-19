public class Seller extends Market{
    private int level;
    @Override
    public double getBonus(int dan) {
        if (dan > 300000) {
            return (dan-300000) * 0.2;
        }
        else {
            return super.getSalary() * (-0.2);
        }
    }

    public int getLevel(int dan) {
        if (dan > 300000) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
