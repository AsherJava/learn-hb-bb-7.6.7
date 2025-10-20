/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.JqLib
 */
package com.jiuqi.bi.util.crypto;

import com.jiuqi.bi.util.JqLib;
import com.jiuqi.bi.util.StringUtils;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

@Deprecated
public class DESUtil {
    private static final String Algorithm = "DES";

    public static final SecretKey generateKey() {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance(Algorithm);
            return keygen.generateKey();
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static final SecretKey transKey(String keyHexStr) {
        try {
            DESKeySpec dks = new DESKeySpec(JqLib.hexStringToBytes((String)keyHexStr));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);
            SecretKey key = keyFactory.generateSecret(dks);
            return key;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static final byte[] encrypt(SecretKey deskey, byte[] buff) {
        byte[] cipherByte = null;
        try {
            Cipher c = Cipher.getInstance(Algorithm);
            c.init(1, deskey);
            cipherByte = c.doFinal(buff);
        }
        catch (NoSuchPaddingException ex) {
            ex.printStackTrace();
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (InvalidKeyException ex) {
            ex.printStackTrace();
        }
        catch (BadPaddingException ex) {
            ex.printStackTrace();
        }
        catch (IllegalBlockSizeException ex) {
            ex.printStackTrace();
        }
        return cipherByte;
    }

    public static final String decrypt(SecretKey deskey, byte[] buff, String charset) {
        byte[] cipherByte = null;
        try {
            Cipher c = Cipher.getInstance(Algorithm);
            c.init(2, deskey);
            cipherByte = c.doFinal(buff);
        }
        catch (NoSuchPaddingException ex) {
            ex.printStackTrace();
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        catch (InvalidKeyException ex) {
            ex.printStackTrace();
        }
        catch (BadPaddingException ex) {
            ex.printStackTrace();
        }
        catch (IllegalBlockSizeException ex) {
            ex.printStackTrace();
        }
        if (StringUtils.isEmpty(charset)) {
            return new String(cipherByte);
        }
        try {
            return new String(cipherByte, charset);
        }
        catch (UnsupportedEncodingException e) {
            return new String(cipherByte);
        }
    }

    public static final String decrypt(SecretKey deskey, byte[] buff) {
        return DESUtil.decrypt(deskey, buff, null);
    }

    static {
        try {
            Class<?> jceClass = Class.forName("com.sun.crypto.provider.SunJCE");
            Security.addProvider((Provider)jceClass.newInstance());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

