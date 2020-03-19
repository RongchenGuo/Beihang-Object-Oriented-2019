package demo2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟银行叫号系统,测试程序的入口
 */
public class CalNum {

    static final int counterNum = 10;

    public static void main(String[] args) throws InterruptedException {
        //创建服务中心
        Center center = new Center(counterNum);
        //创建线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        //模拟产生服务人员
        Producer producer = new Producer(center);
        //模拟产生客户
        Consumer consumer = new Consumer(center);
        exec.execute(producer);
        //模拟10个柜台提供服务
        for (int i = 0; i < counterNum; i++) {
            exec.execute(consumer);
        }
        exec.shutdown();
    }
}
