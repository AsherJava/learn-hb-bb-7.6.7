/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.model;

import com.jiuqi.nr.dataentry.model.DimensionObj;
import java.io.Serializable;
import java.util.List;

public class BatchCheckObj
implements Serializable {
    private List<DimensionObj> dimensionObjList;
    private List<String> allowAddErrDesCheckTypeList;

    public List<DimensionObj> getDimensionObjList() {
        return this.dimensionObjList;
    }

    public void setDimensionObjList(List<DimensionObj> dimensionObjList) {
        this.dimensionObjList = dimensionObjList;
    }

    public List<String> getAllowAddErrDesCheckTypeList() {
        return this.allowAddErrDesCheckTypeList;
    }

    public void setAllowAddErrDesCheckTypeList(List<String> allowAddErrDesCheckTypeList) {
        this.allowAddErrDesCheckTypeList = allowAddErrDesCheckTypeList;
    }
}

