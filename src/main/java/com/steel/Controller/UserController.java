package com.steel.Controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.steel.entity.Post;
import com.steel.entity.User;
import com.steel.service.LoginService;
import com.steel.service.PostService;
import com.steel.service.UserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yingling
 * @since 2017-06-23
 */
@Controller
public class UserController{
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	@Autowired
    private PasswordService passwordService;
	/*
	 * 去他人主页
	 */
	@RequestMapping("/toProfile/{uid}")
	public String toProfile(@PathVariable Integer uid,Model model,HttpSession session,HttpServletRequest request){	
		List<Post> postList = postService.selectPostsByUid(uid);
		User sessionUser = (User) session.getAttribute("user");
		String ip = request.getRemoteAddr();
		//更新被访问用户的访问量
		userService.updateScanCount(uid,ip);
		User user = userService.selectUserById(uid);
		
		boolean following = false;
		if(sessionUser!=null){
			following = userService.isFollowed(uid,sessionUser.getUid());			
		}
		model.addAttribute("following", following);
		model.addAttribute("postList",postList);
		model.addAttribute("user", user);
		return "profile";
	}
	/*
	 * 去个人主页
	 */
	@RequestMapping("/toMyProfile")
	public String toProfile(Map<String,Object> map,HttpSession session){
		Integer uid  =  ((User) session.getAttribute("user")).getUid();
		List<Post> postList = postService.selectPostsByUid(uid);
		User user = userService.selectUserById(uid);
		map.put("user", user);
		map.put("postList", postList);
		return "profile";
	}
	/*
	 * 关注用户
	 */
	@RequestMapping("/follow/{uid}")
	public String follow(@PathVariable Integer uid,HttpSession session){
		User sessionUser = (User) session.getAttribute("user");
		userService.follow(uid, sessionUser.getUid());
		return "redirect:/toProfile/"+uid;
	}
	/*
	 * 取消关注
	 */
	@RequestMapping("/unfollow/{uid}")
	public String unfollow(@PathVariable Integer uid,HttpSession session){
		User sessionUser = (User) session.getAttribute("user");
		userService.unfollow(uid, sessionUser.getUid());
		return "redirect:/toProfile/"+uid;
	}
	/*
	 * 去编辑信息页面
	 */
	@RequestMapping("/toEditProfile")
	public String toEditProfile(HttpSession session,Model model){
		Integer uid  =  ((User) session.getAttribute("user")).getUid();
		User user = userService.selectById(uid);
		model.addAttribute("user", user);
		return "editProfile";
	}
	/*
	 * 保存修改信息:jquery
	 */
	@RequestMapping("/saveProfile")
	public String editProfile(User user){
		userService.updateUser(user);
		return "redirect:/toMyProfile";
	}
	
	/*
	 * 修改密码:待修改用jquery
	 */
	@RequestMapping("/updatePassword")
	public String updatePassword(String password,String newpassword,HttpSession session,Model model){
		User user = (User) session.getAttribute("user");	
		boolean ispass = passwordService.passwordsMatch(password, user.getPassword());
		if(!ispass){
			model.addAttribute("passwordError","你输入的密码错误");
			return "changePw";
		}else{
			user.setPassword(newpassword);
			userService.updatePassword(user);
		}
		return "redirect:/toMyProfile";
	}
	/*
	 * 忘记密码：重置密码
	 */
	@RequestMapping("/resetPassword")
	public String resetPassword(String password,String newpassword,String code,Model model){
		User user = userService.selectUserByCode(code);
		boolean ispass = passwordService.passwordsMatch(password, user.getPassword());
		if(!ispass){
			model.addAttribute("passwordError","你输入的密码错误");
			model.addAttribute("code", code);
			return "changePw";
		}else{
			System.out.println("reset");
			user.setPassword(newpassword);
			userService.updatePassword(user);
		}
		return "redirect:/toLogin";
	}
}
