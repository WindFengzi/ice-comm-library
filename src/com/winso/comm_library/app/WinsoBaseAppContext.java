package com.winso.comm_library.app;

import android.location.LocationManager;
import java.io.File;
import java.util.Properties;

import com.winso.comm_library.app.WinsoBaseAppConfig;
import com.winso.comm_library.*;

import com.winso.comm_library.icedb.ICEDBUtil;
import com.winso.comm_library.icedb.SelectHelp;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

/**
 * 应用程序业务访问类，用于访问各类业务组件�?
 * 
 * @author eric goo
 * @version 1.0
 * @created 2015-01-20
 */

public class WinsoBaseAppContext extends Application {

	// 版本发布的目�?
	public static String mSUpgradePath = "release"; // 版本发布的服务器目录

	public  int NETTYPE_WIFI = 0x01;
	public  int NETTYPE_CMWAP = 0x02;
	public  int NETTYPE_CMNET = 0x03;
	public  String STORE_PIC_PATH = "inteactive_pic";

	public static final String S_REMOTE_PIC_PATH = "/upfile/upload/";
	public static final int CENT_PORT = 8862;
	
	//
	// 登录后更新当前

	public String mUserName = "admin";
	public String mUserRight;

	public SelectHelp mHelpRight = new SelectHelp(); // 用户权限列表

	//private Map<String, String> mValueMap = new HashMap<String, String>();
	// 用于存储key value的对应�??
	
	public String mLongitude = ""; // 经度
	public String mLatitude = ""; // 纬度

	public String DEFAULT_DB_SAVE_PATH() {
		// return getApplicationContext().getFilesDir().getPath() +
		// File.separator;
		return getPictureDirectory();
	}

	public LocationManager mGPS = null;

	public String getGPS() {

		return "";

	}

	// 用于保存配置信息
	TinyDB mTinydb = null;

	public ICEDBUtil m_ice = new ICEDBUtil();

	public WinsoBaseAppContext() {
		super();

	}

	public void initApp() {
		mTinydb = new TinyDB(this);

		AssetsDatabaseManager.initManager(this);

		if (getCookie("cent_ip").length() <= 1) {

			setCookie("cent_ip", "www.zjtouchnet.com");

		}

		
		if (getCookie("is_check_up").length() <= 0) {
			setCookie("is_check_up", "1");
		}

		// 设置默认查询时间
		// getCurDateTime

		setCookie("start_time", TimeZoneUtil.getDateByDay(10) + " 00:01:01");
		setCookie("end_time", TimeZoneUtil.getCurDate() + " 23:59:59");
		setCookie("break_prop", "99");

		// startCustomService();

	}


	public boolean loadInitConfigure(String sIP) {

	
		return true;
	}

	// 获取服务器上Android App的版本号
	public int getServerVersion() {
		return Integer.parseInt(m_ice.getConfigure("common",
				"app_android_version"));
	}

	public boolean isCheckUp() {
		if (Integer.parseInt(getCookie("is_check_up")) == 0) {
			return false;
		}
		return true;
	}

	public String getDBID(String sType) {

		return m_ice.getID(sType);
	}

	public String getTime() {

		return m_ice.getTime();
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

	public boolean containsProperty(String key) {
		Properties props = getProperties();
		return props.containsKey(key);
	}

	public void setProperties(Properties ps) {
		WinsoBaseAppConfig.getAppConfig(this).set(ps);
	}

	public Properties getProperties() {
		return WinsoBaseAppConfig.getAppConfig(this).get();
	}

	public void setProperty(String key, String value) {
		WinsoBaseAppConfig.getAppConfig(this).set(key, value);
	}

	public String getProperty(String key) {
		return WinsoBaseAppConfig.getAppConfig(this).get(key);
	}

	public void removeProperty(String... key) {
		WinsoBaseAppConfig.getAppConfig(this).remove(key);
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
	@SuppressLint("DefaultLocale") public int getNetworkType() {
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
					+ STORE_PIC_PATH);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			return directory.getAbsolutePath() + "/";
		}

		// 只能存在本地文件�?
		String sDest = getApplicationContext().getFilesDir().getPath()
				+ File.separator + STORE_PIC_PATH + File.separator;

		File directory = new File(sDest);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		return sDest;

	}

	// 判断用户是否有指定流程节点某项权�?
	public boolean hasNodeRight(int iRightID) {

		return true;
	}


}
