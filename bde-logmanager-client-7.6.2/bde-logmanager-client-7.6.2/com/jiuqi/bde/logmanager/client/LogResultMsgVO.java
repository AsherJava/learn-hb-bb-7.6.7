/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.logmanager.client;

import com.jiuqi.bde.logmanager.client.LogManagerInfoItemVO;
import java.util.List;

public class LogResultMsgVO {
    private String taskName;
    private String schemeName;
    private String periodScheme;
    private String unitCode;
    private String unitName;
    private String userName;
    private String currency;
    private String formNames;
    private String resultMessage;
    private List<LogManagerInfoItemVO> logInfoItemTableData;

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getPeriodScheme() {
        return this.periodScheme;
    }

    public void setPeriodScheme(String periodScheme) {
        this.periodScheme = periodScheme;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFormNames() {
        return this.formNames;
    }

    public void setFormNames(String formNames) {
        this.formNames = formNames;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public List<LogManagerInfoItemVO> getLogInfoItemTableData() {
        return this.logInfoItemTableData;
    }

    public void setLogInfoItemTableData(List<LogManagerInfoItemVO> logInfoItemTableData) {
        this.logInfoItemTableData = logInfoItemTableData;
    }
}

