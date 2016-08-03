package com.winso.comm_library.app;


import com.winso.comm_library.app.TNAppManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TNBaseActivity extends Activity implements OnClickListener {

	// 是否允许全屏
	private boolean allowFullScreen = true;

	// 是否允许�?�?
	private boolean allowDestroy = true;
	public TNAppContext appContext;
	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);

		super.onCreate(savedInstanceState);
		allowFullScreen = true;
		// 添加Activity到堆�?
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // 隐藏软件键盘输入
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 无标题栏
		TNAppManager.getAppManager().addActivity(this);

		appContext = (TNAppContext) getApplication();
	}

	@SuppressWarnings("unchecked")
	public final <E extends View> E getView(int id) {
		try {
			return (E) findViewById(id);
		} catch (ClassCastException ex) {
			// Log.e(TAG, "Could not cast View to concrete class.", ex);
			throw ex;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 结束Activity&从堆栈中移除
		TNAppManager.getAppManager().finishActivity(this);
	}

	public boolean isAllowFullScreen() {
		return allowFullScreen;
	}

	protected void onPause() {
		super.onPause();

	}

	// 设置标题
	public void setTitle(int iID, String sTitle) {
		TextView vTitle = (TextView) findViewById(iID);

		if (vTitle == null)
			return;

		vTitle.setText(sTitle);
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

	public void setClick(int iID)
	{
		View v =  findViewById(iID);
		
		if ( v != null )
			v.setOnClickListener(this);
	}
	
	
	
	//通过名称找到图片ID
	public int getPic(String pid) {
		
		int resID = getResources().getIdentifier(pid, "drawable", this.getPackageName()); 
		
		return resID;
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
