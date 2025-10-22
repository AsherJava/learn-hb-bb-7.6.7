/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityModel
 */
package com.jiuqi.nr.query.dataset.parse;

import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.query.dataset.parse.DataSetDimensionNode;

public class DataSetParentNode
extends DataSetDimensionNode {
    private static final long serialVersionUID = 4410135376990549861L;

    public DataSetParentNode(String entityTableName, IEntityModel entityModel, String sourceTableName, String sourceFieldName) {
        super(entityTableName, entityModel, sourceTableName, sourceFieldName);
    }

    public DataSetParentNode(String entityTableName, String entityFieldName) {
        super(entityTableName, entityFieldName);
    }

    @Override
    public String toEvalFormula() {
        StringBuilder evalExpression = new StringBuilder();
        evalExpression.append("GetMasterData(");
        evalExpression.append("\"").append(this.entityTableName).append("\",");
        this.appendFieldExpression(evalExpression);
        evalExpression.append(",");
        if (this.entityModel.getParentField() != null) {
            evalExpression.append("\"").append(this.entityModel.getParentField().getCode()).append("\"");
        } else {
            evalExpression.append("\"").append("\"");
        }
        evalExpression.append(")");
        return evalExpression.toString();
    }

    public void toString(StringBuilder buff) {
        buff.append(this.entityTableName).append("_PARENT");
    }
}

