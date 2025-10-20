/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.financialcubes.fincubeadjustvchr;

import com.jiuqi.common.financialcubes.dto.FinancialCubesAdjustTaskDTO;
import java.util.Collection;
import java.util.List;

public interface FincubeCommonAdjustVchrDao {
    public String getDeleteIdByVchrIdList(Collection<String> var1);

    public List<FinancialCubesAdjustTaskDTO> listAgingVchrMasterDimByVchrId(Collection<String> var1, String var2);

    public List<FinancialCubesAdjustTaskDTO> listVchrMasterDimByVchrId(Collection<String> var1, String var2);

    public List<FinancialCubesAdjustTaskDTO> listVchrMasterCfByVchrId(Collection<String> var1, String var2);
}

