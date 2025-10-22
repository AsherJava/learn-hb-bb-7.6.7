/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.SqlQueryHelper
 */
package com.jiuqi.nr.data.engine.summary.parse.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.SqlQueryHelper;
import com.jiuqi.nr.data.engine.summary.parse.SumContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;

public class InCollectionFunction
extends Function {
    private static final long serialVersionUID = -4562771098865625223L;
    private static final String COLUMN = "OBJECTCODE";

    public InCollectionFunction() {
        this.parameters().add(new Parameter("text", 0, "\u5f85\u5224\u65ad\u7684\u5185\u5bb9"));
        this.parameters().add(new Parameter("collectionTable", 0, "\u96c6\u5408\u6570\u636e\u6240\u5728\u8868"));
        this.parameters().add(new Parameter("collectionColumn", 0, "\u96c6\u5408\u6570\u636e\u6240\u5728\u5217", true));
    }

    public String name() {
        return "InCollection";
    }

    public String title() {
        return "\u5224\u65ad\u5b57\u7b26\u4e32\u662f\u5426\u5728\u6307\u5b9a\u7684\u96c6\u5408\u4e2d";
    }

    public int getResultType(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        return 1;
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u903b\u8f91\u51fd\u6570";
    }

    public Object evalute(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        Object p2;
        SumContext context = (SumContext)paramIContext;
        Object p0 = paramList.get(0).evaluate(paramIContext);
        if (p0 == null) {
            return false;
        }
        Object p1 = paramList.get(1).evaluate(paramIContext);
        if (p1 == null) {
            return false;
        }
        String columnName = COLUMN;
        if (paramList.size() > 2 && (p2 = paramList.get(2).evaluate(paramIContext)) != null) {
            columnName = p2.toString();
        }
        String cacheKey = this.name() + "_" + p1;
        HashSet<Object> set = (HashSet<Object>)context.getCache().get(cacheKey);
        String tableName = p1.toString();
        if (set == null) {
            try {
                set = new HashSet<Object>();
                context.getCache().put(cacheKey, set);
                Connection conn = context.getConnection();
                StringBuilder sql = new StringBuilder();
                sql.append(" select ct.").append(columnName).append(" from ").append(tableName).append(" ct");
                try (SqlQueryHelper sqlHelper = DataEngineUtil.createSqlQueryHelper();
                     ResultSet rs = sqlHelper.executeQuery(conn, sql.toString());){
                    while (rs.next()) {
                        set.add(rs.getObject(1));
                    }
                }
            }
            catch (Exception e) {
                context.getLogger().error(e.getMessage(), e);
            }
        }
        return set.contains(p0);
    }

    public boolean support(Language lang) {
        return true;
    }

    protected void toSQL(IContext paramIContext, List<IASTNode> paramList, StringBuilder paramStringBuilder, ISQLInfo paramISQLInfo) throws InterpretException {
        try {
            IASTNode field = paramList.get(0);
            String tableName = paramList.get(1).evaluate(paramIContext).toString();
            String columnName = COLUMN;
            if (paramList.size() == 3) {
                columnName = paramList.get(2).evaluate(paramIContext).toString();
            }
            paramStringBuilder.append(" exists (select 1 from ");
            paramStringBuilder.append(tableName).append(" ct ");
            paramStringBuilder.append(" where ct.").append(columnName).append("=").append(field.interpret(paramIContext, Language.SQL, (Object)paramISQLInfo));
            paramStringBuilder.append(")");
        }
        catch (SyntaxException e) {
            throw new InterpretException(e.getMessage(), (Throwable)e);
        }
    }
}

