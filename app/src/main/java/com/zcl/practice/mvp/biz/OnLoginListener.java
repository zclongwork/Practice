package com.zcl.practice.mvp.biz;

import com.zcl.practice.mvp.bean.User;

/**
 * Description TODO
 * Author ZhangChenglong
 * Date 18/1/10 13:04
 */

public interface OnLoginListener {
    void loginSuccess(User user);
    
    void loginFailed();
}
