/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.dafafill.tree;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.dafafill.model.QueryField;

public class ResourceNode
implements INode {
    private String title;
    private String key;
    private String code;
    private int nodeType;
    private String dataSchemeCode;
    private QueryField field;

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

    public int getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public QueryField getField() {
        return this.field;
    }

    public void setField(QueryField field) {
        this.field = field;
    }
}

