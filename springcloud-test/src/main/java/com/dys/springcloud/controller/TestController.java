package com.dys.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping(value = {"/redisson"})
    @ResponseBody
    public String redisson(@RequestParam(name = "size") Integer size) throws Exception {

        

        return "abc";
    }

}
