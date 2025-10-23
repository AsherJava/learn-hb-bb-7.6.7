/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.jiuqi.nr.datascheme.web.facade.DataSchemeVO;
import java.util.List;

public class DataSchemeCheckPropVO
extends DataSchemeVO {
    private List<String> properties;

    public List<String> getProperties() {
        return this.properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }
}

