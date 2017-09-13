package com.steel.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.steel.entity.Reply;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yingling
 * @since 2017-06-24
 */
public interface ReplyDao extends BaseMapper<Reply> {
	List<Reply> selectReplyList(Integer pid);

	void insertReply(Reply reply);
}