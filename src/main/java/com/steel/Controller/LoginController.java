package com.steel.Controller;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.steel.entity.Post;
import com.steel.entity.User;
import com.steel.service.LoginService;
import com.steel.service.PostService;
import com.steel.util.StringUtil;

@Controller

public class LoginController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private PostService postService;
	
	@RequestMapping("/")
	public String toIndex(Model model){
		//String publishTime = null;
		Page<Post> page = new Page<Post>(1,8);
		page = postService.selectAllPosts(page);	
		model.addAttribute("page",page);
		return "index";
	}
	@RequestMapping("/toOtherPage/{current}")
	public String toOtherPage(@PathVariable Integer current,Model model){
		Page<Post> page = new Page<Post>(current,8);
		page = postService.selectAllPosts(page);	 
		model.addAttribute("page",page);
		return "index";
	}
	@RequestMapping("/toLogin")
	public String toLogin(){
		return "login";
	}
	@RequestMapping("/login")
	public String login(User user,Model model,HttpSession session,HttpServletRequest request){
		
		UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(),user.getPassword());
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();
		try{
			model.addAttribute("email", user.getEmail());
			subject.login(token);	
			if(subject.isAuthenticated()){
				User u = loginService.selectUserByEmail(user.getEmail());	
				if(!u.isActived()){				
					model.addAttribute("error", "你的账号还没有被激活，请到邮箱激活") ;
					return "login";
				}
				session.setAttribute("user", u);
				return "redirect:/";
				
			}		
		} catch(UnknownAccountException e){
			model.addAttribute("error", "用户名不存在");
		}catch (IncorrectCredentialsException e) {  			   
			model.addAttribute("error","密码错误");  
		}
		return "login";
	}  
	
	
	@RequestMapping("/register")
	public String register(User user,Model model,Locale locale){
		String code = StringUtil.createActivateCode();
		user.setActivateCode(code);
		String result = loginService.insertUser(user);
		if(result.equals("ok")){
			try {
				loginService.sendRichEmail(user.getEmail(),1,code,locale);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			model.addAttribute("info","系统已经向你的邮箱发送了一封邮件哦，验证后就可以登录啦~");
			return "promptInfo";
		}else {
            model.addAttribute("register","yes");
            model.addAttribute("email",user.getEmail());
            model.addAttribute("error",result);
            return "login";
        }
	}
	@RequestMapping("/activate")
	public String activate(@RequestParam(value="code") String code,Model model){
		loginService.updateActived(code);
		model.addAttribute("info","您的账户已经激活成功，可以去登录啦~");
		return "promptInfo";
	}

	//忘记密码
	@RequestMapping(value="/forgetPassword",method=RequestMethod.GET)
	public String forgetPassword(String email,Locale locale,Model model){
		User user = loginService.selectUserByEmail(email);
		try {
			loginService.sendRichEmail(user.getEmail(),2,user.getActivateCode(),locale);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		model.addAttribute("info","系统已经向你的邮箱发送了一封邮件哦，请注意查收~");
		return "promptInfo";
		
	}
	//到重置密码页面
	@RequestMapping("/changePw")
	public String changePw(String code,Model model){
		model.addAttribute("code", code);
		return "changePw";
	}
	
}
