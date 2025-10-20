/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.domain.AttachmentModeDTO
 *  com.jiuqi.va.attachment.domain.CleanUselessAttachmentDTO
 *  com.jiuqi.va.attachment.domain.SchemeEntity
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.service;

import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.domain.AttachmentModeDTO;
import com.jiuqi.va.attachment.domain.CleanUselessAttachmentDTO;
import com.jiuqi.va.attachment.domain.SchemeEntity;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentBizService {
    public List<AttachmentBizDTO> list(AttachmentBizDO var1);

    public List<AttachmentBizDTO> list(AttachmentBizDO var1, Boolean var2);

    public List<AttachmentBizDTO> queryAttList(AttachmentBizDO var1);

    public int update(AttachmentBizDO var1);

    public int remove(AttachmentBizDO var1);

    public void clean(CleanUselessAttachmentDTO var1);

    public int getAttNumByQuotecode(AttachmentBizDO var1);

    public List<AttachmentBizDTO> listAtt(AttachmentBizDTO var1);

    public Map<String, List<AttachmentBizDTO>> listAttAndSchemeTitle(List<String> var1, Boolean var2);

    public AttachmentBizDO getAtt(AttachmentBizDO var1);

    public R updateAtt(List<AttachmentBizDO> var1, String var2);

    public R removeAtt(List<AttachmentBizDTO> var1, String var2);

    public R deleteFile(AttachmentBizDTO var1);

    public R confim(AttachmentBizDO var1);

    public R reset(AttachmentBizDO var1);

    public R getAttNumByDO(AttachmentBizDO var1);

    public R getQuotecode();

    public R ftpUpload(MultipartFile var1, AttachmentBizDTO var2);

    public void downloadAll(AttachmentBizDTO var1);

    public R upload(MultipartFile var1, AttachmentBizDTO var2);

    public SchemeEntity getMessageFromMode(String var1, AttachmentModeDTO var2);

    public void download(AttachmentBizDTO var1);

    public boolean checkAttachment(AttachmentBizDO var1);

    public void downloadTemplate(UUID var1);

    public void syncOrdinal(List<AttachmentBizDO> var1, String var2);

    public Map<String, Object> copyFiles(List<AttachmentBizDO> var1);

    public R copyFilesRef(AttachmentBizDTO var1);

    public void cleanMarkAsDeletedAttachment(CleanUselessAttachmentDTO var1);
}

