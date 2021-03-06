package com.weidingqiang.wanbase.ui.main.contract;

import com.weidingqiang.rxqwelibrary.base.BasePresenter;
import com.weidingqiang.rxqwelibrary.base.BaseView;
import com.weidingqiang.wanbase.model.bean.FeedArticleListData;

/**
 * Created by Lionel2Messi
 */
public interface NewsContract {

    interface View extends BaseView {
        //返回登陆结果
        void responeError(String errorMsg);

        void getFeedArticleListSuccess(FeedArticleListData feedArticleListData);
    }

    interface Presenter extends BasePresenter<View> {
        void getFeedArticleList(int num);
    }
}