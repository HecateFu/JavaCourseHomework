import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.LockSupport;

/**
 * 使用 Runnable、Thread、LockSupport 实现，通过引用传参获得结果
 */
public class GetResult3 implements Runnable{
    private final Thread main;
    private final Result result;

    public GetResult3(Thread main,Result result){
        this.main = main;
        this.result = result;
    }

    private static class Result{
        private volatile int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Result resultObj = new Result();
        GetResult3 work =new GetResult3(Thread.currentThread(),resultObj);
        Thread thread = new Thread(work);
        String mainThreadName = Thread.currentThread().getName();
        String workThreadName = thread.getName();
        System.out.println(mainThreadName+" 启动 "+workThreadName);
        thread.start();
        // 暂停主线程，等待结果
        LockSupport.park();
        // 被唤醒后获得结果
        int result = resultObj.getValue();
        System.out.println(mainThreadName+" 获得 "+workThreadName+" 结果 "+result);
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        try {
            System.out.println(name+" 工作中……");
            Thread.sleep(1000);
            this.result.setValue(new Random().nextInt());
            System.out.println(name+" 干完了，结果"+result.getValue());
        } catch (InterruptedException e) {
            System.out.println(name+" 被终端，无结果");
            e.printStackTrace();
        } finally {
            // 执行完成，唤醒主线程
            LockSupport.unpark(main);
        }
    }
}
