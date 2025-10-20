/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.bde.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public class OrgMappingDTO {
    private String acctOrgCode;
    private String acctOrgName;
    private String reportOrgCode;
    private String reportOrgName;
    private String acctBookCode;
    private String dataSchemeCode;
    private String pluginType;
    private String dataSourceCode;
    private String assistCode;
    private String assistName;
    private List<OrgMappingItem> orgMappingItems;

    public String getAcctOrgCode() {
        return this.acctOrgCode;
    }

    public void setAcctOrgCode(String acctOrgCode) {
        this.acctOrgCode = acctOrgCode;
    }

    public String getAcctOrgName() {
        return this.acctOrgName;
    }

    public void setAcctOrgName(String acctOrgName) {
        this.acctOrgName = acctOrgName;
    }

    public String getReportOrgCode() {
        return this.reportOrgCode;
    }

    public void setReportOrgCode(String reportOrgCode) {
        this.reportOrgCode = reportOrgCode;
    }

    public String getReportOrgName() {
        return this.reportOrgName;
    }

    public void setReportOrgName(String reportOrgName) {
        this.reportOrgName = reportOrgName;
    }

    public String getAcctBookCode() {
        return this.acctBookCode;
    }

    public void setAcctBookCode(String acctBookCode) {
        this.acctBookCode = acctBookCode;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getPluginType() {
        return this.pluginType;
    }

    public void setPluginType(String pluginType) {
        this.pluginType = pluginType;
    }

    public String getDataSourceCode() {
        return this.dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public List<OrgMappingItem> getOrgMappingItems() {
        return this.orgMappingItems;
    }

    public void setOrgMappingItems(List<OrgMappingItem> orgMappingItems) {
        this.orgMappingItems = orgMappingItems;
    }

    public String getAssistCode() {
        return this.assistCode;
    }

    public void setAssistCode(String assistCode) {
        this.assistCode = assistCode;
    }

    public String getAssistName() {
        return this.assistName;
    }

    public void setAssistName(String assistName) {
        this.assistName = assistName;
    }

    public String toString() {
        return "OrgMappingDTO{acctOrgCode='" + this.acctOrgCode + '\'' + ", acctOrgName='" + this.acctOrgName + '\'' + ", reportOrgCode='" + this.reportOrgCode + '\'' + ", reportOrgName='" + this.reportOrgName + '\'' + ", acctBookCode='" + this.acctBookCode + '\'' + ", dataSchemeCode='" + this.dataSchemeCode + '\'' + ", pluginType='" + this.pluginType + '\'' + ", assistCode='" + this.assistCode + '\'' + ", assistName='" + this.assistName + '\'' + ", dataSourceCode='" + this.dataSourceCode + '\'' + ", orgMappingItems=" + this.orgMappingItems + '}';
    }
}

