/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.SQLInfoDescr
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nvwa.framework.parameter.syntax;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.SQLInfoDescr;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamFilterTranslator;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamNode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SQLFilterByParam
extends Function {
    private static final long serialVersionUID = 1L;
    private Connection conn;

    public SQLFilterByParam(Connection conn) {
        this.parameters().add(new Parameter("valueField", 6, "\u88ab\u8fc7\u6ee4\u7684\u5b57\u6bb5\u6216\u503c"));
        this.parameters().add(new Parameter("param", 0, "\u9650\u5b9a\u7684\u53c2\u6570"));
        this.conn = conn;
    }

    public String name() {
        return "FilterByParam";
    }

    public String title() {
        return "\u6839\u636e\u53c2\u6570\u8fc7\u6ee4\u6570\u636e\uff0c\u8fd4\u56de\u8fc7\u6ee4\u8868\u8fbe\u5f0f";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u8fc7\u6ee4\u51fd\u6570";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (!(parameters.get(1) instanceof ParamNode)) {
            throw new SyntaxException(parameters.get(1).getToken(), "\u4f20\u5165\u53c2\u6570\u5fc5\u987b\u4e3a\u53c2\u6570\u7c7b\u578b\u5bf9\u8c61\u3002");
        }
        return super.validate(context, parameters);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IDatabase db;
        try {
            db = DatabaseManager.getInstance().findDatabaseByConnection(this.conn);
        }
        catch (SQLException e) {
            throw new SyntaxException((Throwable)e);
        }
        SQLInfoDescr info = new SQLInfoDescr(db, true);
        return this.interpret(context, parameters, Language.SQL, info);
    }

    public boolean support(Language lang) {
        return super.support(lang) || lang == Language.SQL;
    }

    protected void toSQL(IContext context, List<IASTNode> parameters, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        ParamFilterTranslator translator = new ParamFilterTranslator(){

            @Override
            public String toValueFieldSQL(IContext context, IASTNode valueNode, ISQLInfo info) throws InterpretException {
                String fieldName;
                StringBuilder buffer = new StringBuilder();
                try {
                    fieldName = (String)valueNode.evaluate(context);
                }
                catch (SyntaxException e) {
                    throw new InterpretException((Throwable)e);
                }
                buffer.append(fieldName);
                return buffer.toString();
            }
        };
        IASTNode valueNode = parameters.get(0);
        ParamNode paramNode = (ParamNode)parameters.get(1);
        translator.toSQL(context, buffer, valueNode, paramNode, info);
    }
}

