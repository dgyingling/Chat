package com.steel.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;

public class UploadUtil {
	 public static String uploadImage(MultipartFile file,String path){
		
		 String cosPath = generateCosPath(file,path);
		 // 初始化秘钥信息
			Credentials cred = new Credentials(MyConstant.QCLOUD_APP_ID, 
					MyConstant.QCLOUD_SECRET_ID, MyConstant.QCLOUD_SECRET_KEY);
			// 初始化客户端配置
	        ClientConfig clientConfig = new ClientConfig();
	        // 设置bucket所在的区域，比如华南园区：gz； 华北园区：tj；华东园区：sh ；
	        clientConfig.setRegion("tj");
	        // 初始化cosClient
	        COSClient cosClient = new COSClient(clientConfig, cred);
	        try {
				cosClient.uploadFile(new  UploadFileRequest("image", cosPath,file.getBytes()));
			} catch (IOException e) {
				e.printStackTrace();
			}
	        return cosPath;
	 }
	 public static String generateCosPath(MultipartFile file,String path){
		//生成文件名
		String fileName = file.getOriginalFilename();
		String fileExt = fileName.substring(fileName.indexOf("."));
		String imageName = UUID.randomUUID().toString()+fileExt;
		return path+imageName;
	 }
}
