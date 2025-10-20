/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.type;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public final class GUID
implements Cloneable,
Comparable<GUID> {
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final byte[] hardCode;
    private static final Random seedProvider;
    public static final String EMPTY = "00000000000000000000000000000000";
    private Random random;
    private byte[] code;
    private char[] buffer;
    private char[] fullBuffer;
    private int hashcode;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public GUID() {
        Random random = seedProvider;
        synchronized (random) {
            this.random = new Random(seedProvider.nextLong());
        }
        this.code = new byte[16];
        this.buffer = new char[32];
        this.fullBuffer = new char[36];
        this.hashcode = 0;
    }

    public void generate() {
        BigInteger v1 = BigInteger.valueOf(System.currentTimeMillis());
        byte[] data = v1.toByteArray();
        if (data.length >= 8) {
            System.arraycopy(data, 0, this.code, 0, 8);
        } else {
            System.arraycopy(data, 0, this.code, 0, data.length);
            System.arraycopy(hardCode, 0, this.code, data.length, 8 - data.length);
        }
        BigInteger v2 = new BigInteger(64, this.random);
        data = v2.toByteArray();
        System.arraycopy(data, 0, this.code, 16 - data.length, data.length);
        for (int i = 0; i < 8 - data.length; ++i) {
            this.code[8 + i] = hardCode[8 - i - 1];
        }
        this.hashcode = 0;
    }

    public byte[] toBytes() {
        byte[] data = new byte[this.code.length];
        System.arraycopy(this.code, 0, data, 0, this.code.length);
        return data;
    }

    public String toString() {
        for (int i = 0; i < this.code.length; ++i) {
            this.buffer[i * 2] = HEX_CHARS[this.code[i] >> 4 & 0xF];
            this.buffer[i * 2 + 1] = HEX_CHARS[this.code[i] & 0xF];
        }
        return new String(this.buffer);
    }

    public String toFullString() {
        int fullBufferIndex = 0;
        for (int i = 0; i < this.code.length; ++i) {
            this.fullBuffer[fullBufferIndex++] = HEX_CHARS[this.code[i] >> 4 & 0xF];
            this.fullBuffer[fullBufferIndex++] = HEX_CHARS[this.code[i] & 0xF];
            if (i != 3 && i != 5 && i != 7 && i != 9) continue;
            this.fullBuffer[fullBufferIndex++] = 45;
        }
        return new String(this.fullBuffer);
    }

    public int hashCode() {
        int h = this.hashcode;
        if (h == 0) {
            for (int i = 0; i < this.code.length; ++i) {
                h = h * 31 + this.code[i];
            }
            this.hashcode = h;
        }
        return h;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof GUID)) {
            return false;
        }
        GUID other = (GUID)obj;
        if (this.hashCode() != other.hashCode()) {
            return false;
        }
        return Arrays.equals(this.code, other.code);
    }

    public Object clone() {
        try {
            GUID result = (GUID)super.clone();
            result.code = new byte[16];
            System.arraycopy(this.code, 0, result.code, 0, 16);
            result.buffer = new char[32];
            System.arraycopy(this.buffer, 0, result.buffer, 0, 32);
            return result;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public int compareTo(GUID arg0) {
        byte[] data = arg0.code;
        for (int i = 0; i < 16; ++i) {
            int ret = this.code[i] - data[i];
            if (ret == 0) continue;
            return ret;
        }
        return 0;
    }

    public static String newGUID() {
        GUID guid = new GUID();
        guid.generate();
        return guid.toString();
    }

    public static String newFullGUID() {
        GUID guid = new GUID();
        guid.generate();
        return guid.toFullString();
    }

    public static byte[] newGUIDBytes() {
        GUID guid = new GUID();
        guid.generate();
        return guid.toBytes();
    }

    public static boolean isGuid(String guid) {
        if (guid == null || guid.length() != 32) {
            return false;
        }
        for (int i = 0; i < guid.length(); ++i) {
            char ch = guid.charAt(i);
            if (GUID.isA2F(ch) || GUID.isNumber(ch)) continue;
            return false;
        }
        return true;
    }

    private static boolean isA2F(char ch) {
        return ch >= 'a' && ch <= 'f' || ch >= 'A' && ch <= 'F';
    }

    private static boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }

    static {
        Date today = new Date();
        BigInteger value = BigInteger.valueOf(today.getTime() ^ System.currentTimeMillis());
        byte[] data = value.toByteArray();
        byte[] buf = new byte[8];
        System.arraycopy(data, 0, buf, 0, data.length > 8 ? 8 : data.length);
        hardCode = buf;
        seedProvider = new SecureRandom();
        seedProvider.setSeed(today.getTime());
    }
}

