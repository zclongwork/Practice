package com.zcl.chain;

public class HandlerDirector extends Handler {


    public HandlerDirector(String name) {
        super(name);
    }

    @Override
    public void process(LeaveRequest leaveRequest) {
        if (leaveRequest.days<=3) {
            System.out.println(name + "处理:" + getInfo(leaveRequest) );
        } else {
            System.out.println(name + "处理 -- 超出权限:" + getInfo(leaveRequest));
            nextHandler.process(leaveRequest);
        }

    }
}
