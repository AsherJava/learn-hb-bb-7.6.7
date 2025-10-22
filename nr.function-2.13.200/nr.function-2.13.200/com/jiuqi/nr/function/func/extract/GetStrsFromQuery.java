/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FuncReadWriteType
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.dataextract.DataExtractResult
 *  com.jiuqi.nvwa.dataextract.ExtractDataRow
 */
package com.jiuqi.nr.function.func.extract;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.function.func.extract.QueryUtils;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.dataextract.DataExtractResult;
import com.jiuqi.nvwa.dataextract.ExtractDataRow;
import java.util.ArrayList;
import java.util.List;

public class GetStrsFromQuery
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -5452381698293310199L;
    private final String type;
    private final String title;

    public GetStrsFromQuery(String type, String title) {
        this.type = type;
        this.title = title;
        this.parameters().add(new Parameter("queryName", 6, "\u67e5\u8be2\u6a21\u677f\u540d"));
        this.parameters().add(new Parameter("columnIndex", 3, "\u67e5\u8be2\u6570\u636e\u5217\u53f7\uff0c\u4ece1\u5f00\u59cb\u8ba1\u6570\uff0c\u8981\u6c42\u5217\u7684\u8fd4\u56de\u503c\u5fc5\u987b\u4e3a\u5b57\u7b26\u578b"));
        this.parameters().add(new Parameter("paraValue", 6, "\u53c2\u6570\u503c\u4e0e\u67e5\u8be2\u6a21\u677f\u4e2d\u7684\u53c2\u6570\u6309\u987a\u5e8f\u4e00\u4e00\u5bf9\u5e94"));
    }

    public String name() {
        return "GetStrsFrom" + this.type;
    }

    public String title() {
        return "\u4ece" + this.title + "\u7684\u67e5\u8be2\u7ed3\u679c\u4e2d\u53d6\u6570\u6574\u5217\u5b57\u7b26\u578b\u6570\u636e\u7ec4\u6210\u6570\u7ec4";
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 11;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        try {
            String queryName = (String)parameters.get(0).evaluate((IContext)qContext);
            int columnIndex = Convert.toInt((Object)parameters.get(1).evaluate((IContext)qContext));
            List<IASTNode> argParas = parameters.subList(2, parameters.size());
            DataExtractResult result = QueryUtils.getResult(qContext, this.type, queryName, argParas, this);
            if (result != null) {
                ArrayList<String> values = new ArrayList<String>();
                for (int r = 0; r < result.size(); ++r) {
                    ExtractDataRow row = result.getRow(r);
                    if (row == null || columnIndex <= 0 || columnIndex > row.getFieldSize()) continue;
                    Object value = row.getValue(columnIndex - 1);
                    values.add(value == null ? "" : value.toString());
                }
                ArrayData array = new ArrayData(6, values);
                return array;
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

