import com.oocourse.specs3.AppRunner;

public class HomeworkEleven {
    public static void main(String[] args) throws Exception {
        AppRunner runner = AppRunner.newInstance(MyPath.class,
                MyRailwaySystem.class);
        runner.run(args);
    }
}
