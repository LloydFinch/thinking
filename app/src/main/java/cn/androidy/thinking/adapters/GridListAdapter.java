package cn.androidy.thinking.adapters;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.AbstractList;
import java.util.List;

import cn.androidy.thinking.R;
import cn.androidy.thinking.data.IGridItem;
import cn.androidy.thinking.views.GridListView2x1;
import cn.androidy.thinking.views.GridListView3x1;
import cn.androidy.thinking.views.GridListView4x1;

public class GridListAdapter extends BaseAdapter {
    Context context;
    List<IGridItem> list;
    LayoutInflater inflater;
    DisplayMetrics dm;

    public GridListAdapter(Context context, List<IGridItem> list) {
        this.context = context;
        this.list = list;
        dm = context.getResources().getDisplayMetrics();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return IGridItem.TYPE_COUNT;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public IGridItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (type == IGridItem.TYPE_2x1) {
            ViewHolder2x1 vh;
            if (convertView == null) {
                convertView = new GridListView2x1(context, 300 * 1.0f / 720, dm.widthPixels);
                vh = new ViewHolder2x1(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder2x1) convertView.getTag();
            }
            ((GridListView2x1) convertView).bindData(8);
        } else if (type == IGridItem.TYPE_3x1) {
            ViewHolder3x1 vh;
            if (convertView == null) {
                convertView = new GridListView3x1(context, 300 * 1.0f / 720, dm.widthPixels);
                vh = new ViewHolder3x1(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder3x1) convertView.getTag();
            }
            ((GridListView3x1) convertView).bindData(9);
        } else if (type == IGridItem.TYPE_4x1) {
            ViewHolder4x1 vh;
            if (convertView == null) {
                convertView = new GridListView4x1(context, 196 * 1.0f / 720, dm.widthPixels);
                vh = new ViewHolder4x1(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder4x1) convertView.getTag();
            }
            ((GridListView4x1) convertView).bindData(8);
        } else if (type == IGridItem.TYPE_DIVIDOR) {
            ViewHolderDividor vh;
            if (convertView == null) {
                convertView = new View(context);
                convertView.setBackgroundResource(R.color.dividor);
                AbsListView.LayoutParams p = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
                convertView.setLayoutParams(p);
                vh = new ViewHolderDividor(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolderDividor) convertView.getTag();
            }
        } else {

        }
        return convertView;
    }


    private class ViewHolder2x1 {
        View v;

        ViewHolder2x1(View v) {
            this.v = v;
        }
    }

    private class ViewHolder3x1 {
        View v;

        ViewHolder3x1(View v) {
            this.v = v;
        }
    }

    private class ViewHolder4x1 {
        View v;

        ViewHolder4x1(View v) {
            this.v = v;
        }
    }

    private class ViewHolderDividor {
        View v;

        ViewHolderDividor(View v) {
            this.v = v;
        }
    }
}
