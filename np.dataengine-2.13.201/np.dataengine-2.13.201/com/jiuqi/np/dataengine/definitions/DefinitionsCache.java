/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportCellProvider
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.np.dataengine.definitions;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportCellProvider;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.LookupKeyItem;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFieldBuilder;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.DataDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableAllocator;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.DynamicDataNodeFinder;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.parse.CellFmlProvider;
import com.jiuqi.np.dataengine.parse.link.CellFmlProvider_link;
import com.jiuqi.np.dataengine.parse.link.DynamicDataNodeFinder_link;
import com.jiuqi.np.dataengine.parse.link.ReportFormulaParser_link;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public class DefinitionsCache {
    private ReportFormulaParser parser;
    private ReportFormulaParser JQReportModelParser;
    private final DataDefinitionsCache dataDefinitionsCache;
    private final IDataDefinitionRuntimeController controller;
    private DataModelDefinitionsCache dataModelDefinitionsCache;
    private ReportFormulaParser_link dataLinkFormulaParser;

    public DefinitionsCache(ExecutorContext context) throws ParseException {
        this.createFormulaParser(context);
        this.controller = context.getRuntimeController();
        this.dataDefinitionsCache = new DataDefinitionsCache(context, this.controller, this.getFormulaParser(context));
        this.dataModelDefinitionsCache = new DataModelDefinitionsCache(context, this.getFormulaParser(context));
    }

    public DefinitionsCache(ExecutorContext context, DefinitionsCache another, IDataDefinitionDesignTimeController designTimeController) throws ParseException {
        this.parser = another.parser;
        this.JQReportModelParser = another.JQReportModelParser;
        this.controller = another.controller;
        this.createFormulaParser(context);
        this.dataDefinitionsCache = new DataDefinitionsCache(context, this.controller, this.parser);
        this.dataModelDefinitionsCache = new DataModelDefinitionsCache(context, this.parser);
        this.setDesignTimeController(designTimeController);
    }

    public DataModelDefinitionsCache getDataModelDefinitionsCache() {
        return this.dataModelDefinitionsCache;
    }

    public void setDataModelDefinitionsCache(DataModelDefinitionsCache dataModelDefinitionsCache) {
        this.dataModelDefinitionsCache = dataModelDefinitionsCache;
    }

    public DataDefinitionsCache getDataDefinitionsCache() {
        return this.dataDefinitionsCache;
    }

    public void setDesignTimeController(IDataDefinitionDesignTimeController designTimeController) {
        this.dataDefinitionsCache.setDesignTimeController(designTimeController);
        this.dataModelDefinitionsCache.setDesignTimeController(designTimeController);
        this.dataModelDefinitionsCache.setDataDefinitionsCache(this.dataDefinitionsCache);
    }

    private void createFormulaParser(ExecutorContext executorContext) {
        DynamicDataNodeFinder nodeFinder;
        CellFmlProvider cellProvider;
        if (this.parser == null) {
            this.parser = ReportFormulaParser.getInstance();
            this.parser.registerDynamicNodeProvider(ExecutorContext.getPrioritycontextvariablemanager());
            if (executorContext.getVariableManager() != null) {
                this.parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)executorContext.getVariableManager());
            }
            cellProvider = new CellFmlProvider();
            this.parser.registerCellProvider((IReportCellProvider)cellProvider);
            nodeFinder = new DynamicDataNodeFinder();
            this.parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)nodeFinder);
            this.parser.registerDynamicNodeProvider(ExecutorContext.getContextvariablemanager());
        }
        if (this.JQReportModelParser == null) {
            this.JQReportModelParser = ReportFormulaParser.getInstance();
            this.JQReportModelParser.setJQReportMode(true);
            this.JQReportModelParser.registerDynamicNodeProvider(ExecutorContext.getPrioritycontextvariablemanager());
            if (executorContext.getVariableManager() != null) {
                this.JQReportModelParser.registerDynamicNodeProvider((IReportDynamicNodeProvider)executorContext.getVariableManager());
            }
            cellProvider = new CellFmlProvider();
            this.JQReportModelParser.registerCellProvider((IReportCellProvider)cellProvider);
            nodeFinder = new DynamicDataNodeFinder();
            this.JQReportModelParser.registerDynamicNodeProvider((IReportDynamicNodeProvider)nodeFinder);
            this.JQReportModelParser.registerDynamicNodeProvider(ExecutorContext.getContextvariablemanager());
        }
    }

    public ReportFormulaParser getFormulaParser(ExecutorContext executorContext) {
        return this.getFormulaParser(executorContext.isJQReportModel());
    }

    public ReportFormulaParser getFormulaParser(boolean isJQRModel) {
        if (isJQRModel) {
            return this.JQReportModelParser;
        }
        return this.parser;
    }

    public ReportFormulaParser_link getDataLinkFormulaParser(ExecutorContext executorContext) {
        if (this.dataLinkFormulaParser == null) {
            this.dataLinkFormulaParser = ReportFormulaParser_link.getInstance();
            this.dataLinkFormulaParser.setJQReportMode(true);
            this.dataLinkFormulaParser.registerDynamicNodeProvider(ExecutorContext.getPrioritycontextvariablemanager());
            if (executorContext.getVariableManager() != null) {
                this.dataLinkFormulaParser.registerDynamicNodeProvider(executorContext.getVariableManager());
            }
            this.dataLinkFormulaParser.registerCellProvider(new CellFmlProvider_link());
            this.dataLinkFormulaParser.registerDynamicNodeProvider(new DynamicDataNodeFinder_link());
            this.dataLinkFormulaParser.registerDynamicNodeProvider(ExecutorContext.getContextvariablemanager());
        }
        return this.dataLinkFormulaParser;
    }

    public boolean hasDataLinkFormulaParser() {
        return this.dataLinkFormulaParser != null;
    }

    public QueryField extractQueryField(ExecutorContext executorContext, FieldDefine fieldDefine, PeriodModifier modifier, DimensionValueSet dimensionRestriction) throws ExpressionException {
        ColumnModelDefine column = this.dataModelDefinitionsCache.getColumnModel(fieldDefine);
        int dataType = DataTypesConvert.fieldTypeToDataType(fieldDefine.getType());
        if (dataType == 3 && column.getPrecision() > 16) {
            dataType = 10;
        }
        QueryField queryField = this.createQueryField(executorContext, column, dataType, modifier, dimensionRestriction);
        return queryField;
    }

    public QueryField extractQueryField(ExecutorContext executorContext, ColumnModelDefine fieldDefine, PeriodModifier modifier, DimensionValueSet dimensionRestriction) throws ExpressionException {
        int dataType = DataTypesConvert.fieldTypeToDataType(fieldDefine.getColumnType());
        if (dataType == 3 && fieldDefine.getPrecision() > 16) {
            dataType = 10;
        }
        QueryField queryField = this.createQueryField(executorContext, fieldDefine, dataType, modifier, dimensionRestriction);
        return queryField;
    }

    private QueryField createQueryField(ExecutorContext executorContext, ColumnModelDefine column, int dataType, PeriodModifier modifier, DimensionValueSet dimensionRestriction) throws ExpressionException {
        QueryTable queryTable = this.dataModelDefinitionsCache.getQueryTable(column, dataType, modifier, dimensionRestriction);
        QueryField queryField = new QueryField(column, queryTable);
        queryField.setDataType(dataType);
        queryField.setDataType(dataType);
        if (queryTable.isAccountTable()) {
            try {
                boolean needAccountVersion = this.dataModelDefinitionsCache.getAccountColumnModelFinder().isAccountVersionColumn(executorContext, queryField.getUID());
                queryField.setNeedAccountVersion(needAccountVersion);
            }
            catch (Exception e) {
                throw new ExpressionException(e.getMessage(), e);
            }
        }
        return queryField;
    }

    public QueryField extractLookUpQueryField(ExecutorContext executorContext, FieldDefine fieldDefine, String refField, String viewKey) throws ExpressionException {
        ColumnModelDefine column = this.dataModelDefinitionsCache.getColumnModel(fieldDefine);
        TableModelDefine tableModel = this.dataModelDefinitionsCache.getTableModel(column);
        String tableName = tableModel.getName();
        String fieldName = column.getName();
        TableModelRunInfo tableRunInfo = this.dataModelDefinitionsCache.getTableInfo(tableName);
        int dataType = DataTypesConvert.fieldTypeToDataType(fieldDefine.getType());
        QueryFieldBuilder queryFieldBuilder = new QueryFieldBuilder(tableName, fieldName, dataType, (int)fieldDefine.getFractionDigits(), tableRunInfo.getDimensions());
        boolean isEntityView = !StringUtils.isEmpty((String)viewKey);
        String valueKey = StringUtils.isEmpty((String)viewKey) ? fieldDefine.getKey() : viewKey;
        LookupKeyItem keyItem = new LookupKeyItem(refField, valueKey, isEntityView);
        String tableAlias = TableAllocator.getLookupAlias(keyItem);
        QueryTable queryTable = new QueryTable(tableAlias, queryFieldBuilder);
        QueryField queryField = new QueryField(column, queryTable);
        queryField.setDataType(dataType);
        int precision = column.getPrecision();
        if (queryField.getDataType() == 3 && precision > 16) {
            queryField.setDataType(10);
        }
        return queryField;
    }

    public ColumnModelDefine extractFieldDefine(IASTNode colNode) {
        QueryField queryField = ExpressionUtils.extractQueryField(colNode);
        if (queryField == null) {
            return null;
        }
        ColumnModelDefine fieldDefine = this.dataModelDefinitionsCache.findField(queryField.getUID());
        return fieldDefine;
    }
}

