/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.param;

import com.jiuqi.nr.designer.web.rest.vo.ReverseDataFieldVO;
import java.util.List;

public class ReverseBatchCheckPM {
    private String dataSchemeKey;
    private List<ReverseDataFieldVO> dataFields;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public List<ReverseDataFieldVO> getDataFields() {
        return this.dataFields;
    }

    public void setDataFields(List<ReverseDataFieldVO> dataFields) {
        this.dataFields = dataFields;
    }
}

