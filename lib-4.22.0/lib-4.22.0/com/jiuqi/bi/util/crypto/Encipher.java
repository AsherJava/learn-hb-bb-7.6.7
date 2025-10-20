/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.crypto;

import com.jiuqi.bi.util.crypto.CryptoUtil;
import com.jiuqi.bi.util.crypto.EncryptException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Encipher {
    private final String algorithm;
    private final Cipher encryptCipher;

    public Encipher(String algorithm, String publicKey, String privateKey, String iv) throws EncryptException {
        this.algorithm = algorithm;
        try {
            this.encryptCipher = Cipher.getInstance(algorithm);
            if (iv == null || iv.length() == 0) {
                this.encryptCipher.init(1, this.makeKey(publicKey, privateKey));
            } else {
                IvParameterSpec ivSpec = new IvParameterSpec(CryptoUtil.encodeStr(iv));
                this.encryptCipher.init(1, this.makeKey(publicKey, privateKey), ivSpec);
            }
        }
        catch (Exception e) {
            throw new EncryptException(e);
        }
    }

    public Encipher(String algorithm, byte[] publicKey, byte[] privateKey, byte[] iv) throws EncryptException {
        this.algorithm = algorithm;
        try {
            this.encryptCipher = Cipher.getInstance(algorithm);
            if (iv == null || iv.length == 0) {
                this.encryptCipher.init(1, this.makeKey(publicKey, privateKey));
            } else {
                IvParameterSpec ivSpec = new IvParameterSpec(iv);
                this.encryptCipher.init(1, this.makeKey(publicKey, privateKey), ivSpec);
            }
        }
        catch (Exception e) {
            throw new EncryptException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void encrypt(InputStream inStream, OutputStream outStream) throws EncryptException {
        byte[] buffer = new byte[4096];
        CipherOutputStream out = new CipherOutputStream(outStream, this.encryptCipher);
        try {
            try {
                int len;
                while ((len = inStream.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            }
            finally {
                out.close();
            }
        }
        catch (IOException e) {
            throw new EncryptException(e);
        }
    }

    private Key makeKey(byte[] publicKey, byte[] privateKey) throws EncryptException {
        SecretKeyFactory keyFactory;
        DESKeySpec keySpec;
        String acturalAlgorithm;
        int p = this.algorithm.indexOf(47);
        String string = acturalAlgorithm = p < 0 ? this.algorithm : this.algorithm.substring(0, p);
        if ("DES".equals(acturalAlgorithm)) {
            try {
                keySpec = new DESKeySpec(publicKey);
            }
            catch (InvalidKeyException e) {
                throw new EncryptException(e);
            }
        } else {
            throw new EncryptException("\u5c1a\u672a\u652f\u6301\u7684\u89e3\u5bc6\u7b97\u6cd5\uff1a" + this.algorithm);
        }
        try {
            keyFactory = SecretKeyFactory.getInstance(acturalAlgorithm);
        }
        catch (NoSuchAlgorithmException e) {
            throw new EncryptException(e);
        }
        try {
            return keyFactory.generateSecret(keySpec);
        }
        catch (InvalidKeySpecException e) {
            throw new EncryptException(e);
        }
    }

    private Key makeKey(String publicKey, String privateKey) throws EncryptException {
        return this.makeKey(CryptoUtil.encodeStr(publicKey), CryptoUtil.encodeStr(privateKey));
    }
}

