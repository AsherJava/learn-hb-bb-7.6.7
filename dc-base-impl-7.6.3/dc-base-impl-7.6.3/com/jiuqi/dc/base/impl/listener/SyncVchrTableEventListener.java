/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.event.SyncVchrTableEvent
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 */
package com.jiuqi.dc.base.impl.listener;

import com.jiuqi.dc.base.common.event.SyncVchrTableEvent;
import com.jiuqi.dc.base.impl.assistdim.util.AssistDimUtil;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SyncVchrTableEventListener
implements ApplicationListener<SyncVchrTableEvent> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(SyncVchrTableEvent event) {
        ShiroUtil.bindTenantName((String)event.getTenantName());
        Set assistFlagSet = AssistDimUtil.listPublished().stream().map(DimensionVO::getCode).collect(Collectors.toSet());
        for (String assistFlag : event.getAssistFlagList()) {
            if (!assistFlagSet.contains(assistFlag) && !"CFITEMCODE".equals(assistFlag)) continue;
            try {
                AssistDimUtil.syncVchrTableWithItemByItemAssistDim(event.getTenantName(), assistFlag);
            }
            catch (Exception e) {
                this.logger.error("\u540c\u6b65\u8868\u7ed3\u6784\u51fa\u9519", e);
            }
        }
    }
}

