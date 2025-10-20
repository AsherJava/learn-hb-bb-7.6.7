/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.gm.GMNamedCurves
 *  org.bouncycastle.asn1.gm.GMObjectIdentifiers
 *  org.bouncycastle.asn1.x9.X9ECParameters
 *  org.bouncycastle.crypto.CipherParameters
 *  org.bouncycastle.crypto.engines.SM2Engine
 *  org.bouncycastle.crypto.engines.SM2Engine$Mode
 *  org.bouncycastle.crypto.params.ECDomainParameters
 *  org.bouncycastle.crypto.params.ECNamedDomainParameters
 *  org.bouncycastle.crypto.params.ECPrivateKeyParameters
 *  org.bouncycastle.crypto.params.ECPublicKeyParameters
 *  org.bouncycastle.crypto.params.ParametersWithRandom
 *  org.bouncycastle.math.ec.ECCurve
 *  org.bouncycastle.math.ec.ECPoint
 *  org.bouncycastle.util.encoders.Hex
 */
package com.jiuqi.np.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECNamedDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

public class RSAEncryptUtils {
    public static final Logger LOGGER = LoggerFactory.getLogger(RSAEncryptUtils.class);
    private static String PUBLIC_KEY;
    private static String PRIVATE_KEY;
    private static int MAXCHUNK_LENGTH;
    private static String TYPE;
    private static String ALIAS;

    public static String getPublicKey() {
        return PUBLIC_KEY;
    }

    public static String getType() {
        return TYPE;
    }

    public static String getAlias() {
        return ALIAS;
    }

    public static String encryptLong(String str) throws Exception {
        if ("RSA".equals(TYPE)) {
            return RSAEncryptUtils.encrypt(Base64.getEncoder().encodeToString(str.getBytes("UTF-8")));
        }
        return RSAEncryptUtils.encrypt(str);
    }

    public static String decryptLong(String str) throws Exception {
        if ("RSA".equals(TYPE)) {
            return new String(Base64.getDecoder().decode(RSAEncryptUtils.decrypt(str).getBytes("UTF-8")), "UTF-8");
        }
        return RSAEncryptUtils.decrypt(str);
    }

    public static String encrypt(String str) throws Exception {
        if ("RSA".equals(TYPE)) {
            byte[] decoded = Base64.getDecoder().decode(RSAEncryptUtils.keyTransformation(PUBLIC_KEY, "-----BEGIN PUBLIC KEY-----", "-----END PUBLIC KEY-----"));
            RSAPublicKey pubKey = (RSAPublicKey)KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(1, pubKey);
            int maxChunkLength = 50;
            String outStr = "";
            for (int inOffset = 0; inOffset < str.length(); inOffset += maxChunkLength) {
                outStr = outStr + Base64.getEncoder().encodeToString(cipher.doFinal(str.substring(inOffset, inOffset + maxChunkLength > str.length() ? str.length() : inOffset + maxChunkLength).getBytes("UTF-8")));
            }
            return outStr;
        }
        ASN1ObjectIdentifier sm2p256v1 = GMObjectIdentifiers.sm2p256v1;
        X9ECParameters parameters = GMNamedCurves.getByOID((ASN1ObjectIdentifier)sm2p256v1);
        ECNamedDomainParameters namedDomainParameters = new ECNamedDomainParameters(sm2p256v1, parameters.getCurve(), parameters.getG(), parameters.getN());
        ECCurve curve = namedDomainParameters.getCurve();
        ECPoint pukPoint = curve.decodePoint(Hex.decode((String)PUBLIC_KEY));
        ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(pukPoint, (ECDomainParameters)namedDomainParameters);
        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        SecureRandom secureRandom = new SecureRandom();
        sm2Engine.init(true, (CipherParameters)new ParametersWithRandom((CipherParameters)publicKeyParameters, secureRandom));
        byte[] data = str.getBytes("UTF-8");
        byte[] encrypt = sm2Engine.processBlock(data, 0, data.length);
        return Hex.toHexString((byte[])encrypt);
    }

