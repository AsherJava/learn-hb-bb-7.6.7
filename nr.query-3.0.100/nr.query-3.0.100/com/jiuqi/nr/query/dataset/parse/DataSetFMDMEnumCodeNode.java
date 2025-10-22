/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityModel
 */
package com.jiuqi.nr.query.dataset.parse;

import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.query.dataset.parse.DataSetDimensionNode;
import com.jiuqi.nr.query.dataset.parse.DataSetFMDMEnumNode;

public class DataSetFMDMEnumCodeNode
extends DataSetFMDMEnumNode {
    private static final long serialVersionUID = 579464649006954858L;

    public DataSetFMDMEnumCodeNode(String entityTableName, IEntityModel entityModel, String sourceTableName, String sourceFieldName, DataSetDimensionNode fmdmNode) {
        super(entityTableName, entityModel, sourceTableName, sourceFieldName, fmdmNode);
    }

    public DataSetFMDMEnumCodeNode(String entityTableName, String entityFieldName, DataSetDimensionNode fmdmNode) {
        super(entityTableName, entityFieldName, fmdmNode);
    }

    @Override
    public String toEvalFormula() {
        StringBuilder evalExpression = new StringBuilder();
        evalExpression.append("GetMasterData(");
        evalExpression.append("\"").append(this.fmdmNode.getEntityTableName()).append("\",");
        evalExpression.append(this.fmdmNode.toEvalFormula()).append(",");
        evalExpression.append("\"").append(this.sourceFieldName).append("\"");
        evalExpression.append(")");
        return evalExpression.toString();
    }

    public void toString(StringBuilder buff) {
        buff.append(this.entityTableName).append("_CODE");
    }
}

