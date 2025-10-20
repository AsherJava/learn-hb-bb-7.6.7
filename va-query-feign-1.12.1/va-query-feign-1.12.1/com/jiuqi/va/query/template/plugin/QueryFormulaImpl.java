/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeName
 *  com.jiuqi.bi.syntax.ast.IExpression
 */
package com.jiuqi.va.query.template.plugin;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.query.template.plugin.QueryFormula;

@JsonTypeName(value="QueryFormulaImpl")
public class QueryFormulaImpl
implements QueryFormula {
    private String id;
    private String name;
    private String objectId;
    private String title;
    private String expression;
    private transient IExpression compiledExpression;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public IExpression getCompiledExpression() {
        return this.compiledExpression;
    }

    public void setCompiledExpression(IExpression compiledExpression) {
        this.compiledExpression = compiledExpression;
    }
}

