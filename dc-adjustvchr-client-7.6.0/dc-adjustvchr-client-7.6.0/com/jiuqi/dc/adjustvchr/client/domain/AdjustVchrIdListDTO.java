/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.intf.impl.DcTenantDTO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.dc.adjustvchr.client.domain;

import com.jiuqi.dc.base.common.intf.impl.DcTenantDTO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;

public class AdjustVchrIdListDTO
extends DcTenantDTO {
    private static final long serialVersionUID = -1708670774702668057L;
    private List<String> vchrIds;
    private List<DimensionVO> assistDims;
    private List<String> groupList;
    private List<String> convertAmountCols;

    public List<String> getVchrIds() {
        return this.vchrIds;
    }

    public void setVchrIds(List<String> vchrIds) {
        this.vchrIds = vchrIds;
    }

    public List<DimensionVO> getAssistDims() {
        return this.assistDims;
    }

    public void setAssistDims(List<DimensionVO> assistDims) {
        this.assistDims = assistDims;
    }

    public List<String> getGroupList() {
        return this.groupList;
    }

    public void setGroupList(List<String> groupList) {
        this.groupList = groupList;
    }

    public List<String> getConvertAmountCols() {
        return this.convertAmountCols;
    }

    public void setConvertAmountCols(List<String> convertAmountCols) {
        this.convertAmountCols = convertAmountCols;
    }
}

