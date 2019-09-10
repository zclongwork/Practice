package com.zcl.practice.mvp.view;

import com.zcl.practice.mvp.bean.User;

/**
 * Description TODO
 * Author ZhangChenglong
 * Date 18/1/10 12:01
 */

public interface IUserLoginView {
    String getUserName();
    
    String getPassword();
    
    void clearUserName();
    
    void clearPassword();
    
    void showLoading();
    
    void hideLoading();
    
    void toMainActivity(User user);
    
    void showFailedError();
}
