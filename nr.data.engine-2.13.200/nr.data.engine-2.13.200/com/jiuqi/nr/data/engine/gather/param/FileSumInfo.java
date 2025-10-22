/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.engine.gather.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.engine.gather.param.FieldAndGroupKeyInfo;
import java.util.ArrayList;
import java.util.List;

public class FileSumInfo {
    private DimensionValueSet fromDims;
    List<FieldAndGroupKeyInfo> fieldAndGroupKeyInfos;

    public FileSumInfo() {
    }

    public FileSumInfo(DimensionValueSet fromDims, List<FieldAndGroupKeyInfo> fieldAndGroupKeyInfos) {
        this.fromDims = fromDims;
        this.fieldAndGroupKeyInfos = fieldAndGroupKeyInfos;
    }

    public DimensionValueSet getFromDims() {
        return this.fromDims;
    }

    public void setFromDims(DimensionValueSet fromDims) {
        this.fromDims = fromDims;
    }

    public List<FieldAndGroupKeyInfo> getFieldAndGroupKeyInfos() {
        if (this.fieldAndGroupKeyInfos == null) {
            this.fieldAndGroupKeyInfos = new ArrayList<FieldAndGroupKeyInfo>();
            return this.fieldAndGroupKeyInfos;
        }
        return this.fieldAndGroupKeyInfos;
    }

    public void setFieldAndGroupKeyInfos(List<FieldAndGroupKeyInfo> fieldAndGroupKeyInfos) {
        this.fieldAndGroupKeyInfos = fieldAndGroupKeyInfos;
    }
}

