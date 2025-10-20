/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.dc.mappingscheme.client.vo;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.mappingscheme.client.vo.AdvancedMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.AssistMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.DimMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.OrgMappingVO;
import java.util.List;

public class DataMappingVO {
    private OrgMappingVO orgMapping;
    private DimMappingVO subjectMapping;
    private DimMappingVO currencyMapping;
    private DimMappingVO cfitemMapping;
    private List<AssistMappingVO> assistMapping;
    private List<AdvancedMappingVO> advancedMapping;

    public OrgMappingVO getOrgMapping() {
        return this.orgMapping;
    }

    public void setOrgMapping(OrgMappingVO orgMapping) {
        this.orgMapping = orgMapping;
    }

    public DimMappingVO getSubjectMapping() {
        return this.subjectMapping;
    }

    public void setSubjectMapping(DimMappingVO subjectMapping) {
        this.subjectMapping = subjectMapping;
    }

    public DimMappingVO getCurrencyMapping() {
        return this.currencyMapping;
    }

    public void setCurrencyMapping(DimMappingVO currencyMapping) {
        this.currencyMapping = currencyMapping;
    }

    public DimMappingVO getCfitemMapping() {
        return this.cfitemMapping;
    }

    public void setCfitemMapping(DimMappingVO cfitemMapping) {
        this.cfitemMapping = cfitemMapping;
    }

    public List<AssistMappingVO> getAssistMapping() {
        return CollectionUtils.isEmpty(this.assistMapping) ? CollectionUtils.newArrayList() : this.assistMapping;
    }

    public void setAssistMapping(List<AssistMappingVO> assistMapping) {
        this.assistMapping = assistMapping;
    }

    public List<AdvancedMappingVO> getAdvancedMapping() {
        return CollectionUtils.isEmpty(this.advancedMapping) ? CollectionUtils.newArrayList() : this.advancedMapping;
    }

    public void setAdvancedMapping(List<AdvancedMappingVO> advancedMapping) {
        this.advancedMapping = advancedMapping;
    }
}

