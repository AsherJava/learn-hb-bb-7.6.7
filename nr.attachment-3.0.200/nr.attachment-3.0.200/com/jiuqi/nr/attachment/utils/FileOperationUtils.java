/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.Bucket
 *  com.jiuqi.bi.oss.BucketService
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageEngine
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.bi.oss.ObjectStorageService
 */
package com.jiuqi.nr.attachment.utils;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageEngine;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.nr.attachment.message.RowDataInfo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileOperationUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileOperationUtils.class);
    public static final String JTABLE_FILE_AREA = "JTABLEAREA";
    public static final String NAME = "name";
    public static final String CREATETIME = "createtime";
    public static final String GROUPKEY = "fileGroupKey";
    public static final String SECRETLEVEL = "secretlevel";
    public static final String FILEPOOL = "filePool";
    public static final String CATEGORY = "category";

    @Deprecated
    public static ObjectStorageService objService(String bucketName) throws ObjectStorageException {
        BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
        boolean exist = bucketService.existBucket(bucketName);
        if (!exist) {
            Bucket bucket = new Bucket(bucketName);
            bucket.setDesc("\u5973\u5a32\u62a5\u8868-\u6570\u636e\u5f55\u5165-\u5b58\u653e\u9644\u4ef6\u548c\u56fe\u7247\u8d44\u6e90");
            bucketService.createBucket(bucket);
            bucketService.close();
            ObjectStorageManager.getInstance().makeObjectLinkEnable(bucketName);
        }
        return ObjectStorageManager.getInstance().createObjectService(bucketName);
    }

    public static ObjectStorageService getObjService(String bucketName) throws ObjectStorageException {
        ObjectStorageEngine objectStorageEngine = ObjectStorageEngine.newInstance();
        Bucket bucket = objectStorageEngine.getBucket(bucketName);
        if (null == bucket) {
            bucket = new Bucket(bucketName);
            bucket.setDesc("\u5973\u5a32\u62a5\u8868-\u6570\u636e\u5f55\u5165-\u5b58\u653e\u9644\u4ef6\u548c\u56fe\u7247\u8d44\u6e90");
            bucket.setLinkWhenExist(true);
            objectStorageEngine.createBucket(bucket);
        }
        return objectStorageEngine.createObjectService(bucketName);
    }

    public static void writeInputToOutput(InputStream is, OutputStream os) throws IOException {
        int len;
        int size = 1024;
        byte[] data = new byte[size];
        while ((len = is.read(data, 0, size)) != -1) {
            os.write(data, 0, len);
        }
    }

    public static byte[] convertByteArray(InputStream inStream) throws IOException {
        int rc;
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        while ((rc = inStream.read(buff, 0, 1024)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        return swapStream.toByteArray();
    }

    public static Date getCreateDate(ObjectInfo objectInfo) {
        if (objectInfo.getCreateTime() == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = df.parse(objectInfo.getCreateTime());
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return date;
    }

    public static void sortFiles(String order, String sortBy, List<RowDataInfo> rowDatas) {
        if (null != sortBy) {
            for (int i = 0; i < rowDatas.size() - 1; ++i) {
                int index = i;
                for (int j = i + 1; j < rowDatas.size(); ++j) {
                    if (sortBy.equals("title") && order.equals("asc")) {
                        if (rowDatas.get(index).getName().compareTo(rowDatas.get(j).getName()) <= 0) continue;
                        index = j;
                        continue;
                    }
                    if (sortBy.equals("title") && order.equals("desc")) {
                        if (rowDatas.get(index).getName().compareTo(rowDatas.get(j).getName()) >= 0) continue;
                        index = j;
                        continue;
                    }
                    if (sortBy.equals(CREATETIME) && order.equals("asc")) {
                        if (rowDatas.get(index).getCreatetime().compareTo(rowDatas.get(j).getCreatetime()) <= 0) continue;
                        index = j;
                        continue;
                    }
                    if (sortBy.equals(CREATETIME) && order.equals("desc")) {
                        if (rowDatas.get(index).getCreatetime().compareTo(rowDatas.get(j).getCreatetime()) >= 0) continue;
                        index = j;
                        continue;
                    }
                    if (sortBy.equals("size") && order.equals("asc")) {
                        if (rowDatas.get(index).getSize() <= rowDatas.get(j).getSize()) continue;
                        index = j;
                        continue;
                    }
                    if (!sortBy.equals("size") || !order.equals("desc") || rowDatas.get(index).getSize() >= rowDatas.get(j).getSize()) continue;
                    index = j;
                }
                if (index == i) continue;
                RowDataInfo transfer = rowDatas.get(index);
                rowDatas.set(index, rowDatas.get(i));
                rowDatas.set(i, transfer);
            }
        }
    }
}

