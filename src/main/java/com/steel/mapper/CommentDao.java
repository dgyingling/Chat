package com.steel.mapper;

import com.steel.entity.Comment;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yingling
 * @since 2017-06-24
 */
public interface CommentDao extends BaseMapper<Comment> {

	List<Comment> selectCommentList(Integer pid);

	void insertComment(Comment comment);

}