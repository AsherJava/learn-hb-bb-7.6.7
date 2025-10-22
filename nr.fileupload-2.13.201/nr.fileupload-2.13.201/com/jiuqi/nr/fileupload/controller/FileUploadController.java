/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  javax.servlet.http.HttpServletRequest
 *  javax.validation.Valid
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 *  org.springframework.web.multipart.MultipartHttpServletRequest
 */
package com.jiuqi.nr.fileupload.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.fileupload.FileChunkParamInfo;
import com.jiuqi.nr.fileupload.FileChunkReturnInfo;
import com.jiuqi.nr.fileupload.FileMergeReturnInfo;
import com.jiuqi.nr.fileupload.FileUploadParamInfo;
import com.jiuqi.nr.fileupload.FilesUploadReturnInfo;
import com.jiuqi.nr.fileupload.service.CheckUploadFileService;
import com.jiuqi.nr.fileupload.service.FileChunkUploadService;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping(value={"/api/fileUpload"})
public class FileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    @Autowired
    private CheckUploadFileService checkUploadFileService;
    @Autowired
    private FileChunkUploadService fileChunkUploadService;

    @PostMapping(value={"/checkUploadFileSuffixInfo"})
    public FilesUploadReturnInfo checkUploadFileSuffixInfo(@Valid @RequestBody FileUploadParamInfo fileUploadParamInfo, HttpServletRequest request) {
        return this.checkUploadFileService.checkUploadFileSuffix(fileUploadParamInfo.getFileNameSizeMap(), fileUploadParamInfo.getSceneList(), fileUploadParamInfo.getAppName());
    }

    @PostMapping(value={"/checkUploadFile"})
    public FilesUploadReturnInfo checkUploadFileInfo(@RequestParam(value="file") MultipartFile[] files, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        String paramJson = request.getParameter("param");
        FileUploadParamInfo fileUploadParamInfo = null;
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            fileUploadParamInfo = (FileUploadParamInfo)mapper.readValue(paramJson, FileUploadParamInfo.class);
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return this.checkUploadFileService.checkUploadFileInfo(files, fileUploadParamInfo.getSceneList(), fileUploadParamInfo.getAppName());
    }

    @PostMapping(value={"/getAllCheckInfo"})
    @RequiresPermissions(value={"nr:fileupload:fileupload"})
    public Map<String, Object> getAllCheckInfo() {
        return this.checkUploadFileService.getAllCheckInfo();
    }

    @PostMapping(value={"/uploadChunkFile"})
    public FileChunkReturnInfo uploadChunkFile(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        MultipartFile file = multipartRequest.getFile("file");
        String param = multipartRequest.getParameter("param");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        FileChunkParamInfo fileChunkParam = null;
        try {
            fileChunkParam = (FileChunkParamInfo)mapper.readValue(param, FileChunkParamInfo.class);
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        FileChunkReturnInfo fileChunkReturnInfo = this.fileChunkUploadService.uploadChunk(file, fileChunkParam);
        return fileChunkReturnInfo;
    }

    @PostMapping(value={"/checkChunkFile"})
    public FileChunkReturnInfo checkChunkFile(@Valid @RequestBody FileChunkParamInfo fileChunkParamInfo) {
        return this.fileChunkUploadService.checkChunkKey(fileChunkParamInfo);
    }

    @PostMapping(value={"/mergeChunkFile"})
    public FileMergeReturnInfo mergeChunkFile(@Valid @RequestBody FileChunkParamInfo fileChunkParamInfo) {
        return this.fileChunkUploadService.createMergeTask(fileChunkParamInfo);
    }

    @GetMapping(value={"/queryMergeTask"})
    public FileMergeReturnInfo queryMergeTask(String fileKey) {
        return this.fileChunkUploadService.queryMergeTask(fileKey);
    }
}

