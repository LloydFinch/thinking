package justlog.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.androidy.logger.data.ILog;
import cn.androidy.thinking.LogActivity;

public class LogListAdapter extends BaseAdapter {
    private Context context;
    private List<ILog> list;
    private DisplayMetrics dm;
    private String subDir;

    public LogListAdapter(Context context, String subDir, List<ILog> list) {
        super();
        this.context = context;
        this.list = list;
        dm = context.getResources().getDisplayMetrics();
        this.subDir = subDir;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public ILog getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = new TextView(context);
            int padding = (int) (10 * dm.density);
            convertView.setPadding(padding, padding, padding, padding);
            convertView.setBackgroundColor(Color.WHITE);
            ((TextView) convertView).setTextColor(Color.BLACK);
            holder = new ViewHolder((TextView) convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bindData(getItem(position).getLogTag());
        return convertView;
    }

    private class ViewHolder implements View.OnClickListener {
        TextView textView;

        public ViewHolder(TextView textView) {
            super();
            this.textView = textView;
            textView.setOnClickListener(ViewHolder.this);
        }

        public void bindData(String content) {
            textView.setText(content);
        }

        @Override
        public void onClick(View v) {
            LogActivity.checkLog(context, "/" + (TextUtils.isEmpty(subDir) ? "" : (subDir + "/")) + textView.getText().toString());
        }
    }
}
