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

import java.util.ArrayList;
import java.util.List;

public class FieldWriter extends FieldVisitor {

    private final ClassWriter classWriter;

    private final int access;

    private final Constant name;

    private final Constant desc;

    private final Constant signature;

    private List<Attribute> attributeList;

    public FieldWriter(ClassWriter classWriter, int access, String name, String desc, String signature, Object value) {
        if (classWriter.firstFieldWriter == null) {
            classWriter.firstFieldWriter = this;
        } else {
            classWriter.lastFieldWriter.parent = this;
        }
        classWriter.lastFieldWriter = this;

        this.classWriter = classWriter;
        this.access = access;
        this.name = classWriter.newUTF(name);
        this.desc = classWriter.newUTF(desc);
        this.signature = classWriter.newUTF(signature);
        this.attributeList = new ArrayList<Attribute>();
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        if (attribute != null) {
            this.attributeList.add(attribute);
        }
    }

    protected Member build() {
        return new Member(this.access, this.name, this.desc, this.attributeList);
    }
}
