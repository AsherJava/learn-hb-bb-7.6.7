/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.bde.plugin.ebs_r12.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class EbsR12FetchUtil {
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private BaseDataRefDefineService baseDataDefineService;
    private static EbsR12FetchUtil fetchUtil;
    public static final String ORG_INIT_TABLE = "HDPI_COMPANY";
    public static final String KM_INIT_TABLE = "HDPI_ACCOUNTING";

    @PostConstruct
    public void init() {
        fetchUtil = this;
        EbsR12FetchUtil.fetchUtil.dataSourceService = this.dataSourceService;
        EbsR12FetchUtil.fetchUtil.baseDataDefineService = this.baseDataDefineService;
    }

    private static String acquirePeriodField(int year, String[] periods) {
        StringBuilder periodCondition = new StringBuilder();
        for (String period : periods) {
            periodCondition.append(String.format("'%1$s-%2$s',", year, period));
        }
        return StringUtils.isNotEmpty((String)periodCondition.toString()) ? periodCondition.substring(0, periodCondition.length() - 1) : periodCondition.toString();
    }

    public static String formatPeriod(int year, int period) {
        return String.format("%1$s-%2$s", year, period > 9 ? period + "" : "0" + period);
    }

    public static String buildAdjustmentPeriod(int year) {
        String[] specialPeriod = new String[]{"12A", "12B", "06A", "06B"};
        return EbsR12FetchUtil.acquirePeriodField(year, specialPeriod);
    }
}

