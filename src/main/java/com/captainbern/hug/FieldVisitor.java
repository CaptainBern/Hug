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
