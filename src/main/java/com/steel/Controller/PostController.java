package com.steel.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.steel.entity.Comment;
import com.steel.entity.Post;
import com.steel.entity.User;
import com.steel.service.CommentService;
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
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private CommentService commentService;
	/*
	 * 去发布帖子页面
	 */
	@RequestMapping("/toPublish")
	public String toPublish(){
		return "publish";
	}
	
	/*
	 * 发布帖子
	 */
	
	@RequestMapping("/publishPost")
	public String publishPost(Post post,HttpSession session){
		
		User user = (User) session.getAttribute("user");
		userService.incrPostCount(user.getUid());
		postService.insertPost(post);
		return "redirect:/";
	}
	/*
	 * 去帖子页面
	 */
	@RequestMapping("/toPost/{pid}")
	public String toPost(@PathVariable Integer pid,Model model
			,HttpServletRequest request,HttpSession session){
		
		String ip = request.getRemoteAddr();
		postService.updateScanCount(pid,ip);
		//查询帖子
		Post post = postService.selectPostById(pid);
		//查询评论
		List<Comment> commentList = commentService.selectCommentList(pid);
		//判断是否点过赞
		User user = (User) session.getAttribute("user");
		boolean liked = false;
		if(user!=null){
			liked = postService.isLiked(user.getUid(),pid);			
		}
		model.addAttribute("liked", liked);
		model.addAttribute("commentList",commentList);
		model.addAttribute("post", post);
		return "post";
	}
	/*
	 * 删除帖子
	 */
	@RequestMapping("/deletePost/{pid}")
	public String deletePost(@PathVariable Integer pid,HttpSession session){
		User user = (User) session.getAttribute("user");		
		userService.decrPostCount(user.getUid(),pid);
		Post post = new Post();
		post.setPid(pid);
		postService.delete(new EntityWrapper<Post>(post));
		return "redirect:/toMyProfile";
	}
	/*
	 * 处理点赞
	 */
	
	@RequestMapping(value="/ajaxClickLike/{pid}")
	public @ResponseBody String ajaxClickLike(@PathVariable Integer pid
			,@RequestParam Integer uid,HttpSession session){
		Integer sessionUid = ((User) session.getAttribute("user")).getUid();
		return postService.saveLike(pid,sessionUid,uid);
		
	}
}
