/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.zbselector.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.zbselector.service.impl.FieldSelectCacheDeserializer;
import com.jiuqi.nr.zbselector.service.impl.FieldSelectCacheSerializer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonSerialize(using=FieldSelectCacheSerializer.class)
@JsonDeserialize(using=FieldSelectCacheDeserializer.class)
public class FieldSelectCache
implements Cloneable {
    public static final String FS_SCHEMEKEY = "schemeKey";
    public static final String FS_TASKKEY = "taskKey";
    public static final String FS_MASTERKEYS = "masterKeys";
    public static final String FS_BIZFIELDS = "bizFields";
    public static final String FS_FIELDCOUNT = "fieldCount";
    public static final String FS_RELATEDTASKS = "relatedTasks";
    public static final String FS_MASTERDIMENSIONS = "masterDimensions";
    public static final String FS_BIZFIELDKEYS = "bizFieldKeys";
    public static final String FS_PERIODTYPE = "periodType";
    private static final Logger logger = LoggerFactory.getLogger(FieldSelectCache.class);
    private String schemeKey;
    private String taskKey;
    private String masterKeys;
    private String masterDimensions;
    private String bizFieldKeys;
    private Map<String, Object> bizFields;
    private int fieldCount;
    private List<TaskDefine> relatedTasks;
    private String periodType;

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setMasterKeys(String masterKeys) {
        this.masterKeys = masterKeys;
    }

    public String getMasterKeys() {
        return this.masterKeys;
    }

    public void setMasterDimensions(String masterDimensions) {
        this.masterDimensions = masterDimensions.indexOf("[") >= 0 ? masterDimensions.substring(1, masterDimensions.length() - 1) : masterDimensions;
    }

    protected String getMasterDimensions() {
        return this.masterDimensions;
    }

    public void setBizFieldKeys(String bizFieldKeys) {
        this.bizFieldKeys = bizFieldKeys;
    }

    public String getBizFieldKeys() {
        return this.bizFieldKeys;
    }

    public Map<String, Object> getBizFields() {
        return this.bizFields;
    }

    public void setBizFields(Map<String, Object> bizFields) {
        this.bizFields = bizFields;
    }

    public int getFieldCount() {
        return this.fieldCount;
    }

    public void setFieldCount(int fieldCount) {
        this.fieldCount = fieldCount;
    }

    public List<TaskDefine> getRelatedTasks() {
        return this.relatedTasks;
    }

    public void setRelatedTasks(List<TaskDefine> relatedTasks) {
        this.relatedTasks = relatedTasks;
    }

    public void setRelatedDesignTasks(List<DesignTaskDefine> relatedTasks) {
        ArrayList<TaskDefine> list = new ArrayList<TaskDefine>();
        relatedTasks.forEach(task -> list.add((TaskDefine)task));
        this.relatedTasks = list;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public static FieldSelectCache parse(Map<String, Object> cache) {
        FieldSelectCache newCache = new FieldSelectCache();
        if (cache.get(FS_SCHEMEKEY) != null) {
            newCache.setSchemeKey(cache.get(FS_SCHEMEKEY).toString());
        }
        if (cache.get(FS_TASKKEY) != null) {
            newCache.setTaskKey(cache.get(FS_TASKKEY).toString());
        }
        newCache.setMasterKeys(cache.get(FS_MASTERKEYS).toString());
        newCache.setMasterDimensions(cache.get(FS_MASTERDIMENSIONS).toString());
        newCache.setBizFieldKeys(cache.get(FS_BIZFIELDKEYS).toString());
        newCache.setPeriodType(cache.get(FS_PERIODTYPE).toString());
        newCache.setFieldCount(Integer.parseInt(cache.get(FS_FIELDCOUNT).toString()));
        if (cache.get(FS_BIZFIELDS) != null) {
            newCache.setBizFields((Map)cache.get(FS_BIZFIELDS));
        }
        return newCache;
    }

    protected FieldSelectCache clone() {
        FieldSelectCache cache = null;
        try {
            cache = (FieldSelectCache)super.clone();
        }
        catch (CloneNotSupportedException e) {
            logger.error(e.getMessage(), e);
        }
        return cache;
    }

    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString((Object)this);
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

