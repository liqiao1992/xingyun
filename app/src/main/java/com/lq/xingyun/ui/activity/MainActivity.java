package com.lq.xingyun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.lq.xingyun.R;
import com.lq.xingyun.adapter.FragmentAdapter;
import com.lq.xingyun.presenter.BasePresenter;
import com.lq.xingyun.ui.fragment.ArticleFragment;
import com.lq.xingyun.ui.fragment.MovieFragment;
import com.lq.xingyun.ui.fragment.PictureFragment;
import com.lq.xingyun.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/8/10.
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private DrawerLayout drawer;

    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        tabLayout = (TabLayout) findViewById(R.id.navigation_content_tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        List<String> titles = new ArrayList<>();
        titles.add("电影");
        titles.add("美文");
        titles.add("妹子");

        tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(2)));
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MovieFragment());
        fragments.add(new ArticleFragment());
        fragments.add(new PictureFragment());
        final FragmentAdapter adapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = tabLayout.getSelectedTabPosition();
                    Intent intent=new Intent();
                    switch (index) {
                        case 0:
                            intent.setAction(MovieFragment.TAG);
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                            break;
                        case 1:
                            intent.setAction(ArticleFragment.TAG);
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                            break;
                        case 2:
                            intent.setAction(PictureFragment.TAG);
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                            break;

                    }
                }
            });
        }
    }

    @Override
    public void setActionBar() {
        super.setActionBar();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView imageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "fuckyou", Snackbar.LENGTH_LONG).show();

            }
        });
        if (SharedPreferencesUtils.getBoolean(getApplicationContext(), "isNight")) {
            navigationView.getMenu().findItem(R.id.day).setIcon(R.mipmap.ic_day).setTitle("日间模式");
        } else {
            navigationView.getMenu().findItem(R.id.day).setIcon(R.mipmap.ic_night).setTitle("夜间模式");
        }

    }


    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.day:
                if (SharedPreferencesUtils.getBoolean(getApplicationContext(), "isNight")) {
                    ChangeToDay();
                }else{
                    ChangeToNight();
                }
                MainActivity.this.recreate();
                break;
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
