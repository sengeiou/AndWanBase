package com.weidingqiang.wanbase.model;

import com.weidingqiang.wanbase.model.bean.UserVO;
import com.weidingqiang.wanbase.model.http.HttpHelper;
import com.weidingqiang.wanbase.model.http.response.HttpResponse;
import com.weidingqiang.wanbase.model.prefs.PreferencesHelper;

import io.reactivex.Flowable;

/**
 * 作者：weidingqiang on 2017/7/11 09:55
 * 邮箱：dqwei@iflytek.com
 */

public class DataManager implements HttpHelper, PreferencesHelper {

    HttpHelper mHttpHelper;

    PreferencesHelper mPreferencesHelper;

    public DataManager(HttpHelper httpHelper, PreferencesHelper mPreferencesHelper) {
        mHttpHelper = httpHelper;
        this.mPreferencesHelper = mPreferencesHelper;
    }


    @Override
    public Flowable<HttpResponse<UserVO>> postLogin(String username, String password) {
        return mHttpHelper.postLogin(username, password);
    }

    @Override
    public boolean getNightModeState() {
        return false;
    }

    @Override
    public void setNightModeState(boolean state) {

    }
}
