/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.workflow2.form.reject.entity.dao;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.form.reject.entity.IRejectOperateRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.entity.RejectOperateRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.model.FROperateTableModelDefine;
import java.util.List;

public interface IRejectOperateRecordDao {
    public int[] insertRows(FROperateTableModelDefine var1, List<RejectOperateRecordEntity> var2);

    public List<IRejectOperateRecordEntity> queryRows(FROperateTableModelDefine var1, DimensionCombination var2);
}

