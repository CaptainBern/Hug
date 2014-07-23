package com.captainbern.hug.attribute;

import com.captainbern.hug.Attribute;
import com.captainbern.hug.Constant;
import com.captainbern.hug.TryCatchBlock;

import java.util.List;

public class Code extends DefaultAttribute {

    private int maxStack;
    private int maxLocals;
    private byte[] code;
    private List<TryCatchBlock> exceptionTable;
    private List<Attribute> metadata;

    public Code(Constant type, byte[] data) {
        super(type, data);
    }
}
