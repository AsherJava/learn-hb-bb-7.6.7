/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.nr.nrdx.adapter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.nrdx.adapter.param.common.TransferMode;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EParamVO
implements Serializable,
INRContext {
    private static final long serialVersionUID = 1L;
    private String taskKey;
    private String formSchemeKey;
    private String mappingKey;
    private Map<String, DimensionValue> masterKeys;
    private String formKeys;
    private List<String> exportTypes;
    private boolean expAttachment = true;
    private String contextEntityId;
    private String contextFilterExpression;
    private String crypto;
    private String rootFilePath;
    private List<ResItem> resItems;
    private TransferMode mode;
    boolean exportParam;
    boolean exportData;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getMappingKey() {
        return this.mappingKey;
    }

    public void setMappingKey(String mappingKey) {
        this.mappingKey = mappingKey;
    }

    public Map<String, DimensionValue> getMasterKeys() {
        return this.masterKeys;
    }

    public void setMasterKeys(Map<String, DimensionValue> masterKeys) {
        this.masterKeys = masterKeys;
    }

    public List<String> getExportTypes() {
        return this.exportTypes;
    }

    public void setExportTypes(List<String> exportTypes) {
        this.exportTypes = exportTypes;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public boolean isExpAttachment() {
        return this.expAttachment;
    }

    public void setExpAttachment(boolean expAttachment) {
        this.expAttachment = expAttachment;
    }

    public String getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(String formKeys) {
        this.formKeys = formKeys;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public void setContextEntityId(String contextEntityId) {
        this.contextEntityId = contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public void setContextFilterExpression(String contextFilterExpression) {
        this.contextFilterExpression = contextFilterExpression;
    }

    public String getCrypto() {
        return this.crypto;
    }

    public void setCrypto(String crypto) {
        this.crypto = crypto;
    }

    public String getRootFilePath() {
        return this.rootFilePath;
    }

    public void setRootFilePath(String rootFilePath) {
        this.rootFilePath = rootFilePath;
    }

    public List<ResItem> getResItems() {
        return this.resItems;
    }

    public void setResItems(List<ResItem> resItems) {
        this.resItems = resItems;
    }

    public TransferMode getMode() {
        return this.mode;
    }

    public void setMode(TransferMode mode) {
        this.mode = mode;
    }

    public boolean isExportParam() {
        return this.exportParam;
    }

    public void setExportParam(boolean exportParam) {
        this.exportParam = exportParam;
    }

    public boolean isExportData() {
        return this.exportData;
    }

    public void setExportData(boolean exportData) {
        this.exportData = exportData;
    }
}

