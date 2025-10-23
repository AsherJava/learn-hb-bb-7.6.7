/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.var.Variable
 */
package com.jiuqi.nr.transmission.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.transmission.data.common.TransmissionExportType;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeParamDO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.BeanUtils;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SyncSchemeParamDTO
extends SyncSchemeParamDO {
    private String schemeName;
    @JsonIgnore
    private DimensionValueSet dimensionValueSet;
    @JsonIgnore
    private SyncSchemeParamDTO syncSchemeParamDTOAfterMapping;
    private String formSchemeKey;
    private String extendParam;
    private boolean uploadDesc;
    private String exportFilterMessage;
    private String fmdmForm;
    private boolean hasError;
    private String superEntity;
    private List<Variable> variables;
    TransmissionExportType transmissionExportType;
    List<String> srcAllDimCodes = new ArrayList<String>();
    Set<String> exportData = new HashSet<String>();

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public SyncSchemeParamDTO getSyncSchemeParamDTOAfterMapping() {
        return this.syncSchemeParamDTOAfterMapping;
    }

    public void setSyncSchemeParamDTOAfterMapping(SyncSchemeParamDTO syncSchemeParamDTOAfterMapping) {
        this.syncSchemeParamDTOAfterMapping = syncSchemeParamDTOAfterMapping;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getExtendParam() {
        return this.extendParam;
    }

    public void setExtendParam(String extendParam) {
        this.extendParam = extendParam;
    }

    public boolean isUploadDesc() {
        return this.uploadDesc;
    }

    public void setUploadDesc(boolean uploadDesc) {
        this.uploadDesc = uploadDesc;
    }

    public String getExportFilterMessage() {
        return this.exportFilterMessage;
    }

    public void setExportFilterMessage(String exportFilterMessage) {
        this.exportFilterMessage = exportFilterMessage;
    }

    public String getFmdmForm() {
        return this.fmdmForm;
    }

    public void setFmdmForm(String fmdmForm) {
        this.fmdmForm = fmdmForm;
    }

    public boolean isHasError() {
        return this.hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getSuperEntity() {
        return this.superEntity;
    }

    public void setSuperEntity(String superEntity) {
        this.superEntity = superEntity;
    }

    public List<Variable> getVariables() {
        return this.variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public TransmissionExportType getTransmissionExportType() {
        return this.transmissionExportType;
    }

    public void setTransmissionExportType(TransmissionExportType transmissionExportType) {
        this.transmissionExportType = transmissionExportType;
    }

    public List<String> getSrcAllDimCodes() {
        return this.srcAllDimCodes;
    }

    public void setSrcAllDimCodes(List<String> srcAllDimCodes) {
        this.srcAllDimCodes = srcAllDimCodes;
    }

    public Set<String> getExportData() {
        return this.exportData;
    }

    public void setExportData(Set<String> exportData) {
        this.exportData = exportData;
    }

    public static SyncSchemeParamDTO getInstance(SyncSchemeParamDO syncSchemeDO) {
        SyncSchemeParamDTO syncSchemeParamDTO = new SyncSchemeParamDTO();
        if (syncSchemeDO != null) {
            syncSchemeParamDTO.setKey(syncSchemeDO.getKey());
            syncSchemeParamDTO.setSchemeKey(syncSchemeDO.getSchemeKey());
            syncSchemeParamDTO.setTask(syncSchemeDO.getTask());
            syncSchemeParamDTO.setPeriod(syncSchemeDO.getPeriod());
            syncSchemeParamDTO.setPeriodValue(syncSchemeDO.getPeriodValue());
            syncSchemeParamDTO.setEntityType(syncSchemeDO.getEntityType());
            syncSchemeParamDTO.setEntity(syncSchemeDO.getEntity());
            syncSchemeParamDTO.setFormType(syncSchemeDO.getFormType());
            syncSchemeParamDTO.setForm(syncSchemeDO.getForm());
            syncSchemeParamDTO.setIsUpload(syncSchemeDO.getIsUpload());
            syncSchemeParamDTO.setAllowForceUpload(syncSchemeDO.getAllowForceUpload());
            syncSchemeParamDTO.setDescription(syncSchemeDO.getDescription());
            syncSchemeParamDTO.setDimKeys(syncSchemeDO.getDimKeys());
            syncSchemeParamDTO.setDimValues(syncSchemeDO.getDimValues());
            syncSchemeParamDTO.setAdjustPeriod(syncSchemeDO.getAdjustPeriod());
            syncSchemeParamDTO.setMappingSchemeKey(syncSchemeDO.getMappingSchemeKey());
        }
        return syncSchemeParamDTO;
    }

    public static List<SyncSchemeParamDTO> toListInstance(List<SyncSchemeParamDO> syncHistoryDOS) {
        ArrayList<SyncSchemeParamDTO> dtos = new ArrayList<SyncSchemeParamDTO>(syncHistoryDOS.size());
        for (SyncSchemeParamDO syncSchemeParamDO : syncHistoryDOS) {
            SyncSchemeParamDTO syncHistoryDTO = new SyncSchemeParamDTO();
            BeanUtils.copyProperties(syncSchemeParamDO, syncHistoryDTO);
            dtos.add(syncHistoryDTO);
        }
        return dtos;
    }
}

