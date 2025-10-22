/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nvwa.framework.nros.service.IRouteService
 */
package com.jiuqi.nr.period.internal.upgrade;

import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nvwa.framework.nros.service.IRouteService;
import javax.sql.DataSource;

public class UpgradeMenu
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        IRouteService bean = (IRouteService)BeanUtils.getBean(IRouteService.class);
        bean.updateAppName("periodManage", "periodManage", "@nr", "@nvwa");
    }
}

