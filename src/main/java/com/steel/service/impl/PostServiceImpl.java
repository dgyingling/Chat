package com.steel.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.steel.entity.Post;
import com.steel.mapper.PostDao;
import com.steel.service.PostService;
import com.steel.service.UserService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yingling
 * @since 2017-06-24
 */

@Service
public class PostServiceImpl extends ServiceImpl<PostDao, Post> implements PostService {
	@Autowired
	private JedisPool jedisPool;
	
	
	public Page<Post> selectAllPosts(Page<Post> page) {
	
		Jedis jedis = jedisPool.getResource();
		List<Post> posts = baseMapper.selectAllPosts(page);
		for(Post post : posts){
			long scanCount = jedis.scard("postScan:"+post.getPid());
			long likeCount = jedis.scard("postLike:"+post.getPid());
			post.setScanCount((int)scanCount+post.getScanCount());
			post.setLikeCount((int)likeCount);
		}
		page.setRecords(posts);
		return page;
	}
	/*
	 *插入帖子
	 */
	public void insertPost(Post post) {
		post.setLikeCount(0);
		post.setReplyCount(0);
		post.setScanCount(0);
		post.setPublishTime(new Date());
		baseMapper.insertPost(post);
	}
	/*
	 *通过pid查询帖子
	 */

	public Post selectPostById(Integer pid) {

		Post post = baseMapper.selectPostById(pid);
		Jedis jedis = jedisPool.getResource();
		long likeCount =jedis.scard("postLike:"+pid);
		long scanCount = jedis.scard("postScan:"+pid);
		post.setLikeCount((int)likeCount);
		post.setScanCount((int)scanCount+post.getScanCount());//浏览量需要每天清除
		jedis.close();
		return post;
	}
	
	
	/*
	 * 显示个人信息
	 */
	@Override
	@Cacheable("propertyMISCache")
	public List<Post> selectPostsByUid(Integer uid) {	
		return baseMapper.selectPostsByUid(uid);
	}
	/*
	 *保存帖子浏览量到redis
	 */
	
	@Override
	public void updateScanCount(Integer pid,String ip) {
		System.out.println(ip);
		//todo：用redis(时间设为30分钟)sadd
		Jedis jedis = jedisPool.getResource();	
		jedis.sadd("postScan:"+pid, ip);			
		jedis.close();

	}
	
	
	//更新数据库
	
	@Override
	public void updateReplyCount(Post post) {
		baseMapper.updateReplyCount(post);
		
	}
	@Override
	public void updateReplyTime(Post post) {
		baseMapper.updateReplyTime(post);
	}
	
	/*
	 *用redis存储pid:sessionuid ---》点赞
	 */
		
	@Override
	public String saveLike(Integer pid,Integer sessionUid,Integer uid) {
		Jedis jedis = jedisPool.getResource();
		jedis.sadd("postLike:"+pid, sessionUid+"");
		jedis.hincrBy("user_likeCount",uid+"", 1);
	
		//插入一条点赞消息
		//todo
		String result = String.valueOf(jedis.scard("postLike:"+pid));
		jedis.close();
		return result;
	}
	/*
	 * 判断是否点过赞
	 */
	@Override
	public boolean isLiked(Integer uid,Integer pid) {
		
		Jedis jedis = jedisPool.getResource();
		boolean liked = jedis.sismember("postLike:"+pid, uid+"");		
		jedis.close();
		return liked;
	}
	//查询所有pid
	@Override
	public List<Integer> selectAllPids() {
		return baseMapper.selectAllPids(); 
	}
	//更新帖子浏览量到数据库
	@Override
	public void updateScanCount(Integer pid, int postScan) {
		baseMapper.updateScanCount(pid,postScan);		
	}
	
	
}
