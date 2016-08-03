package com.winso.comm_library.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.winso.comm_library.icedb.SelectHelp;

import android.text.Html;

//代表ListView中所有行
public class TNListObjectInfoRowMgr {
	
	public static final String TYPE_TEXT = "text";
	public static final String TYPE_PICTURE = "picture";
	public static final String TYPE_HTML = "html";
	public static final String TYPE_PROGRESS = "progress_bar";

	public TNListObjectInfoRowMgr() {
		m_listItem = new ArrayList<HashMap<String, Object>>();

		m_vHelpFields = new SelectHelp();
		m_vHelpValues = new SelectHelp();
		m_vHelpFields.addField("id");
		m_vHelpFields.addField("res_id");
		m_vHelpFields.addField("type");

	}

	// 获取字段的列表
	public String[] getStringFields() {
		if ( fieldSize() <= 0 )
			return null;
		
		String vStrings[] = new String[fieldSize()];
		// int[] vInts = new int[fieldSize()];

		for (int i = 0; i < m_vHelpFields.size(); i++) {

			vStrings[i] = m_vHelpFields.valueStringByName(i, "id");

		}
		return vStrings;

	}

	// 获取字段的列表
	public int[] getResFields() {
		if ( fieldSize() <= 0 )
			return null;
		
		
		int vInts[] = new int[fieldSize()];
		
		for (int i = 0; i < m_vHelpFields.size(); i++) {

			vInts[i] = m_vHelpFields.valueIntByName(i, "res_id");

		}
		return vInts;

	}

	// 清空
//	public void clear() {
//		m_vRows.clear();
//	}

	// 字段数
	int fieldSize() {
		return m_vHelpFields.size();

	}

	// 返回大小
//	int size() {
//		return m_vRows.size();
//	}



	// 设置help
	public void setHelp(SelectHelp help) {
		m_vHelpValues.copy(help);
		
		//
	}

	// 更新列表
	public void updateList(TNBaseActivity v) {
		m_listItem.clear();

		for (int i = 0; i < m_vHelpValues.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();

			for (int j = 0; j < m_vHelpFields.size(); j++) {
				String type = m_vHelpFields.valueStringByName(j, "type");
				if (TYPE_TEXT.equals(type)) {// 文本
					map.put(m_vHelpFields.valueStringByName(j, "id"),
							m_vHelpValues.valueStringByName(i, m_vHelpFields.valueStringByName(j, "id")));
				} else if (TYPE_PICTURE.equals(type)) {// 图片
					int iResID = v.getPic(m_vHelpValues.valueStringByName(i, m_vHelpFields.valueStringByName(j, "id")));
					if (iResID > 0)
						map.put(m_vHelpFields.valueStringByName(j, "id"), iResID);
				} else if (TYPE_PROGRESS.equals(type)) {
					int[] mProgressItem = new int[]{
							m_vHelpValues.valueIntByName(i, m_vHelpFields.valueStringByName(j, "id") + "_max"),
							m_vHelpValues.valueIntByName(i, m_vHelpFields.valueStringByName(j, "id") + "_value")};
					map.put(m_vHelpFields.valueStringByName(j, "id"), mProgressItem);
				} else if (TYPE_HTML.equals(type)) {
					map.put(m_vHelpFields.valueStringByName(j, "id"), Html
							.fromHtml(m_vHelpValues.valueStringByName(i, m_vHelpFields.valueStringByName(j, "id"))));
				} else {
					throw new IllegalStateException("Fields属性错误。不支持该对象类型：" + type);
				}

			}
			// map.put("btn_save_sel", o_Picture_ID);
			m_listItem.add(map);
		}
	}

	//是否存在
	boolean existField(String sID)
	{
		for (int i = 0; i < m_vHelpFields.size(); i++) {

			if ( m_vHelpFields.valueStringByName(i, "id").equals(sID) )
				return true;
		}
		
		return false;
	}
	// 增加关系
	public void addField(String sField, String sType,int iResID) {
		if ( existField(sField) )
			return;
		
		
		Vector<String> v = new Vector<String>();
		v.add(sField);
		v.add(String.valueOf(iResID));
		v.add(sType);
		
		m_vHelpFields.addValue(v);
	}

	// 返回对像
	// public TNListObjectInfoRow getRow(int iRow) {
	// if (iRow < 0)
	// return null;
	//
	// if (iRow >= m_vRows.size())
	// return null;
	//
	// return m_vRows.get(iRow);
	// }

	// 值
	public SelectHelp m_vHelpValues;

	// 列信息
	public ArrayList<HashMap<String, Object>> m_listItem;

	private SelectHelp m_vHelpFields;
	//private Vector<TNListObjectInfoRow> m_vRows;
}
