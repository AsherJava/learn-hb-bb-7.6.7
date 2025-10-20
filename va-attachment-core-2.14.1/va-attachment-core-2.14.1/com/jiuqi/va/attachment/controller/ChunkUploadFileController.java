/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.oss.feign.domain.VaChunkUploadObjectInfo
 *  com.jiuqi.va.oss.feign.domain.VaChunkUploadVO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.controller;

import com.jiuqi.va.attachment.service.ChunkUploadFileService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.oss.feign.domain.VaChunkUploadObjectInfo;
import com.jiuqi.va.oss.feign.domain.VaChunkUploadVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController(value="vaChunkUploadFileController")
@RequestMapping(value={"/bizAttachment/chunk/upload/file"})
public class ChunkUploadFileController {
    private static final Logger logger = LoggerFactory.getLogger(ChunkUploadFileController.class);
    @Autowired
    private ChunkUploadFileService chunkUploadFileService;

    @PostMapping(value={"/init"})
    R init(@RequestBody VaChunkUploadObjectInfo vaChunkUploadObjectInfo) {
        return this.chunkUploadFileService.init(vaChunkUploadObjectInfo);
    }

    @PostMapping(value={"/upload"})
    R upload(@RequestParam(value="file") MultipartFile file, VaChunkUploadVO vaChunkUploadVO) {
        return this.chunkUploadFileService.upload(file, vaChunkUploadVO);
    }

    @PostMapping(value={"/finishUpload"})
    public R finishUpload(@RequestBody VaChunkUploadObjectInfo vaChunkUploadObjectInfo) {
        return this.chunkUploadFileService.finishUpload(vaChunkUploadObjectInfo);
    }
}

