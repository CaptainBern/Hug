package com.captainbern.hug;

public class TryCatchBlock {

    private int startPc;
    private int endpc;
    private int handlerPc;
    private int catchType;

    public TryCatchBlock(int start, int end, int handler, int catchType) {
        this.startPc = start;
        this.endpc = end;
        this.handlerPc = handler;
        this.catchType = catchType;
    }

    public int getStartPc() {
        return this.startPc;
    }

    public int getEndpc() {
        return this.endpc;
    }

    public int getHandlerPc() {
        return this.handlerPc;
    }

    public int getCatchType() {
        return this.catchType;
    }

    public byte[] getBytes() {
        return Bytes.merge(
                Bytes.toByteArray((short) this.startPc),
                Bytes.merge(Bytes.toByteArray((short) this.endpc),
                        Bytes.merge(Bytes.toByteArray((short) this.handlerPc),
                        Bytes.toByteArray((short) this.catchType))));
    }
}
