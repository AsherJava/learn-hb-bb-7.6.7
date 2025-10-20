/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.designer.web.rest.vo.SurveyCheckItem;
import java.io.Serializable;
import java.util.List;

public class ResponseSurveyCheckDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tableName;
    private String tableCode;
    private boolean tableExist = false;
    @JsonIgnore
    private List<DesignDataField> candidateZBList;
    private List<SurveyCheckItem> items;

    public List<SurveyCheckItem> getItems() {
        return this.items;
    }

    public void setItems(List<SurveyCheckItem> items) {
        this.items = items;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public boolean isTableExist() {
        return this.tableExist;
    }

    public void setTableExist(boolean tableExist) {
        this.tableExist = tableExist;
    }

    public List<DesignDataField> getCandidateZBList() {
        return this.candidateZBList;
    }

    public void setCandidateZBList(List<DesignDataField> candidateZBList) {
        this.candidateZBList = candidateZBList;
    }
}

