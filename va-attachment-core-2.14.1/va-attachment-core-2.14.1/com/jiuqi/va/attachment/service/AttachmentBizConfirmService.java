/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO
 */
package com.jiuqi.va.attachment.service;

import com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO;

public interface AttachmentBizConfirmService {
    public boolean insert(BizAttachmentConfirmDTO var1);

    public boolean confirm(BizAttachmentConfirmDTO var1);

    public boolean delete(BizAttachmentConfirmDTO var1);

    public boolean deleteConfirm(String var1);

    public void executeConfirmData(BizAttachmentConfirmDTO var1);

    public BizAttachmentConfirmDTO query(BizAttachmentConfirmDTO var1);
}

