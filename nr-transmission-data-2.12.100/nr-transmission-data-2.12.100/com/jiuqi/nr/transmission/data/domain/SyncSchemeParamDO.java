/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.transmission.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SyncSchemeParamDO {
    private String key;
    private String schemeKey;
    private String task;
    private int period;
    public static final int ENTITY_TYPE_THEN = 0;
    public static final int ENTITY_TYPE_LAST = -1;
    public static final int ENTITY_TYPE_NEXT = 1;
    public static final int ENTITY_TYPE_DEFINE = 2;
    private String periodValue;
    private int entityType;
    public static final int ENTITY_TYPE_ALL = 1;
    public static final int ENTITY_TYPE_CHOOSE = 2;
    private String entity;
    private int formType;
    public static final int FORM_TYPE_ALL = 1;
    public static final int FORM_TYPE_CHOOSE = 2;
    private String form;
    private int isUpload;
    public static final int DO_NOT_UPLOAD = 0;
    public static final int DO_UPLOAD = 1;
    private int allowForceUpload;
    public static final int NOT_ALLOW_FORCE_UPLOAD = 0;
    public static final int ALLOW_FORCE_UPLOAD = 1;
    private String description;
    private String dimKeys;
    private String dimValues;
    private String adjustPeriod;
    private String mappingSchemeKey;
    private String dataMessage;

    public SyncSchemeParamDO() {
    }

    public SyncSchemeParamDO(SyncSchemeParamDTO syncSchemeParamDTO) {
        this.key = syncSchemeParamDTO.getKey();
        this.schemeKey = syncSchemeParamDTO.getSchemeKey();
        this.task = syncSchemeParamDTO.getTask();
        this.period = syncSchemeParamDTO.getPeriod();
        this.periodValue = syncSchemeParamDTO.getPeriodValue();
        this.entityType = syncSchemeParamDTO.getEntityType();
        this.entity = syncSchemeParamDTO.getEntity();
        this.formType = syncSchemeParamDTO.getFormType();
        this.form = syncSchemeParamDTO.getForm();
        this.isUpload = syncSchemeParamDTO.getIsUpload();
        this.allowForceUpload = syncSchemeParamDTO.getAllowForceUpload();
        this.description = syncSchemeParamDTO.getDescription();
        this.dimKeys = syncSchemeParamDTO.getDimKeys();
        this.dimValues = syncSchemeParamDTO.getDimValues();
        this.adjustPeriod = syncSchemeParamDTO.getAdjustPeriod();
        this.mappingSchemeKey = syncSchemeParamDTO.getMappingSchemeKey();
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getPeriod() {
        return this.period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getEntityType() {
        return this.entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public int getFormType() {
        return this.formType;
    }

    public void setFormType(int formType) {
        this.formType = formType;
    }

    public String getForm() {
        return this.form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public int getIsUpload() {
        return this.isUpload;
    }

    public void setIsUpload(int isUpload) {
        this.isUpload = isUpload;
    }

    public int getAllowForceUpload() {
        return this.allowForceUpload;
    }

    public void setAllowForceUpload(int allowForceUpload) {
        this.allowForceUpload = allowForceUpload;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDimKeys() {
        return this.dimKeys;
    }

    public void setDimKeys(String dimKeys) {
        this.dimKeys = dimKeys;
    }

    public String getDimValues() {
        return this.dimValues;
    }

    public void setDimValues(String dimValues) {
        this.dimValues = dimValues;
    }

    public String getAdjustPeriod() {
        return this.adjustPeriod;
    }

    public void setAdjustPeriod(String adjustPeriod) {
        this.adjustPeriod = adjustPeriod;
    }

    public String getMappingSchemeKey() {
        return this.mappingSchemeKey;
    }

    public void setMappingSchemeKey(String mappingSchemeKey) {
        this.mappingSchemeKey = mappingSchemeKey;
    }

    public String getDataMessage() {
        return this.dataMessage;
    }

    public void setDataMessage(String dataMessage) {
        this.dataMessage = dataMessage;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("TP_KEY", this.getKey());
        map.put("TP_SCHEME_KEY", this.getSchemeKey());
        map.put("TP_TASK", this.getTask());
        map.put("TP_PERIOD", this.getPeriod());
        map.put("TP_PERIOD_VALUE", this.getPeriodValue());
        map.put("TP_ENTITY_TYPE", this.getEntityType());
        map.put("TP_ENTITY", this.getEntity());
        map.put("TP_FORM_TYPE", this.getFormType());
        map.put("TP_FORM", this.getForm());
        map.put("TP_IS_UPLOAD", this.getIsUpload());
        map.put("TP_FORCE_UPLOAD", this.getAllowForceUpload());
        map.put("TP_DESC", this.getDescription());
        map.put("TP_DIM_KEYS", this.getDimKeys());
        map.put("TP_DIM_VALUES", this.getDimValues());
        map.put("TP_ADJUST_PERIOD", this.getAdjustPeriod());
        map.put("TP_MAP_KEY", this.getMappingSchemeKey());
        map.put("TP_DATA_MESSAGE", this.getDataMessage());
        return map;
    }
}

