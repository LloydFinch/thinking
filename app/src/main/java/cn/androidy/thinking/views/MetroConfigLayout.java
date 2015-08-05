package cn.androidy.thinking.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.androidy.listgrid.control.TickTimer;
import cn.androidy.listgrid.views.FixedHeightWidthRatiolayout;
import cn.androidy.thinking.R;

public class MetroConfigLayout extends FixedHeightWidthRatiolayout implements View.OnClickListener {
    public static final float METRO_1_HW_RATIO = 1.0f * 200 / 720;// 1格运营位图片
    public static final float METRO_3_HW_RATIO = 1.0f * 400 / 720;// 3格运营位图片
    public static final float METRO_4_HW_RATIO = 1.0f * 400 / 720;// 4格运营位图片
    public static final float METRO_6_HW_RATIO = 1.0f * 600 / 720;// 6格运营位图片
    private ImageView[] imageViewArray;

    private List<TimerView> timerViewsList = new ArrayList<TimerView>();

    public MetroConfigLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private long endTime = 0;
    private TickTimer tickTimer;

    public MetroConfigLayout(Context context, float heightWidthRatio, int widthInPx) {
        this(context, heightWidthRatio, widthInPx, new TickTimer());
    }

    public MetroConfigLayout(Context context, float heightWidthRatio, int widthInPx, TickTimer tickTimer) {
        super(context, heightWidthRatio, widthInPx);
        this.tickTimer = tickTimer;
        endTime = 18 * TickTimer.HOUR_MILLIS + 9 * TickTimer.MIN_MILLIS + 44 * TickTimer.SEC_MILLIS + System.currentTimeMillis();
    }

