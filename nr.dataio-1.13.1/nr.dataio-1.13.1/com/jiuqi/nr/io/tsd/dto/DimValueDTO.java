/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.io.tsd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.io.tsd.dto.DimValue;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DimValueDTO {
    private String dimName;
    private String dimCode;
    private String dimTitle;
    private boolean dw1v1;
    private List<DimValue> dimValues;

    public String getDimName() {
        return this.dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    public String getDimTitle() {
        return this.dimTitle;
    }

    public void setDimTitle(String dimTitle) {
        this.dimTitle = dimTitle;
    }

    public List<DimValue> getDimValues() {
        return this.dimValues;
    }

    public void setDimValues(List<DimValue> dimValues) {
        this.dimValues = dimValues;
    }

    public boolean isDw1v1() {
        return this.dw1v1;
    }

    public void setDw1v1(boolean dw1v1) {
        this.dw1v1 = dw1v1;
    }

    public String getDimCode() {
        return this.dimCode;
    }

    public void setDimCode(String dimCode) {
        this.dimCode = dimCode;
    }
}

