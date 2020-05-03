package com.mao.logs.processor;

import com.mao.logs.config.LogsProperties;
import com.mao.logs.util.PropertiesUtils;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author bigdope
 * @create 2019-06-20
 **/
@Component
@ConditionalOnResource(resources = "application.properties")
//@ConditionalOnResource(resources = {"application.properties", "config/application.properties"})
//@ConditionalOnBean(name = "logs")
//@ConditionalOnProperty(name = "logs.basePackage")
public class LogsProcessor implements BeanDefinitionRegistryPostProcessor {

    private static Logger logger = LoggerFactory.getLogger(LogsProcessor.class);

    private LogsProperties logsProperties;

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        try {
            // 获取properties, 并转化为logsProperties
            Properties properties = PropertiesUtils.loadProperties("config/application.properties");
            if (properties == null) {
                this.logger.warn("config/application.properties not exist! use file:application.properties");
                properties = PropertiesUtils.loadProperties("application.properties");
            }
            logsProperties = this.getLogsProperties(properties);
            // 进行相关判断过滤
            if (!logsProperties.getAopAuto() || !logsProperties.getAutoOpen()) {
                logger.warn(">>>>>>>>>> logs开启失败! spring.aop.auto = {}, spring.logs.autoOpen = {}", logsProperties.getAopAuto(), logsProperties.getAutoOpen());
                return;
            }
            // 获取最终切点的表达式
            String finalPointcutValue = logsProperties.getFinalPointcutValue();

            ClassPool pool = new ClassPool(true);
            ClassClassPath classPath = new ClassClassPath(this.getClass());
            pool.insertClassPath(classPath);
            //获取要修改的Class
//            CtClass ct = pool.get("com.mao.logs.LogsServiceAop");
            CtClass ct = pool.get("com.mao.logs.aspect.RestLogsAop");

            // 设置Class文件头注释
            ClassFile classFile = ct.getClassFile();
            AnnotationsAttribute attribute = new AnnotationsAttribute(classFile.getConstPool(), AnnotationsAttribute.visibleTag);
            Annotation componentAnnotation = new Annotation("org.springframework.stereotype.Component", classFile.getConstPool());
            Annotation aspectAnnotation = new Annotation("org.aspectj.lang.annotation.Aspect", classFile.getConstPool());
            attribute.addAnnotation(componentAnnotation);
            attribute.addAnnotation(aspectAnnotation);
            classFile.addAttribute(attribute);

            // 设置切点注释
            CtMethod cm = ct.getDeclaredMethod("pointCut");
            MethodInfo methodInfo = cm.getMethodInfo();
            Annotation annotation = new Annotation("org.aspectj.lang.annotation.Pointcut", ct.getClassFile().getConstPool());
            annotation.addMemberValue("value", new StringMemberValue(finalPointcutValue, ct.getClassFile().getConstPool()));
            AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(ct.getClassFile().getConstPool(), "RuntimeVisibleAnnotations");
            annotationsAttribute.addAnnotation(annotation);
            methodInfo.addAttribute(annotationsAttribute);
            ct.writeFile();
            ct.defrost();
            // 不推荐使用以下方法: ct.toClass() 会自动获取类加载器
//            BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ct.toClass(this.getClass().getClassLoader()));
            BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ct.toClass());
//            beanDefinitionRegistry.registerBeanDefinition("logsServiceAop", definitionBuilder.getBeanDefinition());
            beanDefinitionRegistry.registerBeanDefinition("restLogsAop", definitionBuilder.getBeanDefinition());
            logger.info(">>>>>>>>>> logs开启成功! finalPointcutValue = {}", logsProperties.getFinalPointcutValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转化为logsProperties
     * @param properties
     * @return
     */
    private LogsProperties getLogsProperties(Properties properties) {
        String aopAuto = properties.getProperty("spring.aop.auto");
        String autoOpen = properties.getProperty("spring.logs.autoOpen");
        String pointcutExpression = properties.getProperty("spring.logs.pointcutExpression");
        String pointcutPackageName = properties.getProperty("spring.logs.pointcutPackageName");
        LogsProperties logsProperties = new LogsProperties(pointcutPackageName, pointcutExpression);
        if (aopAuto != null) {
            logsProperties.setAopAuto(Boolean.valueOf(aopAuto));
        }
        if (autoOpen != null) {
            logsProperties.setAutoOpen(Boolean.valueOf(autoOpen));
        }
        return logsProperties;
    }


    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

}
