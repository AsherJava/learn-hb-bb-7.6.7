/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 */
package com.jiuqi.nr.file.impl;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.IFileInfoDao;
import com.jiuqi.nr.file.exception.FileNotFoundException;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.file.impl.FileInfoImpl;
import com.jiuqi.nr.file.utils.FileUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileInfoService {
    private static final Logger logger = LoggerFactory.getLogger(FileInfoService.class);
    @Autowired
    private Map<String, IFileInfoDao> fileInfoDao;

    IFileInfoDao getFileInfoDao(String area) {
        if (area.equals("DataVer")) {
            return this.fileInfoDao.get("FileInfoMulDataVerDao");
        }
        return this.fileInfoDao.get("fileInfoDao");
    }

    @Deprecated
    public FileInfo createxxxx(String fileKey, String area, String fileName, String extension, long size) {
        FileInfo fileInfo = null;
        try {
            if (FileUtils.objService(area).existObject(fileKey)) {
                ObjectInfo objectInfo = FileUtils.objService(area).getObjectInfo(fileKey);
            }
        }
        catch (ObjectStorageException e) {
            logger.error(e.getMessage(), e);
        }
        fileInfo = FileInfoBuilder.newFileInfo(fileKey, area, fileName, extension, size);
        this.getFileInfoDao(area).insert(fileInfo);
        return fileInfo;
    }

    @Deprecated
    public FileInfo createxxxx(String fileKey, String area, String fileName, String extension, long size, String group) {
        String currentUser = FileInfoBuilder.resolveCurrentUserName();
        Date nowDate = new Date();
        FileInfo fileInfo = FileInfoBuilder.newFileInfo(fileKey, area, fileName, extension, size, FileStatus.AVAILABLE, currentUser, nowDate, currentUser, nowDate, 0, group);
        this.getFileInfoDao(area).insert(fileInfo);
        return fileInfo;
    }

    public FileInfo getFileInfo(String fileKey, String area, FileStatus status) {
        FileInfoImpl fileInfo = null;
        ObjectInfo objectInfo = null;
        try {
            objectInfo = FileUtils.objService(area).getObjectInfo(fileKey);
        }
        catch (ObjectStorageException e) {
            logger.error(e.getMessage(), e);
        }
        if (objectInfo != null) {
            String group = (String)objectInfo.getExtProp().get(FileUtils.GROUPKEY);
            Date date = FileUtils.getCreateDate(objectInfo);
            fileInfo = (FileInfoImpl)FileInfoBuilder.newFileInfo(fileKey, area, objectInfo.getName(), objectInfo.getExtension(), objectInfo.getSize(), FileStatus.AVAILABLE, FileInfoBuilder.resolveCurrentUserName(), date, FileInfoBuilder.resolveCurrentUserName(), date, 0, group);
            fileInfo.setSecretlevel((String)objectInfo.getExtProp().get(FileUtils.SECRETLEVEL));
        }
        return fileInfo;
    }

    public List<FileInfo> getFileInfoByGroup(String groupKey, String area, FileStatus status) {
        ArrayList<FileInfo> fileInfoResult = new ArrayList<FileInfo>();
        try {
            List prop = FileUtils.objService(area).findObjectByProp(FileUtils.GROUPKEY, groupKey, true);
            for (ObjectInfo objectInfo : prop) {
                String group = (String)objectInfo.getExtProp().get(FileUtils.GROUPKEY);
                FileInfoImpl fileInfo = (FileInfoImpl)FileInfoBuilder.newFileInfo(objectInfo.getKey(), area, objectInfo.getName(), objectInfo.getExtension(), objectInfo.getSize(), FileStatus.AVAILABLE, objectInfo.getOwner(), FileUtils.getCreateDate(objectInfo), objectInfo.getOwner(), new Date(), 0, group);
                fileInfo.setSecretlevel((String)objectInfo.getExtProp().get(FileUtils.SECRETLEVEL));
                fileInfoResult.add(fileInfo);
            }
        }
        catch (ObjectStorageException e) {
            logger.error(e.getMessage(), e);
        }
        return fileInfoResult;
    }

    @Deprecated
    public Map<String, FileInfo> batchGetFileInfoxxxx(Collection<String> fileKeys, String area, FileStatus status) {
        LinkedHashMap<String, FileInfo> fileInfoMap = new LinkedHashMap<String, FileInfo>();
        for (String fk : fileKeys) {
            fileInfoMap.put(fk, null);
        }
        Collection<FileInfo> fileInfos = this.getFileInfoDao(area).getFileInfos(fileKeys, area);
        for (FileInfo fi : fileInfos) {
            if (status != null && !fi.getStatus().equals((Object)status)) continue;
            fileInfoMap.put(fi.getKey(), fi);
        }
        return fileInfoMap;
    }

    @Deprecated
    public FileInfo renameFilexxx(FileInfo fileInfo, String newName, String newExtension) {
        FileInfo newFileInfo = FileInfoBuilder.rename(fileInfo, newName, newExtension);
        this.getFileInfoDao(fileInfo.getArea()).update(newFileInfo);
        return newFileInfo;
    }

    public FileInfo deleteFile(FileInfo fileInfo, Boolean recoverable) {
        if (fileInfo == null) {
            throw new FileNotFoundException("\u6587\u4ef6\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        ObjectInfo objectInfo = null;
        try {
            objectInfo = FileUtils.objService(fileInfo.getArea()).getObjectInfo(fileInfo.getKey());
        }
        catch (ObjectStorageException e) {
            logger.error(e.getMessage(), e);
        }
        if (objectInfo != null) {
            String group = (String)objectInfo.getExtProp().get(FileUtils.GROUPKEY);
            Date date = FileUtils.getCreateDate(objectInfo);
            fileInfo = FileInfoBuilder.newFileInfo(fileInfo.getKey(), fileInfo.getArea(), objectInfo.getName(), objectInfo.getExtension(), objectInfo.getSize(), FileStatus.AVAILABLE, FileInfoBuilder.resolveCurrentUserName(), date, FileInfoBuilder.resolveCurrentUserName(), date, 0, group);
            try {
                FileUtils.objService(fileInfo.getArea()).deleteObject(fileInfo.getKey());
            }
            catch (ObjectStorageException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return fileInfo;
    }

    public List<FileInfo> deleteFilesByGroup(String groupKey, String area, Boolean recoverable) {
        ArrayList<FileInfo> fileInfoResult = new ArrayList<FileInfo>();
        try {
            List list = FileUtils.objService(area).findObjectByProp(FileUtils.GROUPKEY, groupKey, true);
            for (ObjectInfo objectInfo : list) {
                String group = (String)objectInfo.getExtProp().get(FileUtils.GROUPKEY);
                Date date = FileUtils.getCreateDate(objectInfo);
                FileInfo fileInfo = FileInfoBuilder.newFileInfo(objectInfo.getKey(), area, objectInfo.getName(), objectInfo.getExtension(), objectInfo.getSize(), FileStatus.AVAILABLE, FileInfoBuilder.resolveCurrentUserName(), date, FileInfoBuilder.resolveCurrentUserName(), date, 0, group);
                fileInfoResult.add(fileInfo);
                FileUtils.objService(area).deleteObject(objectInfo.getKey());
            }
        }
        catch (ObjectStorageException e) {
            logger.error(e.getMessage(), e);
        }
        if (fileInfoResult.isEmpty()) {
            return null;
        }
        return fileInfoResult;
    }

    @Deprecated
    public FileInfo recoverFilexxx(FileInfo fileInfo) {
        FileInfo newFileInfo = FileInfoBuilder.recover(fileInfo);
        this.getFileInfoDao(fileInfo.getArea()).update(newFileInfo);
        return newFileInfo;
    }
}

