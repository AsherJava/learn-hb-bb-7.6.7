/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.encrypt;

import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class LcdpEncryptDES {
    private static final String MAK = "XsXzH6eP5W1nY8BbnlqQbw==";

    private static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; ++i) {
            int intTmp;
            for (intTmp = arrB[i]; intTmp < 0; intTmp += 256) {
            }
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    private static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i += 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte)Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public static String encrypt(String pwd) {
        try {
            byte[] datasource = pwd.getBytes();
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(MAK.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(1, (Key)securekey, random);
            return LcdpEncryptDES.byteArr2HexStr(cipher.doFinal(datasource));
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String pwd) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(MAK.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, (Key)securekey, random);
            return new String(cipher.doFinal(LcdpEncryptDES.hexStr2ByteArr(pwd)));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

