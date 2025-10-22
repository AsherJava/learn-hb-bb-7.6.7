/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
 *  com.jiuqi.nvwa.dataextract.DataExtractResult
 *  com.jiuqi.nvwa.dataextract.ExtractDataRow
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.function.func.extract;

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
import com.jiuqi.nr.function.func.extract.QueryUtils;
import com.jiuqi.nvwa.dataextract.DataExtractResult;
import com.jiuqi.nvwa.dataextract.ExtractDataRow;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CopyFromQuery
extends AdvanceFunction {
    private static final long serialVersionUID = -5452381698293310199L;
    private final String type;
    private final String title;

    public CopyFromQuery(String type, String title) {
        this.type = type;
        this.title = title;
        this.parameters().add(new Parameter("queryName", 6, "\u67e5\u8be2\u6a21\u677f\u540d"));
        this.parameters().add(new Parameter("relation", 6, "\u5b57\u6bb5\u5173\u8054 \u4f8b\u5982ZB[1,2]=1;ZB[2,2]=2;ZB[3,2]=3"));
        this.parameters().add(new Parameter("paraValue", 6, "\u53c2\u6570\u503c\u4e0e\u67e5\u8be2\u6a21\u677f\u4e2d\u7684\u53c2\u6570\u6309\u987a\u5e8f\u4e00\u4e00\u5bf9\u5e94"));
    }

    public String name() {
        return "CopyFrom" + this.type;
    }

    public String title() {
        return "\u4ece" + this.title + "\u7684\u67e5\u8be2\u7ed3\u679c\u4e2d\u53d6\u6570";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append("\u4ece");
        buffer.append(this.title);
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
            String queryName = null;
            try {
                queryName = (String)parameters.get(0).evaluate((IContext)qContext);
            }
            catch (Exception e) {
                throw new SyntaxException("\u3010\u6570\u636e\u96c6\u6807\u8bc6\u3011\u53d6\u503c\u51fa\u9519\uff0c" + e.getMessage());
            }
            String relation = null;
            try {
                relation = (String)parameters.get(1).evaluate((IContext)qContext);
            }
            catch (Exception e) {
                throw new SyntaxException("\u3010\u5b57\u6bb5\u5173\u8054\u3011\u53d6\u503c\u51fa\u9519\uff0c" + e.getMessage());
            }
            if (StringUtils.isEmpty((String)relation)) {
                throw new SyntaxException("\u672a\u6307\u5b9a\u6620\u5c04\u5173\u7cfb");
            }
            List<IASTNode> argParas = parameters.subList(2, parameters.size());
            if (argParas == null || argParas.isEmpty()) {
                throw new SyntaxException("\u6570\u636e\u96c6\u7f3a\u5c11\u53c2\u6570");
            }
            ArrayList<Integer> columnIndexes = new ArrayList<Integer>();
            String[] relationArray = relation.split(";");
            String[] descFields = new String[relationArray.length];
            for (int i = 0; i < relationArray.length; ++i) {
                String[] strs = relationArray[i].split("=");
                if (strs == null || strs.length != 2) {
                    throw new SyntaxException("\u6620\u5c04\u5173\u7cfb\u683c\u5f0f\u89e3\u6790\u5931\u8d25:" + relation);
                }
                descFields[i] = strs[0];
                try {
                    int columnIndex = Integer.parseInt((String)strs[1]);
                    columnIndexes.add(columnIndex);
                    continue;
                }
                catch (NumberFormatException e) {
                    throw new SyntaxException("\u76ee\u6807\u6307\u6807" + (String)strs[0] + "\u53d6\u503c\u5217\u53f7\u89e3\u6790\u5931\u8d25");
                }
            }
            ArrayList<Integer> argTypes = new ArrayList<Integer>();
            for (IASTNode p : argParas) {
                argTypes.add(p.getType(context));
            }
            QueryUtils.checkDataExtract(qContext, this.type, queryName, columnIndexes, argTypes, (IReportFunction)this);
            TableModelRunInfo destTableInfo = null;
            HashMap<String, ColumnModelDefine> destColumns = new HashMap<String, ColumnModelDefine>();
            DefinitionsCache cache = qContext.getExeContext().getCache();
            ReportFormulaParser parser = cache.getFormulaParser(true);
            for (String descField : descFields) {
                IExpression exp = null;
                ColumnModelDefine field = null;
                try {
                    exp = parser.parseEval(descField, (IContext)qContext);
                    field = cache.extractFieldDefine((IASTNode)exp);
                }
                catch (ParseException e) {
                    throw new SyntaxException("\u76ee\u6807\u6307\u6807" + descField + "\u89e3\u6790\u5931\u8d25," + e.getMessage(), (Throwable)e);
                }
                if (field == null) {
                    throw new SyntaxException("\u76ee\u6807\u6307\u6807" + descField + "\u4e0d\u5b58\u5728");
                }
                if (destTableInfo == null) {
                    TableModelDefine tableModel = cache.getDataModelDefinitionsCache().getTableModel(field);
                    destTableInfo = cache.getDataModelDefinitionsCache().getTableInfo(tableModel);
                    if (destTableInfo.getBizOrderField() == null) {
                        throw new SyntaxException("\u76ee\u6807\u6307\u6807" + descField + "\u4e0d\u5c5e\u4e8e\u660e\u7ec6\u8868");
                    }
                } else if (!destTableInfo.getColumnFieldMap().containsKey(field)) {
                    throw new SyntaxException("\u76ee\u6807\u6307\u6807\u4e0d\u5c5e\u4e8e\u540c\u4e00\u4e2a\u660e\u7ec6\u8868");
                }
                destColumns.put(field.getID(), field);
            }
            QueryUtils.checkDestNotNullColumn(qContext, destTableInfo, destColumns);
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
            String relation = (String)parameters.get(1).evaluate((IContext)qContext);
            List<IASTNode> argParas = parameters.subList(2, parameters.size());
            DataExtractResult result = QueryUtils.getResult(qContext, this.type, queryName, argParas, (IReportFunction)this);
            if (result != null) {
                String[] relationArray = relation.split(";");
                String[] descFields = new String[relationArray.length];
                int[] columnIndexes = new int[relationArray.length];
                for (int i = 0; i < relationArray.length; ++i) {
                    String[] strs = relationArray[i].split("=");
                    descFields[i] = strs[0];
                    columnIndexes[i] = Integer.parseInt(strs[1]) - 1;
                }
                DimensionValueSet currValueSet = qContext.getCurrentMasterKey();
                ColumnModelDefine floatOrderField = null;
                DefinitionsCache cache = qContext.getExeContext().getCache();
                DataModelDefinitionsCache tableCache = cache.getDataModelDefinitionsCache();
                String rowFilter = null;
                String regionKey = null;
                IDataModelLinkFinder dataModelLinkFinder = qContext.getExeContext().getEnv().getDataModelLinkFinder();
                TableModelRunInfo destTableInfo = null;
                HashMap<String, ColumnModelDefine> destColumns = new HashMap<String, ColumnModelDefine>();
                ReportFormulaParser parser = qContext.getExeContext().getCache().getFormulaParser(true);
                if (monitor != null && monitor.isDebug()) {
                    monitor.message("\u539f\u6620\u5c04\u8868\u8fbe\u5f0f\uff1a" + relation, (Object)this);
                }
                for (int index = 0; index < descFields.length; ++index) {
                    Object tableModel;
                    String descField = descFields[index];
                    IExpression exp = null;
                    ColumnModelDefine field = null;
                    try {
                        exp = parser.parseEval(descField, (IContext)qContext);
                        field = cache.extractFieldDefine((IASTNode)exp);
                    }
                    catch (ParseException e) {
                        throw new SyntaxException("\u76ee\u6807\u6307\u6807" + (String)descField + "\u89e3\u6790\u5931\u8d25," + e.getMessage(), (Throwable)e);
                    }
                    if (field == null) {
                        throw new SyntaxException("\u76ee\u6807\u6307\u6807" + (String)descField + "\u4e0d\u5b58\u5728");
                    }
                    if (destTableInfo == null) {
                        tableModel = cache.getDataModelDefinitionsCache().getTableModel(field);
                        destTableInfo = cache.getDataModelDefinitionsCache().getTableInfo((TableModelDefine)tableModel);
                        if (destTableInfo.getBizOrderField() == null) {
                            throw new SyntaxException("\u76ee\u6807\u6307\u6807" + (String)descField + "\u4e0d\u5c5e\u4e8e\u660e\u7ec6\u8868");
                        }
                    } else if (!destTableInfo.getColumnFieldMap().containsKey(field)) {
                        throw new SyntaxException("\u76ee\u6807\u6307\u6807\u4e0d\u5c5e\u4e8e\u540c\u4e00\u4e2a\u660e\u7ec6\u8868");
                    }
                    destColumns.put(field.getID(), field);
                    if (monitor != null && monitor.isDebug()) {
                        monitor.message(relationArray[index] + "\uff1a" + DataTypes.toString((int)field.getColumnType().getValue()) + "=" + DataTypes.toString((int)result.getDataType(index)), (Object)this);
                    }
                    if (regionKey == null) {
                        for (IASTNode node : exp) {
                            if (!(node instanceof DynamicDataNode)) continue;
                            DynamicDataNode dataNode = (DynamicDataNode)node;
                            regionKey = dataNode.getDataModelLink().getRegion();
                        }
                    }
                    if (floatOrderField != null) continue;
                    tableModel = tableCache.getTableModel(field);
                    TableModelRunInfo tableInfo = tableCache.getTableInfo(tableModel.getName());
                    floatOrderField = tableInfo.getOrderField();
                }
                List innerDimensions = destTableInfo.getInnerDimensions();
                if (innerDimensions != null) {
                    for (String innerDim : innerDimensions) {
                        ColumnModelDefine dimField = destTableInfo.getDimensionField(innerDim);
                        if (destColumns.containsKey(dimField.getID())) continue;
                        throw new SyntaxException("\u76ee\u6807\u8868" + dimField.getCode() + "\u4e0d\u53ef\u4e3a\u7a7a");
                    }
                }
                if (regionKey != null) {
                    rowFilter = dataModelLinkFinder.getRegionCondition(qContext.getExeContext(), regionKey);
                }
                FieldDefine floatField = tableCache.getFieldDefine(floatOrderField);
                IDataUpdator destDataUpdator = this.getDestDataUpdator(qContext, descFields, floatField, currValueSet, rowFilter);
                for (int r = 0; r < result.size(); ++r) {
                    ExtractDataRow srcRow = result.getRow(r);
                    IDataRow row = destDataUpdator.addInsertedRow();
                    for (int i = 0; i < descFields.length; ++i) {
                        row.setValue(i, srcRow.getValue(columnIndexes[i]));
                    }
                    if (floatOrderField == null) continue;
                    int floatorder = (r + 1) * 1000;
                    row.setValue(floatField, (Object)floatorder);
                }
                LogHelper.info((String)"\u8fd0\u7b97\u5ba1\u6838", (String)(this.name() + "\u51fd\u6570"), (String)("\u6309\u4e3b\u7ef4\u5ea6" + currValueSet + "\u6e05\u9664\u6570\u636e\u8868" + destTableInfo.getTableModelDefine().getName() + "\u7684\u8bb0\u5f55"));
                destDataUpdator.commitChanges();
                if (monitor != null && monitor.isDebug()) {
                    monitor.message("\u62f7\u8d1d\u8bb0\u5f55\u6570\uff1a" + result.size(), (Object)this);
                }
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

