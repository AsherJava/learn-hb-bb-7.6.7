/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 */
package com.jiuqi.nr.workflow2.form.reject.service;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.form.reject.entity.RejectOperateRecordEntity;
import java.util.List;

public interface IFormRejectRunTimeService {
    public void clearFormRejectRecord(String var1, String var2, List<DimensionCombination> var3);

    public void updateFormRejectRecords(String var1, String var2, List<IFormObject> var3, String var4);

    public void insertFormRejectRecords(String var1, String var2, List<IFormObject> var3, String var4);

    public void insertOperateFormRecords(String var1, String var2, List<RejectOperateRecordEntity> var3);
}

