package com.winso.comm_library.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class UploadHandler {

	private static final String CHARSET_UTF8 = "UTF-8";
	private static final int EXECUTION_COUNT = 3;
	private static final int TIMEOUT = 10 * 1000;

	/** 重试请求响应处理句柄。 */
	private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {

		public boolean retryRequest(IOException exception, int executionCount,
				HttpContext context) {
			if (executionCount >= EXECUTION_COUNT) {
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				return false;
			}

			HttpRequest request = (HttpRequest) context
					.getAttribute(ExecutionContext.HTTP_REQUEST);
			boolean idempotent = request instanceof HttpEntityEnclosingRequest;
			if (!idempotent) {
				return true;
			}

			return false;
		}
	};

	/** 请求响应处理句柄。 */
	private static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

		public String handleResponse(HttpResponse response) throws IOException {
			if (response.getStatusLine().getStatusCode() != 200) {
				return null;
			}

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String charset = EntityUtils.getContentCharSet(entity) == null ? CHARSET_UTF8
						: EntityUtils.getContentCharSet(entity);
				return new String(EntityUtils.toByteArray(entity), charset);
			} else {
				return null;
			}
		}
	};

	/**
	 * POST 请求操作。
	 * 
	 * @param url
	 *            要请求的地址。
	 * @param params
	 *            参数。
	 * @param file
	 *            可能是图片文件。
	 * @param charset
	 *            编码。
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> params,
			File file, String charset) throws Exception {

		if (url == null) {
			return null;
		}

		DefaultHttpClient httpClient = getDefaultHttpClient(charset); // 获取一个
																		// HTTL
																		// 请求类。
		HttpPost post = new HttpPost(url); // 实例化一个 POST 请求类。
		post.setHeader("Content-Type", "multipart/form-data;");

		// 转换参数编码。
		try {
			List<NameValuePair> qparams = getParamsList(params);
			if (qparams != null && qparams.size() > 0) {
				if (charset == null) {
					new UrlEncodedFormEntity(qparams);
				} else {
					new UrlEncodedFormEntity(qparams, "UTF-8");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 设置请求参数。
		if (params != null) {
			post.setEntity(new StringEntity(params.get("json"), "UTF-8"));
		}

		// 读取要上传的文件。
		if (null != file) {
			MultipartEntity fileEntity = new MultipartEntity();
			UploadFile uploadFile = new UploadFile("file", file);
			fileEntity.addPart(uploadFile.getName(), uploadFile);
			post.setEntity(fileEntity);
		}

		String responseStr = null;
		try {
			responseStr = httpClient.execute(post, responseHandler); // 执行请求。
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			abortConnection(post, httpClient); // 最后断开请求。
		}

		return responseStr;
	}

	/**
	 * 获取一个通用的 HttpClient 对象，默认已设置好页头等。
	 * 
	 * @param charset
	 *            编码。
	 * @return
	 */
	private static DefaultHttpClient getDefaultHttpClient(final String charset) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
		httpclient.getParams().setParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		httpclient.getParams().setParameter(
				CoreProtocolPNames.HTTP_CONTENT_CHARSET,
				charset == null ? CHARSET_UTF8 : charset);
		httpclient.setHttpRequestRetryHandler(requestRetryHandler);
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, TIMEOUT);
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				TIMEOUT);

		return httpclient;
	}

	/**
	 * 断开链接操作。
	 * 
	 * @param httpRequestBase
	 * @param httpClient
	 * 
	 * @return
	 */
	private static void abortConnection(final HttpRequestBase httpRequestBase,
			final HttpClient httpClient) {

		if (httpRequestBase != null) {
			httpRequestBase.abort();
		}

		if (httpClient != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 参数转换。
	 * 
	 * @param paramsMap
	 *            参数键值对。
	 * @return
	 */
	private static List<NameValuePair> getParamsList(
			Map<String, String> paramsMap) {

		if (paramsMap == null || paramsMap.size() == 0) { // 参数有效性检测。
			return null;
		}

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> map : paramsMap.entrySet()) {
			params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
		}

		return params;
	}
}
