/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.domain.action;

import com.jiuqi.va.bill.domain.action.BillActionParamDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;

public class CustomActionParamDTO
extends TenantDO {
    private List<BillActionParamDTO> billActionParamList;

    public List<BillActionParamDTO> getBillActionParamList() {
        return this.billActionParamList;
    }

    public void setBillActionParamList(List<BillActionParamDTO> billActionParamList) {
        this.billActionParamList = billActionParamList;
    }
}

