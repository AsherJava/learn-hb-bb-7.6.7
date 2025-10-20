/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.common.financialcubes.systemoption.enabled;

import com.jiuqi.common.financialcubes.systemoption.enabled.IFinancialCubesEnabled;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultFinancialCubesEnabled
implements IFinancialCubesEnabled {
    @Autowired
    private INvwaSystemOptionService optionService;

    @Override
    public boolean isEnabled() {
        String gcFinancialcubesEnabled = this.optionService.findValueById("FINANCIAL_CUBES_ENABLE");
        return "1".equals(gcFinancialcubesEnabled);
    }
}

