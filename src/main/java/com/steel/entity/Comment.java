package com.steel.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;


/**
 * <p>
 * 
 * </p>
 *
 * @author yingling
 * @since 2017-06-24
 * 评论
 */
public class Comment extends Model<Comment> {

    private static final long serialVersionUID = 1L;

	private Integer cid;
	private String content;
	private List<Reply> replyList;
	private User user;
	private Post post;

	@TableField("comment_time")
	private Date commentTime;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	
	@Override
	protected Serializable pkVal() {
		return this.cid;
	}

	public List<Reply> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
