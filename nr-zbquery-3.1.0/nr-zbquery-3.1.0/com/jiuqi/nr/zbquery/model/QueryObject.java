/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonSubTypes
 *  com.fasterxml.jackson.annotation.JsonSubTypes$Type
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.zbquery.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.zbquery.model.DimensionAttributeField;
import com.jiuqi.nr.zbquery.model.FieldGroup;
import com.jiuqi.nr.zbquery.model.FormulaField;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.model.ZBField;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="type", visible=true)
@JsonSubTypes(value={@JsonSubTypes.Type(value=QueryDimension.class, name="DIMENSION"), @JsonSubTypes.Type(value=DimensionAttributeField.class, name="DIMENSIONATTRIBUTE"), @JsonSubTypes.Type(value=ZBField.class, name="ZB"), @JsonSubTypes.Type(value=FormulaField.class, name="FORMULA"), @JsonSubTypes.Type(value=FieldGroup.class, name="GROUP")})
public abstract class QueryObject {
    private String id;
    private String name;
    private String fullName;
    private String schemeName;
    private String title;
    private String alias;
    private QueryObjectType type;
    private boolean visible = true;
    private String parent;
    private String messageAlias;
    private String relatedPublicParameter;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public QueryObjectType getType() {
        return this.type;
    }

    public void setType(QueryObjectType type) {
        this.type = type;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getMessageAlias() {
        return this.messageAlias;
    }

    public void setMessageAlias(String messageAlias) {
        this.messageAlias = messageAlias;
    }

    public String getRelatedPublicParameter() {
        return this.relatedPublicParameter;
    }

    public void setRelatedPublicParameter(String relatedPublicParameter) {
        this.relatedPublicParameter = relatedPublicParameter;
    }

    public String getDisplayTitle() {
        if (StringUtils.isNotEmpty((String)this.alias)) {
            return this.alias;
        }
        return this.title;
    }

    public String toString() {
        return "{fullName=" + this.fullName + ", type=" + (Object)((Object)this.type) + "}";
    }
}

