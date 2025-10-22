/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.node.CellDataNode
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.reader.IQueryFieldDataReader
 *  com.jiuqi.np.dataengine.reader.MemoryDataSetReader
 *  com.jiuqi.np.dataengine.reader.MemoryRowData
 */
package com.jiuqi.nr.function.func.deprecated;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.MemoryDataSetReader;
import com.jiuqi.np.dataengine.reader.MemoryRowData;
import java.util.List;

public class PrevLineData
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 2404890266762622129L;

    public PrevLineData() {
        this.parameters().add(new Parameter("cell", 0, "\u5355\u5143\u683c"));
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public String name() {
        return "PREVLINEDATA";
    }

    public String title() {
        return "\u53d6\u6d6e\u52a8\u884c\u4e0a\u4e00\u884c\u6307\u6807\u6570\u636e";
    }

    public boolean isDeprecated() {
        return true;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        try {
            QueryContext qContext = (QueryContext)context;
            IQueryFieldDataReader dataReader = qContext.getDataReader();
            QueryField queryField = this.getQueryField(parameters.get(0));
            if (queryField != null && dataReader instanceof MemoryDataSetReader) {
                MemoryDataSetReader memoryDataSetReader = (MemoryDataSetReader)dataReader;
                int currentIndex = memoryDataSetReader.getCurrentIndex();
                if (currentIndex <= 0) {
                    return null;
                }
                MemoryRowData prevData = memoryDataSetReader.getRowData(--currentIndex);
                DimensionValueSet prevRowKey = memoryDataSetReader.getRowKey(currentIndex);
                if (prevData == null || prevRowKey == null) {
                    return null;
                }
                Object dataValue = memoryDataSetReader.getValue(prevData, prevRowKey, queryField);
                return dataValue;
            }
        }
        catch (Exception e) {
            return null;
        }
        return null;
    }

    private QueryField getQueryField(IASTNode paramNode) {
        IASTNode dataNode = paramNode;
        if (paramNode instanceof CellDataNode) {
            CellDataNode cellDataNode = (CellDataNode)paramNode;
            dataNode = cellDataNode.getChild(0);
        }
        if (dataNode instanceof DynamicDataNode) {
            DynamicDataNode dynamicDataNode = (DynamicDataNode)dataNode;
            return dynamicDataNode.getQueryField();
        }
        return null;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (parameters != null) {
            return parameters.get(0).getType(context);
        }
        return ((IParameter)this.parameters().get(0)).dataType();
    }
}

