package com.winso.comm_library.icedb;

/**
 * 主要用于进行与远程ICE进行通讯
 * 
 * @author ericgoo
 * 
 */

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.winso.comm_library.CIntent;
import com.winso.comm_library.FileUtils;
import com.winso.comm_library.WinsoCommLibraryConstant;
import Ice.IntHolder;
import Ice.StringHolder;
import MQServerModule.MQInterfacePrx;
import MQServerModule.MQInterfacePrxHelper;

public class ICEDBUtil {

	private Ice.Communicator m_ic = null;
	public MQInterfacePrx m_iceObject;
	private String m_sError = null;
	private int m_iError = 0;
	public static int CONNECT_ERROR_STATE = -99;

	private boolean m_bLogin;
	private String m_sIP;
	private int m_iPort;

	public int mFileRetryTimes = 20; // 下载和上传重试次数
	public int mCacheSize = 10240; // 10K为单位

	/**
	 * 用于将GB2312转化成Unicode
	 */
	public static String toUnicode(String src)
			throws UnsupportedEncodingException {
		return new String(src.getBytes("ISO-8859-1"), "GB2312");
	}

	/**
	 * 用于将GB2312转化成Unicode
	 */
	public static String fromUnicode(String src)
			throws UnsupportedEncodingException {

		return new String(src.getBytes("GB2312"), "ISO-8859-1");
	}

	public ICEDBUtil() {
		m_ic = Ice.Util.initialize(new String[] {});

		m_sIP = new String();
		m_sError = new String();

		m_sIP = "127.0.0.1";
		m_sError = "";
		m_iPort = 8840;
		m_bLogin = false;
	}

	@SuppressLint("DefaultLocale")
	public boolean login(String ip, int iPort) {

		if (m_bLogin)
			return false;

		m_sIP = ip;
		m_iPort = iPort;

		// env:tcp -h www.myxui.com -p 8840
		String sURL = new String();

		sURL = String.format("env:tcp -h %s -p %d", m_sIP, m_iPort);

		try {
			init(sURL);
		} catch (Exception e) {
			m_sError = e.toString();
			m_iError = CONNECT_ERROR_STATE;
			m_bLogin = false;
			return false;
		}

		m_bLogin = true;
		return true;

	}
	
	public int error()
	{
		return m_iError;
	}

	public boolean logout() {

		m_bLogin = false;
		if (m_iceObject != null) {
			m_iceObject = null;
		}
		return true;
	}

	public boolean isLogin() {
		return m_bLogin;
	}

	// 初始化
	public void init(String url) throws Exception {

		if (m_iceObject != null) {
			m_iceObject = null;
		}
		Ice.ObjectPrx base = m_ic.stringToProxy(url);
		m_iceObject = MQInterfacePrxHelper.checkedCast(base.ice_twoway()
				.ice_timeout(15000).ice_secure(false));
	}

	// 释放
	public void release() {
		if (null != m_ic) {
			m_ic.destroy();
		}
	}

	// 检查连接
	public boolean checkConnection() {
		boolean result = false;
		if (null != m_iceObject) {
			result = true;
		}
		return result;
	}

	// 获取时间
	public String getTime() {
		if (null == m_iceObject) {
			return "";
		}

		return m_iceObject.getTime();

	}

	// 获取服务器版本
	public String version() {
		if (null == m_iceObject) {
			return "";
		}

		return m_iceObject.version();

	}

	// 获取时间
	public void send(String msg) {
		if (null == m_iceObject) {
			return;
		}

		m_iceObject.send(msg);

	}

