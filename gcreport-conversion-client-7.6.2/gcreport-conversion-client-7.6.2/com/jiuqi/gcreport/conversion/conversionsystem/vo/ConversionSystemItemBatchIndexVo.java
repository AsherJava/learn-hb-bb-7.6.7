/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.conversionsystem.vo;

import java.util.Set;

public class ConversionSystemItemBatchIndexVo {
    private String taskSchemeId;
    private String formId;
    private Set<String> indexIds;

    public String getTaskSchemeId() {
        return this.taskSchemeId;
    }

    public void setTaskSchemeId(String taskSchemeId) {
        this.taskSchemeId = taskSchemeId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Set<String> getIndexIds() {
        return this.indexIds;
    }

    public void setIndexIds(Set<String> indexIds) {
        this.indexIds = indexIds;
    }
}

