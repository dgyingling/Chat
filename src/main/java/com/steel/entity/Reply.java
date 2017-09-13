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
 * 答复
 */
public class Reply extends Model<Reply> {

    private static final long serialVersionUID = 1L;

	private Integer rid;
	private String content;
	private User user;
	private Comment comment;
	@TableField("reply_to")
	private String replyTo;
	@TableField("reply_time")
	private Date replyTime;
	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.rid;
	}

	

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

}
