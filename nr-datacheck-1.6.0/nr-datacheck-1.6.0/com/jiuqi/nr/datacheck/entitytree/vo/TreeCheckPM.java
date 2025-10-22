/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.entitytree.vo;

import com.jiuqi.nr.datacheck.entitytree.vo.TreeCheckContext;
import java.util.List;

public class TreeCheckPM {
    private TreeCheckContext context;
    private List<String> orgCode;
    private String type;

    public TreeCheckContext getContext() {
        return this.context;
    }

    public void setContext(TreeCheckContext context) {
        this.context = context;
    }

    public List<String> getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(List<String> orgCode) {
        this.orgCode = orgCode;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

