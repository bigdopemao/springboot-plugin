package com.mao.logs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * @author bigdope
 * @create 2019-06-20
 **/
@ConfigurationProperties(prefix = "spring.logs")
public class LogsProperties implements Serializable {

    private static final long serialVersionUID = 5342000949506975347L;

    // 是否打开AOP
    private Boolean aopAuto = true;

    // 是否打开日志
    private Boolean autoOpen = true;

    // 切点的包名
    private String pointcutPackageName = "com";

    // 切点的表达式
    private String pointcutExpression = "execution(* com..*(..))";

    // 最终切点的表达式
    private String finalPointcutValue = "execution(* com..*(..))";

    public LogsProperties() {
    }

    public LogsProperties(String pointcutPackageName, String pointcutExpression) {
        this.pointcutPackageName = pointcutPackageName;
        this.pointcutExpression = pointcutExpression;
        initFinalPointcutValue();
    }

    private void initFinalPointcutValue() {
        if (this.pointcutExpression != null) {
            this.finalPointcutValue = this.pointcutExpression;
        } else {
            this.finalPointcutValue = "execution(* " + this.pointcutPackageName + "..*(..))";
        }
    }

    // 初始化bean
    @PostConstruct
    private void initFinalPointcutValueBean() {
        if (this.pointcutExpression != null && this.pointcutPackageName.equals("com")) {
            this.finalPointcutValue = this.pointcutExpression;
        } else {
            this.finalPointcutValue = "execution(* " + this.pointcutPackageName + "..*(..))";
        }
    }


    public Boolean getAopAuto() {
        return aopAuto;
    }

    public void setAopAuto(Boolean aopAuto) {
        this.aopAuto = aopAuto;
    }

    public Boolean getAutoOpen() {
        return autoOpen;
    }

    public void setAutoOpen(Boolean autoOpen) {
        this.autoOpen = autoOpen;
    }

    public String getPointcutPackageName() {
        return pointcutPackageName;
    }

    public void setPointcutPackageName(String pointcutPackageName) {
        this.pointcutPackageName = pointcutPackageName;
    }

    public String getPointcutExpression() {
        return pointcutExpression;
    }

    public void setPointcutExpression(String pointcutExpression) {
        this.pointcutExpression = pointcutExpression;
    }

    public String getFinalPointcutValue() {
        return finalPointcutValue;
    }

    public void setFinalPointcutValue(String finalPointcutValue) {
        this.finalPointcutValue = finalPointcutValue;
    }
}
