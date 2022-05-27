package com.ants.email.samples.service.api;

import com.ants.email.samples.pojo.MailInfo;

/**
 * @author lyy08
 */
public interface MailServer {
	/***
	 * 功能描述 发送邮件
	 * @author lyy
	 * @date 2022-05-27
	 * @param
	 * @return
	 */
	int sendMail(MailInfo mailInfo);

	/***
	 * 功能描述 发送邮件带附件
	 * @author lyy
	 * @date 2022-05-27
	 * @param [mailInfo]
	 * @return int
	 **/


	int sendMailWithAttachment(MailInfo mailInfo);

	/***
	 * 功能描述 发送邮件 内容添加图片
	 * @author lyy
	 * @date 2022-05-27
	 * @param [mailInfo]
	 * @return int
	 **/

	int sendMailWithInlineImage(MailInfo mailInfo);
}
