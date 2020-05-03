package com.mao.logs.controller;

import com.mao.logs.common.RestResponse;
import com.mao.logs.config.LogsProperties;
import com.mao.logs.model.GenderEnum;
import com.mao.logs.model.User;
import com.mao.logs.service.LogsService;
import com.mao.logs.service.TestTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author bigdope
 * @create 2019-05-22
 **/
@RestController
public class LogsTestController {

    @Autowired
    private LogsService logsService;

    @Autowired
    private LogsProperties logsProperties;

    @Autowired
    private TestTypeService testTypeService;

    // http://localhost:8080/test
    @GetMapping("/test")
    public RestResponse test() {
        RestResponse restResponse = new RestResponse(logsService.getLogsProperties());
        return restResponse;
    }

    // http://localhost:8080/test1
    // http://localhost:8081/test1?a=1&b=2
    @GetMapping("/test1")
    public RestResponse test4() {
        System.out.println("TestController.test1");
        RestResponse restResponse = new RestResponse(logsProperties);
        return restResponse;
    }

    // http://localhost:8080/test2/5
    @GetMapping("/test2/{type}")
    public RestResponse test5(@PathVariable String type) {
        System.out.println("TestController.test2");
        RestResponse restResponse = new RestResponse(type);
        return restResponse;
    }

    // http://localhost:8080/test3/6?a=abc
    @GetMapping("/test3/{type}")
    public RestResponse test6(@PathVariable String type, @RequestParam String a) {
        System.out.println("TestController.test3");
        RestResponse restResponse = new RestResponse(type + " : " + a);
        return restResponse;
    }

    // http://localhost:8080/test4/7?a=abc
    @GetMapping("/test4/{type}")
    public RestResponse test4(@RequestParam String a, @PathVariable String type) {
        System.out.println("TestController.test4");
        RestResponse restResponse = new RestResponse(type + " : " + a);
        return restResponse;
    }

    // http://localhost:8081/test5
    // {"id": 1, "name":"haha","age":10,"ids":[1,2,3]}
    @RequestMapping(value = "/test5", method = RequestMethod.POST)
    public RestResponse test5(@RequestBody User user) {
        System.out.println("TestController.test5");
        RestResponse restResponse = new RestResponse(user);
        return restResponse;
    }

    // http://localhost:8081/test6?id=1&name=haha,hehe&age=10&ids=1,2,3&gg=00
    @RequestMapping(value = "/test6", method = RequestMethod.GET)
    public RestResponse test6(User user) {
        System.out.println("TestController.test6");
        RestResponse restResponse = new RestResponse(user);
        return restResponse;
    }

    // http://localhost:8081/test7/aa
    // {"id": 1, "name":"haha","age":10}
    @RequestMapping(value = "/test7/{type}", method = RequestMethod.POST)
    public RestResponse test7(@PathVariable String type, @RequestBody User user) {
        System.out.println("TestController.test7");
        RestResponse restResponse = new RestResponse(user);
        return restResponse;
    }

    // http://localhost:8080/testType/1
    @GetMapping("/testType/{type}")
    public String testType(@PathVariable("type") Integer type) {
        System.out.println("TestController.testType: " + type);
        return testTypeService.getTestType(type);
    }

    // http://localhost:8081/test8
    @GetMapping("/test8")
    public RestResponse test8() {
        User user = new User();
        user.setId(1);
        user.setName("test");
        user.setGenderEnum(GenderEnum.MAN);
        RestResponse restResponse = new RestResponse(user);
        return restResponse;
    }

    // http://localhost:8081/test9/MAN
    @GetMapping("/test9/{genderEnum}")
    public RestResponse test9(@PathVariable("genderEnum") GenderEnum genderEnum) {
        User user = new User();
        user.setId(1);
        user.setName("test");
        user.setGenderEnum(genderEnum);
        RestResponse restResponse = new RestResponse(user);
        return restResponse;
    }

    // http://localhost:8081/test10?genderEnum=WOMAN
    @GetMapping("/test10")
    public RestResponse test10(GenderEnum genderEnum) {
        User user = new User();
        user.setId(1);
        user.setName("test");
        user.setGenderEnum(genderEnum);
        RestResponse restResponse = new RestResponse(user);
        return restResponse;
    }
}
