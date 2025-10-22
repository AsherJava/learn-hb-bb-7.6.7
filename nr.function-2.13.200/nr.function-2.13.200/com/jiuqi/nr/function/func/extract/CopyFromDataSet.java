/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataRow
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FuncReadWriteType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.DataTypes
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.parse.AdvanceFunction
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.function.func.extract;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.parse.AdvanceFunction;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.function.func.extract.DataSetQueryResult;
import com.jiuqi.nr.function.func.extract.QueryUtils;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CopyFromDataSet
extends AdvanceFunction {
    private static final long serialVersionUID = -5452381698293310199L;

    public CopyFromDataSet() {
        this.parameters().add(new Parameter("dataSetName", 6, "\u6570\u636e\u96c6\u6807\u8bc6"));
        this.parameters().add(new Parameter("columnMap", 6, "\u5b57\u6bb5\u5173\u8054,\u683c\u5f0f\uff1aZB[1,2]=COLUMN1;ZB[2,2]=COLUMN1;..."));
        this.parameters().add(new Parameter("paraValues", 6, "\u53c2\u6570\u503c\uff0c\u683c\u5f0f\uff1aPARAM1=xxx;PARAM2=xxx;..."));
    }

    public String name() {
        return "CopyFromDataSet";
    }

    public String title() {
        return "\u590d\u5236\u6570\u636e\u96c6\u6570\u636e";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append("\u4ece\u6570\u636e\u96c6");
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u4e2d\u590d\u5236\u6570\u636e");
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
            String dataSetName = null;
            try {
                dataSetName = (String)parameters.get(0).evaluate((IContext)qContext);
            }
            catch (Exception e) {
                throw new SyntaxException("\u3010\u6570\u636e\u96c6\u6807\u8bc6\u3011\u53d6\u503c\u51fa\u9519\uff0c" + e.getMessage());
            }
            String columnMap = null;
            try {
                columnMap = (String)parameters.get(1).evaluate((IContext)qContext);
            }
            catch (Exception e) {
                throw new SyntaxException("\u3010\u5b57\u6bb5\u5173\u8054\u3011\u53d6\u503c\u51fa\u9519\uff0c" + e.getMessage());
            }
            if (StringUtils.isEmpty((String)columnMap)) {
                throw new SyntaxException("\u53d6\u503c\u5217\u6620\u5c04\u5173\u7cfb\u4e0d\u80fd\u4e3a\u7a7a\uff01");
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
            String[] relationArray = columnMap.split(";");
            ArrayList<String> columnNames = new ArrayList<String>();
            DefinitionsCache cache = qContext.getExeContext().getCache();
            ReportFormulaParser parser = cache.getFormulaParser(true);
            HashMap<String, ColumnModelDefine> destColumns = new HashMap<String, ColumnModelDefine>();
            TableModelRunInfo destTableInfo = null;
            for (int i = 0; i < relationArray.length; ++i) {
                String[] strs = relationArray[i].split("=");
                if (strs.length != 2) {
                    throw new SyntaxException("\u65e0\u6548\u7684\u5217\u6620\u5c04\uff1a" + relationArray[i]);
                }
                columnNames.add(strs[1]);
                String destExp = strs[0];
                try {
                    IExpression exp = parser.parseEval(destExp, (IContext)qContext);
                    ColumnModelDefine columnModel = cache.extractFieldDefine((IASTNode)exp);
                    if (columnModel == null) {
                        throw new SyntaxException("\u76ee\u6807\u6307\u6807" + destExp + "\u4e0d\u5b58\u5728");
                    }
                    if (destTableInfo == null) {
                        TableModelDefine tableModel = cache.getDataModelDefinitionsCache().getTableModel(columnModel);
                        destTableInfo = cache.getDataModelDefinitionsCache().getTableInfo(tableModel);
                        if (destTableInfo.getBizOrderField() == null) {
                            throw new SyntaxException("\u76ee\u6807\u6307\u6807" + destExp + "\u4e0d\u5c5e\u4e8e\u660e\u7ec6\u8868");
                        }
                    } else if (!destTableInfo.getColumnFieldMap().containsKey(columnModel)) {
                        throw new SyntaxException("\u76ee\u6807\u6307\u6807\u4e0d\u5c5e\u4e8e\u540c\u4e00\u4e2a\u660e\u7ec6\u8868");
                    }
                    destColumns.put(columnModel.getID(), columnModel);
                    continue;
                }
                catch (ParseException e) {
                    throw new SyntaxException("\u76ee\u6807\u6307\u6807" + destExp + "\u89e3\u6790\u5931\u8d25," + e.getMessage(), (Throwable)e);
                }
            }
            QueryUtils.checkDestNotNullColumn(qContext, destTableInfo, destColumns);
            QueryUtils.checkDataSetQuery(dataSetName, columnNames, paraValues, (IReportFunction)this);
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
            String dataSetName = (String)parameters.get(0).evaluate((IContext)qContext);
            String columnMap = (String)parameters.get(1).evaluate((IContext)qContext);
            String paraValues = (String)parameters.get(2).evaluate((IContext)qContext);
            DataSetQueryResult result = QueryUtils.OpenDataSet(qContext, dataSetName, paraValues, (IReportFunction)this);
            Metadata<BIDataSetFieldInfo> metadata = result.getMetadata();
            String[] relationArray = columnMap.split(";");
            String[] descFields = new String[relationArray.length];
            int[] columnIndexes = new int[relationArray.length];
            for (int i = 0; i < relationArray.length; ++i) {
                String[] strs = relationArray[i].split("=");
                if (strs.length != 2) {
                    throw new SyntaxException("\u65e0\u6548\u7684\u5217\u6620\u5c04\uff1a" + relationArray[i]);
                }
                descFields[i] = strs[0];
                columnIndexes[i] = metadata.indexOf(strs[1]);
                if (columnIndexes[i] >= 0) continue;
                throw new SyntaxException("\u65e0\u6548\u7684\u6570\u636e\u96c6\u5217\u540d\uff1a" + dataSetName + "." + strs[1]);
            }
            DimensionValueSet currValueSet = qContext.getCurrentMasterKey();
            ColumnModelDefine floatOrderField = null;
            DefinitionsCache cache = qContext.getExeContext().getCache();
            DataModelDefinitionsCache tableCache = cache.getDataModelDefinitionsCache();
            String rowFilter = null;
            String regionKey = null;
            IDataModelLinkFinder dataModelLinkFinder = qContext.getExeContext().getEnv().getDataModelLinkFinder();
            if (monitor != null && monitor.isDebug()) {
                monitor.message("\u539f\u6620\u5c04\u8868\u8fbe\u5f0f\uff1a" + columnMap, (Object)this);
            }
            TableModelRunInfo tableInfo = null;
            for (int index = 0; index < descFields.length; ++index) {
                String descField = descFields[index];
                ReportFormulaParser parser = qContext.getExeContext().getCache().getFormulaParser(true);
                IExpression exp = parser.parseEval(descField, (IContext)qContext);
                if (regionKey == null) {
                    for (IASTNode node : exp) {
                        if (!(node instanceof DynamicDataNode)) continue;
                        DynamicDataNode dataNode = (DynamicDataNode)node;
                        regionKey = dataNode.getDataModelLink().getRegion();
                    }
                }
                ColumnModelDefine field = cache.extractFieldDefine((IASTNode)exp);
                if (monitor != null && monitor.isDebug()) {
                    monitor.message(relationArray[index] + "\uff1a" + DataTypes.toString((int)field.getColumnType().getValue()) + "=" + DataTypes.toString((int)metadata.getColumn(index).getDataType()), (Object)this);
                }
                if (field != null) {
                    TableModelDefine tableModel = tableCache.getTableModel(field);
                    tableInfo = tableCache.getTableInfo(tableModel.getName());
                    floatOrderField = tableInfo.getOrderField();
                }
                if (floatOrderField != null) break;
            }
            if (regionKey != null) {
                rowFilter = dataModelLinkFinder.getRegionCondition(qContext.getExeContext(), regionKey);
            }
            FieldDefine floatField = tableCache.getFieldDefine(floatOrderField);
            IDataUpdator destDataUpdator = this.getDestDataUpdator(qContext, descFields, floatField, currValueSet, rowFilter);
            for (int r = 0; r < result.size(); ++r) {
                BIDataRow srcRow = result.getRow(r);
                IDataRow row = destDataUpdator.addInsertedRow();
                for (int i = 0; i < descFields.length; ++i) {
                    row.setValue(i, srcRow.getValue(columnIndexes[i]));
                }
                if (floatOrderField == null) continue;
                int floatorder = (r + 1) * 1000;
                row.setValue(floatField, (Object)floatorder);
            }
            LogHelper.info((String)"\u8fd0\u7b97\u5ba1\u6838", (String)"CopyFromDataSet\u51fd\u6570", (String)("\u6309\u4e3b\u7ef4\u5ea6" + currValueSet + "\u6e05\u9664\u6570\u636e\u8868" + tableInfo.getTableModelDefine().getName() + "\u7684\u8bb0\u5f55"));
            destDataUpdator.commitChanges();
            if (monitor != null && monitor.isDebug()) {
                monitor.message("\u62f7\u8d1d\u8bb0\u5f55\u6570\uff1a" + result.size(), (Object)this);
            }
            return true;
        }
        catch (Exception e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
    }

    private IDataUpdator getDestDataUpdator(QueryContext qContext, String[] fieldExpressions, FieldDefine floatOrderField, DimensionValueSet dimValueSet, String rowFilter) throws Exception {
        IDataAccessProvider accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        IDataQuery request = accessProvider.newDataQuery();
        for (String column : fieldExpressions) {
            request.addExpressionColumn(column);
        }
        request.addColumn(floatOrderField);
        request.setQueryParam(qContext.getQueryParam());
        request.setMasterKeys(dimValueSet);
        request.setRowFilter(rowFilter);
        request.setDefaultGroupName(qContext.getDefaultGroupName());
        IDataUpdator updator = request.openForUpdate(qContext.getExeContext(), true);
        return updator;
    }

    public DataEngineConsts.FuncReadWriteType getReadWriteType() {
        return DataEngineConsts.FuncReadWriteType.UNKNOWN;
    }
}

