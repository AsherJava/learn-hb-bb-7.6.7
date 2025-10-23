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
 *  com.jiuqi.nr.param.transfer.definition.dao.AttachmentRuleDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.form.AnalysisFormDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.form.ConditionalStyleDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.form.DataLinkMappingDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.form.FormFoldingDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.form.FormStyleDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.formgroup.FormGroupLinkDTO
 */
package com.jiuqi.nr.nrdx.param.task.dto.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.param.transfer.definition.dao.AttachmentRuleDTO;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.AnalysisFormDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.ConditionalStyleDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.DataLinkMappingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.FormFoldingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.FormStyleDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formgroup.FormGroupLinkDTO;
import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NrdxFormDTO {
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
    private List<FormStyleDTO> formStyles;
    private List<DataLinkMappingDTO> dataLinkMappings;
    private List<ConditionalStyleDTO> conditionalStyles;
    private List<FormFoldingDTO> formFoldings;
    private List<AttachmentRuleDTO> attachmentRules;
    private List<FormGroupLinkDTO> formGroupLinks;
    private List<AnalysisFormDTO> analysisFormDTO;
    private DesParamLanguageDTO desParamLanguageDTO;

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

    public static NrdxFormDTO valueOf(FormDefine formDefine) {
        NrdxFormDTO nrdxFormDTO = new NrdxFormDTO();
        nrdxFormDTO.setDescription(formDefine.getDescription());
        nrdxFormDTO.setFormCode(formDefine.getFormCode());
        nrdxFormDTO.setFormCondition(formDefine.getFormCondition());
        nrdxFormDTO.setFormType(formDefine.getFormType());
        nrdxFormDTO.setGather(formDefine.getIsGather());
        nrdxFormDTO.setKey(formDefine.getKey());
        nrdxFormDTO.setMasterEntitiesKey(formDefine.getMasterEntitiesKey());
        nrdxFormDTO.setMeasureUnit(formDefine.getMeasureUnit());
        nrdxFormDTO.setOrder(formDefine.getOrder());
        nrdxFormDTO.setOwnerLevelAndId(formDefine.getOwnerLevelAndId());
        nrdxFormDTO.setReadOnlyCondition(formDefine.getReadOnlyCondition());
        nrdxFormDTO.setSecretLevel(formDefine.getSecretLevel());
        nrdxFormDTO.setSerialNumber(formDefine.getSerialNumber());
        nrdxFormDTO.setSubTitle(formDefine.getSubTitle());
        nrdxFormDTO.setTitle(formDefine.getTitle());
        nrdxFormDTO.setUpdateTime(formDefine.getUpdateTime());
        nrdxFormDTO.setVersion(formDefine.getVersion());
        nrdxFormDTO.setAnalysisForm(formDefine.isAnalysisForm());
        nrdxFormDTO.setQuoteType(formDefine.getQuoteType());
        nrdxFormDTO.setFillInAutomaticallyDue(formDefine.getFillInAutomaticallyDue());
        nrdxFormDTO.setFormScheme(formDefine.getFormScheme());
        Map formExtension = formDefine.getFormExtension();
        nrdxFormDTO.setFormExtension(formExtension);
        nrdxFormDTO.setUpdateUser(formDefine.getUpdateUser());
        return nrdxFormDTO;
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

    public List<FormStyleDTO> getFormStyles() {
        return this.formStyles;
    }

    public void setFormStyles(List<FormStyleDTO> formStyles) {
        this.formStyles = formStyles;
    }

    public List<DataLinkMappingDTO> getDataLinkMappings() {
        return this.dataLinkMappings;
    }

    public void setDataLinkMappings(List<DataLinkMappingDTO> dataLinkMappings) {
        this.dataLinkMappings = dataLinkMappings;
    }

    public List<ConditionalStyleDTO> getConditionalStyles() {
        return this.conditionalStyles;
    }

    public void setConditionalStyles(List<ConditionalStyleDTO> conditionalStyles) {
        this.conditionalStyles = conditionalStyles;
    }

    public List<FormFoldingDTO> getFormFoldings() {
        return this.formFoldings;
    }

    public void setFormFoldings(List<FormFoldingDTO> formFoldings) {
        this.formFoldings = formFoldings;
    }

    public List<AttachmentRuleDTO> getAttachmentRules() {
        return this.attachmentRules;
    }

    public void setAttachmentRules(List<AttachmentRuleDTO> attachmentRules) {
        this.attachmentRules = attachmentRules;
    }

    public List<FormGroupLinkDTO> getFormGroupLinks() {
        return this.formGroupLinks;
    }

    public void setFormGroupLinks(List<FormGroupLinkDTO> formGroupLinks) {
        this.formGroupLinks = formGroupLinks;
    }

    public List<AnalysisFormDTO> getAnalysisFormDTO() {
        return this.analysisFormDTO;
    }

    public void setAnalysisFormDTO(List<AnalysisFormDTO> analysisFormDTO) {
        this.analysisFormDTO = analysisFormDTO;
    }

    public DesParamLanguageDTO getDesParamLanguageDTO() {
        return this.desParamLanguageDTO;
    }

    public void setDesParamLanguageDTO(DesParamLanguageDTO desParamLanguageDTO) {
        this.desParamLanguageDTO = desParamLanguageDTO;
    }
}

