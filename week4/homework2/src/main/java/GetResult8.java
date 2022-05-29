import java.util.Objects;
import java.util.Random;

/**
 * 使用Synchronized、wait、notify
 */
public class GetResult8 implements Runnable{
    private volatile Integer result;

    public synchronized Integer getResult() throws InterruptedException {
        if (Objects.isNull(result)) {
            this.wait();
        }
        return result;
    }

    private synchronized void setResult(){
        String name = Thread.currentThread().getName();
        try {
            System.out.println(name+" 工作中……");
            Thread.sleep(1000);
            this.result= new Random().nextInt();
            System.out.println(name+" 干完了，结果"+result);
        } catch (InterruptedException e) {
            System.out.println(name+" 被终端，无结果");
            e.printStackTrace();
        } finally {
            // 执行完成，唤醒主线程
            this.notifyAll();
        }
    }

    @Override
    public void run() {
        this.setResult();
    }

    public static void main(String[] args) throws InterruptedException {
        GetResult8 get = new GetResult8();
        Thread thread = new Thread(get);
        String mainThreadName = Thread.currentThread().getName();
        String workThreadName = thread.getName();
        System.out.println(mainThreadName+" 启动 "+workThreadName);
        thread.start();

        int result = get.getResult();
        System.out.println(mainThreadName+" 获得 "+workThreadName+" 结果 "+result);
    }
}
