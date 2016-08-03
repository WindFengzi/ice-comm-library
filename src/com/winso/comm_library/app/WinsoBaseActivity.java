package com.winso.comm_library.app;




import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 应用程序Activity的基类
 * 
 * @author ericgoo
 * @version 1.0
 * @created 2014-11-11
 */
public class WinsoBaseActivity extends Activity {

	// 是否允许全屏
	private boolean allowFullScreen = true;

	// 是否允许销毁
	private boolean allowDestroy = true;
	//public WinsoBaseAppContext appContext;
	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		
		super.onCreate(savedInstanceState);
		allowFullScreen = true;
		// 添加Activity到堆栈
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // 隐藏软件键盘输入。
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 无标题栏。
		WinsoBaseAppManager.getAppManager().addActivity(this);

		//appContext = (WinsoBaseAppContext) getApplication();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 结束Activity&从堆栈中移除
		WinsoBaseAppManager.getAppManager().finishActivity(this);
	}

	public boolean isAllowFullScreen() {
		return allowFullScreen;
	}

	protected void onPause() {
		super.onPause();

	}


	/**
	 * 设置是否可以全屏
	 * 
	 * @param allowFullScreen
	 */
	public void setAllowFullScreen(boolean allowFullScreen) {
		this.allowFullScreen = allowFullScreen;
	}

	public void setAllowDestroy(boolean allowDestroy) {
		this.allowDestroy = allowDestroy;
	}

	public void setAllowDestroy(boolean allowDestroy, View view) {
		this.allowDestroy = allowDestroy;
		this.view = view;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && view != null) {
			view.onKeyDown(keyCode, event);
			if (!allowDestroy) {
				return false;
			}
		} 

		return super.onKeyDown(keyCode, event);
	}

	public void setButtonText(int iID, String sText) {
		Button bt = (Button) findViewById(iID);
		if (bt != null) {
			bt.setText(sText);
		}

	}

	public void setEditText(int iID, String sText) {
		EditText bt = (EditText) findViewById(iID);
		if (bt != null) {
			bt.setText(sText);
		}

	}

	public void setTextViewText(int iID, String sText) {
		TextView tx = (TextView) findViewById(iID);
		if (tx != null) {
			tx.setText(sText);
		}

	}

	public String getButtonText(int iID) {
		Button bt = (Button) findViewById(iID);
		if (bt != null) {
			return bt.getText().toString();
		}

		return "";
	}

	public String getEditText(int iID) {
		EditText bt = (EditText) findViewById(iID);
		if (bt != null) {
			return bt.getText().toString();
		}

		return "";
	}

	public String getTextViewText(int iID) {
		TextView bt = (TextView) findViewById(iID);
		if (bt != null) {
			return bt.getText().toString();
		}

		return "";
	}

}
