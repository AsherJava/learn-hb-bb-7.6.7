/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.datatrace.context.GcDataTracerContext
 *  com.jiuqi.gcreport.datatrace.datatracer.GcDataTracer
 *  com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 */
package com.jiuqi.gcreport.inputdata.datatracer;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.datatrace.context.GcDataTracerContext;
import com.jiuqi.gcreport.datatrace.datatracer.GcDataTracer;
import com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataService;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcInputDataTracer
implements GcDataTracer {
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private InputDataService inputDataService;

    public GcDataTraceCondi queryGcDataTraceCondi(GcDataTracerContext context) {
        GcOffSetVchrItemDTO gcOffSetVchrItemDTO = context.getGcOffSetVchrItemDTO();
        String srcId = gcOffSetVchrItemDTO.getSrcId();
        List<Object> inputDataEOS = new ArrayList();
        if (!StringUtils.isEmpty((String)srcId)) {
            inputDataEOS = this.inputDataService.queryByIds(Arrays.asList(srcId), gcOffSetVchrItemDTO.getTaskId());
        } else {
            String srcOffsetGroupId = gcOffSetVchrItemDTO.getSrcOffsetGroupId();
            if (!StringUtils.isEmpty((String)srcOffsetGroupId)) {
                String tableName = this.inputDataNameProvider.getTableNameByTaskId(gcOffSetVchrItemDTO.getTaskId());
                inputDataEOS = this.inputDataService.queryIdLimitFieldsByOffsetGroupId(Arrays.asList(srcOffsetGroupId), tableName);
            }
        }
        if (CollectionUtils.isEmpty(inputDataEOS) || Objects.isNull(inputDataEOS.get(0))) {
            return null;
        }
        InputDataEO inputDataEO = (InputDataEO)((Object)inputDataEOS.get(0));
        GcDataTraceCondi gcDataTraceCondi = new GcDataTraceCondi();
        gcDataTraceCondi.setGcDataTraceType(GcDataTraceTypeEnum.INPUTDATA.getType());
        gcDataTraceCondi.setAcctPeriod(gcOffSetVchrItemDTO.getAcctPeriod());
        gcDataTraceCondi.setAcctYear(gcOffSetVchrItemDTO.getAcctYear());
        gcDataTraceCondi.setCurrency(gcOffSetVchrItemDTO.getOffSetCurr());
        gcDataTraceCondi.setInputUnitId(context.getCondition().getOrgId());
        gcDataTraceCondi.setOppUnitId(gcOffSetVchrItemDTO.getOppUnitId());
        gcDataTraceCondi.setUnitId(gcOffSetVchrItemDTO.getUnitId());
        gcDataTraceCondi.setOrgType(context.getCondition().getOrgType());
        gcDataTraceCondi.setPeriodStr(gcOffSetVchrItemDTO.getDefaultPeriod());
        gcDataTraceCondi.setSrcId(inputDataEO.getId());
        gcDataTraceCondi.setRuleIds(Arrays.asList(gcOffSetVchrItemDTO.getRuleId()));
        gcDataTraceCondi.setTaskId(gcOffSetVchrItemDTO.getTaskId());
        return gcDataTraceCondi;
    }

    public boolean isMatch(GcDataTracerContext context) {
        GcOffSetVchrItemDTO gcOffSetVchrItemDTO = context.getGcOffSetVchrItemDTO();
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(gcOffSetVchrItemDTO.getTaskId());
        return !StringUtils.isEmpty((String)tableName);
    }

    public int order() {
        return Integer.MAX_VALUE;
    }
}

