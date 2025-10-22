/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.common.log.NRLogCustomAttribute
 *  com.jiuqi.nr.common.log.NRLogEntity
 *  com.jiuqi.nr.common.log.NRLogHelper
 *  com.jiuqi.nr.common.log.UnitReportLog
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 */
package com.jiuqi.nr.dataservice.core.log;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.common.log.NRLogCustomAttribute;
import com.jiuqi.nr.common.log.NRLogEntity;
import com.jiuqi.nr.common.log.NRLogHelper;
import com.jiuqi.nr.common.log.UnitReportLog;
import com.jiuqi.nr.dataservice.core.dimension.ArrayDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataServiceLogHelper {
    private final IRunTimeViewController runtimeViewController;
    private final IEntityViewRunTimeController entityViewRunTimeController;
    private final String module;
    private final OperLevel operLevel;
    private final IEntityDataService entityDataService;
    private final IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private final IPeriodEntityAdapter periodEntityAdapter;
    private static final Logger logger = LoggerFactory.getLogger(DataServiceLogHelper.class);

    public DataServiceLogHelper(String module, OperLevel operLevel, IRunTimeViewController runtimeViewController, IEntityViewRunTimeController entityViewRunTimeController, IEntityDataService entityDataService, IDataDefinitionRuntimeController dataDefinitionRuntimeController, IPeriodEntityAdapter periodEntityAdapter) {
        this.runtimeViewController = runtimeViewController;
        this.entityViewRunTimeController = entityViewRunTimeController;
        this.entityDataService = entityDataService;
        this.dataDefinitionRuntimeController = dataDefinitionRuntimeController;
        this.periodEntityAdapter = periodEntityAdapter;
        this.module = module;
        this.operLevel = operLevel;
    }

    public void info(String taskKey, LogDimensionCollection dimensionCollection, UnitReportLog unitReportLog, String title, String message) {
        String tableLog = DataServiceLogHelper.rebuildLog(dimensionCollection, unitReportLog);
        this.info(taskKey, dimensionCollection, title, message + "\n" + tableLog);
    }

    private static String rebuildLog(LogDimensionCollection dimensionCollection, UnitReportLog unitReportLog) {
        String tableLog = unitReportLog.toLog();
        String[] units = unitReportLog.getUnits();
        ArrayDimensionValue dwDimension = dimensionCollection.getDwDimension();
        if (dwDimension != null && units != null && units.length > 0) {
            dimensionCollection.setDw(dwDimension.getEntityID(), units);
        }
        return tableLog;
    }

    public void error(String taskKey, LogDimensionCollection dimensionCollection, UnitReportLog unitReportLog, String title, String message) {
        String tableLog = DataServiceLogHelper.rebuildLog(dimensionCollection, unitReportLog);
        this.error(taskKey, dimensionCollection, title, message + "\n" + tableLog);
    }

    public void info(String taskKey, LogDimensionCollection dimensionCollection, String title, String message) {
        try {
            NRLogHelper.info((NRLogEntity)this.initLogEntity(taskKey, dimensionCollection, title, message));
        }
        catch (Exception e) {
            logger.error("\u65e5\u5fd7\u8bb0\u5f55\u51fa\u73b0\u9519\u8bef\uff01", e);
        }
    }

    public void error(String taskKey, LogDimensionCollection dimensionCollection, String title, String message) {
        try {
            NRLogHelper.error((NRLogEntity)this.initLogEntity(taskKey, dimensionCollection, title, message));
        }
        catch (Exception e) {
            logger.error("\u65e5\u5fd7\u8bb0\u5f55\u51fa\u73b0\u9519\u8bef\uff01", e);
        }
    }

    private NRLogEntity initLogEntity(String taskKey, LogDimensionCollection dimensionCollection, String title, String message) {
        NRLogEntity entity = new NRLogEntity(this.module, title, message, this.operLevel);
        StringBuilder messageSB = new StringBuilder();
        this.addTaskAttribute(messageSB, taskKey, entity);
        if (null != dimensionCollection) {
            this.addAttribute(messageSB, dimensionCollection, entity);
        }
        messageSB.append(message);
        entity.setMessage(messageSB.toString());
        return entity;
    }

    private void addAttribute(StringBuilder messageSB, LogDimensionCollection dimensionCollection, NRLogEntity entity) {
        boolean simpleModeEnabled = LogHelper.isSimpleModeEnabled();
        ArrayDimensionValue dwDimension = dimensionCollection.getDwDimension();
        FixedDimensionValue periodDimension = dimensionCollection.getPeriodDimension();
        String periodTitle = null;
        Date versionDate = Consts.DATE_VERSION_FOR_ALL;
        if (null != periodDimension) {
            String periodStr = (String)periodDimension.getValue();
            IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(periodDimension.getEntityID());
            periodTitle = periodProvider.getPeriodTitle(periodStr);
            try {
                versionDate = periodProvider.getPeriodDateRegion(periodStr)[1];
            }
            catch (ParseException e) {
                logger.error("\u8bb0\u5f55\u65e5\u5fd7\u83b7\u53d6\u65f6\u671f\u5931\u8d25\uff01{}", periodDimension.getValue(), (Object)e);
            }
            entity.setPeriodAttribute(new NRLogCustomAttribute(periodStr, periodTitle));
        }
        if (null != dwDimension && null != dwDimension.getValue()) {
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            EntityViewDefine buildEntityView = this.entityViewRunTimeController.buildEntityView(dwDimension.getEntityID(), "", false);
            entityQuery.setEntityView(buildEntityView);
            entityQuery.setQueryVersionDate(versionDate);
            try {
                IEntityTable executeReader = entityQuery.executeReader((IContext)new ExecutorContext(this.dataDefinitionRuntimeController));
                Object[] dwValues = dwDimension.getValue();
                messageSB.append("\u5355\u4f4d\uff1a");
                Set dwList = Arrays.stream(dwValues).map(d -> (String)d).collect(Collectors.toSet());
                ArrayList<NRLogCustomAttribute> dwAttributes = new ArrayList<NRLogCustomAttribute>();
                Map entityRows = executeReader.findByEntityKeys(dwList);
                boolean needComma = false;
                for (IEntityRow dwEntity : entityRows.values()) {
                    if (needComma) {
                        if (!simpleModeEnabled) {
                            messageSB.append(",");
                            messageSB.append(dwEntity.getTitle()).append("\u3010").append(dwEntity.getCode()).append("\u3011");
                        }
                    } else {
                        messageSB.append(dwEntity.getTitle()).append("\u3010").append(dwEntity.getCode()).append("\u3011");
                    }
                    dwAttributes.add(new NRLogCustomAttribute(dwEntity.getCode(), dwEntity.getTitle()));
                    needComma = true;
                }
                if (simpleModeEnabled && entityRows.size() > 1) {
                    messageSB.append("\u7b49").append(entityRows.size()).append("\u5bb6");
                }
                entity.setDwAttribute(dwAttributes);
                messageSB.append("\u3002");
            }
            catch (Exception e) {
                logger.error("\u8bb0\u5f55\u65e5\u5fd7\u83b7\u53d6\u5355\u4f4d\u5931\u8d25\uff01", e);
            }
        }
        if (null != periodTitle) {
            messageSB.append("\u65f6\u671f\uff1a").append(periodTitle).append("\u3002");
        }
    }

    private void addTaskAttribute(StringBuilder messageSB, String taskKey, NRLogEntity entity) {
        if (StringUtils.isEmpty((String)taskKey)) {
            return;
        }
        TaskDefine taskDefine = this.runtimeViewController.queryTaskDefine(taskKey);
        if (taskDefine != null) {
            entity.setTaskAttribute(new NRLogCustomAttribute(taskDefine.getTaskCode(), taskDefine.getTitle()));
            messageSB.append("\u4efb\u52a1\uff1a").append(taskDefine.getTitle()).append("\u3010").append(taskDefine.getTaskCode()).append("\u3011\u3002");
        } else {
            logger.info("\u8bb0\u5f55\u65e5\u5fd7\u83b7\u53d6\u4efb\u52a1\u5931\u8d25\uff01\u3010" + taskKey + "\u3011\u672a\u627e\u5230\u3002");
            entity.setTaskAttribute(new NRLogCustomAttribute(taskKey, taskKey));
        }
    }
}

