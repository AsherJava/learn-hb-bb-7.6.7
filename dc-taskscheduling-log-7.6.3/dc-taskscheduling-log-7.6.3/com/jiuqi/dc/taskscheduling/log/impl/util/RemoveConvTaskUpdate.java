/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.dc.taskscheduling.log.impl.util;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import javax.sql.DataSource;

public class RemoveConvTaskUpdate
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
        jdbcTemplate.update("DELETE FROM DC_TASKMANAGE WHERE TASKNAME IN ('AssBalanceConvert', 'CfBalanceConvert', 'CfBalanCeconvDiffCal', 'CONVERSIONDIFFHANDLE', 'AGINGCONVDIFFHANDLE')");
    }
}

