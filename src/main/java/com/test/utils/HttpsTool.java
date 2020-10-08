package com.test.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsTool {

	private static final class DefaultTrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}

	private static HttpsURLConnection getHttpsURLConnection(String uri, String method) throws IOException {
		SSLContext ctx = null;
		try {
			ctx = SSLContext.getInstance("TLS");
			ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		SSLSocketFactory ssf = ctx.getSocketFactory();

		URL url = new URL(uri);
		HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
		httpsConn.setSSLSocketFactory(ssf);
		httpsConn.setHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		});
		httpsConn.setRequestMethod(method);
		httpsConn.setDoInput(true);
		httpsConn.setDoOutput(true);
		return httpsConn;
	}

	private static byte[] getBytesFromStream(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] kb = new byte[1024];
		int len;
		while ((len = is.read(kb)) != -1) {
			baos.write(kb, 0, len);
		}
		byte[] bytes = baos.toByteArray();
		baos.close();
		is.close();
		return bytes;
	}

	private static void setBytesToStream(OutputStream os, byte[] bytes) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		byte[] kb = new byte[1024];
		int len;
		while ((len = bais.read(kb)) != -1) {
			os.write(kb, 0, len);
		}
		os.flush();
		os.close();
		bais.close();
	}

	public static String doGet(String uri) {
		try {
			HttpsURLConnection httpsConn = getHttpsURLConnection(uri, "GET");
			byte[] bytes = getBytesFromStream(httpsConn.getInputStream());
			return new String(bytes, "utf-8");
		} catch (Exception e) {
		}
		return "";

	}

	public static String doPost(String uri, String data) {
		try {
			HttpsURLConnection httpsConn = getHttpsURLConnection(uri, "POST");
			setBytesToStream(httpsConn.getOutputStream(), data.getBytes());
			byte[] bytes = getBytesFromStream(httpsConn.getInputStream());
			return new String(bytes, "utf-8");
		} catch (Exception e) {
		}
		return "";

	}
}