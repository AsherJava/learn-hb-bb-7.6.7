/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 */
package com.jiuqi.nr.workflow2.form.reject.entity.dao;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.form.reject.entity.RejectFormRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.model.FRStatusTableModelDefine;
import java.util.List;

public interface IRejectFormRecordDao {
    public int[] insertRows(FRStatusTableModelDefine var1, List<IFormObject> var2, String var3);

    public int updateRows(FRStatusTableModelDefine var1, DimensionCombination var2, List<String> var3, String var4);

    public int deleteRows(FRStatusTableModelDefine var1, DimensionCombination var2);

    public List<RejectFormRecordEntity> queryRows(FRStatusTableModelDefine var1, DimensionCombination var2);
}

