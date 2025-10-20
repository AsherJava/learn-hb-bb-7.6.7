/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.domain.AttachmentHandleIntf
 *  com.jiuqi.va.attachment.domain.AttachmentModeDTO
 *  com.jiuqi.va.attachment.domain.AttachmentSchemeDO
 *  com.jiuqi.va.attachment.domain.CopyProgressDO
 *  com.jiuqi.va.attachment.domain.SchemeEntity
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.attachment.service;

import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.domain.AttachmentHandleIntf;
import com.jiuqi.va.attachment.domain.AttachmentModeDTO;
import com.jiuqi.va.attachment.domain.AttachmentSchemeDO;
import com.jiuqi.va.attachment.domain.CopyProgressDO;
import com.jiuqi.va.attachment.domain.SchemeEntity;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AttachmentBizHelpService {
    public R checkQtcode(String var1);

    public R checkFile(AttachmentModeDTO var1, String var2, long var3);

    public int add(String var1, AttachmentBizDTO var2);

    public AttachmentBizDTO get(AttachmentBizDO var1);

    public SchemeEntity getMessageFromScheme(String var1);

    public void parseSchemeConfig(AttachmentSchemeDO var1);

    public void sortAttachment(String var1, AttachmentBizDTO var2);

    public UUID getFileId(String var1, AttachmentBizDTO var2);

    public void syncCopyFileProgress(String var1, CopyProgressDO var2);

    public void asyncCopyFile(Map<String, String> var1, List<AttachmentHandleIntf> var2, List<AttachmentBizDTO> var3, String var4);

    public void copyFileRef(Map<String, String> var1, AttachmentBizDTO var2);
}

