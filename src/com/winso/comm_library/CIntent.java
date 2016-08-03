package com.winso.comm_library;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.winso.comm_library.icedb.SelectHelp;

//用于参数传递
//可用于接口参数的传递
//如果是从SelectHelp导入的话，导入的是第一行的数据
public class CIntent {
	
	public CIntent() {
		m_map = new HashMap<String, String>();
		
		
	}

	public int size() {

		return m_map.size();
	}

	public void copy(CIntent in) {
		m_map.clear();
		
		if (in.size() <= 0)
			return;

		Iterator<String> it = in.m_map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			String value = m_map.get(key);

			
			m_map.put(key, value);

		}
	}
	public double getDouble(String key)
	{
		try
		{
			return Double.parseDouble(get(key));
		}
		catch(Exception e)
		{
			return -1;
		}
	}
	public int getInt(String key)
	{
		try
		{
			return Integer.parseInt(get(key));
		}
		catch(Exception e)
		{
			return -1;
		}
	}
	public void clear() {
		m_map.clear();
	}

	public void set(String key, String v) {
		m_map.put(key, v);
	}

	public void setHelp(String key, SelectHelp v) {
		
		
		m_map.put(key, MyBase64.encode(v.toString()));
	}

	public void setHelpString(String key, String v) {
		m_map.put(key, 
				MyBase64.encode(v)					);
	}

	public boolean exist(String key) {

		if (m_map.get(key) != null)
			return true;

		return false;
	}

	public String get(String key) {

		return m_map.get(key);
	}
	public static String toUnicode(String src)
			throws UnsupportedEncodingException {
		return new String(src.getBytes("ISO-8859-1"), "GB2312");
	}
	public boolean getHelp(String key, SelectHelp help) {

		help.reset();

		String sMsg = get(key);
		
		if ( sMsg == null )
			return false;
		
		String s = new String("");
		s =  MyBase64.decode(sMsg);
		
		help.fromString(s);

		return true;
	}

	public void dump() {

	}

	public void dumpKeys() {

	}

	public void fromString(String s) {
		SelectHelp help = new SelectHelp();
		help.fromString(s);
		fromHelp(help);
	}

	public String toString() {
		SelectHelp help = new SelectHelp();

		toHelp(help);

		return help.toString();
	}

	// 转化成CSelectHelp
	public void toHelp(SelectHelp help) {
		help.reset();
		if (m_map.size() <= 0)
			return;

		Vector<String> vs = new Vector<String>();

		Iterator<String> it = m_map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			String value = m_map.get(key);

			help.addField(key);

			vs.add(value);

		}

		help.addValue(vs);
	}

	// 转化成CSelectHelp
	public boolean fromHelp(SelectHelp help) {
		m_map.clear();

		if (help.size() <= 0)
			return false;

		for (int i = 0; i < help._fields.size(); i++) {
			set(help._fields.get(i), help.valueString(0, i));

		}
		return true;
	}

	// 清空，并且增加code,error,help三个字段
	public void setInterfaceDefault() {
		m_map.clear();

		set("code", "0");
		set("error", "0");
		set("help", "");

	}

	public void setInterface(String sCode, String sError, SelectHelp help) {
		m_map.clear();

		set("code", sCode);
		set("error", sError);

		if (help._fields.size() <= 0)
			set("help", "");
		else
			setHelp("help", help);

	}

	public void setInterfaceString(String sCode, String sError, String v) {
		m_map.clear();

		set("code", sCode);
		set("error", sError);
		setHelpString("help", v);
	}

	private Map<String, String> m_map;
};
