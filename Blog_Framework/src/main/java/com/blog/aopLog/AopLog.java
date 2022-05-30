package com.blog.aopLog;

import com.alibaba.fastjson.JSON;
import com.blog.annotation.SystemLog;
import com.blog.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class AopLog {
    @Pointcut("@annotation(com.blog.annotation.SystemLog)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object printLog(ProceedingJoinPoint pjp) throws Throwable {
        //目标方法调用
        Object proceed;
        try{
            handleBefore(pjp);
             proceed = pjp.proceed();
            handleAfter(proceed);
        }finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator());
        }
        return  proceed;
    }

    private void handleAfter(Object result) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSON(result));
    }

    private void handleBefore(ProceedingJoinPoint pjp) {
        ServletRequestAttributes servletRequestAttributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}", request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}",getSystemLog(pjp).BusinessName() );
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", pjp.getSignature().getDeclaringTypeName() ,((MethodSignature)pjp.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求的 IP
        log.info("UserID         : {}", SecurityUtils.getUserId());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(pjp.getArgs()));
    }
   //获取增强方法上的指定注解
    private SystemLog getSystemLog(ProceedingJoinPoint pjp){
        MethodSignature methodSignature = (MethodSignature)  pjp.getSignature();
        return methodSignature.getMethod().getAnnotation(SystemLog.class);
    }

}
