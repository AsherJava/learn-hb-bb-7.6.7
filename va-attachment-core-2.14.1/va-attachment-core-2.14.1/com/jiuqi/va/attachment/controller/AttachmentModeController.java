/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentModeDO
 *  com.jiuqi.va.attachment.domain.AttachmentModeDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.controller;

import com.jiuqi.va.attachment.domain.AttachmentModeDO;
import com.jiuqi.va.attachment.domain.AttachmentModeDTO;
import com.jiuqi.va.attachment.service.AttachmentModeService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController(value="vaAttachmentModeController")
@RequestMapping(value={"/bizAttachment/mode"})
public class AttachmentModeController {
    @Autowired
    private AttachmentModeService attachmentModeService;

    @PostMapping(value={"/getattmode"})
    AttachmentModeDTO get(@RequestBody AttachmentModeDO attachmentModeDTO) {
        return this.attachmentModeService.get(attachmentModeDTO);
    }

    @PostMapping(value={"/add"})
    R add(@RequestParam(value="file", required=false) MultipartFile file, AttachmentModeDO attachmentModeDO) {
        return this.attachmentModeService.add(file, attachmentModeDO);
    }

    @PostMapping(value={"/delete"})
    R delete(@RequestBody AttachmentModeDO attachmentModeDO) {
        return this.attachmentModeService.delete(attachmentModeDO);
    }

    @PostMapping(value={"/update"})
    R update(@RequestParam(value="file", required=false) MultipartFile file, AttachmentModeDO attachmentModeDO) {
        return this.attachmentModeService.update(file, attachmentModeDO);
    }

    @PostMapping(value={"/list"})
    PageVO<AttachmentModeDTO> list(@RequestBody AttachmentModeDO attachmentModeDO) {
        return this.attachmentModeService.list(attachmentModeDO);
    }

    @PostMapping(value={"/checkmode"})
    R checkMode(@RequestBody AttachmentModeDO attachmentModeDO) {
        return this.attachmentModeService.checkMode(attachmentModeDO);
    }
}

