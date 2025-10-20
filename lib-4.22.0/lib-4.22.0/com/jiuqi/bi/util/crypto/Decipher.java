/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.crypto;

import com.jiuqi.bi.util.crypto.CryptoUtil;
import com.jiuqi.bi.util.crypto.DecryptException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Decipher {
    private final String algorithm;
    private final Cipher decryptCipher;

    public Decipher(String algorithm, String key, String iv) throws DecryptException {
        this.algorithm = algorithm;
        try {
            this.decryptCipher = Cipher.getInstance(algorithm);
            if (iv == null || iv.length() == 0) {
                this.decryptCipher.init(2, this.makeKey(key));
            } else {
                IvParameterSpec ivSpec = new IvParameterSpec(CryptoUtil.encodeStr(iv));
                this.decryptCipher.init(2, this.makeKey(key), ivSpec);
            }
        }
        catch (Exception e) {
            throw new DecryptException(e);
        }
    }

    public Decipher(String algorithm, byte[] key, byte[] iv) throws DecryptException {
        this.algorithm = algorithm;
        try {
            this.decryptCipher = Cipher.getInstance(algorithm);
            if (iv == null || iv.length == 0) {
                this.decryptCipher.init(2, this.makeKey(key));
            } else {
                IvParameterSpec ivSpec = new IvParameterSpec(iv);
                this.decryptCipher.init(2, this.makeKey(key), ivSpec);
            }
        }
        catch (Exception e) {
            throw new DecryptException(e);
        }
    }

    private Key makeKey(byte[] keyData) throws DecryptException {
        SecretKeyFactory keyFactory;
        DESKeySpec keySpec;
        String acturalAlgorithm;
        int p = this.algorithm.indexOf(47);
        String string = acturalAlgorithm = p < 0 ? this.algorithm : this.algorithm.substring(0, p);
        if ("DES".equals(acturalAlgorithm)) {
            try {
                keySpec = new DESKeySpec(keyData);
            }
            catch (InvalidKeyException e) {
                throw new DecryptException(e);
            }
        } else {
            throw new DecryptException("\u5c1a\u672a\u652f\u6301\u7684\u89e3\u5bc6\u7b97\u6cd5\uff1a" + this.algorithm);
        }
        try {
            keyFactory = SecretKeyFactory.getInstance(acturalAlgorithm);
        }
        catch (NoSuchAlgorithmException e) {
            throw new DecryptException(e);
        }
        try {
            return keyFactory.generateSecret(keySpec);
        }
        catch (InvalidKeySpecException e) {
            throw new DecryptException(e);
        }
    }

    private Key makeKey(String keyStr) throws DecryptException {
        return this.makeKey(CryptoUtil.encodeStr(keyStr));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void decrypt(InputStream inStream, OutputStream outStream) throws DecryptException {
        byte[] buffer = new byte[4096];
        CipherInputStream in = new CipherInputStream(inStream, this.decryptCipher);
        try {
            try {
                int len;
                while ((len = in.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
            }
            finally {
                in.close();
            }
        }
        catch (IOException e) {
            throw new DecryptException(e);
        }
    }
}

