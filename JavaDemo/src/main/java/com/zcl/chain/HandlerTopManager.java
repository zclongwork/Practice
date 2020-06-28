package com.zcl.chain;

public class HandlerTopManager extends Handler {

    public HandlerTopManager(String name) {
        super(name);
    }

    @Override
    public void process(LeaveRequest leaveRequest) {
        System.out.println(name + "处理:" + getInfo(leaveRequest));
    }
}
