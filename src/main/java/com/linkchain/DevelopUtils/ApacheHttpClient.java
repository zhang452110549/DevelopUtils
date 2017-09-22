package com.linkchain.DevelopUtils;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class ApacheHttpClient {

	private static PoolingHttpClientConnectionManager connectionManager = null;
	private static HttpClientBuilder httpBulder = null;
	private static RequestConfig requestConfig = null;

	private static int MAXCONNECTION = 10;

	private static int DEFAULTMAXCONNECTION = 5;

	// 连接池方式
	static {

		// 设置超时时间
		requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(5000)
				.setConnectionRequestTimeout(5000).build();
		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("SSLv3");

			// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
			X509TrustManager trustManager = new X509TrustManager() {
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
						String paramString) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
						String paramString) throws CertificateException {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			sslContext.init(null, new TrustManager[] { trustManager }, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslContext)).build();
		// 连接池
		connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		connectionManager.setMaxTotal(MAXCONNECTION);
		connectionManager.setDefaultMaxPerRoute(DEFAULTMAXCONNECTION);
		httpBulder = HttpClients.custom();
		httpBulder.setConnectionManager(connectionManager);
	}

	public static CloseableHttpClient getConnection() {
		return httpBulder.build();
	}

	public static String formPost(String url, Map<String, String> paramMap) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		for (String key : paramMap.keySet()) {
			parameters.add(new BasicNameValuePair(key, paramMap.get(key)));
		}
		HttpPost httpPost = new HttpPost(url);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, Consts.UTF_8);
		httpPost.setEntity(entity);
		httpPost.setConfig(requestConfig);

		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpclient = getConnection();
			response = httpclient.execute(httpPost);
			String respData = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
			// 返回数据
			return respData;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static String jsonPost(String url, String json) {
		HttpPost httpPost = new HttpPost(url);
		StringEntity entity = new StringEntity(json, Consts.UTF_8);// 解决中文乱码问题
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		httpPost.setConfig(requestConfig);

		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpclient = getConnection();
			response = httpclient.execute(httpPost);
			String respData = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
			// 返回数据
			return respData;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static String get(String url) {
		HttpGet httpGet = new HttpGet(url);

		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpclient = getConnection();
			response = httpclient.execute(httpGet);
			String respData = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
			// 返回数据
			return respData;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

}
