package com.steel.service;

import com.steel.entity.Reply;

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
public interface ReplyService extends IService<Reply> {

	List<Reply> selectReplyList(Integer pid);

	void insertReply(Reply reply);
	
}
