/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.zbquery.rest.vo;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.zbquery.bean.ZBQueryGroup;

public class GroupTreeNodeVO
implements INode {
    private String title;
    private String key;
    private String code;

    public GroupTreeNodeVO() {
    }

    public GroupTreeNodeVO(ZBQueryGroup zBQueryGroup) {
        this.title = zBQueryGroup.getTitle();
        this.key = zBQueryGroup.getId();
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

