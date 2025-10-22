/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.util.LogHelper
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.dataentry.internal.service.parse;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.util.LogHelper;
import com.jiuqi.nr.dataentry.internal.service.parse.CKRQueryContext;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;

@Deprecated
public class CKRNode
extends ASTNode {
    private static final long serialVersionUID = -20860266924858710L;
    protected ColumnModelDefine columnModel;
    protected int columnIndex = -1;
    private String restrictions;
    private int dataType = -1;

    public CKRNode(Token token, ColumnModelDefine columnModel) {
        super(token);
        this.columnModel = columnModel;
    }

    public CKRNode(ColumnModelDefine columnModel) {
        this(null, columnModel);
    }

    public CKRNode() {
        this(null, null);
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DYNAMICDATA;
    }

    public int getType(IContext context) throws SyntaxException {
        if (this.dataType < 0) {
            ColumnModelType columnType = this.columnModel.getColumnType();
            this.dataType = columnType == ColumnModelType.INTEGER ? ColumnModelType.DOUBLE.getValue() : columnType.getValue();
        }
        return this.dataType;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        return null;
    }

    public boolean support(Language lang) {
        return lang != Language.JQMDX;
    }

    protected void toSQL(IContext context, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        CKRQueryContext qContext = (CKRQueryContext)context;
        String tableID = this.columnModel.getTableID();
        DataModelService bean = (DataModelService)BeanUtil.getBean(DataModelService.class);
        String tableAlias = qContext.getTableAliaMap().get(bean.getTableModelDefineById(tableID).getName());
        buffer.append(tableAlias).append(".");
        buffer.append(this.columnModel.getName());
    }

    public boolean isStatic(IContext context) {
        return false;
    }

    public void toString(StringBuilder buffer) {
        try {
            this.toFormula(null, buffer, null);
        }
        catch (InterpretException e) {
            LogHelper.error((Exception)((Object)e));
        }
    }

    public ColumnModelDefine getQueryColumn() {
        return this.columnModel;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        this.toString(buffer);
        return buffer.toString();
    }

    public int hashCode() {
        int hashCode = this.columnModel.hashCode();
        return hashCode;
    }

    public boolean equals(Object obj) {
        if (obj instanceof CKRNode) {
            CKRNode other = (CKRNode)((Object)obj);
            return this.columnModel.equals(other.columnModel);
        }
        return super.equals(obj);
    }

    protected int initNumSacle(IContext context) {
        return this.columnModel.getDecimal();
    }

    public String getRestrictions() {
        return this.restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }
}

