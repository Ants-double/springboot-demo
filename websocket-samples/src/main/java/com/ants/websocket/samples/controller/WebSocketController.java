package com.ants.websocket.samples.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-26
 **/
@RestController
@Slf4j
public class WebSocketController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	/** 广播发送消息，将消息发送到指定的目标地址 */
	@MessageMapping("/test")
	public void sendTopicMessage(String messageBody) {
		// 将消息发送到 WebSocket 配置类中配置的代理中（/topic）进行消息转发
		simpMessageSendingOperations.convertAndSend("", messageBody);
	}
	/** 消息发送工具对象 */
	@Autowired
	private SimpMessageSendingOperations simpMessageSendingOperations;
	/**
	 * 广播
	 * @param message
	 * @return
	 */
	@MessageMapping("/welcome")
	@SendTo("/topic/greetings")
	public String say(String message) {
		log.info(message);
		Map res = new HashMap();
		res.put("topic", message);
		return message;
	}
	/**
	 * 点对点通信
	 * @param message
	 */
    @MessageMapping(value = "/points")
    public void point1(String message) {
        log.info(message + "******" );
        //发送消息给指定用户, 最后接受消息的路径会变成 /user/admin/queue/points
        messagingTemplate.convertAndSendToUser("admin", "/queue/points", message);
    }

	/**
	 * 请求接口推送消息-（广播）
	 * @param message
	 */
	@GetMapping("/sentMes")
	public void sentMes(String message) {
		String pushMessage= "content";
		messagingTemplate.convertAndSendToUser( "name", "/queue/points", pushMessage);
		messagingTemplate.convertAndSend("/queue/msg",pushMessage);
	}


	/**
	 * 定时任务推送消息-（广播）
	 * @throws Exception
	 */
	// @Scheduled(fixedDelay=5000)
	public void priceAutoConvert() throws Exception {
		messagingTemplate.convertAndSend("/topic/getResponse", "dddd");
	}



}
