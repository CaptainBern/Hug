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

import java.util.List;

public class Constant {

    // Constant op-codes
    public static final byte CONSTANT_Utf8                  = 1;
    public static final byte CONSTANT_Integer               = 3;
    public static final byte CONSTANT_Float                 = 4;
    public static final byte CONSTANT_Long                  = 5;
    public static final byte CONSTANT_Double                = 6;
    public static final byte CONSTANT_Class                 = 7;
    public static final byte CONSTANT_String                = 8;
    public static final byte CONSTANT_FieldRef              = 9;
    public static final byte CONSTANT_MethodRef             = 10;
    public static final byte CONSTANT_InterfaceMethodRef    = 11;
    public static final byte CONSTANT_NameAndType           = 12;
    public static final byte CONSTANT_MethodHandle          = 15;
    public static final byte CONSTANT_MethodType            = 16;
    public static final byte CONSTANT_InvokeDynamic         = 18;
    public static final String[] CONSTANT_NAMES = new String[] {"INVALID", "CONSTANT_Utf8", "INVALID",
            "CONSTANT_Integer", "CONSTANT_Float", "CONSTANT_Long", "CONSTANT_Double",
            "CONSTANT_Class", "CONSTANT_String", "CONSTANT_FieldRef", "CONSTANT_MethodRef",
            "CONSTANT_InterfaceMethodRef", "CONSTANT_NameAndType", "INVALID", "INVALID", "INVALID",
            "CONSTANT_MethodHandle", "CONSTANT_MethodType", "INVALID", "CONSTANT_InvokeDynamic"
    };

    private byte type;

    private int index;

    private byte[] data;

    private List<Constant> pool;

    public Constant(byte type, int index, byte[] data, List<Constant> pool) {
        this.type = type;
        this.index = index;
        this.data = data;
        this.pool = pool;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public byte[] getRawData() {
        return this.data;
    }

    public void setRawData(byte[] data) {
        this.data = data;
    }

    public byte[] getBytes() {
        if (this.type == CONSTANT_Utf8) {
            return Bytes.rMerge(Bytes.merge(new byte[]{(byte) (this.data.length >> 8), (byte) this.data.length}, this.data), this.type);
        }
        return Bytes.rMerge(this.data, this.type);
    }

    public String rawStringValue() {
        try {
            switch (this.type) {
                case CONSTANT_Utf8:
                    return new String(this.data, "UTF-8");
                default:
                    return this.data.toString();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to return the raw string value of: " + Integer.toHexString(this.type));
        }
    }

    public List<Constant> getPool() {
        return this.pool;
    }

    public boolean isEqualTo(Constant other) {
        return other.toString().equals(this.toString());
    }

    @Override
    public String toString() {

        switch (this.type) {
            case CONSTANT_Utf8:
                return this.rawStringValue();
            case CONSTANT_Integer:
                return String.valueOf(Bytes.toInt(this.data));
            case CONSTANT_Float:
                return String.valueOf(Bytes.toFloat(this.data));
            case CONSTANT_Long:
                return String.valueOf(Bytes.toLong(this.data));
            case CONSTANT_Double:
                return String.valueOf(Bytes.toDouble(this.data));
            case CONSTANT_Class:
            case CONSTANT_String:
            case CONSTANT_MethodType:
                return this.pool.get(Bytes.toShort(this.data)).rawStringValue();
            case CONSTANT_FieldRef:
            case CONSTANT_MethodRef:
            case CONSTANT_InterfaceMethodRef:
                int classIndex;
                int nameAndTypeIndex;
                byte[] indexNameAndTypeBits = new byte[2];
                System.arraycopy(this.data, 0, indexNameAndTypeBits, 0, 2);
                classIndex = Bytes.toShort(indexNameAndTypeBits);
                System.arraycopy(this.data, 2, indexNameAndTypeBits, 0, 2);
                nameAndTypeIndex = Bytes.toShort(indexNameAndTypeBits);

                return this.pool.get(classIndex).toString() + "&" + this.pool.get(nameAndTypeIndex).toString();
            case CONSTANT_NameAndType:
                byte[] nameTypeBits = new byte[2];
                System.arraycopy(this.data, 0, nameTypeBits, 0, 2);
                int nameIndex = Bytes.toShort(nameTypeBits);
                System.arraycopy(this.data, 2, nameTypeBits, 0, 2);
                int typeIndex = Bytes.toShort(nameTypeBits);

                return this.pool.get(nameIndex).rawStringValue() + "%" + this.pool.get(typeIndex).rawStringValue();
            case CONSTANT_MethodHandle:
                int kind;
                int index;
                byte[] kindIndexBits = new byte[2];
                System.arraycopy(this.data, 0, kindIndexBits, 0, 2);
                kind = Bytes.toShort(kindIndexBits);
                System.arraycopy(this.data, 2, kindIndexBits, 0, 2);
                index = Bytes.toShort(kindIndexBits);

                return kind + "#" + this.pool.get(index).toString();
            case CONSTANT_InvokeDynamic:
                int bootstrap_index;
                int nameTypeIndex;
                byte[] indexBits = new byte[2];
                System.arraycopy(this.data, 0, indexBits, 0, 2);
                bootstrap_index = Bytes.toShort(indexBits);
                System.arraycopy(this.data, 2, indexBits, 0, 2);
                nameTypeIndex = Bytes.toShort(indexBits);

                return bootstrap_index + "$" + this.pool.get(nameTypeIndex).toString();
            default:
                return "<Unknown>";
        }
    }
}