/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 */
package com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service;

import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InputDataAdvanceService {
    public void updateCustomInfoAfterSave(Map<String, String> var1, List<InputDataEO> var2);

    public Map<String, Set<String>> realTimeOffset(List<InputDataEO> var1, DataEntryContext var2, boolean var3);

    public Map<String, Set<String>> realTimeOffsetLimit(List<InputDataEO> var1, DataEntryContext var2, int var3);

    public void formulaCalc(DataEntryContext var1, List<String> var2);

    public Map<String, Set<String>> autoBatchOffset(List<InputDataEO> var1, DataEntryContext var2);

    public List<InputDataEO> inputDataCalAfterMappingRule(Collection<String> var1, DataEntryContext var2);

    public Map<String, Set<String>> doCheckAfterOffset(List<InputDataEO> var1);
}

