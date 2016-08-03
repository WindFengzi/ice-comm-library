package com.winso.comm_library.icedb;

import java.util.Vector;
import java.util.Formatter;

import com.winso.comm_library.EasyLog;
import com.winso.comm_library.StringUtils;

public class SelectHelp {

	public Vector<String> _fields;
	public Vector<String> _alias;
	private final static char _sSep = 0x01;
	private final static char _sLineSep = 0x02;

	public int mReturnCode; // 用于表示查询的返回结果 <0代表失败
	public String mReturnError; // 用于存储查询返的错误码

	/** 返回的记录条数 */
	// public int _count;
	public boolean _bHeader;

	/** 存储数据库返回的各行信息 */
	private Vector<Vector<String>> _values;

	public SelectHelp() {
		_fields = new Vector<String>();
		_alias = new Vector<String>();
		_values = new Vector<Vector<String>>();
		// _count = 0;
		_bHeader = true;

		mReturnCode = 1;
		mReturnError = "";

	}

	public void changeFiledName(String sFrom, String sTo) {
		for (int i = 0; i < _fields.size(); i++) {
			if (sFrom.equals(_fields.get(i))) {
				_fields.set(i, sTo);
				return;
			}
		}
	}

	public int size() {
		return _values.size();
	}

	public void addField(String s) {
		_fields.add(s);

	}

	public void copy(SelectHelp help)
	{
		_fields = help._fields;
		_values = help._values;
		_alias = help._alias;
		
	}
	public void reset() {
		_fields.clear();
		_values.clear();
		_alias.clear();

	}

	public void dump() {
//		for (int k = 0; k < _fields.size(); k++) {
//
//			Log.i(WinsoCommLibraryConstant.CONST_LOG_TAG, _fields.get(k));
//
//			if (k <= (_fields.size() - 2))
//				Log.i(WinsoCommLibraryConstant.CONST_LOG_TAG, "\t");
//
//		}
//		System.out.print("\n");
//		for (int i = 0; i < _values.size(); i++) {
//			Vector<String> v = _values.get(i);
//
//			for (int j = 0; j < v.size(); j++) {
//				Log.i(WinsoCommLibraryConstant.CONST_LOG_TAG, v.get(j));
//
//				if (j <= (_fields.size() - 2))
//					Log.i(WinsoCommLibraryConstant.CONST_LOG_TAG, "\t");
//			}
//			Log.i(WinsoCommLibraryConstant.CONST_LOG_TAG, "\n");
//		}
		EasyLog.debug(toString());
		
	}

	public int fromString(String src) {
		if (src.length() <= 0)
			return 0;

		reset();

		// _count = 0;

		int i = 0;
		// 先分隔子串
		String[] vsAll = src.split(String.valueOf(_sLineSep), -1);

		if (vsAll.length <= 0)
			return 0;

		String[] vHeader = vsAll[0].split(String.valueOf(_sSep), -1);
		// 增加字段
		for (i = 0; i < vHeader.length; i++) {
			if (_bHeader) {

				addField(vHeader[i]);
			} else {
				//@SuppressWarnings("resource")
				Formatter fmt = new Formatter();

				fmt.format("f%03d", i);

				addField(fmt.toString());
				
				
				fmt.close();
			}
		}

		// 判断是否有头
		if (vsAll.length <= 1) {
			/* 只有头没有数据 */
			return 0;
		}

		// 取子串
		for (i = 1; i < vsAll.length; i++) {

			String[] strSubVector = vsAll[i].split(String.valueOf(_sSep), -1);

			if (strSubVector.length < _fields.size())
				continue;
			Vector<String> vTmp = new Vector<String>();
			for (int j = 0; j < strSubVector.length; j++) {

				vTmp.add(strSubVector[j]);

			}

			_values.add(vTmp);

			// _count++;
		}

		return _values.size();

	}

	public void addValue(Vector<String> v) {
		_values.add(v);

	}

	public int getIndexByName(String key) {

		for (int i = 0; i < _fields.size(); i++) {

			if (key.compareToIgnoreCase(_fields.get(i)) == 0) {
				return i;
			}

		}
		return -1;
	}

	
	public double valueDoubleByName(int iRow, String sCol) {
		try
		{
			String v = valueStringByName(iRow,sCol);
			
			if ( StringUtils.isNumber(v))
			{
				return Double.valueOf(v);
			}
		}
		catch( java.lang.Exception e )
		{
			return 0;
		}
		return 0;
		
	}
	
	
	public int valueIntByName(int iRow, String sCol) {
		try
		{
			return Integer.valueOf(valueStringByName(iRow,sCol));
		}
		catch( java.lang.Exception e )
		{
			return 0;
		}
		
	}
	public String valueString(int iRow, int iCol) {
		if (iRow < 0)
			return "";
		if (iRow >= _values.size())
			return "";

		if (iCol >= _fields.size() || iCol < 0) {
			return "字段序号不正确";
		}

		return _values.get(iRow).get(iCol);
	}

	public String valueStringByName(int iRow, String sCol) {
		if (iRow < 0)
			return "";
		if (iRow >= _values.size())
			return "";

		int idx = getIndexByName(sCol);

		if (idx >= _fields.size() || idx < 0) {

			return "字段" + sCol + "不正确";

		}

		return _values.get(iRow).get(idx);
	}

	public String toString() {
		String sout = new String();
		int i = 0;
		for (i = 0; i < _fields.size(); i++) {
			sout += _fields.get(i);
			if (i != _fields.size() - 1)
				sout += _sSep;
		}
		sout += _sLineSep;

		int iRow = 0;
		for (iRow = 0; iRow < _values.size(); iRow++) {

			for (i = 0; i < _fields.size(); i++) {
				sout += valueString(iRow, i);
				sout += _sSep;
			}

			if (iRow != _values.size() - 1)
				sout += _sLineSep;

		}
		return sout;
	}
	

	/** 在返回的结果集中，修改结果集中的整数值
	*  @param[in] 行号
	*  @param[in] 字段名
	*  @param[in] 修改的值
	*  @return 是否修改成功
	*/
	public boolean setValueInt(int iRow, String key, int value)
	{
		if (iRow<0) return false;
	
		int idx = getIndexByName(key);
		if (idx >= (int)_fields.size() || idx<0) return false;
	
		//char buf[40] = { 0 };
		//sprintf(buf, "%d", value);
	
		//_values[row][idx] = buf;
	
		_values.get(iRow).setElementAt(String.valueOf(value),idx);
		
		return true;
	}
	public boolean setValueString(int iRow, String key, String value)
	{
		if (iRow<0) return false;
	
		int idx = getIndexByName(key);
		if (idx >= (int)_fields.size() || idx<0) return false;
	
		//char buf[40] = { 0 };
		//sprintf(buf, "%d", value);
	
		//_values[row][idx] = buf;
	
		_values.get(iRow).setElementAt(value,idx);
		return true;
	}



	public void genDebugData() {
		reset();
		addField("运营商");
		addField("年纪");
		addField("编号");

		Vector<String> line = new Vector<String>();
		line.add("中国电信");
		line.add("10");
		line.add("10000");
		addValue(line);
		line.clear();

		line.add("中国移动");
		line.add("12");
		line.add("10086");
		addValue(line);
		line.clear();

		line.add("中国联通");
		line.add("11");
		line.add("9090");
		addValue(line);
		line.clear();

	}

}
