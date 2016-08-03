package com.winso.comm_library.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import com.winso.comm_library.R;
import com.winso.comm_library.EasyLog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DownloadFile
{
	private static final int DOWN_NOSDCARD = 0;
	private static final int DOWN_UPDATE = 1;
	private static final int DOWN_OVER = 2;
	// 进度条
	private ProgressBar mProgress;
	// 显示下载数值
	private TextView mProgressText;
	// 进度值
	private int progress;
	// 下载线程
	private Thread downLoadThread;
	// 终止标记
	private boolean interceptFlag;

	// 下载包保存路径
	private String savePath = "";
	// apk保存完整路径
	private String apkFilePath = "";
	// 临时下载文件路径
	private String tmpFilePath = "";
	// 下载文件大小
	private String apkFileSize;
	// 已下载文件大小
	private String tmpFileSize;

	private Dialog downloadDialog;
	public String sDownloadURL;
	public String sDownloadFile;

	public String sHttpServer;
	public String sHttpPort;
	public Context mContext;

	private static DownloadFile downloadManager;
	public boolean isDownloading = false;
	
	
	public DownloadFile()
	{
		//mContext = ac;
	}
	/**
	 * 显示下载对话框
	 */
	private void showDownloadDialog()
	{
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("正在下载新版本");

		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.update_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		mProgressText = (TextView) v.findViewById(R.id.update_progress_text);

		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		builder.setOnCancelListener(new OnCancelListener()
		{
			@Override
			public void onCancel(DialogInterface dialog)
			{
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.setCanceledOnTouchOutside(false);
		downloadDialog.show();

		//downloadApk();
	}
	public static DownloadFile getDownloadManager()
	{
		if (downloadManager == null)
		{
			downloadManager = new DownloadFile();
		}
		downloadManager.interceptFlag = false;
		return downloadManager;
	}
	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				mProgressText.setText(tmpFileSize + "/" + apkFileSize);
				break;
			case DOWN_OVER:
				downloadDialog.dismiss();
				isDownloading = false;
				//installApk();
				break;
			case DOWN_NOSDCARD:
				downloadDialog.dismiss();
				Toast.makeText(mContext, "无法下载安装文件，请检查SD卡是否挂载", 3000).show();
				isDownloading = false;
				break;
			}
		};
	};

	private Runnable mdownRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			try
			{
				//Looper.prepare();
				
				
				isDownloading = true;
				
				String apkName =  sDownloadFile;
				String tmpApk =  sDownloadFile + ".tmp";
				// 判断是否挂载了SD卡
				//String storageState = Environment.getExternalStorageState();
				//if (storageState.equals(Environment.MEDIA_MOUNTED))
				{

					File file = new File(savePath);
					if (!file.exists())
					{
						file.mkdirs();
					}
					apkFilePath = savePath + apkName;
					tmpFilePath = savePath + tmpApk;
				}

				// 没有挂载SD卡，无法下载文件
				if (apkFilePath == null || apkFilePath == "")
				{
					mHandler.sendEmptyMessage(DOWN_NOSDCARD);
					isDownloading = false;
					return;
				}

				File ApkFile = new File(apkFilePath);

				// 是否已下载更新文件
				
				/*if (ApkFile.exists())
				{
					if (downloadDialog != null)
						downloadDialog.dismiss();
					// installApk();
					isDownloading = false;
					return;
				}*/
				

				// 输出临时下载文件
				File tmpFile = new File(tmpFilePath);
				EasyLog.info(tmpFilePath);
				
				FileOutputStream fos = new FileOutputStream(tmpFile);

				String apkUrl = "";
//				apkUrl = "http://";
//				apkUrl += sHttpServer;
//				apkUrl += ":";
//				apkUrl += sHttpPort;
//				apkUrl += "/upfile/release/";
				apkUrl += sDownloadURL;
				apkUrl += apkName;

				EasyLog.debug(apkUrl);

				URL url = new URL(apkUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();

				// 显示文件大小格式：2个小数点显示
				DecimalFormat df = new DecimalFormat("0.00");
				// 进度条下面显示的总文件大小
				apkFileSize = df.format((float) length / 1024 / 1024) + "MB";

				int count = 0;
				byte buf[] = new byte[1024];

				do
				{
					int numread = is.read(buf);
					count += numread;
					// 进度条下面显示的当前下载文件大小
					tmpFileSize = df.format((float) count / 1024 / 1024) + "MB";
					// 当前进度值
					progress = (int) (((float) count / length) * 100);
					// 更新进度
					mHandler.sendEmptyMessage(DOWN_UPDATE);
					if (numread <= 0)
					{
						// 下载完成 - 将临时下载文件转成APK文件
						if (tmpFile.renameTo(ApkFile))
						{
							// 通知安装
							mHandler.sendEmptyMessage(DOWN_OVER);
						}
						break;
					}
					fos.write(buf, 0, numread);
				} while (!interceptFlag);// 点击取消就停止下载

				fos.close();
				is.close();
			} catch (MalformedURLException e)
			{
				mHandler.sendEmptyMessage(DOWN_NOSDCARD);
				
				e.printStackTrace();
			} catch (IOException e)
			{
				mHandler.sendEmptyMessage(DOWN_NOSDCARD);
				e.printStackTrace();
			}

			isDownloading = false;
		}
		
	};

	
	/**
	 * 下载文件
	 * 
	 * @param url
	 */
	public void download(Context ac,String sURL,String sFile,String sSavePath)
	{
		if ( isDownloading ) return ;
		
		mContext =  ac;
		sDownloadURL = sURL;
		sDownloadFile = sFile;
		savePath = sSavePath;
		
		showDownloadDialog();
		
		downLoadThread = new Thread(mdownRunnable);
		downLoadThread.start();
	}
}
