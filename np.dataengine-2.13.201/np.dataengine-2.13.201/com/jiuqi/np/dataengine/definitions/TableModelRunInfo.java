/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nvwa.definition.common.ApplyType
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.np.dataengine.definitions;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nvwa.definition.common.ApplyType;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class TableModelRunInfo {
    private final ArrayList<ColumnModelDefine> dimFields = new ArrayList();
    private final ArrayList<ColumnModelDefine> allFields = new ArrayList();
    private final HashMap<String, ColumnModelDefine> fieldsCache = new HashMap();
    private final HashMap<String, ColumnModelDefine> colNameSearch = new HashMap();
    private final HashMap<String, ColumnModelDefine> dimensionSearch = new HashMap();
    private final HashMap<String, ColumnModelDefine> parserSearch = new HashMap();
    private final HashMap<ColumnModelDefine, FieldDefine> columnFieldMap = new HashMap();
    private final TableModelDefine tableDefine;
    private ColumnModelDefine periodField;
    private ColumnModelDefine orderField;
    private ColumnModelDefine bizOrderField;
    private ColumnModelDefine recField;
    private DimensionSet dimensions;
    private ColumnModelDefine unitOrderField;
    private ColumnModelDefine codeField;
    private ColumnModelDefine titleField;
    private ColumnModelDefine versionField;
    private ColumnModelDefine parentField;
    private ColumnModelDefine typeField;
    private ColumnModelDefine accountIdField;
    private final HashMap<String, String> fieldDimensionNames = new HashMap();
    private final IColumnModelFinder columnModelFinder;
    private DataEngineConsts.QueryTableType tableType = DataEngineConsts.QueryTableType.COMMON;
    private PeriodType tablePeriodType;
    private List<String> innerDimensions;
    private HashSet<String> needRenameDimensions;
    private static final Logger logger = LoggerFactory.getLogger(AbstractMonitor.class);

    public TableModelRunInfo(TableModelDefine tableDefine, List<ColumnModelDefine> tableFields, IColumnModelFinder columnModelFinder) {
        this.tableDefine = tableDefine;
        this.columnModelFinder = columnModelFinder;
        for (ColumnModelDefine columnModelDefine : tableFields) {
            this.allFields.add(columnModelDefine);
            this.colNameSearch.put(columnModelDefine.getName(), columnModelDefine);
            this.parserSearch.put(columnModelDefine.getCode(), columnModelDefine);
            this.fieldsCache.put(columnModelDefine.getID(), columnModelDefine);
            if (columnModelFinder != null) {
                this.initColFieldMap(columnModelDefine);
            }
            ApplyType valueType = columnModelDefine.getApplyType();
            this.initSpecificField(valueType, columnModelDefine);
        }
    }

    public void buildTableInfo(ExecutorContext executorContext) throws Exception {
        IDataModelLinkFinder dataModelLinkFinder;
        DataModelDefinitionsCache dataModelDefinitionsCache = executorContext.getCache().getDataModelDefinitionsCache();
        this.buildTableDimension(dataModelDefinitionsCache);
        if (!executorContext.isDesignTimeData()) {
            this.initTableType(executorContext, dataModelDefinitionsCache);
        }
        this.tablePeriodType = this.columnModelFinder.getPeriodType(this.tableDefine);
        if (this.bizOrderField != null && executorContext.getEnv() != null && (dataModelLinkFinder = executorContext.getEnv().getDataModelLinkFinder()) != null) {
            try {
                List<String> innerKeyFields = dataModelLinkFinder.getTableInnerKeys(executorContext, this.tableDefine.getName());
                if (innerKeyFields != null && innerKeyFields.size() > 0) {
                    this.innerDimensions = new ArrayList<String>(innerKeyFields.size());
                    for (String fieldId : innerKeyFields) {
                        ColumnModelDefine keyField = this.fieldsCache.get(fieldId);
                        if (this.isBizOrderField(keyField.getCode())) continue;
                        this.innerDimensions.add(this.getDimensionName(keyField.getCode()));
                    }
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public void buildTableInfo(ExecutorContext executorContext, boolean buildInnerDimensions) throws Exception {
        IDataModelLinkFinder dataModelLinkFinder;
        DataModelDefinitionsCache dataModelDefinitionsCache = executorContext.getCache().getDataModelDefinitionsCache();
        this.buildTableDimension(dataModelDefinitionsCache);
        if (!executorContext.isDesignTimeData()) {
            this.initTableType(executorContext, dataModelDefinitionsCache);
        }
        this.tablePeriodType = this.columnModelFinder.getPeriodType(this.tableDefine);
        if (this.bizOrderField != null && executorContext.getEnv() != null && buildInnerDimensions && (dataModelLinkFinder = executorContext.getEnv().getDataModelLinkFinder()) != null) {
            try {
                List<String> innerKeyFields = dataModelLinkFinder.getTableInnerKeys(executorContext, this.tableDefine.getName());
                if (innerKeyFields != null && innerKeyFields.size() > 0) {
                    this.innerDimensions = new ArrayList<String>(innerKeyFields.size());
                    for (String fieldId : innerKeyFields) {
                        ColumnModelDefine keyField = this.fieldsCache.get(fieldId);
                        if (this.isBizOrderField(keyField.getCode())) continue;
                        this.innerDimensions.add(this.getDimensionName(keyField.getCode()));
                    }
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public void buildTableDimension(DataModelDefinitionsCache dataModelDefinitionsCache) {
        this.periodField = this.allFields.stream().filter(e -> e.getCode().equals("DATATIME")).findFirst().orElse(null);
        String bizKeys = this.tableDefine.getBizKeys();
        if (bizKeys == null) {
            return;
        }
        String[] keyFieldIds = bizKeys.split(";");
        if (keyFieldIds == null) {
            return;
        }
        if (keyFieldIds.length == 1 && (this.tableDefine.getType() == TableModelType.ENUM || this.tableDefine.getType() == TableModelType.ENUM_NOSUMMARY)) {
            String dimension = dataModelDefinitionsCache.getDimensionNameByTableCode(this.tableDefine.getCode());
            this.dimensions = new DimensionSet();
            this.dimensions.addDimension(dimension);
            ColumnModelDefine keyField = dataModelDefinitionsCache.findField(keyFieldIds[0]);
            this.dimFields.add(keyField);
            this.fieldDimensionNames.put(keyField.getCode(), dimension);
            this.dimensionSearch.put(dimension, keyField);
        } else {
            DimensionSet dimensionArray = new DimensionSet();
            for (int i = 0; i < keyFieldIds.length; ++i) {
                ColumnModelDefine keyField = dataModelDefinitionsCache.findField(keyFieldIds[i]);
                String dimension = null;
                if (this.periodField != null && keyField.getID().equals(this.periodField.getID()) || "PERIOD".equals(keyField.getName())) {
                    dimension = "DATATIME";
                } else if (keyFieldIds.length == 1 && this.tableDefine.getCode().startsWith("NR_PERIOD_")) {
                    dimension = "DATATIME";
                } else if ("SBID".equals(keyField.getCode())) {
                    this.accountIdField = keyField;
                    dimension = "RECORDKEY";
                } else {
                    dimension = dataModelDefinitionsCache.getDimensionNameByField(keyField);
                }
                if (this.needRenameDimensions != null && this.needRenameDimensions.contains(dimension)) {
                    dimension = keyField.getCode();
                } else {
                    ColumnModelDefine dimField = this.dimensionSearch.get(dimension);
                    if (dimField != null) {
                        if (!dimField.getCode().equals("MDCODE")) {
                            this.dimensionSearch.remove(dimension);
                            this.fieldDimensionNames.remove(dimField.getCode());
                            dimensionArray.removeDimension(dimension);
                            this.dimensionSearch.put(dimField.getCode(), dimField);
                            this.fieldDimensionNames.put(dimField.getCode(), dimField.getCode());
                            dimensionArray.addDimension(dimField.getCode());
                        }
                        dimension = this.renameDimension(dimension, keyField);
                    }
                }
                if (StringUtils.isEmpty((String)dimension)) {
                    dimension = keyField.getCode();
                }
                dimensionArray.addDimension(dimension);
                this.dimFields.add(keyField);
                this.fieldDimensionNames.put(keyField.getCode(), dimension);
                this.dimensionSearch.put(dimension, keyField);
            }
            this.dimensions = dimensionArray;
        }
    }

    protected void initTableType(ExecutorContext executorContext, DataModelDefinitionsCache dataModelDefinitionsCache) throws Exception {
        TableModelType type = this.tableDefine.getType();
        String tableName = this.tableDefine.getCode();
        if (this.dimensions.size() == 1) {
            String dimensionName = this.dimensions.get(0);
            if (dimensionName.equals("DATATIME")) {
                this.tableType = DataEngineConsts.QueryTableType.PERIOD;
            } else if (type == TableModelType.ENUM || type == TableModelType.ENUM_NOSUMMARY) {
                this.tableType = DataEngineConsts.QueryTableType.DIMENSION;
            }
        } else if (tableName.endsWith("_HIS")) {
            this.tableType = DataEngineConsts.QueryTableType.ACCOUNT_HIS;
        } else if (tableName.endsWith("_RPT")) {
            this.tableType = DataEngineConsts.QueryTableType.ACCOUNT_RPT;
        } else {
            if (CollectionUtils.isEmpty(this.dimFields) && Objects.isNull(this.accountIdField)) {
                return;
            }
            ColumnModelDefine anyField = Objects.isNull(this.accountIdField) ? (ColumnModelDefine)this.dimFields.stream().findAny().get() : this.accountIdField;
            FieldDefine accountFieldDefine = dataModelDefinitionsCache.getColumnModelFinder().findFieldDefine(anyField);
            if (Objects.isNull(accountFieldDefine)) {
                return;
            }
            String ownerTableKey = accountFieldDefine.getOwnerTableKey();
            if (ownerTableKey != null && dataModelDefinitionsCache.getAccountColumnModelFinder().isAccountTable(executorContext, ownerTableKey)) {
                this.tableType = DataEngineConsts.QueryTableType.ACCOUNT;
            }
        }
    }

    public boolean isKeyField(String fieldCode) {
        return this.fieldDimensionNames.containsKey(fieldCode.toUpperCase());
    }

    public boolean isRecField(String fieldName) {
        if (this.recField == null) {
            return false;
        }
        return this.recField.getName().equalsIgnoreCase(fieldName);
    }

    public boolean isBizOrderField(String fieldName) {
        if (this.bizOrderField == null) {
            return false;
        }
        return this.bizOrderField.getName().equalsIgnoreCase(fieldName);
    }

    public String getDimensionName(String fieldCode) {
        return this.fieldDimensionNames.get(fieldCode.toUpperCase());
    }

    public ColumnModelDefine getDimensionField(String dimension) {
        ColumnModelDefine dimField = this.dimensionSearch.get(dimension);
        return dimField;
    }

    public String getDimensionFieldCode(String dimension) {
        ColumnModelDefine dimField = this.dimensionSearch.get(dimension);
        if (dimField != null) {
            return dimField.getCode();
        }
        return null;
    }

    public ColumnModelDefine parseSearchField(String fieldSign) {
        return this.parserSearch.get(fieldSign.toUpperCase());
    }

    public List<ColumnModelDefine> getFieldsByValueType(ApplyType valueType) {
        ArrayList<ColumnModelDefine> resultFields = new ArrayList<ColumnModelDefine>();
        for (ColumnModelDefine columnModelDefine : this.allFields) {
            if (columnModelDefine.getApplyType() != valueType) continue;
            resultFields.add(columnModelDefine);
        }
        return resultFields;
    }

    @Deprecated
    public ColumnModelDefine getPeriodField() {
        return this.periodField;
    }

    public ColumnModelDefine getOrderField() {
        return this.orderField;
    }

    public ColumnModelDefine getBizOrderField() {
        return this.bizOrderField;
    }

    public ColumnModelDefine getRecField() {
        return this.recField;
    }

    public DimensionSet getDimensions() {
        return this.dimensions;
    }

    public TableModelDefine getTableModelDefine() {
        return this.tableDefine;
    }

    public List<ColumnModelDefine> getDimFields() {
        return this.dimFields;
    }

    public ColumnModelDefine getFieldByName(String fieldName) {
        return this.colNameSearch.get(fieldName.toUpperCase());
    }

    public List<ColumnModelDefine> getAllFields() {
        return this.allFields;
    }

    public ColumnModelDefine getCodeField() {
        return this.codeField;
    }

    public ColumnModelDefine getTitleField() {
        return this.titleField;
    }

    public ColumnModelDefine getUnitOrderField() {
        return this.unitOrderField;
    }

    public ColumnModelDefine getFieldByKey(String fieldKey) {
        return this.fieldsCache.get(fieldKey);
    }

    private void initSpecificField(ApplyType valueType, ColumnModelDefine columnModelDefine) {
        if ("BIZKEYORDER".equals(columnModelDefine.getCode())) {
            this.bizOrderField = columnModelDefine;
        } else if ("FLOATORDER".equals(columnModelDefine.getCode())) {
            this.orderField = columnModelDefine;
        }
    }

    private String renameDimension(String dimension, ColumnModelDefine keyField) {
        if (this.needRenameDimensions == null) {
            this.needRenameDimensions = new HashSet();
        }
        this.needRenameDimensions.add(dimension);
        String newDimension = keyField.getCode();
        return newDimension;
    }

    private ColumnModelDefine queryColumnModel(FieldDefine columnModelDefine) {
        ColumnModelDefine column = null;
        try {
            column = this.columnModelFinder.findColumnModelDefine(columnModelDefine);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return column;
    }

    public ColumnModelDefine getVersionField() {
        return this.versionField;
    }

    public ColumnModelDefine getParentField() {
        return this.parentField;
    }

    public ColumnModelDefine getTypeField() {
        return this.typeField;
    }

    public ColumnModelDefine getAccountIdField() {
        return this.accountIdField;
    }

    public HashMap<ColumnModelDefine, FieldDefine> getColumnFieldMap() {
        return this.columnFieldMap;
    }

    private void initColFieldMap(ColumnModelDefine columnModelDefine) {
        try {
            this.columnFieldMap.put(columnModelDefine, this.columnModelFinder.findFieldDefine(columnModelDefine));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DataEngineConsts.QueryTableType getTableType() {
        return this.tableType;
    }

    public PeriodType getTablePeriodType() {
        return this.tablePeriodType;
    }

    public void setTablePeriodType(PeriodType tablePeriodType) {
        this.tablePeriodType = tablePeriodType;
    }

    public List<String> getInnerDimensions() {
        return this.innerDimensions;
    }

    public void setInnerDimensions(List<String> innerDimensions) {
        this.innerDimensions = innerDimensions;
    }

    public boolean isInnerDimension(String dimension) {
        return this.innerDimensions != null && this.innerDimensions.contains(dimension);
    }
}

