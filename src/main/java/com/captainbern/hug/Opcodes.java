/*
 * This file is part of Hug.
 *
 * Hug is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Hug is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Hug (or the project it is being used in).  If not, see <http://www.gnu.org/licenses/>.
 */

package com.captainbern.hug;

public interface Opcodes {

    public static final int JAVA_MAGIC = 0xCAFEBABE;

    /**
     * JDK versions
     */
    public static final int JDK_1_1         = 0 << 15 | 45; // Starting from 45 -> 52
    public static final int JDK_1_2         = 0 << 16 | 46;
    public static final int JDK_1_3         = 0 << 16 | 47;
    public static final int JDK_1_4         = 0 << 16 | 48;
    public static final int JDK_5           = 0 << 16 | 49;
    public static final int JDK_6           = 0 << 16 | 50;
    public static final int JDK_7           = 0 << 16 | 51;
    public static final int JDK_8           = 0 << 16 | 52;

    /**
     * Opcodes
     */
    public static final int NOP             = 0;
    public static final int ACONST_NULL     = 1;
    public static final int ICONST_M1       = 2;
    public static final int ICONST_0        = 3;
    public static final int ICONST_1        = 4;
    public static final int ICONST_2        = 5;
    public static final int ICONST_3        = 6;
    public static final int ICONST_4        = 7;
    public static final int ICONST_5        = 8;
    public static final int LCONST_0        = 9;
    public static final int LCONST_1        = 10;
    public static final int FCONST_0        = 11;
    public static final int FCONST_1        = 12;
    public static final int FCONST_2        = 13;
    public static final int DCONST_0        = 14;
    public static final int DCONST_1        = 15;
    public static final int BIPUSH          = 16;
    public static final int SIPUSH          = 17;
    public static final int LDC             = 18;
    public static final int LDC_W           = 19;
    public static final int LDC2_W          = 20;
    public static final int ILOAD           = 21;
    public static final int LLOAD           = 22;
    public static final int FLOAD           = 23;
    public static final int DLOAD           = 24;
    public static final int ALOAD           = 25;
    public static final int ILOAD_0         = 26;
    public static final int ILOAD_1         = 27;
    public static final int ILOAD_2         = 28;
    public static final int ILOAD_3         = 29;
    public static final int LLOAD_0         = 30;
    public static final int LLOAD_1         = 31;
    public static final int LLOAD_2         = 32;
    public static final int LLOAD_3         = 33;
    public static final int FLOAD_0         = 34;
    public static final int FLOAD_1         = 35;
    public static final int FLOAD_2         = 36;
    public static final int FLOAD_3         = 37;
    public static final int DLOAD_0         = 38;
    public static final int DLOAD_1         = 39;
    public static final int DLOAD_2         = 40;
    public static final int DLOAD_3         = 41;
    public static final int ALOAD_0         = 42;
    public static final int ALOAD_1         = 43;
    public static final int ALOAD_2         = 44;
    public static final int ALOAD_3         = 45;
    public static final int IALOAD          = 46;
    public static final int LALOAD          = 47;
    public static final int FALOAD          = 48;
    public static final int DALOAD          = 49;
    public static final int AALOAD          = 50;
    public static final int BALOAD          = 51;
    public static final int CALOAD          = 52;
    public static final int SALOAD          = 53;
    public static final int ISTORE          = 54;
    public static final int LSTORE          = 55;
    public static final int FSTORE          = 56;
    public static final int DSTORE          = 57;
    public static final int ASTORE          = 58;
    public static final int ISTORE_0        = 59;
    public static final int ISTORE_1        = 60;
    public static final int ISTORE_2        = 61;
    public static final int ISTORE_3        = 62;
    public static final int LSTORE_0        = 63;
    public static final int LSTORE_1        = 64;
    public static final int LSTORE_2        = 65;
    public static final int LSTORE_3        = 66;
    public static final int FSTORE_0        = 67;
    public static final int FSTORE_1        = 68;
    public static final int FSTORE_2        = 69;
    public static final int FSTORE_3        = 70;
    public static final int DSTORE_0        = 71;
    public static final int DSTORE_1        = 72;
    public static final int DSTORE_2        = 73;
    public static final int DSTORE_3        = 74;
    public static final int ASTORE_0        = 75;
    public static final int ASTORE_1        = 76;
    public static final int ASTORE_2        = 77;
    public static final int ASTORE_3        = 78;
    public static final int IASTORE         = 79;
    public static final int LASTORE         = 80;
    public static final int FASTORE         = 81;
    public static final int DASTORE         = 82;
    public static final int AASTORE         = 83;
    public static final int BASTORE         = 84;
    public static final int CASTORE         = 85;
    public static final int SASTORE         = 86;
    public static final int POP             = 87;
    public static final int POP2            = 88;
    public static final int DUP             = 89;
    public static final int DUP_X1          = 90;
    public static final int DUP_X2          = 91;
    public static final int DUP2            = 92;
    public static final int DUP2_X1         = 93;
    public static final int DUP2_X2         = 94;
    public static final int SWAP            = 95;
    public static final int IADD            = 96;
    public static final int LADD            = 97;
    public static final int FADD            = 98;
    public static final int DADD            = 99;
    public static final int ISUB            = 100;
    public static final int LSUB            = 101;
    public static final int FSUB            = 102;
    public static final int DSUB            = 103;
    public static final int IMUL            = 104;
    public static final int LMUL            = 105;
    public static final int FMUL            = 106;
    public static final int DMUL            = 107;
    public static final int IDIV            = 108;
    public static final int LDIV            = 109;
    public static final int FDIV            = 110;
    public static final int DDIV            = 111;
    public static final int IREM            = 112;
    public static final int LREM            = 113;
    public static final int FREM            = 114;
    public static final int DREM            = 115;
    public static final int INEG            = 116;
    public static final int LNEG            = 117;
    public static final int FNEG            = 118;
    public static final int DNEG            = 119;
    public static final int ISHL            = 120;
    public static final int LSHL            = 121;
    public static final int ISHR            = 122;
    public static final int LSHR            = 123;
    public static final int IUSHR           = 124;
    public static final int LUSHR           = 125;
    public static final int IAND            = 126;
    public static final int LAND            = 127;
    public static final int IOR             = 128;
    public static final int LOR             = 129;
    public static final int IXOR            = 130;
    public static final int LXOR            = 131;
    public static final int IINC            = 132;
    public static final int I2L             = 133;
    public static final int I2F             = 134;
    public static final int I2D             = 135;
    public static final int L2I             = 136;
    public static final int L2F             = 137;
    public static final int L2D             = 138;
    public static final int F2I             = 139;
    public static final int F2L             = 140;
    public static final int F2D             = 141;
    public static final int D2I             = 142;
    public static final int D2L             = 143;
    public static final int D2F             = 144;
    public static final int I2B             = 145;
    public static final int I2C             = 146;
    public static final int I2S             = 147;
    public static final int LCMP            = 148;
    public static final int FCMPL           = 149;
    public static final int FCMPG           = 150;
    public static final int DCMPL           = 151;
    public static final int DCMPG           = 152;
    public static final int IFEQ            = 153;
    public static final int IFNE            = 154;
    public static final int IFLT            = 155;
    public static final int IFGE            = 156;
    public static final int IFGT            = 157;
    public static final int IFLE            = 158;
    public static final int IF_ICMPEQ       = 159;
    public static final int IF_ICMPNE       = 160;
    public static final int IF_ICMPLT       = 161;
    public static final int IF_ICMPGE       = 162;
    public static final int IF_ICMPGT       = 163;
    public static final int IF_ICMPLE       = 164;
    public static final int IF_ACMPEQ       = 165;
    public static final int IF_ACMPNE       = 166;
    public static final int GOTO            = 167;
    public static final int JSR             = 168;
    public static final int RET             = 169;
    public static final int TABLESWITCH     = 170;
    public static final int LOOKUPSWITCH    = 171;
    public static final int IRETURN         = 172;
    public static final int LRETURN         = 173;
    public static final int FRETURN         = 174;
    public static final int DRETURN         = 175;
    public static final int ARETURN         = 176;
    public static final int RETURN          = 177;
    public static final int GETSTATIC       = 178;
    public static final int PUTSTATIC       = 179;
    public static final int GETFIELD        = 180;
    public static final int PUTFIELD        = 181;
    public static final int INVOKEVIRTUAL   = 182;
    public static final int INVOKESPECIAL   = 183;
    public static final int INVOKESTATIC    = 184;
    public static final int INVOKEINTERFACE = 185;
    public static final int INVOKEDYNAMIC   = 186;
    public static final int NEW             = 187;
    public static final int NEWARRAY        = 188;
    public static final int ANEWARRAY       = 189;
    public static final int ARRAYLENGTH     = 190;
    public static final int ATHROW          = 191;
    public static final int CHECKCAST       = 192;
    public static final int INSTANCEOF      = 193;
    public static final int MONITORENTER    = 194;
    public static final int MONITOREXIT     = 195;
    public static final int WIDE            = 196;
    public static final int MULTIANEWARRAY  = 197;
    public static final int IFNULL          = 198;
    public static final int IFNONNULL       = 199;
    public static final int GOTO_W          = 200;
    public static final int JSR_W           = 201;

