import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by suxiaohan on 2018/9/18.
 */

/*
*
*
* TimeUnit.DAYS          //天
TimeUnit.HOURS         //小时
TimeUnit.MINUTES       //分钟
TimeUnit.SECONDS       //秒
TimeUnit.MILLISECONDS  //毫秒
TimeUnit.NANOSECONDS   //毫微秒
TimeUnit.MICROSECONDS  //微秒
* */

public class CustThreadPool {

    public static void main(String[] argv) {
        // TODO Auto-generated method stub
//        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
//        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
//        BlockingQueue<Runnable> queue = new SynchronousQueue<>();//这种队列比较特殊，因为不排队就直接创建新线程把任务提交了。

        int temp = 0;
        if (temp == 0) {
            //有且仅有一个工作线程执行任务
//            所有任务按照指定顺序执行，即遵循队列的入队出队规则
            BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
            ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS, queue);

            for (int i = 0; i < 7; i++) {
                Runnable task = new CustomTask(i);
                pool.execute(task);
            }

            pool.shutdown();
        } else if (temp == 1) {
            //            定长线程池FixedThreadPool 5
            BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
            ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, queue);
            for (int i = 0; i < 7; i++) {
                Runnable task = new CustomTask(i);
                pool.execute(task);
            }

            pool.shutdown();
        } else {
//            可缓存线程池CachedThreadPool()
            BlockingQueue<Runnable> queue = new SynchronousQueue<>();//这种队列比较特殊，因为不排队就直接创建新线程把任务提交了。
            ThreadPoolExecutor pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, queue);
            for (int i = 0; i < 10; i++) {
                Runnable task = new CustomTask(i);
                pool.execute(task);
            }

            pool.shutdown();
        }


    }


}

class CustomTask implements Runnable {
    private int id;

    public CustomTask(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("#" + id + "   threadId=" + Thread.currentThread().getName());
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/*

可缓存线程池CachedThreadPool()
*public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
    }

*
* */