/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterCustomFormulaSettingVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterSettingVO
 */
package com.jiuqi.gcreport.workingpaper.utils;

import com.jiuqi.gcreport.workingpaper.enums.ArbitrarilyMergeOrgFilterControlTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.ArbitrarilyMergeOrgFilterDataSourceTypeEnum;
import com.jiuqi.gcreport.workingpaper.service.ArbitrarilyMergeService;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterCustomFormulaSettingVO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeOrgFilterSettingVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AssemblyFormulaUtil {
    @Autowired
    private ArbitrarilyMergeService arbitrarilyMergeService;

    public String assemblyFormula(List<ArbitrarilyMergeOrgFilterSettingVO> dataList) {
        ArrayList<String> finalFormulaList = new ArrayList<String>();
        for (ArbitrarilyMergeOrgFilterSettingVO condition : dataList) {
            String key;
            Map map;
            if (condition.getSourceType().equals(ArbitrarilyMergeOrgFilterDataSourceTypeEnum.BASE_DATA.getType())) {
                if (condition.getControlType().equals(ArbitrarilyMergeOrgFilterControlTypeEnum.ORG_SINGLE.getType())) {
                    map = condition.getValueMap();
                    for (Map.Entry entry : map.entrySet()) {
                        key = (String)entry.getKey();
                        List orgCodeList = (List)entry.getValue();
                        if (CollectionUtils.isEmpty(orgCodeList)) continue;
                        String dwfwFormula = orgCodeList.stream().map(s -> "GETUNITANDALLCHILDREN(\"" + s + "\")=\"true\"").collect(Collectors.joining(" OR "));
                        dwfwFormula = "(" + dwfwFormula + ")";
                        finalFormulaList.add(dwfwFormula);
                    }
                    continue;
                }
                map = condition.getValueMap();
                for (Map.Entry entry : map.entrySet()) {
                    key = (String)entry.getKey();
                    List baseDataCodeList = (List)entry.getValue();
                    if (CollectionUtils.isEmpty(baseDataCodeList)) continue;
                    String baseDataCodeFormula = baseDataCodeList.stream().map(s -> "GETUNITDATA(\"" + key.trim() + "\")=\"" + s + "\"").collect(Collectors.joining(" OR "));
                    baseDataCodeFormula = "(" + baseDataCodeFormula + ")";
                    finalFormulaList.add(baseDataCodeFormula);
                }
                continue;
            }
            map = condition.getValueMap();
            for (Map.Entry entry : map.entrySet()) {
                key = (String)entry.getKey();
                List formulaIdList = (List)entry.getValue();
                if (CollectionUtils.isEmpty(formulaIdList)) continue;
                ArrayList<String> formulaList = new ArrayList<String>();
                for (String formulaId : formulaIdList) {
                    ArbitrarilyMergeOrgFilterCustomFormulaSettingVO formula = this.arbitrarilyMergeService.getFormulaById(formulaId);
                    formulaList.add(formula.getFormula().trim());
                }
                String customFormula = formulaList.stream().map(s -> "(" + s + ")").collect(Collectors.joining(" OR "));
                customFormula = "(" + customFormula + ")";
                finalFormulaList.add(customFormula);
            }
        }
        finalFormulaList.add("GETUNITDATA(\"BBLX\")=\"0\"");
        return finalFormulaList.stream().map(String::valueOf).collect(Collectors.joining(" AND "));
    }
}

