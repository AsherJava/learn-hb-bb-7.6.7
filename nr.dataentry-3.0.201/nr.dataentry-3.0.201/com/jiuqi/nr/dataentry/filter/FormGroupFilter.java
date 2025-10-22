/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.Filter
 */
package com.jiuqi.nr.dataentry.filter;

import com.jiuqi.np.util.Filter;
import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import java.util.Set;

public class FormGroupFilter
implements Filter<FormGroupData> {
    private Set<String> conditionResult;

    public void setCondition(Set<String> conditionResult) {
        this.conditionResult = conditionResult;
    }

    public boolean accept(FormGroupData formGroup) {
        return this.conditionResult == null || !this.conditionResult.contains(formGroup.getKey());
    }
}

