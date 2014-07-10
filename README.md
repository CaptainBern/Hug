Hug
===

Hug is a very small and compact library which can be used for bytecode manipulation.

Hug is very abstract and very, very low level. A basic set of utility classes is delivered
and can be used to do specific operations easier. (renaming a method, remapping a class etc)

Basic usage
===========

Hug is very easy to use. here's a small code example:

```Java
ClassHug hug = new ClassHug("java.lang.Object"); // Can also be raw bytes or an inputstream

for (Member field : hug.getFields()) {

    System.out.println(field.getDescriptor() + " -> " + field.getName());

}

```