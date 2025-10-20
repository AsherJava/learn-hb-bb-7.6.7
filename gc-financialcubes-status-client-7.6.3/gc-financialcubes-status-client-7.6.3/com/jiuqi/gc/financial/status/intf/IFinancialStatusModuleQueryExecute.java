/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financial.status.intf;

import com.jiuqi.gc.financial.status.dto.FinancialGroupStatusDTO;
import com.jiuqi.gc.financial.status.dto.FinancialUnitStatusDTO;
import com.jiuqi.gc.financial.status.event.FinancialStatusChangeEventData;
import com.jiuqi.gc.financial.status.vo.FinancialStatusQueryParam;
import java.util.List;

public interface IFinancialStatusModuleQueryExecute {
    public String getExecuteName();

    public List<FinancialGroupStatusDTO> listFinancialGroupStatusData(FinancialStatusQueryParam var1);

    public List<FinancialUnitStatusDTO> listFinancialUnitStatusData(FinancialStatusQueryParam var1);

    public void syncGroupCache(FinancialStatusChangeEventData var1);

    public void syncUnitCache(FinancialStatusChangeEventData var1);
}

