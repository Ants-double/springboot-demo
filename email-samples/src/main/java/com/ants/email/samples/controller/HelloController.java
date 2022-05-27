package com.ants.email.samples.controller;

import com.ants.email.samples.pojo.MailInfo;
import com.ants.email.samples.service.api.MailServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-27
 **/
@RestController
@RequestMapping("hello")
public class HelloController {

	@Autowired
	MailServer mailServer;

	@GetMapping("send")
	public String sendMail() {
		MailInfo mailInfo = MailInfo.builder()
				.from("liyangyang0808@126.com")
				.subject("测试")
				.content("测试内容")
				.receives(Arrays.asList("ants_double@qq.com", "liyangyang@y.com"))
				.Ccs(Arrays.asList("test@qq.com"))
				.build();
		int i = mailServer.sendMail(mailInfo);
		return i > 0 ? "成功" : "失败";
	}

	@GetMapping("send_image")
	public String sendMailWithImage() {
		Map<String,String> imageMap=new HashMap<>();
		imageMap.put("image1","E:/demo.png");
		imageMap.put("image2","E:/demo.png");
		MailInfo mailInfo = MailInfo.builder()
				.from("liyangyang0808@126.com")
				.subject("测试")
				.content("测试内容")
				.receives(Arrays.asList("ants_double@qq.com", "liyangyang@eh.com"))
				.Ccs(Arrays.asList("test@qq.com4@qq.com"))
				.attachment(Arrays.asList("E:/bb.txt","E:/cc.txt"))
				.images(imageMap)
				.build();
		int i = mailServer.sendMailWithInlineImage(mailInfo);
		return i > 0 ? "成功" : "失败";
	}
	@GetMapping("send_file")
	public String sendMailWithAttachment() {

		MailInfo mailInfo = MailInfo.builder()
				.from("liyangyang0808@126.com")
				.subject("测试")
				.content("测试内容")
				.receives(Arrays.asList("ants_double@qq.com", "liyangyang@e.com"))
				.Ccs(Arrays.asList("test@qq.com"))
				.attachment(Arrays.asList("E:/demo.png","E:/demo.png"))
				.build();
		int i = mailServer.sendMailWithAttachment(mailInfo);
		return i > 0 ? "成功" : "失败";
	}
}
