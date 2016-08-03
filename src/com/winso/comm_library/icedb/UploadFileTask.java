package com.winso.comm_library.icedb;

import java.io.File;
import java.io.FileInputStream;
import org.apache.http.HttpResponse;

import com.winso.comm_library.CallbackInterface;
import com.winso.comm_library.FileUtils;
import com.winso.comm_library.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

/**
 * 异步上传文件任务类。
 */
public class UploadFileTask extends AsyncTask<HttpResponse, Integer, String> {

	public ProgressDialog pd;
	private Activity context;
	public String sResponeFile;
	private CallbackInterface mCall;
	private String sShowMessage;
	ICEDBUtil mDB;
	
	private int iSendBlockSize = 10240;
	String sInSrcFile="",sRemotePath="";
	private boolean mIsProcessing = false;
	
	public UploadFileTask(ICEDBUtil db,Activity context, String sSrcFile,String sRemote, CallbackInterface bc) 
	{
		sRemotePath = sRemote;
		mDB = db;
		sShowMessage = context.getResources()
				.getString(R.string.s_uploing_noew);
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
					public void onClick(DialogInterface dialog, int which) 
					{
						cancel(true);
						
						mCall.cancel("取消");
						mIsProcessing = false;
					}
				});
		pd.show();
	}

//	@Override
//	public void publishProgress(int process) {
//		super.publishProgress(process);
//	}
	

	
	@Override
	protected String doInBackground(HttpResponse... arg0) {

		if ( mIsProcessing ) return "";
		if  ( !mDB.isLogin() ) return "";
		
		mIsProcessing = true;
		// 进入上传发起管理类。
		int iCacheSize = iSendBlockSize;
		int iRetryTimes = 3000;
		try {

			//获取服务器全局的名称
			sResponeFile = mDB.getID("pic_id");
			
			//String sFileName = FileUtils.getFileName(sInSrcFile);

			File file=new File(sInSrcFile);

			
			if( !file.exists() ) return "fail";
			String prefix=sInSrcFile.substring(sInSrcFile.lastIndexOf(".")+1);
			
			sResponeFile +="." ;
			sResponeFile += prefix;
			
			
			//改名称
			String sDestFile = file.getParent()+"/"+sResponeFile;
			FileUtils.reNamePath(sInSrcFile,sDestFile);
			
			
				
			FileInputStream fin = new FileInputStream(sDestFile);
			int fSize = fin.available();

			int iRead = 0;

			int iCurPos = 0;

			byte[] buffer = new byte[iCacheSize];

			// fin.read(buffer);

			int iCurRetry = 0;

			while ((iRead = fin.read(buffer)) > 0) {
				
				
				int iWrite = mDB.m_iceObject.UploadFile(sRemotePath + "/"
						+ sResponeFile, iCurPos,iRead, buffer);

				if (iWrite <= 0) {
					iCurRetry++;

					if (iCurRetry >= iRetryTimes)
						break;

					if (iWrite == -3) {
						iCurPos = 0;

					}

					continue;
				}

				iCurPos += iRead;
				
				float iv = ((float)iCurPos/fSize)*100;
				
				//更新进度
				publishProgress((int)iv);
			}
			
			fin.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
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