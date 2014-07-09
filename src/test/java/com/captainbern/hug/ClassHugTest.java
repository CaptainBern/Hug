package com.captainbern.hug;

import org.junit.Test;

public class ClassHugTest {

    @Test
    public void test() {
        String source = "com.captainbern.hug.TestClass";
        ClassHug hug = new ClassHug(source);

        hug.setClassName("lol");
        System.out.println("ClassName: " + hug.getClassName());

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