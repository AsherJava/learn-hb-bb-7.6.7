/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.dataflow.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean;
import java.util.List;
import java.util.Map;

public interface IReadOnlyService {
    public ReadOnlyBean readOnly(DataEntryParam var1);

    public Map<DimensionValueSet, Map<String, Boolean>> batchReadOnlyMap(DataEntryParam var1);

    public List<ReadOnlyBean> batchReadOnly(DataEntryParam var1);

    public ReadOnlyBean readOnly(DataEntryParam var1, UploadStateNew var2);
}

