package com.winso.comm_library.app;

import java.util.List;
import java.util.Map;


import android.content.Context;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * 
 * 描述 进度条适配器，在SimpleAdapter的基础上，增加对进度条展现的支持
 * @author qiuxd
 * @version 1.0
 * @date 2015年12月18日
 */
class NormalAdapter extends SimpleAdapter {
	private Context context;   
	private int resource;
    private int[] mTo;
    private String[] mFrom;		
	private List<? extends Map<String, ?>> mData;

	public NormalAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.context = context;   
		this.resource = resource;
		this.mData = data;
		this.mTo = to;
		this.mFrom = from;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v;
        if (convertView == null) {
            v = LayoutInflater.from(context).inflate(resource, parent, false);
        } else {
            v = convertView;
        }
        bindView(position, v);
		return v;
	}
	/**
	 * 数据绑定到界面
	 * @param position
	 * @param v
	 */
	private void bindView(int position, View view) {
		final Map<String, ?> dataSet = mData.get(position);
		if (dataSet == null) {
			return;
		}

		final String[] from = mFrom;
		final int[] to = mTo;
		final int count = to.length;

		for (int i = 0; i < count; i++) {
			final View v = view.findViewById(to[i]);
			if (v != null) {
				final Object data = dataSet.get(from[i]);
				String text = data == null ? "" : data.toString();
				if (text == null) {
					text = "";
				}

				if (v instanceof Checkable) {
					if (data instanceof Boolean) {
						((Checkable) v).setChecked((Boolean) data);
					} else if (v instanceof TextView) {
						// Note: keep the instanceof TextView check at the bottom of these
                        // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                        setViewText((TextView) v, text);
					} else {
						throw new IllegalStateException(
								v.getClass().getName() + " should be bound to a Boolean, not a "
										+ (data == null ? "<unknown type>" : data.getClass()));
					}
				} else if (v instanceof TextView) {
					if (data instanceof Spanned) {
						((TextView) v).setText((CharSequence) data);
					} else {
					// Note: keep the instanceof TextView check at the bottom of these
                    // ifs since a lot of views are TextViews (e.g. CheckBoxes).
						setViewText((TextView) v, text);
					}
				} else if (v instanceof ImageView) {
					if (data instanceof Integer) {
						setViewImage((ImageView) v, (Integer) data);
					} else {
						setViewImage((ImageView) v, text);
					}
				} else if (v instanceof ProgressBar) {//进度条设置
					int[] mProgressItem =  (int[]) data;
					((ProgressBar) v).setMax(mProgressItem[0]);
					((ProgressBar) v).setProgress(mProgressItem[1]);
				}else {
					throw new IllegalStateException(v.getClass().getName() + " is not a "
							+ " view that can be bounds by this com.winso.comm_library.app.TNBaseListRefreshViewProgressActivity.NormalAdapter");
				}
			}
		}
	}
	/**
	 * 
	 * 描述 进度条对象
	 * @author qiuxd
	 * @version 1.0
	 * @date 2015年12月18日
	 */
	static class ProgressItem {
		private final int max;
		private final int progress;
		
		public ProgressItem(int max, int progress) {
			this.max = max;
			this.progress = progress;
		}

		public int getMax() {
			return max;
		}

		public int getProgress() {
			return progress;
		}
		
	}
}