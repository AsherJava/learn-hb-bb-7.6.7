/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.file.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoImpl;
import java.util.Date;
import java.util.UUID;

public class FileInfoBuilder {
    public static String generateFileKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String generateFileKey(String extension) {
        String key = FileInfoBuilder.generateFileKey();
        return StringUtils.isEmpty((String)extension) ? key : String.format("%s%s", key, extension);
    }

    public static String tryParseExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        int extensionPos = fileName.lastIndexOf(".");
        if (extensionPos <= 0) {
            return null;
        }
        return fileName.substring(extensionPos);
    }

    public static FileInfo newFileInfo(String key, String area, String name, String extension, long size, FileStatus status, String creater, Date createTime, String lastModifier, Date lastModifyTime, int version, String fileGroupKey, String path) {
        FileInfoImpl fileInfo = new FileInfoImpl();
        fileInfo.setKey(StringUtils.isEmpty((String)key) ? FileInfoBuilder.generateFileKey(extension) : key);
        fileInfo.setArea(area);
        fileInfo.setName(StringUtils.isEmpty((String)name) ? fileInfo.getKey() : name);
        fileInfo.setExtension(extension);
        fileInfo.setSize(size);
        fileInfo.setStatus(status);
        fileInfo.setCreater(creater);
        fileInfo.setCreateTime(createTime);
        fileInfo.setLastModifier(lastModifier);
        fileInfo.setLastModifyTime(lastModifyTime);
        fileInfo.setVersion(version);
        fileInfo.setFileGroupKey(fileGroupKey);
        fileInfo.setPath(path);
        return fileInfo;
    }

    public static FileInfo newFileInfo(FileInfo fileInfo, String fileGroupKey, String path) {
        return FileInfoBuilder.newFileInfo(fileInfo.getKey(), fileInfo.getArea(), fileInfo.getName(), fileInfo.getExtension(), fileInfo.getSize(), FileStatus.RECOVERABLE, fileInfo.getCreater(), fileInfo.getCreateTime(), FileInfoBuilder.resolveCurrentUserName(), new Date(), fileInfo.getVersion() + 1, fileGroupKey, path);
    }

    public static FileInfo newFileInfo(String key, String area, String name, String extension, long size, FileStatus status, String creater, Date createTime, String lastModifier, Date lastModifyTime, int version, String groupKey) {
        FileInfoImpl fileInfo = new FileInfoImpl();
        fileInfo.setKey(StringUtils.isEmpty((String)key) ? FileInfoBuilder.generateFileKey(extension) : key);
        fileInfo.setArea(area);
        fileInfo.setName(StringUtils.isEmpty((String)name) ? fileInfo.getKey() : name);
        fileInfo.setExtension(extension);
        fileInfo.setSize(size);
        fileInfo.setStatus(status);
        fileInfo.setCreater(creater);
        fileInfo.setCreateTime(createTime);
        fileInfo.setLastModifier(lastModifier);
        fileInfo.setLastModifyTime(lastModifyTime);
        fileInfo.setVersion(version);
        fileInfo.setFileGroupKey(groupKey);
        return fileInfo;
    }

    public static FileInfo newFileInfo(String key, String area, String name, String extension, long size) {
        String currentUser = FileInfoBuilder.resolveCurrentUserName();
        Date nowDate = new Date();
        return FileInfoBuilder.newFileInfo(key, area, name, extension, size, FileStatus.AVAILABLE, currentUser, nowDate, currentUser, nowDate, 0, null);
    }

    public static FileInfo rename(FileInfo fileInfo, String newName, String newExtension) {
        return FileInfoBuilder.newFileInfo(fileInfo.getKey(), fileInfo.getArea(), newName, newExtension, fileInfo.getSize(), fileInfo.getStatus(), fileInfo.getCreater(), fileInfo.getCreateTime(), FileInfoBuilder.resolveCurrentUserName(), new Date(), fileInfo.getVersion() + 1, fileInfo.getFileGroupKey());
    }

    public static FileInfo markAsRecoverable(FileInfo fileInfo) {
        return FileInfoBuilder.newFileInfo(fileInfo.getKey(), fileInfo.getArea(), fileInfo.getName(), fileInfo.getExtension(), fileInfo.getSize(), FileStatus.RECOVERABLE, fileInfo.getCreater(), fileInfo.getCreateTime(), FileInfoBuilder.resolveCurrentUserName(), new Date(), fileInfo.getVersion() + 1, fileInfo.getFileGroupKey());
    }

    public static FileInfo markAsDelete(FileInfo fileInfo) {
        return FileInfoBuilder.newFileInfo(fileInfo.getKey(), fileInfo.getArea(), fileInfo.getName(), fileInfo.getExtension(), fileInfo.getSize(), FileStatus.DELETED, fileInfo.getCreater(), fileInfo.getCreateTime(), FileInfoBuilder.resolveCurrentUserName(), new Date(), fileInfo.getVersion() + 1, fileInfo.getFileGroupKey());
    }

    public static FileInfo recover(FileInfo fileInfo) {
        return FileInfoBuilder.newFileInfo(fileInfo.getKey(), fileInfo.getArea(), fileInfo.getName(), fileInfo.getExtension(), fileInfo.getSize(), FileStatus.AVAILABLE, fileInfo.getCreater(), fileInfo.getCreateTime(), FileInfoBuilder.resolveCurrentUserName(), new Date(), fileInfo.getVersion() + 1, fileInfo.getFileGroupKey());
    }

    public static String resolveCurrentUserName() {
        ContextUser operator = NpContextHolder.getContext().getUser();
        return operator == null ? null : operator.getName();
    }
}

