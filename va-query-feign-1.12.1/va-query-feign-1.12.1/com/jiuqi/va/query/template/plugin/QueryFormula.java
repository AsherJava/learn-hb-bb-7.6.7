/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonSubTypes
 *  com.fasterxml.jackson.annotation.JsonSubTypes$Type
 */
package com.jiuqi.va.query.template.plugin;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.jiuqi.va.query.template.plugin.QueryFormulaImpl;

@JsonSubTypes(value={@JsonSubTypes.Type(value=QueryFormulaImpl.class)})
public interface QueryFormula {
    public String getId();

    public String getName();

    public String getObjectId();

    public String getTitle();

    public String getExpression();

    public Object getCompiledExpression();
}

