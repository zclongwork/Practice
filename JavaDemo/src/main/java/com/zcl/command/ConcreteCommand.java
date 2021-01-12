package com.zcl.command;

public class ConcreteCommand implements Command{
    private Receiver receiver;

    public ConcreteCommand(Receiver receiverRole1) {
        this.receiver = receiverRole1;
    }

    @Override
    public void execute() {
        /*
         * 可以加入命令排队等等，未执行的命令支持redo操作
         */
        receiver.opActionUpdateAge(26);//执行具体的命令操作
    }

    @Override
    public void undo() {
        receiver.rollBackAge();//执行具体的撤销回滚操作
    }

}
