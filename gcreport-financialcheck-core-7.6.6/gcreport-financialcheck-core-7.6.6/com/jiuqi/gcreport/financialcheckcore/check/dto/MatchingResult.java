/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gcreport.financialcheckcore.check.dto;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.financialcheckcore.check.dto.MatchingVchr;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.ArrayList;
import java.util.List;

public class MatchingResult {
    private List<MatchingVchr> matchingVchrs = new ArrayList<MatchingVchr>();
    private List<GcRelatedItemEO> unmatchingVchrItems = new ArrayList<GcRelatedItemEO>();

    public List<MatchingVchr> getMatchingVchrs() {
        return this.matchingVchrs;
    }

    public void addMatchingVchrs(List<MatchingVchr> matchingVchrs) {
        if (CollectionUtils.isEmpty(matchingVchrs)) {
            return;
        }
        this.matchingVchrs.addAll(matchingVchrs);
    }

    public List<GcRelatedItemEO> getUnmatchingVchrItems() {
        return this.unmatchingVchrItems;
    }

    public void addUnmatchingVchrItems(List<GcRelatedItemEO> unmatchingVchrItems) {
        if (CollectionUtils.isEmpty(unmatchingVchrItems)) {
            return;
        }
        this.unmatchingVchrItems.addAll(unmatchingVchrItems);
    }

    public void addUnmatchingVchrItem(GcRelatedItemEO unmatchingVchrItem) {
        this.unmatchingVchrItems.add(unmatchingVchrItem);
    }
}

