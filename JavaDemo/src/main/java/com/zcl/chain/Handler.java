package com.zcl.chain;

public abstract class Handler {
    protected String name; // 处理者姓名
    protected Handler nextHandler;  // 下一个处理者

    public Handler(String name) {
        this.name = name;
    }

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }



    public abstract void process(LeaveRequest leaveRequest); // 处理请假


    protected String getInfo(LeaveRequest leaveRequest) {
        return leaveRequest.name + "的" + leaveRequest.days + "天假期";
    }
}
