/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.util.SerializeUtil
 */
package com.jiuqi.nr.print.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.util.SerializeUtil;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import org.springframework.util.StringUtils;

public class DesignerInfoDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String designerId;
    private DesignerVersion designerVersion;
    private String printTemplateId;
    private String printSchemeId;
    private String formSchemeId;
    private String formId;
    private String originTemplate;
    private boolean resetTemplate;
    private boolean customGrid;
    private Date customGuidDate;
    private boolean linkedChange;
    private Collection<String> linkedForms;
    private String linkedCommonCode;

    public String getDesignerId() {
        return this.designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }

    public String getPrintSchemeId() {
        return this.printSchemeId;
    }

    public void setPrintSchemeId(String printSchemeId) {
        this.printSchemeId = printSchemeId;
    }

    public void setPrintSchemeKey(String printSchemeId) {
        this.printSchemeId = printSchemeId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public void setFormKey(String formId) {
        this.formId = formId;
    }

    public String getOriginTemplate() {
        return this.originTemplate;
    }

    public void setOriginTemplate(String originTemplate) {
        this.originTemplate = originTemplate;
    }

    public boolean isResetTemplate() {
        return this.resetTemplate;
    }

    public void setResetTemplate(boolean resetTemplate) {
        this.resetTemplate = resetTemplate;
        if (this.resetTemplate) {
            this.customGrid = false;
            this.customGuidDate = null;
        }
    }

    public boolean isCustomGrid() {
        return this.customGrid;
    }

    public void setCustomGrid(boolean customGrid) {
        this.customGrid = customGrid;
    }

    public Date getCustomGuidDate() {
        return this.customGuidDate;
    }

    public void setCustomGuidDate(Date customGuidDate) {
        this.customGuidDate = customGuidDate;
    }

    public String getPrintTemplateId() {
        return this.printTemplateId;
    }

    public void setPrintTemplateId(String printTemplateId) {
        this.printTemplateId = printTemplateId;
    }

    public DesignerVersion getDesignerVersion() {
        return this.designerVersion;
    }

    public void setDesignerVersion(DesignerVersion designerVersion) {
        this.designerVersion = designerVersion;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public boolean isLinkedChange() {
        return this.linkedChange;
    }

    public void setLinkedChange(boolean linkedChange) {
        this.linkedChange = linkedChange;
    }

    public Collection<String> getLinkedForms() {
        return this.linkedForms;
    }

    public void setLinkedForms(Collection<String> linkedForms) {
        this.linkedForms = linkedForms;
    }

    public String getLinkedCommonCode() {
        return this.linkedCommonCode;
    }

    public void setLinkedCommonCode(String linkedCommonCode) {
        this.linkedCommonCode = linkedCommonCode;
    }

    public boolean isCoverTemplate() {
        return !StringUtils.hasText(this.formId) || "coverTem".equals(this.formId);
    }

    public boolean isCommonTemplate() {
        return "commonTem".equals(this.formId);
    }

    public boolean isFormTemplate() {
        return !this.isCommonTemplate() && !this.isCommonTemplate();
    }

    public boolean isChanged(ITemplateDocument document) {
        if (this.linkedChange) {
            return false;
        }
        if (null == document) {
            return true;
        }
        String usedDocument = SerializeUtil.serialize((ITemplateObject)document);
        return this.getOriginTemplate().equals(usedDocument);
    }

    public static DesignerInfoDTO valueOf(String content) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return (DesignerInfoDTO)mapper.readValue(content, DesignerInfoDTO.class);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return "DesignerInfoDTO{designerId='" + this.designerId + '\'' + ", designerVersion=" + (Object)((Object)this.designerVersion) + ", printTemplateId='" + this.printTemplateId + '\'' + ", printSchemeId='" + this.printSchemeId + '\'' + ", formSchemeId='" + this.formSchemeId + '\'' + ", formId='" + this.formId + '\'' + '}';
    }

    public static enum DesignerVersion {
        V1,
        V2;

    }
}

