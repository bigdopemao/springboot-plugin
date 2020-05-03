package com.mao.logs.service;

import org.springframework.stereotype.Service;

/**
 * @author bigdope
 * @create 2019-06-19
 **/
@Service
public class TestType2Impl implements TestType {

    @Override
    public Integer getType() {
        return 2;
    }

    @Override
    public String getTypeStr() {
        return "TestType2Impl: 2";
    }

}
