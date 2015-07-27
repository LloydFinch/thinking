package com.example.android.common.logger;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


import java.io.File;

import cn.androidy.thinking.R;
import cn.androidy.thinking.utils.DimenUtils;
import cn.androidy.thinking.utils.FileUtils;
import cn.androidy.thinking.utils.SizeColorPhrase;

public class LogDetailFragment extends Fragment implements LogReadTask.ILogReadListener {
	public static final String KEY_LOG_TAG = "KEY_LOG_TAG";
	LoggerDetailView customLoggerView;

	public static LogDetailFragment newInstance(String tag) {
		LogDetailFragment detailFragment = new LogDetailFragment();
		Bundle args = new Bundle();
		args.putString(KEY_LOG_TAG, tag);
		detailFragment.setArguments(args);
		return detailFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_logdetail, null);
		customLoggerView = (LoggerDetailView) view.findViewById(R.id.customLoggerView);
		final String tag = getLogTag();
		new LogReadTask(this).execute(tag);
		view.findViewById(R.id.btn_clear).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				File f;
				if (!TextUtils.isEmpty(tag)) {
					f = new File(LogManager.logFileDir, tag);
				} else {
					f = new File(LogManager.logFileDir);
				}
				FileUtils.deleteFile(f.getAbsolutePath());
				new LogReadTask(LogDetailFragment.this).execute(tag);
			}
		});
		return view;
	}

	private String getLogTag() {
		return getArguments().getString(KEY_LOG_TAG);
	}

	@Override
	public void onLogGet(LogEntity entity) {
		if (entity != null) {
			customLoggerView.getLogDetailText().setText(getHighliText(entity.getMsg()));
		} else {
			customLoggerView.getLogDetailText().setText("");
		}
	}

	public static final String TEXT_TO_HIGHLIGHT = "cn.androidy.thinking";

	private CharSequence getHighliText(String msg) {
		if (TextUtils.isEmpty(msg) || !msg.contains(TEXT_TO_HIGHLIGHT)) {
			return msg;
		} else {
			msg = msg.replaceAll(TEXT_TO_HIGHLIGHT, "{" + TEXT_TO_HIGHLIGHT + "}");
			CharSequence charSequence = SizeColorPhrase.from(msg).withSeparator("{}")
					.innerColorSize(Color.BLUE, DimenUtils.spTopx(getActivity(), 16)).format();
			return charSequence;
		}
	}
}
