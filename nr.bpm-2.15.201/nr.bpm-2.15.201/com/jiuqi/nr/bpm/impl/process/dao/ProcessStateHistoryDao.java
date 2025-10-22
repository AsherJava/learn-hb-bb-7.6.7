/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.bpm.impl.process.dao;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.impl.process.dao.BatchCompleteParam;
import com.jiuqi.nr.bpm.impl.process.dao.UploadProcessInstanceDto;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.List;

public interface ProcessStateHistoryDao {
    public void batchUpdateState(BatchCompleteParam var1);

    public void updateState(BusinessKey var1, String var2, String var3, boolean var4, String var5);

    public void updateState(BusinessKey var1, String var2, String var3, boolean var4, IConditionCache var5, String var6);

    public UploadStateNew queryUploadState(DimensionValueSet var1, String var2, FormSchemeDefine var3);

    public void deleteUploadStateAndRecord(BusinessKey var1);

    public List<UploadProcessInstanceDto> queryUploadInstance(String var1, String var2);

    public void updateUnitState(String var1, DimensionValueSet var2, String var3, String var4, boolean var5);
}

