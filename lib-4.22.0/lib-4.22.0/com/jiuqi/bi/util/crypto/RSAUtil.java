/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.crypto;

import com.jiuqi.bi.util.crypto.EncryptException;
import com.jiuqi.bi.util.crypto.SingletonBouncyCastleProvider;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;

public class RSAUtil {
    public static KeyPair generateKeyPair() throws EncryptException {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", (Provider)SingletonBouncyCastleProvider.getProvider());
            int KEY_SIZE = 1024;
            keyPairGen.initialize(1024, new SecureRandom());
            KeyPair keyPair = keyPairGen.genKeyPair();
            return keyPair;
        }
        catch (Exception e) {
            throw new EncryptException(e.getMessage());
        }
    }

    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws EncryptException {
        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA", (Provider)SingletonBouncyCastleProvider.getProvider());
        }
        catch (NoSuchAlgorithmException ex) {
            throw new EncryptException(ex.getMessage());
        }
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
        try {
            return (RSAPublicKey)keyFac.generatePublic(pubKeySpec);
        }
        catch (InvalidKeySpecException ex) {
            throw new EncryptException(ex.getMessage());
        }
    }

    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws EncryptException {
        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA", (Provider)SingletonBouncyCastleProvider.getProvider());
        }
        catch (NoSuchAlgorithmException ex) {
            throw new EncryptException(ex.getMessage());
        }
        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
        try {
            return (RSAPrivateKey)keyFac.generatePrivate(priKeySpec);
        }
        catch (InvalidKeySpecException ex) {
            throw new EncryptException(ex.getMessage());
        }
    }

    public static byte[] encrypt(Key key, byte[] data) throws EncryptException {
        try {
            Cipher cipher = Cipher.getInstance("RSA", (Provider)SingletonBouncyCastleProvider.getProvider());
            cipher.init(1, key);
            int blockSize = cipher.getBlockSize();
            int outputSize = cipher.getOutputSize(data.length);
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize) {
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                } else {
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
                }
                ++i;
            }
            return raw;
        }
        catch (Exception e) {
            throw new EncryptException(e.getMessage());
        }
    }

    public static byte[] decrypt(Key key, byte[] raw) throws EncryptException {
        try {
            Cipher cipher = Cipher.getInstance("RSA", (Provider)SingletonBouncyCastleProvider.getProvider());
            cipher.init(2, key);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
            int j = 0;
            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                ++j;
            }
            return bout.toByteArray();
        }
        catch (Exception e) {
            throw new EncryptException(e.getMessage());
        }
    }
}

