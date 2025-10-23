/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package com.jiuqi.nr.param.transfer.definition.dto.formgroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.param.transfer.definition.dto.BaseDTO;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formgroup.FormGroupInfoDTO;
import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormGroupDTO
extends BaseDTO {
    private FormGroupInfoDTO formGroupInfo;
    private DesParamLanguageDTO desParamLanguageDTO;

    public FormGroupInfoDTO getFormGroupInfo() {
        return this.formGroupInfo;
    }

    public void setFormGroupInfo(FormGroupInfoDTO formGroupInfo) {
        this.formGroupInfo = formGroupInfo;
    }

    public DesParamLanguageDTO getDesParamLanguageDTO() {
        return this.desParamLanguageDTO;
    }

    public void setDesParamLanguageDTO(DesParamLanguageDTO desParamLanguageDTO) {
        this.desParamLanguageDTO = desParamLanguageDTO;
    }

    public static FormGroupDTO valueOf(byte[] bytes, ObjectMapper objectMapper) throws IOException {
        return (FormGroupDTO)objectMapper.readValue(bytes, FormGroupDTO.class);
    }
}

