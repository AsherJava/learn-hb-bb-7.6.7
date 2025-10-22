/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.node.CellDataNode
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.reader.IQueryFieldDataReader
 *  com.jiuqi.np.dataengine.reader.MemoryDataSetReader
 *  com.jiuqi.np.dataengine.reader.MemoryRowData
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.function.func.floats;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.MemoryDataSetReader;
import com.jiuqi.np.dataengine.reader.MemoryRowData;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FloatPrevData
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -3140472624949850485L;

    public FloatPrevData() {
        this.parameters().add(new Parameter("evalExp", 0, "\u6d6e\u52a8\u5b57\u6bb5\u6216\u8868\u8fbe\u5f0f(\u6ce8\u610f\u4e0d\u8981\u52a0\u5f15\u53f7,\u4f7f\u7528\u5750\u6807\u8bed\u6cd5)"));
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IASTNode p0 = parameters.get(0);
        QueryContext qContext = (QueryContext)context;
        try {
            for (IASTNode node : p0) {
                MemoryDataSetReader reader;
                MemoryRowData rowData;
                String bizkeyOrderKey;
                Integer rowIndex;
                IQueryFieldDataReader dataReader;
                if (!(node instanceof CellDataNode)) continue;
                CellDataNode cellNode = (CellDataNode)node;
                DataModelLinkColumn dataModelLinkColumn = cellNode.getDataModelLinkColumn();
                String regionKey = dataModelLinkColumn.getRegion();
                String cacheKey = this.name() + "_" + dataModelLinkColumn.getDataLinkCode() + "_" + qContext.getCurrentMasterKey().toString();
                FloatOrderIndex orderIndex = (FloatOrderIndex)qContext.getCache().get(cacheKey);
                if (orderIndex == null) {
                    ExecutorContext exeContext = qContext.getExeContext();
                    ReportFmlExecEnvironment destEnv = (ReportFmlExecEnvironment)exeContext.getEnv();
                    DataRegionDefine region = destEnv.getController().queryDataRegionDefine(regionKey);
                    String tableCode = exeContext.getCache().getDataModelDefinitionsCache().getTableModel(dataModelLinkColumn.getColumModel()).getCode();
                    IDataQuery dataQuery = this.createDataQuery(qContext, tableCode, node.interpret((IContext)qContext, Language.FORMULA, (Object)new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA)), region);
                    IReadonlyTable table = dataQuery.executeReader(exeContext);
                    orderIndex = new FloatOrderIndex(table, p0.getType((IContext)qContext));
                    qContext.getCache().put(cacheKey, orderIndex);
                }
                if (!((dataReader = qContext.getDataReader()) instanceof MemoryDataSetReader) || (rowIndex = orderIndex.indexOf(bizkeyOrderKey = (rowData = (reader = (MemoryDataSetReader)dataReader).getRowDatas()).getRecKey())) == null) continue;
                return orderIndex.getValue(rowIndex - 1, 1);
            }
        }
        catch (Exception e) {
            throw new SyntaxException((Throwable)e);
        }
        return null;
    }

    private IDataQuery createDataQuery(QueryContext qContext, String tableCode, String expression, DataRegionDefine region) throws ParseException {
        IDataAccessProvider accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        IDataQuery request = accessProvider.newDataQuery();
        request.addExpressionColumn("[BIZKEYORDER]");
        request.addExpressionColumn(expression);
        request.setQueryParam(qContext.getQueryParam());
        request.setDefaultGroupName(tableCode);
        this.setOrderBy(qContext.getExeContext(), region.getSortFieldsList(), request);
        request.setRowFilter(region.getFilterCondition());
        request.setMasterKeys(new DimensionValueSet(qContext.getCurrentMasterKey()));
        return request;
    }

    private void setOrderBy(ExecutorContext srcExeContext, String sortingInfoList, IDataQuery request) throws ParseException {
        if (StringUtils.isNotEmpty((String)sortingInfoList)) {
            String[] sortingInfos = sortingInfoList.split(";");
            for (int i = 0; i < sortingInfos.length; ++i) {
                String sortingInfo = sortingInfos[i];
                boolean desc = false;
                String fieldKey = "";
                if (sortingInfo.endsWith("+")) {
                    desc = false;
                    fieldKey = sortingInfo.substring(0, sortingInfo.length() - 1);
                } else if (sortingInfo.endsWith("-")) {
                    desc = true;
                    fieldKey = sortingInfo.substring(0, sortingInfo.length() - 1);
                } else {
                    fieldKey = sortingInfo;
                }
                FieldDefine orderField = srcExeContext.getCache().getDataDefinitionsCache().findField(fieldKey);
                request.addOrderByItem(orderField, desc);
            }
        }
        request.addOrderByItem("[FLOATORDER]", false);
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String name() {
        return "FloatPrevData";
    }

    public String title() {
        return "\u53d6\u6d6e\u52a8\u884c\u4e0a\u4e00\u884c\u67d0\u5b57\u6bb5\u7684\u6570\u636e";
    }

    private class FloatOrderIndex {
        private Map<String, Integer> rowSearch = new HashMap<String, Integer>();
        private IReadonlyTable table = null;
        private int resultType;

        public FloatOrderIndex(IReadonlyTable table, int resultType) {
            this.table = table;
            this.resultType = resultType;
            for (int index = 0; index < table.getCount(); ++index) {
                IDataRow dataRow = table.getItem(index);
                String bizkeyOrder = dataRow.getAsString(0);
                this.rowSearch.put(bizkeyOrder, index);
            }
        }

        public Integer indexOf(String key) {
            return this.rowSearch.get(key);
        }

        public Object getValue(int rowIndex, int columnIndex) {
            if (rowIndex < 0) {
                return null;
            }
            return DataTypesConvert.toASTNodeData((Object)this.table.getItem(rowIndex).getValue(columnIndex).getAsObject(), (int)this.resultType);
        }
    }
}

