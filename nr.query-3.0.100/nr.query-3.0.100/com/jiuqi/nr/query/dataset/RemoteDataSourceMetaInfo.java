/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.dataset;

public class RemoteDataSourceMetaInfo {
    private String attrName;
    private String attrTitle;
    private String keyColAttrName;
    private String nameColAttrName;
    private boolean isParentChild;

    public String getAttrName() {
        return this.attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrTitle() {
        return this.attrTitle;
    }

    public void setAttrTitle(String attrTitle) {
        this.attrTitle = attrTitle;
    }

    public String getKeyColAttrName() {
        return this.keyColAttrName;
    }

    public void setKeyColAttrName(String keyColAttrName) {
        this.keyColAttrName = keyColAttrName;
    }

    public String getNameColAttrName() {
        return this.nameColAttrName;
    }

    public void setNameColAttrName(String nameColAttrName) {
        this.nameColAttrName = nameColAttrName;
    }

    public boolean isParentChild() {
        return this.isParentChild;
    }

    public void setParentChild(boolean isParentChild) {
        this.isParentChild = isParentChild;
    }
}

