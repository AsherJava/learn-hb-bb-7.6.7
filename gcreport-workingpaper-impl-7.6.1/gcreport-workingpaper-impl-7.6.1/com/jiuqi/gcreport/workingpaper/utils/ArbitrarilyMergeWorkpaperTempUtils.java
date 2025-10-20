/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 */
package com.jiuqi.gcreport.workingpaper.utils;

import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

public class ArbitrarilyMergeWorkpaperTempUtils {
    public static QueryParamsVO convertPenInfoToOffsetParams(WorkingPaperQueryCondition penInfo) {
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        BeanUtils.copyProperties(penInfo, queryParamsVO);
        queryParamsVO.setTaskId(penInfo.getTaskID());
        queryParamsVO.setSchemeId(penInfo.getSchemeID());
        queryParamsVO.setOrgType(penInfo.getOrg_type());
        queryParamsVO.setPeriodStr(penInfo.getPeriodStr());
        queryParamsVO.setOrgId(penInfo.getOrgid());
        queryParamsVO.setCurrency(penInfo.getCurrency());
        queryParamsVO.setOtherShowColumns(penInfo.getOtherShowColumnKeys());
        queryParamsVO.setPageSize(-1);
        queryParamsVO.setPageNum(-1);
        return queryParamsVO;
    }

    public static List<Map<String, Object>> setRowSpanAndSort(List<Map<String, Object>> unSortedRecords) {
        if (CollectionUtils.isEmpty(unSortedRecords)) {
            return unSortedRecords;
        }
        ArrayList<Map<String, Object>> sortedRecords = new ArrayList<Map<String, Object>>();
        ArrayList<Map<String, Object>> oneEntryRecords = new ArrayList<Map<String, Object>>();
        String mrecid = null;
        Comparator comparator = (record1, record2) -> {
            int result = MapUtils.compareInt((Map)record2, (Map)record1, (Object)"SUBJECTORIENT");
            if (result == 0) {
                result = MapUtils.compareStr((Map)record1, (Map)record2, (Object)"SUBJECTCODE");
            }
            return result;
        };
        int entryIndex = 1;
        for (Map<String, Object> record : unSortedRecords) {
            String tempMrecid = (String)record.get("MRECID");
            if (null == mrecid || !mrecid.equals(tempMrecid)) {
                int size = oneEntryRecords.size();
                if (size > 0) {
                    oneEntryRecords.sort(comparator);
                    sortedRecords.addAll(oneEntryRecords);
                    ((Map)oneEntryRecords.get(0)).put("rowspan", size);
                    ((Map)oneEntryRecords.get(0)).put("index", entryIndex++);
                    oneEntryRecords.clear();
                }
                mrecid = tempMrecid;
            }
            oneEntryRecords.add(record);
        }
        int size = oneEntryRecords.size();
        if (size > 0) {
            oneEntryRecords.sort(comparator);
            sortedRecords.addAll(oneEntryRecords);
            ((Map)oneEntryRecords.get(0)).put("rowspan", size);
            ((Map)oneEntryRecords.get(0)).put("index", entryIndex++);
            oneEntryRecords.clear();
        }
        unSortedRecords.clear();
        return sortedRecords;
    }
}

