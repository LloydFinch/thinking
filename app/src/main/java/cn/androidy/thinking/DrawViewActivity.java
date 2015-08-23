package cn.androidy.thinking;

import android.os.Bundle;
import android.view.ViewParent;

import cn.androidy.logger.core.Log;

public class DrawViewActivity extends TraceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_view);
        Log.d("TraceActivity", "onCreate getWindow=" + getWindow());

        ViewParent topParent = (ViewParent) findViewById(R.id.top_vg);
        while (topParent != null && topParent.getParent() != null) {
            topParent = topParent.getParent();
            Log.d("TraceActivity", ">>>" + topParent);
        }
        Log.d("TraceActivity", "topParent=" + topParent);
    }
}
