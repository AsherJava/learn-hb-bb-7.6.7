/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 */
package com.jiuqi.va.attachment.service;

import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import java.util.UUID;

public interface VaAttachmentBizRefService {
    public boolean refCopyFile(AttachmentBizDTO var1, String var2);

    public boolean refMoveFile(UUID var1);

    public boolean checkRef(UUID var1);
}

