/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.gather.IRegisterDataentryRefreshParams
 *  com.jiuqi.nr.dataentry.gather.IRegisterDataentryRefreshParams$RefreshType
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.gcreport.conversion.action;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.gather.IRegisterDataentryRefreshParams;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GcConversionRefreshParam
implements IRegisterDataentryRefreshParams {
    private static Logger LOGGER = LoggerFactory.getLogger(GcConversionRefreshParam.class);

    public IRegisterDataentryRefreshParams.RefreshType getRefreshType() {
        return IRegisterDataentryRefreshParams.RefreshType.UNIT_REFRESH;
    }

    public String getPramaKey(JtableContext context) {
        return "GcConversionButtonDataentryRefreshParams";
    }

    public Object getPramaValue(JtableContext envContext) {
        Map dimensionSet = envContext.getDimensionSet();
        DimensionValue gcorgtypeValue = (DimensionValue)dimensionSet.get("MD_GCORGTYPE");
        DimensionValue currencyValue = (DimensionValue)dimensionSet.get("MD_CURRENCY");
        DimensionValue orgValue = (DimensionValue)dimensionSet.get("MD_ORG");
        String orgTypeId = gcorgtypeValue == null ? null : gcorgtypeValue.getValue();
        String currencyId = currencyValue == null ? null : currencyValue.getValue();
        String orgId = orgValue == null ? null : orgValue.getValue();
        String taskKey = envContext.getTaskKey();
        String formSchemeKey = envContext.getFormSchemeKey();
        FormulaSchemeConfigService formulaSchemeConfigService = (FormulaSchemeConfigService)SpringContextUtils.getBean(FormulaSchemeConfigService.class);
        INvwaSystemOptionService nvwaSystemOptionService = (INvwaSystemOptionService)SpringContextUtils.getBean(INvwaSystemOptionService.class);
        Map dimensionSetMap = DimensionUtils.generateDimMap(null, null, (String)currencyId, (String)orgTypeId, (String)"0", (String)taskKey);
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("formulaSchemeConversion", "1");
        try {
            String convertSystemSchemeId;
            FormulaSchemeConfigDTO formulaSchemeConfigDTO = formulaSchemeConfigService.getSchemeConfigByOrgAndAssistDim(formSchemeKey, orgId, dimensionSetMap);
            if (formulaSchemeConfigDTO != null && "false".equals(convertSystemSchemeId = formulaSchemeConfigDTO.getConvertSystemSchemeId())) {
                LOGGER.debug("\u5355\u4f4d[{}]\u4e0d\u5141\u8bb8\u5916\u5e01\u6298\u7b97\uff0c\u539f\u56e0\uff1a{}\u3002", (Object)orgId, (Object)"\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u914d\u7f6e\u4e0d\u5141\u8bb8\u5916\u5e01\u6298\u7b97");
                param.put("formulaSchemeConversion", "0");
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        String option = nvwaSystemOptionService.get("ALLOW_UPLOADSTATE_CONVERSION", "ALLOW_UPLOADSTATE_CONVERSION");
        param.put("uploadStateConversion", option);
        return param;
    }
}

