/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.domain;

import com.jiuqi.va.bill.domain.BillActionAuthDO;
import java.util.ArrayList;
import java.util.List;

public class BillActionAuthVO
extends BillActionAuthDO {
    private String actTitle;
    private List<BillActionAuthVO> children = new ArrayList<BillActionAuthVO>();

    public String getActTitle() {
        return this.actTitle;
    }

    public void setActTitle(String actTitle) {
        this.actTitle = actTitle;
    }

    public List<BillActionAuthVO> getChildren() {
        return this.children;
    }

    public void addChildren(BillActionAuthVO child) {
        if (this.children == null) {
            this.children = new ArrayList<BillActionAuthVO>();
        }
        for (BillActionAuthVO existingChild : this.children) {
            if (!existingChild.getActname().equals(child.getActname())) continue;
            return;
        }
        this.children.add(child);
    }
}

