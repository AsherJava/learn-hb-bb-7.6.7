/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.dto;

import com.jiuqi.dc.mappingscheme.client.dto.AdvancedMappingDTO;
import com.jiuqi.dc.mappingscheme.client.dto.AssistMappingDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DimMappingDTO;
import java.io.Serializable;
import java.util.List;

public class DataMappingDTO
implements Serializable {
    private static final long serialVersionUID = 1749106824878642390L;
    private DimMappingDTO orgMapping;
    private DimMappingDTO subjectMapping;
    private DimMappingDTO currencyMapping;
    private DimMappingDTO cfitemMapping;
    private List<AssistMappingDTO> assistMapping;
    private List<AdvancedMappingDTO> advancedMapping;

    public DimMappingDTO getOrgMapping() {
        return this.orgMapping;
    }

    public void setOrgMapping(DimMappingDTO orgMapping) {
        this.orgMapping = orgMapping;
    }

    public DimMappingDTO getSubjectMapping() {
        return this.subjectMapping;
    }

    public void setSubjectMapping(DimMappingDTO subjectMapping) {
        this.subjectMapping = subjectMapping;
    }

    public DimMappingDTO getCurrencyMapping() {
        return this.currencyMapping;
    }

    public void setCurrencyMapping(DimMappingDTO currencyMapping) {
        this.currencyMapping = currencyMapping;
    }

    public DimMappingDTO getCfitemMapping() {
        return this.cfitemMapping;
    }

    public void setCfitemMapping(DimMappingDTO cfitemMapping) {
        this.cfitemMapping = cfitemMapping;
    }

    public List<AssistMappingDTO> getAssistMapping() {
        return this.assistMapping;
    }

    public void setAssistMapping(List<AssistMappingDTO> assistMapping) {
        this.assistMapping = assistMapping;
    }

    public List<AdvancedMappingDTO> getAdvancedMapping() {
        return this.advancedMapping;
    }

    public void setAdvancedMapping(List<AdvancedMappingDTO> advancedMapping) {
        this.advancedMapping = advancedMapping;
    }
}

