/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financial.status.service;

import com.jiuqi.gc.financial.status.dto.FinancialGroupStatusDTO;
import com.jiuqi.gc.financial.status.dto.FinancialUnitStatusDTO;
import com.jiuqi.gc.financial.status.vo.FinancialStatusQueryParam;
import java.util.List;

public interface FinancialStatusQueryService {
    public List<FinancialGroupStatusDTO> listFinancialGroupStatusData(FinancialStatusQueryParam var1);

    public List<FinancialUnitStatusDTO> listFinancialUnitStatusData(FinancialStatusQueryParam var1);
}

