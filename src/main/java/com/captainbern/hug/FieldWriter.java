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
