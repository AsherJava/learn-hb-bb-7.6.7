/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.task.form.formcopy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyPushRecord;
import com.jiuqi.nr.task.form.formcopy.bean.impl.FormCopyPushRecordImpl;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public class FormCopyRecordPush {
    private String key;
    private String srcFormSchemeKey;
    private Date updateTime;
    private Map<String, List<String>> formSchemeMap;
    private Map<String, List<String>> attScheme;

    public FormCopyRecordPush() {
    }

    public FormCopyRecordPush(IFormCopyPushRecord iFormCopyPushRecord) throws JsonProcessingException {
        this.key = iFormCopyPushRecord.getKey();
        this.srcFormSchemeKey = iFormCopyPushRecord.getSrcFormSchemeKey();
        this.updateTime = iFormCopyPushRecord.getUpdateTime();
        String attSchemeStr = iFormCopyPushRecord.getAttScheme();
        String formToFormSchemeMap = iFormCopyPushRecord.getFormSchemeMap();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (StringUtils.hasText(formToFormSchemeMap)) {
            this.formSchemeMap = (Map)objectMapper.readValue(formToFormSchemeMap, Map.class);
        }
        if (StringUtils.hasLength(attSchemeStr)) {
            this.attScheme = (Map)objectMapper.readValue(attSchemeStr, Map.class);
        }
    }

    public IFormCopyPushRecord toFormCopyPushRecord() throws JsonProcessingException {
        FormCopyPushRecordImpl formCopyPushRecord = new FormCopyPushRecordImpl();
        formCopyPushRecord.setKey(StringUtils.hasLength(this.key) ? this.key : UUIDUtils.getKey());
        formCopyPushRecord.setSrcFormSchemeKey(this.srcFormSchemeKey);
        formCopyPushRecord.setUpdateTime(this.updateTime);
        ObjectMapper objectMapper = new ObjectMapper();
        if (null != this.formSchemeMap) {
            formCopyPushRecord.setFormSchemeMap(objectMapper.writeValueAsString(this.formSchemeMap));
        }
        if (null != this.attScheme) {
            formCopyPushRecord.setAttScheme(objectMapper.writeValueAsString(this.attScheme));
        }
        return formCopyPushRecord;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Map<String, List<String>> getFormSchemeMap() {
        return this.formSchemeMap;
    }

    public void setFormSchemeMap(Map<String, List<String>> formSchemeMap) {
        this.formSchemeMap = formSchemeMap;
    }

    public Map<String, List<String>> getAttScheme() {
        return this.attScheme;
    }

    public void setAttScheme(Map<String, List<String>> attScheme) {
        this.attScheme = attScheme;
    }
}

