/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.service;

import com.jiuqi.nr.data.logic.facade.param.input.BatchDelCheckDesParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import java.util.List;

public interface ICKDChangeNotifier {
    public void beforeDelete(BatchDelCheckDesParam var1);

    public void afterDelete(List<CheckDesObj> var1);

    public void afterInsert(List<CheckDesObj> var1);

    public void afterUpdateOrInsert(List<CheckDesObj> var1);
}

