/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ArgumentValueEnum
 *  com.jiuqi.bde.fetch.client.FetchFormulaClient
 *  com.jiuqi.bde.fetch.client.dto.FetchFormulaDTO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker;

import com.jiuqi.bde.common.constant.ArgumentValueEnum;
import com.jiuqi.bde.fetch.client.FetchFormulaClient;
import com.jiuqi.bde.fetch.client.dto.FetchFormulaDTO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FailedSettingLog;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExcelContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FixedAdaptSettingExcelDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeLogicChecker {
    @Autowired
    FetchFormulaClient fetchFormulaClient;

    public boolean doCheck(FixedAdaptSettingExcelDTO fixedAdaptSettingDTO, FetchSettingExcelContext fetchSettingExcelContext) {
        if (StringUtils.isEmpty((String)fixedAdaptSettingDTO.getLogicFormula())) {
            return true;
        }
        FetchFormulaDTO fetchFormulaDTO = new FetchFormulaDTO();
        fetchFormulaDTO.setFormula(fixedAdaptSettingDTO.getLogicFormula());
        fetchFormulaDTO.getEnvParamMap().putAll(BdeLogicChecker.initEnv());
        try {
            return BdeClientUtil.parseResponse((BusinessResponseEntity)this.fetchFormulaClient.check(fetchFormulaDTO)) == null;
        }
        catch (Exception e) {
            fetchSettingExcelContext.addFailedSettingLog(new FailedSettingLog(fixedAdaptSettingDTO.getSheetName(), e.getMessage() + "\n"));
            return false;
        }
    }

    private static Map<String, Object> initEnv() {
        HashMap<String, Object> params = new HashMap<String, Object>(ArgumentValueEnum.values().length);
        boolean period = true;
        params.put(ArgumentValueEnum.UNITCODE.getCode(), "M0112000000000");
        params.put(ArgumentValueEnum.YEAR.getCode(), "2022");
        params.put(ArgumentValueEnum.PERIOD.getCode(), "07");
        params.put(ArgumentValueEnum.BOOKCODE.getCode(), "B01");
        params.put(ArgumentValueEnum.STARTDATE.getCode(), "20220101");
        params.put(ArgumentValueEnum.ENDDATE.getCode(), "20220131");
        params.put(ArgumentValueEnum.TASKID.getCode(), "4fd8a55e-f675-4719-bd18-84563852cfd6");
        params.put(ArgumentValueEnum.INCLUDEUNCHARGED.getCode(), 1);
        params.put(ArgumentValueEnum.PERIODSCHEME.getCode(), "2022Y12");
        params.put(ArgumentValueEnum.MD_GCADJTYPE.getCode(), "GCADJTYPE");
        params.put(ArgumentValueEnum.MD_CURRENCY.getCode(), "CNY");
        params.put(ArgumentValueEnum.MD_GCORGTYPE.getCode(), "MD_GCADJTYPE");
        return params;
    }

    public boolean doCheck(List<FixedAdaptSettingExcelDTO> fixedAdaptSettingDTOList, FetchSettingExcelContext fetchSettingExcelContext) {
        if (fixedAdaptSettingDTOList.stream().noneMatch(item -> StringUtils.isEmpty((String)item.getLogicFormula()))) {
            return true;
        }
        return fixedAdaptSettingDTOList.stream().allMatch(item -> StringUtils.isEmpty((String)item.getLogicFormula()));
    }
}

