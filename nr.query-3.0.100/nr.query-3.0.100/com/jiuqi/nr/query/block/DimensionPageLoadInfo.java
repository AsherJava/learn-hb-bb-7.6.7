/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.query.block;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.query.deserializer.DimensionPageLoadInfoDeserializer;
import com.jiuqi.nr.query.serializer.DimensionPageLoadInfoSerializer;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonSerialize(using=DimensionPageLoadInfoSerializer.class)
@JsonDeserialize(using=DimensionPageLoadInfoDeserializer.class)
public class DimensionPageLoadInfo {
    private static final Logger log = LoggerFactory.getLogger(DimensionPageLoadInfo.class);
    public static final String PAGEINFO_DIMENSIONROWS = "dimensionRows";
    public static final String PAGEINFO_RECORDINDEX = "recordIndex";
    public static final String PAGEINFO_RECORDSTART = "recordStart";
    public static final String PAGEINFO_FIRSTDIMENSIONID = "firstDimensionID";
    public static final String PAGEINFO_STRUCNODE = "strucNode";
    public static final String PAGEINFO_DIMENSIONNAME = "dimensionName";
    public static final String PAGEINFO_PARENTDEPTH = "parentDepth";
    public static final String PAGEINFO_LASTITEM = "lastItem";
    public static final String PAGEINFO_PASSEDROWS = "passedRows";
    public static final String PAGEINFO_ISFIRSTPAGE = "isFirstPage";
    public static final String PAGEINFO_DETAILSTART = "detailStart";
    public static final String PAGEINFO_DETAILEND = "detailEnd";
    public static final String PAGEINFO_HASDATAITEMS = "hasDataItems";
    public static final String PAGEINFO_LASTDIMENSIONNAME = "lastDimensionName";
    public static final String PAGEINFO_LASTITEMCOUNT = "lastItemCount";
    public static final String PAGEINFO_LASTDIMENSIONRECORD = "lastDimensionRecord";
    public static final String PAGEINFO_LASTDIMENSIONVALUESET = "lastDimensionValueSet";
    public Map<String, Map<Integer, List<String>>> dimensionRows = new LinkedHashMap<String, Map<Integer, List<String>>>();
    public Map<String, Integer> recordIndex = new LinkedHashMap<String, Integer>();
    public Map<String, Integer> recordStart = new LinkedHashMap<String, Integer>();
    public Map<Integer, String> hasDataItems = new LinkedHashMap<Integer, String>();
    public String lastItem;
    public Map<Integer, List<String>> passedRows = new LinkedHashMap<Integer, List<String>>();
    public Map<Integer, List<String>> lastPassedRows = new LinkedHashMap<Integer, List<String>>();
    public String firstDimensionID;
    public String dimensionName;
    public String strucNode;
    public int parentDepth;
    public Boolean isFirstPage = true;
    public int detailStart;
    public int detailEnd;
    public String lastDimensionName = null;
    public int lastItemCount;
    public Map<String, Integer> lastDimensionRecord = new LinkedHashMap<String, Integer>();
    public String lastDimValueSetStr;
    public DimensionValueSet lastDimValueSet;
    public DimensionValueSet dimNodeSet;
    public String expandDimName;

    public Map<String, Map<Integer, List<String>>> getDimensionRows(String dimensionRowsStr) {
        if (dimensionRowsStr == null) {
            return this.dimensionRows;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map dimensionRows = null;
        try {
            dimensionRows = (Map)objectMapper.readValue(dimensionRowsStr, (TypeReference)new TypeReference<Map<String, Map<Integer, List<String>>>>(){});
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return dimensionRows;
    }

    public Map<String, Integer> getRecordIndex(String recordIndexStr) {
        if (recordIndexStr == null) {
            return this.recordIndex;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map recordIndex = null;
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, new Class[]{String.class, Integer.class});
            recordIndex = (Map)objectMapper.readValue(recordIndexStr, javaType);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return recordIndex;
    }

    public Map<String, Integer> getRecordStart(String recordStartStr) {
        if (recordStartStr == null) {
            return this.recordStart;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map recordStart = null;
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, new Class[]{String.class, Integer.class});
            recordStart = (Map)objectMapper.readValue(recordStartStr, javaType);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return recordStart;
    }

    public Map<Integer, List<String>> getPassedRows(String passedRowsStr) {
        if (passedRowsStr == null) {
            return this.passedRows;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map passedRows = null;
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, new Class[]{Integer.class, List.class});
            passedRows = (Map)objectMapper.readValue(passedRowsStr, javaType);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return passedRows;
    }

    public Map<Integer, String> gethasDataItems(String hasDataItemsStr) {
        if (hasDataItemsStr == null) {
            return this.hasDataItems;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map hasDataItems = null;
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, new Class[]{Integer.class, String.class});
            hasDataItems = (Map)objectMapper.readValue(hasDataItemsStr, javaType);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return hasDataItems;
    }

    public Map<String, Integer> getLastDimensionRecord(String lastDimensionRecordStr) {
        if (lastDimensionRecordStr == null) {
            return this.lastDimensionRecord;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map lastDimensionRecord = null;
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, new Class[]{String.class, Integer.class});
            lastDimensionRecord = (Map)objectMapper.readValue(lastDimensionRecordStr, javaType);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return lastDimensionRecord;
    }
}

