/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.rest.param;

import com.jiuqi.nr.zbquery.model.ZBQueryModel;

public class AdaptTreeParam {
    private String parentKey;
    private String parentCode;
    private int nodeType;
    private ZBQueryModel zbQueryModel;

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public int getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public ZBQueryModel getZbQueryModel() {
        return this.zbQueryModel;
    }

    public void setZbQueryModel(ZBQueryModel zbQueryModel) {
        this.zbQueryModel = zbQueryModel;
    }
}

