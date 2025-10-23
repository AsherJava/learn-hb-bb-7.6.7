/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.web.vo;

import java.util.List;

public class MoveTreeNodeVO {
    private String id;
    private String title;
    private String expand;
    private List<MoveTreeNodeVO> children;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpand() {
        return this.expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public List<MoveTreeNodeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<MoveTreeNodeVO> children) {
        this.children = children;
    }
}

