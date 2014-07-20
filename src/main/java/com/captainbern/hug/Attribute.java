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

public abstract class Attribute {

    protected Constant type;
    protected int length;

    public Attribute(Constant type, int length) {
        this.type = type;
        this.length = length;
    }

    public byte[] getBytes() {
        return Bytes.merge(Bytes.toByteArray((short) this.type.getIndex()), Bytes.toByteArray(this.length));
    }

    public String getType() {
        return this.type.rawStringValue();
    }

    public void setType(String type) {
        this.type.getPool().set(this.type.getIndex(), this.type = new Constant(
                Constant.CONSTANT_Utf8,
                this.type.getIndex(),
                type.getBytes(),
                this.type.getPool()));
    }

    public int getLength() {
        return this.length;
    }

    public abstract byte[] getData();

    public abstract void setData(byte[] data);
}
