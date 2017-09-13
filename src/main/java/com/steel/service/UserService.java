package com.steel.service;

import com.steel.entity.User;

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
public interface UserService extends IService<User> {
	
	User selectUserByEmail(String email);

	//void updatePostCount(Integer uid,String inde);
	void incrPostCount(Integer uid);
	
	void decrPostCount(Integer uid,Integer pid);

	boolean isFollowed(Integer uid,Integer sessionUid);

	void follow(Integer uid, Integer sessionUid);

	void unfollow(Integer uid, Integer uid2);

	void updateScanCount(Integer uid, String ip);

	User selectUserById(Integer uid);

	List<Integer> selectAllUids();

	void updateScanCount(Integer uid, int userScan);

	public void createUser(User user); //创建账户  

	User selectUserByCode(String code);

	void updatePassword(User user);

	void updateUser(User user);
}
