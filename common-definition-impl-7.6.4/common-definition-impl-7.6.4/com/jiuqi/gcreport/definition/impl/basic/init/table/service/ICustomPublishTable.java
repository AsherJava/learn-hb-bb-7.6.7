/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.service;

import com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import java.util.List;

public interface ICustomPublishTable {
    public List<DesignColumnModelDefine> customColumnList(ShowModelDTO var1);
}

