/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.nvwa.datav.parameter.remote.RemoteParameterValue
 */
package com.jiuqi.nr.query.dataset;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.nvwa.datav.parameter.remote.RemoteParameterValue;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamUtils {
    private static final Logger log = LoggerFactory.getLogger(ParamUtils.class);

    public static Date[] getDateRegion(List<RemoteParameterValue> parameterValues, IPeriodAdapter periodAdapter) {
        Date[] region = null;
        String period = ParamUtils.getPeriod(parameterValues);
        try {
            if (StringUtils.isNotEmpty((String)period)) {
                region = periodAdapter.getPeriodDateRegion(period);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return region;
    }

    public static String getPeriod(List<RemoteParameterValue> parameterValues) {
        String period = null;
        block2: for (RemoteParameterValue parameterValue : parameterValues) {
            try {
                List periods;
                if (!parameterValue.getName().equals("DATATIME") || (periods = parameterValue.getValues()).size() <= 0) continue;
                Collections.sort(periods);
                for (int i = periods.size() - 1; i >= 0; --i) {
                    String p = (String)periods.get(i);
                    if (!StringUtils.isNotEmpty((String)p)) continue;
                    period = p;
                    continue block2;
                }
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return period;
    }
}

