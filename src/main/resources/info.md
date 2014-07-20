basic bytecode blah blah aka my notes
=====================================

CONSTANT_Class:
    fields:
	    tag: 7
		name_index: -> CONSTANT_Utf8 in the pool

CONSTANT_Fieldref, CONSTANT_Methodref, CONSTANT_InterfaceMethodref:
    fields:
	    tag: 9, 10, 11
		class_index: -> CONSTANT_Class in the pool
		name_and_type_index: -> CONSTANT_NameAndType in the pool

CONSTANT_String:
    field:
        tag: 8
		string_index: -> CONSTANT_Utf8 in the pool

CONSTANT_Integer, CONSTANT_Float:
    fields:
	    tag: 3, 4
		bytes: data

CONSTANT_Long, CONSTANT_Double:
    fields:
	    tag: 5, 6
		high_bytes: (don't worry about those)
		low_bytes: (don't worry about those)

CONSTANT_NameAndType:
    fields:
	    tag: 12
		name_index: -> CONSTANT_Utf8 in the pool
		descriptor_index: -> CONSTANT_Utf8 in the pool

CONSTANT_Utf8:
    fields:
	    tag: 1
		length: (don't worry about these)
		bytes[length]: (don't worry about these)

CONSTANT_MethodHandle:
    fields:
	    tag: 15
		reference_kind: number in the range 1 to 9
		reference_index: See http://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.8 for more info

CONSTANT_MethodType:
    fields:
	    tag: 16
		descriptor_index: -> CONSTANT_Utf8 in the pool

CONSTANT_InvokeDynamic
    fields:
	    tag: 18
		bootstrap_method_attr_index: valid item in bootstrap_methods table of the classfile
		name_and_type_index: -> CONSTANT_NameAndType in the pool




frames
======
tfuck is this

Classes
=======

cafebabe
minor
major
poolsize
pool[poolsize]
access
this
super
interfacesCount
interfaces[interfacesCount]
fieldCount
fields[fieldCount]
methodCount
methods[methodCount]
attributeCount
attributes[attributeCount]



ConstantPool gen is easy





















