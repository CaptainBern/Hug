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

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.captainbern.hug.Constant.*;

/**
 * A class used to patch some of our classes to work
 * with custom servers like Cauldron.
 *
 * It's some very basic bytecode editing. Also note that "this class" refers to
 * the class that is being edited. (It does not refer to <i>this</i> class object.
 */
public class ClassHug {

    // The Magic of this class file. It'd better be cafebabe or else the party won't continue
    private int magic;

    // The minor java version of this class file
    private int minor;

    // The major java version of this class
    private int major;

    // A very, very basic ConstantPool implementation
    private List<Constant> pool;

    // The access-flags of this class
    private int accessFlags;

    // The position of the name of our class in the ConstantPool
    private int thisClass;

    // Same as above but with the SuperClass
    private int superClass;

    // The interfaces, if any
    private List<Interface> interfaces;

    // The fields
    private List<Member> fields;

    // Methods
    private List<Member> methods;

    // The attributes or "metadata"
    private List<Attribute> attributes;

    // If, for some reason, you need the "old" bytecode, here it is
    private byte[] code;

    public ClassHug(byte[] b) {
        this(b, 0, b.length);
    }

    public ClassHug(byte[] b, int offset, int length) {
        assert offset < b.length && offset >= 0;
        assert length >= b.length;

        byte[] code = new byte[length];
        System.arraycopy(b, 0, code, 0, b.length);

        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(code), 8192));

        try {

            this.code = code;

            this.magic = inputStream.readInt();
            this.minor = inputStream.readUnsignedShort();
            this.major = inputStream.readUnsignedShort();

            if (major > 0x34) // Lolwat, seems like someone compiled this class with JDK 9 or above. (FYI; We're at 8 now)
                throw new RuntimeException("Unsupported class file!");

            int poolSize = inputStream.readUnsignedShort();
            this.pool = new LinkedList<Constant>();
            this.pool.add(0, null); // Reserved by the JVM

            // The first item in the pool is reserved by the jvm
            for (int i = 1; i < poolSize; i++) {
                // The type
                byte type = inputStream.readByte();
                // Used to read values with a bigger size blah
                byte[] data;
                switch (type) {
                    case CONSTANT_Utf8:
                        this.pool.add(i, new Constant(type, i, inputStream.readUTF().getBytes(), this.pool));
                        break;
                    case CONSTANT_Class:
                    case CONSTANT_String:
                    case CONSTANT_MethodType:
                        data = new byte[2];
                        inputStream.readFully(data);
                        this.pool.add(i, new Constant(type, i, data, this.pool));
                        break;
                    case CONSTANT_Integer:
                    case CONSTANT_Float:
                    case CONSTANT_FieldRef:
                    case CONSTANT_MethodRef:
                    case CONSTANT_InterfaceMethodRef:
                    case CONSTANT_NameAndType:
                    case CONSTANT_InvokeDynamic:
                        data = new byte[4];
                        inputStream.readFully(data);
                        this.pool.add(i, new Constant(type, i, data, this.pool));
                        break;
                    case CONSTANT_Long:
                    case CONSTANT_Double:
                        data = new byte[8];
                        inputStream.readFully(data);
                        this.pool.add(i, new Constant(type, i, data, this.pool));
                        i++;
                        break;
                    case CONSTANT_MethodHandle:
                        data = new byte[3];
                        inputStream.readFully(data);
                        this.pool.add(i, new Constant(type, i, data, this.pool));
                        break;
                    default:
                        throw new RuntimeException("Illegal constant-type: " + type + "!");
                }
            }

            this.accessFlags = inputStream.readUnsignedShort();
            this.thisClass = inputStream.readUnsignedShort();
            this.superClass = inputStream.readUnsignedShort();

            int interfaceCount = inputStream.readUnsignedShort();
            this.interfaces = new ArrayList<Interface>();
            for (int i = 0; i < interfaceCount; i++) {
                this.interfaces.add(new Interface(this.pool.get(inputStream.readUnsignedShort())));
            }

            int fieldCount = inputStream.readUnsignedShort();
            this.fields = new ArrayList<Member>();
            for (int i = 0; i < fieldCount; i++) {
                this.fields.add(new Member(inputStream, this.pool));
            }

            int methodCount = inputStream.readUnsignedShort();
            this.methods = new ArrayList<Member>();
            for (int i = 0; i < methodCount; i++) {
                this.methods.add(new Member(inputStream, this.pool));
            }

            int attributeCount = inputStream.readUnsignedShort();
            this.attributes = new ArrayList<Attribute>();
            for (int i = 0; i < attributeCount; i++) {
                int index = inputStream.readUnsignedShort();
                byte[] data = new byte[inputStream.readInt()];
                inputStream.readFully(data);
                this.attributes.add(new Attribute(pool.get(index), data));
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to hug class: " + b.toString() + "!", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                // We'll just swallow this... For now.
            }
        }
    }

    /**
     * Constructs a new ClassHug with the given source.
     * The "source" = the canonical class name.
     * So for the Object class, this would be java.lang.Object
     * @param classSource
     */
    public ClassHug(String classSource) {
        this(toBytes(ClassLoader.getSystemResourceAsStream(classSource.replace('.', '/') + ".class"), true));
    }

    /**
     * Creates a new ClassHug with the given inputstream.
     * @param inputStream
     */
    public ClassHug(InputStream inputStream) {
        this(toBytes(inputStream, false));
    }

    /**
     * Converts a given InputStream to a byte-array.
     * @param inputStream
     * @param close
     * @return
     */
    private static byte[] toBytes(final InputStream inputStream, boolean close) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[inputStream.available()];

            int n;
            while (-1 != (n = inputStream.read(buffer))) {
                outputStream.write(buffer, 0, n);
            }

            return buffer;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (close) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Returns the raw-bytecode representation of this class.
     * @return
     */
    public byte[] getHugCode() {
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

    /**
     * Writes this class to the given OutputStream.
     */
    public final void write(DataOutputStream codeStream) throws IOException {
        codeStream.writeInt(this.magic);

        codeStream.writeShort(this.minor);
        codeStream.writeShort(this.major);

        codeStream.writeShort(this.pool.size());
        for (int i = 1; i < this.pool.size(); i++) {
            Constant constant = this.pool.get(i);
            if (constant != null) {
                codeStream.write(constant.getBytes());
            }
        }

        codeStream.writeShort(this.accessFlags);
        codeStream.writeShort(this.thisClass);
        codeStream.writeShort(this.superClass);

        codeStream.writeShort(this.interfaces.size());
        for (Interface iface : this.interfaces) {
            codeStream.write(iface.getBytes());
        }

        codeStream.writeShort(this.fields.size());
        for (Member field : this.fields) {
            codeStream.write(field.getBytes());
        }

        codeStream.writeShort(this.methods.size());
        for (Member method : this.methods) {
            codeStream.write(method.getBytes());
        }

        codeStream.writeShort(this.attributes.size());
        for (Attribute attribute : this.attributes) {
            codeStream.write(attribute.getBytes());
        }

        codeStream.flush();
    }

    /**
     * Returns the "original" bytecode of this class
     * @return
     */
    public byte[] getUneditedCode() {
        return this.code;
    }

    public List<Constant> getConstantPool() {
        return this.pool;
    }

    /**
     * Returns the access flags of this class
     * @return
     */
    public int getAccessFlags() {
        return this.accessFlags;
    }

    /**
     * Allows you to set the access flags of this class
     * @param accessFlags
     */
    public void setAccessFlags(int accessFlags) {
        this.accessFlags = accessFlags;
    }

    /**
     * Returns the name of this class
     * @return
     */
    public String getClassName() {
        Constant constant = this.pool.get(this.thisClass);

        if (constant.getType() == CONSTANT_Class) {
            byte[] val = constant.getRawData();
            int index = ((val[0] << 8) | (val[1]));

            Constant utf8 = this.pool.get(index);
            if (utf8.getType() == CONSTANT_Utf8)
                return utf8.rawStringValue();
        }

        throw new RuntimeException("Failed to return the Class-name! Perhaps a corrupted ConstantPool?");
    }

    /**
     * Allows you to set the name of this class
     * @param className
     */
    public void setClassName(String className) {
        Constant constant = this.pool.get(this.thisClass);

        if (constant.getType() == CONSTANT_Class) {
            byte[] val = constant.getRawData();
            int index = ((val[0] << 8) | val[1]);

            Constant utf8 = this.pool.get(index);
            if (utf8.getType() == CONSTANT_Utf8) {
                utf8.setRawData(className.getBytes());
                return;
            }
        }

        throw new RuntimeException("Failed to set the Class-name! Perhaps a corrupted ConstantPool?");
    }

    /**
     * Returns the name of the super-class of this class
     * @return
     */
    public String getSuperClassName() {
        Constant constant = this.pool.get(this.superClass);

        if (constant.getType() == CONSTANT_Class) {
            byte[] val = constant.getRawData();
            int index = ((val[0] << 8) | (val[1]));

            Constant utf8 = this.pool.get(index);
            if (utf8.getType() == CONSTANT_Utf8)
                return utf8.rawStringValue();
        }

        throw new RuntimeException("Failed to return the SuperClass-name! Perhaps a corrupted ConstantPool?");
    }

    /**
     * Allows you to set the super class of this class
     * @param className
     */
    public void setSuperClassName(String className) {
        Constant constant = this.pool.get(this.superClass);

        if (constant.getType() == CONSTANT_Class) {
            byte[] val = constant.getRawData();
            int index = ((val[0] << 8) | val[1]);

            Constant utf8 = this.pool.get(index);
            if (utf8.getType() == CONSTANT_Utf8) {
                utf8.setRawData(className.getBytes());
                return;
            }
        }

        throw new RuntimeException("Failed to set the SuperClass-name! Perhaps a corrupted ConstantPool?");
    }

    /**
     * Returns the interfaces of this class
     * @return
     */
    public List<Interface> getInterfaces() {
        return this.interfaces;
    }

    /**
     * Returns the fields of this class
     * @return
     */
    public List<Member> getFields() {
        return this.fields;
    }

    /**
     * Returns the methods of this class
     * @return
     */
    public List<Member> getMethods() {
        return this.methods;
    }

    /**
     * Returns the attributes or "metadata" of this class
     * @return
     */
    public List<Attribute> getAttributes() {
        return this.attributes;
    }

    /**
     * Creates a Class Object of the byte-code of this class. This method
     * should be fairly safe unless you messed up something.
     * @return
     */
    public Class giveAHug() {
        return new ClassLoader() {
            public Class defineClass(String name, byte[] code) {
                return super.defineClass(name, code, 0, code.length);
            }
        }.defineClass(this.getClassName().replace('/', '.'), this.getHugCode());
    }
}
