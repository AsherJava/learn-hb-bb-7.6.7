/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.period.common.utils.StringUtils
 */
package com.jiuqi.nr.task.form.controller.dto;

import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.task.form.field.dto.DataFieldDTO;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;

public class AutoMappingDTO {
    private String dtCode;
    private String dfCode;
    private String fieldKey;
    private int col;
    private int row;
    private DataTableType dataTableType;
    private boolean isFmdm = false;
    private DataTableDTO dataTableDTO;
    private DataFieldDTO dataFieldDTO;
    private IEntityDefine iEntityDefine;
    private IEntityAttribute iEntityAttribute;

    public IEntityDefine getiEntityDefine() {
        return this.iEntityDefine;
    }

    public void setiEntityDefine(IEntityDefine iEntityDefine) {
        this.iEntityDefine = iEntityDefine;
    }

    public IEntityAttribute getiEntityAttribute() {
        return this.iEntityAttribute;
    }

    public void setiEntityAttribute(IEntityAttribute iEntityAttribute) {
        this.iEntityAttribute = iEntityAttribute;
    }

    public DataTableDTO getDataTableDTO() {
        return this.dataTableDTO;
    }

    public void setDataTableDTO(DataTableDTO dataTableDTO) {
        this.dataTableDTO = dataTableDTO;
    }

    public DataFieldDTO getDataFieldDTO() {
        return this.dataFieldDTO;
    }

    public void setDataFieldDTO(DataFieldDTO dataFieldDTO) {
        this.dataFieldDTO = dataFieldDTO;
    }

    public boolean isFmdm() {
        return this.isFmdm;
    }

    public void setFmdm(boolean fmdm) {
        this.isFmdm = fmdm;
    }

    public DataTableType getDataTableType() {
        return this.dataTableType;
    }

    public void setDataTableType(DataTableType dataTableType) {
        this.dataTableType = dataTableType;
    }

    public String getDtCode() {
        if (StringUtils.isNotEmpty((String)this.dtCode)) {
            return this.dtCode.toUpperCase();
        }
        return this.dtCode;
    }

    public void setDtCode(String dtCode) {
        this.dtCode = dtCode;
    }

    public String getDfCode() {
        if (StringUtils.isNotEmpty((String)this.dfCode)) {
            return this.dfCode.toUpperCase();
        }
        return this.dfCode;
    }

    public void setDfCode(String dfCode) {
        this.dfCode = dfCode;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}

