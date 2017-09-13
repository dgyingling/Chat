package com.steel.service.impl;

import com.steel.entity.Reply;
import com.steel.mapper.ReplyDao;
import com.steel.service.ReplyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

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
public class ReplyServiceImpl extends ServiceImpl<ReplyDao, Reply> implements ReplyService {

	@Override
	public List<Reply> selectReplyList(Integer cid) {
		return baseMapper.selectReplyList(cid);
	}

	@Override
	public void insertReply(Reply reply) {
		baseMapper.insertReply(reply);
	}
	
}
