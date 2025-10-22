/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IAssignable
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.ScriptInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.sql.SQLIntepreter
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IAssignable;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.ScriptInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.sql.SQLIntepreter;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.parse.LJSQLInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import java.util.UUID;

public class VariableDataNode
extends ASTNode
implements IAssignable {
    private static final long serialVersionUID = 1616788229271121740L;
    private Variable variable;
    private boolean isSpecial = false;

    public VariableDataNode(Token token, Variable variable) {
        super(token);
        this.variable = variable;
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DYNAMICDATA;
    }

    public int getType(IContext context) throws SyntaxException {
        int dataType = this.variable.getDataType();
        if (dataType == 5) {
            return 2;
        }
        if (dataType == 4 || dataType == 3) {
            return 3;
        }
        return dataType;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        try {
            QueryContext qContext = (QueryContext)context;
            Object value = qContext.getVarValue(this.variable.getVarName());
            if (value == null) {
                value = this.variable.getVarValue(context);
            }
            return value;
        }
        catch (Exception e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    public boolean isStatic(IContext context) {
        return false;
    }

    public void toString(StringBuilder buffer) {
        buffer.append(this.variable.getVarName());
    }

    public boolean support(Language lang) {
        if (lang == Language.SQL) {
            return this.variable.isStatic();
        }
        return lang != Language.JQMDX && lang != Language.EXCEL;
    }

    protected void toSQL(IContext context, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        if (info instanceof LJSQLInfo) {
            QueryContext qContext = (QueryContext)context;
            String periodSqlField = (String)qContext.getOption("option.periodSqlField");
            String yfSql = SQLIntepreter.toSQL((String)("right(" + periodSqlField + ",4)"), (IDatabase)qContext.getQueryParam().getDatabase());
            buffer.append(yfSql);
            return;
        }
        Object value = null;
        try {
            value = this.evaluate(context);
        }
        catch (SyntaxException e) {
            throw new InterpretException((Throwable)e);
        }
        if (value != null) {
            if (this.variable.getDataType() == 6) {
                buffer.append("'").append(value).append("'");
            } else if (value instanceof UUID) {
                buffer.append(DataEngineUtil.buildGUIDValueSql(info.getDatabase(), (UUID)value));
            } else {
                buffer.append(value);
            }
        }
    }

    protected void toExplain(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append(this.variable.getVarTitle());
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        if (this.isSpecial) {
            buffer.append(this.variable.getVarName());
        } else {
            buffer.append("[").append(this.variable.getVarName()).append("]");
        }
    }

    protected void toJavaScript(IContext context, StringBuilder buffer, ScriptInfo info) throws InterpretException {
        this.variable.toJavaScript(context, buffer, info);
        info.setCurrentName("new _VN(" + info.getCurrentName() + ",'" + this.isSpecial + "')");
    }

    public boolean isSpecial() {
        return this.isSpecial;
    }

    public void setSpecial(boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    public int setValue(IContext context, Object value) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        qContext.setVarValue(this.variable.getVarName(), value);
        return 1;
    }

    public Variable getVariable() {
        return this.variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.variable == null ? 0 : this.variable.getVarName().hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (((Object)((Object)this)).getClass() != obj.getClass()) {
            return false;
        }
        VariableDataNode other = (VariableDataNode)((Object)obj);
        return !(this.variable == null ? other.variable != null : !this.variable.getVarName().equals(other.variable.getVarName()));
    }
}

