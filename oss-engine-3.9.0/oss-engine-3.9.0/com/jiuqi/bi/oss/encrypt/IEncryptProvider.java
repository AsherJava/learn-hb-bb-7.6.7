/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.encrypt;

import com.jiuqi.bi.oss.encrypt.EncryptContext;
import com.jiuqi.bi.oss.encrypt.EncryptException;

public interface IEncryptProvider {
    public void startEncrypt(EncryptContext var1) throws EncryptException;

    public byte[] encrypt(byte[] var1, int var2, int var3) throws EncryptException;

    public byte[] finishEncrypt() throws EncryptException;

    public void startDecrypt(EncryptContext var1) throws EncryptException;

    public byte[] decrypt(byte[] var1, int var2, int var3) throws EncryptException;

    public byte[] finishDecrypt() throws EncryptException;
}

