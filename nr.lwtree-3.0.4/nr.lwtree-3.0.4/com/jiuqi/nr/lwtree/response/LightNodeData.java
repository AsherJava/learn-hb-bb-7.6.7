/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.lwtree.response;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;

public class LightNodeData
implements INode {
    private String key;
    private String code;
    private String title;
    private String parent;
    private String iconValue;
    private int childCount;

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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getIconValue() {
        return this.iconValue;
    }

    public void setIconValue(String iconValue) {
        this.iconValue = iconValue;
    }

    public int getChildCount() {
        return this.childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public static LightNodeData buildEntityData(IEntityRow row) {
        LightNodeData dataObj = new LightNodeData();
        if (row != null) {
            dataObj.setKey(row.getEntityKeyData());
            dataObj.setTitle(row.getTitle());
            dataObj.setCode(row.getCode());
            dataObj.setParent(row.getParentEntityKey());
            dataObj.setIconValue(row.getIconValue());
        }
        return dataObj;
    }
}

