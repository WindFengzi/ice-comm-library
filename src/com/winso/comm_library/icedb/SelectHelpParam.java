package com.winso.comm_library.icedb;

import java.util.Vector;

/*
 * 用于发送参数至中心，便于组装
 * 
 */
public class SelectHelpParam {

	private Vector<String> m_vs;

	public SelectHelpParam() {
		m_vs = new Vector<String>();

	}

	public void add(String src) {
		m_vs.add(src);
	}

	public static String get2(String p1,String p2)
	{
		
		
		char c = 0x01;
		
		return p1+String.valueOf(c)+p2;
		
	}
	
	public String get() {
		char c = 0x01;
		String sDest = new String("");

		for (int i = 0; i < m_vs.size(); i++) {
			sDest += m_vs.get(i);

			if (i != (m_vs.size() - 1))
				sDest += String.valueOf(c);

		}

		// EasyLog.debug(sDest);

		return sDest;
	}
	
	public void clear()
	{
		m_vs.clear();
	}
}
