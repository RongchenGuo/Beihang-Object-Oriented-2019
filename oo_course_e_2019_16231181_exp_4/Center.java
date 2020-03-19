package demo2;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
/**
 服务中心
 */
public class Center extends Thread {
    private BlockingQueue<Waiter> waiters;
    private BlockingQueue<Customer> customers;
    static final int produceSpeed = 300;
    static final int consumeSpeed = 10000;
    private static final int centerCapacity = 100;

    private Random produceInterval = new Random();
    private Random consumeInterval = new Random();

    //创建提供服务的柜台队列和取得号的客户队列
    public Center(int num) {
        waiters = new ArrayBlockingQueue<>(num);
        customers = new ArrayBlockingQueue<>(centerCapacity);
    }

    //取号机产生新号码
    // public boolean produce() {
    public void produce() {
        try{
            Customer cos = new Customer();
            if (customers.size() == centerCapacity) {
                System.out.println("大厅容量已满暂停发号");
                //return false;
                // customers.put(null);
                // Thread.interrupted();
            }
            customers.put(cos);
            System.out.println(String.format("%s号顾客正在等待服务", cos.toString()));
            TimeUnit.MILLISECONDS.sleep(produceInterval.nextInt(produceSpeed));
            // return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return true;
    }

    //客户获得服务，请注意线程安全的实现
    public void consume(Waiter w) {
    // public boolean consume(Waiter w) {
        try {
            Customer cos = customers.take();
            waiters.put(w);
            System.out.println(String.format("%s号顾客请到%s号窗口", cos.toString(), w.toString()));
            // double time2 = consumerRand.nextDouble(consumeSpeed);
            TimeUnit.MILLISECONDS.sleep(consumeInterval.nextInt(consumeSpeed));
            waiters.remove(w);
            // return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return true;
    }

}
