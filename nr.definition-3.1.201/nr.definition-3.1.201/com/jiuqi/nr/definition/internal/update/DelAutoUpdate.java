/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.definition.internal.update;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.update.DelViewApi;
import javax.sql.DataSource;

public class DelAutoUpdate
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        DelViewApi delViewApi = BeanUtil.getBean(DelViewApi.class);
        delViewApi.update(true);
    }
}

