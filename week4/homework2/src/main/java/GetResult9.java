import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * thread、Runnable、同步队列实现
 */
public class GetResult9 implements Runnable{
    private BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);

    public Integer getResult() throws InterruptedException {
        return queue.take();
    }

    private void setResult(){
        String name = Thread.currentThread().getName();
        try {
            System.out.println(name+" 工作中……");
            Thread.sleep(1000);
            int result= new Random().nextInt();
            queue.put(result);
            System.out.println(name+" 干完了，结果"+result);
        } catch (InterruptedException e) {
            System.out.println(name+" 被中断，无结果");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.setResult();
    }

    public static void main(String[] args) throws InterruptedException {
        GetResult9 get = new GetResult9();
        Thread thread = new Thread(get);
        String mainThreadName = Thread.currentThread().getName();
        String workThreadName = thread.getName();
        System.out.println(mainThreadName+" 启动 "+workThreadName);
        thread.start();

        int result = get.getResult();
        System.out.println(mainThreadName+" 获得 "+workThreadName+" 结果 "+result);
    }
}
