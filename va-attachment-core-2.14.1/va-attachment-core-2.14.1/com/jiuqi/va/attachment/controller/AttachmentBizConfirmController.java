/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.attachment.controller;

import com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO;
import com.jiuqi.va.attachment.service.AttachmentBizConfirmService;
import com.jiuqi.va.domain.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="vaAttachmentBizConfirmController")
@RequestMapping(value={"/bizAttachment"})
public class AttachmentBizConfirmController {
    @Autowired
    private AttachmentBizConfirmService bizAttachmentService;

    @PostMapping(value={"/confirm/data/update"})
    R check(@RequestBody BizAttachmentConfirmDTO param) {
        if (param.getBizcode() == null) {
            return R.ok();
        }
        return this.bizAttachmentService.confirm(param) ? R.ok() : R.error((String)"\u66f4\u65b0\u5931\u8d25");
    }
}

