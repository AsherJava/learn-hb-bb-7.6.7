/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.datacrud.impl.loggger;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.loggger.DataServiceLogger;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataServiceLogWrapper {
    @Autowired
    private DataServiceLoggerFactory loggerFactory;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private DataEngineService dataEngineService;
    @Autowired
    private IExecutorContextFactory executorContextFactory;
    private static final String MODULE = "\u6570\u636e\u8bfb\u5199\u670d\u52a1";

    public DataServiceLogger getCrudLogger(RegionRelation relation, DimensionCombination dimensionCombination) {
        Object period;
        DataServiceLogHelper logger = this.loggerFactory.getLogger(MODULE, OperLevel.USER_OPER);
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        FixedDimensionValue periodDimensionValue = dimensionCombination.getPeriodDimensionValue();
        TaskDefine taskDefine = relation.getTaskDefine();
        String dw = taskDefine.getDw();
        String entityId = this.dataAccesslUtil.contextEntityId(dw);
        ExecutorContext executorContext = this.executorContextFactory.getExecutorContext((ParamRelation)relation, (DimensionCombination)null);
        IDataAssist dataAssist = this.dataEngineService.getDataAssist(executorContext);
        RunTimeEntityViewDefineImpl runTimeEntityViewDefine = new RunTimeEntityViewDefineImpl();
        runTimeEntityViewDefine.setEntityId(entityId);
        String dimensionName = dataAssist.getDimensionName((EntityViewDefine)runTimeEntityViewDefine);
        Object value = dimensionCombination.getValue(dimensionName);
        if (value != null) {
            logDimensionCollection.setDw(entityId, new String[]{value.toString()});
        }
        if ((period = periodDimensionValue.getValue()) != null) {
            logDimensionCollection.setPeriod(taskDefine.getDateTime(), period.toString());
        }
        return new DataServiceLogger(logger, relation, logDimensionCollection);
    }

    public DataServiceLogger getCrudLogger(RegionRelation relation, DimensionCollection dimensionCollection) {
        DataServiceLogHelper logger = this.loggerFactory.getLogger(MODULE, OperLevel.USER_OPER);
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        ExecutorContext executorContext = this.executorContextFactory.getExecutorContext((ParamRelation)relation, (DimensionCombination)null);
        IDataAssist dataAssist = this.dataEngineService.getDataAssist(executorContext);
        TaskDefine taskDefine = relation.getTaskDefine();
        String dw = taskDefine.getDw();
        String entityId = this.dataAccesslUtil.contextEntityId(dw);
        RunTimeEntityViewDefineImpl runTimeEntityViewDefine = new RunTimeEntityViewDefineImpl();
        runTimeEntityViewDefine.setEntityId(entityId);
        String dimensionName = dataAssist.getDimensionName((EntityViewDefine)runTimeEntityViewDefine);
        ArrayList<String> dwCodes = new ArrayList<String>();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            Object value = dimensionCombination.getValue(dimensionName);
            if (value == null) continue;
            dwCodes.add(value.toString());
        }
        logDimensionCollection.setDw(entityId, dwCodes.toArray(new String[0]));
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), ((DimensionCombination)dimensionCombinations.get(0)).getPeriodDimensionValue().getValue().toString());
        return new DataServiceLogger(logger, relation, logDimensionCollection);
    }
}

