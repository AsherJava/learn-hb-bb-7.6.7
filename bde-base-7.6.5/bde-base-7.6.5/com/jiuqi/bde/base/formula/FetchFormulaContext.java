/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.reportparser.IReportContext
 */
package com.jiuqi.bde.base.formula;

import com.jiuqi.bi.syntax.reportparser.IReportContext;
import java.util.HashMap;
import java.util.Map;

public class FetchFormulaContext
implements IReportContext {
    private final Map<String, Object> envParamMap;
    private Map<String, Object> floatRowMap = new HashMap<String, Object>();
    private Map<String, Object> fetchResultMap = new HashMap<String, Object>();

    public FetchFormulaContext(Map<String, Object> envParamMap) {
        this.envParamMap = envParamMap;
    }

    public Map<String, Object> getEnvParamMap() {
        return this.envParamMap;
    }

    public Map<String, Object> getFetchResultMap() {
        return this.fetchResultMap;
    }

    public void setFetchResultMap(Map<String, Object> fetchResultMap) {
        this.fetchResultMap = fetchResultMap;
    }

    public Map<String, Object> getFloatRowMap() {
        return this.floatRowMap;
    }

    public void setFloatRowMap(Map<String, Object> floatRowMap) {
        this.floatRowMap = floatRowMap;
    }

    public String toString() {
        return "FetchFormulaContext [envParamMap=" + this.envParamMap + ", floatRowMap=" + this.floatRowMap + ", fetchResultMap=" + this.fetchResultMap + "]";
    }
}

