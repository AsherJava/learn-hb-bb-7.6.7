/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.facade;

import com.jiuqi.nr.analysisreport.facade.DimensionObj;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DimensionConfigObj
implements Serializable {
    private static final long serialVersionUID = 6992274640636195236L;
    private List<DimensionObj> srcDims = new ArrayList<DimensionObj>();

    public List<DimensionObj> getSrcDims() {
        return this.srcDims;
    }

    public void setDestDims(List<DimensionObj> srcDims) {
        this.srcDims = srcDims;
    }
}