    //Implementation - specific opcodes
    public static final int BREAKPOINT      = 202;
    public static final int IMPDEP1         = 254;
    public static final int IMPDEP2         = 255;

    /**
     * Types (for the newarray instruction)
     */
    public static final int T_BOOLEAN       = 4;
    public static final int T_CHAR          = 5;
    public static final int T_FLOAT         = 6;
    public static final int T_DOUBLE        = 7;
    public static final int T_BYTE          = 8;
    public static final int T_SHORT         = 9;
    public static final int T_INT           = 10;
    public static final int T_LONG          = 11;
    public static final byte T_VOID         = 12;
    public static final byte T_ARRAY        = 13;
    public static final byte T_OBJECT       = 14;
    public static final byte T_REFERENCE    = 14; // Deprecated
    public static final byte T_UNKNOWN      = 15;
    public static final byte T_ADDRESS      = 16;

    /**
     * Constant tags
     */
    public static final byte CONSTANT_Utf8                  = 1;
    public static final byte CONSTANT_Integer               = 3;
    public static final byte CONSTANT_Float                 = 4;
    public static final byte CONSTANT_Long                  = 5;
    public static final byte CONSTANT_Double                = 6;
    public static final byte CONSTANT_Class                 = 7;
    public static final byte CONSTANT_String                = 8;
    public static final byte CONSTANT_Fieldref              = 9;
    public static final byte CONSTANT_Methodref             = 10;
    public static final byte CONSTANT_InterfaceMethodref    = 11;
    public static final byte CONSTANT_NameAndType           = 12;
    public static final byte CONSTANT_MethodHandle          = 15;
    public static final byte CONSTANT_MethodType            = 16;
    public static final byte CONSTANT_InvokeDynamic         = 18;
    public static final String[] CONSTANT_NAMES = {
            "", "CONSTANT_Utf8", "", "CONSTANT_Integer",
            "CONSTANT_Float", "CONSTANT_Double", "CONSTANT_Class",
            "CONSTANT_Class", "CONSTANT_String", "CONSTANT_Fieldref",
            "CONSTANT_Methodref", "CONSTANT_InterfaceMethodref",
            "CONSTANT_NameAndType", "", "", "CONSTANT_MethodHandle",
            "CONSTANT_MethodType", "", "CONSTANT_InvokeDynamic" };

