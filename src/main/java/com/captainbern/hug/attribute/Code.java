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
import com.captainbern.hug.Constant;
import com.captainbern.hug.TryCatchBlock;

import java.util.List;

public class Code extends DefaultAttribute {

    private int maxStack;
    private int maxLocals;
    private byte[] code;
    private List<TryCatchBlock> exceptionTable;
    private List<Attribute> metadata;

    public Code(Constant type, byte[] data) {
        super(type, data);
    }
}
