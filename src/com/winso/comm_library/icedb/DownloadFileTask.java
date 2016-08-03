package com.winso.comm_library.icedb;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.apache.http.HttpResponse;

import com.winso.comm_library.CallbackInterface;
import com.winso.comm_library.FileUtils;
import com.winso.comm_library.R;

import Ice.IntHolder;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

/**
 * 异步上传文件任务类。
 */
public class DownloadFileTask extends AsyncTask<HttpResponse, Integer, String> {

	public ProgressDialog pd;
	private Activity context;
	public String sResponeFile;
	private CallbackInterface mCall;
	private String sShowMessage;
	ICEDBUtil mDB;
	boolean  mbCancelFlag=false;
	private int iSendBlockSize = 10240;
	String sInSrcFile = "", sSaveLocalPath = "";
	private boolean mIsProcessing = false;

	public DownloadFileTask(ICEDBUtil db, Activity context, String sSrcFile,
			String sLocalPath, CallbackInterface bc) {
		sSaveLocalPath = sLocalPath;
		mDB = db;
		sShowMessage = context.getResources().getString(
				R.string.s_download_noew);
		this.context = context;
		mCall = bc;
		sInSrcFile = sSrcFile;
	}

	void setShowMessage(String sMsg) {
		sShowMessage = sMsg;
	}

	@Override
	protected void onPreExecute() {

		// UploadFileInfoActivity.textView.setText(""); // 每次上传时，先清空原先的信息。

		pd = null;
		pd = new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage(sShowMessage);
		pd.setCancelable(true);
		pd.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						cancel(true);
						mbCancelFlag = true;

						sResponeFile = "";
						mCall.cancel("取消");
						
						mIsProcessing = false;
					}
				});
		pd.show();
	}

	// @Override
	// public void publishProgress(int process) {
	// super.publishProgress(process);
	// }

	@SuppressWarnings("resource")
	@Override
	protected String doInBackground(HttpResponse... arg0) {

		sResponeFile = "";
		
		if (mIsProcessing)
			return "";
		if (!mDB.isLogin())
			return "";

		mIsProcessing = true;
		// 进入上传发起管理类。
		int iCacheSize = iSendBlockSize;
		int iRetryTimes = 3000;
		int iCurPos = 0;

		// 获取服务器全局的名称
		// sResponeFile = mDB.getID("pic_id");
		byte[] buffer = null;
		String sDestFile = sSaveLocalPath + "/"
				+ FileUtils.getFileName(sInSrcFile)+".tmp";
		
		FileUtils.deleteFile(sDestFile);
		
		String sRealFile = sSaveLocalPath + "/"
				+ FileUtils.getFileName(sInSrcFile);
		
		FileOutputStream fos = null;

		// 获取文件信息
		SelectHelp help = mDB.getFileInfo(sInSrcFile);
		if (help.size() <= 0)
			return "";

		long fSize = Integer.valueOf(help.valueStringByName(0, "size"));

		if ( fSize < 0 ) return "";
		
		try {

			fos = new FileOutputStream(sDestFile);

			int iCurTry = 0;

			// 远程下载
			IntHolder iReadSize = new IntHolder();

			while (true) {
				if ( mbCancelFlag  ) 
					return "";
				
				iReadSize.value = 0;
				buffer = mDB.getFileCompressed(sInSrcFile, iCurPos, iCacheSize,
						iReadSize);

				if ( buffer == null )
					continue;
				
				if (iReadSize.value < 0) {

					iCurTry++;

					if (iCurTry >= iRetryTimes) {
						break;
					}
					continue;
				}

				if (buffer.length <= 0) {
					break;
				}

				fos.write(buffer);
				iCurTry = 0;

				iCurPos += buffer.length;

				float iv = ((float) iCurPos / fSize) * 100;
				// 更新进度
				publishProgress((int) iv);

			}

			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return "";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
		
		sResponeFile = sRealFile;

		FileUtils.reNamePath(sDestFile, sRealFile);
		return "success";
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		pd.setProgress((int) (progress[0]));
	}

	@Override
	protected void onPostExecute(String result) {
		mIsProcessing = false;
		pd.dismiss();

		mCall.func(sResponeFile);
	}
}