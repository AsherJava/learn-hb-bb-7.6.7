/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.data.logic.internal.dataup;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.definition.internal.BeanUtil;
import javax.sql.DataSource;

public class ReviseDesRECIDExecutor
implements CustomClassExecutor {
    private static final ICheckErrorDescriptionService desService = (ICheckErrorDescriptionService)BeanUtil.getBean(ICheckErrorDescriptionService.class);

    public void execute(DataSource dataSource) throws Exception {
        desService.reviseCheckDesKey();
    }
}

