/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.time.setting.util.PeriodUtilS
 *  com.jiuqi.nvwa.resourceview.utils.AppConditionUtil
 *  com.jiuqi.nvwa.resourceview.utils.AppFunctionConfigUtil
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.gather.gzw.web.app.context;

import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextData;
import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextDataImpl;
import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextHelper;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.time.setting.util.PeriodUtilS;
import com.jiuqi.nvwa.resourceview.utils.AppConditionUtil;
import com.jiuqi.nvwa.resourceview.utils.AppFunctionConfigUtil;
import com.jiuqi.util.StringUtils;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BatchGatherGZWContextHelperImpl
implements BatchGatherGZWContextHelper {
    @Resource
    private IRunTimeViewController rtViewService;
    @Resource
    private IRuntimeDataSchemeService dataSchemeService;
    @Resource
    private DefinitionAuthorityProvider authorityProvider;

    @Override
    public BatchGatherGZWContextData initContextData() {
        BatchGatherGZWContextDataImpl contextData = this.initSummaryContext();
        TaskDefine currTaskDefine = this.getCurrTaskDefine(contextData.getTaskId());
        if (currTaskDefine != null) {
            contextData.setTaskId(currTaskDefine.getKey());
        }
        return contextData;
    }

    private BatchGatherGZWContextDataImpl initSummaryContext() {
        String functionConfig = AppFunctionConfigUtil.getFunctionConfig();
        if (StringUtils.isNotEmpty((String)functionConfig)) {
            return (BatchGatherGZWContextDataImpl)BatchSummaryUtils.toJavaBean((String)functionConfig, BatchGatherGZWContextDataImpl.class);
        }
        return new BatchGatherGZWContextDataImpl();
    }

    @Override
    public BatchGatherGZWContextData getContextData() {
        String objectStr = AppConditionUtil.getConitionValue();
        BatchGatherGZWContextDataImpl contextData = null;
        if (StringUtils.isNotEmpty((String)objectStr)) {
            contextData = (BatchGatherGZWContextDataImpl)BatchSummaryUtils.toJavaBean((String)objectStr, BatchGatherGZWContextDataImpl.class);
        }
        return contextData;
    }

    @Override
    public void updateContextData(BatchGatherGZWContextData contextData) {
        AppConditionUtil.storageConditionValue((String)BatchSummaryUtils.toJSONStr((Object)contextData));
    }

    @Override
    public TaskDefine getCurrTaskDefine(String taskKey) {
        List allTaskDefines;
        TaskDefine taskDefine = this.rtViewService.queryTaskDefine(taskKey);
        if (taskDefine == null && (allTaskDefines = this.rtViewService.getAllTaskDefines()) != null && !allTaskDefines.isEmpty() && !(allTaskDefines = allTaskDefines.stream().filter(task -> this.authorityProvider.canReadTask(task.getKey())).collect(Collectors.toList())).isEmpty()) {
            taskDefine = (TaskDefine)allTaskDefines.get(0);
        }
        return taskDefine;
    }

    @Override
    public FormSchemeDefine getCurrFormSchemeDefine(TaskDefine taskDefine) {
        if (taskDefine != null) {
            try {
                PeriodWrapper periodWrapper = PeriodUtilS.currentPeriod((GregorianCalendar)new GregorianCalendar(), (int)taskDefine.getPeriodType().type());
                SchemePeriodLinkDefine schemePeriodLinkDefine = this.rtViewService.querySchemePeriodLinkByPeriodAndTask(periodWrapper.toString(), taskDefine.getKey());
                if (schemePeriodLinkDefine != null) {
                    return this.rtViewService.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
                }
                List formSchemeDefines = this.rtViewService.queryFormSchemeByTask(taskDefine.getKey());
                if (formSchemeDefines != null && !formSchemeDefines.isEmpty()) {
                    return (FormSchemeDefine)formSchemeDefines.get(0);
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}

