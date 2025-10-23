/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.bean;

import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckSchemeOrg;
import java.util.List;

public class MCSchemeParam {
    MultcheckScheme mcScheme;
    List<MultcheckSchemeOrg> mcsOrgList;
    List<MultcheckItem> mcItemList;

    public MultcheckScheme getMcScheme() {
        return this.mcScheme;
    }

    public void setMcScheme(MultcheckScheme mcScheme) {
        this.mcScheme = mcScheme;
    }

    public List<MultcheckSchemeOrg> getMcsOrgList() {
        return this.mcsOrgList;
    }

    public void setMcsOrgList(List<MultcheckSchemeOrg> mcsOrgList) {
        this.mcsOrgList = mcsOrgList;
    }

    public List<MultcheckItem> getMcItemList() {
        return this.mcItemList;
    }

    public void setMcItemList(List<MultcheckItem> mcItemList) {
        this.mcItemList = mcItemList;
    }
}

