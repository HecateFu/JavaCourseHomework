import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * CountDownLatch 实现
 */
public class GetResult10 {
    public static void main(String[] args) throws InterruptedException {
        String mainThreadName = Thread.currentThread().getName();
        System.out.println(mainThreadName+" 启动");

        AtomicReference<Integer> result = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        CompletableFuture.runAsync(() -> {
            String name = Thread.currentThread().getName();
            try {
                System.out.println(name+" 工作中……");
                Thread.sleep(1000);
                result.set(new Random().nextInt());
                System.out.println(name+" 干完了，结果"+result.get());
            } catch (InterruptedException e) {
                System.out.println(name+" 被中断，无结果");
                e.printStackTrace();
            } finally {
                // 执行完成，计数器减1
                latch.countDown();
            }
        });
        latch.await();
        System.out.println(mainThreadName+" 获得结果 "+result.get());
    }
}
