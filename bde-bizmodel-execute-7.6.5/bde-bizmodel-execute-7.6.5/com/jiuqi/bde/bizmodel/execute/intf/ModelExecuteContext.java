/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 */
package com.jiuqi.bde.bizmodel.execute.intf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ModelExecuteContext
extends FetchTaskContext {
    private String optimizeRuleGroup;
    private List<Dimension> assTypeList;

    public String getOptimizeRuleGroup() {
        return this.optimizeRuleGroup;
    }

    public void setOptimizeRuleGroup(String optimizeRuleGroup) {
        this.optimizeRuleGroup = optimizeRuleGroup;
    }

    public List<Dimension> getAssTypeList() {
        return this.assTypeList;
    }

    public void setAssTypeList(List<Dimension> assTypeList) {
        this.assTypeList = assTypeList;
    }

    public String toString() {
        return "ModelExecuteContext [optimizeRuleGroup='" + this.optimizeRuleGroup + "', assTypeList=" + this.assTypeList + ", toString()=" + super.toString() + "]";
    }
}

