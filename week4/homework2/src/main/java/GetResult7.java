import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池、Callable、Future实现
 */
public class GetResult7 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String mainThreadName = Thread.currentThread().getName();
        System.out.println(mainThreadName+" 启动");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(() -> {
            int result = new Random().nextInt();
            String name = Thread.currentThread().getName();
            System.out.println(name+" 工作中……");
            Thread.sleep(1000);
            System.out.println(name+" 干完了，结果"+result);
            return result;
        });
        System.out.println(mainThreadName+" 获得结果 "+future.get());
        executor.shutdown();
    }
}
