/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  nr.single.data.bean.CheckResultNode
 */
package com.jiuqi.nr.datacheck.entitytree.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.data.bean.CheckResultNode;

public class TreeCheckResultVO {
    private Map<String, List<CheckResultNode>> right = new HashMap<String, List<CheckResultNode>>();
    private Map<String, List<CheckResultNode>> error = new HashMap<String, List<CheckResultNode>>();

    public void add(CheckResultNode node) {
        if (node != null) {
            if (node.getErrorType() > 0) {
                this.error.computeIfAbsent(node.getUnitCode(), k -> new ArrayList()).add(node);
            } else {
                this.right.computeIfAbsent(node.getUnitCode(), k -> new ArrayList()).add(node);
            }
        }
    }

    public Map<String, List<CheckResultNode>> getRight() {
        return this.right;
    }

    public void setRight(Map<String, List<CheckResultNode>> right) {
        this.right = right;
    }

    public Map<String, List<CheckResultNode>> getError() {
        return this.error;
    }

    public void setError(Map<String, List<CheckResultNode>> error) {
        this.error = error;
    }
}

