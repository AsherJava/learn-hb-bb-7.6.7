/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.gcreport.common.util;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.util.SnowflakeIdGenerator;
import java.math.BigInteger;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class UUIDOrderSnowUtils
extends UUIDUtils {
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static byte[] hardCode = new byte[0];
    private final byte[] code;
    private final char[] buffer;

    public UUIDOrderSnowUtils() {
        byte[] buf = new byte[8];
        ThreadLocalRandom.current().nextBytes(buf);
        hardCode = buf;
        this.code = new byte[16];
        this.buffer = new char[36];
    }

    public void generate() {
        BigInteger v1 = BigInteger.valueOf(SnowflakeIdGenerator.nextId());
        byte[] data = v1.toByteArray();
        if (data.length >= 8) {
            System.arraycopy(data, 0, this.code, 0, 8);
        } else {
            System.arraycopy(data, 0, this.code, 0, data.length);
            System.arraycopy(hardCode, 0, this.code, data.length, 8 - data.length);
        }
        System.arraycopy(hardCode, 0, this.code, 8, 8);
    }

    public UUID toUUID() {
        int index = 0;
        for (int i = 0; i < this.code.length; ++i) {
            if (i == 4 || i == 6 || i == 8 || i == 10) {
                this.buffer[i * 2 + index++] = 45;
            }
            this.buffer[i * 2 + index] = HEX_CHARS[this.code[i] >> 4 & 0xF];
            this.buffer[i * 2 + 1 + index] = HEX_CHARS[this.code[i] & 0xF];
        }
        return UUID.fromString(new String(this.buffer));
    }

    public static UUID newUUID() {
        UUIDOrderSnowUtils guid = new UUIDOrderSnowUtils();
        guid.generate();
        return guid.toUUID();
    }

    public static String newUUIDStr() {
        UUIDOrderSnowUtils guid = new UUIDOrderSnowUtils();
        guid.generate();
        return guid.toString();
    }

    public String toString() {
        int index = 0;
        for (int i = 0; i < this.code.length; ++i) {
            if (i == 4 || i == 6 || i == 8 || i == 10) {
                this.buffer[i * 2 + index++] = 45;
            }
            this.buffer[i * 2 + index] = HEX_CHARS[this.code[i] >> 4 & 0xF];
            this.buffer[i * 2 + 1 + index] = HEX_CHARS[this.code[i] & 0xF];
        }
        return new String(this.buffer);
    }
}

