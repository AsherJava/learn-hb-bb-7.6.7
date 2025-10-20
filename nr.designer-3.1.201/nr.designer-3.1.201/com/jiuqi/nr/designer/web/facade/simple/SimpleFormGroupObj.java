/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 */
package com.jiuqi.nr.designer.web.facade.simple;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.designer.web.facade.simple.SimpleFormObj;
import java.util.List;

public class SimpleFormGroupObj {
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value="Code")
    private String code;
    @JsonProperty(value="Order")
    private String order;
    private List<SimpleFormObj> children;

    public SimpleFormGroupObj() {
    }

    public SimpleFormGroupObj(DesignFormGroupDefine formGroupDefine) {
        if (formGroupDefine != null) {
            this.id = formGroupDefine.getKey();
            this.title = formGroupDefine.getTitle();
            this.code = formGroupDefine.getCode();
            this.order = formGroupDefine.getOrder();
        }
    }

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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<SimpleFormObj> getChildren() {
        return this.children;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setChildren(List<SimpleFormObj> children) {
        this.children = children;
    }
}

