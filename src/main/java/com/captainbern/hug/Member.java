/*
 * This file is part of Hug.
 *
 * Hug is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Hug is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Hug (or the project it is being used in).  If not, see <http://www.gnu.org/licenses/>.
 */

package com.captainbern.hug;

import com.captainbern.hug.attribute.DefaultAttribute;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Member {

    private int accessFlags;
    private Constant name;       // Should be Utf8
    private Constant descriptor; // Should be Utf8
    private List<Attribute> metadata;

    public Member(DataInputStream inputStream, List<Constant> pool) throws IOException {
        this.accessFlags = inputStream.readUnsignedShort();
        this.name = pool.get(inputStream.readUnsignedShort());
        this.descriptor = pool.get(inputStream.readUnsignedShort());

        int metadataCount = inputStream.readUnsignedShort();
        this.metadata = new ArrayList<Attribute>();
        for (int i = 0; i < metadataCount; i++) {
            int index = inputStream.readUnsignedShort();
            this.metadata.add(new DefaultAttribute(inputStream, pool.get(index)));
        }
    }

    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(byteStream);

        outputStream.writeShort(this.accessFlags);
        outputStream.writeShort(this.name.getIndex());
        outputStream.writeShort(this.descriptor.getIndex());

        outputStream.writeShort(this.metadata.size());
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
        this.name.getPool().set(this.name.getIndex(), this.name = new Constant(Constant.CONSTANT_Utf8, this.name.getIndex(), name.getBytes(), this.name.getPool()));
    }

    public String getDescriptor() {
        return this.descriptor.rawStringValue();
    }

    public void setDescriptor(String descriptor) {
        this.descriptor.getPool().set(this.descriptor.getIndex(), this.descriptor = new Constant(Constant.CONSTANT_Utf8, this.descriptor.getIndex(), descriptor.getBytes(), this.descriptor.getPool()));
    }

    public List<Attribute> getMetadata() {
        return this.metadata;
    }
}
