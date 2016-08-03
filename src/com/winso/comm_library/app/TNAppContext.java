package com.winso.comm_library.app;

import android.location.LocationManager;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.winso.comm_library.*;

import com.winso.comm_library.icedb.SelectHelp;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

/**
 * 应用程序业务访问类，用于访问各类业务组件 新调整
 * 
 * @author eric goo
 * @version 1.0
 * @created 2015-09-10
 */

public class TNAppContext extends Application {

	// 版本发布的目
	public static String mSUpgradePath = "release"; // 版本发布的服务器目录

	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x03;
	public static final String STORE_PIC_PATH = "inteactive_pic";

	public static final String S_REMOTE_PIC_PATH = "/upfile/upload/";
	//public int CENT_PORT = 8842;

	public boolean bNeedRefreshMainReview = false;
	//

	public String mUserName = "admin";
	public String mUserRight;

	public SelectHelp mHelpRight = new SelectHelp(); // 用户权限列表

	private Map<String, String> mSQLMap = new HashMap<String, String>();

	public String mLongitude = ""; // 经度
	public String mLatitude = ""; // 纬度

	public String DEFAULT_DB_SAVE_PATH() {
		return getPictureDirectory();
	}

	public LocationManager mGPS = null;

	public String getGPS() {

		return "";

	}

	// 用于保存配置信息
	TinyDB mTinydb = null;

	//public ICEDBUtil m_ice = new ICEDBUtil();

	public TNAppContext() {
		super();

	}

	public void setServer(String sServer,int iPort)
	{
		setCookie("cent_ip", sServer);
		setCookie("cent_port", String.valueOf(iPort) );

	}
	public void initApp() {

		mTinydb = new TinyDB(this);

		AssetsDatabaseManager.initManager(this);

		

	}
	public boolean isCheckUp() {
		if (Integer.parseInt(getCookie("is_check_up")) == 0) {
			return false;
		} 
		return true;
	}
	public void convertSQLMap(String sContent) {
		if (sContent == null)
			return;

		mSQLMap.clear();
		String[] vsAll = sContent.split(";");

		for (int i = 0; i < vsAll.length; i++) {
			String[] vSub = vsAll[i].split("@");

			if (vSub.length >= 2) {

				mSQLMap.put(vSub[0].trim(), vSub[1]);
			}
		}
	}

	public String getSQL(String sType) {
		return mSQLMap.get(sType);
	}


	/**
	 * 设置Cookie，可以持续保存�??
	 * 
	 * @return
	 */
	public void setCookie(String k, String v) {
		mTinydb.putString(k, v);
	}

	public void setCookieInt(String k, int v) {
		mTinydb.putInt(k, v);
	}

	public void setCookieBoolean(String k, boolean v) {
		mTinydb.putBoolean(k, v);
	}

	/**
	 * 查询 Cookie
	 * 
	 * @return 返回查到的Cookie
	 */
	public String getCookie(String k) {
		return mTinydb.getString(k);
	}

	public int getCookieInt(String k) {
		return mTinydb.getInt(k);
	}

	public boolean getCookieBoolean(String k) {
		return mTinydb.getBoolean(k);
	}

	/**
	 * �?测网络是否可�?
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 获取当前网络类型
	 * 
	 * @return 0：没有网�? 1：WIFI网络 2：WAP网络 3：NET网络
	 */
	@SuppressLint("DefaultLocale")
	public int getNetworkType() {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if (!StringUtils.isEmpty(extraInfo)) {
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}

	/**
	 * 判断当前版本是否兼容目标版本的方�?
	 * 
	 * @param VersionCode
	 * @return
	 */
	public static boolean isMethodsCompat(int VersionCode) {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		return currentVersion >= VersionCode;
	}

	/**
	 * 获取App安装包信�?
	 * 
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	public static boolean isSDAviable() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// sd card 可用
			return true;

		}

		return false;
	}

	public String getPictureDirectory() {

		if (isSDAviable()) {
			File directory = null;
			directory = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath()
					+ File.separator
					+ TNAppContext.STORE_PIC_PATH);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			return directory.getAbsolutePath() + "/";
		}

		// 只能存在本地文件�?
		String sDest = getApplicationContext().getFilesDir().getPath()
				+ File.separator + TNAppContext.STORE_PIC_PATH + File.separator;

		File directory = new File(sDest);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		return sDest;

	}

	// ///////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////
	
	public static void popMsg(Context ac, String title, String msg) {

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

}
