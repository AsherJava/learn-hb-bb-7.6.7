/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.sql.DBException
 *  com.jiuqi.bi.sql.DataTypes
 *  com.jiuqi.bi.sql.SQLPretreatment
 *  com.jiuqi.bi.sql.SQLScriptExcutor
 *  com.jiuqi.bi.sql.util.SQLUtils
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.parser.FormulaParser
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener$ParamInfo
 *  com.jiuqi.bi.syntax.sql.SQLQueryException
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor$ReplaceError
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.patterns.ITranslator
 *  com.jiuqi.bi.util.patterns.PatternException
 *  com.jiuqi.bi.util.patterns.Patterns
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.sql.DBException;
import com.jiuqi.bi.sql.DataTypes;
import com.jiuqi.bi.sql.SQLPretreatment;
import com.jiuqi.bi.sql.SQLScriptExcutor;
import com.jiuqi.bi.sql.util.SQLUtils;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.parser.FormulaParser;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.sql.ISQLQueryListener;
import com.jiuqi.bi.syntax.sql.SQLQueryException;
import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.patterns.ITranslator;
import com.jiuqi.bi.util.patterns.PatternException;
import com.jiuqi.bi.util.patterns.Patterns;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.List;

public class SQLQueryScriptExcutor
extends SQLScriptExcutor {
    private Connection conn;
    private IDSContext context;
    private FormulaParser parser;
    private String[] paramNames;

    public SQLQueryScriptExcutor(Reader sqlReader, Connection conn, IDSContext context) {
        super(sqlReader, false);
        this.conn = conn;
        this.context = context;
        this.parser = new FormulaParser();
        this.parser.registerDynamicNodeProvider((IDynamicNodeProvider)new ParamProvider());
    }

    protected void excuteSingleSql(String sql) throws DBException {
        sql = SQLUtils.skipComments((String)sql);
        try {
            sql = this.replaceMacros(sql);
            sql = this.prepareParams(sql);
        }
        catch (SQLQueryException e1) {
            throw new DBException((Throwable)e1);
        }
        try (PreparedStatement ps = this.conn.prepareStatement(sql);){
            this.setParamValues(ps, this.paramNames);
            ps.execute();
        }
        catch (Exception e) {
            throw new DBException(e.getMessage(), (Throwable)e);
        }
    }

    private String prepareParams(String sql) throws SQLQueryException {
        SQLPretreatment pretreatment = new SQLPretreatment(sql);
        try {
            pretreatment.execute();
        }
        catch (DBException e) {
            throw new SQLQueryException(e.getMessage(), (Throwable)e);
        }
        for (String param : this.paramNames = pretreatment.getParamNames()) {
            if (!StringUtils.isEmpty((String)param)) continue;
            throw new SQLQueryException("SQL\u8bed\u53e5\u4e2d\u5b58\u5728\u672a\u6307\u5b9a\u540d\u79f0\u7684\u53c2\u6570\u3002");
        }
        return pretreatment.getSQL();
    }

    private String replaceMacros(String sql) throws SQLQueryException {
        Patterns patterns = Patterns.getInstance();
        try {
            patterns.parse(sql);
        }
        catch (PatternException e) {
            throw new SQLQueryException(e.getMessage(), (Throwable)e);
        }
        catch (SQLQueryExecutor.ReplaceError e) {
            throw new SQLQueryException(e.getMessage(), (Throwable)e);
        }
        return patterns.repace(new ITranslator(){

            public String[] translate(String pattern, int length) {
                String[] stringArray;
                String value = SQLQueryScriptExcutor.this.evaluate(pattern.startsWith("=") ? pattern.substring(1) : pattern);
                if (value == null) {
                    stringArray = null;
                } else {
                    String[] stringArray2 = new String[1];
                    stringArray = stringArray2;
                    stringArray2[0] = value;
                }
                return stringArray;
            }
        });
    }

    private String evaluate(String expr) {
        Object value;
        try {
            IExpression exprNode = this.parser.parseEval(expr, null);
            value = exprNode.evaluate(null);
        }
        catch (SyntaxException e) {
            throw new SQLQueryExecutor.ReplaceError((Throwable)e);
        }
        return SQLQueryScriptExcutor.toSQL(value);
    }

    private static String toSQL(Object value) {
        if (value == null) {
            return "NULL";
        }
        if (value instanceof Number) {
            return com.jiuqi.bi.syntax.DataType.formatValue((int)3, (Object)value);
        }
        if (value instanceof Calendar) {
            return com.jiuqi.bi.syntax.DataType.formatValue((int)2, (Object)value);
        }
        if (value instanceof ArrayData) {
            return SQLQueryScriptExcutor.toSQL((ArrayData)value);
        }
        return value.toString();
    }

    private void setParamValues(PreparedStatement stmt, String[] paramNames) throws SQLQueryException {
        int i = 1;
        for (String paramName : paramNames) {
            ISQLQueryListener.ParamInfo param;
            try {
                param = this.findParam(paramName.toUpperCase());
            }
            catch (SyntaxException e) {
                throw new SQLQueryException(e.getMessage(), (Throwable)e);
            }
            if (param == null) {
                throw new SQLQueryException("\u67e5\u627e\u53c2\u6570\u4e0d\u5b58\u5728\uff1a" + paramName);
            }
            this.setParamValue(i, param, stmt);
            ++i;
        }
    }

    private void setParamValue(int i, ISQLQueryListener.ParamInfo param, PreparedStatement stmt) throws SQLQueryException {
        try {
            if (param.value == null) {
                stmt.setNull(i, DataTypes.toJavaSQLType((int)param.dataType));
            } else {
                switch (param.dataType) {
                    case 6: {
                        stmt.setString(i, (String)param.value);
                        break;
                    }
                    case 1: {
                        stmt.setBoolean(i, (Boolean)param.value);
                        break;
                    }
                    case 3: {
                        stmt.setDouble(i, ((Number)param.value).doubleValue());
                        break;
                    }
                    case 10: {
                        stmt.setBigDecimal(i, (BigDecimal)param.value);
                        break;
                    }
                    case 2: {
                        Calendar cal = (Calendar)param.value;
                        Date x = new Date(cal.getTimeInMillis());
                        stmt.setDate(i, x, cal);
                        break;
                    }
                    default: {
                        throw new SQLQueryException("\u8bbe\u7f6eSQL\u7684\u53c2\u6570\u503c\u65f6\u9047\u5230\u672a\u652f\u6301\u7684\u53c2\u6570\u7c7b\u578b\uff1a" + param.name + " - " + com.jiuqi.bi.syntax.DataType.toString((int)param.dataType));
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), (Throwable)e);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private ISQLQueryListener.ParamInfo findParam(String name) throws SyntaxException {
        IParameterEnv parameterEnv;
        String range;
        String paramName;
        int index = name.indexOf(46);
        if (index != -1) {
            paramName = name.substring(0, index);
            range = name.substring(index + 1, name.length());
        } else {
            paramName = name;
            range = null;
        }
        try {
            parameterEnv = this.context.getEnhancedParameterEnv();
        }
        catch (ParameterException e1) {
            throw new SyntaxException(e1.getMessage(), (Throwable)e1);
        }
        ParameterModel model = parameterEnv.getParameterModelByName(paramName);
        if (model == null) {
            return null;
        }
        int dataType = DataType.INTEGER.value() == model.getDataType() ? DataType.DOUBLE.value() : model.getDataType();
        try {
            void var10_18;
            ParameterResultset resultVal = parameterEnv.getValue(paramName);
            List list = resultVal.getValueAsList();
            if (model.isRangeParameter()) {
                if (range == null) {
                    List list2 = list;
                    return new ISQLQueryListener.ParamInfo(paramName, dataType, (Object)var10_18);
                } else if ("min".equalsIgnoreCase(range)) {
                    Object e = list.get(0);
                    return new ISQLQueryListener.ParamInfo(paramName, dataType, (Object)var10_18);
                } else {
                    if (!"max".equalsIgnoreCase(range)) throw new SyntaxException("\u4e0d\u652f\u6301\u7684\u8bed\u6cd5:" + name);
                    Object e = list.get(1);
                }
                return new ISQLQueryListener.ParamInfo(paramName, dataType, (Object)var10_18);
            } else if (model.getSelectMode() == ParameterSelectMode.MUTIPLE) {
                ArrayData array = new ArrayData(dataType, list.size());
                for (int i = 0; i < list.size(); ++i) {
                    array.set(i, list.get(i));
                }
                ArrayData arrayData = array;
                dataType = 11;
                return new ISQLQueryListener.ParamInfo(paramName, dataType, (Object)var10_18);
            } else if (list == null || list.size() == 0) {
                Object var10_16 = null;
                return new ISQLQueryListener.ParamInfo(paramName, dataType, (Object)var10_18);
            } else {
                Object e = list.get(0);
            }
            return new ISQLQueryListener.ParamInfo(paramName, dataType, (Object)var10_18);
        }
        catch (ParameterException e) {
            throw new SyntaxException("\u83b7\u53d6\u53c2\u6570\u4fe1\u606f\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
        }
    }

    public static String toSQL(ArrayData arr) {
        if (arr == null || arr.size() == 0) {
            return "NULL";
        }
        StringBuilder buffer = new StringBuilder("(");
        if (arr.baseType() == 6) {
            boolean started = false;
            for (Object item : arr) {
                if (started) {
                    buffer.append(',');
                } else {
                    started = true;
                }
                buffer.append("'").append(SQLQueryScriptExcutor.encodeStr((String)item)).append("'");
            }
        } else {
            boolean started = false;
            for (Object item : arr) {
                if (started) {
                    buffer.append(',');
                } else {
                    started = true;
                }
                buffer.append(com.jiuqi.bi.syntax.DataType.formatValue((int)arr.baseType(), item));
            }
        }
        buffer.append(')');
        return buffer.toString();
    }

    private static String encodeStr(String s) {
        return s.replaceAll("'", "''");
    }

    private static final class ArrayFormat
    extends Format {
        private static final long serialVersionUID = 1L;

        private ArrayFormat() {
        }

        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            String sql = SQLQueryExecutor.toSQL((ArrayData)((ArrayData)obj));
            return toAppendTo.append(sql);
        }

        @Override
        public Object parseObject(String source, ParsePosition pos) {
            throw new UnsupportedOperationException();
        }
    }

    static final class ParamNode
    extends DynamicNode {
        private static final long serialVersionUID = 1L;
        private ISQLQueryListener.ParamInfo param;

        public ParamNode(Token token, ISQLQueryListener.ParamInfo param) {
            super(token);
            this.param = param;
        }

        public int getType(IContext context) throws SyntaxException {
            return this.param.dataType;
        }

        public Object evaluate(IContext context) throws SyntaxException {
            return this.param.value;
        }

        public boolean isStatic(IContext context) {
            return true;
        }

        public ISQLQueryListener.ParamInfo getParam() {
            return this.param;
        }

        public void toString(StringBuilder buffer) {
            buffer.append(this.param.name);
        }

        public IDataFormator getDataFormator(IContext context) throws SyntaxException {
            if (this.param.dataType == 11 || this.param.dataType == 0 && this.param.value instanceof ArrayData) {
                return new IDataFormator(){

                    public Format getFormator(IContext context) throws SyntaxException {
                        return new ArrayFormat();
                    }
                };
            }
            return null;
        }
    }

    final class ParamProvider
    implements IDynamicNodeProvider {
        ParamProvider() {
        }

        public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
            ISQLQueryListener.ParamInfo param;
            try {
                param = SQLQueryScriptExcutor.this.findParam(refName.toUpperCase());
            }
            catch (SyntaxException e) {
                throw new DynamicNodeException((Throwable)e);
            }
            return param == null ? null : new ParamNode(token, param);
        }

        public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
            StringBuilder refName = new StringBuilder(objPath.get(0));
            for (int i = 1; i < objPath.size(); ++i) {
                refName.append('.').append(objPath.get(i));
            }
            return this.find(context, token, refName.toString());
        }

        public IASTNode findSpec(IContext context, Token token, String refName, String spec) throws DynamicNodeException {
            return null;
        }

        public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
            return null;
        }
    }
}

