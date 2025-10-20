/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 */
package com.jiuqi.gcreport.dimension.internal.service;

import com.jiuqi.gcreport.dimension.dto.DimensionDTO;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;

public interface DimensionCustomColumnModelService {
    public void postProcessColumnModel(DesignColumnModelDefine var1, DimensionDTO var2);

    public boolean match(DimensionDTO var1, String var2);
}

