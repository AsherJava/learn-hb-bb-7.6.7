/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.attachement.AttachmentComponentInfo
 *  com.jiuqi.va.domain.attachement.AttachmentConfigInfo
 *  com.jiuqi.va.domain.attachement.QueryAttachmentInfoParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.bill.service.attachment;

import com.jiuqi.va.bill.domain.attachmen.AttachmentInfo;
import com.jiuqi.va.domain.attachement.AttachmentComponentInfo;
import com.jiuqi.va.domain.attachement.AttachmentConfigInfo;
import com.jiuqi.va.domain.attachement.QueryAttachmentInfoParam;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface BillAttachmentService {
    public List<AttachmentConfigInfo> listAttachmentTableFieldConfig(QueryAttachmentInfoParam var1);

    public List<AttachmentComponentInfo> queryAttachmentComponentInfo(String var1);

    public List<AttachmentInfo> uploadAttachment(List<MultipartFile> var1, String var2);

    public List<AttachmentInfo> uploadAttachment(List<MultipartFile> var1, String var2, String var3);
}

