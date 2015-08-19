package cn.androidy.thinking;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import cn.androidy.thinking.constant.Constants;
import cn.androidy.thinking.data.DemoViewTreeManager;

public class DrawViewActivity extends DemoDetailBaseActivity {
    private float density;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        density = getResources().getDisplayMetrics().density;
        DemoViewTreeManager.getInstance(density).setTopView(findViewById(R.id.top_vg));
        DemoViewTreeManager.getInstance(density).getTopView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_draw_view;
    }

    @Override
    protected int getFloatingActionButtonId() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_draw_view_activity1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_check_log) {
            startActivity(new Intent(this, DrawViewTreeActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        DemoViewTreeManager.getInstance(density).reset();
        super.onDestroy();
    }
}
