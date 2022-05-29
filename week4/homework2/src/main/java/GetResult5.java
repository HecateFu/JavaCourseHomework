import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * CompletableFuture.supplyAsync
 */
public class GetResult5 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String mainThreadName = Thread.currentThread().getName();
        System.out.println(mainThreadName+" 启动");
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            String name = Thread.currentThread().getName();
            try {
                int result = new Random().nextInt();
                System.out.println(name+" 工作中……");
                Thread.sleep(1000);
                System.out.println(name+" 干完了，结果"+result);
                return result;
            } catch (InterruptedException e) {
                System.out.println(name+" 被中断，无结果");
                e.printStackTrace();
                return null;
            }
        });
        int result = future.get();
        System.out.println(mainThreadName+" 获得结果 "+result);
    }

}
