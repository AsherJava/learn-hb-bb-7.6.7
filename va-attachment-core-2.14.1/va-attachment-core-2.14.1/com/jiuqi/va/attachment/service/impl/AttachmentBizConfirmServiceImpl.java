/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.BizAttachmentConfirmDO
 *  com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO
 *  com.jiuqi.va.attachment.intf.BizAttachmentExecuteConfirmSchedule
 *  org.apache.shiro.util.CollectionUtils
 *  org.springframework.dao.DataIntegrityViolationException
 */
package com.jiuqi.va.attachment.service.impl;

import com.jiuqi.va.attachment.dao.BizAttachmentConfirmDao;
import com.jiuqi.va.attachment.domain.BizAttachmentConfirmDO;
import com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO;
import com.jiuqi.va.attachment.intf.BizAttachmentExecuteConfirmSchedule;
import com.jiuqi.va.attachment.service.AttachmentBizConfirmService;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AttachmentBizConfirmServiceImpl
implements AttachmentBizConfirmService,
InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentBizConfirmServiceImpl.class);
    @Autowired
    private BizAttachmentConfirmDao bizAttachmentConfirmDao;
    @Autowired
    private List<BizAttachmentExecuteConfirmSchedule> bizAttachmentExecuteConfirmSchedules;
    private final Map<String, BizAttachmentExecuteConfirmSchedule> bizAttachmentExecuteConfirmScheduleMap = new HashMap<String, BizAttachmentExecuteConfirmSchedule>();

    @Override
    public void afterPropertiesSet() throws Exception {
        for (BizAttachmentExecuteConfirmSchedule bizAttachmentExecuteConfirmSchedule : this.bizAttachmentExecuteConfirmSchedules) {
            this.bizAttachmentExecuteConfirmScheduleMap.put(bizAttachmentExecuteConfirmSchedule.getBizType(), bizAttachmentExecuteConfirmSchedule);
        }
    }

    @Override
    public boolean insert(BizAttachmentConfirmDTO bizAttachmentConfirmDTO) {
        if (bizAttachmentConfirmDTO == null || bizAttachmentConfirmDTO.getQuotecode() == null) {
            return false;
        }
        try {
            BizAttachmentConfirmDO newData = new BizAttachmentConfirmDO();
            newData.setId(UUID.randomUUID());
            newData.setQuotecode(bizAttachmentConfirmDTO.getQuotecode());
            newData.setBizcode(bizAttachmentConfirmDTO.getBizcode());
            newData.setBiztype(bizAttachmentConfirmDTO.getBiztype());
            newData.setExtdata(bizAttachmentConfirmDTO.getExtdata());
            return this.bizAttachmentConfirmDao.insertData(newData) > 0;
        }
        catch (DataIntegrityViolationException e) {
            BizAttachmentConfirmDO update = new BizAttachmentConfirmDO();
            update.setQuotecode(bizAttachmentConfirmDTO.getQuotecode());
            return this.bizAttachmentConfirmDao.updateUpdateTime(update) > 0;
        }
    }

    @Override
    public boolean confirm(BizAttachmentConfirmDTO bizAttachmentConfirmDTO) {
        String bizcode = bizAttachmentConfirmDTO.getBizcode();
        try {
            this.bizAttachmentConfirmDao.confirmByBizcode((BizAttachmentConfirmDO)bizAttachmentConfirmDTO);
        }
        catch (Exception e) {
            LOGGER.error("\u6279\u91cf\u786e\u8ba4\u9644\u4ef6\u5931\u8d25:{}", (Object)bizcode);
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(BizAttachmentConfirmDTO bizAttachmentConfirmDTO) {
        if (bizAttachmentConfirmDTO != null && bizAttachmentConfirmDTO.getQuotecode() != null) {
            return this.bizAttachmentConfirmDao.deleteByQuotecode((BizAttachmentConfirmDO)bizAttachmentConfirmDTO) > 0;
        }
        return false;
    }

    @Override
    public boolean deleteConfirm(String qcode) {
        BizAttachmentConfirmDTO bizAttachmentConfirmDTO = new BizAttachmentConfirmDTO();
        bizAttachmentConfirmDTO.setQuotecode(qcode);
        try {
            this.bizAttachmentConfirmDao.deleteByQuotecode((BizAttachmentConfirmDO)bizAttachmentConfirmDTO);
            return true;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public void executeConfirmData(BizAttachmentConfirmDTO param) {
        if (CollectionUtils.isEmpty(this.bizAttachmentExecuteConfirmSchedules)) {
            return;
        }
        List<BizAttachmentConfirmDTO> list = this.bizAttachmentConfirmDao.selectByUpdatetime(param);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (BizAttachmentConfirmDTO bizAttachmentConfirmDTO : list) {
            try {
                if (bizAttachmentConfirmDTO.getBiztype() == null) {
                    bizAttachmentConfirmDTO.setBiztype("bill");
                }
                this.bizAttachmentExecuteConfirmScheduleMap.get(bizAttachmentConfirmDTO.getBiztype()).executeConfirm(bizAttachmentConfirmDTO);
            }
            catch (Exception e) {
                LOGGER.error("\u6267\u884c\u9644\u4ef6\u786e\u8ba4\u5931\u8d25:{}", (Object)(bizAttachmentConfirmDTO.getQuotecode() + e.getMessage()));
            }
        }
    }

    @Override
    public BizAttachmentConfirmDTO query(BizAttachmentConfirmDTO bizAttachmentConfirmDTO) {
        if (bizAttachmentConfirmDTO == null || bizAttachmentConfirmDTO.getQuotecode() == null) {
            return null;
        }
        BizAttachmentConfirmDTO query = new BizAttachmentConfirmDTO();
        query.setQuotecode(bizAttachmentConfirmDTO.getQuotecode());
        List select = this.bizAttachmentConfirmDao.select(query);
        if (CollectionUtils.isEmpty((Collection)select)) {
            return null;
        }
        return ((BizAttachmentConfirmDO)select.get(0)).copyEntity();
    }
}

