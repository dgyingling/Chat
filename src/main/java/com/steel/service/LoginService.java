package com.steel.service;

import java.util.Locale;

import javax.mail.MessagingException;

import com.baomidou.mybatisplus.service.IService;
import com.steel.entity.User;

public interface LoginService extends IService<User>{
	
	String insertUser(User user);
	
	void updateActived(String code);
	
	User selectUserByEmail(String email);
	
	void sendRichEmail(String email,int operation,String code,Locale locale) throws MessagingException;
	
	void changePW(String code);
	

}
