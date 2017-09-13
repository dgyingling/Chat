package com.steel.Controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.steel.entity.Comment;
import com.steel.entity.Reply;
import com.steel.entity.User;
import com.steel.service.ReplyService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yingling
 * @since 2017-06-23
 */
@Controller
@RequestMapping
public class ReplyController {
	@Autowired
	private ReplyService replyService;
	
	@RequestMapping("/doReply")
	public String doReply(Integer cid,Integer pid,Reply reply,HttpSession session){
		
		User user = (User) session.getAttribute("user");
		/*if(user==null){
			return "login";
		}*/
		reply.setReplyTime(new Date());
		Comment comment = new Comment();
		comment.setCid(cid);
		reply.setUser(user);
		reply.setComment(comment);
		
		replyService.insertReply(reply);
		System.out.println("回复成功");
		return "redirect:/toPost/"+pid;
	}
}
