/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizRemarksDO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.RedisLockUtil
 */
package com.jiuqi.va.attachment.service.impl;

import com.jiuqi.va.attachment.dao.VaAttachmentBizRemarksDao;
import com.jiuqi.va.attachment.domain.AttachmentBizRemarksDO;
import com.jiuqi.va.attachment.service.AttachmentBizRemarksService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.RedisLockUtil;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentBizRemarksServiceImpl
implements AttachmentBizRemarksService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentBizRemarksServiceImpl.class);
    @Autowired
    private VaAttachmentBizRemarksDao remarksDao;

    @Override
    public int insert(AttachmentBizRemarksDO attachmentBizRemarksDO) {
        AttachmentBizRemarksDO remarksDO = new AttachmentBizRemarksDO();
        UUID masterid = attachmentBizRemarksDO.getMasterid();
        if (masterid == null) {
            return 0;
        }
        R execute = RedisLockUtil.execute(() -> {
            remarksDO.setMasterid(masterid);
            AttachmentBizRemarksDO result = (AttachmentBizRemarksDO)this.remarksDao.selectOne(remarksDO);
            if (result != null) {
                result.setRemarks(attachmentBizRemarksDO.getRemarks());
                int i = this.remarksDao.updateByPrimaryKey(result);
                if (i == 0) {
                    throw new RuntimeException("\u5907\u6ce8\u66f4\u65b0\u5931\u8d25");
                }
            } else {
                attachmentBizRemarksDO.setId(UUID.randomUUID());
                int insert = this.remarksDao.insert(attachmentBizRemarksDO);
                if (insert == 0) {
                    throw new RuntimeException("\u5907\u6ce8\u63d2\u5165\u5931\u8d25");
                }
            }
        }, (String)masterid.toString(), (long)1000L, (boolean)true);
        if (execute.getCode() == 0) {
            return 1;
        }
        if (execute.getCode() == 100) {
            LOGGER.info("\u5907\u6ce8\u63d2\u5165\u6267\u884c\u5931\u8d25\uff0c\u539f\u56e0\uff1a{}", (Object)execute.getMsg());
            return 1;
        }
        LOGGER.error("\u5907\u6ce8\u63d2\u5165\u6267\u884c\u5931\u8d25\uff0c\u539f\u56e0\uff1a{}", (Object)execute.getMsg());
        return 0;
    }

    @Override
    public int remove(AttachmentBizRemarksDO attachmentBizRemarksDO) {
        return this.remarksDao.delete(attachmentBizRemarksDO);
    }

    @Override
    public int update(AttachmentBizRemarksDO attachmentBizRemarksDO) {
        return this.remarksDao.updateByPrimaryKey(attachmentBizRemarksDO);
    }
}

