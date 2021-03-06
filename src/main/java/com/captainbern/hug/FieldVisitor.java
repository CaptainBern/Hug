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

public class FieldVisitor {
    // TODO: Annotations etc...
    protected FieldVisitor parent;

    public FieldVisitor() {}

    public FieldVisitor(FieldVisitor parent) {
        this.parent = parent;
    }

    public void visitAttribute(Attribute attribute) {
        if (parent != null)
            this.parent.visitAttribute(attribute);
    }

    public void visitEnd() {
        if (this.parent != null)
            this.parent.visitEnd();
    }
}
