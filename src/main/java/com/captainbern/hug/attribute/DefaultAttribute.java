package com.captainbern.hug.attribute;

import com.captainbern.hug.Attribute;
import com.captainbern.hug.Bytes;
import com.captainbern.hug.Constant;

import java.io.DataInputStream;
import java.io.IOException;

public class DefaultAttribute extends Attribute {

    private byte[] data;

    public DefaultAttribute(Constant type, byte[] data) {
        super(type, data.length);
        this.data = data;
    }

    public DefaultAttribute(DataInputStream inputStream, Constant type) throws IOException {
        super(type, inputStream.readInt());
        this.data = new byte[this.length];
        inputStream.readFully(this.data);
    }

    @Override
    public byte[] getBytes() {
        this.length = this.data.length; // Safety-check
        return Bytes.merge(super.getBytes(), this.data);
    }

    @Override
    public byte[] getData() {
        return this.data;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
        this.length = data.length;
    }
}
