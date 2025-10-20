/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService
 *  com.jiuqi.gcreport.inputdata.util.InputDataConver
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.gcreport.onekeymerge.util;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService;
import com.jiuqi.gcreport.inputdata.util.InputDataConver;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.nr.common.params.DimensionValue;
import java.util.Map;

public class EfdcUtils {
    public static String getFetchScheme(GcActionParamsVO paramsVO, String diffUnitId) {
        GcOnekeyMergeService onekeyMergeService = (GcOnekeyMergeService)SpringContextUtils.getBean(GcOnekeyMergeService.class);
        FormulaSchemeConfigService formulaSchemeConfigService = (FormulaSchemeConfigService)SpringContextUtils.getBean(FormulaSchemeConfigService.class);
        Map<String, DimensionValue> dimensionValueMap = onekeyMergeService.buildDimensionMap(paramsVO.getTaskId(), paramsVO.getCurrency(), paramsVO.getPeriodStr(), paramsVO.getOrgType(), diffUnitId, paramsVO.getSelectAdjustCode());
        FormulaSchemeConfigDTO formulaSchemeConfigDTO = formulaSchemeConfigService.getSchemeConfigByOrgAndAssistDim(paramsVO.getSchemeId(), diffUnitId, InputDataConver.getDimFieldValueMap(dimensionValueMap, (String)paramsVO.getTaskId()));
        return formulaSchemeConfigDTO.getFetchSchemeId();
    }
}

