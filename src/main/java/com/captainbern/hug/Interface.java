package com.captainbern.hug;

public class Interface {

    private Constant constant; // Constant in the ConstantPool

    public Interface(Constant constant) {
        this.constant = constant;
    }

    public byte[] getBytes() {
        return Bytes.toByteArray(this.constant.getIndex());
    }

    public String getValue() {
        return this.constant.rawStringValue();
    }

    public void setValue(String value) {
        this.constant.getPool()[this.constant.getIndex()] = this.constant = new Constant(
                Constant.CONSTANT_Utf8,
                this.constant.getIndex(),
                value.getBytes(),
                this.constant.getPool()
        );
    }
}
