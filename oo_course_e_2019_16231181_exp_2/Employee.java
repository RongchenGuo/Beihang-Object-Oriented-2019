public class Employee {
    private int num;
    private double salary;
    private int time;
    private int func;
    private double years;
    private double bonus;
    private int level;

    public Employee() {
        this.func = 0;
        this.num = 0;
        this.salary = 50000;
        this.time = 0;
        this.years = 0;
        this.bonus = 0;
        this.level = 0;
    }

    public void setYears(double years) {
        this.years = years;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getYears() {
        return this.years;
    }

    public int getFunc() {
        return this.func;
    }

    public int getNum() {
        return this.num;
    }

    public double getSalary() {
        return this.salary;
    }

    public double getTime() {
        return this.time;
    }

    public double getBonus(int time) {
        return 0;
    }

    public double wholeSal(int time) {
        return getSalary() + getBonus(time);
    }
}
