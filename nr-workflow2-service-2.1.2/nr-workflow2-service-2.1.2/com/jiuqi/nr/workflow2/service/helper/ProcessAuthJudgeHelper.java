/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.workflow2.service.helper;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.service.helper.IProcessAuthJudgeHelper;
import java.text.ParseException;
import java.util.Date;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessAuthJudgeHelper
implements IProcessAuthJudgeHelper {
    @Autowired
    public IEntityAuthorityService authService;
    @Autowired
    public PeriodEngineService periodEngineService;

    @Override
    public boolean hasUnitAuditOperation(String entityId, String rowKey, String periodEntityId, String periodString) {
        try {
            if (this.authService.isEnableAuthority(entityId)) {
                Date[] dateRange = this.parseFromPeriod(periodString, periodEntityId);
                return this.authService.canAuditEntity(entityId, rowKey, dateRange[0], dateRange[1]);
            }
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return true;
    }

    @Override
    public boolean hasUnitEditOperation(String entityId, String rowKey, String periodEntityId, String periodString) {
        try {
            if (this.authService.isEnableAuthority(entityId)) {
                Date[] dateRange = this.parseFromPeriod(periodString, periodEntityId);
                return this.authService.canEditEntity(entityId, rowKey, dateRange[0], dateRange[1]);
            }
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return true;
    }

    private Date[] parseFromPeriod(String periodString, String periodEntityId) throws ParseException {
        Date[] dateRegion = new Date[]{Consts.DATE_VERSION_INVALID_VALUE, Consts.DATE_VERSION_FOR_ALL};
        if (StringUtils.isEmpty((String)periodString) || StringUtils.isEmpty((String)periodEntityId)) {
            return dateRegion;
        }
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntityId);
        return periodProvider.getPeriodDateRegion(periodString);
    }
}