    public static String decrypt(String str) throws Exception {
        if ("RSA".equals(TYPE)) {
            byte[] decoded = Base64.getDecoder().decode(RSAEncryptUtils.keyTransformation(PRIVATE_KEY, "-----BEGIN PRIVATE KEY-----", "-----END PRIVATE KEY-----"));
            RSAPrivateKey priKey = (RSAPrivateKey)KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(2, priKey);
            int maxChunkLength = MAXCHUNK_LENGTH;
            String outStr = "";
            for (int inOffset = 0; inOffset < str.length(); inOffset += maxChunkLength) {
                String tempStr = str.substring(inOffset, inOffset + maxChunkLength > str.length() ? str.length() : inOffset + maxChunkLength);
                byte[] inputByte = Base64.getDecoder().decode(tempStr.getBytes("UTF-8"));
                outStr = outStr + new String(cipher.doFinal(inputByte), "UTF-8");
            }
            return outStr;
        }
        BigInteger privateKey = new BigInteger(PRIVATE_KEY, 16);
        ASN1ObjectIdentifier sm2p256v1 = GMObjectIdentifiers.sm2p256v1;
        X9ECParameters parameters = GMNamedCurves.getByOID((ASN1ObjectIdentifier)sm2p256v1);
        ECNamedDomainParameters namedDomainParameters = new ECNamedDomainParameters(sm2p256v1, parameters.getCurve(), parameters.getG(), parameters.getN());
        ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKey, (ECDomainParameters)namedDomainParameters);
        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        sm2Engine.init(false, (CipherParameters)privateKeyParameters);
        byte[] cipherData = Hex.decode((String)str);
        byte[] processBlock = null;
        if (cipherData[0] == 4) {
            processBlock = sm2Engine.processBlock(cipherData, 0, cipherData.length);
        } else {
            byte[] bytes = new byte[cipherData.length + 1];
            bytes[0] = 4;
            System.arraycopy(cipherData, 0, bytes, 1, cipherData.length);
            processBlock = sm2Engine.processBlock(bytes, 0, bytes.length);
        }
        return new String(processBlock, "UTF-8");
    }

    private static String keyTransformation(String privateKey, String begin, String end) {
        return privateKey.replace(begin, "").replace(end, "").replace("\r\n", "").replace("\r", "").replace("\n", "");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static {
        InputStream inputStream = null;
        try {
            ClassPathResource templateResource = new ClassPathResource("keys/RSA.key");
            inputStream = templateResource.getInputStream();
            String defaultKey = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            int indexOf = defaultKey.indexOf("-----BEGIN PRIVATE KEY-----");
            if (indexOf > -1) {
                TYPE = "RSA";
                ALIAS = "3";
                PUBLIC_KEY = defaultKey.substring(0, indexOf);
                PRIVATE_KEY = defaultKey.substring(indexOf, defaultKey.length());
                int pubKeyLength = RSAEncryptUtils.keyTransformation(PUBLIC_KEY, "-----BEGIN PUBLIC KEY-----", "-----END PUBLIC KEY-----").length();
                if (pubKeyLength == 128) {
                    MAXCHUNK_LENGTH = 88;
                    return;
                } else if (pubKeyLength == 216) {
                    MAXCHUNK_LENGTH = 172;
                    return;
                } else {
                    if (pubKeyLength != 392) throw new RuntimeException("only support 512 1024 2048 RSA");
                    MAXCHUNK_LENGTH = 344;
                }
                return;
            } else {
                TYPE = "SM2";
                ALIAS = "2";
                String[] split = defaultKey.split("\n");
                PUBLIC_KEY = RSAEncryptUtils.keyTransformation(split[0], "", "");
                PRIVATE_KEY = RSAEncryptUtils.keyTransformation(split[1], "", "");
            }
            return;
        }
        catch (IOException e) {
            LOGGER.error("\u521d\u59cb\u5316\uff1aRSA/SM2\u5bc6\u94a5\u62a5\u9519\uff01", e);
            return;
        }
        finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    LOGGER.error("\u521d\u59cb\u5316\uff1aRSA/SM2\u5bc6\u94a5\u62a5\u9519\uff01", e);
                }
            }
        }
    }
}

