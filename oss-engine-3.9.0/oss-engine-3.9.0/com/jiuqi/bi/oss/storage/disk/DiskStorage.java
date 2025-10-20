/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 */
package com.jiuqi.bi.oss.storage.disk;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.encrypt.ObjectEncryptManager;
import com.jiuqi.bi.oss.storage.AbstractObjectStorage;
import com.jiuqi.bi.oss.storage.IObjectMetaAdapter;
import com.jiuqi.bi.oss.storage.MultipartObjectUploader;
import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.bi.oss.storage.StorageUtils;
import com.jiuqi.bi.oss.storage.TemporaryFileInputStream;
import com.jiuqi.bi.oss.storage.disk.DiskFileUtils;
import com.jiuqi.bi.oss.storage.disk.DiskMultipartObjectUploader;
import com.jiuqi.bi.oss.storage.disk.DiskStorageConfig;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiskStorage
extends AbstractObjectStorage {
    private static final Logger logger = LoggerFactory.getLogger(DiskStorage.class);
    private String basePath;
    private int partationSize;
    private boolean encrypt = true;
    private File root;

    public DiskStorage(IObjectMetaAdapter metaAdapter) {
        super(metaAdapter);
    }

    @Override
    public void initialize(StorageConfig context) throws ObjectStorageException {
        DiskStorageConfig cxt = (DiskStorageConfig)context;
        this.basePath = cxt.getBasePath();
        this.partationSize = cxt.getPartationSize();
        this.encrypt = cxt.isEncrypt();
        File f = new File(this.basePath);
        if (!f.exists()) {
            if (cxt.isCreateIfNotExist()) {
                if (!f.mkdirs()) {
                    throw new ObjectStorageException("\u521b\u5efaOSS\u76ee\u5f55\u5931\u8d25. \u8def\u5f84: " + f.getAbsolutePath());
                }
            } else {
                throw new ObjectStorageException("\u78c1\u76d8\u76ee\u5f55\u4e0d\u5b58\u5728\uff1a" + this.basePath);
            }
        }
        if (!f.isDirectory()) {
            throw new ObjectStorageException("\u4f20\u5165\u7684\u8def\u5f84\u5fc5\u987b\u662f\u4e00\u4e2a\u78c1\u76d8\u76ee\u5f55");
        }
        this.root = f;
    }

    @Override
    public boolean deleteBucket(String bucketName) throws ObjectStorageException {
        try {
            PathUtils.validatePathManipulation((String)this.root.getPath());
        }
        catch (SecurityContentException e) {
            throw new ObjectStorageException(e.getMessage(), e);
        }
        File f = new File(this.root, bucketName);
        if (!f.exists()) {
            return false;
        }
        DiskFileUtils.deleteFileRecurisve(f);
        return true;
    }

    @Override
    public void upload(String bucketName, String key, InputStream input, ObjectInfo info) throws ObjectStorageException {
        String eKey;
        DiskFileUtils.validateObjectKey(key);
        Bucket bucket = this.getBucket(bucketName);
        File path = DiskFileUtils.computePathByKey(this.root, this.partationSize, bucketName, key, false);
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, key);
        String name = this.encrypt ? ObjectEncryptManager.getInstance().getDefaultEncryptName() : null;
        File tmpFile = StorageUtils.putStreamToTemporary(bucket, key, input, name, eKey = this.encrypt ? ObjectEncryptManager.getInstance().getDefaultEncryptKey() : null);
        boolean rs = tmpFile.renameTo(file);
        if (!rs) {
            try {
                DiskStorage.copyFile(tmpFile, file);
            }
            catch (IOException e) {
                throw new ObjectStorageException("\u590d\u5236\u6587\u4ef6\u51fa\u9519\uff1a" + tmpFile.getAbsolutePath(), e);
            }
            tmpFile.delete();
        } else if (!file.exists()) {
            try {
                DiskStorage.copyFile(tmpFile, file);
            }
            catch (IOException e) {
                throw new ObjectStorageException("\u590d\u5236\u6587\u4ef6\u51fa\u9519\uff1a" + tmpFile.getAbsolutePath(), e);
            }
            tmpFile.delete();
        }
    }

    @Override
    public void move(String key, String srcBucket, String destBucket) throws ObjectStorageException {
        File dest;
        File src = DiskFileUtils.computePathByKey(this.root, this.partationSize, srcBucket, key, true);
        File file = new File(src, key);
        boolean rs = file.renameTo(new File(dest = DiskFileUtils.computePathByKey(this.root, this.partationSize, destBucket, key, false), key));
        if (!rs) {
            throw new ObjectStorageException("\u79fb\u52a8\u6587\u4ef6\u5931\u8d25\uff1a" + file.getPath());
        }
    }

    private static void copyFile(File source, File dest) throws IOException {
        try (FileInputStream input = new FileInputStream(source);
             FileOutputStream output = new FileOutputStream(dest);
             FileChannel inputChannel = input.getChannel();
             FileChannel outputChannel = output.getChannel();){
            outputChannel.transferFrom(inputChannel, 0L, inputChannel.size());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public InputStream download(String bucketName, String key) throws ObjectStorageException {
        DiskFileUtils.validateObjectKey(key);
        Bucket bucket = this.getBucket(bucketName);
        File path = DiskFileUtils.computePathByKey(this.root, this.partationSize, bucketName, key, true);
        File file = new File(path, key);
        try (FileInputStream fileInputStream = new FileInputStream(file);){
            String name = this.encrypt ? ObjectEncryptManager.getInstance().getDefaultEncryptName() : null;
            String eKey = this.encrypt ? ObjectEncryptManager.getInstance().getDefaultEncryptKey() : null;
            File tmpFile = StorageUtils.loadStreamToTemporary(bucket, key, fileInputStream, name, eKey);
            TemporaryFileInputStream temporaryFileInputStream = new TemporaryFileInputStream(tmpFile);
            return temporaryFileInputStream;
        }
        catch (IOException e) {
            throw new ObjectStorageException("\u5bf9\u8c61\u4e0d\u5b58\u5728. key: " + key, e);
        }
        catch (Exception e) {
            throw new ObjectStorageException("\u51fa\u73b0\u672a\u77e5\u9519\u8bef\u3002\u4e0b\u8f7d\u6587\u4ef6\u5931\u8d25: bucket: " + bucketName + ", key: " + key, e);
        }
    }

    @Override
    public void download(String bucketName, String key, OutputStream output) throws ObjectStorageException {
        DiskFileUtils.validateObjectKey(key);
        Bucket bucket = this.getBucket(bucketName);
        File path = DiskFileUtils.computePathByKey(this.root, this.partationSize, bucketName, key, true);
        File file = new File(path, key);
        if (!file.exists()) {
            throw new ObjectStorageException("\u5bf9\u8c61\u4e0d\u5b58\u5728\uff1akey->" + key);
        }
        try (FileInputStream fileInputStream = new FileInputStream(file);){
            String name = this.encrypt ? ObjectEncryptManager.getInstance().getDefaultEncryptName() : null;
            String eKey = this.encrypt ? ObjectEncryptManager.getInstance().getDefaultEncryptKey() : null;
            StorageUtils.loadStreamToOutputStream(bucket, key, fileInputStream, name, eKey, output);
        }
        catch (IOException e) {
            throw new ObjectStorageException("\u8bfb\u53d6\u78c1\u76d8\u6587\u4ef6\u5931\u8d25\uff1a" + file.getAbsolutePath(), e);
        }
    }

    @Override
    public boolean existObject(String bucketName, String key) throws ObjectStorageException {
        DiskFileUtils.validateObjectKey(key);
        File path = DiskFileUtils.computePathByKey(this.root, this.partationSize, bucketName, key, true);
        File file = new File(path, key);
        return file.exists();
    }

    @Override
    public boolean deleteObject(String bucketName, String key) throws ObjectStorageException {
        DiskFileUtils.validateObjectKey(key);
        File path = DiskFileUtils.computePathByKey(this.root, this.partationSize, bucketName, key, true);
        File file = new File(path, key);
        if (!file.exists()) {
            logger.error("\u5220\u9664\u5bf9\u8c61\u5931\u8d25\uff0c\u6587\u4ef6\u4e0d\u5b58\u5728\u3002bucket: {}, key: {}", (Object)bucketName, (Object)key);
            return false;
        }
        return file.delete();
    }

    @Override
    public boolean createBucket(Bucket bucket) throws ObjectStorageException {
        StorageUtils.checkBucketName(bucket.getName());
        try {
            PathUtils.validatePathManipulation((String)this.root.getPath());
        }
        catch (SecurityContentException e) {
            throw new ObjectStorageException(e.getLocalizedMessage(), e);
        }
        File f = new File(this.root, bucket.getName());
        if (f.exists()) {
            return false;
        }
        if (!f.mkdir()) {
            throw new ObjectStorageException("\u521b\u5efa\u76ee\u5f55\u5931\u8d25\uff1a" + bucket.getName());
        }
        return true;
    }

    @Override
    public MultipartObjectUploader createMultipartObjectUploader(Bucket bucket) throws ObjectStorageException {
        return new DiskMultipartObjectUploader(this.root, this.encrypt, this.partationSize, bucket);
    }
}

