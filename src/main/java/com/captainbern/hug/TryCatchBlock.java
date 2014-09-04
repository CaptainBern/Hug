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
