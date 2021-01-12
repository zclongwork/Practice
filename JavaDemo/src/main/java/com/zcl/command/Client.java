package com.zcl.command;

public class Client {
    /**
     * 组装操作
     */
    public void assembleAction() {
        //创建一个命令接收者
        Receiver receiver = new Receiver();
        //创建一个命令的具体实现对象，并指定命令接收者
        Command command = new ConcreteCommand(receiver);

        Invoker invokerRole = new Invoker();//创建一个命令调用者
        invokerRole.setCommand(command);//为调用者指定命令对象
        invokerRole.invoke();//发起调用命令请求
    }
}
