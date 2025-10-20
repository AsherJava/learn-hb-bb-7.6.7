/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.filter.bill.filter;

import com.jiuqi.va.filter.bill.service.BaseDataEncryptService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class BaseDataEncryptDES {
    private static BaseDataEncryptService baseDataEncryptService;

    private static BaseDataEncryptService getBaseDataEncryptService() {
        if (baseDataEncryptService == null) {
            baseDataEncryptService = (BaseDataEncryptService)ApplicationContextRegister.getBean(BaseDataEncryptService.class);
        }
        return baseDataEncryptService;
    }

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
            DESKeySpec desKey = new DESKeySpec(BaseDataEncryptDES.getBaseDataEncryptService().getMAK());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(BaseDataEncryptDES.getBaseDataEncryptService().getSecretKey());
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance(BaseDataEncryptDES.getBaseDataEncryptService().getSecretKey());
            cipher.init(1, (Key)securekey, random);
            return BaseDataEncryptDES.byteArr2HexStr(cipher.doFinal(datasource));
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String pwd) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(BaseDataEncryptDES.getBaseDataEncryptService().getMAK());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(BaseDataEncryptDES.getBaseDataEncryptService().getSecretKey());
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance(BaseDataEncryptDES.getBaseDataEncryptService().getSecretKey());
            cipher.init(2, (Key)securekey, random);
            return new String(cipher.doFinal(BaseDataEncryptDES.hexStr2ByteArr(pwd)));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

