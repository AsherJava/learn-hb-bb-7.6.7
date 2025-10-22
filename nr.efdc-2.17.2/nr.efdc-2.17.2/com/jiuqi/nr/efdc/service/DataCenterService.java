/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.efdc.service;

import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.efdc.bean.EntityDataObject;
import com.jiuqi.nr.efdc.internal.pojo.AssistEntitys;
import com.jiuqi.nr.efdc.internal.pojo.EntityQueryVO;
import com.jiuqi.nr.efdc.internal.utils.NrResult;
import java.util.List;

public interface DataCenterService {
    public NrResult getAllTask();

    public List<FormulaSchemeDefine> getAllFormulaSchemeByFromScheme(String var1);

    public List<FormulaSchemeDefine> getAllRPTFormulaSchemeDefinesByFormScheme(String var1);

    public NrResult getEntity(String var1);

    public List<EntityDataObject> getEntityData(String var1);

    public List<AssistEntitys> getAssistDimData(EntityQueryVO var1);

    public PeriodWrapper transformToPeriod(String var1);

    public List<EntityDataObject> getChildrenData(String var1, String var2);

    public List<String> getSelectedEntity(String var1);
}

