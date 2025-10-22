/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.entity.model.IEntityModel
 */
package com.jiuqi.nr.query.dataset.parse;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.entity.model.IEntityModel;
import java.util.List;

public abstract class DataSetDimensionNode
extends ASTNode {
    private static final long serialVersionUID = 2591375094217390138L;
    protected IEntityModel entityModel;
    protected String sourceTableName;
    protected String sourceFieldName;
    protected String entityTableName;
    protected String entityFieldName;
    protected List<String> fieldExpList;

    public DataSetDimensionNode(String entityTableName, IEntityModel entityModel, String sourceTableName, String sourceFieldName) {
        super(null);
        this.entityTableName = entityTableName;
        this.entityModel = entityModel;
        this.sourceTableName = sourceTableName;
        this.sourceFieldName = sourceFieldName;
    }

    public DataSetDimensionNode(String entityTableName, String entityFieldName) {
        super(null);
        this.entityTableName = entityTableName;
        this.entityFieldName = entityFieldName;
    }

    public Object evaluate(IContext arg0) throws SyntaxException {
        return null;
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DYNAMICDATA;
    }

    public int getType(IContext arg0) throws SyntaxException {
        return 6;
    }

    public boolean isStatic(IContext arg0) {
        return false;
    }

    public abstract String toEvalFormula();

    protected void appendFieldExpression(StringBuilder evalExpression) {
        if (this.fieldExpList == null) {
            evalExpression.append(this.sourceTableName).append("[").append(this.sourceFieldName).append("]");
        } else {
            evalExpression.append("getNotNullValue(");
            for (String fieldExp : this.fieldExpList) {
                evalExpression.append(fieldExp).append("[").append(",");
            }
            evalExpression.setLength(evalExpression.length() - 1);
            evalExpression.append(")");
        }
    }

    public String getEntityTableName() {
        return this.entityTableName;
    }

    public String getEntityFieldName() {
        return this.entityFieldName;
    }

    public void setEntityTableName(String entityTableName) {
        this.entityTableName = entityTableName;
    }

    public void setEntityFieldName(String entityFieldName) {
        this.entityFieldName = entityFieldName;
    }

    public IEntityModel getEntityModel() {
        return this.entityModel;
    }

    public void setEntityModel(IEntityModel entityModel) {
        this.entityModel = entityModel;
    }

    public String getSourceTableName() {
        return this.sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getSourceFieldName() {
        return this.sourceFieldName;
    }

    public void setSourceFieldName(String sourceFieldName) {
        this.sourceFieldName = sourceFieldName;
    }

    public void setFieldExpList(List<String> fieldExpList) {
        this.fieldExpList = fieldExpList;
    }
}

