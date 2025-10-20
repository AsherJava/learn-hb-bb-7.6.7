/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO
 *  com.jiuqi.va.attachment.feign.client.VaAttachmentFeignClient
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.intf.data.DataTransEvent
 *  com.jiuqi.va.biz.intf.data.DataUpdate
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.bill.impl.event;

import com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO;
import com.jiuqi.va.attachment.feign.client.VaAttachmentFeignClient;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.intf.data.DataTransEvent;
import com.jiuqi.va.biz.intf.data.DataUpdate;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.domain.common.R;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaAttachmentConfirmGlobalDataTransEvent
implements DataTransEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(VaAttachmentConfirmGlobalDataTransEvent.class);
    @Autowired
    private VaAttachmentFeignClient vaAttachmentFeignClient;

    public void onSaveAfterCommit(DataImpl data, Map<String, DataUpdate> dataChangeSet) {
        BizAttachmentConfirmDTO confirmDTO = new BizAttachmentConfirmDTO();
        ListContainer rowsData = data.getMasterTable().getRows();
        if (rowsData.size() == 0) {
            LOGGER.info("\u5355\u636e\u4e3b\u8868\u65e0\u6570\u636e\uff0c\u65e0\u9700\u6267\u884c\u9644\u4ef6\u786e\u8ba4\u903b\u8f91");
            return;
        }
        confirmDTO.setBizcode(((DataRowImpl)rowsData.get(0)).getString("ID"));
        R r = this.vaAttachmentFeignClient.confirmDataUpdate(confirmDTO);
        if (r.getCode() != 0) {
            LOGGER.error("\u9644\u4ef6\u786e\u8ba4\u5931\u8d25\uff0c\u539f\u56e0\uff1a{}", (Object)r.getMsg());
        }
    }
}

