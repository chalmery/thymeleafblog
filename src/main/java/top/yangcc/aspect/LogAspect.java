package top.yangcc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * AOP 处理日志
 * @author yangcc
 */
@Aspect
@Component
public class LogAspect {
    /**
     *   拿到日志记录器
      */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 切入点,拦截controller下的所有方法
     */
    @Pointcut("execution(* top.yangcc.controller.*.*(..))")
    public void log(){

    }

    /**
     * 切入点之前执行
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        // 从请求中拿到参数
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        // 从JoinPoint拿到方法名,参数
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        // 打印
        RequestLog requestLog = new RequestLog(url, ip, method, args);
        logger.info("Request : {}",requestLog);
    }

    /**
     * 切入点之前执行
     */
    @After("log()")
    public void doAfter(){
        logger.info("--------after-----------");
    }

    /**
     * 切入点之后执行，返回数据
     */
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result : {}",result);
    }

    /**
     * 内部类，日志内容实体
     */
    private class RequestLog{
        private String url; //请求路径
        private String ip;  // ip
        private String classMethod; // 请求的方法
        private Object[] agrs; // 参数

        public RequestLog(String url, String ip, String classMethod, Object[] agrs) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.agrs = agrs;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", agrs=" + Arrays.toString(agrs) +
                    '}';
        }
    }
}
