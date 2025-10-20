/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Guid
 */
package com.jiuqi.bi.oss.storage;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.encrypt.EncryptContext;
import com.jiuqi.bi.oss.encrypt.IEncryptProvider;
import com.jiuqi.bi.oss.encrypt.ObjectEncryptManager;
import com.jiuqi.bi.util.Guid;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StorageUtils {
    public static final long DEFAULT_MEMORY_MAX_BYTES_LENGTH = -100663296L;

    public static void checkBucketName(String name) throws ObjectStorageException {
        char[] chars;
        if (name == null || name.isEmpty()) {
            throw new ObjectStorageException("\u540d\u79f0\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        if (name.length() > 18) {
            throw new ObjectStorageException("\u540d\u79f0\u7684\u5b57\u7b26\u603b\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc718\u4e2a");
        }
        for (char c : chars = name.toLowerCase().toCharArray()) {
            if (c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c == '_') continue;
            throw new ObjectStorageException("\u540d\u79f0\u5fc5\u987b\u7531\u5b57\u6bcd\u3001\u6570\u5b57\u548c\u4e0b\u5212\u7ebf\u7ec4\u6210");
        }
    }

    public static File createTemporaryFile(String key, long random) throws IOException {
        StringBuilder buf = new StringBuilder();
        buf.append(Guid.newGuid()).append("_").append(random);
        return File.createTempFile(buf.toString(), ".tmp");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static File putStreamToTemporary(Bucket bucket, String key, InputStream input, String encryptName, String encryptKey) throws ObjectStorageException {
        File tmpFile;
        try {
            FileOutputStream bos;
            EncryptContext context = new EncryptContext(encryptKey);
            IEncryptProvider encryptProvider = ObjectEncryptManager.newEncryptProvider(encryptName, context);
            tmpFile = StorageUtils.createTemporaryFile(key, System.currentTimeMillis());
            try (FileOutputStream stream = bos = new FileOutputStream(tmpFile);){
                byte[] data;
                int len = 0;
                byte[] buffer = new byte[1024];
                encryptProvider.startEncrypt(context);
                while ((len = input.read(buffer, 0, 1024)) != -1) {
                    data = encryptProvider.encrypt(buffer, 0, len);
                    ((OutputStream)stream).write(data, 0, data.length);
                }
                data = encryptProvider.finishEncrypt();
                if (data != null && data.length > 0) {
                    ((OutputStream)stream).write(data);
                }
                stream.flush();
            }
        }
        catch (Exception e) {
            throw new ObjectStorageException(e.getMessage(), e);
        }
        return tmpFile;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] putStreamToMemory(Bucket bucket, String key, InputStream input, ObjectInfo info, String encryptName, String encryptKey, long maxByteSize) throws ObjectStorageException {
        int size = 0;
        try {
            EncryptContext context = new EncryptContext(encryptKey);
            IEncryptProvider encryptProvider = ObjectEncryptManager.newEncryptProvider(encryptName, context);
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();){
                byte[] data;
                int len = 0;
                byte[] buffer = new byte[1024];
                encryptProvider.startEncrypt(context);
                while ((len = input.read(buffer, 0, 1024)) != -1) {
                    data = encryptProvider.encrypt(buffer, 0, len);
                    bos.write(data, 0, data.length);
                    if (maxByteSize <= 0L || (long)(size += len) <= maxByteSize) continue;
                    throw new ObjectStorageException("\u4e0a\u4f20\u7684\u6587\u4ef6\u8fc7\u5927");
                }
                data = encryptProvider.finishEncrypt();
                if (data != null && data.length > 0) {
                    bos.write(data);
                }
                bos.flush();
            }
            return bos.toByteArray();
        }
        catch (Exception e) {
            throw new ObjectStorageException(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static File loadStreamToTemporary(Bucket bucket, String key, InputStream data, String encryptName, String encryptKey) throws ObjectStorageException {
        File tmpFile;
        try {
            tmpFile = StorageUtils.createTemporaryFile(key, System.currentTimeMillis());
            try (FileOutputStream fos = new FileOutputStream(tmpFile);
                 BufferedOutputStream bos = new BufferedOutputStream(fos);){
                StorageUtils.loadStreamToOutputStream(bucket, key, data, encryptName, encryptKey, bos);
            }
        }
        catch (IOException e) {
            throw new ObjectStorageException(e.getMessage(), e);
        }
        return tmpFile;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void loadStreamToOutputStream(Bucket bucket, String key, InputStream data, String encryptName, String encryptKey, OutputStream output) throws ObjectStorageException {
        try {
            EncryptContext context = new EncryptContext(encryptKey);
            IEncryptProvider encryptProvider = ObjectEncryptManager.newEncryptProvider(encryptName, context);
            try (InputStream input = data;){
                byte[] tmp;
                int len = 0;
                byte[] buffer = new byte[1024];
                encryptProvider.startDecrypt(context);
                while ((len = input.read(buffer, 0, 1024)) != -1) {
                    tmp = encryptProvider.decrypt(buffer, 0, len);
                    output.write(tmp, 0, tmp.length);
                }
                tmp = encryptProvider.finishDecrypt();
                if (tmp != null && tmp.length > 0) {
                    output.write(tmp);
                }
                output.flush();
            }
        }
        catch (Exception e) {
            throw new ObjectStorageException(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] loadStreamToMemory(Bucket bucket, String key, InputStream data, String encryptName, String encryptKey, int maxByteSize) throws ObjectStorageException {
        int size = 0;
        try {
            EncryptContext context = new EncryptContext(encryptKey);
            IEncryptProvider encryptProvider = ObjectEncryptManager.newEncryptProvider(encryptName, context);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try (InputStream input = data;){
                try {
                    byte[] tmp;
                    int len = 0;
                    byte[] buffer = new byte[1024];
                    encryptProvider.startDecrypt(context);
                    while ((len = input.read(buffer, 0, 1024)) != -1) {
                        tmp = encryptProvider.decrypt(buffer, 0, len);
                        bos.write(tmp, 0, tmp.length);
                        if (maxByteSize <= 0 || (size += len) <= maxByteSize) continue;
                        throw new ObjectStorageException("\u4e0a\u4f20\u7684\u6587\u4ef6\u8fc7\u5927");
                    }
                    tmp = encryptProvider.finishDecrypt();
                    if (tmp != null && tmp.length > 0) {
                        bos.write(tmp);
                    }
                    bos.flush();
                }
                finally {
                    bos.close();
                }
            }
            return bos.toByteArray();
        }
        catch (Exception e) {
            throw new ObjectStorageException(e.getMessage(), e);
        }
    }
}

