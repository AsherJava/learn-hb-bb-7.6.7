/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignFieldGroupDefine
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 */
package nr.single.para.parain.internal.cache;

import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignFieldGroupDefine;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.para.parain.internal.cache.FieldInfoDefine;
import nr.single.para.parain.internal.cache.TableInfoDefine;

public class RegionTableCache {
    private DesignDataRegionDefine regionDefine;
    private TableInfoDefine tableDefine;
    private Map<String, FieldInfoDefine> fieldCodeMap;
    private Map<String, FieldInfoDefine> fieldAlisMap;
    private Map<String, FieldInfoDefine> fieldIdMap;
    private String tableCode;
    private String mainTableCode;
    private String bizCode;
    private List<String> bizCodes;
    DesignFieldGroupDefine fieldGroup;
    private List<String> singleFields;
    private int tableSize;
    private String bizKeyOrderField;
    private boolean isTableNew;
    private String dataGroupKey;

    public RegionTableCache() {
        this.tableSize = 0;
        this.isTableNew = false;
    }

    public RegionTableCache(DesignDataRegionDefine regionDefine, TableInfoDefine tableDefine) {
        this.regionDefine = regionDefine;
        this.tableDefine = tableDefine;
        this.mainTableCode = tableDefine.getCode();
        this.tableCode = tableDefine.getCode();
        this.tableSize = 0;
        this.isTableNew = false;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getMainTableCode() {
        return this.mainTableCode;
    }

    public void setMainTableCode(String mainTableCode) {
        this.mainTableCode = mainTableCode;
    }

    public DesignDataRegionDefine getRegionDefine() {
        return this.regionDefine;
    }

    public void setRegionDefine(DesignDataRegionDefine regionDefine) {
        this.regionDefine = regionDefine;
    }

    public TableInfoDefine getTableDefine() {
        return this.tableDefine;
    }

    public void setTableDefine(TableInfoDefine tableDefine) {
        this.tableDefine = tableDefine;
    }

    public Map<String, FieldInfoDefine> getFieldCodeMap() {
        if (this.fieldCodeMap == null) {
            this.fieldCodeMap = new HashMap<String, FieldInfoDefine>();
        }
        return this.fieldCodeMap;
    }

    public Map<String, FieldInfoDefine> getFieldIDMap() {
        if (this.fieldIdMap == null) {
            this.fieldIdMap = new HashMap<String, FieldInfoDefine>();
        }
        return this.fieldIdMap;
    }

    public void addFieldDefine(FieldInfoDefine field) {
        if (!this.getFieldIDMap().containsKey(field.getKey())) {
            this.getFieldIDMap().put(field.getKey(), field);
            this.getFieldCodeMap().put(field.getCode(), field);
            this.getFieldAlisMap().put(field.getAlis(), field);
            this.tableSize = field.getFieldDefine() != null ? (this.tableSize += this.getFieldSize(field.getFieldDefine())) : (this.tableSize += this.getFieldSize(field.getDataField()));
        }
    }

    public FieldInfoDefine getFieldByCode(String fieldCode) {
        FieldInfoDefine field = null;
        if (this.fieldCodeMap.containsKey(fieldCode)) {
            field = this.fieldCodeMap.get(fieldCode);
        }
        return field;
    }

    public int getFieldSize(DesignFieldDefine field) {
        switch (field.getType()) {
            case FIELD_TYPE_FLOAT: {
                return 8;
            }
            case FIELD_TYPE_INTEGER: {
                return 8;
            }
            case FIELD_TYPE_DECIMAL: {
                return 8;
            }
            case FIELD_TYPE_GENERAL: {
                return 8;
            }
            case FIELD_TYPE_STRING: {
                return 3 * field.getSize();
            }
            case FIELD_TYPE_TEXT: {
                return 3 * field.getSize();
            }
            case FIELD_TYPE_DATE: {
                return 8;
            }
            case FIELD_TYPE_DATE_TIME: {
                return 8;
            }
            case FIELD_TYPE_TIME_STAMP: {
                return 8;
            }
            case FIELD_TYPE_TIME: {
                return 8;
            }
            case FIELD_TYPE_LOGIC: {
                return 1;
            }
            case FIELD_TYPE_UUID: {
                return 150;
            }
            case FIELD_TYPE_FILE: {
                return 150;
            }
            case FIELD_TYPE_PICTURE: {
                return 150;
            }
            case FIELD_TYPE_BINARY: {
                return 150;
            }
        }
        return 8;
    }

    public int getFieldSize(DesignDataField field) {
        int stringLen = 20;
        if (field.getPrecision() != null) {
            stringLen = field.getPrecision();
        }
        if (field.getDataFieldType() == null) {
            return stringLen;
        }
        switch (field.getDataFieldType()) {
            case INTEGER: {
                return 8;
            }
            case BIGDECIMAL: {
                return 8;
            }
            case STRING: {
                return 3 * stringLen;
            }
            case CLOB: {
                return 3 * stringLen;
            }
            case DATE: {
                return 8;
            }
            case BOOLEAN: {
                return 1;
            }
            case UUID: {
                return 150;
            }
            case FILE: {
                return 150;
            }
            case PICTURE: {
                return 150;
            }
        }
        return 8;
    }

    public int getFieldSize(FieldInfoDefine field) {
        if (field.getFieldDefine() != null) {
            return this.getFieldSize(field.getFieldDefine());
        }
        if (field.getDataField() != null) {
            return this.getFieldSize(field.getDataField());
        }
        return 0;
    }

    public int getFieldsSize(List<FieldInfoDefine> fieldList) {
        int tableFieldsSize = 0;
        for (FieldInfoDefine field : fieldList) {
            tableFieldsSize += this.getFieldSize(field);
        }
        return tableFieldsSize;
    }

    public int getFieldCount() {
        return this.getFieldIDMap().size();
    }

    public String getBizCode() {
        return this.bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public DesignFieldGroupDefine getFieldGroup() {
        return this.fieldGroup;
    }

    public void setFieldGroup(DesignFieldGroupDefine fieldGroup) {
        this.fieldGroup = fieldGroup;
    }

    public List<String> getSingleFields() {
        if (this.singleFields == null) {
            this.singleFields = new ArrayList<String>();
        }
        return this.singleFields;
    }

    public void setSingleFields(List<String> singleFields) {
        this.singleFields = singleFields;
    }

    public int getTableSize() {
        return this.tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    public String getBizKeyOrderField() {
        return this.bizKeyOrderField;
    }

    public void setBizKeyOrderField(String bizKeyOrderField) {
        this.bizKeyOrderField = bizKeyOrderField;
    }

    public boolean isTableNew() {
        return this.isTableNew;
    }

    public void setTableNew(boolean isTableNew) {
        this.isTableNew = isTableNew;
    }

    private List<String> getBizCodes() {
        if (this.bizCodes == null) {
            this.bizCodes = new ArrayList<String>();
        }
        return this.bizCodes;
    }

    private String[] getBizCodes2() {
        return this.getBizCodes().toArray(new String[0]);
    }

    private void setBizCodes(List<String> bizCodes) {
        this.bizCodes = bizCodes;
    }

    private void setBizCodes(String[] bizCodes) {
        this.getBizCodes().clear();
        for (String code : this.getBizCodes()) {
            this.getBizCodes().add(code);
        }
    }

    public Map<String, FieldInfoDefine> getFieldAlisMap() {
        if (this.fieldAlisMap == null) {
            this.fieldAlisMap = new HashMap<String, FieldInfoDefine>();
        }
        return this.fieldAlisMap;
    }

    public void setFieldAlisMap(Map<String, FieldInfoDefine> fieldAlisMap) {
        this.fieldAlisMap = fieldAlisMap;
    }

    public String getDataGroupKey() {
        return this.dataGroupKey;
    }

    public void setDataGroupKey(String dataGroupKey) {
        this.dataGroupKey = dataGroupKey;
    }
}

