package com.winso.comm_library;

import java.util.Vector;

//用于组装SQL 参数,以0x01分隔
public class ParamString {
	private final static char _sSep = 0x01;
	
	public ParamString()
	{
		 
	}
	
	public void add(String s)
	{
		m_vNodes.add(s);
	}
	
	public String toString()
	{
		//String.valueOf(_sSep)

		String sReturn = "";
		for (int i = 0; i < m_vNodes.size(); i++)
		{
			sReturn += m_vNodes.get(i);

			if ( i != (m_vNodes.size()-1) )
				sReturn += String.valueOf(_sSep);

		}
		return sReturn;
		
	}

	public void clear()
	{
		m_vNodes.clear();
	}


	private Vector<String> m_vNodes = new  Vector<String>();
}


