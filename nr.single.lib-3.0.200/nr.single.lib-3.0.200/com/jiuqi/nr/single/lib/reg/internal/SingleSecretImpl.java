/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.lib.reg.internal;

import com.jiuqi.nr.single.lib.reg.SingleSecret;
import com.jiuqi.nr.single.lib.reg.exception.SingleSecretException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleSecretImpl
implements SingleSecret {
    private static final Logger logger = LoggerFactory.getLogger(SingleSecretImpl.class);

    @Override
    public List<String> getNewKeys() throws SingleSecretException {
        ArrayList<String> list = new ArrayList<String>();
        try {
            KeyPairGenerator g = KeyPairGenerator.getInstance("RSA");
            KeyPair keyPair = g.generateKeyPair();
            String publicdatas = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            String privatedatas = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
            list.add(publicdatas);
            list.add(privatedatas);
        }
        catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            throw new SingleSecretException(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public String publicEncrypt(String publicKey, String value) throws SingleSecretException {
        String result = null;
        try {
            byte[] publicBytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            Cipher p = Cipher.getInstance("RSA/None/OAEPWITHSHA-256ANDMGF1PADDING");
            p.init(1, pubKey);
            byte[] newData = p.doFinal(value.getBytes(StandardCharsets.UTF_8));
            result = Base64.getEncoder().encodeToString(newData);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SingleSecretException(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public String privateDecEncrypt(String privateKey, String value) throws SingleSecretException {
        String result = null;
        try {
            byte[] privateBytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec pkcSpec = new PKCS8EncodedKeySpec(privateBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyFactory.generatePrivate(pkcSpec);
            Cipher p = Cipher.getInstance("RSA/None/OAEPWITHSHA-256ANDMGF1PADDING");
            p.init(2, priKey);
            byte[] micode = Base64.getDecoder().decode(value);
            byte[] newData = p.doFinal(micode);
            result = new String(newData);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SingleSecretException(e.getMessage(), e);
        }
        return result;
    }

    public void test(String code) throws SingleSecretException {
        List<String> list = this.getNewKeys();
        logger.info("\u516c\u94a5:" + list.get(0));
        logger.info("\u79c1\u94a5:" + list.get(1));
        logger.info("\u539f\u6587:" + code);
        String newCode = this.publicEncrypt(list.get(0), code);
        logger.info("\u52a0\u5bc6\u540e:" + newCode);
        String newCode2 = this.privateDecEncrypt(list.get(1), newCode);
        logger.info("\u89e3\u5bc6\u540e:" + newCode2);
    }
}

