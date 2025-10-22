/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.web.response;

import com.jiuqi.nr.data.estimation.web.response.EstimationFormulaSchemeInfo;
import java.util.List;

public class EstimationFormulaSchemeSet {
    private List<String> checkFormulaSchemes;
    private List<EstimationFormulaSchemeInfo> allFormulaSchemes;

    public List<String> getCheckFormulaSchemes() {
        return this.checkFormulaSchemes;
    }

    public void setCheckFormulaSchemes(List<String> checkFormulaSchemes) {
        this.checkFormulaSchemes = checkFormulaSchemes;
    }

    public List<EstimationFormulaSchemeInfo> getAllFormulaSchemes() {
        return this.allFormulaSchemes;
    }

    public void setAllFormulaSchemes(List<EstimationFormulaSchemeInfo> allFormulaSchemes) {
        this.allFormulaSchemes = allFormulaSchemes;
    }
}

