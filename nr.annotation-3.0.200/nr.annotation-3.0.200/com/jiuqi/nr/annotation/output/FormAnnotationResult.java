/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.output;

import com.jiuqi.nr.annotation.message.RegionAnnotationResult;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FormAnnotationResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String formKey;
    private Map<String, RegionAnnotationResult> regions = new HashMap<String, RegionAnnotationResult>();

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public Map<String, RegionAnnotationResult> getRegions() {
        return this.regions;
    }

    public void setRegions(Map<String, RegionAnnotationResult> regions) {
        this.regions = regions;
    }
}

