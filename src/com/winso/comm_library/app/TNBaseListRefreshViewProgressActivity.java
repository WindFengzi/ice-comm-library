/**
 * 
 */
package com.winso.comm_library.app;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.winso.comm_library.R;
import com.winso.comm_library.app.NormalAdapter.ProgressItem;
import com.winso.comm_library.app.TNBaseActivity;
import com.winso.comm_library.icedb.SelectHelp;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * 描述：带进度条列表的模版
 * @author qiuxd
 * @version 1.0
 * @date 2015年12月17日
 */
public abstract class TNBaseListRefreshViewProgressActivity extends TNBaseActivity {
	// 刷新
	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;
	ArrayList<HashMap<String, ?>> mListItem;
	private NormalAdapter mListItemAdapter;
	private boolean bIsWorking = false;
	protected SelectHelp mHelpValue = new SelectHelp();
	
	// 定义字段的对应关系
	public final static String s_ID = "title_id";
	public final static String s_title_left = "title_left"; // title
	public final static String s_title_right = "title_right";
	public final static String s_title_content = "title_content";
	public final static String s_progress_max = "progress_max";
	public final static String s_progress_value = "progress_value";
	
	public Object o_Picture_ID = R.drawable.list_logo; // 用于图片的编号

	/**
	 * 设置列表界面
	 * @param mPullRefreshListView
	 */
	public void setPullRefreshListView(PullToRefreshListView mPullRefreshListView) {
		this.mPullRefreshListView = mPullRefreshListView;
	}
	/**
	 * 启动异步加载线程
	 */
	public void starLoadListThread() {
		if (bIsWorking)
			return;
		new GetListDataTask().execute();

	}
	/**
	 * 初始化界面中公共组建
	 */
	protected void initListView() {
		if(mPullRefreshListView==null) {
			Log.e("TNBaseListRefreshViewProgressActivity", "请在子类中调用setPullRefreshListView，否则界面可能与预期不一致");
			mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.lt_refresh_view);
		}
		actualListView = mPullRefreshListView.getRefreshableView();

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Do work to refresh the list here.
				if (!bIsWorking) {
					new GetListDataTask().execute();
				}
			}
		});

		actualListView.setOnItemClickListener(listListener);
		mListItem = new ArrayList<HashMap<String, ?>>();
		mListItemAdapter = new NormalAdapter(this, mListItem, // 数据源
				R.layout.list_view_refresh_progress, // ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "title_left", "title_id", "title_right", "btn_save_sel", "title_content", "plan_progress" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.title_left, R.id.title_id, R.id.title_right, R.id.btn_save_sel, R.id.title_content, R.id.plan_progress });
		// 添加并且显示
		actualListView.setAdapter(mListItemAdapter);
	}

	// 执行刷新任务
	public class GetListDataTask extends AsyncTask<Void, Void, String[]> {
		private String[] mStrings = { " " };

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				bIsWorking = true;
				reLoadView();
			} catch (Exception e) {
				Log.e("TNBaseListRefreshViewProgressActivity", "doInBackground error...", e);
			}

			bIsWorking = false;
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			mListItemAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();
			updateUIView();
			bIsWorking = false;
			super.onPostExecute(result);
		}
	}
	/**
	 * 后台加载数据，其中数据组织拼装在此方法中完成
	 */
	abstract protected void reLoadView();
	/**
	 * 数据媒介的转换，理论上不需要修改。
	 */
	protected void updateUIView() {
		mListItem.clear();

		if (mHelpValue == null)
			return;

		for (int i = 0; i < mHelpValue.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("title_left", mHelpValue.valueStringByName(i, s_title_left));
			map.put("title_id", mHelpValue.valueStringByName(i, s_ID));
			map.put("title_right", mHelpValue.valueStringByName(i, s_title_right));
			map.put("title_content", mHelpValue.valueStringByName(i, s_title_content));
			map.put("btn_save_sel", o_Picture_ID);
			ProgressItem pItem = new ProgressItem(mHelpValue.valueIntByName(i, s_progress_max),
					mHelpValue.valueIntByName(i, s_progress_value));
			map.put("plan_progress", pItem);
			mListItem.add(map);
		}
	}
	
	/**
	 * 点击一行处理函数
	 */
	private OnItemClickListener listListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			@SuppressWarnings("unchecked")
			Map<String, ?> mapItem = (Map<String, ?>) parent.getAdapter().getItem(position);
			processClickView(mapItem);
		}
	};
	/**
	 * 列表单击事件。注：该方法不采用抽象方法，需要点击事件自行覆盖实现。
	 * @param mapItem
	 */
	protected void processClickView(Map<String, ?> mapItem) {
		//该方法不采用抽象方法，需要点击事件自行覆盖实现。
	}
}
