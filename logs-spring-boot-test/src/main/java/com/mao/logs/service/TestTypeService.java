package com.mao.logs.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用其构造器注入实现类，然后根据不同type调用不同的实现类
 * @author bigdope
 * @create 2019-06-19
 **/
@Service
public class TestTypeService {

    private Map<Integer, TestType> testTypeMap = new HashMap<>();

    public TestTypeService(List<TestType> testTypes) {
        for (TestType testType : testTypes) {
            testTypeMap.put(testType.getType(), testType);
        }
    }

    public String getTestType(Integer type) {
        String typeStr = testTypeMap.get(type).getTypeStr();
        System.out.println(typeStr);
        return typeStr;
    }

//    @Autowired
//    @Qualifier("testType1Impl")
//    private TestType testType;
//
////    @Resource(name = "testType2Impl")
////    private TestType testType;
//
//    public String getTestType(Integer type) {
//        String typeStr = testType.getTypeStr();
//        System.out.println(typeStr);
//        return typeStr;
//    }

}
