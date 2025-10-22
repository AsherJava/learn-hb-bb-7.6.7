/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.grouping;

import com.jiuqi.nr.data.engine.grouping.EnumRowItem;
import java.util.HashMap;
import java.util.List;

public class GradeEnumTree {
    private String entityViewId;
    private int treeDepth;
    private List<Integer> gradeLevels;
    private HashMap<String, EnumRowItem> enumRowItems;

    public int getTreeDepth() {
        return this.treeDepth;
    }

    public void setTreeDepth(int treeDepth) {
        this.treeDepth = treeDepth;
    }

    public List<Integer> getGradeLevels() {
        return this.gradeLevels;
    }

    public void setGradeLevels(List<Integer> gradeLevels) {
        this.gradeLevels = gradeLevels;
    }

    public HashMap<String, EnumRowItem> getEnumRowItems() {
        return this.enumRowItems;
    }

    public void setEnumRowItems(HashMap<String, EnumRowItem> enumRowItems) {
        this.enumRowItems = enumRowItems;
    }

    public String getEntityViewId() {
        return this.entityViewId;
    }

    public void setEntityViewId(String entityViewId) {
        this.entityViewId = entityViewId;
    }
}

