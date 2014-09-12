package com.captainbern.hug;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClassWriterTest {

    @Test
    public void testClassWriter() {
        ClassWriter writer = new ClassWriter();
        writer.visit(0 << 16 | 51, 0x0001, "Test", null, "java/lang/Object", null);
        writer.visitSource("Test.java");
        writer.visitField(0x0001 + 0x0008, "testField", "Ljava/lang/String;", null, null);

        byte[] code = writer.getByteCode();

        Class<?> clazz = new ClassLoader() {
            public Class define(byte[] bytes, String name) {
                return super.defineClass(name, bytes, 0, bytes.length);
            }
        }.define(code, "Test");

        System.out.println(clazz.getCanonicalName());

        ClassReader reader = new ClassReader(code);

        for (Attribute attribute : reader.getAttributes()) {
            if (attribute.getType().equalsIgnoreCase("SourceFile")) {
                System.out.println(reader.getConstantPool().get(Bytes.toShort(attribute.getData())));
            }
        }

        try {
            System.out.println("Field0: " + clazz.getDeclaredFields()[0].getName());
            System.out.println("    Old Value: " + clazz.getDeclaredFields()[0].get(null));
            clazz.getDeclaredFields()[0].set(null, "Changed!");
            System.out.println("    New Value: " + clazz.getDeclaredFields()[0].get(null));

            assertEquals("Changed!", clazz.getDeclaredFields()[0].get(null));
        } catch (Exception e) {
            throw new RuntimeException("Failed to get/set the field!", e);
        }
    }
}