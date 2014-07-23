package com.captainbern.hug;

import org.junit.Test;

public class ClassReaderTest {

    @Test
    public void test() {
        String source = "com.captainbern.hug.TestClass";
        ClassReader hug = new ClassReader(source);

        log("ClassName: " + hug.getClassName());

        for (Constant constant : hug.getConstantPool()) {
            if (constant != null)
                log(constant.toString());
        }

        for (Member field : hug.getFields()) {
            for (Attribute attribute : field.getMetadata()) {
                log(attribute.getType());
            }

            log(field.getName() + ", " + field.getDescriptor());
        }

        for (Member method : hug.getMethods()) {
            for (Attribute attribute : method.getMetadata()) {
                log(attribute.getType());
            }

            log(method.getName() + ", " + method.getDescriptor());
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