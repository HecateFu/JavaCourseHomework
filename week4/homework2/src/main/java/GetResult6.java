import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * CompletableFuture.runAsync
 */
public class GetResult6 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String mainThreadName = Thread.currentThread().getName();
        System.out.println(mainThreadName+" 启动");
        AtomicInteger result = new AtomicInteger();
        CompletableFuture.runAsync(() -> {
            String name = Thread.currentThread().getName();
            try {
                result.set(new Random().nextInt());
                System.out.println(name+" 工作中……");
                Thread.sleep(1000);
                System.out.println(name+" 干完了，结果"+result);
            } catch (InterruptedException e) {
                System.out.println(name+" 被终端，无结果");
                e.printStackTrace();
            }
        }).get();
        System.out.println(mainThreadName+" 获得结果 "+result.get());
    }

}
