/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.fmlcheck.vo;

import com.jiuqi.nr.datacheck.fmlcheck.vo.EntityDataVO;
import java.io.Serializable;
import java.util.List;

public class DimensionVO
implements Serializable {
    private static final long serialVersionUID = -9013301157822661073L;
    private String dimensionName;
    private String dimensionTitle;
    private List<EntityDataVO> dimensionValue;

    public DimensionVO(String dimensionName, String dimensionTitle, List<EntityDataVO> dimensionValue) {
        this.dimensionName = dimensionName;
        this.dimensionTitle = dimensionTitle;
        this.dimensionValue = dimensionValue;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getDimensionTitle() {
        return this.dimensionTitle;
    }

    public void setDimensionTitle(String dimensionTitle) {
        this.dimensionTitle = dimensionTitle;
    }

    public List<EntityDataVO> getDimensionValue() {
        return this.dimensionValue;
    }

    public void setDimensionValue(List<EntityDataVO> dimensionValue) {
        this.dimensionValue = dimensionValue;
    }
}

