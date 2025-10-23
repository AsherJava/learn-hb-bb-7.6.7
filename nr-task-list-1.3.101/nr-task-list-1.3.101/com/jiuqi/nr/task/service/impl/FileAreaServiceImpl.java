/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.nr.task.api.exception.FileAreaException
 *  com.jiuqi.nr.task.api.file.IFileAreaService
 *  com.jiuqi.nr.task.api.file.dto.FileAreaDTO
 *  com.jiuqi.nr.task.api.file.dto.FileInfoDTO
 *  com.jiuqi.nr.task.api.util.FileAreaUtil
 */
package com.jiuqi.nr.task.service.impl;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.nr.task.api.exception.FileAreaException;
import com.jiuqi.nr.task.api.file.IFileAreaService;
import com.jiuqi.nr.task.api.file.dto.FileAreaDTO;
import com.jiuqi.nr.task.api.file.dto.FileInfoDTO;
import com.jiuqi.nr.task.api.util.FileAreaUtil;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.stereotype.Service;

@Service
public class FileAreaServiceImpl
implements IFileAreaService {
    public FileInfoDTO fileUpload(String fileName, InputStream input, FileAreaDTO fileAreaDTO) {
        ObjectInfo objectInfo = FileAreaUtil.upload((String)fileName, (InputStream)input, (String)"", (FileAreaDTO)fileAreaDTO);
        return this.Obj2DTO(objectInfo);
    }

    public FileInfoDTO fileUploadByKey(String fileName, InputStream input, String fileKey, FileAreaDTO fileAreaDTO) {
        ObjectInfo objectInfo = FileAreaUtil.upload((String)fileName, (InputStream)input, (String)fileKey, (FileAreaDTO)fileAreaDTO);
        return this.Obj2DTO(objectInfo);
    }

    public FileInfoDTO getFile(String file, FileAreaDTO fileAreaDTO) {
        ObjectInfo objectInfo;
        ObjectStorageService objService = null;
        try {
            objService = this.getObjService(fileAreaDTO);
            objectInfo = objService.getObjectInfo(file);
        }
        catch (ObjectStorageException e) {
            throw new FileAreaException(" get file failed. " + e.getMessage(), (Throwable)e);
        }
        finally {
            FileAreaUtil.closeObjService((ObjectStorageService)objService);
        }
        return this.Obj2DTO(objectInfo);
    }

    public void updateFileName(String fileKey, String newName, FileAreaDTO fileAreaDTO) {
        ObjectStorageService objService = null;
        try {
            objService = this.getObjService(fileAreaDTO);
            objService.modifyObjectProp(fileKey, "name", newName);
        }
        catch (ObjectStorageException e) {
            throw new FileAreaException(" update file failed. " + e.getMessage(), (Throwable)e);
        }
        finally {
            FileAreaUtil.closeObjService((ObjectStorageService)objService);
        }
    }

    public boolean existFile(String fileKey, FileAreaDTO fileAreaDTO) {
        boolean fileExist;
        ObjectStorageService objService = null;
        try {
            objService = this.getObjService(fileAreaDTO);
            fileExist = objService.existObject(fileKey);
        }
        catch (ObjectStorageException e) {
            throw new FileAreaException(" determine file failed. " + e.getMessage(), (Throwable)e);
        }
        finally {
            FileAreaUtil.closeObjService((ObjectStorageService)objService);
        }
        return fileExist;
    }

    public void deleteFile(String fileKey, FileAreaDTO fileAreaDTO) {
        ObjectStorageService objService = null;
        try {
            objService = this.getObjService(fileAreaDTO);
            boolean fileExist = objService.existObject(fileKey);
            if (fileExist) {
                objService.deleteObject(fileKey);
            }
        }
        catch (ObjectStorageException e) {
            throw new FileAreaException(" delete file failed. " + e.getMessage(), (Throwable)e);
        }
        finally {
            FileAreaUtil.closeObjService((ObjectStorageService)objService);
        }
    }

    public byte[] download(String fileKey, FileAreaDTO fileAreaDTO) {
        return FileAreaUtil.fileDownLoad((String)fileKey, null, (FileAreaDTO)fileAreaDTO);
    }

    public void download(String fileKey, OutputStream outputStream, FileAreaDTO fileAreaDTO) {
        FileAreaUtil.fileDownLoad((String)fileKey, (OutputStream)outputStream, (FileAreaDTO)fileAreaDTO);
    }

    private ObjectStorageService getObjService(FileAreaDTO fileAreaDTO) {
        return FileAreaUtil.getObjService((FileAreaDTO)fileAreaDTO);
    }

    protected FileInfoDTO Obj2DTO(ObjectInfo objectInfo) {
        if (objectInfo == null) {
            return null;
        }
        FileInfoDTO fileInfoDTO = new FileInfoDTO();
        fileInfoDTO.setKey(objectInfo.getKey());
        fileInfoDTO.setName(objectInfo.getName());
        fileInfoDTO.setExtension(objectInfo.getExtension());
        fileInfoDTO.setSize(objectInfo.getSize());
        fileInfoDTO.setOwner(objectInfo.getOwner());
        fileInfoDTO.setCreateTime(objectInfo.getCreateTime());
        return fileInfoDTO;
    }
}

