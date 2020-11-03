package com.ants.unify.controller;

import com.ants.unify.annotation.ResponseResultBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyy
 * @Deprecated
 * @date 2020/11/3
 */
@RestController
@ResponseResultBody
@RequestMapping(value = "/login")
public class LoginController {


    @GetMapping(value = "")
    public Object  login() throws Exception {
        List<String> list=new ArrayList<>();
        list.add("ersdc");
        throw new Exception("helloError");
    }

}
