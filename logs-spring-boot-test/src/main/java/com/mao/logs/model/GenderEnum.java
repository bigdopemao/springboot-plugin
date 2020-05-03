package com.mao.logs.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * https://www.cnblogs.com/yumiaoxia/p/10414212.html
 * @author bigdope
 * @create 2019-11-05
 **/
public enum GenderEnum {

    MAN(1, "男人"),
    WOMAN(2, "女人");

    private Integer value;

    private String desc;

    GenderEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

//    @JsonValue
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }}
