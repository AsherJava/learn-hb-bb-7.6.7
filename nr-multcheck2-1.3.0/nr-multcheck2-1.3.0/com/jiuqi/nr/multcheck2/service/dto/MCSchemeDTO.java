/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.service.dto;

import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.service.dto.DimSetDTO;
import com.jiuqi.nr.multcheck2.web.vo.MCRunVO;
import com.jiuqi.nr.multcheck2.web.vo.ReportDimVO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;

public class MCSchemeDTO
extends MultcheckScheme {
    private List<String> orgList;
    private List<String> asyncIds;
    private ReportDimVO reportDimVO;
    private MCRunVO vo;
    private Map<String, DimSetDTO> OrgDims;
    private Map<String, Map<String, DimSetDTO>> itemsOrgDims;

    public MCSchemeDTO() {
    }

    public MCSchemeDTO(MultcheckScheme scheme) {
        BeanUtils.copyProperties(scheme, this);
    }

    public MCSchemeDTO(MultcheckScheme scheme, List<String> orgList) {
        BeanUtils.copyProperties(scheme, this);
        this.orgList = orgList;
    }

    public List<String> getOrgList() {
        return this.orgList;
    }

    public void setOrgList(List<String> orgList) {
        this.orgList = orgList;
    }

    public ReportDimVO getReportDimVO() {
        return this.reportDimVO;
    }

    public void setReportDimVO(ReportDimVO reportDimVO) {
        this.reportDimVO = reportDimVO;
    }

    public List<String> getAsyncIds() {
        return this.asyncIds;
    }

    public void setAsyncIds(List<String> asyncIds) {
        this.asyncIds = asyncIds;
    }

    public MCRunVO getVo() {
        return this.vo;
    }

    public void setVo(MCRunVO vo) {
        this.vo = vo;
    }

    public Map<String, DimSetDTO> getOrgDims() {
        return this.OrgDims;
    }

    public void setOrgDims(Map<String, DimSetDTO> orgDims) {
        this.OrgDims = orgDims;
    }

    public Map<String, Map<String, DimSetDTO>> getItemsOrgDims() {
        return this.itemsOrgDims;
    }

    public void setItemsOrgDims(Map<String, Map<String, DimSetDTO>> itemsOrgDims) {
        this.itemsOrgDims = itemsOrgDims;
    }
}

