import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.LockSupport;

/**
 * 使用 Runnable、Thread、主线程自旋 实现，通过Runnable对象属性值获得结果
 */
public class GetResult4 implements Runnable{
    private volatile Integer result;

    public Integer getResult() {
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        GetResult4 get =new GetResult4();
        Thread thread = new Thread(get);
        String mainThreadName = Thread.currentThread().getName();
        String workThreadName = thread.getName();
        System.out.println(mainThreadName+" 启动 "+workThreadName);
        thread.start();
        // 主线程自旋，等待结果
        Integer result;
        do {
            result = get.getResult();
        } while (result==null);

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
            System.out.println(name+" 被终端，无结果");
            e.printStackTrace();
        }
    }
}
