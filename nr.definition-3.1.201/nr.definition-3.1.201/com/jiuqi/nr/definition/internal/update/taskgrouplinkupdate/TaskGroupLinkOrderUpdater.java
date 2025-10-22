/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.definition.internal.update.taskgrouplinkupdate;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.update.taskgrouplinkupdate.TaskGroupLinkOrderService;
import javax.sql.DataSource;

public class TaskGroupLinkOrderUpdater
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        TaskGroupLinkOrderService taskGroupLink = BeanUtil.getBean(TaskGroupLinkOrderService.class);
        taskGroupLink.update();
    }
}

