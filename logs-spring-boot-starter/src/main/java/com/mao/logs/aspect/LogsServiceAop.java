//package com.mao.logs;
//
//import com.alibaba.fastjson.JSON;
//import com.mao.logs.common.RestResponse;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.joda.time.DateTime;
//import org.joda.time.Interval;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.lang.reflect.UndeclaredThrowableException;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * https://blog.csdn.net/yjy91913/article/details/78528922
// * https://www.jianshu.com/p/e87df39d8c1a
// * https://blog.csdn.net/u014598014/article/details/69791515
// * https://www.jb51.net/article/83166.htm
// * https://blog.csdn.net/qq_15396517/article/details/84106997
// * https://segmentfault.com/a/1190000018452641?utm_source=tag-newest
// *
// * http://www.importnew.com/30610.html -- SpringBoot系列三：SpringBoot自定义Starter
// * https://www.cnblogs.com/zhaww/p/8674657.html -- SpringBoot配置Aop笔记【例子】
// * https://blog.csdn.net/w05980598/article/details/79053209 -- spring-boot使用AOP统一处理日志
// * https://blog.csdn.net/linxingliang/article/details/52324965 --
// * https://www.jianshu.com/p/8cdd5895360d -- springboot aop
// *
// * https://chinalhr.github.io/posts/aspect_dynamic/ -- javassist实现Aspect动态Pointcut
// *
// * https://blog.csdn.net/millery22/article/details/80037703 -- Spring AOP+反射实现自定义动态配置校验规则，让校验规则飞起来
// *
// * https://blog.csdn.net/heye644171300/article/details/81008564 -- Spring基础（21）——AOP——动态PointCut
// * http://www.360doc.com/content/13/1119/17/203871_330546874.shtml --
// *
// * https://blog.csdn.net/ryo1060732496/article/details/80891125 -- 深入浅出 java 注解-06-java 动态设置注解的属性
// *
// * @author bigdope
// * @create 2019-06-20
// **/
////@Aspect
////@Component
//public class LogsServiceAop {
//
//    private static Logger logger = LoggerFactory.getLogger(LogsServiceAop.class);
//
//    @Autowired
//    private LogsProperties logsProperties;
//
//    /**service层切面*/
////    private final String POINT_CUT = "execution(* com.mao.logs.controller..*(..))";
////    private final String POINT_CUT = "execution(* com.mao.logs.test..*(..))";
//    private final String POINT_CUT = "execution(* com.mao.logs..*(..))";
//
////    @Pointcut(POINT_CUT)
//    private void pointCut() {}
//
////    /**
////     * 后置最终通知（目标方法只要执行完了就会执行后置通知方法）
////     * 日志管理
////     * @param joinPoint
////     */
////    @Before(value = "pointCut()")
////    public void before(JoinPoint joinPoint) throws NoSuchMethodException {
////        System.out.println(logsProperties.getPointcutPackageName());
////
////        System.out.println("目标方法执行前先执行");
////        String className = joinPoint.getTarget().getClass().getName();
////        String methodName = joinPoint.getSignature().getName();
////        System.out.println("className: " + className);
////        System.out.println("methodName: " + methodName);
////
////        //获取request
////        if (RequestContextHolder.getRequestAttributes() != null ) {
////            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
////
////            //请求url
////            String url = request.getRequestURI();
////            System.out.println("url: " + url);
////            StringBuffer requestURL = request.getRequestURL();
////            System.out.println("requestURL: " + requestURL);
////
////            //参数列表
////            String param = null;
////            Map<String, String[]> parameterMap = request.getParameterMap();
////            if(parameterMap!=null&& parameterMap.size()>0 ){
////                param = JSON.toJSONString(parameterMap);
////                System.out.println("请求中的参数param: "+param);
////            }
////        }
////    }
////
////    @After(value = "pointCut()")
////    public void after(JoinPoint joinPoint) {
////        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
////        Method method = signature.getMethod();
////        // 日志工具后继处理
////        System.out.println("方法被调用之后执行，无论方法执行成功与否：" + method.getName());
////    }
////
////    @AfterReturning(returning = "object",value = "pointCut()")
////    public void deleteBigMeeting_After(JoinPoint joinPoint, Object object){
////        System.out.println("目标方法执行完并成功返回结果 正常结束后才执行");
////        System.out.println("方法的返回结果为: " + object);
////        System.out.println("目标方法内的参数为: "+ Arrays.asList(joinPoint.getArgs()));
////    }
////
////    @AfterThrowing(throwing = "e",value = "pointCut()")
////    public void afterThrowing(Throwable  e) {
////        System.out.println("目标方法抛出异常后才执行");
////        System.out.println("异常信息为: " + e);
////    }
////
//////    @Around(value = "pointCut()")
//////    public void around(JoinPoint joinPoint) {
//////        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//////        Method method = signature.getMethod();
//////        // 日志工具后继处理
//////        System.out.println("环绕通知，方法被调用前后执行：" + method.getName());
//////    }
//
//
//    // ==============================================================================
//    @Around(value = "pointCut()")
//    public Object aroundRestApi(ProceedingJoinPoint point) throws Throwable {
//        return this.log(point);
//    }
//
//    private Object log(ProceedingJoinPoint point) throws Throwable {
//        MethodSignature methodSignature = (MethodSignature)point.getSignature();
//        Map<String, Object> paras = this.getArgsMap(methodSignature.getParameterNames(), point.getArgs());
//        logger.info("日志统一打印 ===== {}.{}() start =====,参数:{}", new Object[]{point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), this.argsToString(paras)});
//        DateTime startTime = new DateTime();
//        Object response = null;
//
//        try {
//            response = point.proceed();
//        } catch (UndeclaredThrowableException var10) {
//            logger.error("未知的异常", var10);
//            if (var10.getCause() instanceof InvocationTargetException) {
//                response = new RestResponse(-1, ((InvocationTargetException)var10.getCause()).getTargetException().getMessage().replaceAll("Exception.*?]", ""), (Object)null);
//            } else {
//                response = new RestResponse(-1, var10.getCause().getMessage().replaceAll("Exception.*?]", ""), (Object)null);
//            }
//        } catch (Exception var11) {
//            logger.error("", var11);
//            if (var11 instanceof RuntimeException) {
//                if (!StringUtils.isEmpty(var11.getMessage()) && var11.getMessage().contains("TimeOutException")) {
//                    response = new RestResponse(-1, "请求超时", (Object)null);
//                } else {
//                    response = new RestResponse(-1, var11.getMessage().replaceAll("Exception.*?]", ""), (Object)null);
//                }
//            } else {
//                response = new RestResponse(-1, "系统繁忙,请稍后重试！", (Object)null);
//            }
//        }
//
////        if (isTestFlow()) {
////            try {
////                ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
////                if (attributes != null) {
////                    HttpServletResponse servletResponse = attributes.getResponse();
////                    RestResponse restResponse = (RestResponse)response;
////                    if (restResponse != null && restResponse.getResultCode() != 0) {
////                        servletResponse.setStatus(500);
////                    }
////                }
////            } catch (Exception var9) {
////                logger.error("压测流量修改httpresponse状态码异常", var9);
////            }
////        }
//
//        DateTime endTime = new DateTime();
//        Interval interval = new Interval(startTime, endTime);
//        logger.info("日志统一打印 ===== {}.{}() end =====,响应时间:{}毫秒,响应内容:{}", new Object[]{point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), interval.toDurationMillis(), this.argsToString(response)});
//        return response;
//    }
//
//    private String argsToString(Object object) {
//        try {
//            return JSON.toJSONString(object);
//        } catch (Exception var3) {
//            return String.valueOf(object);
//        }
//    }
//
//    private Map<String, Object> getArgsMap(String[] paramsNames, Object[] objects) {
//        Map<String, Object> resultMap = new HashMap();
//
//        for(int i = 0; i < paramsNames.length; ++i) {
//            if (!(objects[i] instanceof HttpServletResponse)) {
//                if (objects[i] instanceof HttpServletRequest) {
//                    logger.info("paramsNames:{}, value:{}", paramsNames[i], objects[i].toString());
//                    resultMap.put(paramsNames[i], objects[i].toString());
//                } else {
//                    resultMap.put(paramsNames[i], objects[i]);
//                }
//            }
//        }
//
//        return resultMap;
//    }
//
//}
