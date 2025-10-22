/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.system.check.service;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.List;
import java.util.Map;

public interface IParamCheckEntityUpgrader
extends IEntityUpgrader {
    public List<IPeriodRow> getCustomPeriodDataList(String var1) throws Exception;

    public List<String> getAllUnitKeyForEntity(EntityViewDefine var1, String var2, Map<String, String> var3) throws Exception;
}

