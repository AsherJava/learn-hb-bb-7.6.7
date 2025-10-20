/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum
 *  com.jiuqi.gcreport.offsetitem.enums.TabSelectEnum
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAdjustExecutor
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.offsetitem.service.impl;

import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum;
import com.jiuqi.gcreport.offsetitem.enums.TabSelectEnum;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAdjustExecutor;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemShowType;
import com.jiuqi.gcreport.offsetitem.i18n.UnOffsetSelectOptionI18Const;
import com.jiuqi.gcreport.offsetitem.service.GcOffsetBaseService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class GcOffsetBaseServiceImpl
implements GcOffsetBaseService {
    @Resource
    GcOffSetItemAdjustExecutor gcOffSetItemAdjustExecutor;

    @Override
    public List<Map<String, Object>> listshowTypeConfigForPage(String pageCode, String dataSource) {
        List gcOffsetItemShowTypes = this.gcOffSetItemAdjustExecutor.listShowTypesForCondition(pageCode, dataSource);
        ArrayList<Map<String, Object>> configList = new ArrayList<Map<String, Object>>();
        int index = 0;
        for (GcOffsetItemShowType gcOffsetItemShowType : gcOffsetItemShowTypes) {
            if (FilterMethodEnum.COMMON.getCode().equals(gcOffsetItemShowType.getCode())) continue;
            HashMap<String, Object> configMap = new HashMap<String, Object>();
            configMap.put("isSuppportUnitTreeSort", gcOffsetItemShowType.isEnableMemorySort());
            configMap.put("isShow", "0");
            configMap.put("isUnitTreeSort", "0");
            configMap.put("ordinal", index);
            String I18n = UnOffsetSelectOptionI18Const.getI18nForCode(gcOffsetItemShowType.getCode());
            if (StringUtils.hasLength(I18n)) {
                configMap.put("showType", GcI18nUtil.getMessage((String)I18n));
            } else {
                configMap.put("showType", gcOffsetItemShowType.getTitle());
            }
            configMap.put("value", gcOffsetItemShowType.getCode());
            configList.add(configMap);
            ++index;
        }
        return configList;
    }

    @Override
    public List<Map<String, Object>> listshowTypeConfig(String dataSource) {
        List gcOffsetItemShowTypes = this.gcOffSetItemAdjustExecutor.listShowTypeForDataSource(dataSource);
        ArrayList<Map<String, Object>> configDatas = new ArrayList<Map<String, Object>>();
        HashMap<String, List> categorizedData = new HashMap<String, List>();
        int index = 0;
        for (GcOffsetItemShowType showType : gcOffsetItemShowTypes) {
            if (FilterMethodEnum.COMMON.getCode().equals(showType.getCode())) continue;
            HashMap<String, String> configMap = new HashMap<String, String>();
            configMap.put("isSuppportUnitTreeSort", String.valueOf(showType.isEnableMemorySort()));
            configMap.put("isShow", String.valueOf(1));
            configMap.put("isUnitTreeSort", String.valueOf(0));
            configMap.put("ordinal", String.valueOf(index));
            String i18nCode = UnOffsetSelectOptionI18Const.getI18nForCode(showType.getCode());
            configMap.put("showType", StringUtils.hasLength(i18nCode) ? GcI18nUtil.getMessage((String)i18nCode) : showType.getTitle());
            configMap.put("value", showType.getCode());
            categorizedData.computeIfAbsent(showType.getPage().getPageCode(), k -> new ArrayList()).add(configMap);
            ++index;
        }
        categorizedData.forEach((tab, showTypes) -> {
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("title", TabSelectEnum.fromCode((String)tab).getTitle());
            dataMap.put("code", tab);
            dataMap.put("value", showTypes);
            configDatas.add(dataMap);
        });
        return configDatas;
    }
}

