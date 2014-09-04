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
