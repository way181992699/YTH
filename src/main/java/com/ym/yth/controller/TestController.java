package com.ym.yth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wkx
 * @description 测试
 * @date 2020/6/2
 */
@RestController
@RequestMapping("/Test")
public class TestController {

    @RequestMapping(value = "/aaa", method = RequestMethod.GET)
    public String fun() {
        return "success";
    }

    @RequestMapping(value = "/bbb", method = RequestMethod.GET)
    public String fun1(Integer a ,Integer b ) {
        return a+b +"";
    }

}
