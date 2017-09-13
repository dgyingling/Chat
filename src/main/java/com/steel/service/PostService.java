package com.steel.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.steel.entity.Post;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yingling
 * @since 2017-06-24
 */
public interface PostService extends IService<Post> {

	Page<Post> selectAllPosts(Page<Post> page);

	void insertPost(Post post);

	Post selectPostById(Integer pid);

	List<Post> selectPostsByUid(Integer uid);

	void updateScanCount(Integer pid,String ip);
	
	void updateScanCount(Integer pid, int postScan);
	
	/*Integer findLikeCount(Integer pid);*/
	

	void updateReplyCount(Post post);
	
	void updateReplyTime(Post post);
	
	String saveLike(Integer pid,Integer sessionUid,Integer uid);

	boolean isLiked(Integer uid,Integer pid);

	List<Integer> selectAllPids();

	

	
	
}
