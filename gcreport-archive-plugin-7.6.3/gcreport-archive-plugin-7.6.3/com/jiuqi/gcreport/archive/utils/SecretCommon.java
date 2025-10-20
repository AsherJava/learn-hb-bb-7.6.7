/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.core.encrypt.util.ByteUtils
 *  org.bouncycastle.crypto.CipherParameters
 *  org.bouncycastle.crypto.Digest
 *  org.bouncycastle.crypto.digests.SM3Digest
 *  org.bouncycastle.crypto.macs.HMac
 *  org.bouncycastle.crypto.params.KeyParameter
 */
package com.jiuqi.gcreport.archive.utils;

import com.jiuqi.nvwa.core.encrypt.util.ByteUtils;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

public class SecretCommon {
    public static String encrypt(String plainText) {
        return ByteUtils.toHexString((byte[])SecretCommon.encrypt(plainText.getBytes()));
    }

    public static byte[] encrypt(byte[] plainByte) {
        SM3Digest sm3Digest = new SM3Digest();
        sm3Digest.update(plainByte, 0, plainByte.length);
        byte[] digestByte = new byte[sm3Digest.getDigestSize()];
        sm3Digest.doFinal(digestByte, 0);
        return digestByte;
    }

    public static String encryptByKey(String keyText, String plainText) {
        return ByteUtils.toHexString((byte[])SecretCommon.encryptByKey(keyText.getBytes(), plainText.getBytes()));
    }

    public static byte[] encryptByKey(byte[] keyByte, byte[] plainByte) {
        KeyParameter keyParameter = new KeyParameter(keyByte);
        SM3Digest sm3Digest = new SM3Digest();
        HMac hMac = new HMac((Digest)sm3Digest);
        hMac.init((CipherParameters)keyParameter);
        hMac.update(plainByte, 0, plainByte.length);
        byte[] result = new byte[hMac.getMacSize()];
        hMac.doFinal(result, 0);
        return result;
    }
}

