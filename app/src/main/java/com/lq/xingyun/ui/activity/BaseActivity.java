package com.lq.xingyun.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.lq.xingyun.R;
import com.lq.xingyun.inter.IBase;
import com.lq.xingyun.presenter.BasePresenter;
import com.lq.xingyun.ui.view.IBaseView;
import com.lq.xingyun.utils.ActivityManagerUtil;
import com.lq.xingyun.utils.SharedPreferencesUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.Locale;

/**
 * Created by lenovo on 2016/7/28.
 */
public abstract class BaseActivity extends AppCompatActivity implements IBase {

    protected BasePresenter mPresenter;
    protected View mRootView;
    protected Toolbar mToolbar;
    private View mNightView = null;
    private boolean mIsAddedView;
    private WindowManager mWindowManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityManagerUtil.addActivity(this);
        initTheme();
        changeLanguage();
        super.onCreate(savedInstanceState);
        mIsAddedView = false;
        if (SharedPreferencesUtils.getBoolean("night_mode")) {
//            initNightView();
//            mNightView.setBackgroundResource(R.color.night_mask);
        }
        mPresenter = getPresenter();
        if (mPresenter != null && this instanceof IBaseView) {
            mPresenter.attach((IBaseView) this);
        }
        getIntentValue();
        setContentView(getContentLayout());
        mToolbar = (Toolbar) findViewById(getToolBarId());
        initWindow();
        setSupportActionBar(mToolbar);

        bindView(savedInstanceState);
        setActionBar();

    }
    protected void changeLanguage(){
        int languageIndex=SharedPreferencesUtils.getInteger("language_mode");
        if(languageIndex>-1) {
            String language = null;
            String country = null;
            switch (languageIndex) {
                case 0:
                    language = "en";
                    country = "US";
                    break;
                case 1:
                    language = "zh";
                    country = "CN";
                    break;
                case 2:
                    language = "zh";
                    country = "TW";
                    break;
            }
            Locale locale = new Locale(language, country);
            Configuration configuration = getResources().getConfiguration();
            configuration.locale = locale;
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        }
    }
    public void initTheme() {
        if (SharedPreferencesUtils.getBoolean( "night_mode")) {
            setTheme(R.style.AppTheme_night);
        } else {
            setTheme(R.style.AppTheme_day);
        }

    }

    protected void ChangeToDay() {
//        showAnimation();
        if (mIsAddedView && mNightView != null) {
            mWindowManager.removeViewImmediate(mNightView);
        }
    }

    protected void ChangeToNight() {
//        showAnimation();
        initNightView();
    }

    protected void initNightView() {
        if (mIsAddedView)
            return;
        WindowManager.LayoutParams mNightViewParam = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                PixelFormat.TRANSPARENT);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mNightView = new View(this);
        mNightView.setBackgroundResource(R.color.night_mask);
        mWindowManager.addView(mNightView, mNightViewParam);
        mIsAddedView = true;
    }

    public void showAnimation() {
        Log.i("fuck","开始动画");
        Bitmap cacheBitmap = getCaptureView();
        if (getWindow().getDecorView() instanceof ViewGroup && cacheBitmap != null) {
            Log.i("fuck","进入动画开始--------------------");
            final View view = new View(this);
            view.setBackgroundDrawable(new BitmapDrawable(getResources(), cacheBitmap));
            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) getWindow().getDecorView()).addView(view, layoutParam);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            objectAnimator.setDuration(3000);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) getWindow().getDecorView()).removeView(view);
                }
            });
            objectAnimator.start();
        }
    }

    private Bitmap getCaptureView() {
        View view = getWindow().getDecorView();
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        view.destroyDrawingCache();
        return bitmap;
    }

    public void getIntentValue() {

    }

    public void setActionBar() {

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //重写IBase中的方法

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public View getView() {
        return null;
    }

    //是否设置沉浸式状态栏
    protected boolean isSetStatusBar() {
        return false;
    }

    public abstract int getToolBarId();

    public Toolbar getToolbar() {
        return mToolbar;
    }

    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isSetStatusBar()) {
            Log.i("fuck", "设置沉浸");
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setNavigationBarTintEnabled(true);
//            tintManager.setStatusBarTintColor(Color.parseColor("#20000000"));
//            tintManager.setStatusBarTintResource(R.color.toolBarBgColor);
            mToolbar.getLayoutParams().height = getAppBarHeight();
            mToolbar.setPadding(mToolbar.getPaddingLeft(),
                    getStatusBarHeight(),
                    mToolbar.getPaddingRight(),
                    mToolbar.getPaddingBottom());
            mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        }
    }

    private int getAppBarHeight() {
        return dip2px(56) + getStatusBarHeight();
    }


    private int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
