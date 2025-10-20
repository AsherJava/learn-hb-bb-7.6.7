/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.dto;

import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;

public class OrgMappingTypeDTO {
    private String code;
    private String name;
    private String desc;
    private BaseDataMappingDefineDTO baseDataMappingDefine;

    public OrgMappingTypeDTO() {
    }

    public OrgMappingTypeDTO(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
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

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BaseDataMappingDefineDTO getBaseDataMappingDefine() {
        return this.baseDataMappingDefine;
    }

    public void setBaseDataMappingDefine(BaseDataMappingDefineDTO baseDataMappingDefine) {
        this.baseDataMappingDefine = baseDataMappingDefine;
    }
}

