package com.zcl.practice.mvp.biz;

/**
 * Description TODO
 * Author ZhangChenglong
 * Date 18/1/10 13:04
 */

public interface IUserBiz {
    public void login(String username, String password, OnLoginListener loginListener);
}
