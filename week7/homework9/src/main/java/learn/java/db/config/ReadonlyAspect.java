package learn.java.db.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 给添加Readonly注解的方法设置从库数据源
 */
@Aspect
@Component
public class ReadonlyAspect {
    @Pointcut("@annotation(learn.java.db.annotation.Readonly)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object setDatabaseFlag(ProceedingJoinPoint joinPoint) throws Throwable {
        DatasourceContextHolder.set(DatasourceContextHolder.SLAVE);
        Object result = joinPoint.proceed();
        DatasourceContextHolder.clear();
        return result;
    }
}
