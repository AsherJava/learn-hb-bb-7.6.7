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
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.attachment.tools.impl;

import com.jiuqi.bi.oss.ObjectFilterCondition;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.oss.ObjectUploadMode;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.attachment.exception.FileException;
import com.jiuqi.nr.attachment.exception.FileNotFoundException;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.provider.IFileBucketNameProvider;
import com.jiuqi.nr.attachment.provider.param.FileBucketNameParam;
import com.jiuqi.nr.attachment.service.FileUploadCheckService;
import com.jiuqi.nr.attachment.tools.AttachmentFileAreaService;
import com.jiuqi.nr.attachment.utils.FileInfoBuilder;
import com.jiuqi.nr.attachment.utils.FileOperationUtils;
import com.jiuqi.nr.attachment.utils.FileStatus;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentFileAreaServiceImpl
implements AttachmentFileAreaService {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentFileAreaServiceImpl.class);
    @Resource
    private UserService<User> userService;
    @Autowired(required=false)
    private FileUploadCheckService fileUploadCheckService;
    @Autowired(required=false)
    private IFileBucketNameProvider fileBucketNameProvider;
    private static final String SEPARATOR = "/";
    private static final long DEFAULT_EXPIRATION_TIME = 86400L;
    private static final String ERROR_TEXT = "\uff0c\u9519\u8bef\u4fe1\u606f\uff1a";

    @Override
    public FileInfo upload(FileBucketNameParam param, String fileName, InputStream file, Map<String, String> expandInfo) {
        return this.uploadFile(param, fileName, null, file, expandInfo);
    }

    @Override
    public FileInfo uploadByKey(FileBucketNameParam param, String fileName, String fileKey, InputStream file, Map<String, String> expandInfo) {
        return this.uploadFile(param, fileName, fileKey, file, expandInfo);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void uploadByKey(FileBucketNameParam param, String fileName, String fileKey, String owner, String createTime, InputStream file, Map<String, String> expandInfo) {
        ObjectStorageService objectStorageService;
        block11: {
            String extension = FileInfoBuilder.tryParseExtension(fileName);
            objectStorageService = null;
            try {
                ObjectInfo info = new ObjectInfo();
                info.setExtension(extension);
                info.setKey(fileKey);
                info.setName(fileName);
                info.setOwner(owner);
                info.setCreateTime(createTime);
                long size = file.available();
                info.setSize(size);
                String md5 = DigestUtils.md5Hex(file);
                file.reset();
                info.setMd5(md5);
                for (Map.Entry<String, String> expandCodeInfo : expandInfo.entrySet()) {
                    String expandCode = expandCodeInfo.getKey();
                    String value = expandCodeInfo.getValue();
                    if (!StringUtils.isNotEmpty((String)value)) continue;
                    info.getExtProp().put(expandCode, value);
                }
                String bucketName = "JTABLEAREA";
                if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                    bucketName = this.fileBucketNameProvider.getBucketName(param);
                }
                objectStorageService = FileOperationUtils.getObjService(bucketName);
                objectStorageService.upload(fileKey, file, info);
                if (null == file) break block11;
            }
            catch (Exception e) {
                try {
                    throw new FileException("\u9644\u4ef6\u66f4\u65b0\u4e0a\u4f20\u5931\u8d25\uff0cfileKey\uff1a" + fileKey, e);
                }
                catch (Throwable throwable) {
                    if (null != file) {
                        try {
                            file.close();
                        }
                        catch (IOException e2) {
                            logger.error(e2.getMessage(), e2);
                        }
                    }
                    this.closeOSS(objectStorageService);
                    throw throwable;
                }
            }
            try {
                file.close();
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        this.closeOSS(objectStorageService);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public FileInfo delete(FileBucketNameParam param, String fileKey) {
        ObjectStorageService objectStorageService;
        block7: {
            objectStorageService = null;
            String bucketName = "JTABLEAREA";
            if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                bucketName = this.fileBucketNameProvider.getBucketName(param);
            }
            objectStorageService = FileOperationUtils.getObjService(bucketName);
            ObjectInfo objectInfo = objectStorageService.getObjectInfo(fileKey);
            objectStorageService.deleteObject(fileKey);
            if (objectInfo == null) break block7;
            User user = this.userService.getByUsername(objectInfo.getOwner());
            String fullname = "";
            if (null != user) {
                fullname = user.getFullname();
            }
            fullname = StringUtils.isEmpty((String)fullname) ? objectInfo.getOwner() : fullname;
            Date date = FileOperationUtils.getCreateDate(objectInfo);
            FileInfo fileInfo = FileInfoBuilder.newFileInfo(fileKey, bucketName, objectInfo.getName(), objectInfo.getExtension(), objectInfo.getSize(), FileStatus.AVAILABLE, fullname, date, null, null, 0);
            fileInfo.setMd5(objectInfo.getMd5());
            fileInfo.setFileGroupKey((String)objectInfo.getExtProp().get("fileGroupKey"));
            fileInfo.setSecretlevel((String)objectInfo.getExtProp().get("secretlevel"));
            fileInfo.setFilepoolKey((String)objectInfo.getExtProp().get("filePool"));
            fileInfo.setCategory((String)objectInfo.getExtProp().get("category"));
            FileInfo fileInfo2 = fileInfo;
            this.closeOSS(objectStorageService);
            return fileInfo2;
        }
        try {
            try {
                throw new FileNotFoundException(fileKey);
            }
            catch (ObjectStorageException e) {
                logger.error("\u9644\u4ef6\u5220\u9664\u5931\u8d25\uff0c\u9644\u4ef6key\uff1a" + fileKey + ERROR_TEXT + e.getMessage(), e);
                FileInfo fileInfo = null;
                this.closeOSS(objectStorageService);
                return fileInfo;
            }
        }
        catch (Throwable throwable) {
            this.closeOSS(objectStorageService);
            throw throwable;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void batchDelete(FileBucketNameParam param, List<String> fileKeys) {
        if (null == fileKeys || fileKeys.isEmpty()) {
            return;
        }
        ObjectStorageService objService = null;
        try {
            String bucketName = "JTABLEAREA";
            if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                bucketName = this.fileBucketNameProvider.getBucketName(param);
            }
            objService = FileOperationUtils.getObjService(bucketName);
            objService.batchDeleteObject(fileKeys);
            this.closeOSS(objService);
        }
        catch (ObjectStorageException e) {
            try {
                logger.error("\u9644\u4ef6\u6279\u91cf\u5220\u9664\u5931\u8d25\uff0c\u9644\u4ef6keys\uff1a" + fileKeys.toString() + ERROR_TEXT + e.getMessage(), e);
                this.closeOSS(objService);
            }
            catch (Throwable throwable) {
                this.closeOSS(objService);
                throw throwable;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void batchDeletePic(FileBucketNameParam param, List<String> groupKeys) {
        if (CollectionUtils.isEmpty(groupKeys)) {
            return;
        }
        ObjectStorageService objService = null;
        try {
            String bucketName = "JTABLEAREA";
            if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                bucketName = this.fileBucketNameProvider.getBucketName(param);
            }
            objService = FileOperationUtils.getObjService(bucketName);
            ArrayList delFileKeys = new ArrayList();
            for (String groupKey : groupKeys) {
                List picObjInfos = objService.findObjectByProp("fileGroupKey", groupKey, true);
                if (CollectionUtils.isEmpty(picObjInfos)) continue;
                delFileKeys.addAll(picObjInfos.stream().map(ObjectInfo::getKey).collect(Collectors.toList()));
                for (ObjectInfo objectInfo : picObjInfos) {
                    List thumbnailObjInfos = objService.findObjectByProp("fileGroupKey", objectInfo.getKey(), true);
                    if (!CollectionUtils.isEmpty(thumbnailObjInfos)) continue;
                    delFileKeys.addAll(thumbnailObjInfos.stream().map(ObjectInfo::getKey).collect(Collectors.toList()));
                }
            }
            if (!CollectionUtils.isEmpty(delFileKeys)) {
                objService.batchDeleteObject(delFileKeys);
            }
            this.closeOSS(objService);
        }
        catch (ObjectStorageException e) {
            try {
                logger.error(String.format("\u6279\u91cf\u5220\u9664\u56fe\u7247\u5931\u8d25\uff0c\u56fe\u7247groupKeys\uff1a%s", String.join((CharSequence)";", groupKeys)), e);
                this.closeOSS(objService);
            }
            catch (Throwable throwable) {
                this.closeOSS(objService);
                throw throwable;
            }
        }
    }

    @Override
    public void update(FileBucketNameParam param, String fileKey, Map<String, String> updateInfo) throws ObjectStorageException {
        String bucketName = "JTABLEAREA";
        if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
            bucketName = this.fileBucketNameProvider.getBucketName(param);
        }
        ObjectStorageService objectStorageService = FileOperationUtils.getObjService(bucketName);
        for (Map.Entry<String, String> updateInfoCodeNewValue : updateInfo.entrySet()) {
            String updateInfoCode = updateInfoCodeNewValue.getKey();
            String newValue = updateInfoCodeNewValue.getValue();
            if (!StringUtils.isNotEmpty((String)newValue)) continue;
            objectStorageService.modifyObjectProp(fileKey, updateInfoCode, newValue);
        }
        objectStorageService.close();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void batchupdate(FileBucketNameParam param, List<String> fileKeys, Map<String, String> updateInfo) {
        if (fileKeys.isEmpty() || updateInfo.isEmpty()) {
            return;
        }
        ObjectStorageService objectStorageService = null;
        try {
            String bucketName = "JTABLEAREA";
            if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                bucketName = this.fileBucketNameProvider.getBucketName(param);
            }
            objectStorageService = FileOperationUtils.getObjService(bucketName);
            for (Map.Entry<String, String> updateInfoCodeNewUpdateInfo : updateInfo.entrySet()) {
                String updateInfoCode = updateInfoCodeNewUpdateInfo.getKey();
                String newUpdateInfo = updateInfoCodeNewUpdateInfo.getValue();
                if (!StringUtils.isNotEmpty((String)newUpdateInfo)) continue;
                objectStorageService.batchModifyObjectProp(fileKeys, updateInfoCode, newUpdateInfo);
            }
            this.closeOSS(objectStorageService);
        }
        catch (Exception e) {
            try {
                logger.error(e.getMessage(), e);
                this.closeOSS(objectStorageService);
            }
            catch (Throwable throwable) {
                this.closeOSS(objectStorageService);
                throw throwable;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> batchFileCopy(FileBucketNameParam param, List<String> fileKeys) {
        ArrayList<String> copyFileKeys = new ArrayList<String>();
        if (null == fileKeys || fileKeys.isEmpty()) {
            return copyFileKeys;
        }
        ObjectStorageService objectStorageService = null;
        try {
            String bucketName = "JTABLEAREA";
            if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                bucketName = this.fileBucketNameProvider.getBucketName(param);
            }
            objectStorageService = FileOperationUtils.getObjService(bucketName);
            for (String fileKey : fileKeys) {
                String copyFileKey = objectStorageService.copy(fileKey);
                copyFileKeys.add(copyFileKey);
            }
            ArrayList<String> arrayList = copyFileKeys;
            this.closeOSS(objectStorageService);
            return arrayList;
        }
        catch (Exception e) {
            try {
                logger.error("\u9644\u4ef6\u590d\u5236\u51fa\u9519\uff1a" + e.getMessage() + ",\u9644\u4ef6keys\uff1a" + fileKeys.toString(), e);
                this.closeOSS(objectStorageService);
            }
            catch (Throwable throwable) {
                this.closeOSS(objectStorageService);
                throw throwable;
            }
        }
        return copyFileKeys;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public FileInfo getFileInfo(FileBucketNameParam param, String fileKey) {
        ObjectStorageService objectStorageService;
        block9: {
            String bucketName;
            block8: {
                boolean b;
                objectStorageService = null;
                bucketName = "JTABLEAREA";
                if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                    bucketName = this.fileBucketNameProvider.getBucketName(param);
                }
                if (b = (objectStorageService = FileOperationUtils.getObjService(bucketName)).existObject(fileKey)) break block8;
                FileInfo fileInfo = null;
                this.closeOSS(objectStorageService);
                return fileInfo;
            }
            ObjectInfo objectInfo = objectStorageService.getObjectInfo(fileKey);
            if (objectInfo == null) break block9;
            User user = this.userService.getByUsername(objectInfo.getOwner());
            String fullname = "";
            if (null != user) {
                fullname = user.getFullname();
            }
            fullname = StringUtils.isEmpty((String)fullname) ? objectInfo.getOwner() : fullname;
            Date date = FileOperationUtils.getCreateDate(objectInfo);
            FileInfo fileInfo = FileInfoBuilder.newFileInfo(fileKey, bucketName, objectInfo.getName(), objectInfo.getExtension(), objectInfo.getSize(), FileStatus.AVAILABLE, fullname, date, null, null, 0);
            fileInfo.setMd5(objectInfo.getMd5());
            fileInfo.setFileGroupKey((String)objectInfo.getExtProp().get("fileGroupKey"));
            fileInfo.setSecretlevel((String)objectInfo.getExtProp().get("secretlevel"));
            fileInfo.setFilepoolKey((String)objectInfo.getExtProp().get("filePool"));
            fileInfo.setCategory((String)objectInfo.getExtProp().get("category"));
            FileInfo fileInfo2 = fileInfo;
            this.closeOSS(objectStorageService);
            return fileInfo2;
        }
        try {
            FileInfo fileInfo = null;
            this.closeOSS(objectStorageService);
            return fileInfo;
        }
        catch (Exception e) {
            try {
                logger.error("\u83b7\u53d6\u9644\u4ef6\u4fe1\u606f\u5931\u8d25\uff0c\u9644\u4ef6key\uff1a" + fileKey + ERROR_TEXT + e.getMessage(), e);
                FileInfo fileInfo = null;
                this.closeOSS(objectStorageService);
                return fileInfo;
            }
            catch (Throwable throwable) {
                this.closeOSS(objectStorageService);
                throw throwable;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ObjectInfo getObjectInfo(FileBucketNameParam param, String fileKey) {
        ObjectStorageService objectStorageService;
        block6: {
            boolean b;
            objectStorageService = null;
            String bucketName = "JTABLEAREA";
            if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                bucketName = this.fileBucketNameProvider.getBucketName(param);
            }
            if (b = (objectStorageService = FileOperationUtils.getObjService(bucketName)).existObject(fileKey)) break block6;
            ObjectInfo objectInfo = null;
            this.closeOSS(objectStorageService);
            return objectInfo;
        }
        try {
            ObjectInfo objectInfo = objectStorageService.getObjectInfo(fileKey);
            this.closeOSS(objectStorageService);
            return objectInfo;
        }
        catch (Exception e) {
            try {
                logger.error("\u83b7\u53d6\u9644\u4ef6\u4fe1\u606f\u5931\u8d25\uff0c\u9644\u4ef6key\uff1a" + fileKey + ERROR_TEXT + e.getMessage(), e);
                ObjectInfo objectInfo = null;
                this.closeOSS(objectStorageService);
                return objectInfo;
            }
            catch (Throwable throwable) {
                this.closeOSS(objectStorageService);
                throw throwable;
            }
        }
    }

    @Override
    public List<FileInfo> getFileInfoByKeys(FileBucketNameParam param, List<String> fileKeys) {
        return this.getFileInfoByKeys(param, fileKeys, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<FileInfo> getFileInfoByKeys(FileBucketNameParam param, List<String> fileKeys, boolean exist) {
        ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
        if (null == fileKeys || fileKeys.isEmpty()) {
            return fileInfos;
        }
        ObjectStorageService objectStorageService = null;
        try {
            String bucketName = "JTABLEAREA";
            if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                bucketName = this.fileBucketNameProvider.getBucketName(param);
            }
            objectStorageService = FileOperationUtils.getObjService(bucketName);
            ArrayList<String> propValues = new ArrayList<String>(fileKeys);
            ObjectFilterCondition cond = new ObjectFilterCondition("OBJ_KEY", propValues);
            List objectInfos = objectStorageService.find(cond, null);
            for (ObjectInfo objectInfo : objectInfos) {
                boolean rs;
                if (exist && !(rs = objectStorageService.existObject(objectInfo.getKey(), 2))) continue;
                User user = this.userService.getByUsername(objectInfo.getOwner());
                String fullname = "";
                if (null != user) {
                    fullname = user.getFullname();
                }
                fullname = StringUtils.isEmpty((String)fullname) ? objectInfo.getOwner() : fullname;
                Date date = FileOperationUtils.getCreateDate(objectInfo);
                FileInfo fileInfo = FileInfoBuilder.newFileInfo(objectInfo.getKey(), bucketName, objectInfo.getName(), objectInfo.getExtension(), objectInfo.getSize(), FileStatus.AVAILABLE, fullname, date, null, null, 0);
                fileInfo.setMd5(objectInfo.getMd5());
                fileInfo.setFileGroupKey((String)objectInfo.getExtProp().get("fileGroupKey"));
                fileInfo.setSecretlevel((String)objectInfo.getExtProp().get("secretlevel"));
                fileInfo.setFilepoolKey((String)objectInfo.getExtProp().get("filePool"));
                fileInfo.setCategory((String)objectInfo.getExtProp().get("category"));
                fileInfos.add(fileInfo);
            }
            ArrayList<FileInfo> arrayList = fileInfos;
            this.closeOSS(objectStorageService);
            return arrayList;
        }
        catch (Exception e) {
            try {
                logger.error("\u6279\u91cf\u83b7\u53d6\u9644\u4ef6\u4fe1\u606f\u5931\u8d25\uff0c\u9644\u4ef6keys\uff1a" + fileKeys + ERROR_TEXT + e.getMessage(), e);
                ArrayList<FileInfo> arrayList = fileInfos;
                this.closeOSS(objectStorageService);
                return arrayList;
            }
            catch (Throwable throwable) {
                this.closeOSS(objectStorageService);
                throw throwable;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<ObjectInfo> getObjectInfoByKeys(FileBucketNameParam param, List<String> fileKeys) {
        if (null == fileKeys || fileKeys.isEmpty()) {
            return new ArrayList<ObjectInfo>();
        }
        ObjectStorageService objectStorageService = null;
        try {
            String bucketName = "JTABLEAREA";
            if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                bucketName = this.fileBucketNameProvider.getBucketName(param);
            }
            objectStorageService = FileOperationUtils.getObjService(bucketName);
            ArrayList<String> propValues = new ArrayList<String>(fileKeys);
            ObjectFilterCondition cond = new ObjectFilterCondition("OBJ_KEY", propValues);
            List objectInfos = objectStorageService.find(cond, null);
            ArrayList<ObjectInfo> finalObjectInfo = new ArrayList<ObjectInfo>();
            for (ObjectInfo objectInfo : objectInfos) {
                boolean rs = objectStorageService.existObject(objectInfo.getKey(), 2);
                if (!rs) continue;
                finalObjectInfo.add(objectInfo);
            }
            ArrayList<ObjectInfo> arrayList = finalObjectInfo;
            this.closeOSS(objectStorageService);
            return arrayList;
        }
        catch (Exception e) {
            try {
                logger.error("\u6279\u91cf\u83b7\u53d6\u9644\u4ef6\u4fe1\u606f\u5931\u8d25\uff0c\u9644\u4ef6keys\uff1a" + fileKeys + ERROR_TEXT + e.getMessage(), e);
                ArrayList<ObjectInfo> arrayList = new ArrayList<ObjectInfo>();
                this.closeOSS(objectStorageService);
                return arrayList;
            }
            catch (Throwable throwable) {
                this.closeOSS(objectStorageService);
                throw throwable;
            }
        }
    }

    @Override
    public List<FileInfo> getFileInfoByProp(FileBucketNameParam param, String propName, String propValue) {
        return this.getFileInfoByProp(param, propName, propValue, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<FileInfo> getFileInfoByProp(FileBucketNameParam param, String propName, String propValue, boolean exist) {
        ArrayList<FileInfo> fileInfos = new ArrayList<FileInfo>();
        ObjectStorageService objectStorageService = null;
        try {
            String bucketName = "JTABLEAREA";
            if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                bucketName = this.fileBucketNameProvider.getBucketName(param);
            }
            objectStorageService = FileOperationUtils.getObjService(bucketName);
            List prop = objectStorageService.findObjectByProp(propName, propValue, true);
            for (ObjectInfo objectInfo : prop) {
                boolean rs;
                if (exist && !(rs = objectStorageService.existObject(objectInfo.getKey(), 2))) continue;
                User user = this.userService.getByUsername(objectInfo.getOwner());
                String fullname = "";
                if (null != user) {
                    fullname = user.getFullname();
                }
                fullname = StringUtils.isEmpty((String)fullname) ? objectInfo.getOwner() : fullname;
                Date date = FileOperationUtils.getCreateDate(objectInfo);
                FileInfo fileInfo = FileInfoBuilder.newFileInfo(objectInfo.getKey(), bucketName, objectInfo.getName(), objectInfo.getExtension(), objectInfo.getSize(), FileStatus.AVAILABLE, fullname, date, null, null, 0);
                fileInfo.setMd5(objectInfo.getMd5());
                fileInfo.setFileGroupKey((String)objectInfo.getExtProp().get("fileGroupKey"));
                fileInfo.setSecretlevel((String)objectInfo.getExtProp().get("secretlevel"));
                fileInfo.setFilepoolKey((String)objectInfo.getExtProp().get("filePool"));
                fileInfo.setCategory((String)objectInfo.getExtProp().get("category"));
                fileInfos.add(fileInfo);
            }
            ArrayList<FileInfo> arrayList = fileInfos;
            this.closeOSS(objectStorageService);
            return arrayList;
        }
        catch (ObjectStorageException e) {
            try {
                logger.error("\u6839\u636e\u62d3\u5c55\u5c5e\u6027\u83b7\u53d6\u9644\u4ef6\u4fe1\u606f\u5931\u8d25\uff0c\u62d3\u5c55\u5c5e\u6027\u540d\u79f0\uff1a" + propName + "\uff0c\u62d3\u5c55\u5c5e\u6027\u503c\uff1a" + propValue + ERROR_TEXT + e.getMessage(), e);
                ArrayList<FileInfo> arrayList = fileInfos;
                this.closeOSS(objectStorageService);
                return arrayList;
            }
            catch (Throwable throwable) {
                this.closeOSS(objectStorageService);
                throw throwable;
            }
        }
    }

    @Override
    public List<ObjectInfo> getObjectInfoByProp(FileBucketNameParam param, String propName, String propValue) {
        return this.getObjectInfoByProp(param, propName, propValue, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<ObjectInfo> getObjectInfoByProp(FileBucketNameParam param, String propName, String propValue, boolean exist) {
        ObjectStorageService objectStorageService = null;
        try {
            String bucketName = "JTABLEAREA";
            if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                bucketName = this.fileBucketNameProvider.getBucketName(param);
            }
            objectStorageService = FileOperationUtils.getObjService(bucketName);
            List prop = objectStorageService.findObjectByProp(propName, propValue, true);
            List<ObjectInfo> finalObjectInfo = new ArrayList();
            if (exist) {
                for (ObjectInfo objectInfo : prop) {
                    boolean rs = objectStorageService.existObject(objectInfo.getKey(), 2);
                    if (!rs) continue;
                    finalObjectInfo.add(objectInfo);
                }
            } else {
                finalObjectInfo = prop;
            }
            List<ObjectInfo> list = finalObjectInfo;
            this.closeOSS(objectStorageService);
            return list;
        }
        catch (ObjectStorageException e) {
            try {
                logger.error("\u6839\u636e\u62d3\u5c55\u5c5e\u6027\u83b7\u53d6\u9644\u4ef6\u4fe1\u606f\u5931\u8d25\uff0c\u62d3\u5c55\u5c5e\u6027\u540d\u79f0\uff1a" + propName + "\uff0c\u62d3\u5c55\u5c5e\u6027\u503c\uff1a" + propValue + ERROR_TEXT + e.getMessage(), e);
                ArrayList<ObjectInfo> arrayList = new ArrayList<ObjectInfo>();
                this.closeOSS(objectStorageService);
                return arrayList;
            }
            catch (Throwable throwable) {
                this.closeOSS(objectStorageService);
                throw throwable;
            }
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public byte[] download(FileBucketNameParam param, String fileKey) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
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

    @Override
    public void download(FileBucketNameParam param, String fileKey, OutputStream outputStream) {
        boolean b = false;
        ObjectStorageService objectStorageService = null;
        try {
            String bucketName = "JTABLEAREA";
            if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                bucketName = this.fileBucketNameProvider.getBucketName(param);
            }
            objectStorageService = FileOperationUtils.getObjService(bucketName);
            b = objectStorageService.existObject(fileKey);
        }
        catch (ObjectStorageException e) {
            logger.error(e.getMessage(), e);
        }
        if (!b) {
            throw new FileNotFoundException(fileKey);
        }
        try {
            objectStorageService.download(fileKey, outputStream);
        }
        catch (Exception e) {
            throw new FileException("failed to down load file.fileKey:" + fileKey, e);
        }
        finally {
            this.closeOSS(objectStorageService);
        }
    }

    @Override
    public String getPath(FileBucketNameParam param, String fileKey, String tenant) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date();
        long addTime = curDate.getTime() / 1000L + 86400L;
        curDate.setTime(addTime * 1000L);
        String bucketName = "JTABLEAREA";
        if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
            bucketName = this.fileBucketNameProvider.getBucketName(param);
        }
        return String.format("%s/%s/%s/%s", tenant, bucketName, fileKey, df.format(curDate)).replace("//", SEPARATOR);
    }

    @Override
    public List<String> existFile(FileBucketNameParam param, List<String> fileKeys) {
        return this.existFile(param, fileKeys, 0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> existFile(FileBucketNameParam param, List<String> fileKeys, int checkMode) {
        ArrayList<String> noExistFileKeys = new ArrayList<String>();
        if (null == fileKeys || fileKeys.isEmpty()) {
            return noExistFileKeys;
        }
        ObjectStorageService objectStorageService = null;
        try {
            String bucketName = "JTABLEAREA";
            if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                bucketName = this.fileBucketNameProvider.getBucketName(param);
            }
            objectStorageService = FileOperationUtils.getObjService(bucketName);
            for (String fileKey : fileKeys) {
                boolean rs = objectStorageService.existObject(fileKey, checkMode);
                if (rs) continue;
                noExistFileKeys.add(fileKey);
            }
            ArrayList<String> arrayList = noExistFileKeys;
            this.closeOSS(objectStorageService);
            return arrayList;
        }
        catch (ObjectStorageException e) {
            try {
                logger.error(e.getMessage(), e);
                this.closeOSS(objectStorageService);
            }
            catch (Throwable throwable) {
                this.closeOSS(objectStorageService);
                throw throwable;
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private FileInfo uploadFile(FileBucketNameParam param, String fileName, String fileKey, InputStream file, Map<String, String> expandInfo) {
        String extension = FileInfoBuilder.tryParseExtension(fileName);
        fileKey = this.ossUpload(param, fileKey, fileName, extension, file, expandInfo);
        ObjectStorageService objectStorageService = null;
        try {
            ObjectInfo objectInfo;
            String bucketName = "JTABLEAREA";
            if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                bucketName = this.fileBucketNameProvider.getBucketName(param);
            }
            if ((objectInfo = (objectStorageService = FileOperationUtils.getObjService(bucketName)).getObjectInfo(fileKey)) != null) {
                FileInfo fileInfo = FileInfoBuilder.newFileInfo(fileKey, bucketName, fileName, extension, objectInfo.getSize(), FileStatus.AVAILABLE, FileInfoBuilder.resolveCurrentUserName(), new Date(), null, null, 0);
                fileInfo.setMd5(objectInfo.getMd5());
                fileInfo.setFileGroupKey((String)objectInfo.getExtProp().get("fileGroupKey"));
                fileInfo.setSecretlevel((String)objectInfo.getExtProp().get("secretlevel"));
                fileInfo.setFilepoolKey((String)objectInfo.getExtProp().get("filePool"));
                fileInfo.setCategory((String)objectInfo.getExtProp().get("category"));
                FileInfo fileInfo2 = fileInfo;
                this.closeOSS(objectStorageService);
                return fileInfo2;
            }
            this.closeOSS(objectStorageService);
        }
        catch (Exception e) {
            try {
                logger.error("\u9644\u4ef6\u4e0a\u4f20\u5931\u8d25\uff0c\u9644\u4ef6\u540d\u79f0\uff1a" + fileName + "\uff0c\u9644\u4ef6key\uff1a" + fileKey + ",\u9519\u8bef\u4fe1\u606f\uff1a" + e.getMessage(), e);
                this.ossDeleteIfExist(param, fileKey);
                this.closeOSS(objectStorageService);
            }
            catch (Throwable throwable) {
                this.closeOSS(objectStorageService);
                throw throwable;
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String ossUpload(FileBucketNameParam param, String fileKey, String fileName, String extension, InputStream file, Map<String, String> expandInfo) {
        String string;
        ObjectStorageService objectStorageService;
        block14: {
            objectStorageService = null;
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
                String bucketName = "JTABLEAREA";
                if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                    bucketName = this.fileBucketNameProvider.getBucketName(param);
                }
                objectStorageService = FileOperationUtils.getObjService(bucketName);
                if (null != this.fileUploadCheckService && this.fileUploadCheckService.fileUploadCheck()) {
                    objectStorageService.upload(fileKey, file, info, ObjectUploadMode.OVERWRITE_BROKEN);
                } else {
                    objectStorageService.upload(fileKey, file, info);
                }
                string = fileKey;
                if (null == file) break block14;
            }
            catch (Exception e) {
                try {
                    throw new FileException("faild to save file. fileKey:" + fileKey, e);
                }
                catch (Throwable throwable) {
                    if (null != file) {
                        try {
                            file.close();
                        }
                        catch (IOException e2) {
                            logger.error(e2.getMessage(), e2);
                        }
                    }
                    this.closeOSS(objectStorageService);
                    throw throwable;
                }
            }
            try {
                file.close();
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        this.closeOSS(objectStorageService);
        return string;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void ossDeleteIfExist(FileBucketNameParam param, String fileKey) {
        ObjectStorageService objService = null;
        try {
            String bucketName = "JTABLEAREA";
            if (null != param && StringUtils.isNotEmpty((String)param.getDataSchemeKey()) && null != this.fileBucketNameProvider) {
                bucketName = this.fileBucketNameProvider.getBucketName(param);
            }
            objService = FileOperationUtils.getObjService(bucketName);
            objService.deleteObject(fileKey);
            this.closeOSS(objService);
        }
        catch (ObjectStorageException e) {
            try {
                logger.error("\u9644\u4ef6\u5220\u9664\u5931\u8d25\uff0c\u9644\u4ef6key\uff1a" + fileKey + ERROR_TEXT + e.getMessage(), e);
                this.closeOSS(objService);
            }
            catch (Throwable throwable) {
                this.closeOSS(objService);
                throw throwable;
            }
        }
    }

    private void closeOSS(ObjectStorageService objectStorageService) {
        if (null != objectStorageService) {
            try {
                objectStorageService.close();
            }
            catch (ObjectStorageException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}

