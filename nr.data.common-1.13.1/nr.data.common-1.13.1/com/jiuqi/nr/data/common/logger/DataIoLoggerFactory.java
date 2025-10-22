/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.common.log.UnitReportLog
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.datacrud.impl.service.DataEngineService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.common.logger;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.common.log.UnitReportLog;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.common.logger.DataImportLogger;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataIoLoggerFactory {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private DataEngineService dataEngineService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;

    public DataImportLogger getDataImportLogger(String module, String formSchemeKey, DimensionCombination dimensionCombination) {
        Object period;
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        String dwEntityId = formSchemeDefine.getDw();
        String periodEntityId = formSchemeDefine.getDateTime();
        String taskKey = formSchemeDefine.getTaskKey();
        DataServiceLogHelper dataServiceLogHelper = this.dataServiceLoggerFactory.getLogger(module, OperLevel.USER_OPER);
        UnitReportLog unitReportLog = this.dataServiceLoggerFactory.getUnitReportLog();
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        FixedDimensionValue periodDimensionValue = dimensionCombination.getPeriodDimensionValue();
        String entityId = this.dataAccesslUtil.contextEntityId(dwEntityId);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setPeriodView(periodEntityId);
        IDataAssist dataAssist = this.dataEngineService.getDataAssist(context);
        RunTimeEntityViewDefineImpl runTimeEntityViewDefine = new RunTimeEntityViewDefineImpl();
        runTimeEntityViewDefine.setEntityId(entityId);
        String dimensionName = dataAssist.getDimensionName((EntityViewDefine)runTimeEntityViewDefine);
        Object value = dimensionCombination.getValue(dimensionName);
        if (value != null) {
            if (value instanceof List) {
                logDimensionCollection.setDw(entityId, ((List)value).toArray(new String[0]));
            } else {
                logDimensionCollection.setDw(entityId, new String[]{value.toString()});
            }
        }
        if ((period = periodDimensionValue.getValue()) != null) {
            logDimensionCollection.setPeriod(periodEntityId, period.toString());
        }
        return new DataImportLogger(taskKey, dataServiceLogHelper, unitReportLog, logDimensionCollection);
    }
}

