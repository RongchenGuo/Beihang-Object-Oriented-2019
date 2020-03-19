public class Developer extends Employee {
    private int level;

    @Override
    public double getSalary() {
        return (super.getSalary() + 1000*super.getYears());
    }

    @Override
    public double getBonus(int time) {
        if(time >= 40) {
            return (super.getBonus(time) + super.getSalary() * 0.3);
        }
        else {
            return (super.getBonus(time) + super.getSalary() * 0.1);
        }
    }

    public int getLevel(int time) {
        if(time >= 40) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
