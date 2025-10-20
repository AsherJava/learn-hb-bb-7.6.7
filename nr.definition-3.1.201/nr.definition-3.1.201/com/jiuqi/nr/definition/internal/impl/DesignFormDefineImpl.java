/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLink
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.FormViewType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.IMeasureConfig;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.log.CompareFieldType;
import com.jiuqi.nr.definition.log.ComparePropertyAnno;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@DBAnno.DBTable(dbTable="NR_PARAM_FORM_DES")
@DBAnno.DBLink(linkWith=DesignFormGroupLink.class, linkField="formKey", field="key")
@ComparePropertyAnno.CompareType
public class DesignFormDefineImpl
implements DesignFormDefine {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="fm_code")
    @ComparePropertyAnno.CompareField(title="\u62a5\u8868\u6807\u8bc6")
    private String formCode;
    @DBAnno.DBField(dbField="fm_sub_title")
    @ComparePropertyAnno.CompareField(title="\u62a5\u8868\u526f\u6807\u9898")
    private String subTitle;
    @DBAnno.DBField(dbField="fm_desc")
    private String description;
    @DBAnno.DBField(dbField="fm_serial_Number")
    private String serialNumber;
    @DBAnno.DBField(dbField="fm_gather_type", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    @ComparePropertyAnno.CompareField(title="\u6c47\u603b\u65b9\u5f0f")
    private boolean isGather;
    @DBAnno.DBField(dbField="fm_unit")
    private String measureUnit;
    @ComparePropertyAnno.CompareField(title="\u5bc6\u7ea7")
    @DBAnno.DBField(dbField="fm_secret_level")
    private int secretLevel;
    @DBAnno.DBField(dbField="fm_type", tranWith="transFormType", dbType=Integer.class, appType=FormType.class)
    @ComparePropertyAnno.CompareField(title="\u62a5\u8868\u7c7b\u578b")
    private FormType formType;
    @DBAnno.DBField(dbField="fm_condition")
    @ComparePropertyAnno.CompareField(title="\u62a5\u8868\u8fc7\u6ee4\u6761\u4ef6")
    private String formCondition;
    @ComparePropertyAnno.CompareField(title="\u586b\u62a5\u8bf4\u660e")
    private String fillingGuide;
    @DBAnno.DBField(dbField="fm_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="fm_title")
    @ComparePropertyAnno.CompareField(title="\u62a5\u8868\u6807\u9898")
    private String title;
    @DBAnno.DBField(dbField="fm_order", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="fm_version")
    private String version;
    @DBAnno.DBField(dbField="fm_level")
    @ComparePropertyAnno.CompareField(title="\u7ea7\u6b21")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="fm_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="fm_formExtensions", tranWith="transExtensions", dbType=Clob.class, appType=HashMap.class)
    private HashMap<String, Object> formExtension;
    @DBAnno.DBField(dbField="fm_master_key")
    @ComparePropertyAnno.CompareField(title="\u62a5\u8868\u4e3b\u4f53", type=CompareFieldType.VIEW)
    private String masterEntitiesKey;
    @DBAnno.DBField(dbField="fm_readonly_condition")
    private String readOnlyCondition;
    @DBAnno.DBField(dbField="fm_quote_type", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean quoteType;
    @DBAnno.DBField(dbField="fm_analysis_form", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean analysisForm;
    @DBAnno.DBField(dbField="fm_ledger_form", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean ledgerForm;
    private FillInAutomaticallyDue fillInAutomaticallyDue;
    @DBAnno.DBField(dbField="FM_AUTOMATICALLY_DUE_TYPE")
    private int automaticallyDueType = FillInAutomaticallyDue.Type.DEFAULT.getValue();
    @DBAnno.DBField(dbField="FM_AUTOMATICALLY_DUE_DAYS")
    private int automaticallyDueDays;
    @DBAnno.DBField(dbField="FM_AUTOMATIC_TERMINATION", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean automaticTermination = true;
    @DBAnno.DBField(dbField="FM_FORMSCHEME")
    private String formScheme;
    @DBAnno.DBField(dbField="FM_UPDATE_USER")
    private String updateUser;
    private String surveyData;
    private String scriptEditor;
    private byte[] binaryData;

    @Override
    public String getUpdateUser() {
        return this.updateUser;
    }

    @Override
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String getFormScheme() {
        return this.formScheme;
    }

    @Override
    public void setFormScheme(String formscheme) {
        this.formScheme = formscheme;
    }

    @Override
    public String getFormCode() {
        return this.formCode;
    }

    @Override
    public String getSubTitle() {
        return this.subTitle;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getSerialNumber() {
        return this.serialNumber;
    }

    @Override
    public boolean getIsGather() {
        return this.isGather;
    }

    @Override
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    @Override
    public int getSecretLevel() {
        return this.secretLevel;
    }

    @Override
    public FormType getFormType() {
        return this.formType;
    }

    @Override
    public String getFormCondition() {
        return this.formCondition;
    }

    @Override
    public PeriodType getFormPeriodType() {
        return null;
    }

    @Override
    public String getFillingGuide() {
        return this.fillingGuide;
    }

    @Override
    public FormViewType getFormViewType() {
        return null;
    }

    @Override
    public byte[] getBinaryData() {
        return this.binaryData;
    }

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void setFormCode(String fromCode) {
        this.formCode = fromCode;
    }

    @Override
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public void setIsGather(boolean isGather) {
        this.isGather = isGather;
    }

    @Override
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    @Override
    public void setSecretLevel(int secretLevel) {
        this.secretLevel = secretLevel;
    }

    @Override
    public void setFormType(FormType formType) {
        this.formType = formType;
    }

    @Override
    public void setFormCondition(String fromCondition) {
        this.formCondition = fromCondition;
    }

    @Override
    public void setFormPeriodType(PeriodType periodType) {
    }

    @Override
    public void setFillingGuide(String fillingGuide) {
        this.fillingGuide = fillingGuide;
    }

    @Override
    public void setFormViewType(FormViewType viewType) {
    }

    @Override
    public void setBinaryData(byte[] binaryData) {
        this.binaryData = binaryData;
    }

    @Override
    public String getMasterEntitiesKey() {
        return this.masterEntitiesKey;
    }

    @Override
    public void setMasterEntitiesKey(String masterEntitiesKey) {
        this.masterEntitiesKey = masterEntitiesKey;
    }

    @Override
    public FormDefine clone() throws CloneNotSupportedException {
        return (FormDefine)super.clone();
    }

    @Override
    public String getReadOnlyCondition() {
        return this.readOnlyCondition;
    }

    @Override
    public void setReadOnlyCondition(String readOnlyCondition) {
        this.readOnlyCondition = readOnlyCondition;
    }

    @Override
    public IMeasureConfig getMeasureConfig() {
        return null;
    }

    @Override
    public void setSurveyData(String surveyData) {
        this.surveyData = surveyData;
    }

    @Override
    public String getSurveyData() {
        return this.surveyData;
    }

    @Override
    public void setScriptEditor(String scriptEditor) {
        this.scriptEditor = scriptEditor;
    }

    @Override
    public String getScriptEditor() {
        return this.scriptEditor;
    }

    @Override
    public boolean getQuoteType() {
        return this.quoteType;
    }

    @Override
    public void setQuoteType(boolean quoteType) {
        this.quoteType = quoteType;
    }

    @Override
    public boolean isAnalysisForm() {
        return this.analysisForm;
    }

    @Override
    public void setAnalysisForm(boolean analysisForm) {
        this.analysisForm = analysisForm;
    }

    @Override
    public boolean getLedgerForm() {
        return this.ledgerForm;
    }

    @Override
    public void setLedgerForm(boolean ledgerForm) {
        this.ledgerForm = ledgerForm;
    }

    @Override
    public Set<String> getExtensionPronNames() {
        if (this.formExtension == null) {
            return Collections.emptySet();
        }
        return this.formExtension.keySet();
    }

    @Override
    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        if (this.fillInAutomaticallyDue == null) {
            this.fillInAutomaticallyDue = new FillInAutomaticallyDue(this.automaticallyDueType, this.automaticallyDueDays, this.automaticTermination);
        }
        return this.fillInAutomaticallyDue;
    }

    @Override
    public void addExtensions(String key, Object value) {
        if (this.formExtension == null) {
            this.formExtension = new HashMap();
        }
        this.formExtension.put(key, value);
    }

    @Override
    public void setFillInAutomaticallyDue(FillInAutomaticallyDue fillInAutomaticallyDue) {
        if (fillInAutomaticallyDue != null) {
            this.fillInAutomaticallyDue = fillInAutomaticallyDue;
            this.automaticallyDueType = fillInAutomaticallyDue.getType();
            this.automaticallyDueDays = fillInAutomaticallyDue.getDays();
            this.automaticTermination = fillInAutomaticallyDue.getAutomaticTermination();
        }
    }

    @Override
    public Object getExtensionProp(String key) {
        if (this.formExtension == null) {
            return null;
        }
        return this.formExtension.get(key);
    }

    @Override
    public Map<String, Object> getFormExtension() {
        return this.formExtension;
    }
}

