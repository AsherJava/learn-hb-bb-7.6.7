/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.upload.modeling;

import com.jiuqi.nr.bpm.exception.BpmException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class ProcessBuilderUtils {
    public static String MD5(String input) {
        if (input == null || input.length() == 0) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("SHA-256");
            md5.update(input.getBytes());
            byte[] byteArray = md5.digest();
            BigInteger bigInt = new BigInteger(1, byteArray);
            String result = bigInt.toString(16);
            while (result.length() < 32) {
                result = "0" + result;
            }
            return "a" + result;
        }
        catch (NoSuchAlgorithmException e) {
            throw new BpmException(e);
        }
    }

    public static String produceUUIDKey(String code) {
        UUID fromCode = ProcessBuilderUtils.fromCode(code);
        return fromCode.toString();
    }

    public static UUID fromCode(String code) {
        int i;
        byte[] name = code.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = ProcessBuilderUtils.getMessageDigest();
        byte[] md5Bytes = md.digest(name);
        md5Bytes[6] = (byte)(md5Bytes[6] & 0xF);
        md5Bytes[6] = (byte)(md5Bytes[6] | 0x30);
        md5Bytes[8] = (byte)(md5Bytes[8] & 0x3F);
        md5Bytes[8] = (byte)(md5Bytes[8] | 0x80);
        long msb = 0L;
        long lsb = 0L;
        assert (md5Bytes.length == 16) : "data must be 16 bytes in length";
        for (i = 0; i < 8; ++i) {
            msb = msb << 8 | (long)(md5Bytes[i] & 0xFF);
        }
        for (i = 8; i < 16; ++i) {
            lsb = lsb << 8 | (long)(md5Bytes[i] & 0xFF);
        }
        return new UUID(msb, lsb);
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException nsae) {
            throw new InternalError("SHA-256 not supported", nsae);
        }
        return md;
    }
}

