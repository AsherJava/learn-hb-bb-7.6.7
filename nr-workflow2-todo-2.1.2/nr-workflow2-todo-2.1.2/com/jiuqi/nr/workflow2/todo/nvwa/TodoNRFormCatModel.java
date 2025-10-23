/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.todocat.model.TodoCatModel
 */
package com.jiuqi.nr.workflow2.todo.nvwa;

import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.todocat.model.TodoCatModel;
import org.springframework.stereotype.Component;

@Component
public class TodoNRFormCatModel
implements TodoCatModel {
    public String getName() {
        return "NR-WORKFLOW-TODO";
    }

    public String getModuleName() {
        return "NR";
    }

    public int count(TenantDO tenantDO) {
        return 20;
    }
}

