/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 */
package com.jiuqi.nr.data.gather.lock.event;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.data.gather.lock.event.CreateLockTableUtil;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import java.util.List;
import javax.sql.DataSource;

public class HistoryDataSchemeUpdate
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
        CreateLockTableUtil createLockTableUtil = (CreateLockTableUtil)SpringBeanUtils.getBean(CreateLockTableUtil.class);
        List dataSchemes = dataSchemeService.getAllDataScheme();
        for (DataScheme dataScheme : dataSchemes) {
            createLockTableUtil.initLockTableDeploy(dataScheme.getKey(), dataScheme, false);
        }
    }
}

