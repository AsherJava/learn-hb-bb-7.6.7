/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.multcheck2.web.vo;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;

public class OrgTreeNode
implements INode {
    private String key;
    private String code;
    private String title;

    public OrgTreeNode() {
    }

    public OrgTreeNode(IEntityRow row) {
        this.key = row.getEntityKeyData();
        this.code = row.getCode();
        this.title = row.getTitle();
    }

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

