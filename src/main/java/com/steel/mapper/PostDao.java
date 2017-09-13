package com.steel.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.steel.entity.Post;

/**
 * <p>
  *  Mapper 接口
 * </p>
 * @param page
     *            翻页对象，可以作为 xml 参数直接使用，传递参数 Page 即自动分页
 * @author yingling
 * @since 2017-06-24
 */
public interface PostDao extends BaseMapper<Post> {
	
	List<Post> selectAllPosts(Pagination page);
	
	 void insertPost(Post post);
	 
	 Post selectPostById(Integer pid);
	 
	 List<Post> selectPostsByUid(Integer uid);
	 
	 void updateReplyCount(Post post);
	 
	 void updateReplyTime(Post post);
	 
	 void updatePost(Post post);

	void updateLikeCount(Integer pid);
	
	List<Integer> selectAllPids();
	
	void updateScanCount(@Param(value="pid") Integer pid,@Param(value="postScan") int postScan);
}