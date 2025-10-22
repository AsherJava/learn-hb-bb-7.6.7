/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.model.response.fieldupgrade;

import com.jiuqi.nr.system.check.model.response.fieldupgrade.FormSchemeInfoVO;
import java.util.List;

public class TaskInfoVO {
    private String key;
    private String title;
    private List<FormSchemeInfoVO> formSchemeInfos;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<FormSchemeInfoVO> getFormSchemeInfos() {
        return this.formSchemeInfos;
    }

    public void setFormSchemeInfos(List<FormSchemeInfoVO> formSchemeInfos) {
        this.formSchemeInfos = formSchemeInfos;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

