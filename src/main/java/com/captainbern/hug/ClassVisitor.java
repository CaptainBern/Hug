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

public class ClassVisitor {

    protected ClassVisitor parent;

    public ClassVisitor() {}

    public ClassVisitor(ClassVisitor parent) {
        this.parent = parent;
    }

    public void visit(int version, int flags, String name, String signature, String superName, String[] interfaces) {
        if (this.parent != null)
            this.parent.visit(version, flags, name, signature, superName, interfaces);
    }

    public void visitSource(String source) {
        if (this.parent != null)
            this.parent.visitSource(source);
    }

    public void visitAttribute(Attribute attribute) {
        if (this.parent != null)
            this.parent.visitAttribute(attribute);
    }

    public void visitInterface(Interface iface) {
        if (this.parent != null)
            this.parent.visitInterface(iface);
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if (this.parent != null)
            return this.parent.visitField(access, name, desc, signature, value);
        return null;
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptionTable) {
        if (this.parent != null)
            return this.parent.visitMethod(access, name, desc, signature, exceptionTable);
        return null;
    }

    public void visitEnd() {
        if (this.parent != null)
            this.parent.visitEnd();
    }
}
