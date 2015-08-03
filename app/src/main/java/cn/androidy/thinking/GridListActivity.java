package cn.androidy.thinking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.androidy.thinking.adapters.GridListAdapter;
import cn.androidy.thinking.constant.Constants;
import cn.androidy.thinking.data.IGridItem;

public class GridListActivity extends DemoDetailBaseActivity {
    @Bind(R.id.listView)
    ListView listView;
    private GridListAdapter adapter;
    private List<IGridItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        list = new ArrayList<>();
        list.add(new IGridItem() {
            @Override
            public int getType() {
                return IGridItem.TYPE_4x1;
            }
        });
        list.add(new IGridItem() {
            @Override
            public int getType() {
                return IGridItem.TYPE_DIVIDOR;
            }
        });
        list.add(new IGridItem() {
            @Override
            public int getType() {
                return IGridItem.TYPE_2x1;
            }
        });
        list.add(new IGridItem() {
            @Override
            public int getType() {
                return IGridItem.TYPE_DIVIDOR;
            }
        });
        list.add(new IGridItem() {
            @Override
            public int getType() {
                return IGridItem.TYPE_3x1;
            }
        });
        adapter = new GridListAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_grid_list;
    }

    @Override
    protected int getFloatingActionButtonId() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grid_list, menu);
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
            Constants.color_dividor = Constants.color_dividor == 0 ? R.color.dividor : 0;
            adapter.notifyDataSetChanged();
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
