/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.encrypt;

import com.jiuqi.bi.oss.encrypt.EncryptContext;
import com.jiuqi.bi.oss.encrypt.EncryptException;
import com.jiuqi.bi.oss.encrypt.HexByteArrayUtil;
import com.jiuqi.bi.oss.encrypt.IEncryptProvider;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryptProvider
implements IEncryptProvider {
    private static final String ALGORITHM_NAME = "AES";
    private Cipher cipher;

    @Override
    public byte[] encrypt(byte[] buffer, int offset, int length) throws EncryptException {
        return this.cipher.update(buffer, offset, length);
    }

    @Override
    public byte[] decrypt(byte[] buffer, int offset, int length) throws EncryptException {
        return this.cipher.update(buffer, offset, length);
    }

    @Override
    public void startEncrypt(EncryptContext context) throws EncryptException {
        SecretKeySpec secretKey = new SecretKeySpec(HexByteArrayUtil.hexStringToByteArray(context.getEncryptKey()), ALGORITHM_NAME);
        byte[] secKeyBytes = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secKeyBytes, ALGORITHM_NAME);
        try {
            this.cipher = Cipher.getInstance(ALGORITHM_NAME);
            this.cipher.init(1, secretKeySpec);
        }
        catch (Exception e) {
            throw new EncryptException(e.getMessage(), e);
        }
    }

    @Override
    public byte[] finishEncrypt() throws EncryptException {
        try {
            return this.cipher.doFinal();
        }
        catch (Exception e) {
            throw new EncryptException(e.getMessage(), e);
        }
    }

    @Override
    public void startDecrypt(EncryptContext context) throws EncryptException {
        SecretKeySpec secretKey = new SecretKeySpec(HexByteArrayUtil.hexStringToByteArray(context.getEncryptKey()), ALGORITHM_NAME);
        byte[] secKeyBytes = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secKeyBytes, ALGORITHM_NAME);
        try {
            this.cipher = Cipher.getInstance(ALGORITHM_NAME);
            this.cipher.init(2, secretKeySpec);
        }
        catch (Exception e) {
            throw new EncryptException(e.getMessage(), e);
        }
    }

    @Override
    public byte[] finishDecrypt() throws EncryptException {
        try {
            return this.cipher.doFinal();
        }
        catch (Exception e) {
            throw new EncryptException(e.getMessage(), e);
        }
    }
}

