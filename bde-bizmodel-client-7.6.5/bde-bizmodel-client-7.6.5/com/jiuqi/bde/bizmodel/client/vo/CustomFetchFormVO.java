/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.client.vo;

import com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum;
import com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum;
import java.util.List;

public class CustomFetchFormVO {
    private List<AggregateFuncEnum> aggregateFuncEnums;
    private List<MatchingRuleEnum> matchingRuleEnums;

    public CustomFetchFormVO() {
    }

    public CustomFetchFormVO(List<AggregateFuncEnum> aggregateFuncEnums, List<MatchingRuleEnum> matchingRuleEnums) {
        this.aggregateFuncEnums = aggregateFuncEnums;
        this.matchingRuleEnums = matchingRuleEnums;
    }

    public List<AggregateFuncEnum> getAggregateFuncEnums() {
        return this.aggregateFuncEnums;
    }

    public void setAggregateFuncEnums(List<AggregateFuncEnum> aggregateFuncEnums) {
        this.aggregateFuncEnums = aggregateFuncEnums;
    }

    public List<MatchingRuleEnum> getMatchingRuleEnums() {
        return this.matchingRuleEnums;
    }

    public void setMatchingRuleEnums(List<MatchingRuleEnum> matchingRuleEnums) {
        this.matchingRuleEnums = matchingRuleEnums;
    }
}

