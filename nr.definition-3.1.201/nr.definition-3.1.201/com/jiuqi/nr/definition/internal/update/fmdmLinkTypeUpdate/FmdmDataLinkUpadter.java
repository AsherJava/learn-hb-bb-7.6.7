/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.definition.internal.update.fmdmLinkTypeUpdate;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.update.fmdmLinkTypeUpdate.FmdmDataLinkTypeService;
import javax.sql.DataSource;

public class FmdmDataLinkUpadter
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        FmdmDataLinkTypeService fmdmLinkApi = BeanUtil.getBean(FmdmDataLinkTypeService.class);
        fmdmLinkApi.update();
    }
}

