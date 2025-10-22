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
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.dataextract.DataExtractResult
 *  com.jiuqi.nvwa.dataextract.ExtractDataRow
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
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.function.func.extract.QueryUtils;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.dataextract.DataExtractResult;
import com.jiuqi.nvwa.dataextract.ExtractDataRow;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetFromQuery
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -5452381698293310199L;
    private final String type;
    private final String title;

    public GetFromQuery(String type, String title) {
        this.type = type;
        this.title = title;
        this.parameters().add(new Parameter("queryName", 6, "\u67e5\u8be2\u6a21\u677f\u540d"));
        this.parameters().add(new Parameter("columnIndex", 3, "\u67e5\u8be2\u6570\u636e\u5217\u53f7\uff0c\u4ece1\u5f00\u59cb\u8ba1\u6570"));
        this.parameters().add(new Parameter("keyColumnIndexes", 6, "\u5173\u952e\u5217\u5e8f\u53f7\uff0c\u652f\u6301\u591a\u4e2a\u5173\u952e\u5217\uff0c\u4ee5\u5206\u53f7\u9694\u5f00\u3002\u5982\u679c\u9884\u671f\u7ed3\u679c\u53ea\u6709\u4e00\u884c\uff0c\u5219\u53ef\u4ee5\u4e3a\u7a7a"));
        this.parameters().add(new Parameter("keyColumnValues", 6, "\u5173\u952e\u5217\u503c\uff0c\u652f\u6301\u591a\u4e2a\u5173\u952e\u5217\uff0c\u4ee5\u5206\u53f7\u9694\u5f00\u3002\u5982\u679c\u9884\u671f\u7ed3\u679c\u53ea\u6709\u4e00\u884c\uff0c\u5219\u53ef\u4ee5\u4e3a\u7a7a"));
        this.parameters().add(new Parameter("paraValue", 6, "\u53c2\u6570\u503c\u4e0e\u67e5\u8be2\u6a21\u677f\u4e2d\u7684\u53c2\u6570\u6309\u987a\u5e8f\u4e00\u4e00\u5bf9\u5e94"));
    }

    public String name() {
        return "GetFrom" + this.type;
    }

    public String title() {
        return "\u4ece" + this.title + "\u7684\u67e5\u8be2\u7ed3\u679c\u4e2d\u53d6\u6570";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append(this.title);
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u7684\u7b2c");
        parameters.get(1).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u5217");
    }

    public boolean isInfiniteParameter() {
        return true;
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
            String queryName = null;
            try {
                queryName = (String)parameters.get(0).evaluate((IContext)qContext);
            }
            catch (Exception e) {
                throw new SyntaxException("\u3010\u6570\u636e\u96c6\u6807\u8bc6\u3011\u53d6\u503c\u51fa\u9519\uff0c" + e.getMessage());
            }
            ArrayList<Integer> columnIndexes = new ArrayList<Integer>();
            int columnIndex = Convert.toInt((Object)parameters.get(1).evaluate((IContext)qContext));
            columnIndexes.add(columnIndex);
            String keyColumnIndexes = (String)parameters.get(2).evaluate((IContext)qContext);
            String keyColumnValues = null;
            try {
                keyColumnValues = (String)parameters.get(3).evaluate((IContext)qContext);
            }
            catch (Exception e) {
                throw new SyntaxException("\u3010\u5173\u952e\u5217\u503c\u3011\u53d6\u503c\u51fa\u9519\uff0c" + e.getMessage());
            }
            if (StringUtils.isNotEmpty((String)keyColumnIndexes)) {
                String[] strs = keyColumnIndexes.split(";");
                if (StringUtils.isEmpty((String)keyColumnValues)) {
                    throw new SyntaxException("\u5173\u952e\u5217\u503c\u4e0d\u53ef\u4e3a\u7a7a");
                }
                String[] values = keyColumnValues.split(";");
                if (strs.length != values.length) {
                    throw new SyntaxException("\u5173\u952e\u5217\u4e2a\u6570\u4e3a" + strs.length + ",\u5173\u952e\u5217\u503c\u4e2a\u6570\u4e3a" + values.length + ",\u4e24\u8005\u4e0d\u4e00\u81f4");
                }
                for (int i = 0; i < strs.length; ++i) {
                    try {
                        int keyIndex = Integer.parseInt(strs[i]);
                        columnIndexes.add(keyIndex);
                        continue;
                    }
                    catch (NumberFormatException e) {
                        throw new SyntaxException("\u5173\u952e\u5217\u53f7" + strs[i] + "\u89e3\u6790\u5931\u8d25");
                    }
                }
            }
            List<IASTNode> argParas = parameters.subList(4, parameters.size());
            ArrayList<Integer> argTypes = new ArrayList<Integer>();
            for (IASTNode p : argParas) {
                argTypes.add(p.getType(context));
            }
            QueryUtils.checkDataExtract(qContext, this.type, queryName, columnIndexes, argTypes, this);
        }
        catch (Exception e) {
            throw new SyntaxException("\u51fd\u6570" + this.name() + "\u53c2\u6570\u6821\u9a8c\u51fa\u9519\uff1a" + e.getMessage(), (Throwable)e);
        }
        return this.getResultType((IContext)qContext, parameters);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        IMonitor monitor = qContext.getMonitor();
        try {
            String queryName = (String)parameters.get(0).evaluate((IContext)qContext);
            int columnIndex = Convert.toInt((Object)parameters.get(1).evaluate((IContext)qContext));
            String keyColumnIndexes = (String)parameters.get(2).evaluate((IContext)qContext);
            String keyColumnValues = (String)parameters.get(3).evaluate((IContext)qContext);
            List<IASTNode> argParas = parameters.subList(4, parameters.size());
            DataExtractResult result = QueryUtils.getResult(qContext, this.type, queryName, argParas, this);
            if (result != null) {
                if (monitor != null && monitor.isDebug()) {
                    monitor.message("\u67e5\u8be2\u5217\u53f7\uff1a" + columnIndex, (Object)this);
                    monitor.message("\u67e5\u8be2\u5217\u540d\uff1a" + result.getColumnName(columnIndex - 1), (Object)this);
                }
                ExtractDataRow row = null;
                if (StringUtils.isNotEmpty((String)keyColumnValues)) {
                    Map searchMap = result.findSearchMap(keyColumnIndexes);
                    row = (ExtractDataRow)searchMap.get(keyColumnValues);
                    ArrayList<Integer> columnIndexes = new ArrayList<Integer>();
                    if (monitor != null && monitor.isDebug()) {
                        int keyIndex;
                        int i;
                        String[] strs = keyColumnIndexes.split(";");
                        if (StringUtils.isEmpty((String)keyColumnValues)) {
                            throw new SyntaxException("\u5173\u952e\u5217\u503c\u4e0d\u53ef\u4e3a\u7a7a");
                        }
                        String[] values = keyColumnValues.split(";");
                        if (strs.length != values.length) {
                            throw new SyntaxException("\u5173\u952e\u5217\u4e2a\u6570\u4e3a" + strs.length + ",\u5173\u952e\u5217\u503c\u4e2a\u6570\u4e3a" + values.length + ",\u4e24\u8005\u4e0d\u4e00\u81f4");
                        }
                        for (i = 0; i < strs.length; ++i) {
                            try {
                                keyIndex = Integer.parseInt(strs[i]);
                                columnIndexes.add(keyIndex - 1);
                                continue;
                            }
                            catch (NumberFormatException e) {
                                throw new SyntaxException("\u5173\u952e\u5217\u53f7" + strs[i] + "\u89e3\u6790\u5931\u8d25");
                            }
                        }
                        for (i = 0; i < columnIndexes.size(); ++i) {
                            keyIndex = (Integer)columnIndexes.get(i);
                            String columnName = result.getColumnName(keyIndex);
                            monitor.message("\u5173\u952e\u5217\u53d6\u503c\uff1a" + columnName + "=" + values[i], (Object)this);
                        }
                    }
                } else if (result.size() > 0) {
                    row = result.getRow(0);
                }
                if (row != null && columnIndex > 0 && columnIndex <= row.getFieldSize()) {
                    if (monitor != null && monitor.isDebug()) {
                        monitor.message("\u5b9a\u4f4d\u5230\u6570\u636e\u884c\uff1a" + row.toString(), (Object)this);
                    }
                    return row.getValue(columnIndex - 1);
                }
            }
        }
        catch (Exception e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
        return null;
    }

    public DataEngineConsts.FuncReadWriteType getReadWriteType() {
        return DataEngineConsts.FuncReadWriteType.UNKNOWN;
    }
}

