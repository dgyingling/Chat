package com.steel.service.impl;

import java.util.List;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.steel.entity.User;
import com.steel.mapper.UserDao;
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
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
	@Autowired
	private JedisPool jedisPool;
	@Autowired
    private PasswordService passwordService;
	@Override
	public User selectUserByEmail(String email) {
		return baseMapper.selectUserByEmail(email);
	}
	//为user（数据库中取出的）赋值（redis的值）
	@Override
	public User selectUserById(Integer uid) {
		Jedis jedis = jedisPool.getResource();
		User user = baseMapper.selectUserById(uid);
		long scanCount = jedis.scard("userScan:"+uid);
		String publishCount = jedis.hget("user_publishCount", uid+"");
		String likeCount = jedis.hget("user_likeCount", uid+"");
		long followCount = jedis.scard("follow:"+uid);
		long followerCount = jedis.scard("follower:"+uid);
		if(publishCount == null){
			user.setPostCount(0);
		}else{
			user.setPostCount(Integer.valueOf(publishCount));			
		}
		if(likeCount == null){
			user.setLikeCount(0);
		}else{
			user.setLikeCount(Integer.valueOf(likeCount));			
		}
		user.setFollowCount((int)followCount);
		user.setFollowerCount((int)followerCount);
		user.setScanCount(user.getScanCount()+(int)scanCount);
		return user;
	}

	@Override
	public void incrPostCount(Integer uid) {
		
		Jedis jedis = jedisPool.getResource();
		jedis.hincrBy("user_publishCount", uid+"", 1);
		jedis.close();
	}
	//减少user的publishCount ，同时删除pid:like,pid:scan
	public void decrPostCount(Integer uid,Integer pid) {
		
		Jedis jedis = jedisPool.getResource();
		jedis.hincrBy("user_publishCount", uid+"", -1);
		jedis.del("postLike:"+pid,"postScan:"+pid);
		jedis.close();
	}
	//判断当前用户是否关注了uid
	@Override
	public boolean isFollowed(Integer uid,Integer sessionUid) {
		
		Jedis jedis = jedisPool.getResource();
		boolean followed = jedis.sismember("follower:"+uid, sessionUid+"");
		jedis.close();
		return followed;
		
	}

	@Override
	public void follow(Integer uid, Integer sessionUid) {
		
		Jedis jedis = jedisPool.getResource();
		jedis.sadd("follower:"+uid, sessionUid+"");
		jedis.sadd("follow:"+sessionUid, uid+"");
		jedis.close();
		
	}
	
	@Override
	public void unfollow(Integer uid, Integer sessionUid) {
		Jedis jedis = jedisPool.getResource();
		jedis.srem("follower:"+uid, sessionUid+"");
		jedis.srem("follow:"+sessionUid, uid+"");
		jedis.close();
	}
	//更新被访问用户的访问量
	@Override
	public void updateScanCount(Integer uid, String ip) {
		Jedis jedis = jedisPool.getResource();
		jedis.sadd("userScan:"+uid, ip);
		jedis.close();
	}
	//查询所有uid
	@Override
	public List<Integer> selectAllUids() {
		return baseMapper.selectAllUids();
	}
	//更新用户主页浏览量到数据库
	@Override
	public void updateScanCount(Integer uid, int userScan) {
		baseMapper.updateScanCount(uid,userScan);
	}
	@Override
	public void createUser(User user) {
		//PasswordHelper.encryptPassword(user);
		 // 加密密码
        String password = passwordService.encryptPassword(user.getPassword());
        user.setPassword(password);
		baseMapper.insert(user);
	}
	@Override
	public void updatePassword(User user) {
		String encrPassword = passwordService.encryptPassword(user.getPassword());
		user.setPassword(encrPassword);
		baseMapper.updatePassword(user);	
	}
	@Override
	public User selectUserByCode(String code) {
		 return baseMapper.selectUserByCode(code);	
	}
	@Override
	public void updateUser(User user) {
		baseMapper.updateUser(user);
		
	}
	

	
	
}
