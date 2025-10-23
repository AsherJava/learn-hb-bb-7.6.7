/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.core.DeployResultDetail
 */
package com.jiuqi.nr.datascheme.fix.core;

import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.core.DeployResultDetail;
import com.jiuqi.nr.datascheme.fix.common.DeployExType;
import com.jiuqi.nr.datascheme.fix.core.DeployFixCheckExResult;
import java.util.Map;

public class DeployExCheckResultDTO {
    protected String dataSchemeKey;
    protected String dataTableKey;
    protected String dataTableCode;
    protected String dataSchemeCode;
    protected String dataTableTitle;
    protected String dataSchemeTitle;
    protected DeployExType exType;
    protected String exDesc;
    protected String deployMessage;
    protected boolean isScheme;
    protected Map<String, String> tmKeyAndLtName;

    public DeployExCheckResultDTO() {
    }

    public DeployExCheckResultDTO(DeployExType exType) {
        this.exType = exType;
        this.exDesc = exType.getTitle();
    }

    public DeployExCheckResultDTO(String dataSchemeKey, DeployResultDetail detial, DeployFixCheckExResult checkResult, boolean isScheme) {
        this.dataSchemeKey = dataSchemeKey;
        this.dataTableKey = detial.getTableKey();
        this.dataTableCode = detial.getTableCode();
        this.dataTableTitle = detial.getTableTitle();
        this.exType = checkResult.getExType();
        this.tmKeyAndLtName = checkResult.getTmKeyAndLtName();
        this.isScheme = isScheme;
    }

    public DeployExCheckResultDTO(DesignDataScheme dataScheme, String exDesc, boolean isScheme) {
        this.dataSchemeKey = dataScheme.getKey();
        this.dataSchemeCode = dataScheme.getCode();
        this.dataSchemeTitle = dataScheme.getTitle();
        this.deployMessage = exDesc;
        this.isScheme = isScheme;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public String getDataTableCode() {
        return this.dataTableCode;
    }

    public void setDataTableCode(String dataTableCode) {
        this.dataTableCode = dataTableCode;
    }

    public String getDataTableTitle() {
        return this.dataTableTitle;
    }

    public void setDataTableTitle(String dataTableTitle) {
        this.dataTableTitle = dataTableTitle;
    }

    public DeployExType getExType() {
        return this.exType;
    }

    public void setExType(DeployExType exType) {
        this.exType = exType;
    }

    public String getExDesc() {
        return this.exDesc;
    }

    public void setExDesc(String exDesc) {
        this.exDesc = exDesc;
    }

    public Map<String, String> getTmKeyAndLtName() {
        return this.tmKeyAndLtName;
    }

    public void setTmKeyAndLtName(Map<String, String> tmKeyAndLtName) {
        this.tmKeyAndLtName = tmKeyAndLtName;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getDataSchemeTitle() {
        return this.dataSchemeTitle;
    }

    public void setDataSchemeTitle(String dataSchemeTitle) {
        this.dataSchemeTitle = dataSchemeTitle;
    }

    public String getDeployMessage() {
        return this.deployMessage;
    }

    public void setDeployMessage(String deployMessage) {
        this.deployMessage = deployMessage;
    }

    public boolean isScheme() {
        return this.isScheme;
    }

    public void setScheme(boolean scheme) {
        this.isScheme = scheme;
    }
}

