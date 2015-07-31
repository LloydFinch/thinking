package cn.androidy.thinking;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.androidy.common.utils.Phrase;
import cn.androidy.common.utils.SizeColorPhrase;
import cn.androidy.thinking.demos.ThirdParthDemo;


public class ColorPhraseActivity extends DemoDetailBaseActivity {

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
    public void formatSize() {
        float mTextSizeInner = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 28, getResources().getDisplayMetrics());
        float mTextSizeOuter = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics());
        float price = 2999.00f;
        String pattern = "这件商品售价[{price}]元";
        CharSequence chars = Phrase.from(pattern).put("price", Float.toString(price)).format();
        chars = SizeColorPhrase.from(chars).withSeparator("[]").innerSize(mTextSizeInner).outerSize(mTextSizeOuter).format();
        SpannableStringBuilder sp = new SpannableStringBuilder(chars);
        textView.setText(sp);
    }

    @OnClick(R.id.button2)
    public void formatColor() {
        float price = 2999.00f;
        String pattern = "这件商品售价[{price}]元";
        CharSequence chars = Phrase.from(pattern).put("price", Float.toString(price)).format();
        chars = SizeColorPhrase.from(chars).withSeparator("[]").innerColor(Color.RED).outerColor(Color.BLACK).format();
        SpannableStringBuilder sp = new SpannableStringBuilder(chars);
        textView.setText(sp);
    }

    @OnClick(R.id.button3)
    public void formatSizeColor() {
        float mTextSizeInner = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 28, getResources().getDisplayMetrics());
        float mTextSizeOuter = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics());
        float price = 2999.00f;
        String pattern = "这件商品售价[{price}]元";
        CharSequence chars = Phrase.from(pattern).put("price", Float.toString(price)).format();
        chars = SizeColorPhrase.from(chars).withSeparator("[]").innerColorSize(Color.RED, mTextSizeInner).outerColorSize(Color.BLACK, mTextSizeOuter).format();
        SpannableStringBuilder sp = new SpannableStringBuilder(chars);
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
