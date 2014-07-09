package com.captainbern.hug;

public class Attribute {

    private Constant type;
    private byte[] data;

    public Attribute(Constant type, byte[] data) {
        this.type = type;
        this.data = data;
    }

    public byte[] getBytes() {
        return Bytes.merge(Bytes.toByteArray((short) this.type.getIndex()), Bytes.merge(Bytes.toByteArray(this.data.length), data));
    }

    public String getType() {
        return this.type.rawStringValue();
    }

    public int getLength() {
        return this.data.length;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
