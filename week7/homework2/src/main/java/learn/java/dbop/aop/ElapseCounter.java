package learn.java.dbop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 方法执行的计时工具
 */
@Slf4j
@Aspect
@Component
public class ElapseCounter {
    @Pointcut("@annotation(learn.java.dbop.annotation.Elapse)")
    public void countElapse(){}

    @Around("countElapse()")
    public Object count(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String name = joinPoint.getSignature().getName();
        log.info("{}-开始：{}",name,start);
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        log.info("{}-完成：{},耗时：{}",name,end,(end-start));
        return result;
    }
}
