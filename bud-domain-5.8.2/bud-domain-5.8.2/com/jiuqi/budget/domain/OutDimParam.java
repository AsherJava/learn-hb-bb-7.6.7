/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.domain;

import com.jiuqi.budget.domain.OutDimSelectType;
import java.util.List;

public class OutDimParam {
    private String dimCode;
    private OutDimSelectType outDimSelectType;
    private List<String> dimVals;

    public String getDimCode() {
        return this.dimCode;
    }

    public void setDimCode(String dimCode) {
        this.dimCode = dimCode;
    }

    public OutDimSelectType getOutDimSelectType() {
        return this.outDimSelectType;
    }

    public void setOutDimSelectType(OutDimSelectType outDimSelectType) {
        this.outDimSelectType = outDimSelectType;
    }

    public List<String> getDimVals() {
        return this.dimVals;
    }

    public void setDimVals(List<String> dimVals) {
        this.dimVals = dimVals;
    }
}

