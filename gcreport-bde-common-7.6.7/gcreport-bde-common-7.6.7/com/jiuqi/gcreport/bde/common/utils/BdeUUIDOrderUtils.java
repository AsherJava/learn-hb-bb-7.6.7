/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.hardware.HardwareUtil
 */
package com.jiuqi.gcreport.bde.common.utils;

import com.jiuqi.bi.util.hardware.HardwareUtil;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public final class BdeUUIDOrderUtils {
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final byte[] hardCode;
    private static final Random seedProvider;
    private Random random;
    private byte[] code;
    private char[] buffer;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public BdeUUIDOrderUtils() {
        Random random = seedProvider;
        synchronized (random) {
            this.random = new Random(seedProvider.nextLong());
        }
        this.code = new byte[16];
        this.buffer = new char[36];
    }

    public void generate() {
        BigInteger v1 = BigInteger.valueOf(System.nanoTime());
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
        BdeUUIDOrderUtils guid = new BdeUUIDOrderUtils();
        guid.generate();
        return guid.toUUID();
    }

    public static String newUUIDStr() {
        BdeUUIDOrderUtils guid = new BdeUUIDOrderUtils();
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

    static {
        Date today = new Date();
        byte[] buf = HardwareUtil.getMachineSign((String)"GUID");
        if (buf == null) {
            BigInteger value = BigInteger.valueOf(today.getTime() ^ System.currentTimeMillis());
            byte[] data = value.toByteArray();
            buf = new byte[8];
            System.arraycopy(data, 0, buf, 0, data.length > 8 ? 8 : data.length);
        }
        hardCode = buf;
        seedProvider = new SecureRandom();
        seedProvider.setSeed(today.getTime());
    }
}

