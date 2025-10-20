/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.formcopy.FormCopyAttSchemeMap;
import com.jiuqi.nr.designer.formcopy.FormCopyRecord;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class FormSyncRecordVO {
    private String key;
    private String title;
    private String formSchemeKey;
    private List<String> formKeys;
    private Map<String, FormCopyAttSchemeMap> attScheme;

    public FormSyncRecordVO(FormCopyRecord record) {
        this.key = record.getKey();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.title = simpleDateFormat.format(record.getUpdateTime());
        this.formKeys = record.getFormKeys();
        this.attScheme = record.getAttScheme();
        this.formSchemeKey = record.getFormSchemeKey();
    }

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return this.title;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public Map<String, FormCopyAttSchemeMap> getAttScheme() {
        return this.attScheme;
    }
}

