package com.steel.service.impl;

import com.steel.entity.Comment;
import com.steel.entity.Reply;
import com.steel.mapper.CommentDao;
import com.steel.service.CommentService;
import com.steel.service.ReplyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yingling
 * @since 2017-06-24
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {
	@Autowired
	private ReplyService replyService;
	@Override
	public List<Comment> selectCommentList(Integer pid) {
		List<Comment> commentList = baseMapper.selectCommentList(pid);
		for(Comment comment : commentList){
			List<Reply> replyList = replyService.selectReplyList(comment.getCid());	
			comment.setReplyList(replyList);
		}
		return commentList;
	}
	@Override
	public void insertComment(Comment comment) {
		baseMapper.insertComment(comment);
	}
	
}
