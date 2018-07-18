package com.xczn.smos.ui.fragment.home;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xczn.smos.R;
import com.xczn.smos.base.BaseBackFragment;
import com.xczn.smos.utils.SharedPreferencesUtils;

import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @Author zhangxiao
 * @Date 2018/4/23 0023
 * @Comment SVG页面
 */

public class SvgFragment extends BaseBackFragment {

    public static final String TAG = SvgFragment.class.getName();
    private WebView svgWebView;
    private ProgressBar progressBar;
    private boolean isFirst = true;
    private Timer timer;
    private SvgTimerTask svgTimerTask;
    //private ImageView imageView;

    public static SvgFragment newInstance() {
        return new SvgFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_svg, container, false);
        //_mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        timer = new Timer();
        initView(view);
        initProgressBar();
        initWebView();
        startSvgTimer();
        return view;
    }

    private void initProgressBar() {
        //progressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setMax(100);
        progressBar.setProgressDrawable(this.getResources().getDrawable(R.drawable.color_progressbar));

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        final WebSettings webSettings = svgWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //webSettings.setSupportZoom(true);

        svgWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView webView,int newProgress){
                if (isFirst) {
                    progressBar.setProgress(newProgress);
                    if (progressBar != null && newProgress != 100) {
//                      WebView加载没有完成 就显示我们自定义的加载图
                        progressBar.setVisibility(VISIBLE);
                    } else if (progressBar != null) {
//                      WebView加载完成 就隐藏进度条,显示WebView
                        progressBar.setVisibility(GONE);
                        isFirst = false;
                        //imageView.setVisibility(GONE);
                    }
                }
            }
        });

        svgWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url){
                if (!url.isEmpty()){
                    int index = url.lastIndexOf("/");
                    String newUrl = url.substring(index+1, url.length()-4);
                    newUrl = SharedPreferencesUtils.getInstance().getBaseUrl() + "svgs?name=" + newUrl;
                    Log.d(TAG, "shouldOverrideUrlLoading: "+newUrl);
                    webView.loadUrl(newUrl);
                }
                return false;

            }
        });
        String url = SharedPreferencesUtils.getInstance().getBaseUrl();
        svgWebView.loadUrl(url+"svgs?name=首页");
    }

    private void initView(View view) {
        svgWebView = view.findViewById(R.id.svg_webView);
        progressBar = view.findViewById(R.id.svg_progressBar);
//        Toolbar toolbar = view.findViewById(R.id.toolbar);
//        initToolbarNav(toolbar);
    }

    private void startSvgTimer(){
        if (timer != null) {
            if (svgTimerTask != null) {
                svgTimerTask.cancel();
            }
        }
        svgTimerTask = new SvgTimerTask();
        timer.schedule(svgTimerTask, 1000,5000);
    }
    private void stopSvgTimer(){
        if (timer != null) {
            if (svgTimerTask != null) {
                svgTimerTask.cancel();
            }
        }
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                svgWebView.reload();
                Log.d(TAG, "handleMessage: " +"svgReload");
            }
            super.handleMessage(msg);
        }
    };

    class SvgTimerTask extends TimerTask{
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    public void onDestroyView() {

        super.onDestroyView();
        isFirst = true;
        stopSvgTimer();
        Log.d(TAG, "onDestroyView: ");
        //_mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }
}
