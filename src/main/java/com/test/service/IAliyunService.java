package com.test.service;

import org.springframework.web.multipart.MultipartFile;

public interface IAliyunService {

	String uploadObject(MultipartFile file, String suffix);

	String uploadObjectByUri(String uri, String name);

	String send(String phone);

	void sendBugNotice();

	boolean downFileGet(String uri, String localUri) throws Exception;
}
