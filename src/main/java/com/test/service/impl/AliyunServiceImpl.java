package com.test.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.test.constant.AliyunConfig;
import com.test.model.aliyun.AliyunSms;
import com.test.service.IAliyunService;
import com.test.utils.CheckTool;
import com.test.utils.FileTool;
import com.test.utils.StringTool;

@Service("aliyunService")
public class AliyunServiceImpl implements IAliyunService {


	// 对象存储
	private OSSClient ossClient = new OSSClient(AliyunConfig.endpoint, AliyunConfig.accessKeyId,
			AliyunConfig.accessKeySecret);

	// 验证码发送
	private static DefaultProfile defaultProfile = DefaultProfile.getProfile(AliyunConfig.regionIdSms,
			AliyunConfig.accessKeyIdSms, AliyunConfig.accessKeySecretSms);
	private IAcsClient iAcsClient = new DefaultAcsClient(defaultProfile);

	//根据后缀上传图片
	@Override
	public String uploadObject(MultipartFile file, String suffix) {

		if (CheckTool.isNullOrEmpty(file)) {
			return null;
		}

		String fileName = System.currentTimeMillis() + StringTool.getId() + suffix;

		String absPath = this.getClass().getClassLoader().getResource("").getPath();
		absPath = absPath + fileName;
		absPath = absPath.replace("classes", "static");

		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(absPath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		File objectFile = new File(absPath);
		ossClient.putObject(AliyunConfig.bucketName, fileName, objectFile);
		// 设置URL过期时间为10年 3600l* 1000*24*365*10
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		URL url = ossClient.generatePresignedUrl(AliyunConfig.bucketName, fileName, expiration);
		// 暂不关闭
		// ossClient.shutdown();

		FileTool.delFile(absPath);
		return url.toString().replace("http", "https");

	}

	@Override
	public String uploadObjectByUri(String uri, String name) {

		if (CheckTool.isNullOrEmpty(uri)) {
			return null;
		}

		File objectFile = new File(uri);
		ossClient.putObject(AliyunConfig.bucketName, name, objectFile);
		// 设置URL过期时间为10年 3600l* 1000*24*365*10
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		URL url = ossClient.generatePresignedUrl(AliyunConfig.bucketName, name, expiration);
		// 暂不关闭
		// ossClient.shutdown();

		return url.toString().replace("http", "https");

	}

	// 使用阿里云发送验证码
	@Override
	public String send(String phone) {


		String code = StringTool.getRandomInt4();
		if (CheckTool.isNullOrEmpty(code)) {
			return null;
		}

		CommonRequest request = new CommonRequest();
		request.setMethod(MethodType.POST);
		request.setDomain("dysmsapi.aliyuncs.com");
		request.setVersion("2017-05-25");
		request.setAction("SendSms");
		request.putQueryParameter("RegionId", AliyunConfig.regionIdSms);
		request.putQueryParameter("PhoneNumbers", phone);
		request.putQueryParameter("SignName", AliyunConfig.signNameSms);
		request.putQueryParameter("TemplateCode", AliyunConfig.templateCodeSms);
		request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
		try {
			CommonResponse response = iAcsClient.getCommonResponse(request);

			AliyunSms smsAliyun = (AliyunSms) StringTool.jsonToObject(response.getData(), AliyunSms.class);
			if ("OK".equals(smsAliyun.getCode())) {
				return code;
			} else if (smsAliyun.getCode().contains("LIMIT")) {
				return "-1";
			} else {
				return null;
			}

		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return null;

	}

	//
	@Override
	public boolean downFileGet(String uri, String localUri) throws Exception {
		// new一个URL对象
		URL url = new URL(uri);
		// 打开链接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置请求方式为"GET"
		conn.setRequestMethod("GET");
		// 超时响应时间为5秒
		conn.setConnectTimeout(5 * 1000);
		// 通过输入流获取图片数据
		InputStream inStream = conn.getInputStream();
		// 得到图片的二进制数据，以二进制封装得到数据，具有通用性
		byte[] data = readInputStream(inStream);
		// new一个文件对象用来保存图片，默认保存当前工程根目录
		File imageFile = new File(localUri);
		// 创建输出流
		FileOutputStream outStream = new FileOutputStream(imageFile);
		// 写入数据
		outStream.write(data);
		// 关闭输出流
		outStream.close();

		return true;
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}

	//通知崩溃
	@Override
	public void sendBugNotice() {

		String phone = "13522347280";
		String code = "1111";

		CommonRequest request = new CommonRequest();
		// request.setProtocol(ProtocolType.HTTPS);
		request.setMethod(MethodType.POST);
		request.setDomain("dysmsapi.aliyuncs.com");
		request.setVersion("2017-05-25");
		request.setAction("SendSms");
		request.putQueryParameter("RegionId", AliyunConfig.regionIdSms);
		request.putQueryParameter("PhoneNumbers", phone);
		request.putQueryParameter("SignName", AliyunConfig.signNameSms);
		request.putQueryParameter("TemplateCode", AliyunConfig.templateCodeSms);
		request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
		try {
			CommonResponse response = iAcsClient.getCommonResponse(request);

			AliyunSms smsAliyun = (AliyunSms) StringTool.jsonToObject(response.getData(), AliyunSms.class);
			if ("OK".equals(smsAliyun.getCode())) {
			} else {
			}

		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}

	}

}
