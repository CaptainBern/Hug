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