    /**
     * Attribute tags
     */
    public static final String ATTR_SOURCE_FILE                             = "SourceFile";
    public static final String ATTR_SOURCE_DEBUG_EXTENSION                  = "SourceDebugExtension";
    public static final String ATTR_CONSTANT_VALUE                		    = "ConstantValue";
    public static final String ATTR_CODE                                    = "Code";
    public static final String ATTR_EXCEPTIONS                    		    = "Exceptions";
    public static final String ATTR_LINE_NUMBER_TABLE             		    = "LineNumberTable";
    public static final String ATTR_LOCAL_VARIABLE_TABLE          		    = "LocalVariableTable";
    public static final String ATTR_INNER_CLASSES                 		    = "InnerClasses";
    public static final String ATTR_SYNTHETIC                     		    = "Synthetic";
    public static final String ATTR_DEPRECATED                    		    = "Deprecated";
    public static final String ATTR_SIGNATURE                     		    = "Signature";
    public static final String ATTR_STACK_MAP                     		    = "StackMap";
    public static final String ATTR_RUNTIME_VISIBLE_ANNOTATIONS    		    = "RuntimeVisibleAnnotations";
    public static final String ATTR_RUNTIME_INVISIBLE_ANNOTATIONS           = "RuntimeInvisibleAnnotations";
    public static final String ATTR_RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS   = "RuntimeVisibleParameterAnnotations";
    public static final String ATTR_RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS = "RuntimeInvisibleParameterAnnotations";
    public static final String ATTR_ANNOTATION_DEFAULT                      = "AnnotationDefault";
    public static final String ATTR_LOCAL_VARIABLE_TYPE_TABLE               = "LocalVariableTypeTable";
    public static final String ATTR_ENCLOSING_METHOD                        = "EnclosingMethod";
    public static final String ATTR_BOOTSTRAP_METHODS                       = "BootstrapMethods";
    public static final String ATTR_STACK_MAP_TABLE                         = "StackMapTable";
    public static final String ATTR_METHOD_PARAMETERS                       = "MethodParameters";

