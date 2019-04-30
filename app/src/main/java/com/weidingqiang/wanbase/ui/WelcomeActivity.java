package com.weidingqiang.wanbase.ui;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weidingqiang.rxqwelibrary.base.SimpleActivity;
import com.weidingqiang.wanbase.R;
import com.weidingqiang.wanbase.app.AppContext;
import com.weidingqiang.wanbase.ui.login.activity.LoginActivity;
import com.weidingqiang.wanbase.ui.main.MainActivity;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

/**
 * 2.权限        要     4
 * 3.rebing
 * 4.路由
 * 5.列表        要     5
 * 7.下载        要     11
 * 8.升级        要     10
 * 10.上传图片   要     12
 * 11.音频
 * 12.视频
 * 13.webview    要    6
 * 14.弹框       要     7
 * 15.全面屏
 * 17.图片加载 圆角图片   要          8
 * 18.topbar  bottombar  自定义组件   要     9
 */

/**
 * 已存在的功能
 * 1.log
 * 2.autolayout 适配
 * 3.qmui皮肤
 * 4.bga欢迎页面
 * 5.多状态
 * 6.rxjava dagger
 * 7.walle打包
 * 8.fragmentation管理
 * 9.utilcodex工具类
 * 10.BaseRecyclerViewAdapterHelper
 */

public class WelcomeActivity extends SimpleActivity {

    private LinearLayout welcome_bg;

    private RelativeLayout banner_guide;

    private BGABanner mBackgroundBanner;
    private BGABanner mForegroundBanner;

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initEventAndData() {
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }

        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        welcome_bg = (LinearLayout) this.findViewById(R.id.welcome_bg);
        banner_guide = (RelativeLayout) this.findViewById(R.id.banner_guide);

        //判断第一次进入
        SharedPreferences shared=getSharedPreferences("is", MODE_PRIVATE);
        boolean isfer=shared.getBoolean("isfer", true);
//        boolean isfer = true;
        SharedPreferences.Editor editor=shared.edit();
        if(isfer){
            //第一次

            editor.putBoolean("isfer", false);
            editor.commit();

            banner_guide.setVisibility(View.VISIBLE);
            welcome_bg.setVisibility(View.GONE);

            mBackgroundBanner = (BGABanner)this.findViewById(R.id.banner_guide_background);
            mForegroundBanner = (BGABanner) this.findViewById(R.id.banner_guide_foreground);

            setListener();
            processLogic();
        }
        else {
            //第二次进入跳转
            welcome_bg.setVisibility(View.VISIBLE);

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.app_start_anim);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                //动画完成
                @Override
                public void onAnimationEnd(Animation animation) {
                    initApp(animation);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            welcome_bg.setAnimation(animation);
        }
    }

    private void setListener() {
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        mForegroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
//                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                initApp(null);
            }
        });
    }

    private void processLogic() {
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        // 设置数据源
        mBackgroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.uoko_guide_background_1,
                R.drawable.uoko_guide_background_2,
                R.drawable.uoko_guide_background_3);

        mForegroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.uoko_guide_foreground_1,
                R.drawable.uoko_guide_foreground_2,
                R.drawable.uoko_guide_foreground_3);
    }

    private void initApp(Animation animation){
        if(!AppContext.getInstance().initLogin())
        {
            if(animation != null){
                welcome_bg.setAnimation(animation);
            }
            return;
        }

        new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        if(AppContext.getInstance().isLogin())
                        {
                            startActivity(MainActivity.newInstance(getApplicationContext()));
                        }
                        else{
                            startActivity(LoginActivity.newInstance(getApplicationContext()));
                        }
                        finish();
                    } else {
                        // Oups permission denied
                        ToastUtils.showLong("您没有授权该权限，请在设置中打开授权");
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();

        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        if(mBackgroundBanner != null){
            mBackgroundBanner.setBackgroundResource(android.R.color.white);
        }
    }


}
