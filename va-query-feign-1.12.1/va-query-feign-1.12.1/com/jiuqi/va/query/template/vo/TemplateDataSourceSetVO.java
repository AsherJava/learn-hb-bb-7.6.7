/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.vo;

public class TemplateDataSourceSetVO {
    private String id;
    private String templateId;
    private String defineSql;

    public TemplateDataSourceSetVO() {
    }

    public TemplateDataSourceSetVO(String id, String templateId, String defineSql) {
        this.id = id;
        this.templateId = templateId;
        this.defineSql = defineSql;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getDefineSql() {
        return this.defineSql;
    }

    public void setDefineSql(String defineSql) {
        this.defineSql = defineSql;
    }

    public String toString() {
        return "TemplateDataSourceSetVO [id=" + this.id + ", templateId=" + this.templateId + ", defineSql=" + this.defineSql + "]";
    }
}

