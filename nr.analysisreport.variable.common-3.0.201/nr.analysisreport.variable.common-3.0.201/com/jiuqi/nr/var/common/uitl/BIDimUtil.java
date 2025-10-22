/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.analysisreport.vo.ReportBaseVO
 *  com.jiuqi.nr.analysisreport.vo.ReportBaseVO$PeriodDim
 *  com.jiuqi.nr.analysisreport.vo.ReportBaseVO$UnitDim
 *  com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.var.common.uitl;

import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.analysisreport.vo.ReportBaseVO;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;

public class BIDimUtil {
    private static final String MESSAGE_ALIAS_PREFIX = "P_";
    private static final String MD_ORG = "MD_ORG";

    public static Map<String, List<String>> generateMasterValues(ReportVariableParseVO reportVariableParseVO) {
        List chooseUnits;
        HashMap<String, List<String>> masterValues = new HashMap<String, List<String>>();
        ReportBaseVO reportBaseVO = reportVariableParseVO.getReportBaseVO();
        ReportBaseVO.PeriodDim periodDim = reportBaseVO.getPeriod();
        if (periodDim != null) {
            PeriodEngineService periodEngineService = (PeriodEngineService)BeanUtil.getBean(PeriodEngineService.class);
            String dimensionDateName = periodEngineService.getPeriodAdapter().getPeriodEntity(periodDim.getViewKey()).getDimensionName();
            ArrayList<String> value = new ArrayList<String>();
            value.add(periodDim.getCalendarCode());
            masterValues.put(MESSAGE_ALIAS_PREFIX + dimensionDateName, value);
        }
        if (!CollectionUtils.isEmpty(chooseUnits = reportBaseVO.getChooseUnits())) {
            Iterator unitDimIterator = chooseUnits.iterator();
            IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
            while (unitDimIterator.hasNext()) {
                ReportBaseVO.UnitDim unitDim = (ReportBaseVO.UnitDim)unitDimIterator.next();
                String entityName = entityMetaService.queryEntity(unitDim.getViewKey()).getCode();
                entityName = entityName.contains(MD_ORG) && !masterValues.containsKey("P_MD_ORG") ? "P_MD_ORG" : MESSAGE_ALIAS_PREFIX + entityName;
                ArrayList<String> value = new ArrayList<String>();
                value.add(unitDim.getCode());
                masterValues.put(entityName, value);
            }
        }
        return masterValues;
    }
}

