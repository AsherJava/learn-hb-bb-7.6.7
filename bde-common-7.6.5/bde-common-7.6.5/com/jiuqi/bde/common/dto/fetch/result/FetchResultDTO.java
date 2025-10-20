/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto.fetch.result;

import com.jiuqi.bde.common.dto.fetch.result.FloatRegionResultDTO;
import java.util.Map;

public class FetchResultDTO {
    private Map<String, Object> fixedResults;
    private FloatRegionResultDTO floatResults;

    public Map<String, Object> getFixedResults() {
        return this.fixedResults;
    }

    public void setFixedResults(Map<String, Object> fixedResults) {
        this.fixedResults = fixedResults;
    }

    public FloatRegionResultDTO getFloatResults() {
        return this.floatResults;
    }

    public void setFloatResults(FloatRegionResultDTO floatResults) {
        this.floatResults = floatResults;
    }
}

