/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.nr.data.access.service.DPEFactoryBuilder
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.common.ProviderStore
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.nr.data.access.service.DPEFactoryBuilder;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.logic.api.IDataLogicServiceFactory;
import com.jiuqi.nr.data.logic.api.param.CheckExeResult;
import com.jiuqi.nr.data.logic.facade.extend.IFmlCheckListener;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.service.ICheckService;
import com.jiuqi.nr.data.logic.internal.async.AllCheckAsyncTaskExecutor;
import com.jiuqi.nr.data.logic.internal.async.BatchCheckAsyncTaskExecutor;
import com.jiuqi.nr.data.logic.internal.impl.DefCheckMonitor;
import com.jiuqi.nr.data.logic.internal.impl.EmptyFmlMonitor;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.common.ProviderStore;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class CheckServiceImplV2
implements ICheckService {
    @Autowired
    private IDataLogicServiceFactory dataLogicServiceFactory;
    @Autowired
    private IProviderStore providerStore;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired(required=false)
    private List<IFmlCheckListener> fmlCheckListeners;
    @Autowired
    private ParamUtil paramUtil;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Override
    public CheckResult check(CheckParam checkParam) {
        com.jiuqi.nr.data.logic.api.ICheckService checkService = this.getCheckService(checkParam);
        return checkService.check(checkParam);
    }

    @Override
    public String allCheck(CheckParam checkParam, IFmlMonitor fmlMonitor) {
        com.jiuqi.nr.data.logic.api.ICheckService checkService = this.getCheckService(checkParam);
        DefCheckMonitor defCheckMonitor = this.getDefCheckMonitor(fmlMonitor);
        CheckExeResult checkExeResult = checkService.allCheck(checkParam, defCheckMonitor);
        return checkExeResult.getExecuteId();
    }

    @Override
    public String allCheckAsync(CheckParam checkParam) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = this.paramUtil.getNpRealTimeTaskInfo(checkParam, (AbstractRealTimeJob)new AllCheckAsyncTaskExecutor());
        return this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "ASYNC_TASK_ALL_CHECK");
    }

    @Override
    public String batchCheck(CheckParam checkParam, IFmlMonitor fmlMonitor) {
        com.jiuqi.nr.data.logic.api.ICheckService checkService = this.getCheckService(checkParam);
        DefCheckMonitor defCheckMonitor = this.getDefCheckMonitor(fmlMonitor);
        CheckExeResult checkExeResult = checkService.batchCheck(checkParam, defCheckMonitor);
        return checkExeResult.getExecuteId();
    }

    @Override
    public String batchCheckAsync(CheckParam checkParam) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = this.paramUtil.getNpRealTimeTaskInfo(checkParam, (AbstractRealTimeJob)new BatchCheckAsyncTaskExecutor());
        return this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "ASYNC_TASK_BATCH_CHECK");
    }

    private com.jiuqi.nr.data.logic.api.ICheckService getCheckService(CheckParam checkParam) {
        return this.dataLogicServiceFactory.getCheckService(this.getProviderStore(checkParam));
    }

    private IProviderStore getProviderStore(CheckParam checkParam) {
        Set<String> ignoreItems = checkParam.getIgnoreItems();
        if (CollectionUtils.isEmpty(ignoreItems)) {
            return this.providerStore;
        }
        DPEFactoryBuilder dpeFactoryBuilder = new DPEFactoryBuilder(this.dataAccessServiceProvider);
        ignoreItems.forEach(arg_0 -> ((DPEFactoryBuilder)dpeFactoryBuilder).ignorePermission(arg_0));
        DataPermissionEvaluatorFactory dataPermissionEvaluatorFactory = dpeFactoryBuilder.build();
        return new ProviderStore(dataPermissionEvaluatorFactory);
    }

    private DefCheckMonitor getDefCheckMonitor(IFmlMonitor fmlMonitor) {
        DefCheckMonitor defCheckMonitor = fmlMonitor == null ? new DefCheckMonitor(EmptyFmlMonitor.getInstance(), this.fmlCheckListeners) : new DefCheckMonitor(fmlMonitor, this.fmlCheckListeners);
        return defCheckMonitor;
    }
}

