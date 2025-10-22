/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.definitions;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.definitions.DataDefinitionsCache;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class TableRunInfo {
    private final ArrayList<FieldDefine> dimFields = new ArrayList();
    private final ArrayList<FieldDefine> allFields = new ArrayList();
    private final HashMap<String, FieldDefine> fieldsCache = new HashMap();
    private final HashMap<String, FieldDefine> colNameSearch = new HashMap();
    private final HashMap<String, FieldDefine> dimensionSearch = new HashMap();
    private final HashMap<String, FieldDefine> parserSearch = new HashMap();
    private final TableDefine tableDefine;
    private FieldDefine periodField;
    private FieldDefine orderField;
    private FieldDefine bizOrderField;
    private FieldDefine recField;
    private DimensionSet dimensions;
    private FieldDefine unitOrderField;
    private FieldDefine codeField;
    private FieldDefine titleField;
    private FieldDefine versionField;
    private FieldDefine parentField;
    private FieldDefine typeField;
    private final HashMap<String, String> fieldDimensionNames = new HashMap();
    private final IColumnModelFinder columnModelFinder;
    private HashSet<String> needRenameDimensions;

    public TableRunInfo(TableDefine tableDefine, List<FieldDefine> tableFields, IColumnModelFinder columnModelFinder) {
        this.tableDefine = tableDefine;
        this.columnModelFinder = columnModelFinder;
        for (FieldDefine fieldDefine : tableFields) {
            ColumnModelDefine column = this.getColumnModefine(fieldDefine);
            this.allFields.add(fieldDefine);
            this.colNameSearch.put(column == null ? fieldDefine.getCode() : column.getName(), fieldDefine);
            this.parserSearch.put(fieldDefine.getCode(), fieldDefine);
            this.fieldsCache.put(fieldDefine.getKey(), fieldDefine);
            FieldValueType valueType = fieldDefine.getValueType();
            this.initSpecificField(valueType, fieldDefine);
        }
    }

    public void buildTableInfo(DataDefinitionsCache dataDefinitionsCache) {
        String[] keyFieldIds = this.tableDefine.getBizKeyFieldsID();
        if (keyFieldIds == null) {
            return;
        }
        DimensionSet dimensionArray = new DimensionSet();
        for (int i = 0; i < keyFieldIds.length; ++i) {
            FieldDefine keyField = dataDefinitionsCache.findField(keyFieldIds[i]);
            this.dimFields.add(keyField);
            String dimension = null;
            if (StringUtils.isEmpty(dimension)) {
                dimension = this.periodField != null && keyField.getKey().equals(this.periodField.getKey()) || this.tableDefine.getKind() == TableKind.TABLE_KIND_ENTITY_PERIOD && keyFieldIds.length == 1 ? "DATATIME" : (keyFieldIds.length == 1 && this.tableDefine.getCode().startsWith("NR_PERIOD_") ? "DATATIME" : (this.tableDefine.getKind() == TableKind.TABLE_KIND_DICTIONARY ? dataDefinitionsCache.getDimensionNameByTableCode(this.tableDefine.getCode()) : dataDefinitionsCache.getDimensionNameByField(keyField)));
            }
            if (this.needRenameDimensions != null && this.needRenameDimensions.contains(dimension)) {
                dimension = keyField.getCode();
            } else {
                FieldDefine dimField = this.dimensionSearch.get(dimension);
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
            dimensionArray.addDimension(dimension);
            ColumnModelDefine column = this.getColumnModefine(keyField);
            this.fieldDimensionNames.put(column == null ? keyField.getCode() : column.getName(), dimension);
            this.dimensionSearch.put(dimension, keyField);
        }
        this.dimensions = dimensionArray;
    }

    public boolean isCodeBizKey() {
        return this.codeField == null || this.codeField != null && this.dimFields.size() == 1 && this.codeField.getKey().equals(this.dimFields.get(0).getKey());
    }

    public boolean isKeyField(String fieldName) {
        return this.fieldDimensionNames.containsKey(fieldName.toUpperCase());
    }

    public boolean isRecField(String fieldName) {
        if (this.recField == null) {
            return false;
        }
        ColumnModelDefine column = this.getColumnModefine(this.recField);
        return column.getName().equalsIgnoreCase(fieldName);
    }

    public boolean isBizOrderField(String fieldName) {
        if (this.bizOrderField == null) {
            return false;
        }
        ColumnModelDefine column = this.getColumnModefine(this.bizOrderField);
        return column.getName().equalsIgnoreCase(fieldName);
    }

    public String getDimensionName(String fieldName) {
        return this.fieldDimensionNames.get(fieldName.toUpperCase());
    }

    public FieldDefine getDimensionField(String dimension) {
        FieldDefine dimField = this.dimensionSearch.get(dimension);
        return dimField;
    }

    public String getDimensionFieldCode(String dimension) {
        FieldDefine dimField = this.dimensionSearch.get(dimension);
        if (dimField != null) {
            return dimField.getCode();
        }
        return null;
    }

    public FieldDefine parseSearchField(String fieldSign) {
        return this.parserSearch.get(fieldSign.toUpperCase());
    }

    public List<FieldDefine> getFieldsByValueType(FieldValueType valueType) {
        ArrayList<FieldDefine> resultFields = new ArrayList<FieldDefine>();
        for (FieldDefine fieldDefine : this.allFields) {
            if (fieldDefine.getValueType() != valueType) continue;
            resultFields.add(fieldDefine);
        }
        return resultFields;
    }

    @Deprecated
    public FieldDefine getPeriodField() {
        return this.periodField;
    }

    public FieldDefine getOrderField() {
        return this.orderField;
    }

    public FieldDefine getBizOrderField() {
        return this.bizOrderField;
    }

    public FieldDefine getRecField() {
        return this.recField;
    }

    public DimensionSet getDimensions() {
        return this.dimensions;
    }

    public TableDefine getTableDefine() {
        return this.tableDefine;
    }

    public List<FieldDefine> getDimFields() {
        return this.dimFields;
    }

    public FieldDefine getFieldByName(String fieldName) {
        return this.colNameSearch.get(fieldName.toUpperCase());
    }

    public List<FieldDefine> getAllFields() {
        return this.allFields;
    }

    public FieldDefine getCodeField() {
        return this.codeField;
    }

    public FieldDefine getTitleField() {
        return this.titleField;
    }

    public FieldDefine getUnitOrderField() {
        return this.unitOrderField;
    }

    public FieldDefine getFieldByKey(String fieldKey) {
        return this.fieldsCache.get(fieldKey);
    }

    private void initSpecificField(FieldValueType valueType, FieldDefine fieldDefine) {
        if (valueType == FieldValueType.FIELD_VALUE_RECORD_KEY || (this.tableDefine.getKind() == TableKind.TABLE_KIND_ENTITY || this.tableDefine.getKind() == TableKind.TABLE_KIND_DICTIONARY) && valueType == FieldValueType.FIELD_VALUE_UNIT_KEY) {
            if (this.recField == null || valueType == FieldValueType.FIELD_VALUE_RECORD_KEY) {
                this.recField = fieldDefine;
            }
        } else if (valueType == FieldValueType.FIELD_VALUE_INPUT_ORDER) {
            this.orderField = fieldDefine;
        } else if (valueType == FieldValueType.FIELD_VALUE_BIZKEY_ORDER) {
            this.bizOrderField = fieldDefine;
        } else if (valueType == FieldValueType.FIELD_VALUE_UNIT_ORDER) {
            this.unitOrderField = fieldDefine;
        } else if (valueType == FieldValueType.FIELD_VALUE_DICTIONARY_CODE || valueType == FieldValueType.FIELD_VALUE_UNIT_CODE) {
            this.codeField = fieldDefine;
        } else if (valueType == FieldValueType.FIELD_VALUE_DICTIONARY_TITLE || valueType == FieldValueType.FIELD_VALUE_UNIT_NAME) {
            this.titleField = fieldDefine;
        } else if (valueType == FieldValueType.FIELD_VALUE_DATE_VERSION) {
            this.versionField = fieldDefine;
        } else if (valueType == FieldValueType.FIELD_VALUE_PARENT_UNIT || valueType == FieldValueType.FIELD_VALUE_DICTIONARY_PARENT) {
            this.parentField = fieldDefine;
        } else if (valueType == FieldValueType.FIELD_VALUE_UNIT_TYPE) {
            this.typeField = fieldDefine;
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

    private String renameDimension(String dimension, FieldDefine keyField) {
        if (this.needRenameDimensions == null) {
            this.needRenameDimensions = new HashSet();
        }
        this.needRenameDimensions.add(dimension);
        String newDimension = keyField.getCode();
        return newDimension;
    }

    public FieldDefine getVersionField() {
        return this.versionField;
    }

    public FieldDefine getParentField() {
        return this.parentField;
    }

    public FieldDefine getTypeField() {
        return this.typeField;
    }
}

