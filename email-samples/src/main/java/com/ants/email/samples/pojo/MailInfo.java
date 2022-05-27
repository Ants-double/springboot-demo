package com.ants.email.samples.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-27
 **/
@Data
@Builder
@AllArgsConstructor
public class MailInfo implements Serializable {
	private static final long serialVersionUID = 2079318548981891642L;
	private String from;
	private String subject;
	private String content;
	// 接收人

	private List<String> receives;

	// 抄送人

	 private List<String> Ccs;

	// 附件

	private List<String> attachment;

	// 图片（在内容中显示的图片附件)

	private Map<String,String> images;

}
