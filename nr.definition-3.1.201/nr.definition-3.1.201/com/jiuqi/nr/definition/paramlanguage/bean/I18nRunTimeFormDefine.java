/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definition.paramlanguage.bean;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.FormViewType;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.IMeasureConfig;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.util.StringUtils;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class I18nRunTimeFormDefine
implements FormDefine {
    private final FormDefine formDefine;
    private String title;
    private byte[] binaryData;
    private String fillingGuide;

    public I18nRunTimeFormDefine(FormDefine formDefine) {
        this.formDefine = formDefine;
    }

    @Override
    public String getFormCode() {
        return this.formDefine.getFormCode();
    }

    @Override
    public String getSubTitle() {
        return this.formDefine.getSubTitle();
    }

    @Override
    public String getDescription() {
        return this.formDefine.getDescription();
    }

    @Override
    public String getSerialNumber() {
        return this.formDefine.getSerialNumber();
    }

    @Override
    public boolean getIsGather() {
        return this.formDefine.getIsGather();
    }

    @Override
    public String getMeasureUnit() {
        return this.formDefine.getMeasureUnit();
    }

    @Override
    public IMeasureConfig getMeasureConfig() {
        return this.formDefine.getMeasureConfig();
    }

    @Override
    public int getSecretLevel() {
        return this.formDefine.getSecretLevel();
    }

    @Override
    public FormType getFormType() {
        return this.formDefine.getFormType();
    }

    @Override
    public String getFormCondition() {
        return this.formDefine.getFormCondition();
    }

    @Override
    public PeriodType getFormPeriodType() {
        return this.formDefine.getFormPeriodType();
    }

    @Override
    public FormViewType getFormViewType() {
        return this.formDefine.getFormViewType();
    }

    @Override
    public String getMasterEntitiesKey() {
        return this.formDefine.getMasterEntitiesKey();
    }

    @Override
    public String getReadOnlyCondition() {
        return this.formDefine.getReadOnlyCondition();
    }

    @Override
    @Deprecated
    public String getFillingGuide() {
        return null == this.fillingGuide ? this.formDefine.getFillingGuide() : this.fillingGuide;
    }

    @Override
    public byte[] getBinaryData() {
        return null == this.binaryData ? this.formDefine.getBinaryData() : this.binaryData;
    }

    @Override
    public String getSurveyData() {
        return this.formDefine.getSurveyData();
    }

    @Override
    public String getScriptEditor() {
        return this.formDefine.getScriptEditor();
    }

    @Override
    public boolean getQuoteType() {
        return this.formDefine.getQuoteType();
    }

    @Override
    public boolean isAnalysisForm() {
        return this.formDefine.isAnalysisForm();
    }

    @Override
    public boolean getLedgerForm() {
        return this.formDefine.getLedgerForm();
    }

    @Override
    public Object getExtensionProp(String key) {
        return this.formDefine.getExtensionProp(key);
    }

    @Override
    public FormDefine clone() throws CloneNotSupportedException {
        return (FormDefine)super.clone();
    }

    @Override
    public Set<String> getExtensionPronNames() {
        return this.formDefine.getExtensionPronNames();
    }

    @Override
    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        return this.formDefine.getFillInAutomaticallyDue();
    }

    @Override
    public String getFormScheme() {
        return this.formDefine.getFormScheme();
    }

    @Override
    public Map<String, Object> getFormExtension() {
        return this.formDefine.getFormExtension();
    }

    public String getKey() {
        return this.formDefine.getKey();
    }

    public String getOrder() {
        return this.formDefine.getOrder();
    }

    public String getVersion() {
        return this.formDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.formDefine.getOwnerLevelAndId();
    }

    public Date getUpdateTime() {
        return this.formDefine.getUpdateTime();
    }

    public String getTitle() {
        return StringUtils.isEmpty((String)this.title) ? this.formDefine.getTitle() : this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBinaryData(byte[] binaryData) {
        this.binaryData = binaryData;
    }

    public void setFillingGuide(String fillingGuide) {
        this.fillingGuide = fillingGuide;
    }

    @Override
    public String getUpdateUser() {
        return this.formDefine.getUpdateUser();
    }
}

