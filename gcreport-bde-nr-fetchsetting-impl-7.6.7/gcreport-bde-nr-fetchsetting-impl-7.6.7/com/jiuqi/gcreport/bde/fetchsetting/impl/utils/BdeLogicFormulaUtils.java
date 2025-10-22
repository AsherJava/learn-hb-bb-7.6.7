/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.common.constant.ArgumentValueEnum
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.FloatRegionConfigVO
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.utils;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.common.constant.ArgumentValueEnum;
import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.FloatRegionConfigVO;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExcelContext;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FixedAdaptSettingExcelDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.RegionFetchSetting;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BdeLogicFormulaUtils {
    public static void loadLogicFormula(RegionFetchSetting regionFetchSetting, FetchSettingExcelContext fetchSettingExcelContext) {
        for (Map.Entry<String, List<FixedAdaptSettingExcelDTO>> fixedAdaptSettingEntry : regionFetchSetting.getFixeAdaptSettingDTOMap().entrySet()) {
            for (FixedAdaptSettingExcelDTO fixedAdaptSettingDTO : fixedAdaptSettingEntry.getValue()) {
                String logicFormal = fixedAdaptSettingDTO.getLogicFormula();
                if (StringUtils.isEmpty((String)logicFormal)) continue;
                Map<String, String> map = BdeLogicFormulaUtils.initTitleToNameMap(regionFetchSetting.getFloatRegionConfigVO());
                for (Map.Entry<String, List<ExcelRowFetchSettingVO>> entry : fixedAdaptSettingDTO.getBizModelFormula().entrySet()) {
                    BizModelDTO bizModelDTO = fetchSettingExcelContext.getBizModelByCode(entry.getKey());
                    Integer index = entry.getValue().size();
                    for (int i = entry.getValue().size() - 1; i >= 0; --i) {
                        Object[] objectArray = new Object[2];
                        objectArray[0] = bizModelDTO.getName();
                        Integer n = index;
                        Integer n2 = index = Integer.valueOf(index - 1);
                        objectArray[1] = n;
                        map.put(String.format("\u4e1a\u52a1\u6a21\u578b.%1$s-%2$d", objectArray), String.format("BIZMODEL[%1$s]", entry.getValue().get(i).getId()));
                    }
                }
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    logicFormal = logicFormal.replace(entry.getKey(), (CharSequence)entry.getValue());
                }
                fixedAdaptSettingDTO.setLogicFormula(logicFormal);
            }
        }
    }

    public static String convertLogicFormulaToTitle(FixedAdaptSettingVO fixedAdaptSettingDTO, FetchSettingExcelContext fetchSettingExcelContext, List<FloatQueryFieldVO> queryFields) {
        String logicFormal = fixedAdaptSettingDTO.getLogicFormula();
        if (StringUtils.isEmpty((String)logicFormal)) {
            return null;
        }
        Map<String, String> map = BdeLogicFormulaUtils.initNameToTitleMap(queryFields);
        for (Map.Entry entry : fixedAdaptSettingDTO.getBizModelFormula().entrySet()) {
            BizModelDTO bizModelByCode = fetchSettingExcelContext.getBizModelByCode((String)entry.getKey());
            int index = 1;
            for (FixedFetchSourceRowSettingVO fixedFetchSourceRowSettingVO : (List)entry.getValue()) {
                map.put(String.format("BIZMODEL[%1$s]", fixedFetchSourceRowSettingVO.getId()), String.format("\u4e1a\u52a1\u6a21\u578b.%1$s-%2$d", bizModelByCode.getName(), index++));
            }
        }
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            if (!logicFormal.contains((CharSequence)entry.getKey())) continue;
            logicFormal = logicFormal.replace((CharSequence)entry.getKey(), (CharSequence)entry.getValue());
        }
        return logicFormal;
    }

    public static Map<String, String> initTitleToNameMap(FloatRegionConfigVO floatRegionConfigVO) {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        for (ArgumentValueEnum argumentValueEnum : ArgumentValueEnum.values()) {
            map.put(String.format("\u62a5\u8868\u53c2\u6570.%1$s", argumentValueEnum.getTitle()), String.format("ENV[%1$s]", argumentValueEnum.getCode()));
        }
        if (floatRegionConfigVO != null) {
            map.putAll(floatRegionConfigVO.getQueryConfigInfo().getQueryFields().stream().collect(Collectors.toMap(item -> String.format("\u67e5\u8be2\u7ed3\u679c.%1$s", item.getTitle()), item -> String.format("FLOAT[%1$s]", item.getName()), (K1, K2) -> K1)));
        }
        return map;
    }

    public static Map<String, String> initNameToTitleMap(List<FloatQueryFieldVO> queryFields) {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        for (ArgumentValueEnum argumentValueEnum : ArgumentValueEnum.values()) {
            map.put(String.format("ENV[%1$s]", argumentValueEnum.getCode()), String.format("\u62a5\u8868\u53c2\u6570.%1$s", argumentValueEnum.getTitle()));
        }
        if (queryFields != null) {
            map.putAll(queryFields.stream().collect(Collectors.toMap(item -> String.format("FLOAT[%1$s]", item.getName()), item -> String.format("\u67e5\u8be2\u7ed3\u679c.%1$s", item.getTitle()), (K1, K2) -> K1)));
        }
        return map;
    }
}

