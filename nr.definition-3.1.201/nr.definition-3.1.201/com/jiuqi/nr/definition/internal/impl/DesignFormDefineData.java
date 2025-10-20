/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.FormViewType;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum2;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.IMeasureConfig;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class DesignFormDefineData
implements DesignFormDefine {
    private DesignFormDefine designFormDefine;
    private DesignBigDataService designBigDataService;

    public DesignFormDefineData(DesignFormDefine designFormDefine, DesignBigDataService designBigDataService) {
        this.designFormDefine = designFormDefine;
        this.designBigDataService = designBigDataService;
    }

    public DesignFormDefine getDesignFormDefine() {
        return this.designFormDefine;
    }

    @Override
    public String getFillingGuide() {
        if (null != this.designFormDefine.getFillingGuide()) {
            return this.designFormDefine.getFillingGuide();
        }
        try {
            byte[] bigData = this.designBigDataService.getBigData(this.getKey(), "FILLING_GUIDE");
            if (null == bigData) {
                return null;
            }
            this.designFormDefine.setFillingGuide(DesignFormDefineBigDataUtil.bytesToString(bigData));
            return this.designFormDefine.getFillingGuide();
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_QUERY, (Throwable)e);
        }
    }

    @Override
    public byte[] getBinaryData() {
        if (null != this.designFormDefine.getBinaryData()) {
            return this.designFormDefine.getBinaryData();
        }
        try {
            byte[] bigData = this.designBigDataService.getBigData(this.getKey(), "FORM_DATA");
            if (null == bigData) {
                return null;
            }
            this.designFormDefine.setBinaryData(bigData);
            return this.designFormDefine.getBinaryData();
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_QUERY, (Throwable)e);
        }
    }

    @Override
    public String getSurveyData() {
        if (null != this.designFormDefine.getSurveyData()) {
            return this.designFormDefine.getSurveyData();
        }
        try {
            byte[] bigData = this.designBigDataService.getBigData(this.getKey(), "BIG_SURVEY_DATA");
            if (null == bigData) {
                return null;
            }
            this.designFormDefine.setSurveyData(DesignFormDefineBigDataUtil.bytesToString(bigData));
            return this.designFormDefine.getSurveyData();
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_QUERY, (Throwable)e);
        }
    }

    @Override
    public String getScriptEditor() {
        if (null != this.designFormDefine.getScriptEditor()) {
            return this.designFormDefine.getScriptEditor();
        }
        try {
            byte[] bigData = this.designBigDataService.getBigData(this.getKey(), "BIG_SCRIPT_EDITOR");
            if (null == bigData) {
                return null;
            }
            this.designFormDefine.setScriptEditor(DesignFormDefineBigDataUtil.bytesToString(bigData));
            return this.designFormDefine.getScriptEditor();
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.FORM_QUERY, (Throwable)e);
        }
    }

    @Override
    public void setKey(String key) {
        this.designFormDefine.setKey(key);
    }

    @Override
    public void setOrder(String order) {
        this.designFormDefine.setOrder(order);
    }

    @Override
    public void setVersion(String version) {
        this.designFormDefine.setVersion(version);
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.designFormDefine.setOwnerLevelAndId(ownerLevelAndId);
    }

    @Override
    public void setTitle(String title) {
        this.designFormDefine.setTitle(title);
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.designFormDefine.setUpdateTime(updateTime);
    }

    @Override
    public void setFormCode(String fromCode) {
        this.designFormDefine.setFormCode(fromCode);
    }

    @Override
    public void setSubTitle(String subTitle) {
        this.designFormDefine.setSubTitle(subTitle);
    }

    @Override
    public void setDescription(String description) {
        this.designFormDefine.setDescription(description);
    }

    @Override
    public void setSerialNumber(String serialNumber) {
        this.designFormDefine.setSerialNumber(serialNumber);
    }

    @Override
    public void setIsGather(boolean isGather) {
        this.designFormDefine.setIsGather(isGather);
    }

    @Override
    public void setMeasureUnit(String measureUnit) {
        this.designFormDefine.setMeasureUnit(measureUnit);
    }

    @Override
    public void setSecretLevel(int secretLevel) {
        this.designFormDefine.setSecretLevel(secretLevel);
    }

    @Override
    public void setFormType(FormType formType) {
        this.designFormDefine.setFormType(formType);
    }

    @Override
    public void setFormCondition(String fromCondition) {
        this.designFormDefine.setFormCondition(fromCondition);
    }

    @Override
    public void setFormPeriodType(PeriodType periodType) {
        this.designFormDefine.setFormPeriodType(periodType);
    }

    @Override
    public void setFillingGuide(String fillingGuide) {
        this.designFormDefine.setFillingGuide(fillingGuide);
    }

    @Override
    public void setFormViewType(FormViewType viewType) {
        this.designFormDefine.setFormViewType(viewType);
    }

    @Override
    public void setBinaryData(byte[] binaryData) {
        this.designFormDefine.setBinaryData(binaryData);
    }

    @Override
    public void setMasterEntitiesKey(String masterEntitiesKey) {
        this.designFormDefine.setMasterEntitiesKey(masterEntitiesKey);
    }

    @Override
    public void setReadOnlyCondition(String readOnlyCondition) {
        this.designFormDefine.setReadOnlyCondition(readOnlyCondition);
    }

    @Override
    public void setSurveyData(String surveyData) {
        this.designFormDefine.setSurveyData(surveyData);
    }

    @Override
    public void setScriptEditor(String scriptEditor) {
        this.designFormDefine.setScriptEditor(scriptEditor);
    }

    @Override
    public void setQuoteType(boolean quoteType) {
        this.designFormDefine.setQuoteType(quoteType);
    }

    @Override
    public void setAnalysisForm(boolean analysisForm) {
        this.designFormDefine.setAnalysisForm(analysisForm);
    }

    @Override
    public void setLedgerForm(boolean ledgerForm) {
        this.designFormDefine.setLedgerForm(ledgerForm);
    }

    @Override
    public void addExtensions(String key, Object value) {
        this.designFormDefine.addExtensions(key, value);
    }

    @Override
    public void setFillInAutomaticallyDue(FillInAutomaticallyDue fillInAutomaticallyDue) {
        this.designFormDefine.setFillInAutomaticallyDue(fillInAutomaticallyDue);
    }

    @Override
    public void setFormScheme(String formScheme) {
        this.designFormDefine.setFormScheme(formScheme);
    }

    @Override
    public String getFormCode() {
        return this.designFormDefine.getFormCode();
    }

    @Override
    public String getSubTitle() {
        return this.designFormDefine.getSubTitle();
    }

    @Override
    public String getDescription() {
        return this.designFormDefine.getDescription();
    }

    @Override
    public String getSerialNumber() {
        return this.designFormDefine.getSerialNumber();
    }

    @Override
    public boolean getIsGather() {
        return this.designFormDefine.getIsGather();
    }

    @Override
    public String getMeasureUnit() {
        return this.designFormDefine.getMeasureUnit();
    }

    @Override
    public IMeasureConfig getMeasureConfig() {
        return this.designFormDefine.getMeasureConfig();
    }

    @Override
    public int getSecretLevel() {
        return this.designFormDefine.getSecretLevel();
    }

    @Override
    public FormType getFormType() {
        return this.designFormDefine.getFormType();
    }

    @Override
    public String getFormCondition() {
        return this.designFormDefine.getFormCondition();
    }

    @Override
    public PeriodType getFormPeriodType() {
        return this.designFormDefine.getFormPeriodType();
    }

    @Override
    public FormViewType getFormViewType() {
        return this.designFormDefine.getFormViewType();
    }

    @Override
    public String getMasterEntitiesKey() {
        return this.designFormDefine.getMasterEntitiesKey();
    }

    @Override
    public String getReadOnlyCondition() {
        return this.designFormDefine.getReadOnlyCondition();
    }

    @Override
    public boolean getQuoteType() {
        return this.designFormDefine.getQuoteType();
    }

    @Override
    public boolean isAnalysisForm() {
        return this.designFormDefine.isAnalysisForm();
    }

    @Override
    public boolean getLedgerForm() {
        return this.designFormDefine.getLedgerForm();
    }

    @Override
    public Object getExtensionProp(String key) {
        return this.designFormDefine.getExtensionProp(key);
    }

    @Override
    public FormDefine clone() throws CloneNotSupportedException {
        return this.designFormDefine.clone();
    }

    @Override
    public Set<String> getExtensionPronNames() {
        return this.designFormDefine.getExtensionPronNames();
    }

    @Override
    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        return this.designFormDefine.getFillInAutomaticallyDue();
    }

    @Override
    public String getFormScheme() {
        return this.designFormDefine.getFormScheme();
    }

    @Override
    public Map<String, Object> getFormExtension() {
        return this.designFormDefine.getFormExtension();
    }

    public Date getUpdateTime() {
        return this.designFormDefine.getUpdateTime();
    }

    public String getKey() {
        return this.designFormDefine.getKey();
    }

    public String getTitle() {
        return this.designFormDefine.getTitle();
    }

    public String getOrder() {
        return this.designFormDefine.getOrder();
    }

    public String getVersion() {
        return this.designFormDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.designFormDefine.getOwnerLevelAndId();
    }

    @Override
    public void setUpdateUser(String updateUser) {
        this.designFormDefine.setUpdateUser(updateUser);
    }

    @Override
    public String getUpdateUser() {
        return this.designFormDefine.getUpdateUser();
    }
}

