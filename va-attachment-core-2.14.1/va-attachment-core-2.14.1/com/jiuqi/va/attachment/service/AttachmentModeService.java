/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentModeDO
 *  com.jiuqi.va.attachment.domain.AttachmentModeDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.service;

import com.jiuqi.va.attachment.domain.AttachmentModeDO;
import com.jiuqi.va.attachment.domain.AttachmentModeDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentModeService {
    public AttachmentModeDTO get(AttachmentModeDO var1);

    public R add(MultipartFile var1, AttachmentModeDO var2);

    public R checkMode(AttachmentModeDO var1);

    public R update(MultipartFile var1, AttachmentModeDO var2);

    public R delete(AttachmentModeDO var1);

    public PageVO<AttachmentModeDTO> list(AttachmentModeDO var1);
}

