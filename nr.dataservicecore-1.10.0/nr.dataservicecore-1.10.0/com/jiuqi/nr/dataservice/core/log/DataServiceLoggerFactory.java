/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.common.log.UnitReportLog
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 */
package com.jiuqi.nr.dataservice.core.log;

import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.common.log.UnitReportLog;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.UnitReportStorage;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataServiceLoggerFactory {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService iEntityDataService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;

    public DataServiceLogHelper getLogger(String module, OperLevel operLevel) {
        return new DataServiceLogHelper(module, operLevel, this.runTimeViewController, this.entityViewRunTimeController, this.iEntityDataService, this.dataDefinitionRuntimeController, this.periodEntityAdapter);
    }

    public UnitReportLog getUnitReportLog() {
        UnitReportStorage unitReportStorage = new UnitReportStorage();
        unitReportStorage.setRunTimeViewController(this.runTimeViewController);
        return unitReportStorage;
    }
}

