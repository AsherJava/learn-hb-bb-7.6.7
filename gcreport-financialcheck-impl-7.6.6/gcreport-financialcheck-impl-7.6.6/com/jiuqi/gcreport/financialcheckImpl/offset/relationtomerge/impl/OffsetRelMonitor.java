/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.financialcheckapi.checkconfig.vo.FinancialCheckConfigVO
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.offsetitem.monitor.IOffsetCoreMonitor
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dao.OffsetRelatedItemDao;
import com.jiuqi.gcreport.financialcheckapi.checkconfig.vo.FinancialCheckConfigVO;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.offsetitem.monitor.IOffsetCoreMonitor;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import java.util.Collection;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OffsetRelMonitor
implements IOffsetCoreMonitor {
    @Autowired
    private OffsetRelatedItemDao offsetRelatedItemDao;
    @Autowired
    private GcOffSetItemAdjustCoreService offSetItemAdjustCoreService;

    public String monitorName() {
        return "OffsetRelMonitor";
    }

    public void beforeDelete(Collection<String> idList) {
        Collection groupIds = this.offSetItemAdjustCoreService.listOffsetGroupIdsById(idList);
        FinancialCheckConfigVO checkConfig = FinancialCheckConfigUtils.getCheckConfig();
        if (Objects.isNull(checkConfig) || StringUtils.isEmpty((String)checkConfig.getCheckWay())) {
            return;
        }
        if (ReconciliationModeEnum.ITEM.getCode().equals(checkConfig.getCheckWay())) {
            this.offsetRelatedItemDao.batchDeleteByOffsetGroupId(groupIds);
            return;
        }
        this.offsetRelatedItemDao.batchClearOffsetGroupId(groupIds);
    }
}

