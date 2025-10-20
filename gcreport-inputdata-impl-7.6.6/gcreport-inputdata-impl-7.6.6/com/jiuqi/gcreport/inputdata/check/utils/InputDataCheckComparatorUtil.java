/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.common.util.MapUtils
 */
package com.jiuqi.gcreport.inputdata.check.utils;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class InputDataCheckComparatorUtil {
    public static List<Map<String, Object>> getCompareSumRecordIds(List<Map<String, Object>> data) {
        return data.stream().sorted(new Comparator<Map<String, Object>>(){

            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return String.valueOf(o1.get("RECORDKEY")).compareTo(String.valueOf(o2.get("RECORDKEY")));
            }
        }).collect(Collectors.toList());
    }

    public static Comparator<Map<String, Object>> mapUniversalComparator() {
        Comparator comparator = (record1, record2) -> {
            int result = MapUtils.compareStr((Map)record2, (Map)record1, (Object)"RECORDKEY");
            if (result == 0) {
                result = NumberUtils.compareInt((Integer)InputDataCheckComparatorUtil.getAmtOrient(record2), (Integer)InputDataCheckComparatorUtil.getAmtOrient(record1));
            }
            if (result == 0) {
                result = MapUtils.compareStr((Map)record1, (Map)record2, (Object)"SUBJECTCODE");
            }
            if (result == 0) {
                result = MapUtils.compareStr((Map)record1, (Map)record2, (Object)"ID");
            }
            return result;
        };
        return comparator;
    }

    public static List<Map<String, Object>> setRowSpanAndSort(List<Map<String, Object>> unSortedRecords, Map<String, Object> filterCondition) {
        if (CollectionUtils.isEmpty(unSortedRecords)) {
            return unSortedRecords;
        }
        ArrayList<Map<String, Object>> sortedRecords = new ArrayList<Map<String, Object>>();
        ArrayList<Map<String, Object>> oneEntryRecords = new ArrayList<Map<String, Object>>();
        String mrecid = null;
        int entryIndex = 1;
        for (Map<String, Object> record : unSortedRecords) {
            String tempMrecid = (String)record.get("RECORDKEY");
            if (null == mrecid || !mrecid.equals(tempMrecid)) {
                int size = oneEntryRecords.size();
                if (size > 0) {
                    oneEntryRecords.sort(InputDataCheckComparatorUtil.mapUniversalComparator());
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
            oneEntryRecords.sort(InputDataCheckComparatorUtil.mapUniversalComparator());
            sortedRecords.addAll(oneEntryRecords);
            ((Map)oneEntryRecords.get(0)).put("rowspan", size);
            ((Map)oneEntryRecords.get(0)).put("index", entryIndex++);
            oneEntryRecords.clear();
        }
        return InputDataCheckComparatorUtil.filterCheckDiffAmt(sortedRecords, filterCondition);
    }

    private static Integer getAmtOrient(Map<String, Object> record) {
        Integer subjectOrient = (Integer)record.get("DC");
        return subjectOrient == null ? null : subjectOrient;
    }

    private static List<Map<String, Object>> filterCheckDiffAmt(List<Map<String, Object>> checkDatas, Map<String, Object> filterCondition) {
        Object checkDiffAmtInterval;
        List doubleValues;
        if (filterCondition.isEmpty() || !filterCondition.keySet().contains("checkDiffAmtInterval")) {
            return checkDatas;
        }
        if (CollectionUtils.isEmpty(checkDatas)) {
            CollectionUtils.newArrayList();
        }
        if (CollectionUtils.isEmpty((Collection)(doubleValues = (List)(checkDiffAmtInterval = filterCondition.get("checkDiffAmtInterval"))))) {
            return checkDatas;
        }
        HashMap<String, List> inputDataCheckItemGroupByRecordKey = new HashMap<String, List>();
        for (Map<String, Object> inputData : checkDatas) {
            String recordKey = String.valueOf(inputData.get("RECORDKEY"));
            List inputDaTaItems = inputDataCheckItemGroupByRecordKey.computeIfAbsent(recordKey, item -> new ArrayList());
            inputDaTaItems.add(inputData);
        }
        ArrayList<Map<String, Object>> filterInputDataItems = new ArrayList<Map<String, Object>>();
        int index = 1;
        for (String recordKey : inputDataCheckItemGroupByRecordKey.keySet()) {
            Object biggerValue;
            Double smallerValue;
            List inputDataItems = (List)inputDataCheckItemGroupByRecordKey.get(recordKey);
            BigDecimal sumCredit = new BigDecimal(0);
            BigDecimal sumDebit = new BigDecimal(0);
            for (Map item2 : inputDataItems) {
                BigDecimal creditAmt = new BigDecimal(item2.get("CHECKCREDIT").toString());
                BigDecimal debitAmt = new BigDecimal(item2.get("CHECKDEBIT").toString());
                sumCredit = sumCredit.add(creditAmt);
                sumDebit = sumDebit.add(debitAmt);
            }
            BigDecimal diffCheckAmt = sumCredit.subtract(sumDebit).abs();
            boolean isDiffAmtFilter = false;
            Double d = smallerValue = Objects.nonNull(doubleValues.get(0)) ? doubleValues.get(0) : Double.valueOf(0.0);
            if (smallerValue instanceof Number) {
                BigDecimal smallDiffAmt = new BigDecimal(((Number)smallerValue).doubleValue());
                boolean bl = isDiffAmtFilter = diffCheckAmt.compareTo(smallDiffAmt) >= 0;
            }
            if (isDiffAmtFilter && doubleValues.size() > 1 && Objects.nonNull(biggerValue = doubleValues.get(1)) && biggerValue instanceof Number) {
                BigDecimal biggerDiffAmt = new BigDecimal(((Number)biggerValue).doubleValue());
                boolean bl = isDiffAmtFilter = diffCheckAmt.compareTo(biggerDiffAmt) <= 0;
            }
            if (!isDiffAmtFilter) continue;
            int finalIndex = index++;
            inputDataItems.forEach(item -> {
                if (Objects.nonNull(item.get("index"))) {
                    item.put("index", finalIndex);
                }
            });
            filterInputDataItems.addAll(inputDataItems);
        }
        return filterInputDataItems;
    }
}

