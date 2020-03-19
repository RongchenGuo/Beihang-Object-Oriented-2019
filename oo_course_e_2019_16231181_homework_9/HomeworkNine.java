import com.oocourse.specs1.AppRunner;

public class HomeworkNine {
    public static void main(String[] args) throws Exception {
        AppRunner runner =
                AppRunner.newInstance(MyPath.class, MyPathContainer.class);
        runner.run(args);
    }
}
