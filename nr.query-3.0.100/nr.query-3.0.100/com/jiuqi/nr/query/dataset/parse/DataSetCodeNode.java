/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityModel
 */
package com.jiuqi.nr.query.dataset.parse;

import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.query.dataset.parse.DataSetDimensionNode;

public class DataSetCodeNode
extends DataSetDimensionNode {
    private static final long serialVersionUID = 4105438078501185850L;

    public DataSetCodeNode(String entityTableName, IEntityModel entityModel, String sourceTableName, String sourceFieldName) {
        super(entityTableName, entityModel, sourceTableName, sourceFieldName);
    }

    public DataSetCodeNode(String entityTableName, String entityFieldName) {
        super(entityTableName, entityFieldName);
    }

    @Override
    public String toEvalFormula() {
        StringBuilder evalExpression = new StringBuilder();
        this.appendFieldExpression(evalExpression);
        return evalExpression.toString();
    }

    public void toString(StringBuilder buff) {
        buff.append(this.entityTableName).append("_CODE");
    }
}

