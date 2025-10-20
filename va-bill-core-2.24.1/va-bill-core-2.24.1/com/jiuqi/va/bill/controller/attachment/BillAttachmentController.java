/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.attachement.AttachmentComponentInfo
 *  com.jiuqi.va.domain.attachement.AttachmentConfigInfo
 *  com.jiuqi.va.domain.attachement.QueryAttachmentInfoParam
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller.attachment;

import com.jiuqi.va.bill.service.attachment.BillAttachmentService;
import com.jiuqi.va.domain.attachement.AttachmentComponentInfo;
import com.jiuqi.va.domain.attachement.AttachmentConfigInfo;
import com.jiuqi.va.domain.attachement.QueryAttachmentInfoParam;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill/attachment"})
public class BillAttachmentController {
    private static final Logger logger = LoggerFactory.getLogger(BillAttachmentController.class);
    @Autowired
    private BillAttachmentService billAttachmentService;

    @PostMapping(value={"/table-field-config/list"})
    public R listAttachmentTableFieldConfig(@RequestBody QueryAttachmentInfoParam attachmentInfoParam) {
        try {
            List<AttachmentConfigInfo> configInfos = this.billAttachmentService.listAttachmentTableFieldConfig(attachmentInfoParam);
            R r = R.ok();
            r.put("data", configInfos);
            return r;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/componentInfo/list"})
    public List<AttachmentComponentInfo> queryAttachmentComponentInfo(@RequestBody TenantDO param) {
        return this.billAttachmentService.queryAttachmentComponentInfo((String)param.getExtInfo("defineCode"));
    }
}

