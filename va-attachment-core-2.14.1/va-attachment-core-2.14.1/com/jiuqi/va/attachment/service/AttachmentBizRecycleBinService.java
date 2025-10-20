/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.BizAttachmentRecycleBinDTO
 */
package com.jiuqi.va.attachment.service;

import com.jiuqi.va.attachment.domain.BizAttachmentRecycleBinDTO;
import java.util.List;

public interface AttachmentBizRecycleBinService {
    public int insert(BizAttachmentRecycleBinDTO var1);

    public void scheduleClear();

    public List<BizAttachmentRecycleBinDTO> listErrorData(BizAttachmentRecycleBinDTO var1);

    public List<BizAttachmentRecycleBinDTO> listNothingData(BizAttachmentRecycleBinDTO var1);

    public BizAttachmentRecycleBinDTO restore(List<BizAttachmentRecycleBinDTO> var1);

    public BizAttachmentRecycleBinDTO deleteRecord(List<BizAttachmentRecycleBinDTO> var1);

    public boolean markAsNotHandle(List<BizAttachmentRecycleBinDTO> var1);

    public int getNoThingTotal();

    public int getErrorTotal();
}

