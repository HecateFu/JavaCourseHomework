import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

/**
 * 使用 Runnable、Thread、LockSupport 实现，通过Runnable对象属性值获得结果
 */
public class GetResult2 implements Runnable{
    private final Thread main;
    private volatile int result;

    public GetResult2(Thread main){
        this.main = main;
    }

    public int getResult() {
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        GetResult2 get2 =new GetResult2(Thread.currentThread());
        Thread thread = new Thread(get2);
        String mainThreadName = Thread.currentThread().getName();
        String workThreadName = thread.getName();
        System.out.println(mainThreadName+" 启动 "+workThreadName);
        thread.start();
        // 暂停主线程，等待结果
        LockSupport.park();
        // 被唤醒后获得结果
        int result = get2.getResult();
        System.out.println(mainThreadName+" 获得 "+workThreadName+" 结果 "+result);
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        try {
            System.out.println(name+" 工作中……");
            Thread.sleep(1000);
            this.result = new Random().nextInt();
            System.out.println(name+" 干完了，结果"+result);
        } catch (InterruptedException e) {
            System.out.println(name+" 被中断，无结果");
            e.printStackTrace();
        } finally {
            // 执行完成，唤醒主线程
            LockSupport.unpark(main);
        }
    }
}
