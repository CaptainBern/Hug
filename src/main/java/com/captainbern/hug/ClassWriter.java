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

import com.captainbern.hug.attribute.DefaultAttribute;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public class ClassWriter extends ClassVisitor {

    protected int version;

    // A key used to lookup items in the ConstantPool
    protected Constant key;

    protected int index;

    protected List<Constant> constantPool;

    protected int access;

    protected int className;

    protected String thisClass;

    protected int signature;

    protected int superClass;

    protected String superName;

    protected List<Interface> interfaces;

    // TODO: fields + method writers

    protected List<Member> fields;

    protected FieldWriter firstFieldWriter;

    protected FieldWriter lastFieldWriter;

    protected List<Member> methods;

    protected List<Attribute> attributes;

    protected int source;

    public ClassWriter() {
        super();
        // The index of the items does matter here, hence why we use a LinkedList
        this.constantPool = new LinkedList<Constant>();
        this.interfaces = new ArrayList<Interface>();
        this.fields = new ArrayList<Member>();
        this.methods = new ArrayList<Member>();
        this.attributes = new ArrayList<Attribute>();

        this.constantPool.add(0, null); // The first item is reserved by the JVM
        this.index = 0; // Have to set this to 0, else it will put the next item at index '2'
    }

    @Override
    public void visit(int version, int flags, String name, String signature, String superName, String[] interfaces) {
        this.version = version;
        this.access = flags;
        this.className = newClass(name).getIndex();
        this.thisClass = name;
        this.superClass = superName == null ? 0 : newClass(superName).getIndex();
        this.superName = superName;

        if (interfaces != null && interfaces.length > 0) {
            for (String iface : interfaces) {
                this.interfaces.add(new Interface(newUTF(iface)));
            }
        }
    }

    @Override
    public void visitSource(String source) {
        if (source != null) {
            this.source = newUTF(source).getIndex();
        }
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        if (attribute != null) {
            this.attributes.add(attribute);
        }
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        return new FieldWriter(this, access, name, desc, signature, value);
    }

    /* Returns the raw-bytecode representation of this class.
     * @return
     */
    public byte[] getByteCode() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8192);
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream, 8192));

        try {
            write(dataOutputStream);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            try {
                dataOutputStream.close();
            } catch (IOException ioe1) {
                ioe1.printStackTrace();
            }
        }

        return byteArrayOutputStream.toByteArray();
    }

    public final void write(DataOutputStream outputStream) throws IOException {
        // Build the field list
        FieldWriter fieldWriter = this.firstFieldWriter;
        while (fieldWriter != null) {
            this.fields.add(fieldWriter.build());
            fieldWriter = (FieldWriter) firstFieldWriter.parent;
        }

        if (this.source != 0) {
            this.attributes.add(new DefaultAttribute(newUTF("SourceFile"), Bytes.toByteArray((short) this.source)));
        }

        // Finally start writing the class

        outputStream.writeInt(0xcafebabe);

        outputStream.writeInt(this.version);

        outputStream.writeShort(this.constantPool.size());
        for (int i = 1; i < this.constantPool.size(); i++) {
            if (this.constantPool.get(i) != null)
                outputStream.write(this.constantPool.get(i).getBytes());
        }

        outputStream.writeShort(this.access);
        outputStream.writeShort(this.className);
        outputStream.writeShort(this.superClass);

        outputStream.writeShort(this.interfaces.size());
        for (Interface iface : this.interfaces) {
            outputStream.write(iface.getBytes());
        }

        outputStream.writeShort(this.fields.size());
        for (Member member : this.fields) {
            outputStream.write(member.getBytes());
        }

        outputStream.writeShort(this.methods.size());
        for (Member member : this.methods) {
            outputStream.write(member.getBytes());
        }

        outputStream.writeShort(this.attributes.size());
        for (Attribute attribute : this.attributes) {
            outputStream.write(attribute.getBytes());
        }

        outputStream.flush();
    }

    /**
     * ConstantPool stuff
     */
    private static final String METHODREF_DELIM  = ":";
    private static final String IMETHODREF_DELIM = "#";
    private static final String FIELDREF_DELIM   = "&";
    private static final String NAT_DELIM        = "%";

    private Map<String, Integer> utfToIndex = new HashMap<String, Integer>();   // Used to lookup UTF-8 values in the pool
    private Map<String, Integer> stringToIndex = new HashMap<String, Integer>(); // Used to lookup CONSTANT_String values in the pool
    private Map<String, Integer> natToIndex = new HashMap<String, Integer>(); // used to lookup nat values in the pool

    protected Constant newUTF(String utf) {
        if (utf == null)
            return null;

        Constant result = null;

        if (this.utfToIndex.containsKey(utf)) {
            result = this.constantPool.get(this.utfToIndex.get(utf));
        }

        if (result == null || result.getType() != Constant.CONSTANT_Utf8) {
            this.index++;
            result = new Constant(Constant.CONSTANT_Utf8, this.index, utf.getBytes(), this.constantPool);
            this.utfToIndex.put(utf, this.index);
            this.constantPool.add(index, result);
        }
        return result;
    }

    protected Constant newInteger(int i) {
        key = new Constant(Constant.CONSTANT_Integer, 0, Bytes.toByteArray(i), this.constantPool);
        Constant result = get(key);
        if (result == null) {
            this.index++;
            result = new Constant(Constant.CONSTANT_Integer, this.index, Bytes.toByteArray(i), this.constantPool);
            this.constantPool.add(index, result);
        }
        return result;
    }

    protected Constant newFloat(float f) {
        key = new Constant(Constant.CONSTANT_Float, 0, Bytes.toByteArray(f), this.constantPool);
        Constant result = get(key);
        if (result == null) {
            this.index++;
            result = new Constant(Constant.CONSTANT_Float, this.index, Bytes.toByteArray(f), this.constantPool);
            this.constantPool.add(index, result);
        }
        return result;
    }

    protected Constant newLong(long l) {
        key = new Constant(Constant.CONSTANT_Long, 0, Bytes.toByteArray(l), this.constantPool);
        Constant result = get(key);
        if (result == null) {
            this.index++;
            result = new Constant(Constant.CONSTANT_Long, this.index, Bytes.toByteArray(l), this.constantPool);
            this.constantPool.add(index, result);
        }
        return result;
    }

    protected Constant newDouble(double d) {
        key = new Constant(Constant.CONSTANT_Double, 0, Bytes.toByteArray(d), this.constantPool);
        Constant result = get(key);
        if (result == null) {
            this.index++;
            result = new Constant(Constant.CONSTANT_Double, this.index, Bytes.toByteArray(d), this.constantPool);
            this.constantPool.add(index, result);
        }
        return result;
    }

    protected Constant newClass(String clazz) {
        if (clazz == null)
            return null;

        Constant utf = newUTF(clazz);

        Constant result = null;

        if (this.stringToIndex.containsKey(clazz)) {
            result = this.constantPool.get(this.stringToIndex.get(clazz));
        }

        if (result == null || result.getType() != Constant.CONSTANT_Class) {
            this.index++;
            result = new Constant(Constant.CONSTANT_Class, this.index, Bytes.toByteArray((short) utf.getIndex()), this.constantPool);
            this.stringToIndex.put(clazz, this.index);
            this.constantPool.add(index, result);
        }
        return result;
    }

    protected Constant newString(String string) {
        Constant utf = newUTF(string);

        Constant result = null;

        if (this.stringToIndex.containsKey(string)) {
            result = this.constantPool.get(this.stringToIndex.get(string));
        }

        if (result == null || result.getType() != Constant.CONSTANT_String) {
            this.index++;
            result = new Constant(Constant.CONSTANT_String, this.index, Bytes.toByteArray(utf.getIndex()), this.constantPool);
            this.stringToIndex.put(string, this.index);
            this.constantPool.add(index, result);
        }
        return result;
    }

    protected Constant newField(int access, String name, String desc) {
        return null;
    }

    protected Constant newMethod(int access, String name, String desc) {
        return null;
    }

    protected Constant newInterfaceMethodRef() {
        return null;
    }

    protected Constant newNameAndType(String name, String type) {
        Constant result = null;

        if (this.natToIndex.containsKey(name + NAT_DELIM + type)) {
            result = this.constantPool.get(this.natToIndex.get(name + NAT_DELIM + type));
        }

        if (result == null || result.getType() != Constant.CONSTANT_NameAndType) {
            int nameIndex = newUTF(name).getIndex();
            int typeIndex = newUTF(type).getIndex();
            this.index++;
            result = new Constant(Constant.CONSTANT_NameAndType, this.index, Bytes.merge(
                    Bytes.toByteArray((short) nameIndex),
                    Bytes.toByteArray((short) typeIndex)), this.constantPool);
            this.natToIndex.put(name + NAT_DELIM + type, this.index);
            this.constantPool.add(this.index, result);
        }
        return result;
    }

    protected Constant newMethodHandle() {
        return null;
    }

    protected Constant newMethodType() {
        return null;
    }

    protected Constant newInvokeDynamic() {
        return null;
    }

    // May be a bit slow but well...
    private Constant get(Constant key) {
        for (Constant constant : this.constantPool) {
            if (constant != null && constant.isEqualTo(key))
                return constant;
        }

        return null;
    }
}
