package com.steel.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.steel.service.PostService;
import com.steel.service.UserService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class UpdateTask{
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private PostService postService;
	@Autowired
	private UserService userService;
	

	@Scheduled(cron="0 0 3 * * ?")
	public void execute(){
		List<Integer> pids = postService.selectAllPids();
		List<Integer> uids = userService.selectAllUids();
		Jedis jedis = jedisPool.getResource();
		
		for(Integer pid : pids){
			System.out.println(pid);
			long postScan = jedis.scard("postScan:"+pid);
			System.out.println(postScan+"postScan");
			postService.updateScanCount(pid,(int)postScan);	
			System.out.println("更新完成");
			jedis.del("postScan:"+pid);
			System.out.println("删除成功");
		}
		for(Integer uid : uids){
			long userScan = jedis.scard("userScan:"+uid);
			System.out.println(uid+"uid");
			userService.updateScanCount(uid,(int)userScan);	
			System.out.println("更新完成");
			jedis.del("userScan:"+uid);
			System.out.println("删除成功");
		}
		jedis.close();
	}

}
