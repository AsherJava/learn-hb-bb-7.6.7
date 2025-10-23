/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.dafafill.web.vo;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.dafafill.entity.DataFillGroup;

public class GroupTreeNodeVO
implements INode {
    private String title;
    private String key;
    private String code;

    public GroupTreeNodeVO() {
    }

    public GroupTreeNodeVO(String key, String code, String title) {
        this.key = key;
        this.code = code;
        this.title = title;
    }

    public GroupTreeNodeVO(DataFillGroup group) {
        this.key = group.getId();
        this.code = group.getId();
        this.title = group.getTitle();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setCode(String code) {
        this.code = code;
    }
}

