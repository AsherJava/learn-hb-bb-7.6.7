/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.datacheck.dataanalyze.vo;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.Set;

public class OrgDimSetDTO {
    private Set<String> errorOrgList;
    private DimensionValueSet dimSet;

    public OrgDimSetDTO() {
    }

    public OrgDimSetDTO(DimensionValueSet dimSet) {
        this.dimSet = dimSet;
    }

    public Set<String> getErrorOrgList() {
        return this.errorOrgList;
    }

    public void setErrorOrgList(Set<String> errorOrgList) {
        this.errorOrgList = errorOrgList;
    }

    public DimensionValueSet getDimSet() {
        return this.dimSet;
    }

    public void setDimSet(DimensionValueSet dimSet) {
        this.dimSet = dimSet;
    }
}

