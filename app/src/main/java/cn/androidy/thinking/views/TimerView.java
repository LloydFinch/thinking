package cn.androidy.thinking.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.androidy.listgrid.control.ITickListener;
import cn.androidy.listgrid.control.TickTimer;
import cn.androidy.thinking.R;

public class TimerView extends LinearLayout implements ITickListener {
	protected String strHour;
	protected String strMin;
	protected String strSec;
	private long endTime = 0;
	public Handler handler = new Handler();
	@Bind(R.id.tvHour)
	TextView tvHour;
	@Bind(R.id.tvMin)
	TextView tvMin;
	@Bind(R.id.tvSec)
	TextView tvSec;
	@Bind(R.id.tvStart)
	TextView tvStart;

	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TimerView(Context context) {
		super(context);
		init();
	}

	private void init() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		inflater.inflate(R.layout.timer_layout, this);
		ButterKnife.bind(this, this);
	}

	public void onHourRemain(int hour) {
		strHour = String.format("%02d", hour);
		if (tvHour != null) {
			tvHour.setText(strHour);
		}
	};

	public void onMinRemain(int min) {
		strMin = String.format("%02d", min);
		if (tvMin != null) {
			tvMin.setText(strMin);
		}
	};

	public void onSecRemain(int sec) {
		strSec = String.format("%02d", sec);
		if (tvSec != null) {
			tvSec.setText(strSec);
		}
	}

	@Override
	public void onTick() {
		long endTime = getEndTime();
		long currentTimeMillis = System.currentTimeMillis();
		long remain = endTime - currentTimeMillis;
		if (remain > 0) {
			dispatchRemainTime(endTime - currentTimeMillis);
		}
	}

	protected void dispatchRemainTime(long timeRemaining) {
		final int hour = (int) (timeRemaining / TickTimer.HOUR_MILLIS);// 小时数
		final int min = (int) ((timeRemaining - TickTimer.HOUR_MILLIS * hour) / TickTimer.MIN_MILLIS);
		final int sec = (int) ((timeRemaining - TickTimer.HOUR_MILLIS * hour - TickTimer.MIN_MILLIS * min) / TickTimer.SEC_MILLIS);
		handler.post(new Runnable() {

			@Override
			public void run() {
				onHourRemain(hour);
				onMinRemain(min);
				onSecRemain(sec);
			}
		});
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public void hideStartText() {
		tvStart.setVisibility(View.INVISIBLE);
	}
}
