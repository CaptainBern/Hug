package com.captainbern.hug;

import org.junit.Test;

public class ClassReaderTest {

    @Test
    public void test() {
        String source = "com.captainbern.hug.TestClass";
        ClassReader hug = new ClassReader(source);

        System.out.println("ClassName: " + hug.getClassName());

        for (Constant constant : hug.getConstantPool()) {
            if (constant != null)
                System.out.println(constant.toString());
        }

        for (Member field : hug.getFields()) {
            for (Attribute attribute : field.getMetadata()) {
                System.out.println(attribute.getType());
            }

            System.out.println(field.getName() + ", " + field.getDescriptor());
        }

        for (Member method : hug.getMethods()) {
            for (Attribute attribute : method.getMetadata()) {
                System.out.println(attribute.getType());
            }

            System.out.println(method.getName() + ", " + method.getDescriptor());
        }

        Class clazz = hug.defineClass();

        try {
            System.out.println("Result: " + clazz.getDeclaredField("test").get(null));
            System.out.println("Class: " + clazz.getCanonicalName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void log(Object message) {
        System.out.println(message);
    }
}