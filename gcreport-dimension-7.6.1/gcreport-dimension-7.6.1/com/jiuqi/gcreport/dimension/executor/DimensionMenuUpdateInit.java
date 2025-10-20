/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.gcreport.dimension.executor;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.dimension.update.DimensionMenuUpdateService;
import com.jiuqi.np.sql.CustomClassExecutor;
import javax.sql.DataSource;

public class DimensionMenuUpdateInit
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        ((DimensionMenuUpdateService)SpringContextUtils.getBean(DimensionMenuUpdateService.class)).updateMenu();
    }
}

