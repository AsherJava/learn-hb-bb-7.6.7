/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizRemarksDO
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.attachment.controller;

import com.jiuqi.va.attachment.domain.AttachmentBizRemarksDO;
import com.jiuqi.va.attachment.service.AttachmentBizRemarksService;
import com.jiuqi.va.attachment.utils.VaAttachmentI18nUtil;
import com.jiuqi.va.domain.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaAttachmentRemarksController")
@RequestMapping(value={"/bizAttachment/remarks"})
public class AttachmentRemarksController {
    @Autowired
    private AttachmentBizRemarksService attachmentBizRemarksService;

    @PostMapping(value={"/insert"})
    R insert(@RequestBody AttachmentBizRemarksDO attachmentBizRemarksDO) {
        return this.attachmentBizRemarksService.insert(attachmentBizRemarksDO) == 1 ? R.ok() : R.error((String)VaAttachmentI18nUtil.getMessage("va.attachment.memo.insert.error", new Object[0]));
    }
}

