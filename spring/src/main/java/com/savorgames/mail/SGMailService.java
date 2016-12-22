package com.savorgames.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SGMailService {
	
	@Autowired private JavaMailSender mailSender;
	
	@Value("${sg.mail.from}")
	private String from;
	
	@Value("${sg.app.name}")
	private String appName;
	
	/**
	 * 注册成功发送邮件
	 * @param to
	 * @param content
	 * @return
	 * @throws MessagingException 
	 */
	@Async
	public void sendAsyncEmail(String to, String content){
		MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(),"utf-8");
		try {
			helper.setText(content, true);
			helper.setSubject("您在 "+ appName +" 注册成功");
			helper.setFrom(this.from);
			helper.setTo(to);
		} catch (MessagingException e) {
			System.out.println("封装邮件helper错误" + e.getMessage());
		}
		this.send(helper.getMimeMessage());
	}
	
	@Async
	public void sendForgetPasswordMail(String to, String content) {
	  MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(),"utf-8");
	     try {
           helper.setText(content, true);
           helper.setSubject("SAVORGAMES 您正在找回密码");
           helper.setFrom(this.from);
           helper.setTo(to);
       } catch (MessagingException e) {
           System.out.println("封装邮件helper错误" + e.getMessage());
       }
       this.send(helper.getMimeMessage());
	}

	public boolean send(MimeMessage mimeMessage) {
		try {
			mailSender.send(mimeMessage);
			return true;
		} catch (MailException e) {
			System.out.println("发送邮件错误" + e.getMessage());
			return false;
		}
	}
}
