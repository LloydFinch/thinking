package cn.androidy.thinking;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.List;

import cn.androidy.thinking.views.ScrollPicker;


public class ScrollPickerActivity extends DemoDetailBaseActivity {
    ScrollPicker scrollPicker;
    private List<Object> textList = new ArrayList<Object>();
    private View coverBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scrollPicker = (ScrollPicker) findViewById(R.id.scrollPicker);
        textList.add("北京");
        textList.add("天津");
        textList.add("上海");
        textList.add("广州");
        textList.add("深圳");
        textList.add("杭州");
        textList.add("成都");
        scrollPicker.bindData(textList);
        coverBg = findViewById(R.id.coverBg);
    }

    public void clickCoverBg(View v) {
        boolean show = scrollPicker.close();
        if (show) {
            ViewPropertyAnimator.animate(coverBg).alpha(0).setDuration(300).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    coverBg.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    coverBg.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    coverBg.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
    }

    public void clickScollPicker(View v) {

    }

    public void clickSelectBj(View v) {
        selectIndex(0);
    }

    public void clickSelectSz(View v) {
        selectIndex(4);
    }

    public void clickSelectCd(View v) {
        selectIndex(6);
    }

    private void selectIndex(int index) {
        scrollPicker.selectIndex(index);
    }

    public void clickSelectCity(View v) {
        boolean show = scrollPicker.show();
        if (show) {
            ViewHelper.setAlpha(coverBg, 0);
            ViewPropertyAnimator.animate(coverBg).alpha(1).setDuration(300).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    coverBg.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scroll_picker;
    }

    @Override
    protected int getFloatingActionButtonId() {
        return 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
