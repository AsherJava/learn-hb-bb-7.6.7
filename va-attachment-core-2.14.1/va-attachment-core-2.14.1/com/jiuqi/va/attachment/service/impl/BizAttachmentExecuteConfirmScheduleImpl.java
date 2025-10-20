/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO
 *  com.jiuqi.va.attachment.intf.BizAttachmentExecuteConfirmSchedule
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  org.apache.shiro.util.CollectionUtils
 */
package com.jiuqi.va.attachment.service.impl;

import com.jiuqi.va.attachment.common.AttachmentConst;
import com.jiuqi.va.attachment.dao.VaAttachmentBizDao;
import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO;
import com.jiuqi.va.attachment.entity.BizDataDO;
import com.jiuqi.va.attachment.intf.BizAttachmentExecuteConfirmSchedule;
import com.jiuqi.va.attachment.service.AttachmentBizConfirmService;
import com.jiuqi.va.attachment.service.AttachmentBizService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BizAttachmentExecuteConfirmScheduleImpl
implements BizAttachmentExecuteConfirmSchedule {
    private static final Logger LOGGER = LoggerFactory.getLogger(BizAttachmentExecuteConfirmScheduleImpl.class);
    @Autowired
    private AttachmentBizService attachmentBizService;
    @Autowired
    private AttachmentBizConfirmService attachmentBizConfirmService;
    @Autowired
    private VaAttachmentBizDao vaAttachmentBizDao;

    public String getBizType() {
        return "bill";
    }

    public void executeConfirm(BizAttachmentConfirmDTO bizAttachmentConfirmDTO) {
        String id;
        AttachmentBizDO attachmentBizDO = new AttachmentBizDO();
        attachmentBizDO.setQuotecode(bizAttachmentConfirmDTO.getQuotecode());
        attachmentBizDO.setTenantName(ShiroUtil.getTenantName());
        List<AttachmentBizDTO> attachmentBizDTOS = this.attachmentBizService.queryAttList(attachmentBizDO);
        String extdata = bizAttachmentConfirmDTO.getExtdata();
        if (extdata == null) {
            ArrayList<AttachmentBizDTO> deleteRecordList = new ArrayList<AttachmentBizDTO>();
            for (AttachmentBizDTO attachmentBizDTO : attachmentBizDTOS) {
                if (attachmentBizDTO.getStatus() == 0) {
                    deleteRecordList.add(attachmentBizDTO);
                    continue;
                }
                if (attachmentBizDTO.getStatus() != 2) continue;
                attachmentBizDTO.setStatus(Integer.valueOf(1));
                this.attachmentBizService.update((AttachmentBizDO)attachmentBizDTO);
            }
            this.deleteRecord(bizAttachmentConfirmDTO, deleteRecordList, AttachmentConst.DELETE_STATUS_CONFIRM);
            return;
        }
        Map stringObjectMap = JSONUtil.parseMap((String)extdata);
        String tableName = (String)stringObjectMap.get("tablename");
        int i = this.vaAttachmentBizDao.bizDataCount(new BizDataDO(tableName, id = (String)stringObjectMap.get("id")));
        if (i == 0) {
            this.deleteRecord(bizAttachmentConfirmDTO, attachmentBizDTOS, AttachmentConst.DELETE_STATUS_CONFIRM);
            return;
        }
        if (bizAttachmentConfirmDTO.getConfirmtime() == null) {
            List<AttachmentBizDTO> insertData = attachmentBizDTOS.stream().filter(o -> o.getStatus() == 0).collect(Collectors.toList());
            this.deleteRecord(bizAttachmentConfirmDTO, insertData, AttachmentConst.DELETE_STATUS_CONFIRM);
            List deleteData = attachmentBizDTOS.stream().filter(o -> o.getStatus() == 2).collect(Collectors.toList());
            for (AttachmentBizDTO deleteDatum : deleteData) {
                deleteDatum.setStatus(Integer.valueOf(1));
                deleteDatum.setSuffix(deleteDatum.getQuotecode().substring(0, 6));
                this.attachmentBizService.update((AttachmentBizDO)deleteDatum);
            }
        } else {
            ArrayList<AttachmentBizDTO> deleteRecordListException = new ArrayList<AttachmentBizDTO>();
            ArrayList<AttachmentBizDTO> deleteRecordListConfirm = new ArrayList<AttachmentBizDTO>();
            Date confirmtime = bizAttachmentConfirmDTO.getConfirmtime();
            for (AttachmentBizDTO attachmentBizDTO : attachmentBizDTOS) {
                if (confirmtime.compareTo(attachmentBizDTO.getCreatetime()) >= 0) {
                    if (attachmentBizDTO.getStatus() == 0) {
                        deleteRecordListException.add(attachmentBizDTO);
                        continue;
                    }
                    if (attachmentBizDTO.getStatus() != 2) continue;
                    attachmentBizDTO.setStatus(Integer.valueOf(1));
                    this.attachmentBizService.update((AttachmentBizDO)attachmentBizDTO);
                    continue;
                }
                if (attachmentBizDTO.getStatus() == 0) {
                    deleteRecordListConfirm.add(attachmentBizDTO);
                    continue;
                }
                if (attachmentBizDTO.getStatus() != 2) continue;
                attachmentBizDTO.setStatus(Integer.valueOf(1));
                this.attachmentBizService.update((AttachmentBizDO)attachmentBizDTO);
            }
            this.deleteRecord(bizAttachmentConfirmDTO, deleteRecordListException, AttachmentConst.DELETE_STATUS_EXCEPTION);
            this.deleteRecord(bizAttachmentConfirmDTO, deleteRecordListConfirm, AttachmentConst.DELETE_STATUS_CONFIRM);
        }
    }

    private void deleteRecord(BizAttachmentConfirmDTO bizAttachmentConfirmDTO, List<AttachmentBizDTO> collect, Integer deleteType) {
        if (CollectionUtils.isEmpty(collect)) {
            this.attachmentBizConfirmService.delete(bizAttachmentConfirmDTO);
            return;
        }
        collect.forEach(attachmentBizDTO -> attachmentBizDTO.setDeleteType(deleteType));
        R r = this.attachmentBizService.removeAtt(collect, bizAttachmentConfirmDTO.getQuotecode());
        if (r.getCode() != 0) {
            LOGGER.error("\u9644\u4ef6\u786e\u8ba4\u5b9a\u65f6\u4efb\u52a1\u5220\u9664\uff0c\u5220\u9664\u9644\u4ef6\u5931\u8d25\uff1a{}", (Object)r.getMsg());
        } else {
            this.attachmentBizConfirmService.delete(bizAttachmentConfirmDTO);
        }
    }
}

