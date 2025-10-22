/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.select.common.RunType
 *  com.jiuqi.nr.period.select.service.IRuntimePeriodModuleService
 */
package com.jiuqi.nr.dataentry.plantask;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.dataentry.paramInfo.TaskOrgData;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.select.common.RunType;
import com.jiuqi.nr.period.select.service.IRuntimePeriodModuleService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlanTaskUtil {
    @Autowired
    private IRuntimePeriodModuleService iRuntimePeriodModuleService;
    @Autowired
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;

    public String getPeriod(String taskKey, String period, int periondConfig) {
        int customPeriod = 2;
        String periodValue = null;
        int offset = 0;
        if (periondConfig != customPeriod) {
            offset = periondConfig == 3 ? 2 : (periondConfig == -3 ? -2 : periondConfig);
        } else {
            return period;
        }
        TaskData taskData = this.dataEntryParamService.getRuntimeTaskByKey(taskKey);
        IPeriodProvider periodProvider = null;
        for (EntityViewData view : taskData.getEntitys()) {
            if (!this.periodEntityAdapter.isPeriodEntity(view.getKey())) continue;
            periodProvider = this.periodEntityAdapter.getPeriodProvider(view.getKey());
        }
        PeriodWrapper currentPeriod = null;
        periodValue = this.iRuntimePeriodModuleService.queryOffsetPeriod(taskKey, RunType.RUNTIME);
        try {
            currentPeriod = new PeriodWrapper(periodValue);
            if (periodProvider != null) {
                periodProvider.modifyPeriod(currentPeriod, offset);
            }
            PeriodWrapper fromPeriod = new PeriodWrapper(taskData.getFromPeriod());
            PeriodWrapper toPeriod = new PeriodWrapper(taskData.getToPeriod());
            if (offset > 0) {
                if (currentPeriod.compareTo((Object)toPeriod) > 0) {
                    currentPeriod = new PeriodWrapper(taskData.getToPeriod());
                }
            } else if (offset < 0 && currentPeriod.compareTo((Object)fromPeriod) < 0) {
                currentPeriod = new PeriodWrapper(taskData.getFromPeriod());
            }
        }
        catch (Exception e) {
            currentPeriod = new PeriodWrapper(periodValue);
        }
        return currentPeriod.toString();
    }

    public List<TaskOrgData> queryTaskorgDataList(String taskKey) {
        IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        TaskOrgLinkListStream taskOrgLinkListStream = iRunTimeViewController.listTaskOrgLinkStreamByTask(taskKey);
        ArrayList<TaskOrgData> taskOrgDataList = new ArrayList<TaskOrgData>();
        IEntityMetaService iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkListStream.auth().i18n().getList()) {
            TaskOrgData taskOrgData = new TaskOrgData();
            taskOrgData.setId(taskOrgLinkDefine.getEntity());
            if (StringUtils.isNotEmpty((String)taskOrgLinkDefine.getEntityAlias())) {
                taskOrgData.setTitle(taskOrgLinkDefine.getEntityAlias());
            } else {
                taskOrgData.setTitle(iEntityMetaService.queryEntity(taskOrgLinkDefine.getEntity()).getTitle());
            }
            taskOrgDataList.add(taskOrgData);
        }
        return taskOrgDataList;
    }
}

