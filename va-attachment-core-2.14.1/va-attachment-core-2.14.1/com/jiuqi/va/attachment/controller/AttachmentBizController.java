/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.controller;

import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.intf.AttachmentFileCheckIntf;
import com.jiuqi.va.attachment.service.AttachmentBizService;
import com.jiuqi.va.attachment.utils.VaAttachmentI18nUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController(value="vaAttachmentBizController")
@RequestMapping(value={"/bizAttachment"})
public class AttachmentBizController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentBizController.class);
    @Autowired
    private AttachmentBizService bizAttachmentService;
    @Autowired(required=false)
    private List<AttachmentFileCheckIntf> attachmentFileCheckIntfs;

    @PostMapping(value={"/check/{quotecode}/{modename}"})
    boolean check(AttachmentBizDO param) {
        return this.bizAttachmentService.checkAttachment(param);
    }

    @GetMapping(value={"/list/{quotecode}"})
    List<AttachmentBizDTO> list(AttachmentBizDTO param) {
        return this.bizAttachmentService.listAtt(param);
    }

    @PostMapping(value={"/listByQuotecodes"})
    Map<String, List<AttachmentBizDTO>> listByQuotecodes(@RequestBody TenantDO tenantDO) {
        Object quotecodes = tenantDO.getExtInfo("quotecodes");
        if (ObjectUtils.isEmpty(quotecodes)) {
            return null;
        }
        Object flag = tenantDO.getExtInfo("flag");
        return this.bizAttachmentService.listAttAndSchemeTitle((List)quotecodes, (Boolean)flag);
    }

    @GetMapping(value={"/get/{quotecode}/{id}"})
    AttachmentBizDO get(AttachmentBizDO param) {
        return this.bizAttachmentService.getAtt(param);
    }

    @PostMapping(value={"/update/{quotecode}"})
    R update(@RequestBody List<AttachmentBizDO> param, @PathVariable(value="quotecode") String qcode) {
        return this.bizAttachmentService.updateAtt(param, qcode);
    }

    @PostMapping(value={"/remove/{quotecode}"})
    R remove(@RequestBody List<AttachmentBizDTO> param, @PathVariable(value="quotecode") String qcode) {
        return this.bizAttachmentService.removeAtt(param, qcode);
    }

    @PostMapping(value={"/confirm/{quotecode}"})
    R confirm(AttachmentBizDO param) {
        return this.bizAttachmentService.confim(param);
    }

    @PostMapping(value={"/reset/{quotecode}"})
    @Deprecated
    R reset(AttachmentBizDO param) {
        return this.bizAttachmentService.reset(param);
    }

    @GetMapping(value={"/getattnum/{quotecode}"})
    R getAttNumByQuotecode(AttachmentBizDO param) {
        return this.bizAttachmentService.getAttNumByDO(param);
    }

    @GetMapping(value={"/quotecode/get"})
    R getQuotecode() {
        return this.bizAttachmentService.getQuotecode();
    }

    @PostMapping(value={"/upload/{quotecode}"})
    R upload(@RequestParam(value="file") MultipartFile file, AttachmentBizDTO bizAttachmentDTO) {
        if (this.attachmentFileCheckIntfs != null) {
            String fileName = file.getOriginalFilename();
            if (!StringUtils.hasText(fileName)) {
                return R.error((String)VaAttachmentI18nUtil.getMessage("va.attachment.file.name.not.empty", new Object[0]));
            }
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            for (AttachmentFileCheckIntf attachmentFileCheckIntf : this.attachmentFileCheckIntfs) {
                R r;
                if (!attachmentFileCheckIntf.getName().equals(suffix) || (r = attachmentFileCheckIntf.doCheck(file)).getCode() != 1) continue;
                return r;
            }
        }
        return this.bizAttachmentService.upload(file, bizAttachmentDTO);
    }

    @PostMapping(value={"/ftpupload"})
    R ftpUpload(@RequestParam(value="file") MultipartFile file, AttachmentBizDTO bizAttachmentDTO) {
        return this.bizAttachmentService.ftpUpload(file, bizAttachmentDTO);
    }

    @GetMapping(value={"/download/{quotecode}"})
    void downloadAll(AttachmentBizDTO param) {
        this.bizAttachmentService.downloadAll(param);
    }

    @PostMapping(value={"/download/more"})
    void downloadMore(@RequestBody AttachmentBizDTO param) {
        this.bizAttachmentService.downloadAll(param);
    }

    @GetMapping(value={"/download/{quotecode}/{id}"})
    void download(AttachmentBizDTO param) {
        this.bizAttachmentService.download(param);
    }

    @PostMapping(value={"/download"})
    void downloadFile(@RequestBody AttachmentBizDTO param) {
        this.bizAttachmentService.download(param);
    }

    @GetMapping(value={"/downloadtemplate/{masterid}"})
    void downloadTemplate(@PathVariable(value="masterid") UUID masterid) {
        this.bizAttachmentService.downloadTemplate(masterid);
    }

    @PostMapping(value={"/sync/ordinal/{quotecode}"})
    R syncOrdinal(@RequestBody List<AttachmentBizDO> param, @PathVariable(value="quotecode") String quotecode) {
        try {
            this.bizAttachmentService.syncOrdinal(param, quotecode);
            return R.ok();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return R.error();
        }
    }
}

