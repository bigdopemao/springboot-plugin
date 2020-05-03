package com.mao.logs.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mao.logs.common.RestResponse;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bigdope
 * @create 2019-06-25
 **/
public abstract class AbstractRestLogsAop {

    private static Logger logger = LoggerFactory.getLogger(AbstractRestLogsAop.class);

    private void pointCut() {}

    @Around("pointCut()")
    public Object aroundRestApi(ProceedingJoinPoint point) throws Throwable {
        this.logURL(point);
        return this.logParams(point);
    }

    /**
     * 打印请求参数
     * @param point
     * @return
     * @throws Throwable
     */
    private Object logParams(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Map<String, Object> params = this.getArgsMap(methodSignature.getParameterNames(), point.getArgs());
        logger.info("日志统一打印 ===== {}.{}() start ===== 实际接收参数:{}", new Object[]{point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), this.argsToString(params)});

        DateTime startTime = new DateTime();
        Object response = null;
        try {
            response = point.proceed();
        } catch (UndeclaredThrowableException e1) {
            logger.error("未知的异常", e1);
            if (e1.getCause() instanceof InvocationTargetException) {
                response = new RestResponse(-1, ((InvocationTargetException)e1.getCause()).getTargetException().getMessage().replaceAll("Exception.*?]", ""), null);
            } else {
                response = new RestResponse(-1, e1.getCause().getMessage().replaceAll("Exception.*?]", ""), null);
            }
        } catch (Exception e) {
            logger.error("Exception", e);
            if (e instanceof RuntimeException) {
                if (!StringUtils.isEmpty(e.getMessage()) && e.getMessage().contains("TimeOutException")) {
                    response = new RestResponse(-1, "请求超时", null);
                } else {
                    response = new RestResponse(-1, e.getMessage().replaceAll("Exception.*?]", ""), null);
                }
            } else {
                response = new RestResponse(-1, "系统繁忙,请稍后重试！", null);
            }
        }

        DateTime endTime = new DateTime();
        Interval interval = new Interval(startTime, endTime);
        logger.info("日志统一打印 ===== {}.{}() end ===== 响应时间:{}毫秒, 响应内容:{}", new Object[]{point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), interval.toDurationMillis(), this.argsToString(response)});
        return response;
    }

    /**
     * 打印URL
     * @param point
     */
    private void logURL(ProceedingJoinPoint point) {
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // 请求uri
            String requestURI = request.getRequestURI();
            logger.info("日志统一打印 ===== {}.{}() start ===== requestURI:{}", new Object[]{point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), this.argsToString(requestURI)});
            // 请求url
            StringBuffer requestURL = request.getRequestURL();
            logger.info("日志统一打印 ===== {}.{}() start ===== requestURL:{}", new Object[]{point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), this.argsToString(requestURL)});

            // 获取请求参数，除了 PathVariable
            JSONObject paramObject = new JSONObject();
            // 参数列表
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                paramObject.put(entry.getKey(), entry.getValue().length == 1 ? entry.getValue()[0] : entry.getValue());
            }

            // 获取 PathVariable 和 RequestBody 的请求参数值
            Object[] args = point.getArgs();
            MethodSignature methodSignature = (MethodSignature) point.getSignature();
            Parameter[] parameters = methodSignature.getMethod().getParameters();
            Annotation[][] parameterAnnotations = methodSignature.getMethod().getParameterAnnotations();
            for (Annotation[] parameterAnnotation : parameterAnnotations) {
                int paramIndex = ArrayUtils.indexOf(parameterAnnotations, parameterAnnotation);
                for (Annotation annotation : parameterAnnotation) {
                    if (annotation instanceof PathVariable) {
                        String name = StringUtils.isNotBlank(((PathVariable) annotation).value()) ? ((PathVariable) annotation).value() : parameters[paramIndex].getName();
                        Object paramValue = args[paramIndex];
                        paramObject.put(name, paramValue);
                    } else if (annotation instanceof RequestBody) {
                        String name = parameters[paramIndex].getName();
                        Object paramValue = args[paramIndex];
                        paramObject.put(name, paramValue);
                    }
                }
            }
            logger.info("日志统一打印 ===== {}.{}() start ===== 请求中的参数:{}", new Object[]{point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), this.argsToString(paramObject)});
        }
    }

    /**
     * 转换为String
     * @param object
     * @return
     */
    private String argsToString(Object object) {
        try {
            return JSON.toJSONString(object);
        } catch (Exception var3) {
            return String.valueOf(object);
        }
    }

    /**
     * 获取参数map
     * @param paramsNames
     * @param objects
     * @return
     */
    private Map<String, Object> getArgsMap(String[] paramsNames, Object[] objects) {
        Map<String, Object> resultMap = new HashMap<>();
        for (int i = 0; i < paramsNames.length; i++) {
            if (!(objects[i] instanceof HttpServletResponse)) {
                if (objects[i] instanceof HttpServletRequest) {
                    logger.info("paramsNames:{}, value:{}", paramsNames[i], objects[i].toString());
                    resultMap.put(paramsNames[i], objects[i].toString());
                } else {
                    resultMap.put(paramsNames[i], objects[i]);
                }
            }
        }
        return resultMap;
    }

}
