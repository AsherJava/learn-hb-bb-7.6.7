/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.snapshot.output;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.snapshot.output.ComparisonResult;
import java.util.List;

public class BatchComparisonResult {
    private DimensionCombination dim;
    private List<ComparisonResult> results;

    public DimensionCombination getDim() {
        return this.dim;
    }

    public void setDim(DimensionCombination dim) {
        this.dim = dim;
    }

    public List<ComparisonResult> getResults() {
        return this.results;
    }

    public void setResults(List<ComparisonResult> results) {
        this.results = results;
    }
}

