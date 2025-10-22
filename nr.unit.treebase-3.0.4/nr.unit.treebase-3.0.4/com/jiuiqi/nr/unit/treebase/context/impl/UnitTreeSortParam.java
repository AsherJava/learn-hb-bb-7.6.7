/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 *  org.json.JSONObject
 */
package com.jiuiqi.nr.unit.treebase.context.impl;

import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeSortField;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.List;
import org.json.JSONObject;

public class UnitTreeSortParam {
    private static final String SORT_FIELDS_PARAM_KEY = "sortFieldsParam";
    private List<UnitTreeSortField> fields;
    private boolean ascOrder;

    public List<UnitTreeSortField> getFields() {
        return this.fields;
    }

    public void setFields(List<UnitTreeSortField> fields) {
        this.fields = fields;
    }

    public boolean isAscOrder() {
        return this.ascOrder;
    }

    public void setAscOrder(boolean ascOrder) {
        this.ascOrder = ascOrder;
    }

    public static UnitTreeSortParam translate2SortParam(JSONObject customVariable) {
        return (UnitTreeSortParam)JavaBeanUtils.toJavaBean((JSONObject)customVariable, (String)SORT_FIELDS_PARAM_KEY, UnitTreeSortParam.class);
    }
}

