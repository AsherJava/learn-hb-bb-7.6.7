/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.nvwa.definition.common.TableDictType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.np.dataengine.definitions;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryFieldBuilder;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.ColumnModelAdapter;
import com.jiuqi.np.dataengine.definitions.DataDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelAdapter;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.definitions.TableRunInfo;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.account.IAccountColumnModelFinder;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.nvwa.definition.common.TableDictType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataModelDefinitionsCache {
    protected static final Logger logger = LoggerFactory.getLogger(DataModelDefinitionsCache.class);
    protected IDimensionProvider dimensionProvider;
    protected IColumnModelFinder columnModelFinder;
    protected IDataDefinitionDesignTimeController designTimeController;
    protected HashMap<String, TableModelRunInfo> tableNameSearch = new HashMap();
    protected HashMap<String, ColumnModelDefine> designEntityColumnSearch = new HashMap();
    protected HashMap<String, ColumnModelAdapter> designFieldKeySearch = new HashMap();
    protected HashMap<String, TableModelAdapter> designTableKeySearch = new HashMap();
    protected HashMap<String, TableModelRunInfo> tableCodeSearch = new HashMap();
    protected HashMap<String, IExpression> builtCalculation = new HashMap();
    protected HashMap<String, IExpression> builtDefaultValues = new HashMap();
    protected HashMap<String, IExpression> builtVerifications = new HashMap();
    protected ReportFormulaParser parser;
    protected ExecutorContext executorContext;
    protected DataModelService dataModelService;
    protected DataDefinitionsCache dataDefinitionsCache;
    protected IAccountColumnModelFinder accountColumnModelFinder;

    public DataModelDefinitionsCache(ExecutorContext context, ReportFormulaParser parser) {
        this.executorContext = context;
        this.parser = parser;
        this.columnModelFinder = (IColumnModelFinder)SpringBeanProvider.getBean(IColumnModelFinder.class);
        this.dataModelService = (DataModelService)SpringBeanProvider.getBean(DataModelService.class);
        this.dimensionProvider = (IDimensionProvider)SpringBeanProvider.getBean(IDimensionProvider.class);
        this.accountColumnModelFinder = (IAccountColumnModelFinder)SpringBeanProvider.getBean(IAccountColumnModelFinder.class);
    }

    public String getDimensionName(ColumnModelDefine keyField) {
        TableModelDefine table = this.getTableModel(keyField);
        TableModelRunInfo tableRunInfo = this.getTableInfo(table.getName());
        if (tableRunInfo != null && tableRunInfo.isKeyField(keyField.getCode())) {
            return tableRunInfo.getDimensionName(keyField.getCode());
        }
        return this.getDimensionNameByField(keyField);
    }

    protected String getDimensionNameByField(ColumnModelDefine keyField) {
        if (StringUtils.isNotEmpty((String)keyField.getReferTableID())) {
            return this.dimensionProvider.getFieldDimensionName(this.executorContext, keyField);
        }
        if (StringUtils.isNotEmpty((String)keyField.getReferColumnID())) {
            ColumnModelDefine referColumn = this.dataModelService.getColumnModelDefineByID(keyField.getReferColumnID());
            if (StringUtils.isEmpty((String)referColumn.getReferTableID())) {
                TableModelDefine tableModel = this.dataModelService.getTableModelDefineById(referColumn.getTableID());
                return tableModel.getCode();
            }
            return this.dimensionProvider.getFieldDimensionName(this.executorContext, referColumn);
        }
        if (keyField.getCode().equals("BIZKEYORDER")) {
            return "RECORDKEY";
        }
        return keyField.getCode();
    }

    public TableModelRunInfo getTableInfo(String tableName) {
        return this.getTableInfo(tableName, true);
    }

    public TableModelRunInfo getTableInfo(String tableName, boolean buildInnerDimensions) {
        try {
            tableName = tableName.toUpperCase();
            if (this.tableNameSearch.containsKey(tableName)) {
                return this.tableNameSearch.get(tableName);
            }
            TableModelDefine tableDefine = this.dataModelService.getTableModelDefineByName(tableName);
            if (tableDefine == null) {
                return null;
            }
            List allFields = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
            TableModelRunInfo tableRunInfo = new TableModelRunInfo(tableDefine, allFields, this.columnModelFinder);
            tableRunInfo.buildTableInfo(this.executorContext, buildInnerDimensions);
            this.tableNameSearch.put(tableName, tableRunInfo);
            return tableRunInfo;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public TableModelRunInfo getTableInfo(TableModelDefine tableDefine) {
        try {
            String tableName = tableDefine.getName().toUpperCase();
            if (this.tableNameSearch.containsKey(tableName)) {
                return this.tableNameSearch.get(tableName);
            }
            ArrayList<ColumnModelDefine> allFields = null;
            if (this.designTimeController != null) {
                List allFieldsInTable = this.designTimeController.getAllFieldsInTable(tableDefine.getID());
                allFields = new ArrayList<ColumnModelDefine>(allFieldsInTable.size());
                for (DesignFieldDefine field : allFieldsInTable) {
                    ColumnModelDefine columnModel = this.findDesignField(field);
                    allFields.add(columnModel);
                }
            } else {
                allFields = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
            }
            TableModelRunInfo tableRunInfo = new TableModelRunInfo(tableDefine, allFields, this.columnModelFinder);
            tableRunInfo.buildTableInfo(this.executorContext);
            this.tableNameSearch.put(tableName, tableRunInfo);
            return tableRunInfo;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public TableModelRunInfo getTableInfoByCode(String tableCode) {
        try {
            tableCode = tableCode.toUpperCase();
            if (this.tableCodeSearch.containsKey(tableCode)) {
                return this.tableCodeSearch.get(tableCode);
            }
            TableModelDefine tableDefine = this.dataModelService.getTableModelDefineByCode(tableCode);
            if (tableDefine == null) {
                return null;
            }
            List allFields = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
            TableModelRunInfo tableRunInfo = new TableModelRunInfo(tableDefine, allFields, this.columnModelFinder);
            tableRunInfo.buildTableInfo(this.executorContext);
            this.tableCodeSearch.put(tableCode, tableRunInfo);
            return tableRunInfo;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public String getDimensionName(EntityViewDefine viewDefine) {
        return this.dimensionProvider.getDimensionNameByEntityId(viewDefine.getEntityId());
    }

    public String getDimensionName(String entityId) {
        return this.dimensionProvider.getDimensionNameByEntityId(entityId);
    }

    protected String getDimensionNameByTableCode(String tableCode) {
        return this.dimensionProvider.getDimensionNameByEntityTableCode(this.executorContext, tableCode);
    }

    public ColumnModelDefine findField(String fieldKey) {
        try {
            if (this.designTimeController != null) {
                DesignFieldDefine designFieldDefine;
                ColumnModelAdapter columnModelAdapter = this.designFieldKeySearch.get(fieldKey);
                if (columnModelAdapter == null && (designFieldDefine = this.designTimeController.queryFieldDefine(fieldKey)) != null) {
                    columnModelAdapter = new ColumnModelAdapter(designFieldDefine, this);
                    this.designFieldKeySearch.put(fieldKey, columnModelAdapter);
                }
                if (columnModelAdapter != null) {
                    return columnModelAdapter;
                }
            }
            return this.dataModelService.getColumnModelDefineByID(fieldKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private ColumnModelDefine findDesignField(DesignFieldDefine designFieldDefine) {
        try {
            if (this.designTimeController != null) {
                ColumnModelAdapter columnModelAdapter = this.designFieldKeySearch.get(designFieldDefine.getKey());
                if (columnModelAdapter == null) {
                    columnModelAdapter = new ColumnModelAdapter(designFieldDefine, this);
                    this.designFieldKeySearch.put(designFieldDefine.getKey(), columnModelAdapter);
                }
                if (columnModelAdapter != null) {
                    return columnModelAdapter;
                }
            }
            return null;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public TableModelDefine findTable(String tableKey) {
        try {
            if (this.designTimeController != null) {
                DesignTableDefine designTableDefine;
                TableModelAdapter tableModelAdapter = this.designTableKeySearch.get(tableKey);
                if (tableModelAdapter == null && (designTableDefine = this.designTimeController.queryTableDefine(tableKey)) != null) {
                    tableModelAdapter = new TableModelAdapter(designTableDefine, this);
                    this.designTableKeySearch.put(tableKey, tableModelAdapter);
                }
                if (tableModelAdapter != null) {
                    return tableModelAdapter;
                }
            }
            return this.dataModelService.getTableModelDefineById(tableKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public ColumnModelDefine getDimensionField(String tableName, String dimensionName) {
        TableModelRunInfo tableRunInfo = this.getTableInfo(tableName);
        if (tableRunInfo == null) {
            return null;
        }
        return tableRunInfo.getDimensionField(dimensionName);
    }

    public ColumnModelDefine parseSearchField(String tableSign, String fieldSign, String defaultTableName) {
        try {
            TableRunInfo tableInfo;
            ColumnModelDefine fieldDefine = null;
            String tableCode = tableSign;
            if (StringUtils.isEmpty((String)tableCode)) {
                tableCode = defaultTableName;
            }
            if (StringUtils.isEmpty((String)tableCode)) {
                return this.columnModelFinder.findColumnModelDefine(this.executorContext, fieldSign);
            }
            if (this.dataDefinitionsCache != null && this.designTimeController != null && (tableInfo = this.dataDefinitionsCache.getTableInfoByCode(tableCode)) != null) {
                FieldDefine field = tableInfo.parseSearchField(fieldSign);
                if (field == null) {
                    return fieldDefine;
                }
                return new ColumnModelAdapter((DesignFieldDefine)field, this);
            }
            TableModelRunInfo tableRunInfo = this.getTableInfoByCode(tableCode);
            if (tableRunInfo == null) {
                return fieldDefine;
            }
            fieldDefine = tableRunInfo.parseSearchField(fieldSign);
            return fieldDefine;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public IExpression getFieldCalculation(QueryContext qContext, ColumnModelDefine fieldDefine) {
        return null;
    }

    public IExpression getFieldDefaultValue(QueryContext qContext, ColumnModelDefine column) {
        FieldDefine fieldDefine = this.getFieldDefine(column);
        if (Objects.isNull(fieldDefine)) {
            return null;
        }
        try {
            String fieldKey = fieldDefine.getKey();
            if (this.builtDefaultValues.containsKey(fieldKey)) {
                return this.builtDefaultValues.get(fieldKey);
            }
            if (StringUtils.isEmpty((String)fieldDefine.getDefaultValue())) {
                this.builtDefaultValues.put(fieldKey, null);
                return null;
            }
            IExpression expression = this.parser.parseEval(fieldDefine.getDefaultValue(), (IContext)qContext);
            this.builtDefaultValues.put(fieldKey, expression);
            return expression;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public IExpression getFieldDefaultValue(QueryContext qContext, FieldDefine fieldDefine) {
        try {
            String fieldKey = fieldDefine.getKey();
            if (this.builtDefaultValues.containsKey(fieldKey)) {
                return this.builtDefaultValues.get(fieldKey);
            }
            if (StringUtils.isEmpty((String)fieldDefine.getDefaultValue())) {
                this.builtDefaultValues.put(fieldKey, null);
                return null;
            }
            IExpression expression = this.parser.parseEval(fieldDefine.getDefaultValue(), (IContext)qContext);
            this.builtDefaultValues.put(fieldKey, expression);
            return expression;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public IExpression getFieldVerification(QueryContext qContext, ColumnModelDefine fieldDefine) {
        return null;
    }

    public IDataDefinitionDesignTimeController getDesignTimeController() {
        return this.designTimeController;
    }

    public void setDesignTimeController(IDataDefinitionDesignTimeController designTimeController) {
        this.designTimeController = designTimeController;
    }

    public ColumnModelDefine getFieldByCodeInTable(String fieldCode, String tableKey) {
        try {
            return this.dataModelService.getColumnModelDefineByCode(tableKey, fieldCode);
        }
        catch (Exception e) {
            return null;
        }
    }

    public IDimensionProvider getDimensionProvider() {
        return this.dimensionProvider;
    }

    public IColumnModelFinder getColumnModelFinder() {
        return this.columnModelFinder;
    }

    public String getTableName(FieldDefine fieldDefine) {
        ColumnModelDefine column = this.getColumnModel(fieldDefine);
        return this.getTableName(column);
    }

    public ColumnModelDefine getColumnModel(FieldDefine fieldDefine) {
        ColumnModelDefine column = null;
        try {
            column = this.columnModelFinder.findColumnModelDefine(fieldDefine);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return column;
    }

    public FieldDefine getFieldDefine(ColumnModelDefine columnModelDefine) {
        if (columnModelDefine == null) {
            return null;
        }
        TableModelDefine table = this.getTableModel(columnModelDefine);
        if (this.tableNameSearch.containsKey(table.getName())) {
            return this.tableNameSearch.get(table.getName()).getColumnFieldMap().get(columnModelDefine);
        }
        if (this.tableCodeSearch.containsKey(table.getCode())) {
            return this.tableCodeSearch.get(table.getCode()).getColumnFieldMap().get(columnModelDefine);
        }
        TableModelRunInfo tableInfo = this.getTableInfo(table.getName());
        return tableInfo.getColumnFieldMap().get(columnModelDefine);
    }

    public String getReferFieldKey(FieldDefine define) {
        ColumnModelDefine column = this.getColumnModel(define);
        if (Objects.isNull(column)) {
            return null;
        }
        return column.getReferColumnID();
    }

    public TableModelDefine getTableModel(ColumnModelDefine columnModelDefine) {
        if (Objects.isNull(columnModelDefine)) {
            return null;
        }
        if (this.designTimeController != null) {
            return this.findTable(columnModelDefine.getTableID());
        }
        return this.dataModelService.getTableModelDefineById(columnModelDefine.getTableID());
    }

    public String getTableDefineCode(ColumnModelDefine columnModelDefine) {
        try {
            FieldDefine fieldDefine = this.getFieldDefine(columnModelDefine);
            TableDefine tableDefine = this.executorContext.getRuntimeController().queryTableDefine(fieldDefine.getOwnerTableKey());
            return tableDefine.getCode();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return this.getTableName(columnModelDefine);
        }
    }

    public String getTableDefineCode(String tableName) {
        try {
            TableModelRunInfo tableInfo = this.getTableInfo(tableName);
            FieldDefine fieldDefine = this.getFieldDefine(tableInfo.getAllFields().get(0));
            if (fieldDefine.getOwnerTableKey() != null) {
                TableDefine tableDefine = this.executorContext.getRuntimeController().queryTableDefine(fieldDefine.getOwnerTableKey());
                return tableDefine.getCode();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return tableName;
    }

    public String getTableName(ColumnModelDefine columnModelDefine) {
        TableModelDefine tableModel = this.findTable(columnModelDefine.getTableID());
        return tableModel.getName();
    }

    public String getFieldName(FieldDefine fieldDefine) {
        ColumnModelDefine column = this.getColumnModel(fieldDefine);
        if (Objects.isNull(column)) {
            return null;
        }
        return column.getName();
    }

    public ColumnModelDefine getDesignEntityColumn(String entityKey) {
        ColumnModelDefine columnModelDefine = this.designEntityColumnSearch.get(entityKey);
        if (columnModelDefine == null) {
            try {
                String dimensionName = this.dimensionProvider.getDimensionNameByEntityId(entityKey);
                String tableName = this.dimensionProvider.getDimensionTableName(this.executorContext, dimensionName);
                TableModelRunInfo tableModelInfo = this.getTableInfo(tableName);
                columnModelDefine = tableModelInfo.getDimFields().get(0);
                this.designEntityColumnSearch.put(entityKey, columnModelDefine);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return columnModelDefine;
    }

    public QueryTable getQueryTable(ColumnModelDefine column, int dataType, PeriodModifier modifier, DimensionValueSet dimensionRestriction) throws ExpressionException {
        TableModelDefine tableModel = this.getTableModel(column);
        String tableName = tableModel.getName();
        TableModelRunInfo tableRunInfo = this.getTableInfo(tableModel);
        QueryFieldBuilder queryFieldBuilder = new QueryFieldBuilder(tableName, column.getName(), dataType, column.getDecimal(), tableRunInfo.getDimensions());
        if (tableRunInfo.getDimensions().contains("ADJUST") && (modifier != null || dimensionRestriction != null && dimensionRestriction.hasValue("DATATIME"))) {
            if (dimensionRestriction == null) {
                dimensionRestriction = new DimensionValueSet();
                dimensionRestriction.setValue("ADJUST", "0");
            } else if (!dimensionRestriction.hasValue("ADJUST")) {
                dimensionRestriction.setValue("ADJUST", "0");
            }
        }
        queryFieldBuilder.combinePeriodModifier(this.executorContext, modifier, tableModel.getDictType() == TableDictType.ZIPPER);
        queryFieldBuilder.combineRestriction(dimensionRestriction);
        QueryTable queryTable = new QueryTable(queryFieldBuilder);
        queryTable.setTableType(tableRunInfo.getTableType());
        queryTable.setTablePeriodType(tableRunInfo.getTablePeriodType());
        return queryTable;
    }

    public void setDataDefinitionsCache(DataDefinitionsCache dataDefinitionsCache) {
        this.dataDefinitionsCache = dataDefinitionsCache;
    }

    public IAccountColumnModelFinder getAccountColumnModelFinder() {
        return this.accountColumnModelFinder;
    }

    public DataDefinitionsCache getDataDefinitionsCache() {
        return this.dataDefinitionsCache;
    }
}

