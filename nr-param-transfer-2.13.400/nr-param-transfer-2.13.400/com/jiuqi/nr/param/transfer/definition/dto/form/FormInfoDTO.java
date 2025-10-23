/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 */
package com.jiuqi.nr.param.transfer.definition.dto.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import java.util.Date;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormInfoDTO {
    private String formCode;
    private String subTitle;
    private String description;
    private String serialNumber;
    private boolean gather;
    private String measureUnit;
    private int secretLevel;
    private FormType formType;
    private String formCondition;
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private Map<String, Object> formExtension;
    private String masterEntitiesKey;
    private String readOnlyCondition;
    private boolean quoteType;
    private boolean analysisForm;
    private boolean ledgerForm;
    private FillInAutomaticallyDue fillInAutomaticallyDue;
    private String formScheme;
    private String updateUser;
    private byte[] fillGuide;

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean isGather() {
        return this.gather;
    }

    public void setGather(boolean gather) {
        this.gather = gather;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public int getSecretLevel() {
        return this.secretLevel;
    }

    public void setSecretLevel(int secretLevel) {
        this.secretLevel = secretLevel;
    }

    public FormType getFormType() {
        return this.formType;
    }

    public void setFormType(FormType formType) {
        this.formType = formType;
    }

    public String getFormCondition() {
        return this.formCondition;
    }

    public void setFormCondition(String formCondition) {
        this.formCondition = formCondition;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Map<String, Object> getFormExtension() {
        return this.formExtension;
    }

    public void setFormExtension(Map<String, Object> formExtension) {
        this.formExtension = formExtension;
    }

    public String getMasterEntitiesKey() {
        return this.masterEntitiesKey;
    }

    public void setMasterEntitiesKey(String masterEntitiesKey) {
        this.masterEntitiesKey = masterEntitiesKey;
    }

    public String getReadOnlyCondition() {
        return this.readOnlyCondition;
    }

    public void setReadOnlyCondition(String readOnlyCondition) {
        this.readOnlyCondition = readOnlyCondition;
    }

    public boolean isQuoteType() {
        return this.quoteType;
    }

    public void setQuoteType(boolean quoteType) {
        this.quoteType = quoteType;
    }

    public boolean isAnalysisForm() {
        return this.analysisForm;
    }

    public void setAnalysisForm(boolean analysisForm) {
        this.analysisForm = analysisForm;
    }

    public boolean isLedgerForm() {
        return this.ledgerForm;
    }

    public void setLedgerForm(boolean ledgerForm) {
        this.ledgerForm = ledgerForm;
    }

    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        return this.fillInAutomaticallyDue;
    }

    public void setFillInAutomaticallyDue(FillInAutomaticallyDue fillInAutomaticallyDue) {
        this.fillInAutomaticallyDue = fillInAutomaticallyDue;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public byte[] getFillGuide() {
        return this.fillGuide;
    }

    public void setFillGuide(byte[] fillGuide) {
        this.fillGuide = fillGuide;
    }

    public static FormInfoDTO valueOf(FormDefine formDefine) {
        FormInfoDTO formInfo = new FormInfoDTO();
        formInfo.setDescription(formDefine.getDescription());
        formInfo.setFormCode(formDefine.getFormCode());
        formInfo.setFormCondition(formDefine.getFormCondition());
        formInfo.setFormType(formDefine.getFormType());
        formInfo.setGather(formDefine.getIsGather());
        formInfo.setKey(formDefine.getKey());
        formInfo.setMasterEntitiesKey(formDefine.getMasterEntitiesKey());
        formInfo.setMeasureUnit(formDefine.getMeasureUnit());
        formInfo.setOrder(formDefine.getOrder());
        formInfo.setOwnerLevelAndId(formDefine.getOwnerLevelAndId());
        formInfo.setReadOnlyCondition(formDefine.getReadOnlyCondition());
        formInfo.setSecretLevel(formDefine.getSecretLevel());
        formInfo.setSerialNumber(formDefine.getSerialNumber());
        formInfo.setSubTitle(formDefine.getSubTitle());
        formInfo.setTitle(formDefine.getTitle());
        formInfo.setUpdateTime(formDefine.getUpdateTime());
        formInfo.setVersion(formDefine.getVersion());
        formInfo.setAnalysisForm(formDefine.isAnalysisForm());
        formInfo.setQuoteType(formDefine.getQuoteType());
        formInfo.setFillInAutomaticallyDue(formDefine.getFillInAutomaticallyDue());
        formInfo.setFormScheme(formDefine.getFormScheme());
        Map formExtension = formDefine.getFormExtension();
        formInfo.setFormExtension(formExtension);
        formInfo.setUpdateUser(formDefine.getUpdateUser());
        return formInfo;
    }

    public void value2Define(DesignFormDefine formDefine) {
        formDefine.setDescription(this.getDescription());
        formDefine.setFormCode(this.getFormCode());
        formDefine.setFormCondition(this.getFormCondition());
        formDefine.setFormType(this.getFormType());
        formDefine.setIsGather(this.isGather());
        formDefine.setKey(this.getKey());
        formDefine.setMasterEntitiesKey(this.getMasterEntitiesKey());
        formDefine.setMeasureUnit(this.getMeasureUnit());
        formDefine.setOrder(this.getOrder());
        formDefine.setOwnerLevelAndId(this.getOwnerLevelAndId());
        formDefine.setReadOnlyCondition(this.getReadOnlyCondition());
        formDefine.setSecretLevel(this.getSecretLevel());
        formDefine.setSerialNumber(this.getSerialNumber());
        formDefine.setSubTitle(this.getSubTitle());
        formDefine.setTitle(this.getTitle());
        formDefine.setUpdateTime(this.getUpdateTime());
        formDefine.setVersion(this.getVersion());
        formDefine.setAnalysisForm(this.isAnalysisForm());
        formDefine.setQuoteType(this.isQuoteType());
        formDefine.setFillInAutomaticallyDue(this.getFillInAutomaticallyDue());
        formDefine.setFormScheme(this.getFormScheme());
        Map<String, Object> formExtension = this.getFormExtension();
        if (formExtension != null) {
            for (Map.Entry<String, Object> entry : formExtension.entrySet()) {
                formDefine.addExtensions(entry.getKey(), entry.getValue());
            }
        }
        formDefine.setUpdateUser(this.getUpdateUser());
    }
}

