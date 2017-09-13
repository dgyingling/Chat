package com.steel.Controller;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.steel.entity.User;
import com.steel.service.UserService;
import com.steel.util.MyConstant;
import com.steel.util.UploadUtil;
@Controller
public class UploadController {
	@Autowired
	private UserService userService;
	/*
	 * 上传图片
	 */
	@ResponseBody
	@RequestMapping(value="/upload")
	public String uploadPicture(MultipartFile myFileName){
		//上传
		 String result = UploadUtil.uploadImage(myFileName,"/chat/");
		return MyConstant.QCLOUD_IMAGE_URL+result;
	}
	/*
	 * 上传头像
	 */
	@RequestMapping("/uploadImage")
	public String uploadImage(MultipartFile file,HttpSession session,Model model){
		
		//上传
		 String result = UploadUtil.uploadImage(file,"/headUrl/");
		//更新头像地址
		User user = (User) session.getAttribute("user");
		user.setHeadUrl(MyConstant.QCLOUD_IMAGE_URL+result);
		userService.updateById(user);
        return "redirect:/toMyProFile";
	}
}
