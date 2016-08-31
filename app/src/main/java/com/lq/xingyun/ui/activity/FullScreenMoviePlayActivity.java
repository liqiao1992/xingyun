package com.lq.xingyun.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.lq.xingyun.R;
import com.lq.xingyun.presenter.BasePresenter;
import com.lq.xingyun.utils.LogUtil;
import com.lq.xingyun.widget.MediaController;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;

/**
 * Created by lenovo on 2016/8/26.
 */
public class FullScreenMoviePlayActivity extends BaseActivity {
    private PLVideoTextureView plVideoTextureView;
    private MediaController mMediaController;
    private String videoPath;
    private Toast mToast = null;
    private String videoTitle = "";
    private final Handler mHideHandler = new Handler();
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHideHandler.postDelayed(hideRunnable, 10 * 1000);
    }



    @Override
    public int getToolBarId() {
        return R.id.activity_movie_play_toolbar;
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    public void setActionBar() {
        getSupportActionBar().setTitle(videoTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void getIntentValue() {
        super.getIntentValue();
        videoPath = getIntent().getStringExtra("VIDEO_PATH");
        videoTitle = getIntent().getStringExtra("VIDEO_TITLE");
    }


    @Override
    public void bindView(Bundle savedInstanceState) {
        plVideoTextureView = (PLVideoTextureView) findViewById(R.id.activity_movie_play_VideoView);
        View loadingView = findViewById(R.id.activity_movie_play_LoadingView);
        plVideoTextureView.setBufferingIndicator(loadingView);

        AVOptions options = new AVOptions();
        if (isLiveStreaming(videoPath)) {
            options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
            options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
            options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
        }
        int codec = getIntent().getIntExtra("mediaCodec", 0);
        options.setInteger(AVOptions.KEY_MEDIACODEC, codec);
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);
        plVideoTextureView.setAVOptions(options);

        mMediaController = new MediaController(this, true, isLiveStreaming(videoPath));
        plVideoTextureView.setMediaController(mMediaController);

        plVideoTextureView.setOnCompletionListener(mOnCompletionListener);
        plVideoTextureView.setOnErrorListener(mOnErrorListener);

        plVideoTextureView.setVideoPath(videoPath);
        plVideoTextureView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_16_9);
        plVideoTextureView.start();
    }

    private Runnable hideRunnable = new Runnable() {
        @Override
        public void run() {
            hideStatusBarAndToolBar();
        }
    };

    private void showStatusBarAndToolBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            plVideoTextureView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
        }
    }

    private void hideStatusBarAndToolBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            plVideoTextureView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.i("fuck", "activity页面点击了");
                mHideHandler.removeCallbacks(hideRunnable);
                showStatusBarAndToolBar();
                break;
            case MotionEvent.ACTION_UP:
                mHideHandler.postDelayed(hideRunnable, 10 * 1000);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mToast = null;
        plVideoTextureView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        plVideoTextureView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        plVideoTextureView.stopPlayback();
    }


    private PLMediaPlayer.OnCompletionListener mOnCompletionListener = new PLMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(PLMediaPlayer plMediaPlayer) {
            showToastTips("Play Completed!");
            finish();
        }
    };
    private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer mp, int errorCode) {
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                    showToastTips("Invalid URL !");
                    break;
                case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                    showToastTips("404 resource not found !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                    showToastTips("Connection refused !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                    showToastTips("Connection timeout !");
                    break;
                case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                    showToastTips("Empty playlist !");
                    break;
                case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                    showToastTips("Stream disconnected !");
                    break;
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    showToastTips("Network IO Error !");
                    break;
                case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                    showToastTips("Unauthorized Error !");
                    break;
                case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                    showToastTips("Prepare timeout !");
                    break;
                case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                    showToastTips("Read frame timeout !");
                    break;
                case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
                default:
                    showToastTips("unknown error !");
                    break;
            }
            finish();
            return true;
        }
    };

    private void showToastTips(final String tips) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(FullScreenMoviePlayActivity.this, tips, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    private boolean isLiveStreaming(String url) {
        if (url.startsWith("rtmp://")
                || (url.startsWith("http://") && url.endsWith(".m3u8"))
                || (url.startsWith("http://") && url.endsWith(".flv"))) {
            return true;
        }
        return false;
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_movie_play;
    }
}
