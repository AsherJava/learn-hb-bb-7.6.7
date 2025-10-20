/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.check.dto;

import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO;
import java.util.List;

public class MatchingVchr {
    private FinancialCheckSchemeEO scheme;
    private List<GcRelatedItemEO> vchrItems;
    private List<GcRelatedItemEO> newVchrItems;

    public MatchingVchr(FinancialCheckSchemeEO scheme, List<GcRelatedItemEO> vchrItems) {
        this.scheme = scheme;
        this.vchrItems = vchrItems;
    }

    public FinancialCheckSchemeEO getScheme() {
        return this.scheme;
    }

    public void setScheme(FinancialCheckSchemeEO scheme) {
        this.scheme = scheme;
    }

    public List<GcRelatedItemEO> getVchrItems() {
        return this.vchrItems;
    }

    public void setVchrItems(List<GcRelatedItemEO> vchrItems) {
        this.vchrItems = vchrItems;
    }

    public List<GcRelatedItemEO> getNewVchrItems() {
        return this.newVchrItems;
    }

    public void setNewVchrItems(List<GcRelatedItemEO> newVchrItems) {
        this.newVchrItems = newVchrItems;
    }
}

