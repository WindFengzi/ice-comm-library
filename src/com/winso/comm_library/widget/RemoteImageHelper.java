package com.winso.comm_library.widget;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.winso.comm_library.R;
import com.winso.comm_library.EasyLog;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

/**
 * A helper class facilitates loading a remote image into a given ListView.
 * http://www.cnblogs.com/bjzhanghao/archive/2012/11/11/2764970.html
 * 
 * @author zhanghao
 * 
 */
public class RemoteImageHelper {

	private final Map<String, Drawable> cache = new HashMap<String, Drawable>();
	private String mSavePath;
	public void loadImage(final ImageView imageView, final String urlString,String sSavePath) {
		loadImage(imageView, urlString, mSavePath,true);
	}

	@SuppressLint("HandlerLeak") public void loadImage(final ImageView imageView, final String urlString,
			String sSavePath,boolean useCache) {
		mSavePath = sSavePath; 
		if (useCache && cache.containsKey(urlString)) {
			imageView.setImageDrawable(cache.get(urlString));
		}

		// You may want to show a "Loading" image here
		imageView.setImageResource(R.drawable.pic_is_loding);

		EasyLog.debug("Image url:" + urlString);

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message message) {
				imageView.setImageDrawable((Drawable) message.obj);
			}
		};

		Runnable runnable = new Runnable() {
			public void run() {
				Drawable drawable = null;
				try {
					InputStream is = download(urlString);
					drawable = Drawable.createFromStream(is, "src");
					
					//检查是否存在，不存在则创建
					drawableTofile(drawable,mSavePath);
					
					if (drawable != null) {
						cache.put(urlString, drawable);
					}
				} catch (Exception e) {
					EasyLog.error("Image download failed " + e.toString());
					// Show "download fail" image
					drawable = imageView.getResources().getDrawable(
							R.drawable.img_alert);
				}

				// Notify UI thread to show this image using Handler
				Message msg = handler.obtainMessage(1, drawable);
				handler.sendMessage(msg);
			}
		};
		new Thread(runnable).start();

	}
	public static void drawableTofile(Drawable drawable,String path){
		//Log.i(TAG, "drawableToFile:"+path);
		File file=new File(path);
		Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
		byte[] bitmapdata = bos.toByteArray();

		//write the bytes in file
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(bitmapdata);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		
	}
	/**
	 * Download image from given url. Make sure you have
	 * "android.permission.INTERNET" permission set in AndroidManifest.xml.
	 * 
	 * @param urlString
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private InputStream download(String urlString)
			throws MalformedURLException, IOException {
		InputStream inputStream = (InputStream) new URL(urlString).getContent();
		return inputStream;
	}
}
