package cn.androidy.listgrid.control;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class TickTimer implements OnScrollListener {
	public Handler handler = new Handler();
	private List<ITickListener> tickListenersList = new ArrayList<ITickListener>();
	public static final long SEC_MILLIS = 1000;
	public static final long MIN_MILLIS = 60 * SEC_MILLIS;
	public static final long HOUR_MILLIS = 60 * MIN_MILLIS;
	public int scrollState = SCROLL_STATE_IDLE;

	public TickTimer() {
		handler.post(new TimeTask());
	}

	public void addListener(ITickListener listener) {
		if (!tickListenersList.contains(listener)) {
			tickListenersList.add(listener);
		}
	}

	public class TimeTask implements Runnable {

		@Override
		public void run() {
			if (scrollState == SCROLL_STATE_IDLE && tickListenersList != null) {
				for (ITickListener listener : tickListenersList) {
					listener.onTick();
				}
			}
			handler.postDelayed(new TimeTask(), SEC_MILLIS);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		this.scrollState = scrollState;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

	}
}
