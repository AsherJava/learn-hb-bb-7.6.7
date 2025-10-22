/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.system.check.service.impl;

import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.system.check.service.IParamCheckEntityUpgrader;
import com.jiuqi.nr.system.check.service.SCCustomPeriodService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCCustomPeriodServiceImpl
implements SCCustomPeriodService {
    @Autowired
    private IParamCheckEntityUpgrader iParamCheckEntityUpgrader;

    @Override
    public List<IPeriodRow> getCustomPeriodDataList(String periodEntityKey) throws Exception {
        return this.iParamCheckEntityUpgrader.getCustomPeriodDataList(periodEntityKey);
    }
}

