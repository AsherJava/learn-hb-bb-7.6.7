/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FuncReadWriteType
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.function.func.extract;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.function.func.extract.DataSetQueryResult;
import com.jiuqi.nr.function.func.extract.QueryUtils;
import java.util.ArrayList;
import java.util.List;

public class GetFromDataSet
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -5452381698293310199L;

    public GetFromDataSet() {
        this.parameters().add(new Parameter("dataSetName", 6, "\u6570\u636e\u96c6\u6807\u8bc6"));
        this.parameters().add(new Parameter("columnName", 6, "\u6570\u636e\u96c6\u5b57\u6bb5\u540d"));
        this.parameters().add(new Parameter("paraValues", 6, "\u53c2\u6570\u503c\uff0c\u683c\u5f0f\uff1aPARAM1=xxx;PARAM2=xxx;..."));
        this.parameters().add(new Parameter("rowID", 6, "\u884c\u5b9a\u4f4d\u6807\u8bc6\uff0c\u683c\u5f0f: COLUMN1=xxx;COLUMN2=xxx;...", true));
    }

    public String name() {
        return "GetFromDataSet";
    }

    public String title() {
        return "\u53d6\u6570\u636e\u96c6\u6570\u636e";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append("\u6570\u636e\u96c6");
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u7684");
        parameters.get(1).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u5217");
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        try {
            String dataSetName = null;
            try {
                dataSetName = (String)parameters.get(0).evaluate((IContext)qContext);
            }
            catch (Exception e) {
                throw new SyntaxException("\u3010\u6570\u636e\u96c6\u6807\u8bc6\u3011\u53d6\u503c\u51fa\u9519\uff0c" + e.getMessage());
            }
            String columnName = null;
            try {
                columnName = (String)parameters.get(1).evaluate((IContext)qContext);
            }
            catch (Exception e) {
                throw new SyntaxException("\u3010\u6570\u636e\u96c6\u5b57\u6bb5\u540d\u3011\u53d6\u503c\u51fa\u9519\uff0c" + e.getMessage());
            }
            String paraValues = null;
            try {
                paraValues = (String)parameters.get(2).evaluate((IContext)qContext);
            }
            catch (Exception e) {
                throw new SyntaxException("\u3010\u6570\u636e\u96c6\u53c2\u6570\u503c\u3011\u53d6\u503c\u51fa\u9519\uff0c" + e.getMessage());
            }
            if (StringUtils.isEmpty((String)paraValues)) {
                throw new SyntaxException("\u6570\u636e\u96c6\u7f3a\u5c11\u53c2\u6570");
            }
            String rowID = null;
            if (parameters.size() > 3) {
                try {
                    rowID = (String)parameters.get(3).evaluate((IContext)qContext);
                }
                catch (Exception e) {
                    throw new SyntaxException("\u3010\u884c\u5b9a\u4f4d\u6807\u8bc6\u3011\u53d6\u503c\u51fa\u9519\uff0c" + e.getMessage());
                }
            }
            ArrayList<String> columnNames = new ArrayList<String>();
            columnNames.add(columnName);
            if (StringUtils.isNotEmpty((String)rowID)) {
                String[] keyColumns;
                for (String keyColumn : keyColumns = rowID.split(";")) {
                    if (!keyColumn.contains("=")) {
                        throw new SyntaxException("\u65e0\u6548\u7684\u5173\u952e\u5217\u683c\u5f0f\uff1a" + keyColumn + "\u7f3a\u5c11'='");
                    }
                    String[] keyStr = keyColumn.split("=");
                    if (keyStr.length < 1) {
                        throw new SyntaxException("\u65e0\u6548\u7684\u5173\u952e\u5217\u683c\u5f0f\uff1a" + keyColumn);
                    }
                    String keyName = keyStr[0];
                    columnNames.add(keyName);
                }
            }
            QueryUtils.checkDataSetQuery(dataSetName, columnNames, paraValues, this);
        }
        catch (Exception e) {
            throw new SyntaxException("\u51fd\u6570" + this.name() + "\u53c2\u6570\u6821\u9a8c\u51fa\u9519\uff1a" + e.getMessage(), (Throwable)e);
        }
        return this.getResultType((IContext)qContext, parameters);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        try {
            String dataSetName = (String)parameters.get(0).evaluate((IContext)qContext);
            String columnName = (String)parameters.get(1).evaluate((IContext)qContext);
            String paraValues = (String)parameters.get(2).evaluate((IContext)qContext);
            String rowID = parameters.size() > 3 ? (String)parameters.get(3).evaluate((IContext)qContext) : null;
            DataSetQueryResult result = QueryUtils.OpenDataSet(qContext, dataSetName, paraValues, this);
            return result.getValue(qContext, rowID, columnName);
        }
        catch (SyntaxException se) {
            throw se;
        }
        catch (Exception e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
    }

    public DataEngineConsts.FuncReadWriteType getReadWriteType() {
        return DataEngineConsts.FuncReadWriteType.UNKNOWN;
    }
}

