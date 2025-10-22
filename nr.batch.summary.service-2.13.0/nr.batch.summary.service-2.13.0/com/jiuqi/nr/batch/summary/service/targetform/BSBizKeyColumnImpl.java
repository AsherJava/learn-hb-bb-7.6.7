/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.batch.summary.service.targetform;

import com.jiuqi.nr.batch.summary.service.targetform.BSBizKeyColumn;
import com.jiuqi.nr.batch.summary.service.targetform.BSTableColumnImpl;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class BSBizKeyColumnImpl
extends BSTableColumnImpl
implements BSBizKeyColumn {
    private Object defaultValue;
    private boolean isCorporate;

    public BSBizKeyColumnImpl(ColumnModelDefine columnModel) {
        super(columnModel);
    }

    @Override
    public boolean isCorporate() {
        return this.isCorporate;
    }

    @Override
    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setIsCorporate(boolean isCorporate) {
        this.isCorporate = isCorporate;
    }
}

