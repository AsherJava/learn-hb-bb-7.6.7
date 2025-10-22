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
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.IMeasureConfig;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormGroupLink;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.context.annotation.Primary;

@DBAnno.DBTable(dbTable="NR_PARAM_FORM")
@DBAnno.DBLink(linkWith=RunTimeFormGroupLink.class, linkField="formKey", field="key")
@Primary
public class RunTimeFormDefineImpl
implements FormDefine {
    private static final long serialVersionUID = 7075419709217914121L;
    public static final String TABLE_NAME = "NR_PARAM_FORM";
    public static final String FIELD_NAME_KEY = "FM_KEY";
    public static final String FIELD_NAME_FORMSCHEME_KEY = "FM_FORMSCHEME";
    @DBAnno.DBField(dbField="fm_code")
    private String formCode;
    @DBAnno.DBField(dbField="fm_sub_title")
    private String subTitle;
    @DBAnno.DBField(dbField="fm_desc")
    private String description;
    @DBAnno.DBField(dbField="fm_serial_Number")
    private String serialNumber;
    @DBAnno.DBField(dbField="fm_gather_type", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean isGather;
    @DBAnno.DBField(dbField="fm_unit")
    private String measureUnit;
    @DBAnno.DBField(dbField="fm_secret_level")
    private int secretLevel;
    @DBAnno.DBField(dbField="fm_type", tranWith="transFormType", dbType=Integer.class, appType=FormType.class)
    private FormType formType;
    @DBAnno.DBField(dbField="fm_condition")
    private String formCondition;
    @DBAnno.DBField(dbField="FM_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="fm_title")
    private String title;
    @DBAnno.DBField(dbField="fm_order", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="fm_version")
    private String version;
    @DBAnno.DBField(dbField="fm_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="fm_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="fm_master_key")
    private String masterEntitiesKey;
    @DBAnno.DBField(dbField="fm_readonly_condition")
    private String readOnlyCondition;
    @DBAnno.DBField(dbField="fm_quote_type", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean quoteType;
    @DBAnno.DBField(dbField="fm_analysis_form", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean analysisForm;
    private byte[] binaryData;
    @DBAnno.DBField(dbField="fm_formExtensions", tranWith="transExtensions", dbType=Clob.class, appType=HashMap.class)
    private HashMap<String, Object> formExtension;
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
    private String surveyData;
    private String scriptEditor;
    private String fillingGuide;
    @DBAnno.DBField(dbField="FM_UPDATE_USER")
    private String updateUser;

    @Override
    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setFormCode(String fromCode) {
        this.formCode = fromCode;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setIsGather(boolean isGather) {
        this.isGather = isGather;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public void setSecretLevel(int secretLevel) {
        this.secretLevel = secretLevel;
    }

    public void setFormType(FormType formType) {
        this.formType = formType;
    }

    public void setFormCondition(String fromCondition) {
        this.formCondition = fromCondition;
    }

    public void setFormPeriodType(PeriodType periodType) {
    }

    public void setFillingGuide(String fillingGuide) {
        this.fillingGuide = fillingGuide;
    }

    public void setFormViewType(FormViewType viewType) {
    }

    public void setBinaryData(byte[] binaryData) {
        this.binaryData = binaryData;
    }

    public void setScriptEditor(String scriptEditor) {
        this.scriptEditor = scriptEditor;
    }

    @Override
    public String getMasterEntitiesKey() {
        return this.masterEntitiesKey;
    }

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

    public void setReadOnlyCondition(String readOnlyCondition) {
        this.readOnlyCondition = readOnlyCondition;
    }

    @Override
    public IMeasureConfig getMeasureConfig() {
        return null;
    }

    @Override
    public String getSurveyData() {
        return this.surveyData;
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
    public boolean isAnalysisForm() {
        return this.analysisForm;
    }

    public void setAnalysisForm(boolean analysisForm) {
        this.analysisForm = analysisForm;
    }

    @Override
    public boolean getLedgerForm() {
        return this.ledgerForm;
    }

    public void setLedgerForm(boolean ledgerForm) {
        this.ledgerForm = ledgerForm;
    }

    @Override
    public Object getExtensionProp(String key) {
        if (this.formExtension == null) {
            return null;
        }
        return this.formExtension.get(key);
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
    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    @Override
    public Map<String, Object> getFormExtension() {
        return this.formExtension;
    }
}

