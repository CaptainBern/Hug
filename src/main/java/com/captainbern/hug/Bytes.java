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

public class Bytes {

    public static byte[] merge(byte[] a, byte... b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static byte[] rMerge(byte[] b, byte... a) {
        return merge(a, b);
    }

    public static byte[] toByteArray(int i) {
        return new byte[] {
                (byte) ((i >> 24) & 0x0FF),
                (byte) ((i >> 16) & 0x0FF),
                (byte) ((i >> 8) & 0x0FF),
                (byte) ((i) & 0x0FF)

        };
    }

    public static byte[] toByteArray(short s) {
        return new byte[] {
                (byte) ((s >> 8) & 0x0FF),
                (byte) ((s) & 0x0FF)
        };
    }

    public static byte[] toByteArray(long l) {
        return new byte[] {
                (byte) ((l >> 56) & 0x0FF),
                (byte) ((l >> 48) & 0x0FF),
                (byte) ((l >> 40) & 0x0FF),
                (byte) ((l >> 32) & 0x0FF),
                (byte) ((l >> 24) & 0x0FF),
                (byte) ((l >> 16) & 0x0FF),
                (byte) ((l >> 8) & 0x0FF),
                (byte) ((l) & 0x0FF)
        };
    }

    public static byte[] toByteArray(double d) {
        return toByteArray(Double.doubleToRawLongBits(d));
    }

    public static byte[] toByteArray(float f) {
        return toByteArray(Float.floatToRawIntBits(f));
    }

    public static int toInt(byte[] bytes) {
        return ((bytes[0] & 0x0FF) << 24) |
                ((bytes[1] & 0x0FF) << 16) |
                ((bytes[2] & 0x0FF) << 8) |
                ((bytes[3] & 0x0FF));

    }

    public static int toShort(byte[] bytes) {
        return ((bytes[0] & 0x0FF) << 8) |
                (bytes[1] & 0x0FF);
    }

    public static long toLong(byte[] bytes) {
        return ((bytes[0] & 0x0FF) << 56) |
                ((bytes[1] & 0x0FF) << 48) |
                ((bytes[2] & 0x0FF) << 40) |
                ((bytes[3] & 0x0FF) << 32) |
                ((bytes[4] & 0x0FF) << 24) |
                ((bytes[5] & 0x0FF) << 16) |
                ((bytes[6] & 0x0FF) << 8) |
                ((bytes[7] & 0x0FF));
    }

    public static double toDouble(byte[] bytes) {
        return Double.longBitsToDouble(toLong(bytes));
    }

    public static float toFloat(byte[] bytes) {
        return Float.intBitsToFloat(toInt(bytes));
    }
}