    public void bindData(int layerNum) {
        if (imageViewArray != null && imageViewArray.length != 0) {
            return;
        }

        if (layerNum == 1) {
            imageViewArray = new ImageView[1];
            imageViewArray[0] = new ImageView(getContext());
            addView(imageViewArray[0], 0, 0, 1.0f, 1.0f);
            if (endTime > 0) {
                TimerView timerView = new TimerView(getContext());
                timerView.setEndTime(endTime);
                timerViewsList.add(timerView);
                LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                p.setMargins((int) (13 * dm.density), (int) (15 * dm.density), 0, 0);
                addView(timerView, p);
            }
        } else if (layerNum == 3) {
            imageViewArray = new ImageView[3];

            imageViewArray[0] = new ImageView(getContext());
            addView(imageViewArray[0], 0, 0, 0.5f, 1.0f);

            imageViewArray[1] = new ImageView(getContext());
            addView(imageViewArray[1], 0.5f, 0, 0.5f, 0.5f);

            imageViewArray[2] = new ImageView(getContext());
            addView(imageViewArray[2], 0.5f, 0.5f, 0.5f, 0.5f);

            for (int i = 0; i < imageViewArray.length; i++) {
                imageViewArray[i].setId(0x3f000001 + i);
            }

            if (endTime > 0) {
                TimerView timerView = new TimerView(getContext());
                timerView.hideStartText();
                LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                p.setMargins((int) (9 * dm.density), (int) (23 * dm.density), 0, 0);
                timerView.setEndTime(endTime);
                timerViewsList.add(timerView);
                addView(timerView, p);
            }

            if (endTime > 0) {
                TimerView timerView = new TimerView(getContext());
                timerView.hideStartText();
                LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                timerView.setEndTime(endTime);
                p.addRule(RelativeLayout.ALIGN_LEFT, imageViewArray[1].getId());
                p.setMargins((int) (9 * dm.density), (int) (23 * dm.density), 0, 0);
                timerViewsList.add(timerView);
                addView(timerView, p);
            }

            if (endTime > 0) {
                TimerView timerView = new TimerView(getContext());
                timerView.hideStartText();
                LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                timerView.setEndTime(endTime);
                p.addRule(RelativeLayout.ALIGN_LEFT, imageViewArray[1].getId());
                p.addRule(RelativeLayout.BELOW, imageViewArray[1].getId());
                p.setMargins((int) (9 * dm.density), (int) (23 * dm.density), 0, 0);
                timerViewsList.add(timerView);
                addView(timerView, p);
            }

            addHorizontalLine(R.color.dividor, 0.5f, 0.5f, 2, 0.5f);
            addVerticalLine(R.color.dividor, 0.5f, 0, 2, 1.0f);

        } else if (layerNum == 4) {
            imageViewArray = new ImageView[4];

            imageViewArray[0] = new ImageView(getContext());
            addView(imageViewArray[0], 0, 0, 0.5f, 1.0f);

            imageViewArray[1] = new ImageView(getContext());
            addView(imageViewArray[1], 0.5f, 0, 0.5f, 0.5f);

            imageViewArray[2] = new ImageView(getContext());
            addView(imageViewArray[2], 0.5f, 0.5f, 0.25f, 0.5f);

            imageViewArray[3] = new ImageView(getContext());
            addView(imageViewArray[3], 0.75f, 0.5f, 0.25f, 0.5f);

            for (int i = 0; i < imageViewArray.length; i++) {
                imageViewArray[i].setId(0x3f000001 + i);
            }

            if (endTime > 0) {
                TimerView timerView = new TimerView(getContext());
                timerView.hideStartText();
                LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                p.setMargins((int) (9 * dm.density), (int) (23 * dm.density), 0, 0);
                p.addRule(RelativeLayout.RIGHT_OF, imageViewArray[0].getId());
                timerView.setEndTime(endTime);
                timerViewsList.add(timerView);
                addView(timerView, p);
            }
            addHorizontalLine(R.color.dividor, 0.5f, 0.5f, 2, 0.5f);
            addVerticalLine(R.color.dividor, 0.5f, 0, 2, 1.0f);
            addVerticalLine(R.color.dividor, 0.75f, 0.5f, 2, 0.5f);

        } else if (layerNum == 6) {
            imageViewArray = new ImageView[6];

            imageViewArray[0] = new ImageView(getContext());
            addView(imageViewArray[0], 0, 0, 1f, 1.0f / 3);

            imageViewArray[1] = new ImageView(getContext());
            addView(imageViewArray[1], 0, 1.0f / 3, 0.5f, 2.0f / 3);

            imageViewArray[2] = new ImageView(getContext());
            addView(imageViewArray[2], 0.5f, 1.0f / 3, 0.25f, 1.0f / 3);

            imageViewArray[3] = new ImageView(getContext());
            addView(imageViewArray[3], 0.75f, 1.0f / 3, 0.25f, 1.0f / 3);

            imageViewArray[4] = new ImageView(getContext());
            addView(imageViewArray[4], 0.50f, 2.0f / 3, 0.25f, 1.0f / 3);

            imageViewArray[5] = new ImageView(getContext());
            addView(imageViewArray[5], 0.75f, 2.0f / 3, 0.25f, 1.0f / 3);

            for (int i = 0; i < imageViewArray.length; i++) {
                imageViewArray[i].setId(0x3f000001 + i);
            }

            if (endTime > 0) {
                LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                TimerView timerView = new TimerView(getContext());
                timerView.setEndTime(endTime);
                timerViewsList.add(timerView);
                p.setMargins((int) (13 * dm.density), (int) (15 * dm.density), 0, 0);
                addView(timerView, p);
            }

            addHorizontalLine(R.color.dividor, 0, 1.0f / 3, 2, 1);
            addVerticalLine(R.color.dividor, 0.5f, 1.0f / 3, 2, 2.0f / 3);
            addVerticalLine(R.color.dividor, 0.75f, 1.0f / 3, 2, 2.0f / 3);
            addHorizontalLine(R.color.dividor, 0.5f, 2.0f / 3, 2, 0.5f);
        }
        if (tickTimer != null) {
            for (TimerView timerView : timerViewsList) {
                tickTimer.addListener(timerView);
            }
        }
    }

    public List<TimerView> getTimerViewsList() {
        return timerViewsList;
    }

    @Override
    public void onClick(View v) {

    }
}
