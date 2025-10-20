/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.encrypt;

import com.jiuqi.bi.oss.encrypt.EncryptContext;
import com.jiuqi.bi.oss.encrypt.EncryptException;
import com.jiuqi.bi.oss.encrypt.IEncryptProvider;

public class NonEncryptProvider
implements IEncryptProvider {
    @Override
    public byte[] encrypt(byte[] buffer, int offset, int length) {
        if (length == buffer.length) {
            return buffer;
        }
        byte[] dest = new byte[length];
        System.arraycopy(buffer, offset, dest, 0, length);
        return dest;
    }

    @Override
    public byte[] decrypt(byte[] buffer, int offset, int length) {
        if (length == buffer.length) {
            return buffer;
        }
        byte[] dest = new byte[length];
        System.arraycopy(buffer, offset, dest, 0, length);
        return dest;
    }

    @Override
    public void startEncrypt(EncryptContext context) throws EncryptException {
    }

    @Override
    public byte[] finishEncrypt() throws EncryptException {
        return new byte[0];
    }

    @Override
    public void startDecrypt(EncryptContext context) throws EncryptException {
    }

    @Override
    public byte[] finishDecrypt() throws EncryptException {
        return new byte[0];
    }
}

