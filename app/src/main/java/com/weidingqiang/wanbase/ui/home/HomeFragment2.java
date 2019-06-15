package com.weidingqiang.wanbase.ui.home;

import android.os.Bundle;

import com.weidingqiang.wanbase.R;
import com.weidingqiang.wanbase.base.RootFragment;
import com.weidingqiang.wanbase.model.bean.FeedArticleListData;
import com.weidingqiang.wanbase.ui.main.contract.NewsContract;
import com.weidingqiang.wanbase.ui.main.presenter.NewsPresenter;

/**
 * Created by Lionel2Messi
 */
public class HomeFragment2 extends RootFragment<NewsPresenter> implements NewsContract.View {


    public static HomeFragment2 newInstance() {
        Bundle args = new Bundle();

        HomeFragment2 fragment = new HomeFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_2;
    }

    @Override
    protected void initEventAndData() {

    }


    @Override
    public void responeError(String errorMsg) {

    }

    @Override
    public void getFeedArticleListSuccess(FeedArticleListData feedArticleListData) {

    }
}