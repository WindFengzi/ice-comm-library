package com.winso.comm_library.app;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import com.winso.comm_library.widget.WinsoViewPicActivity;

import com.winso.comm_library.R;

public class WinsoBaseUIHelper {
	//

	
	public static void showMsg(Context ac, String title, String msg) {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(ac);

		alertDialog.setTitle(title);
		alertDialog.setIcon(R.drawable.ic_launcher);

		alertDialog.setMessage(msg);

		alertDialog.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});

		alertDialog.show();

	}

	
	/**
	 * 加载关于同步对话�?.
	 */
	public static void openViewPic(Context ct,String sPicPath) {
		Intent intent = new Intent(ct, WinsoViewPicActivity.class);
		
		intent.putExtra("pic_path", sPicPath);
		
		ct.startActivity(intent);
	}

	
}
