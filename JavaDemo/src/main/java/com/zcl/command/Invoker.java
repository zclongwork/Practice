package com.zcl.command;

public class Invoker {
    private Command command;
    //持有多个命令对象[实际的情况也可能是一个命令对象的集合来保存命令对象]

    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * 执行正常命令
     */
    public void invoke() {
        //可以根据具体情况选择执行某些命令
        command.execute();
    }
}
