/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JTableModel
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.budget.init;

import com.jiuqi.budget.init.BudInitialization;
import com.jiuqi.va.mapper.common.JTableModel;
import javax.validation.constraints.NotNull;
import org.springframework.core.Ordered;

@BudInitialization
public abstract class BaseStorage
implements Ordered {
    @NotNull
    public abstract JTableModel createTable();

    public boolean beforeCreate() {
        return true;
    }

    public void afterCreate() {
    }

    @Override
    public int getOrder() {
        return 999;
    }
}

