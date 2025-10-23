/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.workflow2.service.helper;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.service.execute.runtime.IExecuteTimeSetting;

public interface IProcessExecuteTimeHelper {
    public IExecuteTimeSetting getExecuteTimeSetting(FormSchemeDefine var1, DimensionCombination var2);
}

