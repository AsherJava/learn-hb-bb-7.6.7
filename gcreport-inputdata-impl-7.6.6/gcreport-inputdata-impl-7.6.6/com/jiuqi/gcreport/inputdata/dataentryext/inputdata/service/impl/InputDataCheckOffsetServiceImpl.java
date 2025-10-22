/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.impl;

import com.jiuqi.gcreport.inputdata.check.service.InputDataCheckOffsetService;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataAdvanceService;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputWriteNecLimitCondition;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InputDataCheckOffsetServiceImpl
implements InputDataCheckOffsetService {
    @Autowired
    private GcOffsetAppInputDataService gcOffsetAppInputDataService;
    @Autowired
    private InputDataAdvanceService inputDataAdvanceService;

    @Override
    public void cancelInputOffset(Collection<String> inputItemIds, InputWriteNecLimitCondition inputWriteNecLimitCondition) {
        this.gcOffsetAppInputDataService.cancelInputOffset(inputItemIds, inputWriteNecLimitCondition);
    }

    @Override
    public Map<String, Set<String>> doCheckAfterOffset(List<InputDataEO> inputItems) {
        return this.inputDataAdvanceService.doCheckAfterOffset(inputItems);
    }
}

