package com.captainbern.hug;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Member {

    private int accessFlags;
    private Constant name;       // Should be Utf8
    private Constant descriptor; // Should be Utf8
    private Attribute[] metadata;

    public Member(DataInputStream inputStream, Constant[] pool) throws IOException {
        this.accessFlags = inputStream.readUnsignedShort();
        this.name = pool[inputStream.readUnsignedShort()];
        this.descriptor = pool[inputStream.readUnsignedShort()];

        this.metadata = new Attribute[inputStream.readUnsignedShort()];
        for (int i = 0; i < this.metadata.length; i++) {
            int index = inputStream.readUnsignedShort();
            byte[] data = new byte[inputStream.readInt()];
            inputStream.readFully(data);
            this.metadata[i] = new Attribute(pool[index], data);
        }
    }

    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(byteStream);

        outputStream.writeShort(this.accessFlags);
        outputStream.writeShort(this.name.getIndex());
        outputStream.writeShort(this.descriptor.getIndex());

        outputStream.writeShort(this.metadata.length);
        for (Attribute attribute : this.metadata) {
            outputStream.write(attribute.getBytes());
        }

        return byteStream.toByteArray();
    }

    public int getAccessFlags() {
        return this.accessFlags;
    }

    public void setAccessFlags(int flags) {
        this.accessFlags = flags;
    }

    public String getName() {
        return this.name.rawStringValue();
    }

    public void setName(String name) {
        this.name.getPool()[this.name.getIndex()] = this.name = new Constant(Constant.CONSTANT_Utf8, this.name.getIndex(), name.getBytes(), this.name.getPool());
    }

    public String getDescriptor() {
        return this.descriptor.rawStringValue();
    }

    public void setDescriptor(String descriptor) {
        this.descriptor.getPool()[this.descriptor.getIndex()] = this.descriptor = new Constant(Constant.CONSTANT_Utf8, this.descriptor.getIndex(), descriptor.getBytes(), this.descriptor.getPool());
    }

    public Attribute[] getMetadata() {
        return this.metadata;
    }
}
