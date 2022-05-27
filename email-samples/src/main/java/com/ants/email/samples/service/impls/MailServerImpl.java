package com.ants.email.samples.service.impls;

import com.ants.email.samples.pojo.MailInfo;
import com.ants.email.samples.service.api.MailServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Set;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-27
 **/
@Service("MailServer")
@Slf4j
public class MailServerImpl implements MailServer {

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public int sendMail(MailInfo mailInfo) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(mailInfo.getFrom());
			message.setSubject(mailInfo.getSubject());
			message.setText(mailInfo.getContent());
			String join = String.join(",", mailInfo.getReceives());
			///message.setTo(String.valueOf(InternetAddress.parse("email1@test.com,email2@test.com")));
			///message.setTo(new String[]{"email1@test.com", "email2@test.com"});
			// 收件人
			message.setTo(mailInfo.getReceives().stream().toArray(String[]::new));
			// 抄送人
			if (!StringUtils.isEmpty(mailInfo.getCcs())) {
				message.setCc(mailInfo.getCcs().stream().toArray(String[]::new));
			}
			javaMailSender.send(message);
			return 1;
		} catch (Exception ex) {
			return -1;
		}

	}

	@Override
	public int sendMailWithAttachment(MailInfo mailInfo) {
		try {

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			// use the true flag to indicate you need a multipart message
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(mailInfo.getFrom());
			helper.setTo(mailInfo.getReceives().stream().toArray(String[]::new));
			// 抄送人
			if (!StringUtils.isEmpty(mailInfo.getCcs())) {
				helper.setCc(mailInfo.getCcs().stream().toArray(String[]::new));
			}
			helper.setSubject(mailInfo.getSubject());
			helper.setText(mailInfo.getContent());
			// add the file attachment
			for (String filePath : mailInfo.getAttachment()) {
				File file = new File(filePath);
				FileSystemResource fr = new FileSystemResource(file);
				helper.addAttachment(file.getName(), fr);
			}
			javaMailSender.send(mimeMessage);
			return 1;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return -1;
		}
	}

	@Override
	public int sendMailWithInlineImage(MailInfo mailInfo) {
		try {

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			// use the true flag to indicate you need a multipart message
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(mailInfo.getFrom());
			helper.setTo(mailInfo.getReceives().stream().toArray(String[]::new));
			// 抄送人
			if (!StringUtils.isEmpty(mailInfo.getCcs())) {
				helper.setCc(mailInfo.getCcs().stream().toArray(String[]::new));
			}
			helper.setSubject(mailInfo.getSubject());
			// add the file attachment
			for (String filePath : mailInfo.getAttachment()) {
				File file = new File(filePath);
				FileSystemResource fr = new FileSystemResource(file);
				helper.addAttachment(file.getName(), fr);
			}

			StringBuilder stringBuilder = new StringBuilder();
			if (mailInfo.getImages() != null && mailInfo.getImages().size() > 0) {
				stringBuilder.append(String.format("<html>This message contains %d inline images.<br>", mailInfo.getImages().size()));
				int i = 1;
				for (String key : mailInfo.getImages().keySet()) {
					stringBuilder.append(String.format("The %d image :<br>", i++));
					stringBuilder.append(String.format("<img src='cid:%s'  width=15%% height=15%% /><br>", key));
				}
				stringBuilder.append("end of message");
				stringBuilder.append("</html>");
				helper.setText(stringBuilder.toString(), true);
			} else {
				helper.setText(mailInfo.getContent());
			}
			if (mailInfo.getImages() != null && mailInfo.getImages().size() > 0) {
				// creates message part
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(stringBuilder.toString(), "text/html");

				// creates multi-part
				Multipart multipart = new MimeMultipart("related");
				multipart.addBodyPart(messageBodyPart);
				// adds inline image attachments
				if (mailInfo.getImages() != null && mailInfo.getImages().size() > 0) {
					Set<String> setImageID = mailInfo.getImages().keySet();

					for (String contentId : setImageID) {
						MimeBodyPart imagePart = new MimeBodyPart();
						imagePart.setHeader("Content-ID", "<" + contentId + ">");
						imagePart.setDisposition(MimeBodyPart.INLINE);
						String imageFilePath = mailInfo.getImages().get(contentId);
						imagePart.attachFile(imageFilePath);
						multipart.addBodyPart(imagePart);
					}
				}
				mimeMessage.setContent(multipart);
			}
			javaMailSender.send(mimeMessage);
			return 1;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return -1;
		}
	}
}
