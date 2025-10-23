/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.internal.listener.BatchOptEvent
 *  com.jiuqi.nvwa.subsystem.core.util.SubServerUtil
 */
package com.jiuqi.nr.transmission.data.reject;

import com.jiuqi.nr.dataentry.internal.listener.BatchOptEvent;
import com.jiuqi.nr.transmission.data.reject.RejectParamDTO;
import com.jiuqi.nr.transmission.data.service.RejectService;
import com.jiuqi.nvwa.subsystem.core.util.SubServerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RejectActionListener {
    private static final Logger logger = LoggerFactory.getLogger(RejectActionListener.class);
    @Autowired
    private RejectService rejectService;

    @EventListener(value={BatchOptEvent.class})
    @Async
    public void interceptRejectMessage(BatchOptEvent event) {
        try {
            boolean enable = SubServerUtil.isEnable();
            if (!enable) {
                return;
            }
            if (event.getActionCode().equals("act_reject") || event.getActionCode().equals("cus_reject")) {
                RejectParamDTO rejectParamDTO = new RejectParamDTO();
                rejectParamDTO.setTask(event.getTaskKey());
                rejectParamDTO.setPeriod(event.getPeriod());
                rejectParamDTO.setFormSchemeKey(event.getFromSchemeKey());
                rejectParamDTO.setUnitIds(event.getUnits());
                rejectParamDTO.setUserName(event.getOperator());
                rejectParamDTO.setFromGroupKeys(event.getFromGroupKeys());
                rejectParamDTO.setFormKeys(event.getFormKeys());
                this.rejectService.sendRejectAction(rejectParamDTO);
            }
        }
        catch (Exception e) {
            logger.error("\u591a\u7ea7\u90e8\u7f72\uff0c\u4e0a\u7ea7\u9000\u56de\u5355\u4f4d\uff0c\u7ed9\u4e0b\u7ea7\u5173\u8054\u7684\u673a\u6784\u53d1\u9001\u90ae\u4ef6\u51fa\u9519\uff1a " + e.getMessage());
        }
    }
}

