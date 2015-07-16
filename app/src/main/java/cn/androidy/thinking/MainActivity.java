package cn.androidy.thinking;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.androidy.thinking.adapters.DemoAdapter;
import cn.androidy.thinking.demos.DemoListBuilder;
import cn.androidy.thinking.demos.IDemoEntry;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBar mActionBar;
    private RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    private List<IDemoEntry> mList = new ArrayList<IDemoEntry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActionBar = getSupportActionBar();
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_48pt);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mList = DemoListBuilder.getDemoEntryList();
        mRecyclerView.setAdapter(new DemoAdapter(this, mList));
        Logger.d("hello");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(mNavigationView);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.action_about) {
            mDrawerLayout.closeDrawers();
            return true;
        } else if (id == R.id.action_settings) {
            mDrawerLayout.closeDrawers();
            return true;
        } else if (id == R.id.action_weather) {
            mDrawerLayout.closeDrawers();
            startActivity(new Intent(this, WeatherActivity.class));
            return true;
        }
        return false;
    }

}
