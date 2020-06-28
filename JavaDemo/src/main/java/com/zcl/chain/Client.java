package com.zcl.chain;

public class Client {
    public static void main(String[] args) {
        Handler handler1 = new HandlerDirector("主管");
        Handler handler2 = new HandlerManager("经理");
        Handler handler3 = new HandlerTopManager("总经理");

        handler1.setNextHandler(handler2);
        handler2.setNextHandler(handler3);


        handler1.process(new LeaveRequest("张三", 3));
        handler1.process(new LeaveRequest("李四", 5));
        handler1.process(new LeaveRequest("王五", 9));
    }

}
