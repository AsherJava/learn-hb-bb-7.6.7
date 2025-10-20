/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.web.facade.ExtNode;
import java.util.List;

public class PrivateFormulaEntityTree {
    private String currOrg;
    private String currOrgTitle;
    private List<ExtNode> treeObjs;

    public String getCurrOrgTitle() {
        return this.currOrgTitle;
    }

    public void setCurrOrgTitle(String currOrgTitle) {
        this.currOrgTitle = currOrgTitle;
    }

    public String getCurrOrg() {
        return this.currOrg;
    }

    public void setCurrOrg(String currOrg) {
        this.currOrg = currOrg;
    }

    public List<ExtNode> getTreeObjs() {
        return this.treeObjs;
    }

    public void setTreeObjs(List<ExtNode> treeObjs) {
        this.treeObjs = treeObjs;
    }
}

