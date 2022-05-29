import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable、FutureTask、Thread 实现启动新线程获得返回值
 */
public class GetResult1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            int result = new Random().nextInt();
            String name = Thread.currentThread().getName();
            System.out.println(name+" 工作中……");
            Thread.sleep(1000);
            System.out.println(name+" 干完了，结果"+result);
            return result;
        });
        Thread thread = new Thread(task);
        String mainThreadName = Thread.currentThread().getName();
        String workThreadName = thread.getName();
        System.out.println(mainThreadName+" 启动 "+workThreadName);
        thread.start();
        int result = task.get();
        System.out.println(mainThreadName+" 获得 "+workThreadName+" 结果 "+result);
    }

}
