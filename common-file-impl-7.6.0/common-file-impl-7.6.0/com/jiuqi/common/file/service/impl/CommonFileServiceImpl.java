/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.file.dto.CommonFileDTO
 *  com.jiuqi.nvwa.oss.controller.ObjectController
 *  com.jiuqi.nvwa.oss.service.IObjectService
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.context.request.RequestAttributes
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.common.file.service.impl;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.file.dto.CommonFileDTO;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.nvwa.oss.controller.ObjectController;
import com.jiuqi.nvwa.oss.service.IObjectService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CommonFileServiceImpl
implements CommonFileService {
    @Autowired
    private ObjectController objectController;
    @Autowired
    private IObjectService objectService;

    @Override
    public ObjectInfo uploadFileToOss(MultipartFile file) {
        return this.uploadFileToOss("COMMON_FILE_OSS", file);
    }

    @Override
    public ObjectInfo uploadFileToOss(MultipartFile file, String fileKey) {
        return this.uploadFileToOss("COMMON_FILE_OSS", file, fileKey);
    }

    @Override
    public void downloadOssFile(HttpServletRequest request, HttpServletResponse response, String fileKey) {
        this.downloadOssFile("COMMON_FILE_OSS", request, response, fileKey);
    }

    @Override
    public ObjectInfo getObjectInfo(String fileKey) {
        return this.getObjectInfo("COMMON_FILE_OSS", fileKey);
    }

    @Override
    public void downloadOssZipFile(HttpServletRequest request, HttpServletResponse response, List<String> fileKeys, String zipName) {
        this.downloadOssZipFile("COMMON_FILE_OSS", request, response, fileKeys, zipName);
    }

    @Override
    public CommonFileDTO queryOssFileByFileKey(String fileKey) {
        return this.queryOssFileByFileKey("COMMON_FILE_OSS", fileKey);
    }

    @Override
    public void deleteOssFile(String fileKey) {
        this.deleteOssFile("COMMON_FILE_OSS", fileKey);
    }

    @Override
    public ObjectInfo uploadFileToOss(String bucket, MultipartFile file) {
        ObjectInfo objectInfo = this.uploadFileToOss(bucket, file, UUIDUtils.newUUIDStr());
        return objectInfo;
    }

    @Override
    public ObjectInfo uploadFileToOss(String bucket, MultipartFile file, String fileKey) {
        Objects.requireNonNull(file, "\u9644\u4ef6\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fileKey, "\u9644\u4ef6ID\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        try {
            ObjectInfo objectInfo = this.objectService.getObjectInfo(bucket, fileKey);
            if (objectInfo == null) {
                objectInfo = this.objectController.upload(file, bucket, fileKey);
            }
            return objectInfo;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public void downloadOssFile(String bucket, HttpServletRequest request, HttpServletResponse response, String fileKey) {
        ServletRequestAttributes attributes = new ServletRequestAttributes(request, response);
        RequestContextHolder.setRequestAttributes((RequestAttributes)attributes);
        try {
            this.objectController.download(bucket, fileKey, false);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public ObjectInfo getObjectInfo(String bucket, String fileKey) {
        try {
            ObjectInfo objectInfo = this.objectService.getObjectInfo(bucket, fileKey);
            return objectInfo;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public void downloadOssZipFile(String bucket, HttpServletRequest request, HttpServletResponse response, List<String> fileKeys, String zipName) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(zipName.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
            response.setCharacterEncoding("UTF-8");
            ZipOutputStream zos = new ZipOutputStream((OutputStream)outputStream, Charset.forName("GBK"));
            byte[] buf = new byte[1024];
            fileKeys.stream().forEach(fileKey -> {
                ObjectInfo objectInfo = this.getObjectInfo(bucket, (String)fileKey);
                CommonFileDTO file = this.queryOssFileByFileKey(bucket, (String)fileKey);
                InputStream inputStream = null;
                try {
                    int len;
                    inputStream = file.getInputStream();
                    ZipEntry zipEntry = new ZipEntry(objectInfo.getName());
                    zos.putNextEntry(zipEntry);
                    while ((len = inputStream.read(buf)) != -1) {
                        zos.write(buf, 0, len);
                    }
                }
                catch (IOException e) {
                    throw new BusinessRuntimeException((Throwable)e);
                }
                finally {
                    try {
                        zos.closeEntry();
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                    catch (IOException e) {
                        throw new BusinessRuntimeException((Throwable)e);
                    }
                }
            });
            zos.closeEntry();
            if (zos != null) {
                zos.flush();
                zos.close();
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public CommonFileDTO queryOssFileByFileKey(String bucket, String fileKey) {
        try (InputStream inputStream = this.objectService.download(bucket, fileKey);){
            CommonFileDTO multipartFile;
            int length;
            if (inputStream == null) {
                CommonFileDTO commonFileDTO = null;
                return commonFileDTO;
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            byte[] fileContent = outputStream.toByteArray();
            CommonFileDTO commonFileDTO = multipartFile = new CommonFileDTO("multipartFile", UUID.randomUUID().toString(), "multipart/form-data; charset=ISO-8859-1", fileContent);
            return commonFileDTO;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public void deleteOssFile(String bucket, String fileKey) {
        try {
            this.objectController.delete(bucket, fileKey);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }
}

