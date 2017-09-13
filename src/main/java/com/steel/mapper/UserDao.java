package com.steel.mapper;

import com.steel.entity.User;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yingling
 * @since 2017-06-24
 */
public interface UserDao extends BaseMapper<User> {
	void updateActived(String code);
	
	int selectEmailCount(String email);
	
	User selectUserById(Integer uid);
	
	User selectUserByEmail(String email);

	void incrPostCount(Integer uid);
	
	void decrPostCount(Integer uid);

	List<Integer> selectAllUids();

	void updateScanCount(@Param(value="uid") Integer uid,@Param(value="userScan") int userScan);

	User selectUserByCode(String code);

	void updatePassword(User user);

	void updateUser(User user);

	
}