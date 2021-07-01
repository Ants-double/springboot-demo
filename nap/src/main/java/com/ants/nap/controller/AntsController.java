package com.ants.nap.controller;

import com.ants.nap.annotation.ResponseResultBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lyy
 * @Deprecated
 * @date 2021/7/1
 */
@RestController
@RequestMapping(value = "ants")
@ResponseResultBody
public class AntsController {
    private final Logger log = LoggerFactory.getLogger(AntsController.class);

    @GetMapping(value = "test")
    public Object test() {
        return "test";
    }

}
