/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.fetch.client.dto;

import java.util.HashMap;
import java.util.Map;

public class FetchFormulaDTO {
    private String formula;
    private final Map<String, Object> envParamMap = new HashMap<String, Object>();
    private Map<String, Object> floatRowMap = new HashMap<String, Object>();
    private Map<String, Object> fetchResultMap = new HashMap<String, Object>();

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Map<String, Object> getFloatRowMap() {
        return this.floatRowMap;
    }

    public void setFloatRowMap(Map<String, Object> floatRowMap) {
        this.floatRowMap = floatRowMap;
    }

    public Map<String, Object> getFetchResultMap() {
        return this.fetchResultMap;
    }

    public void setFetchResultMap(Map<String, Object> fetchResultMap) {
        this.fetchResultMap = fetchResultMap;
    }

    public Map<String, Object> getEnvParamMap() {
        return this.envParamMap;
    }

    public String toString() {
        return "FetchFormulaDTO [formula=" + this.formula + ", envParamMap=" + this.envParamMap + ", floatRowMap=" + this.floatRowMap + ", fetchResultMap=" + this.fetchResultMap + "]";
    }
}

