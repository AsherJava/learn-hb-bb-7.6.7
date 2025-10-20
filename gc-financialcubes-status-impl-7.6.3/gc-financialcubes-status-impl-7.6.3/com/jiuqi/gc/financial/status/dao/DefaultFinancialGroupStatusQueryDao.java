/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gc.financial.status.dto.FinancialGroupStatusDTO
 */
package com.jiuqi.gc.financial.status.dao;

import com.jiuqi.gc.financial.status.dto.FinancialGroupStatusDTO;
import java.util.List;

public interface DefaultFinancialGroupStatusQueryDao {
    public List<FinancialGroupStatusDTO> listAllFinancialGroupStatusDataByModuleCode(String var1);
}

