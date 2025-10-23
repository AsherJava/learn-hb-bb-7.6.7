/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageService
 */
package com.jiuqi.nr.file.impl;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.nr.file.FileAreaConfig;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.exception.FileException;
import com.jiuqi.nr.file.exception.FileNotFoundException;
import com.jiuqi.nr.file.exception.FileSizeOutOfLimitException;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.file.impl.FileInfoImpl;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.file.utils.FileUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileAreaServiceImpl
implements FileAreaService {
    private static final Logger logger = LoggerFactory.getLogger(FileAreaServiceImpl.class);
    private static final String SEPARATOR = "/";
    private final FileInfoService fileInfoService;
    private final FileAreaConfig areaConfig;

    public FileAreaServiceImpl(FileInfoService fileInfoService, FileAreaConfig areaConfig) {
        this.fileInfoService = fileInfoService;
        this.areaConfig = areaConfig;
    }

    @Override
    public FileAreaConfig getAreaConfig() {
        return this.areaConfig;
    }

    public String getAreaName() {
        return this.areaConfig.getName();
    }

    private byte[] readFile(InputStream fileStream) {
        if (fileStream == null) {
            throw new IllegalArgumentException("'fileStream' must not be null.");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 1024;
        byte[] tmp = new byte[len];
        try {
            int i;
            while ((i = fileStream.read(tmp, 0, len)) > 0) {
                baos.write(tmp, 0, i);
            }
        }
        catch (IOException e) {
            throw new FileException("faild to read input file.", e);
        }
        return baos.toByteArray();
    }

    @Override
    public FileInfo upload(InputStream fileContent) throws IOException {
        return this.upload(null, fileContent);
    }

    @Override
    public FileInfo upload(byte[] fileContent) {
        return this.upload(null, fileContent);
    }

    @Override
    public FileInfo upload(String fileName, InputStream fileContent) throws IOException {
        return this.upload(fileName, FileInfoBuilder.tryParseExtension(fileName), fileContent);
    }

    @Override
    public FileInfo uploadTemp(String fileName, InputStream fileContent) {
        return this.uploadTemp(fileName, FileInfoBuilder.tryParseExtension(fileName), fileContent);
    }

    @Override
    public FileInfo upload(String fileName, byte[] fileContent) {
        return this.upload(fileName, FileInfoBuilder.tryParseExtension(fileName), fileContent);
    }

    @Override
    public FileInfo upload(String fileName, String extension, InputStream fileContent) throws IOException {
        return this.upload(fileName, extension, fileContent, null);
    }

    @Override
    public FileInfo upload(String fileName, String extension, byte[] fileContent) {
        String fileKey = this.uploadBase(fileName, extension, fileContent);
        try {
            ObjectInfo objectInfo = FileUtils.objService(this.areaConfig.getName()).getObjectInfo(fileKey);
            if (objectInfo != null) {
                FileInfoImpl fileInfo = (FileInfoImpl)FileInfoBuilder.newFileInfo(fileKey, this.areaConfig.getName(), fileName, extension, objectInfo.getSize(), FileStatus.AVAILABLE, FileInfoBuilder.resolveCurrentUserName(), new Date(), FileInfoBuilder.resolveCurrentUserName(), new Date(), 0, (String)objectInfo.getExtProp().get(FileUtils.GROUPKEY));
                fileInfo.setSecretlevel((String)objectInfo.getExtProp().get(FileUtils.SECRETLEVEL));
                return fileInfo;
            }
            return null;
        }
        catch (Exception e) {
            this.ossDeleteIfExist(fileKey);
            return null;
        }
    }

    public FileInfo uploadTemp(String fileName, String extension, InputStream fileContent) {
        String fileKey = this.ossUploadTemp(fileName, extension, fileContent, null, null);
        try {
            ObjectInfo objectInfo = FileUtils.objServiceTemp(this.areaConfig.getName()).getObjectInfo(fileKey);
            if (objectInfo != null) {
                FileInfoImpl fileInfo = (FileInfoImpl)FileInfoBuilder.newFileInfo(fileKey, this.areaConfig.getName(), fileName, extension, objectInfo.getSize(), FileStatus.AVAILABLE, FileInfoBuilder.resolveCurrentUserName(), new Date(), FileInfoBuilder.resolveCurrentUserName(), new Date(), 0, (String)objectInfo.getExtProp().get(FileUtils.GROUPKEY));
                fileInfo.setSecretlevel((String)objectInfo.getExtProp().get(FileUtils.SECRETLEVEL));
                return fileInfo;
            }
            return null;
        }
        catch (Exception e) {
            this.ossDeleteIfExist(fileKey);
            return null;
        }
    }

    public FileInfo upload(String fileName, String extension, byte[] fileContent, String group) {
        String fileKey = FileInfoBuilder.generateFileKey(extension);
        fileKey = this.ossUpload(fileName, extension, fileContent, group, fileKey);
        try {
            ObjectInfo objectInfo = FileUtils.objService(this.areaConfig.getName()).getObjectInfo(fileKey);
            if (objectInfo != null) {
                FileInfoImpl fileInfo = (FileInfoImpl)FileInfoBuilder.newFileInfo(fileKey, this.areaConfig.getName(), fileName, extension, objectInfo.getSize(), FileStatus.AVAILABLE, FileInfoBuilder.resolveCurrentUserName(), new Date(), FileInfoBuilder.resolveCurrentUserName(), new Date(), 0, group);
                fileInfo.setSecretlevel((String)objectInfo.getExtProp().get(FileUtils.SECRETLEVEL));
                return fileInfo;
            }
        }
        catch (Exception e) {
            this.ossDeleteIfExist(fileKey);
        }
        return null;
    }

    private void ossDeleteIfExist(String fileKey) {
        try {
            ObjectStorageService objService = FileUtils.objService(this.areaConfig.getName());
            boolean b = objService.existObject(fileKey);
            if (b) {
                objService.deleteObject(fileKey);
            }
        }
        catch (ObjectStorageException e1) {
            logger.error(e1.getMessage(), e1);
        }
    }

    private String ossUpload(String fileName, String extension, byte[] fileContent, String group, String fileKey) {
        String uploadKey = fileKey;
        try (ByteArrayInputStream input = new ByteArrayInputStream(fileContent);){
            String currentUser = FileInfoBuilder.resolveCurrentUserName();
            String newKey = FileUtils.generateFileKey();
            ObjectInfo info = new ObjectInfo();
            info.setExtension(extension);
            info.setKey(newKey);
            info.setName(fileName);
            info.setSize((long)fileContent.length);
            info.setOwner(currentUser);
            if (group != null) {
                info.getExtProp().put(FileUtils.GROUPKEY, group);
            }
            FileUtils.objService(this.areaConfig.getName()).upload(newKey, (InputStream)input, info);
            uploadKey = newKey;
        }
        catch (Exception e) {
            throw new FileException("faild to save file.", e);
        }
        return uploadKey;
    }

    private void ossUploadByKey(String fileName, String extension, byte[] fileContent, String group, String fileKey, String level) {
        try (ByteArrayInputStream input = new ByteArrayInputStream(fileContent);){
            String currentUser = FileInfoBuilder.resolveCurrentUserName();
            ObjectInfo info = new ObjectInfo();
            info.setExtension(extension);
            info.setKey(fileKey);
            info.setName(fileName);
            info.setSize((long)fileContent.length);
            info.setOwner(currentUser);
            if (group != null) {
                info.getExtProp().put(FileUtils.GROUPKEY, group);
            }
            if (level != null) {
                info.getExtProp().put(FileUtils.SECRETLEVEL, level);
            }
            FileUtils.objService(this.areaConfig.getName()).upload(fileKey, (InputStream)input, info);
        }
        catch (Exception e) {
            throw new FileException("faild to save file.", e);
        }
    }

    private String ossUpload(String fileName, String extension, InputStream input, String group, String fileKey) {
        ObjectStorageService objectStorageService = null;
        try {
            String currentUser = FileInfoBuilder.resolveCurrentUserName();
            String newKey = FileUtils.generateFileKey();
            ObjectInfo info = new ObjectInfo();
            info.setExtension(extension);
            info.setKey(newKey);
            info.setName(fileName);
            info.setOwner(currentUser);
            if (group != null) {
                info.getExtProp().put(FileUtils.GROUPKEY, group);
            }
            objectStorageService = FileUtils.objService(this.areaConfig.getName());
            objectStorageService.upload(newKey, input, info);
            fileKey = newKey;
        }
        catch (Exception e) {
            throw new FileException("faild to save file.", e);
        }
        finally {
            if (null != objectStorageService) {
                try {
                    objectStorageService.close();
                }
                catch (ObjectStorageException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return fileKey;
    }

    private String ossUploadTemp(String fileName, String extension, InputStream input, String group, String fileKey) {
        ObjectStorageService objectStorageService = null;
        try {
            String currentUser = FileInfoBuilder.resolveCurrentUserName();
            String newKey = FileUtils.generateFileKey();
            ObjectInfo info = new ObjectInfo();
            info.setExtension(extension);
            info.setKey(newKey);
            info.setName(fileName);
            info.setOwner(currentUser);
            if (group != null) {
                info.getExtProp().put(FileUtils.GROUPKEY, group);
            }
            objectStorageService = FileUtils.objServiceTemp(this.areaConfig.getName());
            objectStorageService.upload(newKey, input, info);
            String string = fileKey = newKey;
            return string;
        }
        catch (Exception e) {
            throw new FileException("faild to save file.", e);
        }
        finally {
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

    private String uploadBase(String fileName, String extension, byte[] fileContent) {
        if (fileContent.length == 0) {
            throw new IllegalArgumentException("file can not be empty.");
        }
        if ((long)fileContent.length > this.areaConfig.getMaxFileSize()) {
            throw new FileSizeOutOfLimitException(this.areaConfig.getMaxFileSize());
        }
        String fileKey = FileInfoBuilder.generateFileKey(extension);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileContent);
        try {
            String currentUser = FileInfoBuilder.resolveCurrentUserName();
            String newKey = FileUtils.generateFileKey(extension);
            ObjectInfo info = new ObjectInfo();
            info.setExtension(extension);
            info.setKey(newKey);
            info.setName(fileName);
            info.setSize((long)fileContent.length);
            info.setOwner(currentUser);
            FileUtils.objService(this.areaConfig.getName()).upload(newKey, (InputStream)byteArrayInputStream, info);
            String string = fileKey = newKey;
            return string;
        }
        catch (Exception e) {
            throw new FileException("faild to save file.", e);
        }
        finally {
            try {
                byteArrayInputStream.close();
            }
            catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    @Deprecated
    public FileInfo rename(String fileKey, String newName) {
        return this.rename(fileKey, newName, FileInfoBuilder.tryParseExtension(newName));
    }

    @Override
    @Deprecated
    public FileInfo rename(String fileKey, String newName, String newExtension) {
        return null;
    }

    @Override
    public FileInfo delete(String fileKey) {
        return this.delete(fileKey, true);
    }

    @Override
    public FileInfo delete(String fileKey, Boolean recoverable) {
        ObjectInfo objectInfo = null;
        try {
            objectInfo = FileUtils.objService(this.areaConfig.getName()).getObjectInfo(fileKey);
        }
        catch (ObjectStorageException e) {
            logger.error(e.getMessage(), e);
        }
        FileInfoImpl fileInfo = null;
        if (objectInfo != null) {
            String group = (String)objectInfo.getExtProp().get(FileUtils.GROUPKEY);
            Date date = FileUtils.getCreateDate(objectInfo);
            fileInfo = (FileInfoImpl)FileInfoBuilder.newFileInfo(fileKey, this.areaConfig.getName(), objectInfo.getName(), objectInfo.getExtension(), objectInfo.getSize(), FileStatus.AVAILABLE, FileInfoBuilder.resolveCurrentUserName(), date, FileInfoBuilder.resolveCurrentUserName(), date, 0, group);
            fileInfo.setSecretlevel((String)objectInfo.getExtProp().get(FileUtils.SECRETLEVEL));
        }
        this.ossDeleteIfExist(fileKey);
        if (fileInfo == null) {
            throw new FileNotFoundException(fileKey);
        }
        return fileInfo;
    }

    @Override
    @Deprecated
    public FileInfo recover(String fileKey) {
        return null;
    }

    @Override
    public String getPath(String fileKey, String tenant) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date();
        long addTime = curDate.getTime() / 1000L + this.areaConfig.getExpirationTime();
        curDate.setTime(addTime * 1000L);
        return String.format("%s/%s/%s/%s", tenant, this.areaConfig.getName(), fileKey, df.format(curDate)).replace("//", SEPARATOR);
    }

    /*
     * Exception decompiling
     */
    @Override
    public byte[] download(String fileKey) {
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
    public void download(String fileKey, OutputStream outputStream) {
        boolean b = false;
        try {
            b = FileUtils.objService(this.areaConfig.getName()).existObject(fileKey);
        }
        catch (ObjectStorageException e1) {
            logger.error(e1.getMessage(), e1);
        }
        if (!b) {
            throw new FileNotFoundException(fileKey);
        }
        InputStream download = null;
        try {
            download = FileUtils.objService(this.areaConfig.getName()).download(fileKey);
            if (download != null) {
                FileUtils.writeInput2Output(outputStream, download);
            }
        }
        catch (Exception e) {
            throw new FileException("failed to down load file.", e);
        }
        finally {
            if (download != null) {
                try {
                    download.close();
                }
                catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    @Override
    public FileInfo getInfo(String fileKey) {
        ObjectInfo objectInfo = null;
        try {
            objectInfo = FileUtils.objService(this.areaConfig.getName()).getObjectInfo(fileKey);
        }
        catch (ObjectStorageException e) {
            logger.error(e.getMessage(), e);
        }
        FileInfoImpl fileInfo = null;
        if (objectInfo != null) {
            Date date = FileUtils.getCreateDate(objectInfo);
            String group = (String)objectInfo.getExtProp().get(FileUtils.GROUPKEY);
            fileInfo = (FileInfoImpl)FileInfoBuilder.newFileInfo(fileKey, this.areaConfig.getName(), objectInfo.getName(), objectInfo.getExtension(), objectInfo.getSize(), FileStatus.AVAILABLE, objectInfo.getOwner(), date, objectInfo.getOwner(), date, 0, group);
            fileInfo.setSecretlevel((String)objectInfo.getExtProp().get(FileUtils.SECRETLEVEL));
        }
        return fileInfo;
    }

    @Override
    public Map<String, FileInfo> getInfo(Collection<String> fileKeys) {
        LinkedHashMap<String, FileInfo> fileInfoMap = new LinkedHashMap<String, FileInfo>();
        for (String fk : fileKeys) {
            fileInfoMap.put(fk, null);
            try {
                ObjectInfo objectInfo = FileUtils.objService(this.areaConfig.getName()).getObjectInfo(fk);
                FileInfoImpl fileInfo = null;
                if (objectInfo != null) {
                    Date date = FileUtils.getCreateDate(objectInfo);
                    String group = (String)objectInfo.getExtProp().get(FileUtils.GROUPKEY);
                    fileInfo = (FileInfoImpl)FileInfoBuilder.newFileInfo(fk, this.areaConfig.getName(), objectInfo.getName(), objectInfo.getExtension(), objectInfo.getSize(), FileStatus.AVAILABLE, objectInfo.getOwner(), date, objectInfo.getOwner(), date, 0, group);
                    fileInfo.setSecretlevel((String)objectInfo.getExtProp().get(FileUtils.SECRETLEVEL));
                }
                fileInfoMap.put(fk, fileInfo);
            }
            catch (ObjectStorageException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return fileInfoMap;
    }

    @Override
    public FileInfo uploadByGroup(String fileName, String groupKey, byte[] fileContent) {
        FileInfo fileInfo = this.upload(fileName, FileInfoBuilder.tryParseExtension(fileName), fileContent, groupKey);
        return fileInfo;
    }

    @Override
    public FileInfo uploadByGroup(String fileName, String groupKey, byte[] fileContent, String directory) {
        FileInfo fileInfo = this.upload(fileName, FileInfoBuilder.tryParseExtension(fileName), fileContent, groupKey, directory);
        return fileInfo;
    }

    @Override
    public FileInfo uploadByGroupLevel(String fileName, String groupKey, String level, byte[] fileContent) {
        String fileKey = FileInfoBuilder.generateFileKey("");
        FileInfo fileInfo = this.uploadByKeyLevle(fileName, fileKey, groupKey, fileContent, level);
        return fileInfo;
    }

    public FileInfo upload(String fileName, String extension, byte[] fileContent, String group, String directory) {
        String fileKey = FileInfoBuilder.generateFileKey(extension);
        fileKey = this.ossUpload(fileName, extension, fileContent, group, fileKey);
        try {
            ObjectInfo objectInfo = FileUtils.objService(this.areaConfig.getName()).getObjectInfo(fileKey);
            if (objectInfo != null) {
                FileInfoImpl fileInfo = (FileInfoImpl)FileInfoBuilder.newFileInfo(fileKey, this.areaConfig.getName(), fileName, extension, objectInfo.getSize(), FileStatus.AVAILABLE, FileInfoBuilder.resolveCurrentUserName(), new Date(), FileInfoBuilder.resolveCurrentUserName(), new Date(), 0, group);
                fileInfo.setSecretlevel((String)objectInfo.getExtProp().get(FileUtils.SECRETLEVEL));
                return fileInfo;
            }
        }
        catch (Exception e) {
            this.ossDeleteIfExist(fileKey);
        }
        return null;
    }

    @Override
    public FileInfo uploadByKey(String fileName, String fileKey, String groupKey, byte[] fileContent) {
        FileInfo fileInfo = this.uploadKey(fileName, fileKey, FileInfoBuilder.tryParseExtension(fileName), fileContent, groupKey, null);
        return fileInfo;
    }

    @Override
    public FileInfo uploadByKeyLevle(String fileName, String fileKey, String groupKey, byte[] fileContent, String level) {
        FileInfo fileInfo = this.uploadKey(fileName, fileKey, FileInfoBuilder.tryParseExtension(fileName), fileContent, groupKey, level);
        return fileInfo;
    }

    public FileInfo uploadKey(String fileName, String fileKey, String extension, byte[] fileContent, String group, String level) {
        block3: {
            this.ossUploadByKey(fileName, extension, fileContent, group, fileKey, level);
            try {
                ObjectInfo objectInfo = FileUtils.objService(this.areaConfig.getName()).getObjectInfo(fileKey);
                if (objectInfo != null) {
                    FileInfoImpl fileInfo = (FileInfoImpl)FileInfoBuilder.newFileInfo(fileKey, this.areaConfig.getName(), fileName, extension, objectInfo.getSize(), FileStatus.AVAILABLE, FileInfoBuilder.resolveCurrentUserName(), new Date(), FileInfoBuilder.resolveCurrentUserName(), new Date(), 0, group);
                    fileInfo.setSecretlevel((String)objectInfo.getExtProp().get(FileUtils.SECRETLEVEL));
                    return fileInfo;
                }
            }
            catch (Exception e) {
                this.ossDeleteIfExist(fileKey);
                if (!e.getMessage().contains("ORA-00001")) break block3;
                throw new IllegalArgumentException("fileKey error. The primary key repeat\uff01");
            }
        }
        return null;
    }

    @Override
    public FileInfo uploadByGroup(String fileName, String groupKey, InputStream fileContent) throws IOException {
        FileInfo fileInfo = this.upload(fileName, FileInfoBuilder.tryParseExtension(fileName), fileContent, groupKey);
        return fileInfo;
    }

    public FileInfo upload(String fileName, String extension, InputStream fileContent, String group) throws IOException {
        if (fileContent == null) {
            throw new IllegalArgumentException("fileContent \u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        String fileKey = this.ossUpload(fileName, extension, fileContent, group, null);
        try {
            ObjectInfo objectInfo = FileUtils.objService(this.areaConfig.getName()).getObjectInfo(fileKey);
            if (objectInfo != null) {
                FileInfoImpl fileInfo = (FileInfoImpl)FileInfoBuilder.newFileInfo(fileKey, this.areaConfig.getName(), fileName, extension, objectInfo.getSize(), FileStatus.AVAILABLE, FileInfoBuilder.resolveCurrentUserName(), new Date(), FileInfoBuilder.resolveCurrentUserName(), new Date(), 0, group);
                fileInfo.setSecretlevel((String)objectInfo.getExtProp().get(FileUtils.SECRETLEVEL));
                return fileInfo;
            }
        }
        catch (Exception e) {
            this.ossDeleteIfExist(fileKey);
        }
        return null;
    }

    @Override
    public void copyFileGroup(String area, String fromGroupKey, String toGroupKey) {
        List<FileInfo> fileInfos = this.fileInfoService.getFileInfoByGroup(fromGroupKey, area, FileStatus.AVAILABLE);
        ArrayList<FileInfo> fileInfoOfUpload = new ArrayList<FileInfo>();
        for (FileInfo fileInfo : fileInfos) {
            FileInfo fileInfoOfToGroup = FileInfoBuilder.newFileInfo(UUID.randomUUID().toString(), fileInfo.getArea(), fileInfo.getName(), fileInfo.getExtension(), fileInfo.getSize(), FileStatus.AVAILABLE, fileInfo.getCreater(), fileInfo.getCreateTime(), fileInfo.getLastModifier(), fileInfo.getLastModifyTime(), fileInfo.getVersion(), toGroupKey);
            this.fileInfoService.getFileInfoDao(fileInfo.getArea()).insert(fileInfoOfToGroup);
            FileInfo fileInfoUplod = this.uploadByGroup(fileInfoOfToGroup.getName(), toGroupKey, this.download(fileInfo.getKey()));
            fileInfoOfUpload.add(fileInfoUplod);
        }
    }

    @Override
    public void updateLevel(String fileKey, String newLevel) throws ObjectStorageException {
        FileUtils.objService(this.areaConfig.getName()).modifyObjectProp(fileKey, FileUtils.SECRETLEVEL, newLevel);
    }
}

