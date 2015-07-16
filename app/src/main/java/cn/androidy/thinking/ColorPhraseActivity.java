package cn.androidy.thinking;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.androidy.thinking.demos.ThirdParthDemo;
import cn.androidy.thinking.utils.ColorPhrase;


public class ColorPhraseActivity extends DemoDetailBaseActivity {
    @Bind(R.id.editText1)
    EditText editText;
    @Bind(R.id.textView1)
    TextView textView;
    private String originalUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        originalUrl = getIntent().getStringExtra(ThirdParthDemo.KEY_ORIGINAL_URL);
    }

    @OnClick(R.id.button1)
    public void format() {
        float mTextSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 28, getResources().getDisplayMetrics());
        float mTextSize2 = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics());
        String pattern = "abcde{f}g";
        String pattern2 = "hijklm{n}";
        CharSequence chars = ColorPhrase.from(pattern).withSeparator("{}").innerColorSize(0xFFE6454A, mTextSize).outerColorSize(0xFF666666, mTextSize2).format();
        CharSequence chars2 = ColorPhrase.from(pattern2).withSeparator("{}").innerColorSize(0xFFE6454A, mTextSize).outerColorSize(0xFF666666, mTextSize2).format();
        SpannableStringBuilder sp = new SpannableStringBuilder(chars);
        sp.append(chars2);
        textView.setText(sp);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_color_phrase;
    }

    @Override
    protected int getFloatingActionButtonId() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_color_phrase, menu);
        return true;
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
        } else if (id == R.id.action_original_link) {
            Uri uri = Uri.parse(originalUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
