/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.intf.impl.DcTenantDTO
 */
package com.jiuqi.dc.adjustvchr.client.domain;

import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrItemVO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO;
import com.jiuqi.dc.adjustvchr.impl.entity.AdjustVchrItemEO;
import com.jiuqi.dc.adjustvchr.impl.entity.AdjustVoucherEO;
import com.jiuqi.dc.base.common.intf.impl.DcTenantDTO;
import java.util.List;

public class AdjustVoucherDTO
extends DcTenantDTO {
    private static final long serialVersionUID = 4397734715767645743L;
    private AdjustVoucherVO masterVO;
    private List<AdjustVchrItemVO> itemVOList;
    private AdjustVoucherEO masterEO;
    private List<AdjustVchrItemEO> itemEOList;

    public AdjustVoucherVO getMasterVO() {
        return this.masterVO;
    }

    public void setMasterVO(AdjustVoucherVO masterVO) {
        this.masterVO = masterVO;
    }

    public List<AdjustVchrItemVO> getItemVOList() {
        return this.itemVOList;
    }

    public void setItemVOList(List<AdjustVchrItemVO> itemVOList) {
        this.itemVOList = itemVOList;
    }

    public AdjustVoucherEO getMasterEO() {
        return this.masterEO;
    }

    public void setMasterEO(AdjustVoucherEO masterEO) {
        this.masterEO = masterEO;
    }

    public List<AdjustVchrItemEO> getItemEOList() {
        return this.itemEOList;
    }

    public void setItemEOList(List<AdjustVchrItemEO> itemEOList) {
        this.itemEOList = itemEOList;
    }
}

