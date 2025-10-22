/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.internal.service.util.obj;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class DWSplit {
    private List<String> defaultDW = new ArrayList<String>();
    private List<String> customDW = new ArrayList<String>();

    public List<String> getDefaultDW() {
        return this.defaultDW;
    }

    public void setDefaultDW(List<String> defaultDW) {
        this.defaultDW = defaultDW;
    }

    public List<String> getCustomDW() {
        return this.customDW;
    }

    public void setCustomDW(List<String> customDW) {
        this.customDW = customDW;
    }
}

