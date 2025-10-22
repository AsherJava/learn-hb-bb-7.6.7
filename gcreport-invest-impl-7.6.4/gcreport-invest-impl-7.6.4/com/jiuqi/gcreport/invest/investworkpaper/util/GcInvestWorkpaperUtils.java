/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil
 */
package com.jiuqi.gcreport.invest.investworkpaper.util;

import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class GcInvestWorkpaperUtils {
    public static final Logger logger = LoggerFactory.getLogger(GcInvestWorkpaperUtils.class);

    public static List<Map<String, Object>> setRowSpanAndSort(List<Map<String, Object>> unSortedRecords) {
        if (CollectionUtils.isEmpty(unSortedRecords)) {
            return unSortedRecords;
        }
        ArrayList<Map<String, Object>> sortedRecords = new ArrayList<Map<String, Object>>();
        ArrayList<Map<String, Object>> oneEntryRecords = new ArrayList<Map<String, Object>>();
        String mrecid = null;
        Comparator comparator = (record1, record2) -> {
            int result = MapUtils.compareInt((Map)record2, (Map)record1, (Object)"ORIENT");
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
                    OffsetItemComparatorUtil.mapSortComparator(oneEntryRecords);
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
            OffsetItemComparatorUtil.mapSortComparator(oneEntryRecords);
            sortedRecords.addAll(oneEntryRecords);
            ((Map)oneEntryRecords.get(0)).put("rowspan", size);
            ((Map)oneEntryRecords.get(0)).put("index", entryIndex++);
            oneEntryRecords.clear();
        }
        unSortedRecords.clear();
        return sortedRecords;
    }

    public static void pageOffsetByMrecids(int pageNum, int pageSize, Set<String> mRecids) {
        if (pageNum > 0 && pageSize > 0) {
            List tempmRecids = mRecids.stream().sorted(Comparator.comparing(mRecid -> mRecid)).collect(Collectors.toList());
            int mRecidsSize = tempmRecids.size();
            int begin = (pageNum - 1) * pageSize <= mRecidsSize ? (pageNum - 1) * pageSize : 0;
            int range = pageNum * pageSize > mRecidsSize ? mRecidsSize : pageNum * pageSize;
            tempmRecids = tempmRecids.subList(begin, range);
            mRecids.clear();
            mRecids.addAll(tempmRecids);
        }
    }
}

