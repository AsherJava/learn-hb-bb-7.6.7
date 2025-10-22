/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.model.FieldInfo
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 */
package com.jiuqi.nr.bql.datasource.parse;

import com.jiuqi.bi.adhoc.model.FieldInfo;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.bql.datasource.QueryContext;
import com.jiuqi.nr.bql.datasource.parse.ParseInfo;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;

public class DataFieldNode
extends ASTNode
implements IASTNode {
    private static final Logger logger = LogFactory.getLogger(DataFieldNode.class);
    private static final long serialVersionUID = 9051855038039385306L;
    private FieldInfo fieldInfo;
    private String tableCode;

    public DataFieldNode(FieldInfo fieldInfo, String tableCode) {
        super(null);
        this.fieldInfo = fieldInfo;
        this.tableCode = tableCode;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        return qContext.getValue(this.fieldInfo.getName());
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DATA;
    }

    public int getType(IContext arg0) throws SyntaxException {
        int dataType = this.fieldInfo.getDataType();
        if (dataType == 5) {
            dataType = 3;
        }
        return dataType;
    }

    public boolean isStatic(IContext arg0) {
        return false;
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        boolean biSyntax = false;
        boolean isEntityFilter = true;
        if (info != null && info instanceof ParseInfo) {
            ParseInfo parseInfo = (ParseInfo)info;
            biSyntax = parseInfo.isBiSyntax();
            isEntityFilter = parseInfo.isEntityFilter();
        } else {
            QueryContext qContext = (QueryContext)context;
            if (!this.tableCode.equals(qContext.getDefaultTableName()) && !this.fieldInfo.getTableName().startsWith(NrPeriodConst.PREFIX_CODE)) {
                biSyntax = true;
            }
        }
        if (biSyntax) {
            buffer.append(this.tableCode).append(".").append(this.fieldInfo.getName());
        } else if (isEntityFilter) {
            buffer.append(this.tableCode).append("[").append(this.fieldInfo.getName()).append("]");
        } else {
            buffer.append(this.fieldInfo.getPhysicalName());
        }
    }

    public boolean support(Language lang) {
        return true;
    }

    public void toString(StringBuilder buff) {
        try {
            this.toFormula(null, buff, null);
        }
        catch (InterpretException e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    public FieldInfo getFieldInfo() {
        return this.fieldInfo;
    }

    public void setFieldInfo(FieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }
}

