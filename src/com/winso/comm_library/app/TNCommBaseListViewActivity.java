package com.winso.comm_library.app;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.winso.comm_library.icedb.SelectHelp;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import com.winso.comm_library.R;

/**
 * 应用程序Activity的基类
 * 
 * @author ericgoo
 * @version 1.0
 * @created 2014-12-11
 */
public abstract class TNCommBaseListViewActivity extends TNBaseViewSaveActivity {

	// 用于存储列的信息
	public TNListObjectInfoRowMgr m_objectMgr = new TNListObjectInfoRowMgr();

	// 刷新
	private PullToRefreshListView m_pullRefreshListView;
	private ListView m_actualListView;
	SimpleAdapter m_listItemAdapter;
	private boolean bIsWorking = false;
	protected SelectHelp mHelpValue = new SelectHelp();

	// 定义字段的对应关系
	public int o_ListView_ID = R.layout.list_view_refresh_simple; // 用于图片的编号
	public int o_ListViewControl_ID = R.id.lt_refresh_view; // 用于列表控件的编号

	// 执行刷新任务
	public class GetListDataTask extends AsyncTask<Void, Void, String[]> {

		private String[] mStrings = { " " };

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				bIsWorking = true;
				reLoadView();
			} catch (Exception e) {
				Log.e("TNCommBaseListViewActivity", e.getMessage(), e);
			}

			bIsWorking = false;
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			m_listItemAdapter.notifyDataSetChanged();

			// Call onRefreshComplete when the list has been refreshed.
			m_pullRefreshListView.onRefreshComplete();

			updateUIView();
			bIsWorking = false;
			super.onPostExecute(result);
		}

	}
	
	public void enableRefresh(boolean bEnable)
	{
		m_pullRefreshListView.setPullToRefreshEnabled(bEnable);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);

		super.onCreate(savedInstanceState);

		initFields();
	}

	abstract protected void initFields();

	// 设置List的一些基本信息
	public void setList(int iListControlID, int iListUI) {
		o_ListView_ID = iListUI;
		o_ListViewControl_ID = iListControlID;
	}

	public void starLoadListThread() {
		if (bIsWorking)
			return;

		new GetListDataTask().execute();

	}

	// 加载视图
	// 加载中间的视图
	abstract protected void reLoadView();

	abstract protected void processClickView(View v, int iPos, long id);

	// 增加一行数据
	public void addMap(String sField, String sType, int iResID) {
		m_objectMgr.addField(sField, sType, iResID);
	}

	private void updateUIView() {
		m_objectMgr.setHelp(mHelpValue);
		m_objectMgr.updateList(this);
	}

	protected void initListView() {
		m_pullRefreshListView = getView(o_ListViewControl_ID);
		m_actualListView = m_pullRefreshListView.getRefreshableView();

		// Set a listener to be invoked when the list should be refreshed.
		m_pullRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Do work to refresh the list here.
				if (!bIsWorking) {
					new GetListDataTask().execute();
				}
			}
		});

		m_actualListView.setOnItemClickListener(listListener);

		m_listItemAdapter = new NormalAdapter(this, m_objectMgr.m_listItem,// 数据源

				o_ListView_ID,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				m_objectMgr.getStringFields(),
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				m_objectMgr.getResFields());
		// 添加并且显示
		m_actualListView.setAdapter(m_listItemAdapter);
		
		
		

	}

	/*
	 * 处理函数
	 */
	private OnItemClickListener listListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			processClickView(view, position, id);

		}
	};
}
