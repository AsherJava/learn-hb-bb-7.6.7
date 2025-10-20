/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.bde.bizmodel.impl.model.callback;

import com.jiuqi.bde.bizmodel.impl.model.callback.service.BizModelUpdateService;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import javax.sql.DataSource;

public class BizModelDataUpdate
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        BizModelUpdateService bizModelUpdateService = (BizModelUpdateService)ApplicationContextRegister.getBean(BizModelUpdateService.class);
        if (bizModelUpdateService == null) {
            return;
        }
        bizModelUpdateService.bizModelUpdate();
    }
}

