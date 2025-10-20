/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.type.GUID
 */
package com.jiuqi.bi.oss;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectFilterCondition;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectNotFoundException;
import com.jiuqi.bi.oss.ObjectOrderField;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectUploadMode;
import com.jiuqi.bi.oss.storage.IObjectMetaAdapter;
import com.jiuqi.bi.oss.storage.IObjectStorage;
import com.jiuqi.bi.oss.storage.MultipartObjectUploader;
import com.jiuqi.bi.oss.storage.MultipartUploadResult;
import com.jiuqi.bi.oss.storage.ObjectDataInputStream;
import com.jiuqi.bi.oss.storage.ObjectSliceInfo;
import com.jiuqi.bi.oss.storage.PartEtag;
import com.jiuqi.bi.oss.storage.StorageUtils;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.type.GUID;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectStorageService
implements AutoCloseable {
    private IObjectMetaAdapter meta;
    private IObjectStorage storage;
    private MultipartObjectUploader multipartUploader;
    private Bucket bucket;
    private Logger logger = LoggerFactory.getLogger(ObjectStorageService.class);

    ObjectStorageService(IObjectMetaAdapter metaAdapter, IObjectStorage storage, String bucketName) throws ObjectStorageException {
        this.meta = metaAdapter;
        this.storage = storage;
        this.bucket = metaAdapter.getBucket(bucketName);
        if (this.bucket == null) {
            throw new ObjectStorageException("\u6307\u5b9a\u7684bucket\u4e0d\u5b58\u5728\uff1a" + bucketName);
        }
        this.multipartUploader = storage.createMultipartObjectUploader(this.bucket);
    }

    public void upload(String key, InputStream input, ObjectInfo info, ObjectUploadMode mode) throws ObjectStorageException {
        block65: {
            this.checkMigrate("\u4e0a\u4f20\u8d44\u6e90");
            boolean needUpload = false;
            boolean needCheckBroken = false;
            boolean checksum = false;
            switch (mode) {
                case NO_CHECK: {
                    needUpload = true;
                    break;
                }
                case CHECKSUM: {
                    needUpload = true;
                    checksum = true;
                    break;
                }
                case IGNORE_PRESENT: {
                    if (!this.existObject(key, 1)) {
                        needUpload = true;
                        break;
                    }
                    this.logger.info("\u6587\u4ef6\u5df2\u5b58\u5728\uff0c\u4e0d\u518d\u4e0a\u4f20\uff1a" + this.bucket.getName() + ":" + key);
                    break;
                }
                case OVERWRITE_ALL: {
                    if (this.bucket.isLinkWhenExist()) {
                        throw new ObjectStorageException("\u8f6f\u94fe\u6a21\u5f0f\u4e0b\uff0c\u4e0d\u5141\u8bb8\u8986\u76d6\u4e0a\u4f20\u6587\u4ef6");
                    }
                    if (this.existObject(key, 1)) {
                        this.deleteObject(key);
                        this.logger.info("\u6587\u4ef6\u91cd\u590d\u4e0a\u4f20\uff0c\u539f\u6709\u6587\u4ef6\u5c06\u88ab\u8986\u76d6\uff1a" + this.bucket.getName() + ":" + key);
                    }
                    needUpload = true;
                    break;
                }
                case OVERWRITE_BROKEN: {
                    boolean metaExist = this.existObject(key, 1);
                    boolean dataExist = this.existObject(key, 2);
                    if (metaExist && dataExist) {
                        this.logger.info("\u6587\u4ef6\u5df2\u5b58\u5728\uff0c\u4e0d\u518d\u4e0a\u4f20\uff1a" + this.bucket.getName() + ":" + key);
                        break;
                    }
                    if (metaExist && !dataExist) {
                        this.deleteObject(key);
                        needUpload = true;
                        this.logger.warn("\u6587\u4ef6\u5df2\u4e22\u5931\uff0c\u65b0\u4e0a\u4f20\u6587\u4ef6\u5c06\u4f1a\u8986\u76d6\u4e22\u5931\u6587\u4ef6\uff1a" + this.bucket.getName() + ":" + key);
                        break;
                    }
                    if (!this.bucket.isLinkWhenExist()) break;
                    needCheckBroken = true;
                }
            }
            if (!needUpload && !needCheckBroken) {
                return;
            }
            if (info == null) {
                info = new ObjectInfo(key);
            }
            File tmpFile = null;
            try (ObjectDataInputStream odis = new ObjectDataInputStream(input);){
                if (this.bucket.isLinkWhenExist()) {
                    tmpFile = StorageUtils.putStreamToTemporary(new Bucket(this.bucket.getName()), key, odis, null, null);
                    String realKey = this.meta.findObjectStoreKeyByMd5(this.bucket.getName(), odis.getMd5(), odis.getSize());
                    if (realKey != null) {
                        int tag;
                        boolean dataExist;
                        if (needCheckBroken && !(dataExist = this.existObject(realKey, 2))) {
                            ObjectInfo realInfo = this.meta.getObjectInfo(this.bucket.getName(), realKey);
                            try (InputStream ins = Files.newInputStream(tmpFile.toPath(), new OpenOption[0]);){
                                this.storage.upload(this.bucket.getName(), realKey, ins, realInfo);
                            }
                            this.logger.warn("\u6587\u4ef6\u5df2\u4e22\u5931\uff0c\u65b0\u4e0a\u4f20\u6587\u4ef6\u5c06\u4f1a\u8986\u76d6\u4e22\u5931\u6587\u4ef6\uff1a" + this.bucket.getName() + ":" + realKey);
                        }
                        info.setSize(odis.getSize());
                        info.setMd5(odis.getMd5());
                        if (this.meta.existObject(this.bucket.getName(), info.getKey())) {
                            this.meta.deleteObject(this.bucket.getName(), info.getKey());
                        }
                        this.meta.addObjectInfo(this.bucket.getName(), info);
                        if (key.equals(realKey) && (tag = this.meta.getObjectLinkTag(this.bucket.getName(), key)) == 1) {
                            this.meta.deleteObjectLink(this.bucket.getName(), key);
                        }
                        this.meta.deleteObjectLink(this.bucket.getName(), key);
                        this.meta.addObjectLink(this.bucket.getName(), key, realKey);
                    } else {
                        int tag = this.meta.getObjectLinkTag(this.bucket.getName(), key);
                        if (tag == -1) {
                            this.meta.addObjectLink(this.bucket.getName(), key, key);
                        } else if (tag == 1) {
                            this.meta.deleteObjectLink(this.bucket.getName(), key);
                            if (this.storage.existObject(this.bucket.getName(), key)) {
                                this.storage.deleteObject(this.bucket.getName(), key);
                            }
                            this.meta.addObjectLink(this.bucket.getName(), key, key);
                        } else {
                            throw new ObjectStorageException("\u6587\u4ef6\u5df2\u5b58\u5728\uff0c\u72b6\u6001\u672a\u77e5\uff1a" + tag);
                        }
                        try (ObjectDataInputStream tmpOdis = new ObjectDataInputStream(new FileInputStream(tmpFile));){
                            this._doUpload(key, tmpOdis, info, checksum);
                        }
                    }
                    tmpFile.delete();
                    break block65;
                }
                this._doUpload(key, odis, info, checksum);
            }
            catch (IOException e) {
                throw new ObjectStorageException(e.getMessage(), e);
            }
        }
    }

    public void upload(String key, InputStream input, ObjectInfo info) throws ObjectStorageException {
        this.upload(key, input, info, ObjectUploadMode.NO_CHECK);
    }

    private void _doUpload(String key, ObjectDataInputStream odis, ObjectInfo info, boolean checksum) throws ObjectStorageException {
        this.storage.upload(this.bucket.getName(), key, odis, info);
        if (info.getSize() > 0L && (long)odis.getSize() != info.getSize()) {
            this.logger.warn("\u4e0a\u4f20\u6587\u4ef6\u5927\u5c0f\u4e0e\u6d4b\u7b97\u5927\u5c0f\u4e0d\u5339\u914d\uff1asize->" + info.getSize() + " realSize->" + odis.getSize());
        }
        info.setSize(odis.getSize());
        String md5 = info.getMd5();
        if (checksum) {
            if (StringUtils.isNotEmpty((String)md5)) {
                this.logger.error("\u4e0a\u4f20\u6587\u4ef6\u542f\u7528\u4e86MD5\u6821\u9a8c\uff0c\u4f46\u662f\u6587\u4ef6\u4fe1\u606f\u672a\u5305\u542bMD5\u53d6\u503c");
            } else {
                String omd = odis.getMd5();
                if (StringUtils.isEmpty((String)omd)) {
                    this.logger.warn("\u5f53\u524d\u5b58\u50a8\u4ecb\u8d28\u4e0d\u652f\u6301\u8ba1\u7b97MD5");
                } else if (!md5.equals(omd)) {
                    throw new ObjectStorageException("\u4e0a\u4f20\u6587\u4ef6\u7684MD5\u503c\u53d1\u751f\u4e86\u53d8\u5316\uff0c\u6587\u4ef6\u53ef\u80fd\u53d7\u635f");
                }
            }
        } else if (StringUtils.isNotEmpty((String)md5) && !md5.equals(odis.getMd5())) {
            this.logger.error("\u4e0a\u4f20\u6587\u4ef6\u7684MD5\u503c\u53d1\u751f\u4e86\u53d8\u5316\uff0c\u6587\u4ef6\u53ef\u80fd\u53d7\u635f\uff1a" + info.getKey());
        }
        info.setMd5(odis.getMd5());
        this.meta.addObjectInfo(this.bucket.getName(), info);
    }

    public boolean supportMultipartUpload() {
        return this.multipartUploader != null;
    }

    public String initMultipartUpload(ObjectInfo info) throws ObjectStorageException {
        if (this.multipartUploader == null) {
            throw new ObjectStorageException("\u5f53\u524d\u5b58\u50a8\u4ecb\u8d28\u4e0d\u652f\u6301\u6587\u4ef6\u5206\u7247\u4e0a\u4f20");
        }
        return this.multipartUploader.initUpload(info);
    }

    public PartEtag uploadMultiPart(String uploadId, String key, InputStream input, ObjectSliceInfo sliceInfo) throws ObjectStorageException {
        if (this.multipartUploader == null) {
            throw new ObjectStorageException("\u5f53\u524d\u5b58\u50a8\u4ecb\u8d28\u4e0d\u652f\u6301\u6587\u4ef6\u5206\u7247\u4e0a\u4f20");
        }
        return this.multipartUploader.uploadPart(uploadId, key, input, sliceInfo);
    }

    public void finishMultipartUpload(String uploadId, ObjectInfo info) throws ObjectStorageException {
        if (this.multipartUploader == null) {
            throw new ObjectStorageException("\u5f53\u524d\u5b58\u50a8\u4ecb\u8d28\u4e0d\u652f\u6301\u6587\u4ef6\u5206\u7247\u4e0a\u4f20");
        }
        MultipartUploadResult result = this.multipartUploader.finishUpload(uploadId, info.getKey());
        boolean needUpload = true;
        if (this.bucket.isLinkWhenExist()) {
            String realKey = this.meta.findObjectStoreKeyByMd5(this.bucket.getName(), result.getMd5(), result.getTotalSize());
            if (realKey != null) {
                info.setSize(result.getTotalSize());
                info.setMd5(result.getMd5());
                this.meta.addObjectInfo(this.bucket.getName(), info);
                this.meta.addObjectLink(this.bucket.getName(), info.getKey(), realKey);
                needUpload = false;
            } else {
                this.meta.addObjectLink(this.bucket.getName(), info.getKey(), info.getKey());
            }
        }
        if (needUpload) {
            if (info.getSize() > 0L && result.getTotalSize() != info.getSize()) {
                this.logger.warn("\u4e0a\u4f20\u6587\u4ef6\u5927\u5c0f\u4e0e\u6d4b\u7b97\u5927\u5c0f\u4e0d\u5339\u914d\uff1asize->" + info.getSize() + " realSize->" + result.getTotalSize());
            }
            info.setSize(result.getTotalSize());
            String md5 = info.getMd5();
            String rmd5 = result.getMd5();
            if (this.multipartUploader.supportCheckSum()) {
                if (StringUtils.isEmpty((String)md5)) {
                    info.setMd5(rmd5);
                } else if (!md5.equals(rmd5)) {
                    this.logger.error("\u4e0a\u4f20\u6587\u4ef6\u7684MD5\u503c\u53d1\u751f\u4e86\u53d8\u5316\uff0c\u6587\u4ef6\u53ef\u80fd\u53d7\u635f\uff1a" + info.getKey());
                    throw new ObjectStorageException("\u4e0a\u4f20\u6587\u4ef6\u7684MD5\u503c\u53d1\u751f\u4e86\u53d8\u5316\uff0c\u6587\u4ef6\u53ef\u80fd\u53d7\u635f");
                }
            }
            this.meta.addObjectInfo(this.bucket.getName(), info);
        } else {
            this.abortUploadPart(uploadId, info.getKey());
        }
    }

    public void abortUploadPart(String uploadId, String key) throws ObjectStorageException {
        if (this.multipartUploader == null) {
            throw new ObjectStorageException("\u5f53\u524d\u5b58\u50a8\u4ecb\u8d28\u4e0d\u652f\u6301\u6587\u4ef6\u5206\u7247\u4e0a\u4f20");
        }
        this.multipartUploader.abortUploadPart(uploadId, key);
    }

    public int getSize() throws ObjectStorageException {
        return this.meta.getObjectSize(this.bucket.getName());
    }

    public void upload(String key, InputStream input) throws ObjectStorageException {
        this.upload(key, input, null);
    }

    public String copy(String srcKey) throws ObjectStorageException {
        return this.copy(srcKey, null);
    }

    public String copy(String srcKey, String destName) throws ObjectStorageException {
        String destKey;
        ObjectInfo info = this.getObjectInfo(srcKey);
        if (info == null) {
            throw new ObjectNotFoundException("\u627e\u4e0d\u5230\u5bf9\u8c61\uff1a" + srcKey);
        }
        if (this.bucket.isLinkWhenExist()) {
            destKey = GUID.newGUID();
            String realKey = this.meta.findObjectStoreKeyByObjKey(this.bucket.getName(), srcKey);
            if (realKey != null) {
                this.meta.addObjectLink(this.bucket.getName(), destKey, realKey);
            } else {
                this.meta.addObjectLink(this.bucket.getName(), destKey, destKey);
            }
        } else {
            destKey = this.storage.copy(this.bucket.getName(), srcKey);
        }
        info.setKey(destKey);
        if (StringUtils.isNotEmpty((String)destName)) {
            info.setName(destName);
        }
        this.meta.addObjectInfo(this.bucket.getName(), info);
        return destKey;
    }

    public ObjectInfo getObjectInfo(String key) throws ObjectStorageException {
        return this.meta.getObjectInfo(this.bucket.getName(), key);
    }

    public List<ObjectInfo> find(ObjectFilterCondition cond, ObjectOrderField orderField) throws ObjectStorageException {
        return this.meta.find(this.bucket.getName(), cond, orderField);
    }

    public List<ObjectInfo> findObjectByName(String name, boolean exact) throws ObjectStorageException {
        return this.find(new ObjectFilterCondition("name", Arrays.asList(name), exact), null);
    }

    public List<ObjectInfo> findObjectByOwner(String owner) throws ObjectStorageException {
        return this.find(new ObjectFilterCondition("owner", owner), null);
    }

    public List<ObjectInfo> findObjectByProp(String propName, String propVal, boolean exact) throws ObjectStorageException {
        return this.find(new ObjectFilterCondition(propName, Arrays.asList(propVal), exact), null);
    }

    public List<ObjectInfo> findObjectByProp(String propName, String propVal, ObjectOrderField orderField) throws ObjectStorageException {
        return this.find(new ObjectFilterCondition(propName, propVal), orderField);
    }

    public void batchModifyObjectProp(List<String> objKeys, String propName, String propVal) throws ObjectStorageException {
        this.meta.batchModifyObjectProp(this.bucket.getName(), objKeys, propName, propVal);
    }

    public void modifyObjectProp(String objKey, String propName, String propVal) throws ObjectStorageException {
        this.batchModifyObjectProp(Arrays.asList(objKey), propName, propVal);
    }

    public InputStream download(String key) throws ObjectStorageException {
        return this.download(key, true);
    }

    public InputStream download(String key, boolean checkObjectInfo) throws ObjectStorageException {
        ObjectInfo objectInfo;
        String realKey;
        if (this.bucket.isLinkWhenExist() && (realKey = this.meta.findObjectStoreKeyByObjKey(this.bucket.getName(), key)) != null) {
            return this.storage.download(this.bucket.getName(), realKey);
        }
        if (checkObjectInfo && (objectInfo = this.getObjectInfo(key)) == null) {
            throw new ObjectNotFoundException("\u5bf9\u8c61\u4e0d\u5b58\u5728\uff1a" + key);
        }
        return this.storage.download(this.bucket.getName(), key);
    }

    public void download(String key, OutputStream output) throws ObjectStorageException {
        String realKey;
        if (this.bucket.isLinkWhenExist() && (realKey = this.meta.findObjectStoreKeyByObjKey(this.bucket.getName(), key)) != null) {
            this.storage.download(this.bucket.getName(), realKey, output);
            return;
        }
        ObjectInfo objectInfo = this.getObjectInfo(key);
        if (objectInfo == null) {
            throw new ObjectNotFoundException("\u5bf9\u8c61\u4e0d\u5b58\u5728\uff1a" + key);
        }
        this.storage.download(this.bucket.getName(), objectInfo.getKey(), output);
    }

    public boolean existObject(String key) throws ObjectStorageException {
        return this.existObject(key, 0);
    }

    public boolean existObject(String key, int checkMode) throws ObjectStorageException {
        if (checkMode == 1) {
            return this.meta.existObject(this.bucket.getName(), key);
        }
        if (checkMode == 2) {
            String realKey;
            String k = key;
            if (this.bucket.isLinkWhenExist() && (realKey = this.meta.findObjectStoreKeyByObjKey(this.bucket.getName(), key)) != null) {
                k = realKey;
            }
            return this.storage.existObject(this.bucket.getName(), k);
        }
        return this.existObject(key, 1) && this.existObject(key, 2);
    }

    @Override
    public void close() throws ObjectStorageException {
        if (this.storage != null) {
            this.storage.close();
        }
    }

    public boolean deleteObject(String key) throws ObjectStorageException {
        this.checkMigrate("\u5220\u9664\u5bf9\u8c61");
        if (this.bucket.isLinkWhenExist()) {
            String realKey = this.meta.findObjectStoreKeyByObjKey(this.bucket.getName(), key);
            if (realKey != null) {
                if (key.equals(realKey)) {
                    this.meta.updateObjectLinkDelTag(this.bucket.getName(), key);
                } else {
                    this.meta.deleteObjectLink(this.bucket.getName(), key);
                }
            }
        } else {
            this.storage.deleteObject(this.bucket.getName(), key);
        }
        return this.meta.deleteObject(this.bucket.getName(), key);
    }

    public void batchDeleteObject(List<String> keys) throws ObjectStorageException {
        this.checkMigrate("\u5220\u9664\u5bf9\u8c61");
        ArrayList<String> del_links = new ArrayList<String>();
        for (String key : keys) {
            if (this.bucket.isLinkWhenExist()) {
                String realKey = this.meta.findObjectStoreKeyByObjKey(this.bucket.getName(), key);
                if (realKey == null) continue;
                if (key.equals(realKey)) {
                    this.meta.updateObjectLinkDelTag(this.bucket.getName(), key);
                    continue;
                }
                del_links.add(key);
                continue;
            }
            this.storage.deleteObject(this.bucket.getName(), key);
        }
        if (del_links.size() > 0) {
            this.meta.batchDeleteObjectLink(this.bucket.getName(), del_links);
        }
        this.meta.batchDeleteObject(this.bucket.getName(), keys);
    }

    public int removeExpireObject() throws ObjectStorageException {
        List<String> keys = this.meta.getExpireObjectKeys(this.bucket.getName());
        if (keys == null || keys.isEmpty()) {
            return 0;
        }
        for (String key : keys) {
            this.storage.deleteObject(this.bucket.getName(), key);
            this.meta.deleteObject(this.bucket.getName(), key);
        }
        this.logger.info("\u6e05\u7406\u8fc7\u671f\u5bf9\u8c61\u6570\u636e\u6210\u529f\uff0c\u5171\u6e05\u7406" + keys.size() + "\u4e2a\u5bf9\u8c61\u3002\u6240\u5c5ebucket->" + this.bucket.getName());
        return keys.size();
    }

    private void checkMigrate(String operName) throws ObjectStorageException {
        if (this.meta.isBucketLocked(this.bucket.getName())) {
            throw new ObjectStorageException("\u5bf9\u8c61\u5b58\u50a8\u6570\u636e\u6b63\u5728\u8fc1\u79fb\uff0c\u65e0\u6cd5" + operName + "\u3002\u5982\u5728\u5373\u65f6\u4efb\u52a1\u76d1\u63a7\u4e2d\u786e\u8ba4\u8fc1\u79fb\u4efb\u52a1\u5df2\u7ed3\u675f\uff0c\u5c1d\u8bd5\u8fdb\u884c\u91cd\u7f6e\u8fc1\u79fb\u72b6\u6001\u3002");
        }
    }
}

