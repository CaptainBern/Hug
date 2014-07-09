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
