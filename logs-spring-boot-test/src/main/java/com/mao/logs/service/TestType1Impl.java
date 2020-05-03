package com.mao.logs.service;

import org.springframework.stereotype.Service;

/**
 * @author bigdope
 * @create 2019-06-19
 **/
@Service
public class TestType1Impl implements TestType {

    @Override
    public Integer getType() {
        return 1;
    }

    @Override
    public String getTypeStr() {
        return "TestType1Impl: 1";
    }

}
