/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.query.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.query.block.QueryDimensionDefine;
import com.jiuqi.nr.query.block.QueryDimensionType;
import com.jiuqi.nr.query.service.impl.DimensionInfor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

class DataQueryCache {
    public static final String CONST_ROWDIMENSION = "row";
    public static final String CONST_COLUMNDIMENSION = "column";
    private Map<String, Integer> cacheNumbers;
    private Map<String, List<QueryDimensionDefine>> dimensionList;
    private Map<String, DimensionInfor> allDimension;
    private Map<String, Map<String, String>> allEnumValues;
    private Boolean isExport;
    private Boolean sorted;
    private Stack<Integer> rowsToDelete;
    private Map<QueryDimensionType, Map<String, List<String>>> dimFieldValues;
    private Map<QueryDimensionType, Map<String, FieldDefine>> dimFields;
    private DimensionValueSet masterDimensionSet;

    public void setCacheNumbers(Map<String, Integer> cacheNumbers) {
        this.cacheNumbers = cacheNumbers;
    }

    public void putCacheNumbers(String key, Integer value) {
        if (this.cacheNumbers == null) {
            this.cacheNumbers = new LinkedHashMap<String, Integer>();
        }
        this.cacheNumbers.put(key, value);
    }

    public Integer getCacheNumber(String key) {
        if (this.cacheNumbers == null) {
            return 0;
        }
        int num = this.cacheNumbers.get(key) == null ? 0 : this.cacheNumbers.get(key);
        return num;
    }

    public Map<String, Integer> getCacheNumbers() {
        if (this.cacheNumbers == null) {
            this.cacheNumbers = new LinkedHashMap<String, Integer>();
        }
        return this.cacheNumbers;
    }

    public void setDimensionList(String key, List<QueryDimensionDefine> dimensions) {
        if (this.dimensionList == null) {
            this.dimensionList = new LinkedHashMap<String, List<QueryDimensionDefine>>();
        }
        this.dimensionList.put(key, dimensions);
    }

    public List<QueryDimensionDefine> getDimensionList(String key) {
        if (this.dimensionList == null) {
            return new ArrayList<QueryDimensionDefine>();
        }
        return this.dimensionList.get(key);
    }

    public Map<String, DimensionInfor> getAllDimension() {
        return this.allDimension;
    }

    public void setAllDimension(Map<String, DimensionInfor> allDimension) {
        this.allDimension = allDimension;
    }

    public Map<String, Map<String, String>> getAllEnumValues() {
        return this.allEnumValues;
    }

    public void setAllEnumValues(Map<String, Map<String, String>> allEnumValues) {
        this.allEnumValues = allEnumValues;
    }

    public Boolean getExport() {
        if (null == this.isExport) {
            return false;
        }
        return this.isExport;
    }

    public void setExport(Boolean export) {
        this.isExport = export;
    }

    public Boolean getSorted() {
        if (null == this.sorted) {
            return false;
        }
        return this.sorted;
    }

    public void setSorted(Boolean sorted) {
        this.sorted = sorted;
    }

    public Stack<Integer> getRowsToDelete() {
        return this.rowsToDelete;
    }

    public void setRowsToDelete(Stack<Integer> rowsToDelete) {
        this.rowsToDelete = rowsToDelete;
    }

    public void setDimFieldValues(QueryDimensionType type, Map<String, List<String>> values) {
        if (this.dimFieldValues == null) {
            this.dimFieldValues = new LinkedHashMap<QueryDimensionType, Map<String, List<String>>>();
        }
        this.dimFieldValues.put(type, values);
    }

    public Map<String, List<String>> getDimFieldValues(QueryDimensionType type) {
        Map<String, List<String>> value;
        if (this.dimFieldValues == null) {
            this.dimFieldValues = new LinkedHashMap<QueryDimensionType, Map<String, List<String>>>();
        }
        if ((value = this.dimFieldValues.get((Object)type)) == null) {
            value = new LinkedHashMap<String, List<String>>();
        }
        return value;
    }

    public void setDimFields(QueryDimensionType type, Map<String, FieldDefine> values) {
        if (this.dimFields == null) {
            this.dimFields = new LinkedHashMap<QueryDimensionType, Map<String, FieldDefine>>();
        }
        this.dimFields.put(type, values);
    }

    public List<FieldDefine> getDimFields(QueryDimensionType type) {
        ArrayList<Object> value;
        if (this.dimFields == null) {
            this.dimFields = new LinkedHashMap<QueryDimensionType, Map<String, FieldDefine>>();
        }
        if ((value = new ArrayList<FieldDefine>(this.dimFields.get((Object)type).values())) == null) {
            value = new ArrayList();
        }
        return value;
    }

    public Map<String, FieldDefine> getDimFieldsMap(QueryDimensionType type) {
        Map<String, FieldDefine> value;
        if (this.dimFields == null) {
            this.dimFields = new LinkedHashMap<QueryDimensionType, Map<String, FieldDefine>>();
        }
        if ((value = this.dimFields.get((Object)type)) == null) {
            value = new LinkedHashMap<String, FieldDefine>();
        }
        return value;
    }

    public void setMasterDS(DimensionValueSet value) {
        this.masterDimensionSet = value;
    }

    public DimensionValueSet getMasterDS() {
        return this.masterDimensionSet;
    }
}

