/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nr.zbquery.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.zbquery.model.ConditionOperationItem;
import com.jiuqi.nr.zbquery.model.Logic;
import java.util.List;

public class CustomFilter {
    private List<ConditionOperationItem> operationItems;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Logic logic;

    public List<ConditionOperationItem> getOperationItems() {
        return this.operationItems;
    }

    public void setOperationItems(List<ConditionOperationItem> operationItems) {
        this.operationItems = operationItems;
    }

    public Logic getLogic() {
        return this.logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }
}

