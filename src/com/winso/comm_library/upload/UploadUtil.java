package com.winso.comm_library.upload;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * 上传工具类
 */
public class UploadUtil {

	/**
	 * 安卓端日志打印标签名。
	 */
	public static final String LOG_TAG = "uploadFile";
	/**
	 * 上传服务接口。
	 */
	public static String UPLOAD_URL = "http://www.myxui.com:8080/upfile/upload.do";
	// public static String UPLOAD_URL =
	// "http://192.168.118.142:8080/upfile/upload.do";

	private static final int TIME_OUT = 6 * 1000; // 超时时间
	private static final String CHARSET = "utf-8"; // 设置编码
	private static final long MAX_SIZE = 512; // 最大上传文件大小，单位 KB。
	public String sResponeText=""; // 结果值
	public boolean bStop = false;

	/**
	 * android上传文件到服务器
	 * 
	 * @param file
	 *            需要上传的文件
	 * @param RequestURL
	 *            请求的rul
	 * @return 返回响应的内容
	 */
	public String uploadFile(UploadTask post, File file, String RequestURL) {

		sResponeText = "";
		// String result = null;
		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // 内容类型
		if (file == null)
			return "";
		

		try {
			URL url = new URL(RequestURL);
			System.setProperty("sun.net.client.defaultConnectTimeout", "6000");
			System.setProperty("sun.net.client.defaultReadTimeout", "6000");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流

			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", CHARSET); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);

			StringBuffer sb = new StringBuffer();

			// int fixedLength = (int) dos.getChannel().size();

			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINE_END);
			/**
			 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
			 * filename是文件的名字，包含后缀名的 比如:abc.png
			 */
			sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""
					+ file.getName() + "\"" + LINE_END);
			sb.append("Content-Type: application/octet-stream; charset="
					+ CHARSET + LINE_END);
			sb.append(LINE_END);

			InputStream is = new FileInputStream(file);
			int total = is.available(); // 文件大小。

			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
					.getBytes();
			conn.setFixedLengthStreamingMode(sb.length() + total
					+ end_data.length + LINE_END.length());

			if (file != null) {
				/**
				 * 当文件不为空，把文件包装并且上传
				 */
				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());

				dos.write(sb.toString().getBytes());
				// File parsedFile = parseUploadFile(file);

				byte[] bytes = new byte[1024 * 8];
				int len = 0;
				int readed = 0;
				while ((len = is.read(bytes)) != -1) {
					dos.write(bytes, 0, len);
					readed += len;
					post.publishProgress(readed * 100 / total); // 进度通知（百分比）。

					// 如果需要停止，则退出
					if (bStop) {
						bStop = false;
						return "";
					}
				}

				is.close();
				dos.write(LINE_END.getBytes());

				dos.write(end_data);
				dos.flush();
				/**
				 * 获取响应码 200=成功 当响应成功，获取响应的流
				 */
				int res = conn.getResponseCode();

				Log.e(LOG_TAG, "response code:" + res);
				Log.e(LOG_TAG, "request success");

				InputStream input = conn.getInputStream();
				StringBuffer sb1 = new StringBuffer();
				int ss;
				while ((ss = input.read()) != -1) {
					sb1.append((char) ss);
				}
				sResponeText = sb1.toString();
				Log.i("aaaa", "result : " + sResponeText);

				// 如果解析过的图片与最初的图片地址不同，表示是新创建了的，所以再清理一下。
				// String fileAbsolutePath = file.getAbsolutePath();
				// String parsedFileAbsolutePath = parsedFile.getAbsolutePath();
				// if (!fileAbsolutePath.equals(parsedFileAbsolutePath)) {
				// parsedFile.delete();
				// }
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(LOG_TAG, "error ioexception : " + e.toString());
			e.printStackTrace();
		}
		bStop = false;
		return sResponeText;
	}

	/**
	 * 解析上传的文件，如果是图片文件，检测上传大小，超出自动生成新缩放过的文件。 非 png、jpg 文件不处理。
	 * 
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unused")
	private File parseUploadFile(File file) {

		InputStream in = null;

		try {
			String filename = file.getName().toLowerCase(Locale.CHINA);
			// 对 jpg、png 图片大小检测，并缩放。
			if (filename.indexOf(".png") > 0 || filename.indexOf(".jpg") > 0) {
				in = new FileInputStream(file);
				String fileAbsolutePath = file.getAbsolutePath();
				long available = in.available();
				long maxSize = MAX_SIZE * 1024;

				if (available > maxSize) {
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
					// 获取这个图片的宽和高
					Bitmap bitmap = BitmapFactory.decodeFile(fileAbsolutePath,
							options); // 此时返回bm为空
					options.inJustDecodeBounds = false;
					options.inSampleSize = (int) ((float) available / (float) maxSize); // 计算缩放到最大尺寸的比例。
					// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
					bitmap = BitmapFactory
							.decodeFile(fileAbsolutePath, options);

					// 创建新文件，在文件名后加(-c)，之后该文件可能会被清理。
					int lastPointIndex = fileAbsolutePath.lastIndexOf(".");
					String newFilename = fileAbsolutePath.substring(0,
							lastPointIndex)
							+ "-c"
							+ fileAbsolutePath.substring(lastPointIndex);
					File newFile = new File(newFilename);
					FileOutputStream out = new FileOutputStream(newFile);
					if (bitmap
							.compress(
									filename.indexOf(".jpg") > 0 ? Bitmap.CompressFormat.JPEG
											: Bitmap.CompressFormat.PNG, 100,
									out)) {
						out.flush();
						out.close();
					}

					file = newFile;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return file;
	}
}