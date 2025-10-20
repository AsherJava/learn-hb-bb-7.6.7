/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.nr.attachment.exception.FileException
 *  com.jiuqi.nr.attachment.exception.FileNotFoundException
 *  com.jiuqi.nr.attachment.utils.FileOperationUtils
 *  com.jiuqi.nr.convert.pdf.utils.ConvertUtil
 *  com.jiuqi.nr.convert.pdf.utils.ConvertUtil$FILE_TYPE_TO_PDF
 *  com.jiuqi.nvwa.cms.service.FileService
 *  com.jiuqi.nvwa.oss.service.IObjectService
 *  io.lettuce.core.dynamic.annotation.Param
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.io.IOUtils
 *  org.springframework.http.MediaType
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.aidocaudit.web;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.nr.attachment.exception.FileException;
import com.jiuqi.nr.attachment.exception.FileNotFoundException;
import com.jiuqi.nr.attachment.utils.FileOperationUtils;
import com.jiuqi.nr.convert.pdf.utils.ConvertUtil;
import com.jiuqi.nvwa.cms.service.FileService;
import com.jiuqi.nvwa.oss.service.IObjectService;
import io.lettuce.core.dynamic.annotation.Param;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Optional;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping(value={"/api/gcreport/v1/docaudit/ossfile"})
public class OssFileController {
    private final Logger logger = LoggerFactory.getLogger(OssFileController.class);
    @Autowired
    private IObjectService objectService;
    @Autowired
    private FileService fileService;
    @Value(value="${jiuqi.gcreport.aidocaudit.ossfile.verifycode:d19b05a6-b9d6-4bf9-9dab-64e1f62dd6e3}")
    private String verifyCode;

    @GetMapping(value={"/download"})
    public void download(@Param(value="fileId") String fileId, @Param(value="verifyId") String verifyId, HttpServletRequest request, HttpServletResponse response) {
        Assert.state(!StringUtils.isEmpty((String)verifyId), "\u8bf7\u6c42\u53c2\u6570\u9519\u8bef, verifyId \u4e0d\u80fd\u4e3a\u7a7a");
        Assert.state(!StringUtils.isEmpty((String)fileId), "\u8bf7\u6c42\u53c2\u6570\u9519\u8bef, fileId \u4e0d\u80fd\u4e3a\u7a7a");
        if (!this.verifyCode.equals(verifyId)) {
            throw new BusinessRuntimeException("verifyId\u672a\u901a\u8fc7\u6821\u9a8c");
        }
        boolean b = false;
        ObjectStorageService objectStorageService = null;
        try {
            objectStorageService = FileOperationUtils.objService((String)"JTABLEAREA");
            b = objectStorageService.existObject(fileId);
        }
        catch (ObjectStorageException e) {
            this.logger.error(e.getMessage(), e);
        }
        if (!b) {
            throw new FileNotFoundException(fileId);
        }
        InputStream download = null;
        try {
            download = objectStorageService.download(fileId);
            ObjectInfo objectInfo = this.objectService.getObjectInfo("JTABLEAREA", fileId);
            this.setHeader(response, objectInfo);
            IOUtils.copy((InputStream)download, (OutputStream)response.getOutputStream());
            response.getOutputStream().flush();
        }
        catch (Exception e) {
            throw new FileException("failed to down load file.fileKey:" + fileId, (Throwable)e);
        }
        finally {
            if (null != download) {
                try {
                    download.close();
                }
                catch (IOException e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
            this.closeOSS(objectStorageService);
        }
    }

    @GetMapping(value={"/downloadToPdf"})
    public void downloadToPdf(@Param(value="fileId") String fileId, HttpServletRequest request, HttpServletResponse response) {
        Assert.state(!StringUtils.isEmpty((String)fileId), "\u8bf7\u6c42\u53c2\u6570\u9519\u8bef, fileId \u4e0d\u80fd\u4e3a\u7a7a");
        boolean b = false;
        ObjectStorageService objectStorageService = null;
        try {
            objectStorageService = FileOperationUtils.objService((String)"JTABLEAREA");
            b = objectStorageService.existObject(fileId);
        }
        catch (ObjectStorageException e) {
            this.logger.error(e.getMessage(), e);
        }
        if (!b) {
            throw new FileNotFoundException(fileId);
        }
        InputStream download = null;
        try {
            download = objectStorageService.download(fileId);
            ObjectInfo objectInfo = this.objectService.getObjectInfo("JTABLEAREA", fileId);
            String extension = objectInfo.getExtension();
            String fileType = extension.substring(1, extension.length()).toUpperCase();
            byte[] bytes = ConvertUtil.convertToPdf((InputStream)download, (ConvertUtil.FILE_TYPE_TO_PDF)ConvertUtil.FILE_TYPE_TO_PDF.valueOf((String)fileType));
            this.setHeader(response, objectInfo);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            response.getOutputStream().flush();
        }
        catch (Exception e) {
            throw new FileException("failed to down load file.fileKey:" + fileId, (Throwable)e);
        }
        finally {
            if (null != download) {
                try {
                    download.close();
                }
                catch (IOException e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
            this.closeOSS(objectStorageService);
        }
    }

    @PostMapping(value={"/upload"})
    public ObjectInfo upload(MultipartFile file) {
        String guid = Guid.newGuid();
        ObjectInfo objectInfo = new ObjectInfo(guid);
        objectInfo.setSize(file.getSize());
        objectInfo.setName(file.getOriginalFilename());
        try (ObjectStorageService storageService = ObjectStorageManager.getInstance().createObjectService("JTABLEAREA");){
            if (storageService.existObject(objectInfo.getKey(), 1)) {
                this.logger.warn("[oss upload] bucket: {} \u4e2d\u5df2\u5b58\u5728 key \u4e3a: {} \u7684\u6587\u4ef6, \u5148\u5c06\u5176\u5220\u9664\u540e\u4e0a\u4f20", (Object)"JTABLEAREA", (Object)objectInfo.getKey());
                storageService.deleteObject(objectInfo.getKey());
            }
            try (InputStream inputStream = file.getInputStream();){
                storageService.upload(guid, inputStream, objectInfo);
            }
        }
        catch (ObjectStorageException | IOException e) {
            throw new BusinessRuntimeException(e);
        }
        return objectInfo;
    }

    private void setHeader(HttpServletResponse response, ObjectInfo objectInfo) throws UnsupportedEncodingException {
        String fileName = Optional.ofNullable(objectInfo.getName()).orElse(objectInfo.getKey());
        fileName = URLEncoder.encode(fileName, "UTF-8");
        String mimeType = Optional.ofNullable(URLConnection.guessContentTypeFromName(fileName)).orElse(MediaType.ALL.getType());
        response.setHeader("Content-Type", mimeType + ";charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    }

    private void closeOSS(ObjectStorageService objectStorageService) {
        if (null != objectStorageService) {
            try {
                objectStorageService.close();
            }
            catch (ObjectStorageException e) {
                this.logger.error(e.getMessage(), e);
            }
        }
    }
}

