/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectFilterCondition
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.bi.oss.ObjectUploadMode
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.attachment.tools.impl;

import com.jiuqi.bi.oss.ObjectFilterCondition;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.oss.ObjectUploadMode;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.attachment.exception.OSSOperationException;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.provider.IFileBucketNameProvider;
import com.jiuqi.nr.attachment.provider.param.FileBucketNameParam;
import com.jiuqi.nr.attachment.service.FileUploadCheckService;
import com.jiuqi.nr.attachment.tools.IOSSOperationService;
import com.jiuqi.nr.attachment.utils.FileInfoBuilder;
import com.jiuqi.nr.attachment.utils.FileOperationUtils;
import com.jiuqi.nr.attachment.utils.FileStatus;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OSSOperationServiceImpl
implements IOSSOperationService,
Closeable {
    private static final Logger logger = LoggerFactory.getLogger(OSSOperationServiceImpl.class);
    private IFileBucketNameProvider fileBucketNameProvider;
    private FileUploadCheckService fileUploadCheckService;
    private ObjectStorageService objectStorageService;
    private String bucketName;

    public OSSOperationServiceImpl(IFileBucketNameProvider fileBucketNameProvider, FileUploadCheckService fileUploadCheckService) {
        this.fileBucketNameProvider = fileBucketNameProvider;
        this.fileUploadCheckService = fileUploadCheckService;
    }

    @Override
    public void init(FileBucketNameParam param) throws OSSOperationException {
        try {
            this.bucketName = "JTABLEAREA";
            if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                this.bucketName = this.fileBucketNameProvider.getBucketName(param);
            }
            this.objectStorageService = FileOperationUtils.getObjService(this.bucketName);
        }
        catch (ObjectStorageException e) {
            String errMsg = "\u9644\u4ef6\u5206\u533a\u670d\u52a1\u521d\u59cb\u5316\u5931\u8d25";
            logger.error(errMsg, e);
            throw new OSSOperationException(errMsg, e);
        }
    }

    @Override
    public FileInfo uploadByKey(String fileName, String fileKey, InputStream file, Map<String, String> expandInfo) throws OSSOperationException {
        return this.uploadFile(fileName, fileKey, file, expandInfo);
    }

    private FileInfo uploadFile(String fileName, String fileKey, InputStream file, Map<String, String> expandInfo) throws OSSOperationException {
        String extension = FileInfoBuilder.tryParseExtension(fileName);
        fileKey = this.ossUpload(fileKey, fileName, extension, file, expandInfo);
        try {
            ObjectInfo objectInfo = this.objectStorageService.getObjectInfo(fileKey);
            if (objectInfo != null) {
                FileInfo fileInfo = FileInfoBuilder.newFileInfo(fileKey, this.bucketName, fileName, extension, objectInfo.getSize(), FileStatus.AVAILABLE, FileInfoBuilder.resolveCurrentUserName(), new Date(), null, null, 0);
                fileInfo.setMd5(objectInfo.getMd5());
                fileInfo.setFileGroupKey((String)objectInfo.getExtProp().get("fileGroupKey"));
                fileInfo.setSecretlevel((String)objectInfo.getExtProp().get("secretlevel"));
                fileInfo.setFilepoolKey((String)objectInfo.getExtProp().get("filePool"));
                fileInfo.setCategory((String)objectInfo.getExtProp().get("category"));
                return fileInfo;
            }
            return null;
        }
        catch (Exception e) {
            String errMsg = String.format("\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25\uff0cfileKey\uff1a%s\uff0cfileName\uff1a%s", fileKey, fileName);
            logger.error(errMsg, e);
            this.ossDeleteIfExist(fileKey);
            throw new OSSOperationException(errMsg, e);
        }
    }

    private String ossUpload(String fileKey, String fileName, String extension, InputStream file, Map<String, String> expandInfo) throws OSSOperationException {
        try {
            String currentUser = FileInfoBuilder.resolveCurrentUserName();
            if (StringUtils.isEmpty((String)fileKey)) {
                fileKey = FileInfoBuilder.generateFileKey();
            }
            ObjectInfo info = new ObjectInfo();
            info.setExtension(extension);
            info.setKey(fileKey);
            info.setName(fileName);
            info.setOwner(currentUser);
            for (Map.Entry<String, String> expandCodeValue : expandInfo.entrySet()) {
                String expandCode = expandCodeValue.getKey();
                String value = expandInfo.get(expandCode);
                if (!StringUtils.isNotEmpty((String)value)) continue;
                info.getExtProp().put(expandCode, value);
            }
            if (null != this.fileUploadCheckService && this.fileUploadCheckService.fileUploadCheck()) {
                this.objectStorageService.upload(fileKey, file, info, ObjectUploadMode.OVERWRITE_BROKEN);
            } else {
                this.objectStorageService.upload(fileKey, file, info);
            }
            String string = fileKey;
            return string;
        }
        catch (Exception e) {
            String errMsg = String.format("\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25\uff0cfileKey\uff1a%s\uff0cfileName\uff1a%s", fileKey, fileName);
            throw new OSSOperationException(errMsg, e);
        }
        finally {
            if (null != file) {
                try {
                    file.close();
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    private void ossDeleteIfExist(String fileKey) throws OSSOperationException {
        try {
            this.objectStorageService.deleteObject(fileKey);
        }
        catch (ObjectStorageException e) {
            String errMsg = String.format("\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25\uff0cfileKey\uff1a%s", fileKey);
            logger.error(errMsg, e);
            throw new OSSOperationException(errMsg, e);
        }
    }

    @Override
    public List<String> batchCopy(List<String> fileKeys) throws OSSOperationException {
        if (null == fileKeys || fileKeys.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            ArrayList<String> copyFileKeys = new ArrayList<String>();
            for (String fileKey : fileKeys) {
                String copyFileKey = this.objectStorageService.copy(fileKey);
                copyFileKeys.add(copyFileKey);
            }
            return copyFileKeys;
        }
        catch (ObjectStorageException e) {
            String errMsg = String.format("\u9644\u4ef6\u590d\u5236\u5931\u8d25\uff0cfileKeys\uff1a%s", String.join((CharSequence)";", fileKeys));
            logger.error(errMsg, e);
            throw new OSSOperationException(errMsg, e);
        }
    }

    @Override
    public void batchDelete(List<String> fileKeys) throws OSSOperationException {
        try {
            this.objectStorageService.batchDeleteObject(fileKeys);
        }
        catch (ObjectStorageException e) {
            String errMsg = String.format("\u9644\u4ef6\u6279\u91cf\u5220\u9664\u5931\u8d25\uff0cfileKeys\uff1a%s", String.join((CharSequence)";", fileKeys));
            logger.error(errMsg, e);
            throw new OSSOperationException(errMsg, e);
        }
    }

    @Override
    public void batchDeletePic(List<String> picFileKeys) throws OSSOperationException {
        try {
            ArrayList<String> delPicFileKeys = new ArrayList<String>();
            for (String picFileKey : picFileKeys) {
                delPicFileKeys.add(picFileKey);
                List thumbnailObjInfos = this.objectStorageService.findObjectByProp("fileGroupKey", picFileKey, true);
                if (CollectionUtils.isEmpty(thumbnailObjInfos)) continue;
                delPicFileKeys.addAll(thumbnailObjInfos.stream().map(ObjectInfo::getKey).collect(Collectors.toList()));
            }
            this.objectStorageService.batchDeleteObject(delPicFileKeys);
        }
        catch (ObjectStorageException e) {
            String errMsg = String.format("\u56fe\u7247\u6279\u91cf\u5220\u9664\u5931\u8d25\uff0cpicFileKeys\uff1a%s", String.join((CharSequence)";", picFileKeys));
            logger.error(errMsg, e);
            throw new OSSOperationException(errMsg, e);
        }
    }

    @Override
    public void update(String fileKey, Map<String, String> updateInfo) throws OSSOperationException {
        try {
            for (Map.Entry<String, String> updateInfoCodeNewValue : updateInfo.entrySet()) {
                String updateInfoCode = updateInfoCodeNewValue.getKey();
                String newValue = updateInfoCodeNewValue.getValue();
                if (!StringUtils.isNotEmpty((String)newValue)) continue;
                this.objectStorageService.modifyObjectProp(fileKey, updateInfoCode, newValue);
            }
        }
        catch (ObjectStorageException e) {
            String errMsg = String.format("\u4fee\u6539\u9644\u4ef6\u4fe1\u606f\u5931\u8d25\uff0cfileKey\uff1a%s", fileKey);
            logger.error(errMsg, e);
            throw new OSSOperationException(errMsg, e);
        }
    }

    @Override
    public void batchUpdate(List<String> fileKeys, Map<String, String> updateInfo) throws OSSOperationException {
        if (fileKeys.isEmpty() || updateInfo.isEmpty()) {
            return;
        }
        try {
            for (Map.Entry<String, String> updateInfoCodeNewUpdateInfo : updateInfo.entrySet()) {
                String updateInfoCode = updateInfoCodeNewUpdateInfo.getKey();
                String newUpdateInfo = updateInfoCodeNewUpdateInfo.getValue();
                if (!StringUtils.isNotEmpty((String)newUpdateInfo)) continue;
                this.objectStorageService.batchModifyObjectProp(fileKeys, updateInfoCode, newUpdateInfo);
            }
        }
        catch (ObjectStorageException e) {
            String errMsg = String.format("\u6279\u91cf\u4fee\u6539\u9644\u4ef6\u4fe1\u606f\u5931\u8d25\uff0cfileKeys\uff1a%s", String.join((CharSequence)";", fileKeys));
            logger.error(errMsg, e);
            throw new OSSOperationException(errMsg, e);
        }
    }

    @Override
    public void download(String fileKey, OutputStream outputStream) throws OSSOperationException {
        try {
            this.objectStorageService.download(fileKey, outputStream);
        }
        catch (ObjectStorageException e) {
            String errMsg = String.format("\u4e0b\u8f7d\u9644\u4ef6\u5931\u8d25\uff0cfileKey\uff1a%s", fileKey);
            logger.error(errMsg, e);
            throw new OSSOperationException(errMsg, e);
        }
    }

    @Override
    public List<ObjectInfo> getObjectInfoByKeys(List<String> fileKeys, boolean exist) throws OSSOperationException {
        if (null == fileKeys || fileKeys.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            ArrayList<ObjectInfo> finalObjectInfo = new ArrayList();
            ArrayList<String> propValues = new ArrayList<String>(fileKeys);
            ObjectFilterCondition cond = new ObjectFilterCondition("OBJ_KEY", propValues);
            List objectInfos = this.objectStorageService.find(cond, null);
            if (exist) {
                for (ObjectInfo objectInfo : objectInfos) {
                    boolean rs = this.objectStorageService.existObject(objectInfo.getKey(), 2);
                    if (!rs) continue;
                    finalObjectInfo.add(objectInfo);
                }
            } else {
                finalObjectInfo = objectInfos;
            }
            return finalObjectInfo;
        }
        catch (ObjectStorageException e) {
            String errMsg = String.format("\u6839\u636efileKey\u6279\u91cf\u67e5\u8be2\u9644\u4ef6\u4fe1\u606f\u5931\u8d25\uff0cfileKeys\uff1a%s", String.join((CharSequence)";", fileKeys));
            logger.error(errMsg, e);
            throw new OSSOperationException(errMsg, e);
        }
    }

    @Override
    public List<ObjectInfo> getObjectInfoByProp(String propName, String propValue, boolean exist) throws OSSOperationException {
        try {
            ArrayList<ObjectInfo> finalObjectInfo = new ArrayList();
            List prop = this.objectStorageService.findObjectByProp(propName, propValue, true);
            if (exist) {
                for (ObjectInfo objectInfo : prop) {
                    boolean rs = this.objectStorageService.existObject(objectInfo.getKey(), 2);
                    if (!rs) continue;
                    finalObjectInfo.add(objectInfo);
                }
            } else {
                finalObjectInfo = prop;
            }
            return finalObjectInfo;
        }
        catch (ObjectStorageException e) {
            String errMsg = String.format("\u6839\u636e\u9644\u4ef6\u62d3\u5c55\u5c5e\u6027\u67e5\u8be2\u9644\u4ef6\u4fe1\u606f\u5931\u8d25\uff0c\u62d3\u5c55\u5c5e\u6027\u540d\uff1a%s\uff0c\u62d3\u5c55\u5c5e\u6027\u503c\uff1a%s", propName, propValue);
            logger.error(errMsg, e);
            throw new OSSOperationException(errMsg, e);
        }
    }

    @Override
    public Map<String, List<ObjectInfo>> batchGetObjectInfoByProp(String propName, List<String> propValues, boolean exist) throws OSSOperationException {
        try {
            HashMap<String, List<ObjectInfo>> finalObjectInfoMap = new HashMap<String, List<ObjectInfo>>();
            for (String propValue : propValues) {
                List prop = this.objectStorageService.findObjectByProp(propName, propValue, true);
                if (CollectionUtils.isEmpty(prop)) continue;
                List<ObjectInfo> finalObjectInfo = new ArrayList();
                if (exist) {
                    for (ObjectInfo objectInfo : prop) {
                        boolean rs = this.objectStorageService.existObject(objectInfo.getKey(), 2);
                        if (!rs) continue;
                        finalObjectInfo.add(objectInfo);
                    }
                } else {
                    finalObjectInfo = prop;
                }
                finalObjectInfoMap.put(propValue, finalObjectInfo);
            }
            return finalObjectInfoMap;
        }
        catch (ObjectStorageException e) {
            String errMsg = String.format("\u6839\u636e\u9644\u4ef6\u62d3\u5c55\u5c5e\u6027\u6279\u91cf\u67e5\u8be2\u9644\u4ef6\u4fe1\u606f\u5931\u8d25\uff0c\u62d3\u5c55\u5c5e\u6027\u540d\uff1a%s\uff0c\u62d3\u5c55\u5c5e\u6027\u503c\uff1a%s", propName, String.join((CharSequence)";", propValues));
            logger.error(errMsg, e);
            throw new OSSOperationException(errMsg, e);
        }
    }

    @Override
    public void close() throws IOException {
        if (null != this.objectStorageService) {
            try {
                this.objectStorageService.close();
            }
            catch (ObjectStorageException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}

