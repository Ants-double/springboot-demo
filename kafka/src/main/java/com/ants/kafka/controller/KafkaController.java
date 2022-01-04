package com.ants.kafka.controller;

import com.ants.kafka.service.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ants_
 */
@RestController
@RequestMapping(value = "message")
public class KafkaController {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @Autowired
    private KafkaProducer kafkaProducer;

    @RequestMapping(value = "send",method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public String send(String msg){
        //使用kafka模板发送信息
        kafkaProducer.send(msg);
        return "success";
    }
}
