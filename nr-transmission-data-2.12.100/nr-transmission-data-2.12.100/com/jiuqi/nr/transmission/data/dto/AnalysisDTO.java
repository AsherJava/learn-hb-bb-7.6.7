/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils$ZipSubFile
 */
package com.jiuqi.nr.transmission.data.dto;

import com.jiuqi.nr.datascheme.common.io.ZipUtils;
import com.jiuqi.nr.transmission.data.common.MappingType;
import com.jiuqi.nr.transmission.data.dto.SrcParamDTO;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import com.jiuqi.nr.transmission.data.vo.MappingSchemeVO;
import java.util.List;
import java.util.Map;

public class AnalysisDTO {
    private SyncSchemeParamDTO syncSchemeParamDTO;
    private SrcParamDTO srcParamDTO;
    private Map<String, ZipUtils.ZipSubFile> formDataZipMap;
    private boolean isNrd;
    private MappingType mappingType;
    private List<MappingSchemeVO> mappingSchemes;
    private String message;

    public AnalysisDTO() {
    }

    public AnalysisDTO(MappingType mappingType) {
        this.mappingType = mappingType;
    }

    public AnalysisDTO(SyncSchemeParamDTO syncSchemeParamDTO, SrcParamDTO srcParamDTO, Map<String, ZipUtils.ZipSubFile> formDataZipMap, boolean isNrd) {
        this.syncSchemeParamDTO = syncSchemeParamDTO;
        this.srcParamDTO = srcParamDTO;
        this.formDataZipMap = formDataZipMap;
        this.isNrd = isNrd;
    }

    public SyncSchemeParamDTO getSyncSchemeParamDTO() {
        return this.syncSchemeParamDTO;
    }

    public void setSyncSchemeParamDTO(SyncSchemeParamDTO syncSchemeParamDTO) {
        this.syncSchemeParamDTO = syncSchemeParamDTO;
    }

    public SrcParamDTO getSrcParamDTO() {
        return this.srcParamDTO;
    }

    public void setSrcParamDTO(SrcParamDTO srcParamDTO) {
        this.srcParamDTO = srcParamDTO;
    }

    public Map<String, ZipUtils.ZipSubFile> getFormDataZipMap() {
        return this.formDataZipMap;
    }

    public void setFormDataZipMap(Map<String, ZipUtils.ZipSubFile> formDataZipMap) {
        this.formDataZipMap = formDataZipMap;
    }

    public boolean isNrd() {
        return this.isNrd;
    }

    public boolean getIsNrd() {
        return this.isNrd;
    }

    public void setNrd(boolean nrd) {
        this.isNrd = nrd;
    }

    public MappingType getMappingType() {
        return this.mappingType;
    }

    public void setMappingType(MappingType mappingType) {
        this.mappingType = mappingType;
    }

    public List<MappingSchemeVO> getMappingSchemes() {
        return this.mappingSchemes;
    }

    public void setMappingSchemes(List<MappingSchemeVO> mappingSchemes) {
        this.mappingSchemes = mappingSchemes;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

