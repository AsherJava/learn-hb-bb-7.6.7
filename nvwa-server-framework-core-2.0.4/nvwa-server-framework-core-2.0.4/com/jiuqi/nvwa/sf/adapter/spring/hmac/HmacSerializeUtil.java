/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.io.FilteredObjectInputStream
 *  com.jiuqi.va.shiro.config.VaAuthShiroConfig
 *  javax.xml.bind.DatatypeConverter
 *  org.bouncycastle.crypto.CipherParameters
 *  org.bouncycastle.crypto.Digest
 *  org.bouncycastle.crypto.digests.SHA256Digest
 *  org.bouncycastle.crypto.digests.SM3Digest
 *  org.bouncycastle.crypto.macs.HMac
 *  org.bouncycastle.crypto.params.KeyParameter
 */
package com.jiuqi.nvwa.sf.adapter.spring.hmac;

import com.jiuqi.bi.io.FilteredObjectInputStream;
import com.jiuqi.nvwa.sf.adapter.spring.hmac.HmacAuthException;
import com.jiuqi.va.shiro.config.VaAuthShiroConfig;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

public class HmacSerializeUtil {
    private static final String STREAM_START_SYMBOL = "9A8B7C6D5F4D3C2B1A";
    private static final byte STREAM_ENCRYPT_VERSION = 1;
    private static String secretKey;
    private static final byte[] START_BYTES;
    private static final int HEADER_LENGTH;
    private static final int HMAC_LENGTH = 32;

    private static String getSecretKey() {
        if (secretKey == null) {
            secretKey = VaAuthShiroConfig.getTrustCode();
        }
        return secretKey;
    }

    private static byte[] calculateHmacSHA256(byte[] data) {
        KeyParameter keyParameter = new KeyParameter(HmacSerializeUtil.getSecretKey().getBytes());
        SHA256Digest sha256Digest = new SHA256Digest();
        HMac hMac = new HMac((Digest)sha256Digest);
        hMac.init((CipherParameters)keyParameter);
        hMac.update(data, 0, data.length);
        byte[] result = new byte[hMac.getMacSize()];
        hMac.doFinal(result, 0);
        return result;
    }

    private static byte[] calculateHmacSm3(byte[] data) {
        KeyParameter keyParameter = new KeyParameter(HmacSerializeUtil.getSecretKey().getBytes());
        SM3Digest sm3 = new SM3Digest();
        HMac hMac = new HMac((Digest)sm3);
        hMac.init((CipherParameters)keyParameter);
        hMac.update(data, 0, data.length);
        byte[] result = new byte[hMac.getMacSize()];
        hMac.doFinal(result, 0);
        return result;
    }

    private static String bytesToHexString(byte[] bytes) {
        return DatatypeConverter.printHexBinary((byte[])bytes);
    }

    private static byte[] hexStringToBytes() {
        return DatatypeConverter.parseHexBinary((String)STREAM_START_SYMBOL);
    }

    public static byte[] getHmacAuthBytes(byte[] message) {
        byte[] hmac = HmacSerializeUtil.calculateHmacSm3(message);
        byte[] hmacAuthBytes = new byte[HEADER_LENGTH + hmac.length + message.length];
        System.arraycopy(START_BYTES, 0, hmacAuthBytes, 0, START_BYTES.length);
        hmacAuthBytes[HmacSerializeUtil.START_BYTES.length] = 1;
        System.arraycopy(hmac, 0, hmacAuthBytes, HEADER_LENGTH, hmac.length);
        System.arraycopy(message, 0, hmacAuthBytes, HEADER_LENGTH + hmac.length, message.length);
        return hmacAuthBytes;
    }

    public static byte[] authHmacBytes(byte[] data) throws HmacAuthException {
        byte[] message;
        byte[] calculatedHmac;
        if (!Arrays.equals(START_BYTES, Arrays.copyOfRange(data, 0, START_BYTES.length))) {
            throw new HmacAuthException("\u53cd\u5e8f\u5217\u5316\u9a8c\u8bc1\uff1a\u8d77\u59cb\u6807\u8bc6\u9a8c\u8bc1\u5931\u8d25\uff01");
        }
        int versionOffset = START_BYTES.length;
        if (data[versionOffset] != 1) {
            throw new HmacAuthException("\u53cd\u5e8f\u5217\u5316\u9a8c\u8bc1\uff1a\u672a\u77e5\u7684\u7248\u672c\u53f7\uff01");
        }
        int hmacOffset = HEADER_LENGTH;
        byte[] receivedHmac = Arrays.copyOfRange(data, hmacOffset, hmacOffset + 32);
        if (!Arrays.equals(receivedHmac, calculatedHmac = HmacSerializeUtil.calculateHmacSm3(message = Arrays.copyOfRange(data, hmacOffset + 32, data.length)))) {
            throw new HmacAuthException("\u53cd\u5e8f\u5217\u5316\u9a8c\u8bc1\uff1aHMAC\u9a8c\u8bc1\u5931\u8d25\uff01");
        }
        return message;
    }

    public static byte[] serialize(Object object) throws HmacAuthException {
        byte[] result = null;
        if (object == null) {
            return new byte[0];
        }
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);){
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            result = byteStream.toByteArray();
            result = HmacSerializeUtil.getHmacAuthBytes(result);
        }
        catch (Exception ex) {
            throw new HmacAuthException(ex);
        }
        return result;
    }

    public static void serialize(Object object, OutputStream outputStream) throws HmacAuthException {
        try {
            byte[] bytes = HmacSerializeUtil.serialize(object);
            outputStream.write(bytes);
            outputStream.flush();
        }
        catch (Exception e) {
            throw new HmacAuthException(e);
        }
    }

    public static <T> T deserialize(InputStream inputStream, Class<T> className) throws HmacAuthException {
        Object object = HmacSerializeUtil.deserialize(inputStream);
        return className.cast(object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Object deserialize(InputStream inputStream) throws HmacAuthException {
        try {
            int bytesRead;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
            byte[] verifiedBytes = HmacSerializeUtil.authHmacBytes(buffer.toByteArray());
            try (FilteredObjectInputStream objectInputStream = new FilteredObjectInputStream((InputStream)new ByteArrayInputStream(verifiedBytes));){
                Object object = objectInputStream.readObject();
                return object;
            }
        }
        catch (IOException | ClassNotFoundException e) {
            throw new HmacAuthException(e);
        }
    }

    public static <T> T deserialize(byte[] bytes, Class<T> className) throws HmacAuthException {
        Object object = HmacSerializeUtil.deserialize(bytes);
        return className.cast(object);
    }

    /*
     * Exception decompiling
     */
    public static Object deserialize(byte[] bytes) throws HmacAuthException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    static {
        START_BYTES = HmacSerializeUtil.hexStringToBytes();
        HEADER_LENGTH = START_BYTES.length + 1;
    }
}

