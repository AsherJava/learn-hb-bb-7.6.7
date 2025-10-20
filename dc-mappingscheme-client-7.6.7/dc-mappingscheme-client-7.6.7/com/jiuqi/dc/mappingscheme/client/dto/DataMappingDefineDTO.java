/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.common.cache.intf.CacheEntity
 */
package com.jiuqi.dc.mappingscheme.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.common.cache.intf.CacheEntity;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.vo.DataMappingVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataMappingDefineDTO
implements Serializable,
CacheEntity {
    private static final long serialVersionUID = -7092981505604937636L;
    private String id;
    private Long ver;
    private String modelType;
    private String dataSchemeCode;
    private String code;
    private String name;
    private String fieldMappingType;
    private String pluginType;
    private Date createTime;
    private List<FieldMappingDefineDTO> items;
    private String cacheKey;
    private String relTableName;
    private String orgMappingType;
    private DataMappingDTO dataMapping;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getModelType() {
        return this.modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldMappingType() {
        return this.fieldMappingType;
    }

    public void setFieldMappingType(String fieldMappingType) {
        this.fieldMappingType = fieldMappingType;
    }

    public String getPluginType() {
        return this.pluginType;
    }

    public void setPluginType(String pluginType) {
        this.pluginType = pluginType;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<FieldMappingDefineDTO> getItems() {
        return this.items;
    }

    public void setItems(List<FieldMappingDefineDTO> items) {
        this.items = items;
    }

    public String getCacheKey() {
        if (this.cacheKey == null) {
            this.cacheKey = DataMappingDefineDTO.buildCacheKey(this.getDataSchemeCode(), this.getCode());
        }
        return this.cacheKey;
    }

    public DataMappingDefineDTO() {
    }

    public DataMappingDefineDTO(DataSchemeDTO schemeDTO, String code) {
        this.dataSchemeCode = schemeDTO.getCode();
        this.code = code;
        this.pluginType = schemeDTO.getPluginType();
        this.cacheKey = DataMappingDefineDTO.buildCacheKey(schemeDTO.getCode(), code);
        this.items = new ArrayList<FieldMappingDefineDTO>();
        this.orgMappingType = schemeDTO.getDataMapping().getOrgMapping().getOrgMappingType();
        this.setDataMappingByVO(schemeDTO.getDataMapping());
    }

    public static final String buildCacheKey(String dataSchemeCode, String code) {
        if (dataSchemeCode == null) {
            dataSchemeCode = "";
        }
        if (code == null) {
            code = "";
        }
        return dataSchemeCode.concat("|").concat(code);
    }

    public String getRelTableName() {
        return this.relTableName;
    }

    public void setRelTableName(String relTableName) {
        this.relTableName = relTableName;
    }

    public DataMappingDTO getDataMapping() {
        return this.dataMapping;
    }

    public void setDataMapping(DataMappingDTO dataMapping) {
        this.dataMapping = dataMapping;
    }

    public void setDataMappingByVO(DataMappingVO dataMapping) {
        String dataMappingStr = JsonUtils.writeValueAsString((Object)dataMapping);
        this.dataMapping = (DataMappingDTO)JsonUtils.readValue((String)dataMappingStr, DataMappingDTO.class);
    }

    public String getOrgMappingType() {
        return this.orgMappingType;
    }

    public void setOrgMappingType(String orgMappingType) {
        this.orgMappingType = orgMappingType;
    }
}

