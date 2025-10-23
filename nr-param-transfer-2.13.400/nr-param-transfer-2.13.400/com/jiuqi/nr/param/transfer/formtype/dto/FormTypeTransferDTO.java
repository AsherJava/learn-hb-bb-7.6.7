/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.nr.formtype.facade.FormTypeDataDefine
 *  com.jiuqi.nr.formtype.facade.FormTypeDefine
 */
package com.jiuqi.nr.param.transfer.formtype.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import com.jiuqi.nr.param.transfer.formtype.dto.FormTypeDTO;
import com.jiuqi.nr.param.transfer.formtype.dto.FormTypeDataDTO;
import com.jiuqi.nr.param.transfer.formtype.dto.FormTypeGroupDTO;
import java.util.ArrayList;
import java.util.List;

public class FormTypeTransferDTO {
    private List<FormTypeGroupDTO> formTypeGroupDTOList;
    private FormTypeDTO formTypeDTO;
    private List<FormTypeDataDTO> formTypeDataDTO;

    public List<FormTypeGroupDTO> getFormTypeGroupDTOList() {
        if (this.formTypeGroupDTOList == null) {
            this.formTypeGroupDTOList = new ArrayList<FormTypeGroupDTO>();
        }
        return this.formTypeGroupDTOList;
    }

    public void setFormTypeGroupDTOList(List<FormTypeGroupDTO> formTypeGroupDTOList) {
        this.formTypeGroupDTOList = formTypeGroupDTOList;
    }

    public FormTypeDTO getFormTypeDTO() {
        return this.formTypeDTO;
    }

    public void setFormTypeDTO(FormTypeDTO formTypeDTO) {
        this.formTypeDTO = formTypeDTO;
    }

    @JsonIgnore
    public void initFormTypeDTO(FormTypeDefine formTypeDefine) {
        if (formTypeDefine != null) {
            this.formTypeDTO = new FormTypeDTO();
            this.formTypeDTO.setId(formTypeDefine.getId());
            this.formTypeDTO.setCode(formTypeDefine.getCode());
            this.formTypeDTO.setDesc(formTypeDefine.getDesc());
            this.formTypeDTO.setOrder(formTypeDefine.getOrder());
            this.formTypeDTO.setGroupId(formTypeDefine.getGroupId());
            this.formTypeDTO.setTitle(formTypeDefine.getTitle());
            this.formTypeDTO.setUpdateTime(formTypeDefine.getUpdateTime());
        }
    }

    public List<FormTypeDataDTO> getFormTypeDataDTO() {
        return this.formTypeDataDTO;
    }

    public void setFormTypeDataDTO(List<FormTypeDataDTO> formTypeDataDTO) {
        this.formTypeDataDTO = formTypeDataDTO;
    }

    @JsonIgnore
    public void initFormTypeDataDTO(List<FormTypeDataDefine> formTypeDataDefine) {
        this.formTypeDataDTO = new ArrayList<FormTypeDataDTO>();
        for (FormTypeDataDefine typeDataDefine : formTypeDataDefine) {
            FormTypeDataDTO dataDTO = new FormTypeDataDTO();
            dataDTO.setCode(typeDataDefine.getCode());
            dataDTO.setId(typeDataDefine.getId());
            dataDTO.setFormTypeCode(typeDataDefine.getFormTypeCode());
            dataDTO.setIcon(typeDataDefine.getIcon());
            dataDTO.setName(typeDataDefine.getName());
            dataDTO.setNameEnUS(typeDataDefine.getNameEnUS());
            dataDTO.setOrdinal(typeDataDefine.getOrdinal());
            dataDTO.setShortname(typeDataDefine.getShortname());
            dataDTO.setUnitNatrue(typeDataDefine.getUnitNatrue());
            dataDTO.setUpdateTime(typeDataDefine.getUpdateTime());
            this.formTypeDataDTO.add(dataDTO);
        }
    }
}

