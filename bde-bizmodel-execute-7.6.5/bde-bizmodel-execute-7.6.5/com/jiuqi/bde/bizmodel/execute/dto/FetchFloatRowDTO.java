/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.base.intf.FloatDefineEO
 */
package com.jiuqi.bde.bizmodel.execute.dto;

import com.jiuqi.bde.base.intf.FloatDefineEO;
import com.jiuqi.bde.bizmodel.execute.dto.FloatRowResultEO;
import java.util.List;

public class FetchFloatRowDTO {
    private List<FloatDefineEO> floatDefineEOList;
    private List<FloatRowResultEO> floatRowResultEOList;

    public List<FloatDefineEO> getFloatDefineEOList() {
        return this.floatDefineEOList;
    }

    public void setFloatDefineEOList(List<FloatDefineEO> floatDefineEOList) {
        this.floatDefineEOList = floatDefineEOList;
    }

    public List<FloatRowResultEO> getFloatRowResultEOList() {
        return this.floatRowResultEOList;
    }

    public void setFloatRowResultEOList(List<FloatRowResultEO> floatRowResultEOList) {
        this.floatRowResultEOList = floatRowResultEOList;
    }
}

