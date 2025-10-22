/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.lib.reg.internal;

import com.jiuqi.nr.single.lib.reg.SingleElaes;
import com.jiuqi.nr.single.lib.reg.internal.alaes.AlaesConsts;
import com.jiuqi.nr.single.lib.util.SingleConvert;

public class SingleElaesImpl
implements SingleElaes {
    @Override
    public void encryptAESFile(String sourceFileName, String key, String destFileName) {
        AlaesConsts.encryptAESFile(sourceFileName, key, destFileName);
    }

    @Override
    public void decryptAESFile(String sourceFileName, String key, String destFileName) {
        AlaesConsts.decryptAESFile(sourceFileName, key, destFileName);
    }

    @Override
    public String encryptAESString(String sourceString, String key, boolean hex) {
        return AlaesConsts.encryptAESString(sourceString, key, hex);
    }

    @Override
    public String decryptAESString(String sourceString, int size, String key, boolean hex) {
        return AlaesConsts.decryptAESString(sourceString, size, key, hex);
    }

    @Override
    public String decryptAESString(byte[] sourceData, String key, boolean hex) {
        return AlaesConsts.decryptAESString(sourceData, key, hex);
    }

    @Override
    public byte[] decryptAESStringToBytes(byte[] sourceData, String key, boolean hex) {
        return AlaesConsts.decryptAESStringToBytes(sourceData, key, hex);
    }

    @Override
    public String decryptAESStringFromFile(String sourceString, String key, boolean hex) {
        return AlaesConsts.decryptAESStringFromFile(sourceString, key, hex);
    }

    @Override
    public void encryptAESFileEx(String fileName, String key) {
        AlaesConsts.encryptAESFileEx(fileName, key);
    }

    @Override
    public void decryptAESFileEx(String fileName, String key) {
        AlaesConsts.decryptAESFileEx(fileName, key);
    }

    @Override
    public String encryptToBase64(String sourceString, String key) {
        byte[] data = AlaesConsts.encryptAESStringToBytes(sourceString, key, false);
        return SingleConvert.bytesToBase64(data);
    }

    @Override
    public String decryptFromBase64(String sourceString, String key) {
        byte[] data = SingleConvert.base64ToBytes(sourceString);
        return AlaesConsts.decryptAESString2(data, key, false);
    }
}

