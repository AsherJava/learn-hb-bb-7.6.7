/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.common.service.dto;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.common.service.dto.CompletionDimFinder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompletionDim {
    private DimensionValueSet fixedCompletionDims;
    private List<String> dynamicsCompletionDims;
    private CompletionDimFinder finder;
    private Set<String> completionDims = new HashSet<String>();

    public List<String> getDynamicsCompletionDims() {
        return this.dynamicsCompletionDims;
    }

    public void setDynamicsCompletionDims(List<String> dynamicsCompletionDims) {
        this.completionDims.addAll(dynamicsCompletionDims);
        this.dynamicsCompletionDims = dynamicsCompletionDims;
    }

    public CompletionDimFinder getFinder() {
        return this.finder;
    }

    public void setFinder(CompletionDimFinder finder) {
        this.finder = finder;
    }

    public DimensionValueSet getFixedCompletionDims() {
        return this.fixedCompletionDims;
    }

    public void setFixedCompletionDims(DimensionValueSet fixedCompletionDims) {
        this.fixedCompletionDims = fixedCompletionDims;
        for (int i = 0; i < fixedCompletionDims.getDimensionSet().size(); ++i) {
            this.completionDims.add(fixedCompletionDims.getDimensionSet().get(i));
        }
    }

    public Set<String> getCompletionDims() {
        return this.completionDims;
    }

    public void setCompletionDims(Set<String> completionDims) {
        this.completionDims = completionDims;
    }

    public boolean isCompletionDim() {
        return this.fixedCompletionDims != null || this.dynamicsCompletionDims != null;
    }
}

