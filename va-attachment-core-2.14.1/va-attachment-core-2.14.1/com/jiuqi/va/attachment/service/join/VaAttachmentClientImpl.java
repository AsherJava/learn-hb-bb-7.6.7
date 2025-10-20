/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.domain.AttachmentConfigItemDO
 *  com.jiuqi.va.attachment.domain.AttachmentModeDO
 *  com.jiuqi.va.attachment.domain.AttachmentModeDTO
 *  com.jiuqi.va.attachment.domain.AttachmentSchemeDO
 *  com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO
 *  com.jiuqi.va.attachment.feign.client.VaAttachmentFeignClient
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.TreeVO
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.service.join;

import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.domain.AttachmentConfigItemDO;
import com.jiuqi.va.attachment.domain.AttachmentModeDO;
import com.jiuqi.va.attachment.domain.AttachmentModeDTO;
import com.jiuqi.va.attachment.domain.AttachmentSchemeDO;
import com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO;
import com.jiuqi.va.attachment.feign.client.VaAttachmentFeignClient;
import com.jiuqi.va.attachment.service.AttachmentBizConfirmService;
import com.jiuqi.va.attachment.service.AttachmentBizService;
import com.jiuqi.va.attachment.service.AttachmentModeService;
import com.jiuqi.va.attachment.service.AttachmentSchemeService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.TreeVO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Primary
public class VaAttachmentClientImpl
implements VaAttachmentFeignClient {
    @Autowired
    private AttachmentBizService bizAttachmentService;
    @Autowired
    private AttachmentSchemeService attachmentSchemeService;
    @Autowired
    private AttachmentModeService attachmentModeService;
    @Autowired
    private AttachmentBizConfirmService attachmentBizConfirmService;
    @Value(value="${upload.path}")
    private String localpath;

    public R confirm(String quotecode, AttachmentBizDO param) {
        return this.bizAttachmentService.confim(param);
    }

    public R update(String quotecode, List<AttachmentBizDO> param) {
        return this.bizAttachmentService.updateAtt(param, quotecode);
    }

    public R remove(String quotecode, List<AttachmentBizDTO> param) {
        return this.bizAttachmentService.removeAtt(param, quotecode);
    }

    public PageVO<TreeVO<AttachmentConfigItemDO>> tree(AttachmentSchemeDO attachmentSchemeDO) {
        return this.attachmentSchemeService.tree(attachmentSchemeDO);
    }

    public AttachmentSchemeDO get(AttachmentSchemeDO attachmentSchemeDO) {
        return this.attachmentSchemeService.get(attachmentSchemeDO);
    }

    public R copyFiles(List<AttachmentBizDO> param) {
        Map<String, Object> result = this.bizAttachmentService.copyFiles(param);
        R ok = R.ok();
        for (String s : result.keySet()) {
            ok.put(s, result.get(s));
        }
        return ok;
    }

    public R copyFilesRef(AttachmentBizDTO param) {
        return this.bizAttachmentService.copyFilesRef(param);
    }

    public PageVO<AttachmentModeDTO> list(AttachmentModeDO attachmentModeDO) {
        return this.attachmentModeService.list(attachmentModeDO);
    }

    public R getQuotecode() {
        return this.bizAttachmentService.getQuotecode();
    }

    public List<AttachmentBizDTO> listAttachment(String quotecode) {
        AttachmentBizDTO param = new AttachmentBizDTO();
        param.setQuotecode(quotecode);
        return this.bizAttachmentService.listAtt(param);
    }

    public Map<String, List<AttachmentBizDTO>> listByQuotecodes(TenantDO tenantDO) {
        Object quotecodes = tenantDO.getExtInfo("quotecodes");
        if (ObjectUtils.isEmpty(quotecodes)) {
            return null;
        }
        Object flag = tenantDO.getExtInfo("flag");
        return this.bizAttachmentService.listAttAndSchemeTitle((List)quotecodes, (Boolean)flag);
    }

    public boolean check(String quotecode, String modename, AttachmentBizDO attachmentBizDO) {
        return this.bizAttachmentService.checkAttachment(attachmentBizDO);
    }

    public R upload(MultipartFile file, String quotecode, AttachmentBizDTO bizAttachmentDTO) {
        bizAttachmentDTO.setQuotecode(quotecode);
        return this.bizAttachmentService.upload(file, bizAttachmentDTO);
    }

    public R confirmDataUpdate(BizAttachmentConfirmDTO param) {
        return this.attachmentBizConfirmService.confirm(param) ? R.ok() : R.error((String)"\u66f4\u65b0\u5931\u8d25");
    }

    public R getAttNumByQuotecode(String quotecode) {
        AttachmentBizDTO attachment = new AttachmentBizDTO();
        attachment.setQuotecode(quotecode);
        return this.bizAttachmentService.getAttNumByDO((AttachmentBizDO)attachment);
    }
}

