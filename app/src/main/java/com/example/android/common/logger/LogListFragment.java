package com.example.android.common.logger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.androidy.thinking.R;
import cn.androidy.thinking.adapters.LogListAdapter;

public class LogListFragment extends Fragment implements LogListReadTask.ILogListReadListener {

	public static final String KEY_LOG_TAG = "KEY_LOG_TAG";
	ListView listView;
	BaseAdapter adapter;
	List<LogItem> list = new ArrayList<>();

	public static LogListFragment newInstance(String tag) {
		LogListFragment logListFragment = new LogListFragment();
		Bundle args = new Bundle();
		args.putString(KEY_LOG_TAG, tag);
		logListFragment.setArguments(args);
		return logListFragment;
	}

	@Override
	public void onResume() {
		super.onResume();
		new LogListReadTask(this).execute(getLogTag());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_loglist, null);
		listView = (ListView) view.findViewById(R.id.listView);
		return view;
	}

	private String getLogTag() {
		return getArguments().getString(KEY_LOG_TAG);
	}

	@Override
	public void onLogGet(List<LogItem> result) {
		list.clear();
		if (result != null) {
			list.addAll(result);
		}
		if (adapter == null) {
			adapter = new LogListAdapter(getActivity(), getLogTag(), result);
			listView.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
	}
}
