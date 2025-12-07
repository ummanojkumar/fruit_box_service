package com.mono.fruit_box_service.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Aspect
@Component
public class LoggingAspect {

    @Around(
            "@within(org.springframework.web.bind.annotation.RestController) && " +
                    "(@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
                    " @annotation(org.springframework.web.bind.annotation.PostMapping) || " +
                    " @annotation(org.springframework.web.bind.annotation.PutMapping) || " +
                    " @annotation(org.springframework.web.bind.annotation.DeleteMapping) || " +
                    " @annotation(org.springframework.web.bind.annotation.PatchMapping))"
    )
    public Object logApiDetails(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        // Fetch request & response objects
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

        // Get controller method
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // Actual execution
        Object result = joinPoint.proceed();

        long timeTaken = System.currentTimeMillis() - start;

        // Prepare log values
        String httpMethod = request.getMethod();
        String endpoint = request.getRequestURI();
        String methodName = method.getName();
        int status = response.getStatus();

        // SINGLE line log
        System.out.println(
                String.format(
                        "[API] method=%s | endpoint=%s | controllerMethod=%s | status=%d | time=%dms",
                        httpMethod, endpoint, methodName, status, timeTaken
                )
        );

        return result;
    }
}

