package com.ants.kafka.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author ants_
 */
@Component
@Slf4j
public class KafkaProducer {

    /**
     * kafka使用模板
     */
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    /**
     * 自定义topic
     */
    public static final String TOPIC_TEST1 = "topic_test1";

    public static final String TOPIC_TEST2 = "topic_test2";

    /**
     * 自定义组
     */
    public static final String TOPIC_GROUP1 = "topic_group1";

    public static final String TOPIC_GROUP2 = "topic_group2";

    /**
     * 生产消息_发送
     */
    public void send(Object obj) {
        String obj2String = JSONObject.toJSONString(obj);

        log.info("准备发送消息为：{}",obj2String);


        // 发送消息
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC_TEST1,obj);

        // 监听消息加入队列结果返回
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            /**
             * 发送失败处理
             * @param throwable
             */
            @Override
            public void onFailure(Throwable throwable) {
                log.info(TOPIC_TEST1 + " - 生产者 发送消息失败：" + throwable.getMessage());
            }

            /**
             * 发送成功处理
             * @param stringObjectSendResult
             */
            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                // 发送成功处理
                log.info(TOPIC_TEST1 + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());
            }
        });
    }
}
