package com.test.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileTool {

	public static String upload(String filePath, MultipartFile imageFile) {

		String realPath = null;

		if (!imageFile.isEmpty()) {

			String originalName = imageFile.getOriginalFilename();
			String uuidName = UUID.randomUUID().toString();
			String newImageName = uuidName + originalName.substring(originalName.lastIndexOf("."));

			realPath = filePath + newImageName;
			File file = new File(realPath);

			File fileParent = file.getParentFile();
			if (!fileParent.exists()) {
				fileParent.mkdir();
			}

			try {
				imageFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return realPath;

	}

	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (String element : tempList) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + element);
			} else {
				temp = new File(path + File.separator + element);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + element);// 先删除文件夹里面的文件
				delFolder(path + "/" + element);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	public static List<String> readfilePath(String filepath) throws FileNotFoundException, IOException {
		List<String> filePaths = new ArrayList<String>();
		File file = new File(filepath);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			for (String element : filelist) {
				File readfile = new File(filepath + "\\" + element);
				if (!readfile.isDirectory()) {
					filePaths.add(readfile.getAbsolutePath());
				}
			}

		}
		return filePaths;
	}

	public static byte[] getBytesFromFile(File f) {
		if (f == null) {
			return null;
		}
		try {
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1) {
				out.write(b, 0, n);
			}
			stream.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
		}
		return null;
	}

	public static void delFile(String absoluteUri) {
		File file = new File(absoluteUri);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
	}

	public static String readToString(String filePath) {
		String encoding = "UTF-8";
		File file = new File(filePath);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			System.err.println("The OS does not support " + encoding);
			e.printStackTrace();
			return null;
		}
	}

	public static boolean writeFile(String data, String uri) {

		try {
			File file = new File(uri);
			Writer out = new FileWriter(file);
			out.write(data);
			out.close();
			return true;
		} catch (Exception e) {
		}

		return false;
	}

}
