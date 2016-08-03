package com.winso.comm_library;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Vector;


import com.winso.comm_library.icedb.*;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLiteUtil {

	public String m_sError = new String();
	public int m_iError = 0;
	private String m_sDBFile;

	SQLiteDatabase m_db; // 数据库句柄

	public boolean login(String sDBFile) {

		m_sDBFile = sDBFile;

		if (m_db != null) {
			if (m_db.isOpen()) {
				m_db.close();
			}
		} else {
			m_db = SQLiteDatabase.openOrCreateDatabase(m_sDBFile, null);
		}

		return m_db.isOpen();

	}
	public boolean loginAsset(String sDBFile) {

		m_sDBFile = sDBFile;

		if (m_db != null) {
			if (m_db.isOpen()) {
				m_db.close();
			}
		} else {
			AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();   
			m_db = mg.getDatabase(m_sDBFile); 
		}

		return m_db.isOpen();

	}

	public SQLiteUtil(String sDBFile) {

		// 初始化，只需要调用一次  
		
		// 获取管理对象，因为数据库需要通过管理对象才能够获取  
		
		
		login(sDBFile);
	}

	public SQLiteUtil() {
		// 初始化，只需要调用一次  
	

	}
	
	
	
	/*
	 * begin tran
	 */
	public void begin()
	{
		if ( m_db.inTransaction() )
			m_db.endTransaction();
		m_db.beginTransaction();
	}
	public void commit()
	{
		if ( m_db.inTransaction() )
			m_db.endTransaction();
	}
	public void rollback()
	{
		if ( m_db.inTransaction() )
			m_db.endTransaction();
	}
	
	

	/**
	 * 删除数据库
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean deleteDB() throws Exception {
		if (m_db == null)
			return false;
		try {
			File file = new File(m_sDBFile);
			if (file.exists()) {
				return file.delete();
			}
			return true;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public boolean execSQL(String sql) {
		if (m_db == null)
			return false;

		try {
			m_db.execSQL(sql);
		} catch (Exception e) {
			m_sError = e.toString();
			m_iError = -1;

			Log.e("SQLiteDB", "DBError: " + m_sError);

			return false;
		}

		return true;
	}

	public boolean execSQL(String sql, String[] param) {
		if (m_db == null)
			return false;

		try {

			m_db.execSQL(sql, param);
		} catch (Exception e) {
			m_sError = e.toString();
			m_iError = -1;

			Log.e("SQLiteDB", "DBError: " + m_sError);

			return false;
		}

		return true;
	}

	public String getMsg() {
		return m_sError;
	}

	public SelectHelp select(String sql) {
		if (m_db == null)
			return new SelectHelp();

		return selectByParam(sql, null);

	}
	/**
	 * 用于将GB2312转化成Unicode
	 */
	public static String toUnicode(String src)
			throws UnsupportedEncodingException {
		return new String(src.getBytes("ISO-8859-1"), "GB2312");
	}

	public SelectHelp selectByParam(String sql, String[] param) {

		SelectHelp help = new SelectHelp();
		if (m_db == null)
			return help;
		try {

			Cursor c = m_db.rawQuery(sql, param);

			if (c == null) {
				Log.d("SQLiteDB", "sql Query Error: " + sql);
				return help;
			}

			int iFields = c.getColumnCount();

			int i = 0;

			// 加列头
			for (i = 0; i < iFields; i++) {
				help.addField(c.getColumnName(i));
			}

			while (c.moveToNext()) {

				Vector<String> vs = new Vector<String>();

				for (i = 0; i < iFields; i++) {
					vs.add(c.getString(i));

				}
				help.addValue(vs);

			}
			c.close();

			return help;
		} catch (Exception e) {
			Log.e("SQLiteDB", e.toString());
			return help;
		}

	}

	public void logout() {
		if (m_db == null)
			return;
		m_db.close();
	}
	private static String getString(InputStream inputStream) {
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(inputStream, "gbk");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer("");
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 
	 * 执行文件的SQL
	 * 
	 * @throws Exception
	 */
	public boolean execFile(InputStream inputStream) throws Exception {

		String sSQLContents = getString(inputStream);

		String[] vsAll = sSQLContents.split(";");

		// EasyLog.debug("read finished " + vsAll.length);

		for (int i = 0; i < vsAll.length; i++) {
			try {
				execSQL(vsAll[i]);
			} catch (Exception e) {
				EasyLog.error(e.toString());
				return false;
			}
		}
		
		return true;

	}
	
	
}