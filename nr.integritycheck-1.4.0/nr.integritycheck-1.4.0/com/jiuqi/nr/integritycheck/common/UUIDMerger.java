/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.integritycheck.common;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UUIDMerger {
    public static String merge(String runId, String checkId) {
        int i;
        byte[] bytes1 = runId.getBytes(StandardCharsets.UTF_8);
        byte[] bytes2 = checkId.getBytes(StandardCharsets.UTF_8);
        int minLength = Math.min(bytes1.length, bytes2.length);
        byte[] resultBytes = new byte[16];
        for (i = 0; i < minLength; ++i) {
            int n = i % 16;
            resultBytes[n] = (byte)(resultBytes[n] ^ (byte)(bytes1[i] ^ bytes2[i]));
        }
        if (bytes1.length > minLength) {
            for (i = minLength; i < bytes1.length; ++i) {
                int n = i % 16;
                resultBytes[n] = (byte)(resultBytes[n] ^ bytes1[i]);
            }
        } else if (bytes2.length > minLength) {
            for (i = minLength; i < bytes2.length; ++i) {
                int n = i % 16;
                resultBytes[n] = (byte)(resultBytes[n] ^ bytes2[i]);
            }
        }
        ByteBuffer buffer = ByteBuffer.wrap(resultBytes);
        long mostSigBits = buffer.getLong();
        long leastSigBits = buffer.getLong();
        return new UUID(mostSigBits, leastSigBits).toString();
    }
}

