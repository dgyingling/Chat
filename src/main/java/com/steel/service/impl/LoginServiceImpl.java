package com.steel.service.impl;

import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.steel.entity.User;
import com.steel.mapper.UserDao;
import com.steel.service.LoginService;
import com.steel.service.UserService;
import com.steel.util.MyConstant;
import com.steel.util.StringUtil;
@Service
public class LoginServiceImpl extends ServiceImpl<UserDao,User> implements LoginService{

	 private static final String PNG_MIME = "image/png";
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine emailTemplateEngine;
	@Autowired
	private UserService userService;
	/*
	 * 登录
	 */
	/*public Object login(User user){
		
		User u = baseMapper.selectUserByEmail(user.getEmail());		
		if(u!=null){
			if(u.isActived()){
				return u.getPassword().equals(user.getPassword())? u:"密码输入错误";				
			}else{
				return "你的账号还没有被激活，请到邮箱激活";
			}
		}else{
			return "你输入的邮箱不存在";
		}
	}
	*/
	
	/*
	 * 注册
	 */
	
	@Override
	public String insertUser(User user)  {
		System.out.println(user);
		int count = baseMapper.selectEmailCount(user.getEmail());		
		if(count !=0){	
			return "该邮箱已被注册";
		}		
		user.setEnabled(true);
		user.setActived(false);
		user.setJoinTime(new Date());
		user.setNickname("DG"+new Random().nextInt(10000));
		user.setHeadUrl(MyConstant.QCLOUD_IMAGE_URL +"/headUrl/timg.jpg");
		//insert(user);
		userService.createUser(user);
		return "ok";
	}
	/*
	 * 发送邮件
	 */
	
	public void sendRichEmail(String email,int operation,String code,Locale locale) throws MessagingException{
		Context ctx = new Context(locale);
		ctx.setVariable("email", email);
		ctx.setVariable("url",MyConstant.DOMAIN_NAME );
		ctx.setVariable("code", code);
		
		MimeMessage message =mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8"); 
		helper.setFrom("1539261793@qq.com");
		helper.setTo(email);

		
		helper.addInline("image", new ClassPathResource("/resources/images/thymeleaf-logo.png"), PNG_MIME);	
		if(operation==1){
			helper.setSubject("注册DG论坛");
			String emailText = emailTemplateEngine.process("activeMail", ctx);
			helper.setText(emailText, true);
			
		}else{
			helper.setSubject("DG论坛重置密码");
			String emailText = emailTemplateEngine.process("changePwMail", ctx);
			helper.setText(emailText, true);
		}
		mailSender.send(message);
	}
	
	
	/*
	 * 激活账号
	 */
	
	public void updateActived(String code){
		baseMapper.updateActived(code);
	}


	@Override
	public User selectUserByEmail(String email) {
		return baseMapper.selectUserByEmail(email);
		 
	}
	/*
	 *重置密码 
	 */
	@Override
	public void changePW(String code) {
		
		
	}
	
}
