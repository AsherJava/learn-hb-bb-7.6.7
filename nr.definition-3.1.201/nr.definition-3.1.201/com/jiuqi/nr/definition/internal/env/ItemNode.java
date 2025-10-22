/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.sql.SQLIntepreter
 *  com.jiuqi.np.dataengine.parse.LJSQLInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.sql.SQLIntepreter;
import com.jiuqi.np.dataengine.parse.LJSQLInfo;
import com.jiuqi.np.dataengine.query.QueryContext;

public class ItemNode
extends ASTNode {
    private static final long serialVersionUID = 8509770242089658427L;
    private String item;

    public ItemNode(Token token, String item) {
        super(token);
        this.item = item;
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DYNAMICDATA;
    }

    public int getType(IContext context) throws SyntaxException {
        return 6;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        return this.item;
    }

    public boolean support(Language lang) {
        return lang != Language.JQMDX && lang != Language.EXCEL;
    }

    protected void toSQL(IContext context, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        if (info instanceof LJSQLInfo && this.item.equals("YF")) {
            QueryContext qContext = (QueryContext)context;
            String periodSqlField = (String)qContext.getOption("option.periodSqlField");
            String yfSql = SQLIntepreter.toSQL((String)("right(" + periodSqlField + ",4)"), (IDatabase)qContext.getQueryParam().getDatabase());
            buffer.append(yfSql);
        } else {
            buffer.append(this.item);
        }
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        this.toString(buffer);
    }

    public boolean isStatic(IContext context) {
        return false;
    }

    public void toString(StringBuilder buffer) {
        buffer.append(this.item);
    }
}

