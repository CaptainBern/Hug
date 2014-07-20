package com.captainbern.hug.code;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class CodeGenerator {

    public CodeGenerator(byte[] byteCode, int pos) {
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(byteCode), 8192));

        int length = byteCode.length;

        for (int i = 0; i < length; i++) {

        }
    }
}
