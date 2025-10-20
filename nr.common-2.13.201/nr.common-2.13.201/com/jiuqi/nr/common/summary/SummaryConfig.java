/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.summary;

import com.jiuqi.nr.common.summary.SummaryField;
import java.io.Serializable;
import java.util.List;

public class SummaryConfig
implements Serializable {
    private static final long serialVersionUID = 2218489049556742496L;
    private String sumFormula;
    private boolean showDetailUnit;
    private boolean sumOriUnitInvolved;
    private List<SummaryField> sumDimensions;

    public List<SummaryField> getSumDimensions() {
        return this.sumDimensions;
    }

    public void setSumDimensions(List<SummaryField> sumDimensions) {
        this.sumDimensions = sumDimensions;
    }

    public String getSumFormula() {
        return this.sumFormula;
    }

    public void setSumFormula(String sumFormula) {
        this.sumFormula = sumFormula;
    }

    public boolean isShowDetailUnit() {
        return this.showDetailUnit;
    }

    public void setShowDetailUnit(boolean showDetailUnit) {
        this.showDetailUnit = showDetailUnit;
    }

    public boolean isSumOriUnitInvolved() {
        return this.sumOriUnitInvolved;
    }

    public void setSumOriUnitInvolved(boolean sumOriUnitInvolved) {
        this.sumOriUnitInvolved = sumOriUnitInvolved;
    }
}

