/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.designer.formcopy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.designer.formcopy.FormCopyAttSchemeMap;
import com.jiuqi.nr.designer.formcopy.bean.IFormCopyRecord;
import com.jiuqi.nr.designer.formcopy.bean.impl.FormCopyRecordImpl;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public class FormCopyRecord {
    private String key;
    private String formSchemeKey;
    private Date updateTime;
    private List<String> formKeys;
    private Map<String, FormCopyAttSchemeMap> attScheme;

    public FormCopyRecord() {
    }

    public FormCopyRecord(IFormCopyRecord iFormCopyRecord) throws JsonProcessingException {
        JavaType valueType;
        this.key = iFormCopyRecord.getKey();
        this.formSchemeKey = iFormCopyRecord.getFormSchemeKey();
        this.updateTime = iFormCopyRecord.getUpdateTime();
        String attSchemeStr = iFormCopyRecord.getAttScheme();
        String formKeysStr = iFormCopyRecord.getFormKeys();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (StringUtils.hasText(formKeysStr)) {
            valueType = objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{String.class});
            this.formKeys = (List)objectMapper.readValue(formKeysStr, valueType);
            this.formKeys = (List)objectMapper.readValue(formKeysStr, List.class);
        }
        if (StringUtils.hasLength(attSchemeStr)) {
            valueType = objectMapper.getTypeFactory().constructParametricType(Map.class, new Class[]{String.class, FormCopyAttSchemeMap.class});
            this.attScheme = (Map)objectMapper.readValue(attSchemeStr, valueType);
            this.attScheme = (Map)objectMapper.readValue(attSchemeStr, Map.class);
        }
    }

    public IFormCopyRecord toIFormCopyScheme() throws JsonProcessingException {
        FormCopyRecordImpl iFormCopyRecord = new FormCopyRecordImpl();
        iFormCopyRecord.setKey(StringUtils.hasLength(this.key) ? this.key : UUIDUtils.getKey());
        iFormCopyRecord.setFormSchemeKey(this.formSchemeKey);
        iFormCopyRecord.setUpdateTime(this.updateTime);
        ObjectMapper objectMapper = new ObjectMapper();
        if (null != this.formKeys) {
            iFormCopyRecord.setFormKeys(objectMapper.writeValueAsString(this.formKeys));
        }
        if (null != this.attScheme) {
            iFormCopyRecord.setAttScheme(objectMapper.writeValueAsString(this.attScheme));
        }
        return iFormCopyRecord;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Map<String, FormCopyAttSchemeMap> getAttScheme() {
        return this.attScheme;
    }

    public void setAttScheme(Map<String, FormCopyAttSchemeMap> attScheme) {
        this.attScheme = attScheme;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }
}

