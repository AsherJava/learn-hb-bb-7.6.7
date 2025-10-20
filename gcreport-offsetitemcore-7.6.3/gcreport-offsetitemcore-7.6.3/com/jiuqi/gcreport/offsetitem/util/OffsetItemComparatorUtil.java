/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.MapUtils
 */
package com.jiuqi.gcreport.offsetitem.util;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OffsetItemComparatorUtil {
    public static void mapSortComparator(List<Map<String, Object>> oneEntryRecords) {
        oneEntryRecords.forEach(recordMap -> {
            if (!recordMap.containsKey("OFFSET_DEBIT")) {
                recordMap.put("OFFSET_DEBIT", ConverterUtils.getAsDouble(recordMap.get("OFFSETDEBIT")));
            }
            if (!recordMap.containsKey("OFFSET_CREDIT")) {
                recordMap.put("OFFSET_CREDIT", ConverterUtils.getAsDouble(recordMap.get("OFFSETCREDIT")));
            }
            recordMap.put("SUBJECTORIENT", ConverterUtils.getAsInteger(recordMap.get("SUBJECTORIENT")));
        });
        oneEntryRecords.sort(OffsetItemComparatorUtil.mapUniversalComparator());
    }

    public static void mapSortComparatorVO(List<GcOffSetVchrItemVO> gcOffSetVchrItemVOList) {
        gcOffSetVchrItemVOList.sort(OffsetItemComparatorUtil.gcMapUniversalComparator());
    }

    public static Comparator<GcOffSetVchrItemVO> gcMapUniversalComparator() {
        Comparator comparator = (record1, record2) -> {
            int result = StringUtils.compareStr((String)record2.getmRecid(), (String)record1.getmRecid());
            if (result == 0) {
                result = NumberUtils.compareInt((Integer)OffsetItemComparatorUtil.getAmtOrient(record2), (Integer)OffsetItemComparatorUtil.getAmtOrient(record1));
            }
            if (result == 0) {
                result = StringUtils.compareStr((String)record1.getSubjectCode(), (String)record2.getSubjectCode());
            }
            if (result == 0) {
                result = StringUtils.compareStr((String)record1.getId(), (String)record2.getId());
            }
            return result;
        };
        return comparator;
    }

    public static Comparator<Map<String, Object>> mapUniversalComparator() {
        Comparator comparator = (record1, record2) -> {
            int result = MapUtils.compareStr((Map)record2, (Map)record1, (Object)"MRECID");
            if (result == 0) {
                result = NumberUtils.compareInt((Integer)OffsetItemComparatorUtil.getAmtOrient(record2), (Integer)OffsetItemComparatorUtil.getAmtOrient(record1));
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

    public static Comparator<GcOffSetVchrItemAdjustEO> eoUniversalComparator() {
        Comparator comparator = (record1, record2) -> {
            int result = StringUtils.compareStr((String)record1.getmRecid(), (String)record2.getmRecid());
            if (result == 0) {
                result = NumberUtils.compareInt((Integer)OffsetItemComparatorUtil.getAmtOrient(record2), (Integer)OffsetItemComparatorUtil.getAmtOrient(record1));
            }
            if (result == 0) {
                result = StringUtils.compareStr((String)record1.getSubjectCode(), (String)record2.getSubjectCode());
            }
            if (result == 0) {
                result = StringUtils.compareStr((String)record1.getId(), (String)record2.getId());
            }
            return result;
        };
        return comparator;
    }

    private static Integer getAmtOrient(GcOffSetVchrItemAdjustEO record) {
        if (record.getOffSetDebit() != null && record.getOffSetDebit() != 0.0) {
            return 1;
        }
        if (record.getOffSetCredit() != null && record.getOffSetCredit() != 0.0) {
            return -1;
        }
        return record.getSubjectOrient() == null ? null : Integer.valueOf(-record.getSubjectOrient().intValue());
    }

    private static Integer getAmtOrient(GcOffSetVchrItemVO record) {
        if (record.getOffSetDebit() != null && record.getOffSetDebit() != 0.0) {
            return 1;
        }
        if (record.getOffSetCredit() != null && record.getOffSetCredit() != 0.0) {
            return -1;
        }
        if (record.getOffSetDebit() == null && record.getOffSetCredit() == null) {
            return null;
        }
        if (record.getOffSetDebit() == null) {
            return OrientEnum.D.getValue();
        }
        return OrientEnum.C.getValue();
    }

    private static Integer getAmtOrient(Map<String, Object> record) {
        Double offsetDebit = (Double)record.get("OFFSET_DEBIT");
        Double offsetCredit = (Double)record.get("OFFSET_CREDIT");
        if (offsetDebit != null && offsetDebit != 0.0) {
            return 1;
        }
        if (offsetCredit != null && offsetCredit != 0.0) {
            return -1;
        }
        Integer subjectOrient = (Integer)record.get("SUBJECTORIENT");
        return subjectOrient == null ? null : Integer.valueOf(-subjectOrient.intValue());
    }
}

