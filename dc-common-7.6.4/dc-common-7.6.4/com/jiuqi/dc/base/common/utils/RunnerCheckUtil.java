/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.base.common.utils;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.data.AcctPeriodInfo;
import com.jiuqi.dc.base.common.data.PeriodType;
import com.jiuqi.dc.base.common.data.RunnerPeriodParamVO;
import com.jiuqi.dc.base.common.utils.Pair;
import java.util.Calendar;

public class RunnerCheckUtil {
    public static Pair<String, AcctPeriodInfo> checkPeriodParam(RunnerPeriodParamVO runnerParam) {
        if (runnerParam == null) {
            String log = "\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff1a\u9ad8\u7ea7\u53c2\u6570\u8f6c\u6362json\u5931\u8d25";
            return new Pair<String, Object>(log, null);
        }
        if (StringUtils.isEmpty((String)runnerParam.getPeriodType())) {
            String log = "\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff1a\u5468\u671f\u7c7b\u578b\u53c2\u6570\u4e3a\u7a7a\uff0c\u6267\u884c\u5931\u8d25";
            return new Pair<String, Object>(log, null);
        }
        int year = 1970;
        int month = 0;
        Calendar calendar = Calendar.getInstance();
        switch (PeriodType.valueOf(runnerParam.getPeriodType())) {
            case CURRENTPERIOD: {
                year = calendar.get(1);
                month = calendar.get(2) + 1;
                break;
            }
            case PREVIOUSPERIOD: {
                year = calendar.get(1);
                month = calendar.get(2);
                if (month != 0) break;
                --year;
                month = 12;
                break;
            }
            case SELECTPERIOD: {
                if (null == runnerParam.getAcctYear()) {
                    String log = "\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff1a\u5468\u671f\u7c7b\u578b\u53c2\u6570\u4e3a\u6307\u5b9a\u671f\uff0c\u4f46\u662f\u6ca1\u6709\u9009\u62e9\u5e74\u4efd";
                    return new Pair<String, Object>(log, null);
                }
                if (null == runnerParam.getAcctPeriod()) {
                    String log = "\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff1a\u5468\u671f\u7c7b\u578b\u53c2\u6570\u4e3a\u6307\u5b9a\u671f\uff0c\u4f46\u662f\u6ca1\u6709\u9009\u62e9\u6708\u4efd";
                    return new Pair<String, Object>(log, null);
                }
                year = runnerParam.getAcctYear();
                month = runnerParam.getAcctPeriod();
                break;
            }
            default: {
                String log = "\u4efb\u52a1\u6267\u884c\u5931\u8d25\uff1a\u5468\u671f\u7c7b\u578b\u53c2\u6570\u5f02\u5e38";
                return new Pair<String, Object>(log, null);
            }
        }
        return new Pair<Object, AcctPeriodInfo>(null, new AcctPeriodInfo(year, month));
    }
}