	// 获取系统配置
	public String getConfigure(String seg, String key) {
		if (null == m_iceObject) {
			return "";
		}
		return m_iceObject.getConfigure(seg, key);

	}
	public String commandByString(String cmd, String param) {

		SelectHelp help = new SelectHelp();

		StringHolder outmsg = new StringHolder();

		try {
			help.mReturnCode = m_iceObject.command(fromUnicode(cmd),
					fromUnicode(param), outmsg);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			help.fromString(toUnicode(outmsg.value));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return outmsg.value;
	}
	
	public String commandStr(String cmd, String param) {

		int ic = -1;

		StringHolder outmsg = new StringHolder();

		try {
			ic = m_iceObject.command(fromUnicode(cmd),
					fromUnicode(param), outmsg);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if ( ic < 0 )
		{
			return "ERROR";
		}
	
		return outmsg.value;
	}

	
	//
	public SelectHelp command(String cmd, String param) {

		SelectHelp help = new SelectHelp();

		StringHolder outmsg = new StringHolder();

		try {
			help.mReturnCode = m_iceObject.command(fromUnicode(cmd),
					fromUnicode(param), outmsg);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			help.fromString(toUnicode(outmsg.value));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return help;
	}

	//
	public CIntent commandIt(String cmd, String param) {

		CIntent it = new CIntent();

		StringHolder outmsg = new StringHolder();

		try {
			int ic =  m_iceObject.command(fromUnicode(cmd),
					fromUnicode(param), outmsg);
			
			
			
			it.fromString(outmsg.value);
			
			it.set("return_code", String.valueOf(ic));
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			it.set("return_code", String.valueOf(-1));
		}

		

		return it;
	}

	// 直接执行远程
	public boolean execSQL(String sql) {
		StringHolder error = new StringHolder();

		try {
			boolean bReturn = m_iceObject.execSQL(fromUnicode(sql), "", error);
			if (bReturn == false) {
				m_sError = error.value;
			}
			return bReturn;
		} catch (Exception e) {
			return false;
		}

	}

	// 直接执行远程
	public SelectHelp execProc(String sql, String sParam) {
		StringHolder set = new StringHolder();
		StringHolder error = new StringHolder();
		SelectHelp help = new SelectHelp();

		try {
			m_iError = 0;
			boolean bOK = m_iceObject.execProc(fromUnicode(sql),
					fromUnicode(sParam), set, error);
			if (bOK) {
				help.fromString(toUnicode(set.value));
			}

			return help;

		} catch (Exception e) {
			e.printStackTrace();
			m_iError = -1;
			m_sError = e.toString();

			help.mReturnCode = m_iError;
			help.mReturnError = m_sError;

			return help;
		}

	}

	public int getErrorCode() {
		return m_iError;

	}

	public String getMsg() {
		try {
			return toUnicode(m_sError);
		} catch (Exception e) {
			return "";
		}
	}

	// 直接查询远程
	public SelectHelp selectByParam(String sql, String param) {
		StringHolder set = new StringHolder();
		StringHolder error = new StringHolder();
		SelectHelp help = new SelectHelp();

		try {
			m_iError = 0;
			int ic = m_iceObject.select(fromUnicode(sql), fromUnicode(param),
					set, error);

			if (ic > 0) {
				help.fromString(toUnicode(set.value));
			}
			if (ic < 0) {
				m_iError = -1;
				m_sError = error.value;

				help.mReturnCode = m_iError;
				help.mReturnError = m_sError;
			}
			return help;
		} catch (Exception e) {
			e.printStackTrace();

			m_sError = e.toString();
			m_iError = -1;
			help.mReturnCode = m_iError;
			help.mReturnError = m_sError;
			return help;
		}
	}

	// 直接执行远程
	public boolean execSQLByParam(String sql, String param) {
		StringHolder error = new StringHolder();

		try {
			boolean bReturn = m_iceObject.execSQL(fromUnicode(sql),
					fromUnicode(param), error);
			if (bReturn == false) {
				m_sError = error.value;
			}
			return bReturn;

		} catch (Exception e) {

			return false;
		}

	}

	// 直接执行远程
	public int execSQLByParam_int(String sql, String param) {
		StringHolder error = new StringHolder();

		try {
			boolean bReturn = m_iceObject.execSQL(fromUnicode(sql),
					fromUnicode(param), error);
			if (bReturn == false) {
				m_sError = error.value;
			}
			if (bReturn)
				return 1;

			return 0;

		} catch (Exception e) {

			return -1;
		}

	}

	public CIntent selectCmdByIntent(String cmd, String sqlCode, String param) {

		StringHolder set = new StringHolder();
		StringHolder error = new StringHolder();
		CIntent it = new CIntent();
		it.setInterfaceDefault();

		try {
			m_iError = 0;

			int ic = m_iceObject.selectCmd(cmd, sqlCode, fromUnicode(param),
					set, error);

			if (cmd.compareToIgnoreCase("sql_code") == 0) {
				it.set("code", String.valueOf(ic));
				it.set("error", toUnicode(error.value));
				it.setHelpString("help", toUnicode(set.value));
			} else {
				it.fromString(toUnicode(set.value));
			}

		} catch (Exception e) {
			e.printStackTrace();

			m_sError = e.toString();
			m_iError = -1;
			return it;

		}
		return it;
	}

	public CIntent execCmdByIntent(String cmd, String sqlCode, String param) {

		StringHolder set = new StringHolder();
		StringHolder error = new StringHolder();
		CIntent it = new CIntent();
		it.setInterfaceDefault();

		try {
			m_iError = 0;

			int ic = m_iceObject.execCmd(cmd, sqlCode, fromUnicode(param), set,
					error);

			if (cmd.compareToIgnoreCase("sql_code") == 0) {
				it.set("code", String.valueOf(ic));
				it.set("error", toUnicode(error.value));
				it.setHelpString("help", toUnicode(set.value));
			} else {
				it.fromString(toUnicode(set.value));
			}
		} catch (Exception e) {
			e.printStackTrace();

			m_sError = e.toString();
			m_iError = -1;
			return it;

		}
		return it;
	}
	public String execCmdByString(String cmd, String sqlCode, String param) {

		StringHolder set = new StringHolder();
		StringHolder error = new StringHolder();
		CIntent it = new CIntent();
		it.setInterfaceDefault();

		int ic = -1;
		
		try {
			m_iError = 0;

			ic = m_iceObject.execCmd(cmd, sqlCode, fromUnicode(param), set,
					error);

			m_iError = ic;
			
			return toUnicode(set.value);
		
			
			
		} catch (Exception e) {
			e.printStackTrace();

			m_sError = e.toString();
			m_iError = -1;
			return m_sError;

		}
		
	}
	// 直接查询cmd
	public SelectHelp execCmd(String cmd, String sqlCode, String param) {
		StringHolder set = new StringHolder();
		StringHolder error = new StringHolder();
		SelectHelp help = new SelectHelp();

		try {
			m_iError = 0;

			help.mReturnCode = m_iceObject.execCmd(cmd, sqlCode,
					fromUnicode(param), set, error);

			help.mReturnError = error.value;

		} catch (Exception e) {
			e.printStackTrace();

			m_sError = e.toString();
			m_iError = -1;
			help.mReturnCode = m_iError;
			help.mReturnError = m_sError;
			return help;

		}

		if (set.value.length() > 0) {
			try {
				help.fromString(toUnicode(set.value));
			} catch (Exception e) {
				Log.e(WinsoCommLibraryConstant.CONST_LOG_TAG, e.toString());
			}

		}
		return help;
	}

	// 直接查询远程
	public SelectHelp request(String cmd, String sqlCode, String param) {
		StringHolder set = new StringHolder();
		StringHolder error = new StringHolder();
		SelectHelp help = new SelectHelp();

		try {
			m_iError = 0;

			help.mReturnCode = m_iceObject.selectCmd(cmd, sqlCode,
					fromUnicode(param), set, error);

			help.mReturnError = error.value;

		} catch (Exception e) {
			e.printStackTrace();

			m_sError = e.toString();
			m_iError = -1;

			help.mReturnCode = -1;
			help.mReturnError = error.value;
			return help;

		}

		if (help.mReturnCode > 0) {
			try {
				help.fromString(toUnicode(set.value));
			} catch (Exception e) {
				Log.e(WinsoCommLibraryConstant.CONST_LOG_TAG, e.toString());
			}

		} else if (help.mReturnCode < 0) {
			m_iError = -1;
			m_sError = error.value;

			help.mReturnCode = -1;
			help.mReturnError = error.value;

		}
		return help;

	}

	// 直接查询远程
	public SelectHelp select(String sqlcode, String param) {

		try {
			return request("", sqlcode, fromUnicode(param));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new SelectHelp();

		}
	}

	// 直接查询远程
	public String getID(String id) {

		try {
			StringHolder set = new StringHolder();
			int ic = m_iceObject.command("get_sequence", id, set);

			if (ic >= 0)
				return set.value;

		} catch (Exception e) {
			e.printStackTrace();

			m_sError = e.toString();
			m_iError = -1;

			return "";

		}

		return "";
	}

	// 用于文件下载

	public SelectHelp getFileInfo(String sFilePath) {
		SelectHelp help = new SelectHelp();
		StringHolder set = new StringHolder();

		try {

			boolean bok = m_iceObject.getFileInfo(fromUnicode(sFilePath), set);

			if (bok) {
				help.mReturnCode = 1;
				help.fromString(toUnicode(set.value));
			} else {
				help.mReturnCode = -1;
			}
			return help;

		} catch (Exception e) {
			e.printStackTrace();

			m_sError = e.toString();
			m_iError = -1;
			help.mReturnCode = -1;
			help.mReturnError = m_sError;
			return help;

		}
	}

	public SelectHelp getFileInfoSeq(String sFilePath) {
		SelectHelp help = new SelectHelp();
		StringHolder set = new StringHolder();

		try {

			boolean bok = m_iceObject.getFileInfoSeq(fromUnicode(sFilePath),
					set);

			if (bok) {
				help.mReturnCode = 1;
				help.fromString(toUnicode(set.value));
			} else {
				help.mReturnCode = -1;
			}
			return help;

		} catch (Exception e) {
			e.printStackTrace();

			m_sError = e.toString();
			m_iError = -1;
			help.mReturnCode = -1;
			help.mReturnError = m_sError;
			return help;

		}
	}

	// getFileCompressed
	public byte[] getFileCompressed(String sFile, int pos, int num,
			IntHolder iResult) {
		// IntHolder iResult = new IntHolder();
		byte[] bytes = null;

		m_iError = -1;

		try {
			bytes = m_iceObject.getFileCompressed(fromUnicode(sFile), pos, num,
					iResult);
		} catch (Exception e) {
			e.printStackTrace();

			m_sError = e.toString();
			m_iError = -1;

		}
		return bytes;

	}

	public int getFileRetryTimes() {
		return mFileRetryTimes;
	}

	public void setFileRetryTimes(int iCount) {
		mFileRetryTimes = iCount;
	}

	public int getFileCache() {
		return mCacheSize;
	}

	public void setFileCache(int iCacheSize) {
		mCacheSize = iCacheSize;
	}

	/*
	 * 上传文件 FileProcessCallbackInterface fcall
	 */
	public int upload(String sSrcFile, String sRemotePath) {
		int iCacheSize = getFileCache();
		int iRetryTimes = getFileRetryTimes();
		try {

			String sFileName = FileUtils.getFileName(sSrcFile);

			File file = new File(sSrcFile);

			if (!file.exists())
				return -1;

			FileInputStream fin = new FileInputStream(sSrcFile);
			// int fSize = fin.available();

			int iRead = 0;

			int iCurPos = 0;

			byte[] buffer = new byte[iCacheSize];

			// fin.read(buffer);

			int iCurRetry = 0;

			while ((iRead = fin.read(buffer)) > 0) {

				int iWrite = m_iceObject.UploadFile(sRemotePath + "/"
						+ sFileName, iCurPos, iRead, buffer);

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

			}

			fin.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}
}
