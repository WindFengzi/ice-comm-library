package com.winso.comm_library.upload;

import java.io.File;

import org.apache.http.HttpResponse;

import com.winso.comm_library.CallbackInterface;
import com.winso.comm_library.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

/**
 * 异步上传文件任务类。
 */
public class UploadTask extends AsyncTask<HttpResponse, Integer, String> {

	public ProgressDialog pd;
	private Activity context;
	private File uploadFile;
	public String sResponeFile;
	private CallbackInterface mCall;
	private String sShowMessage;
	
	private boolean mIsProcessing = false;

	public String sUpgradeURL; // 上传的upload.do的路径
	public UploadUtil pUploadUtil = new UploadUtil();

	public UploadTask(Activity context, File file, CallbackInterface bc,
			String sURL) {
		sShowMessage = context.getResources()
				.getString(R.string.s_uploing_noew);
		this.context = context;
		uploadFile = file;
		mCall = bc;
		sUpgradeURL = sURL;
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
						pUploadUtil.bStop = true;
						while (pUploadUtil.bStop) {
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						mCall.cancel("取消");
					}
				});
		pd.show();
	}

	public void publishProgress(int process) {
		super.publishProgress(process);
	}

	@Override
	protected String doInBackground(HttpResponse... arg0) {

		if ( mIsProcessing ) return "";
		
		mIsProcessing = true;
		// 进入上传发起管理类。
		
		sResponeFile = pUploadUtil.uploadFile(this, uploadFile, sUpgradeURL);
		return sResponeFile;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		pd.setProgress((int) (progress[0]));
	}

	@Override
	protected void onPostExecute(String result) {

		// 图片文件，展示缩略图。
		// String filePath =
		// uploadFile.getAbsolutePath().toLowerCase(Locale.CHINA);
		// if (filePath.lastIndexOf(".jpg") > 0 || filePath.lastIndexOf(".png")
		// > 0) {
		// Bitmap bitmap = BitmapFactory.decodeFile(filePath);
		// UploadFileInfoActivity.imageView.setImageBitmap(bitmap);
		// }

		// UploadFileInfoActivity.textView.setText(result);
		mIsProcessing = false;
		pd.dismiss();

		mCall.func(sResponeFile);
	}
}