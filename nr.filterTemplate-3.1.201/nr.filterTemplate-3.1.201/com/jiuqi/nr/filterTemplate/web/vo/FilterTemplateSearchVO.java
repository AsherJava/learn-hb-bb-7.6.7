/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.filterTemplate.web.vo;

public class FilterTemplateSearchVO {
    private String keyWords;
    private String filterTemplateID;
    private String filterTemplateTitle;
    private String entityID;
    private String entityTitle;
    private String entityCode;
    private String path;
    private Integer resultType;

    public String getKeyWords() {
        return this.keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getFilterTemplateID() {
        return this.filterTemplateID;
    }

    public void setFilterTemplateID(String filterTemplateID) {
        this.filterTemplateID = filterTemplateID;
    }

    public String getEntityID() {
        return this.entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getFilterTemplateTitle() {
        return this.filterTemplateTitle;
    }

    public void setFilterTemplateTitle(String filterTemplateTitle) {
        this.filterTemplateTitle = filterTemplateTitle;
    }

    public String getEntityTitle() {
        return this.entityTitle;
    }

    public void setEntityTitle(String entityTitle) {
        this.entityTitle = entityTitle;
    }

    public String getEntityCode() {
        return this.entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getResultType() {
        return this.resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }
}

