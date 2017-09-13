package com.steel.service;

import com.steel.entity.Comment;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yingling
 * @since 2017-06-24
 */
public interface CommentService extends IService<Comment> {

	 List<Comment> selectCommentList(Integer pid) ;

	void insertComment(Comment comment);

}
