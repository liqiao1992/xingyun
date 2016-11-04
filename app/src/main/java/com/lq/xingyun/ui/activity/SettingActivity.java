package com.lq.xingyun.ui.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.lq.xingyun.R;
import com.lq.xingyun.inter.ISettingChangeListener;
import com.lq.xingyun.presenter.BasePresenter;
import com.lq.xingyun.ui.fragment.SettingFragment;
import com.lq.xingyun.utils.ActivityManagerUtil;
import com.lq.xingyun.utils.SharedPreferencesUtils;

import java.util.Locale;

/**
 * Created by lenovo on 2016/8/31.
 */
public class SettingActivity extends BaseActivity implements ISettingChangeListener {


    private Toolbar toolbar;
    private FrameLayout frameLayout;

    @Override
    public int getToolBarId() {
        return R.id.activity_setting_toolbar;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void setActionBar() {
        super.setActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.action_settings);
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        toolbar = getToolbar();
        frameLayout = (FrameLayout) findViewById(R.id.activity_setting_framelayout);
        getFragmentManager().beginTransaction().replace(R.id.activity_setting_framelayout, new SettingFragment()).commit();

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    /**
     * 监听配置信息改变
     */
    @Override
    public void onChangeTheme() {
        showAnimation();
        if(SharedPreferencesUtils.getBoolean("night_mode")){
            //夜间模式
            setTheme(R.style.AppTheme_night);
        }else {
            //日间模式
            setTheme(R.style.AppTheme_day);
        }
        refreshUI();
        ActivityManagerUtil.removeActivity(SettingActivity.this);
        ActivityManagerUtil.refreshAllActivities();
    }

    @Override
    public void onChangeLanguage() {
         changeLanguage();
        ActivityManagerUtil.refreshAllActivities();
    }

    private void refreshUI(){
        TypedValue background=new TypedValue();
        TypedValue toolbarColor=new TypedValue();
        Resources.Theme theme=getTheme();
        theme.resolveAttribute(R.attr.containerBackground,background,true);
        theme.resolveAttribute(R.attr.toolbarBackground,toolbarColor,true);

        frameLayout.setBackgroundResource(background.resourceId);
        toolbar.setBackgroundResource(toolbarColor.resourceId);

    }
}
