/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 *  org.json.JSONObject
 */
package com.jiuiqi.nr.unit.treebase.context.impl;

import com.jiuiqi.nr.unit.treebase.enumeration.UnitTreeRangeDisplay;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.List;
import org.json.JSONObject;

public class UnitTreeNodeRangeParam {
    private static final String RANGE_PARAM_KEY = "rangeParam";
    private String rangeType;
    private List<String> entityDataRange;

    public String getRangeType() {
        return this.rangeType;
    }

    public void setRangeType(String rangeType) {
        this.rangeType = rangeType;
    }

    public List<String> getEntityDataRange() {
        return this.entityDataRange;
    }

    public void setEntityDataRange(List<String> entityDataRange) {
        this.entityDataRange = entityDataRange;
    }

    public boolean isValidParam() {
        UnitTreeRangeDisplay rangeDisplay = UnitTreeRangeDisplay.toDisplay(this.rangeType);
        return rangeDisplay != null && this.entityDataRange != null && !this.entityDataRange.isEmpty();
    }

    public static UnitTreeNodeRangeParam translate2EntityRangeParam(JSONObject customVariable) {
        return (UnitTreeNodeRangeParam)JavaBeanUtils.toJavaBean((JSONObject)customVariable, (String)RANGE_PARAM_KEY, UnitTreeNodeRangeParam.class);
    }
}