    /**
     * StackMap types
     *
     * @see <a href="http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.4">StackMap</a>
     */
    public static final byte ITEM_Top               = 0;
    public static final byte ITEM_Integer           = 1;
    public static final byte ITEM_Float             = 2;
    public static final byte ITEM_Double            = 3;
    public static final byte ITEM_Long              = 4;
    public static final byte ITEM_Null              = 5;
    public static final byte ITEM_UninitializedThis = 6;
    public static final byte ITEM_Object            = 7;
    public static final byte ITEM_Uninitialized     = 8;

    /**
     * Values to detect Frame growth
     *
     * @see <a href="http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.4">StackMap</a>
     */
    public static final int SAME_FRAME = 0;
    public static final int SAME_FRAME_MAX = 63;

    public static final int SAME_LOCALS_1_STACK_ITEM = 64;
    public static final int SAME_LOCALS_1_STACK_ITEM_MAX = 127;

    public static final int SAME_LOCALS_1_STACK_ITEM_EXTENDED = 247;

    public static final int CHOP_FRAME = 248;
    public static final int CHOP_FRAME_MAX = 250;

    public static final int SAME_FRAME_EXTENDED = 251;

    public static final int APPEND_FRAME = 252;
    public static final int APPEND_FRAME_MAX = 254;

    public static final int FULL_FRAME = 255;


    //For invokedynamic opcodes
    public static final int H_GETFIELD         = 1;
    public static final int H_GETSTATIC        = 2;
    public static final int H_PUTFIELD         = 3;
    public static final int H_PUTSTATIC        = 4;
    public static final int H_INVOKEVIRTUAL    = 5;
    public static final int H_INVOKESTATIC     = 6;
    public static final int H_INVOKESPECIAL    = 7;
    public static final int H_NEWINVOKESPECIAL = 8;
    public static final int H_INVOKEINTERFACE  = 9;
}
