package service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdviceLogger {

   private Logger logger = LoggerFactory.getLogger((this.getClass()));

   @Pointcut("execution(* controller.CRUDUserProducts.*(..))")
   private void getUserWithProductsLogging(){}

    @Around(value="getUserWithProductsLogging()")
    public Object aroundLogger(ProceedingJoinPoint joinPoint) throws Throwable {
          this.logger.info("Before controller get user");
          Object result = joinPoint.proceed();
          this.logger.info("After controller get user");
          return result;
    }

    //@Before(value="getUserWithProductsLogging()")

}
