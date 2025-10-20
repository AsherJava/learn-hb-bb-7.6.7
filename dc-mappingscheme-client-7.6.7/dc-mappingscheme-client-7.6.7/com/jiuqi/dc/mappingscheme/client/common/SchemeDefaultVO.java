/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 */
package com.jiuqi.dc.mappingscheme.client.common;

import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.vo.BizColumnVO;
import com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.OrgMappingTypeVO;
import java.util.List;
import java.util.Map;

public class SchemeDefaultVO {
    private List<BizColumnVO> columnData;
    private String customConfig;
    private List<FieldDTO> assistFieldData;
    private Map<String, List<DimMappingVO>> bizFieldData;
    private List<SelectOptionVO> IsolationStrategyList;
    private List<OrgMappingTypeVO> orgMappingTypeList;
    private List<SelectOptionVO> fieldMappingTypeList;
    private Boolean assistFieldFlag;

    public List<BizColumnVO> getColumnData() {
        return this.columnData;
    }

    public void setColumnData(List<BizColumnVO> columnData) {
        this.columnData = columnData;
    }

    public String getCustomConfig() {
        return this.customConfig;
    }

    public void setCustomConfig(String customConfig) {
        this.customConfig = customConfig;
    }

    public List<FieldDTO> getAssistFieldData() {
        return this.assistFieldData;
    }

    public void setAssistFieldData(List<FieldDTO> assistFieldData) {
        this.assistFieldData = assistFieldData;
    }

    public Map<String, List<DimMappingVO>> getBizFieldData() {
        return this.bizFieldData;
    }

    public void setBizFieldData(Map<String, List<DimMappingVO>> bizFieldData) {
        this.bizFieldData = bizFieldData;
    }

    public List<SelectOptionVO> getIsolationStrategyList() {
        return this.IsolationStrategyList;
    }

    public void setIsolationStrategyList(List<SelectOptionVO> isolationStrategyList) {
        this.IsolationStrategyList = isolationStrategyList;
    }

    public List<OrgMappingTypeVO> getOrgMappingTypeList() {
        return this.orgMappingTypeList;
    }

    public void setOrgMappingTypeList(List<OrgMappingTypeVO> orgMappingTypeList) {
        this.orgMappingTypeList = orgMappingTypeList;
    }

    public List<SelectOptionVO> getFieldMappingTypeList() {
        return this.fieldMappingTypeList;
    }

    public void setFieldMappingTypeList(List<SelectOptionVO> fieldMappingTypeList) {
        this.fieldMappingTypeList = fieldMappingTypeList;
    }

    public Boolean getAssistFieldFlag() {
        return this.assistFieldFlag;
    }

    public void setAssistFieldFlag(Boolean assistFieldFlag) {
        this.assistFieldFlag = assistFieldFlag;
    }
}

