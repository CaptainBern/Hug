package com.captainbern.hug;

import org.junit.Test;

public class ClassHugTest {

    @Test
    public void test() {
        String source = "com.captainbern.hug.ClassHug";
        ClassHug hug = new ClassHug(source);

        System.out.println("ClassName: " + hug.getClassName());

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

        Class clazz = hug.giveAHug();

        try {
            System.out.println("Result: " + clazz.getDeclaredField("test").get(null));
            System.out.println("Class: " + clazz.getCanonicalName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}