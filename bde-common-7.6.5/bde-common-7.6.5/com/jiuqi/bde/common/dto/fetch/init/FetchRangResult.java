/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto.fetch.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FetchRangResult {
    private Map<String, Set<String>> formRegionsMap;
    private Map<String, Set<String>> fromDataLinkMap;
    private List<String> forms;

    public FetchRangResult() {
    }

    public Map<String, Set<String>> getFormRegionsMap() {
        return this.formRegionsMap;
    }

    public void setFormRegionsMap(Map<String, Set<String>> formRegionsMap) {
        this.formRegionsMap = formRegionsMap;
    }

    public Map<String, Set<String>> getFromDataLinkMap() {
        return this.fromDataLinkMap;
    }

    public void setFromDataLinkMap(Map<String, Set<String>> fromDataLinkMap) {
        this.fromDataLinkMap = fromDataLinkMap;
    }

    public List<String> getForms() {
        if (this.forms != null) {
            return this.forms;
        }
        ArrayList<String> forms = new ArrayList<String>();
        forms.addAll(this.formRegionsMap.keySet());
        forms.addAll(this.fromDataLinkMap.keySet());
        this.forms = forms;
        return forms;
    }

    public FetchRangResult(Map<String, Set<String>> formRegionsMap, Map<String, Set<String>> fromDataLinkMap) {
        this.formRegionsMap = formRegionsMap == null ? new HashMap() : formRegionsMap;
        this.fromDataLinkMap = fromDataLinkMap == null ? new HashMap() : fromDataLinkMap;
    }
}

