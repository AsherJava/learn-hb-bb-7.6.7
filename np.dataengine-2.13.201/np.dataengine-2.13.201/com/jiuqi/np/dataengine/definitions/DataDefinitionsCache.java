/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.exception.NotSupportedException
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.np.dataengine.definitions;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.definitions.TableRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.exception.NotSupportedException;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataDefinitionsCache {
    private static final Logger logger = LoggerFactory.getLogger(DataDefinitionsCache.class);
    private final IDimensionProvider dimensionProvider;
    private final IColumnModelFinder columnModelFinder;
    private final IDataDefinitionRuntimeController dataController;
    private IDataDefinitionDesignTimeController designTimeController;
    private final HashMap<String, TableRunInfo> tableNameSearch = new HashMap();
    private final HashMap<String, TableRunInfo> designTableSearch = new HashMap();
    private final HashMap<String, TableRunInfo> tableCodeSearch = new HashMap();
    private final HashMap<String, TableRunInfo> designCodeSearch = new HashMap();
    private final HashMap<String, IExpression> builtCalculation = new HashMap();
    private final HashMap<String, IExpression> builtDefaultValues = new HashMap();
    private final HashMap<String, IExpression> builtVerifications = new HashMap();
    private final ReportFormulaParser parser;
    private final ExecutorContext executorContext;
    private final DataModelService dataModelService;

    public DataDefinitionsCache(ExecutorContext context, IDataDefinitionRuntimeController controller, ReportFormulaParser parser) {
        this.dataController = controller;
        this.dimensionProvider = (IDimensionProvider)SpringBeanProvider.getBean(IDimensionProvider.class);
        this.columnModelFinder = (IColumnModelFinder)SpringBeanProvider.getBean(IColumnModelFinder.class);
        this.dataModelService = (DataModelService)SpringBeanProvider.getBean(DataModelService.class);
        this.parser = parser;
        this.executorContext = context;
    }

    public String getDimensionName(FieldDefine keyField) {
        ColumnModelDefine column = this.findColumn(keyField);
        TableModelDefine table = this.findTableModel(column.getTableID());
        TableRunInfo tableRunInfo = this.getTableInfo(table.getName());
        if (tableRunInfo != null && tableRunInfo.isKeyField(column.getName())) {
            return tableRunInfo.getDimensionName(column.getName());
        }
        return this.getDimensionNameByField(keyField);
    }

    public String getDimensionName(EntityViewDefine viewDefine) {
        return this.dimensionProvider.getDimensionNameByEntityId(viewDefine.getEntityId());
    }

    public String getDimensionName(String entityId) {
        return this.dimensionProvider.getDimensionNameByEntityId(entityId);
    }

    String getDimensionNameByField(FieldDefine keyField) {
        if (keyField.getEntityKey() != null) {
            return this.dimensionProvider.getFieldDimensionName(this.executorContext, keyField);
        }
        if (keyField.getValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER) {
            return "RECORDKEY";
        }
        return keyField.getCode();
    }

    String getDimensionNameByTableCode(String tableCode) {
        return this.dimensionProvider.getDimensionNameByEntityTableCode(this.executorContext, tableCode);
    }

    public FieldDefine findField(String fieldKey) {
        try {
            if (this.designTimeController != null) {
                return this.designTimeController.queryFieldDefine(fieldKey);
            }
            return this.dataController.queryFieldDefine(fieldKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public ColumnModelDefine findColumn(String columnKey) {
        try {
            return this.dataModelService.getColumnModelDefineByID(columnKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public ColumnModelDefine findColumn(FieldDefine fieldDefine) {
        try {
            return this.columnModelFinder.findColumnModelDefine(fieldDefine);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public TableModelDefine findTableModel(String tableId) {
        try {
            return this.dataModelService.getTableModelDefineById(tableId);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public TableDefine findTable(String tableKey) {
        try {
            if (this.designTimeController != null) {
                return this.designTimeController.queryTableDefine(tableKey);
            }
            return this.dataController.queryTableDefine(tableKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public TableRunInfo getTableInfo(String tableName) {
        try {
            tableName = tableName.toUpperCase();
            if (this.designTimeController != null) {
                if (this.designTableSearch.containsKey(tableName)) {
                    return this.designTableSearch.get(tableName);
                }
                throw new NotSupportedException("\u65b9\u6cd5\u8fc7\u65f6\u6216\u4e0d\u652f\u6301");
            }
            if (this.tableNameSearch.containsKey(tableName)) {
                return this.tableNameSearch.get(tableName);
            }
            throw new NotSupportedException("\u65b9\u6cd5\u8fc7\u65f6\u6216\u4e0d\u652f\u6301");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public TableRunInfo getTableInfoByCode(String tableCode) {
        try {
            tableCode = tableCode.toUpperCase();
            if (this.designTimeController != null) {
                if (this.designCodeSearch.containsKey(tableCode)) {
                    return this.designCodeSearch.get(tableCode);
                }
                DesignTableDefine tableDefine = this.designTimeController.queryTableDefinesByCode(tableCode);
                if (tableDefine == null) {
                    return null;
                }
                List<FieldDefine> allFields = this.getFieldDefines(this.designTimeController.getAllFieldsInTable(tableDefine.getKey()));
                TableRunInfo tableRunInfo = new TableRunInfo((TableDefine)tableDefine, allFields, this.columnModelFinder);
                tableRunInfo.buildTableInfo(this);
                this.designCodeSearch.put(tableCode, tableRunInfo);
                return tableRunInfo;
            }
            if (this.tableCodeSearch.containsKey(tableCode)) {
                return this.tableCodeSearch.get(tableCode);
            }
            TableDefine tableDefine = this.dataController.queryTableDefineByCode(tableCode);
            if (tableDefine == null) {
                return null;
            }
            List allFields = this.dataController.getAllFieldsInTable(tableDefine.getKey());
            TableRunInfo tableRunInfo = new TableRunInfo(tableDefine, allFields, this.columnModelFinder);
            tableRunInfo.buildTableInfo(this);
            this.tableCodeSearch.put(tableCode, tableRunInfo);
            return tableRunInfo;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public FieldDefine getDimensionField(String tableName, String dimensionName) {
        TableRunInfo tableRunInfo = this.getTableInfo(tableName);
        if (tableRunInfo == null) {
            return null;
        }
        return tableRunInfo.getDimensionField(dimensionName);
    }

    public FieldDefine parseSearchField(String tableSign, String fieldSign, String defaultTableName) {
        try {
            FieldDefine fieldDefine = null;
            String tableCode = tableSign;
            if (StringUtils.isEmpty((String)tableCode)) {
                tableCode = defaultTableName;
            }
            if (StringUtils.isNotEmpty((String)tableCode)) {
                TableRunInfo tableRunInfo = this.getTableInfoByCode(tableCode);
                if (tableRunInfo == null) {
                    return fieldDefine;
                }
                fieldDefine = tableRunInfo.parseSearchField(fieldSign);
            }
            return fieldDefine;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public IExpression getFieldCalculation(QueryContext qContext, FieldDefine fieldDefine) {
        throw new NotSupportedException("\u65b9\u6cd5\u8fc7\u65f6\u6216\u4e0d\u652f\u6301");
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

    public IExpression getFieldVerification(QueryContext qContext, FieldDefine fieldDefine) {
        throw new NotSupportedException("\u65b9\u6cd5\u8fc7\u65f6\u6216\u4e0d\u652f\u6301");
    }

    public IDataDefinitionDesignTimeController getDesignTimeController() {
        return this.designTimeController;
    }

    public void setDesignTimeController(IDataDefinitionDesignTimeController designTimeController) {
        this.designTimeController = designTimeController;
    }

    private List<FieldDefine> getFieldDefines(List<DesignFieldDefine> fieldDefines) {
        ArrayList<FieldDefine> resultFields = new ArrayList<FieldDefine>();
        for (FieldDefine fieldDefine : fieldDefines) {
            resultFields.add(fieldDefine);
        }
        return resultFields;
    }

    public FieldDefine getFieldByCodeInTable(String fieldCode, String tableKey) {
        try {
            return this.dataController.queryFieldByCodeInTable(fieldCode, tableKey);
        }
        catch (Exception e) {
            return null;
        }
    }

    private ColumnModelDefine getColumnModefine(FieldDefine keyField) {
        try {
            ColumnModelDefine column = this.columnModelFinder.findColumnModelDefine(keyField);
            if (Objects.isNull(column)) {
                return null;
            }
            return column;
        }
        catch (Exception e) {
            return null;
        }
    }

    private TableModelDefine getTableModelDefine(ColumnModelDefine column) {
        TableModelDefine table = this.dataModelService.getTableModelDefineById(column.getTableID());
        return table;
    }

    private TableModelDefine getTableModelDefine(String tableName) {
        TableModelDefine table = this.dataModelService.getTableModelDefineByName(tableName);
        return table;
    }

    public IDimensionProvider getDimensionProvider() {
        return this.dimensionProvider;
    }

    public IColumnModelFinder getColumnModelFinder() {
        return this.columnModelFinder;
    }
}

