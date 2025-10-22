/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityModel
 */
package com.jiuqi.nr.query.dataset.parse;

import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.query.dataset.parse.DataSetDimensionNode;

public class DataSetCommonNode
extends DataSetDimensionNode {
    private static final long serialVersionUID = -6088001647305640797L;
    private String attributeCode;

    public DataSetCommonNode(String entityTableName, IEntityModel entityModel, String sourceTableName, String sourceFieldName) {
        super(entityTableName, entityModel, sourceTableName, sourceFieldName);
    }

    public DataSetCommonNode(String entityTableName, String entityFieldName) {
        super(entityTableName, entityFieldName);
    }

    @Override
    public String toEvalFormula() {
        StringBuilder evalExpression = new StringBuilder();
        evalExpression.append("GetMasterData(");
        evalExpression.append("\"").append(this.entityTableName).append("\",");
        this.appendFieldExpression(evalExpression);
        evalExpression.append(",");
        evalExpression.append("\"").append(this.attributeCode).append("\"");
        evalExpression.append(")");
        return evalExpression.toString();
    }

    public void toString(StringBuilder buff) {
        if (this.attributeCode.equals("ORGCODE")) {
            buff.append(this.entityTableName).append("_CODE");
        } else {
            buff.append(this.entityTableName).append("_").append(this.attributeCode);
        }
    }

    public String getAttributeCode() {
        return this.attributeCode;
    }

    public void setAttributeCode(String attributeCode) {
        this.attributeCode = attributeCode;
    }
}

