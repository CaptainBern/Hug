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
import java.util.LinkedList;
import java.util.List;

public class ClassWriter extends ClassVisitor {

    private int version;

    // A key used to lookup items in the ConstantPool
    private Constant key;

    private int index;

    private List<Constant> constantPool;

    private int access;

    private int className;

    private String thisClass;

    private int signature;

    private int superClass;

    private String superName;

    private List<Interface> interfaces;

    // TODO: fields + method writers

    private List<Member> fields;

    private List<Member> methods;

    private List<Attribute> attributes;

    public ClassWriter() {
        super();
        this.constantPool = new LinkedList<Constant>();
        this.interfaces = new ArrayList<Interface>();
        this.attributes = new ArrayList<Attribute>();

        this.constantPool.add(0, null); // The first item is reserved by the JVM
        this.index = 1;
    }

    @Override
    public void visit(int version, int flags, String name, String signature, String superName, String[] interfaces) {
        this.version = version;
        this.access = flags;

    }

    /**
     * ConstantPool stuff
     */

    private Constant newConstant(Object value) {
        return null;
    }

    private Constant newUTF(String utf) {
        key = new Constant(Constant.CONSTANT_Utf8, 0, utf.getBytes(), this.constantPool);
        Constant result = get(key);
        if (result == null) {
            result = new Constant(Constant.CONSTANT_Utf8, this.index++, utf.getBytes(), this.constantPool);
            this.constantPool.add(index, result);
        }

        return result;
    }

    private Constant newInteger(int i) {
        return null;
    }

    private Constant newFloat(float f) {
        return null;
    }

    private Constant newLong(long l) {
        return null;
    }

    private Constant newDouble(double d) {
        return null;
    }

    private Constant newClass(String clazz) {
        return null;
    }

    private Constant newString(String utf) {
        return null;
    }

    private Constant newField(int access, String name, String desc) {
        return null;
    }

    private Constant newMethod(int access, String name, String desc) {
        return null;
    }

    private Constant newInterfaceMethodRef() {
        return null;
    }

    private Constant newNameAndType() {
        return null;
    }

    private Constant newMethodHandle() {
        return null;
    }

    private Constant newMethodType() {
        return null;
    }

    private Constant newInvokeDynamic() {
        return null;
    }

    // May be a bit slow but well...
    private Constant get(Constant key) {
        for (Constant constant : this.constantPool) {
            if (constant.isEqualTo(key))
                return constant;
        }

        return null;
    }
}
