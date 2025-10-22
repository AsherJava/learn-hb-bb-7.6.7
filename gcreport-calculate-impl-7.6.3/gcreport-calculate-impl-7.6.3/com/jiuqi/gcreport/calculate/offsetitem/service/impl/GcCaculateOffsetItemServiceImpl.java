/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.calculate.event.GcCalcTaskEvent
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.offsetitem.caculate.service.GcCaculateOffsetItemService
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.calculate.offsetitem.service.impl;

import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.calculate.event.GcCalcTaskEvent;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.offsetitem.caculate.service.GcCaculateOffsetItemService;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class GcCaculateOffsetItemServiceImpl
implements GcCaculateOffsetItemService {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public List<GcOffSetVchrItemDTO> getPreCalcOffSetItems(QueryParamsVO queryParamsVO) {
        GcCalcArgmentsDTO calcArgments = new GcCalcArgmentsDTO();
        calcArgments.setOrgType(queryParamsVO.getOrgType());
        calcArgments.setRuleIds(queryParamsVO.getRules());
        calcArgments.setAcctPeriod(queryParamsVO.getAcctPeriod());
        calcArgments.setTaskId(queryParamsVO.getTaskId());
        calcArgments.setAcctYear(queryParamsVO.getAcctYear());
        calcArgments.setPeriodType(queryParamsVO.getPeriodType());
        calcArgments.setPeriodStr(queryParamsVO.getPeriodStr());
        calcArgments.setCurrency(queryParamsVO.getCurrency());
        calcArgments.setOrgId(queryParamsVO.getOrgId());
        calcArgments.setSchemeId(queryParamsVO.getSchemeId());
        calcArgments.getPreCalcFlag().set(true);
        calcArgments.getDisabledCopyInitOffset().set(true);
        calcArgments.setSn(UUIDOrderUtils.newUUIDStr());
        calcArgments.setSelectAdjustCode(queryParamsVO.getSelectAdjustCode());
        GcCalcEnvContextImpl env = new GcCalcEnvContextImpl(calcArgments.getSn());
        env.setCalcArgments(calcArgments);
        this.applicationEventPublisher.publishEvent((ApplicationEvent)new GcCalcTaskEvent((Object)this, env));
        return env.getCalcContextExpandVariableCenter().getPreCalcOffSetItems();
    }
}

