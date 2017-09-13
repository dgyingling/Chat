package com.steel.Controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.steel.entity.Comment;
import com.steel.entity.Post;
import com.steel.entity.User;
import com.steel.service.CommentService;
import com.steel.service.PostService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yingling
 * @since 2017-06-23
 */
@Controller
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private PostService postService;
	
	@RequestMapping("/comment/{pid}")
	public String doComment(@PathVariable Integer pid,Comment comment,HttpSession session){
		
		User user = (User) session.getAttribute("user");
		/*if(user==null){
			return "login";
		}*/
		comment.setCommentTime(new Date());
		comment.setUser(user);
		Post post = postService.selectPostById(pid);
		post.setReplyCount(post.getReplyCount()+1);
		post.setReplyTime(new Date());
		postService.updateReplyCount(post);
		postService.updateReplyTime(post);
		comment.setPost(post);
		commentService.insertComment(comment);
		return "redirect:/toPost/"+pid;
		
	}
}
