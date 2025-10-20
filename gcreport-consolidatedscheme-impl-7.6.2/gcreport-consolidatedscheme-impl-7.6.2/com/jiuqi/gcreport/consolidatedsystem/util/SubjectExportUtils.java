/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.util.MapUtils
 */
package com.jiuqi.gcreport.consolidatedsystem.util;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.enums.SubjectExportEnum;
import com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SubjectExportUtils {
    public static Map<String, String> getSubjectExcelColumnTitleMap() {
        LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
        titleMap.put(SubjectExportEnum.CODE.getCode(), SubjectExportEnum.CODE.getTitle());
        titleMap.put(SubjectExportEnum.TITLE.getCode(), SubjectExportEnum.TITLE.getTitle());
        titleMap.put(SubjectExportEnum.PARENTCODE.getCode(), SubjectExportEnum.PARENTCODE.getTitle());
        titleMap.put(SubjectExportEnum.STATUS.getCode(), SubjectExportEnum.STATUS.getTitle());
        titleMap.put(SubjectExportEnum.CONSOLIDATIONTYPE.getCode(), SubjectExportEnum.CONSOLIDATIONTYPE.getTitle());
        titleMap.put(SubjectExportEnum.ORIENT.getCode(), SubjectExportEnum.ORIENT.getTitle());
        titleMap.put(SubjectExportEnum.ATTRI.getCode(), SubjectExportEnum.ATTRI.getTitle());
        titleMap.put(SubjectExportEnum.BOUNDINDEXPATH.getCode(), SubjectExportEnum.BOUNDINDEXPATH.getTitle());
        Map<String, String> multilingualNamesTitle = ConsolidatedSystemUtils.getMultilingualNamesTitle();
        titleMap.putAll(multilingualNamesTitle);
        return titleMap;
    }

    public static List<Map<String, Object>> convertImportedData(Map<String, String> titleMap, List<Object[]> excelDatas) {
        int i;
        ArrayList<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        if (CollectionUtils.isEmpty(excelDatas) || MapUtils.isEmpty(titleMap)) {
            return dataList;
        }
        HashMap headTitle2Key = new HashMap();
        titleMap.forEach((key, propertyName) -> headTitle2Key.put(propertyName, key));
        Object[] headData = excelDatas.get(0);
        String[] headsKeys = new String[headData.length];
        for (i = 0; i < headData.length; ++i) {
            String propertyName2 = (String)headData[i];
            String headKey = (String)headTitle2Key.get(propertyName2);
            Objects.requireNonNull(headKey, "\u7b2c" + (i + 1) + "\u5217\u6807\u9898" + propertyName2 + "\u4e0d\u5408\u6cd5\u3002");
            headsKeys[i] = headKey;
        }
        for (i = 1; i < excelDatas.size(); ++i) {
            Object[] dataArr = excelDatas.get(i);
            HashMap<String, Object> rowMap = new HashMap<String, Object>();
            for (int j = 0; j < dataArr.length; ++j) {
                Object value = dataArr[j];
                rowMap.put(headsKeys[j], value);
            }
            dataList.add(rowMap);
        }
        return dataList;
    }
}

