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
import com.jiuqi.nr.workflow2.form.reject.entity.IRejectFormRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.entity.IRejectOperateFormResultSet;
import com.jiuqi.nr.workflow2.form.reject.model.FROperateTableModelDefine;
import com.jiuqi.nr.workflow2.form.reject.model.FRStatusTableModelDefine;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IFormRejectQueryService {
    public FRStatusTableModelDefine getFRStatusTableModelDefine(String var1, String var2);

    public FROperateTableModelDefine getFROperateTableModelDefine(String var1, String var2);

    public List<IRejectFormRecordEntity> queryRejectFormRecordsInUnit(String var1, String var2, DimensionCombination var3);

    public boolean isRejectAllFormsInUnit(String var1, String var2, DimensionCombination var3);

    public List<IRejectFormRecordEntity> queryAllFormRecordsInUnit(String var1, String var2, DimensionCombination var3);

    public Map<IFormObject, IRejectFormRecordEntity> queryRejectFormRecordsMap(String var1, String var2, DimensionCombination var3);

    public Map<IFormObject, IRejectFormRecordEntity> queryRejectFormRecordsMap(String var1, String var2, Collection<DimensionCombination> var3);

    public List<IRejectOperateFormResultSet> queryUnitOperateForms(String var1, String var2, DimensionCombination var3);
}

