/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO
 */
package com.jiuqi.gcreport.samecontrol.util;

import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO;
import java.util.Comparator;

public class SameCtrlOffsetItemComparatorUtil {
    public static Comparator<SameCtrlOffSetItemVO> mapUniversalComparator() {
        Comparator comparator = (record1, record2) -> {
            int result = SameCtrlOffsetItemComparatorUtil.compareStr(record2.getmRecid(), record1.getmRecid());
            if (result == 0) {
                result = NumberUtils.compareInt((Integer)record2.getOrient(), (Integer)record1.getOrient());
            }
            if (result == 0) {
                result = SameCtrlOffsetItemComparatorUtil.compareStr(record1.getSubjectCode(), record2.getSubjectCode());
            }
            if (result == 0) {
                result = SameCtrlOffsetItemComparatorUtil.compareStr(record1.getId(), record2.getId());
            }
            return result;
        };
        return comparator;
    }

    private static int compareStr(String str1, String str2) {
        if (str1 == null) {
            return -1;
        }
        if (str2 == null) {
            return 1;
        }
        return str1.compareTo(str2);
    }
}

