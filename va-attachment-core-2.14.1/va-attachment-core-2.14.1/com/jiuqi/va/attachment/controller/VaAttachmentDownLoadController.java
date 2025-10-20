/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.attachment.controller;

import com.jiuqi.va.attachment.service.VaAttachmentDownLoadService;
import com.jiuqi.va.domain.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bizAttachment"})
public class VaAttachmentDownLoadController {
    @Autowired
    private VaAttachmentDownLoadService attachmentDownLoadService;

    @GetMapping(value={"/download/zip/check"})
    R downloadZipCheck() {
        return this.attachmentDownLoadService.downloadZipCheck();
    }

    @GetMapping(value={"/download/zip"})
    void downloadZipByFolder(@RequestParam(value="key") String key) {
        this.attachmentDownLoadService.downloadZipByFolder(key);
    }
}

