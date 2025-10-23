/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.param.transfer.definition.dto.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.param.transfer.definition.dao.AttachmentRuleDTO;
import com.jiuqi.nr.param.transfer.definition.dto.BaseDTO;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.AnalysisFormDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.ConditionalStyleDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.DataLinkDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.DataLinkMappingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.EntityViewDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.FormFoldingDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.FormInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.FormStyleDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.ReginInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formgroup.FormGroupLinkDTO;
import java.io.IOException;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormDTO
extends BaseDTO {
    private FormInfoDTO formInfo;
    private List<FormStyleDTO> formStyles;
    private List<ReginInfoDTO> reginInfo;
    private List<DataLinkDTO> dataLinks;
    private List<DataLinkMappingDTO> dataLinkMappings;
    private List<ConditionalStyleDTO> conditionalStyles;
    private List<FormFoldingDTO> formFoldings;
    private List<AttachmentRuleDTO> attachmentRules;
    private List<FormGroupLinkDTO> formGroupLinks;
    private List<AnalysisFormDTO> analysisFormDTO;
    private List<EntityViewDTO> entityViews;
    private DesParamLanguageDTO desParamLanguageDTO;

    public List<AnalysisFormDTO> getAnalysisFormDTO() {
        return this.analysisFormDTO;
    }

    public void setAnalysisFormDTO(List<AnalysisFormDTO> analysisFormDTO) {
        this.analysisFormDTO = analysisFormDTO;
    }

    public FormInfoDTO getFormInfo() {
        return this.formInfo;
    }

    public void setFormInfo(FormInfoDTO formInfo) {
        this.formInfo = formInfo;
    }

    public List<FormStyleDTO> getFormStyles() {
        return this.formStyles;
    }

    public void setFormStyles(List<FormStyleDTO> formStyles) {
        this.formStyles = formStyles;
    }

    public List<ReginInfoDTO> getReginInfo() {
        return this.reginInfo;
    }

    public void setReginInfo(List<ReginInfoDTO> reginInfo) {
        this.reginInfo = reginInfo;
    }

    public List<DataLinkDTO> getDataLinks() {
        return this.dataLinks;
    }

    public void setDataLinks(List<DataLinkDTO> dataLinks) {
        this.dataLinks = dataLinks;
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

    public List<EntityViewDTO> getEntityViews() {
        return this.entityViews;
    }

    public void setEntityViews(List<EntityViewDTO> entityViews) {
        this.entityViews = entityViews;
    }

    public DesParamLanguageDTO getDesParamLanguageDTO() {
        return this.desParamLanguageDTO;
    }

    public void setDesParamLanguageDTO(DesParamLanguageDTO desParamLanguageDTO) {
        this.desParamLanguageDTO = desParamLanguageDTO;
    }

    public static FormDTO valueOf(byte[] bytes, ObjectMapper objectMapper) throws IOException {
        return (FormDTO)objectMapper.readValue(bytes, FormDTO.class);
    }
}

