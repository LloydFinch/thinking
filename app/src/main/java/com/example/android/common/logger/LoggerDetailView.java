package com.example.android.common.logger;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class LoggerDetailView extends ScrollView {
	private TextView mLogDetailText;

	public LoggerDetailView(Context context) {
		this(context, null);
	}

	public LoggerDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		ViewGroup.LayoutParams scrollParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		setLayoutParams(scrollParams);
		setBackgroundColor(Color.WHITE);
		mLogDetailText = new LogView(getContext());
		ViewGroup.LayoutParams logParams = new ViewGroup.LayoutParams(scrollParams);
		logParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		logParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
		mLogDetailText.setLayoutParams(logParams);
		mLogDetailText.setClickable(true);
		mLogDetailText.setFocusable(true);
		mLogDetailText.setTypeface(Typeface.MONOSPACE);

		// Want to set padding as 16 dips, setPadding takes pixels. Hooray math!
		int paddingDips = 16;
		double scale = getResources().getDisplayMetrics().density;
		int paddingPixels = (int) ((paddingDips * (scale)) + .5);
		mLogDetailText.setPadding(paddingPixels, paddingPixels, paddingPixels, paddingPixels);
		mLogDetailText.setCompoundDrawablePadding(paddingPixels);
		mLogDetailText.setTextColor(Color.BLACK);
		mLogDetailText.setGravity(Gravity.BOTTOM);
		mLogDetailText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		addView(mLogDetailText);

		mLogDetailText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
	}

	public TextView getLogDetailText() {
		return mLogDetailText;
	}
}
