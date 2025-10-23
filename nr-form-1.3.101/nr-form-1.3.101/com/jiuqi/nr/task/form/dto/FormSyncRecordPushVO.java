/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.form.formcopy.FormCopyRecordPush;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class FormSyncRecordPushVO {
    private String key;
    private String title;
    private String srcFormSchemeKey;
    private Map<String, List<String>> formSchemeMap;
    private Map<String, List<String>> attScheme;

    public FormSyncRecordPushVO(FormCopyRecordPush record) {
        this.key = record.getKey();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.title = simpleDateFormat.format(record.getUpdateTime());
        this.formSchemeMap = record.getFormSchemeMap();
        this.attScheme = record.getAttScheme();
        this.srcFormSchemeKey = record.getSrcFormSchemeKey();
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
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

