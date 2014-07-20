package com.captainbern.hug;

public class MethodVisitor {

    protected MethodVisitor parent;

    public MethodVisitor() {}

    public MethodVisitor(MethodVisitor parent) {
        this.parent = parent;
    }

    public void visitParameter(String name, int access) {
        if (this.parent != null)
            this.parent.visitParameter(name, access);
    }

    public void visitAttribute(Attribute attribute) {
        if (this.parent != null)
            this.parent.visitAttribute(attribute);
    }

    public void visitCode() {
        if (this.parent != null)
            this.parent.visitCode();
    }

    // TODO: Annotations + frame + Extra visit-instruction methods

    public void visitEnd() {
        if (this.parent != null)
            this.parent.visitEnd();
    }
}
