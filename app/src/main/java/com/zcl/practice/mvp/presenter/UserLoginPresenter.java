package com.zcl.practice.mvp.presenter;

import android.os.Handler;

import com.zcl.practice.mvp.bean.User;
import com.zcl.practice.mvp.biz.IUserBiz;
import com.zcl.practice.mvp.biz.OnLoginListener;
import com.zcl.practice.mvp.biz.UserBiz;
import com.zcl.practice.mvp.view.IUserLoginView;

/**
 * Description TODO
 * Author ZhangChenglong
 * Date 18/1/10 12:00
 */

public class UserLoginPresenter {
    private IUserBiz userBiz;
    private IUserLoginView userLoginView;
    private Handler mHandler = new Handler();
    
    public UserLoginPresenter(IUserLoginView userLoginView) {
        this.userLoginView = userLoginView;
        this.userBiz = new UserBiz();
    }
    
    public void login() {
        userLoginView.showLoading();
        userBiz.login(userLoginView.getUserName(), userLoginView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(final User user) {
                //需要在UI线程执行
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.toMainActivity(user);
                        userLoginView.hideLoading();
                    }
                });
                
            }
            
            @Override
            public void loginFailed() {
                //需要在UI线程执行
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.showFailedError();
                        userLoginView.hideLoading();
                    }
                });
                
            }
        });
    }
    
    public void clear() {
        userLoginView.clearUserName();
        userLoginView.clearPassword();
    }
}
