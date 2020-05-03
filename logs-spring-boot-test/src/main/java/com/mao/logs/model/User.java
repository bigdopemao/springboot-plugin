package com.mao.logs.model;

import java.io.Serializable;

/**
 * @author bigdope
 * @create 2019-07-25
 **/
public class User implements Serializable {

    private static final long serialVersionUID = -6247374645232600315L;

    private Integer id;

    private String name;

    private Integer age;

    private Integer[] ids;

    private GenderEnum genderEnum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    public GenderEnum getGenderEnum() {
        return genderEnum;
    }

    public void setGenderEnum(GenderEnum genderEnum) {
        this.genderEnum = genderEnum;
    }
}
