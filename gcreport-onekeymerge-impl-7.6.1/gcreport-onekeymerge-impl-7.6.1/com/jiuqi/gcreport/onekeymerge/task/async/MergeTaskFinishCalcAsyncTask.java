/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.onekeymerge.dto.FinishCalcAsyncTaskDTO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.temp.dto.Message$ProgressResult
 *  com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.shiro.config.subject.MyWebDelegatingSubject
 *  org.apache.shiro.mgt.SecurityManager
 *  org.apache.shiro.session.Session
 *  org.apache.shiro.session.mgt.SimpleSession
 *  org.apache.shiro.subject.PrincipalCollection
 *  org.apache.shiro.subject.SimplePrincipalMap
 *  org.apache.shiro.subject.Subject
 */
package com.jiuqi.gcreport.onekeymerge.task.async;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.onekeymerge.dto.FinishCalcAsyncTaskDTO;
import com.jiuqi.gcreport.onekeymerge.task.GcFinishCalcTaskImpl;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.temp.dto.Message;
import com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.shiro.config.subject.MyWebDelegatingSubject;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalMap;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class MergeTaskFinishCalcAsyncTask
implements NpAsyncTaskExecutor {
    @Lazy
    @Autowired
    private ProgressService<OnekeyProgressDataImpl, Message.ProgressResult> progressService;
    @Lazy
    @Autowired
    private SecurityManager securityManager;

    public void execute(Object args, AsyncTaskMonitor monitor) {
        try {
            if (Objects.nonNull(args)) {
                FinishCalcAsyncTaskDTO finishCalcAsyncTaskDTO = (FinishCalcAsyncTaskDTO)JsonUtils.readValue((String)args.toString(), FinishCalcAsyncTaskDTO.class);
                GcActionParamsVO gcActionParamsVO = new GcActionParamsVO();
                BeanUtils.copyProperties(finishCalcAsyncTaskDTO, gcActionParamsVO);
                GcOrgCacheVO currentOrg = OrgUtils.getOrgByCode(gcActionParamsVO.getPeriodStr(), gcActionParamsVO.getOrgType(), gcActionParamsVO.getOrgId());
                List<String> hbUnitIds = Collections.singletonList(currentOrg.getId());
                List<String> diffUnitIds = Collections.singletonList(currentOrg.getDiffUnitId());
                OnekeyProgressDataImpl onekeyProgressData = new OnekeyProgressDataImpl(OneKeyMergeUtils.generateSN("process", gcActionParamsVO.getTaskLogId()));
                this.progressService.createProgressData((ProgressData)onekeyProgressData);
                TaskLog taskLog = new TaskLog(onekeyProgressData);
                GcFinishCalcTaskImpl gcFinishCalcTask = (GcFinishCalcTaskImpl)SpringContextUtils.getBean(GcFinishCalcTaskImpl.class);
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.startCompleteMerger"), Float.valueOf(1.0f));
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.executor") + ":  " + finishCalcAsyncTaskDTO.getUserName() + "", Float.valueOf(1.0f));
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.startTime") + DateUtils.nowTimeStr((String)"yyyy-MM-dd HH:mm:ss") + "", Float.valueOf(1.0f));
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.unitParameters"), Float.valueOf(2.0f));
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.start") + ": " + currentOrg.getTitle(), Float.valueOf(taskLog.getProcessPercent()));
                this.bindSubject(finishCalcAsyncTaskDTO);
                ReturnObject returnObject = gcFinishCalcTask.finishCalc(gcActionParamsVO, diffUnitIds, hbUnitIds, taskLog, 0);
                if (returnObject.isSuccess()) {
                    monitor.finish(currentOrg.getTitle() + "\u5b8c\u6210\u5408\u5e76\u6210\u529f", (Object)(currentOrg.getTitle() + "\u5b8c\u6210\u5408\u5e76\u6210\u529f"));
                } else {
                    taskLog.writeErrorLog(currentOrg.getTitle() + GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.breakup") + ": " + returnObject.getErrorMessage(), Float.valueOf(taskLog.getProcessPercent()));
                    taskLog.setFinish(true);
                    taskLog.syncTaskLog();
                    monitor.error(currentOrg.getTitle() + "\u5b8c\u6210\u5408\u5e76\u5931\u8d25", null, returnObject.getErrorMessage());
                }
            }
        }
        catch (Exception e) {
            monitor.error("\u5b8c\u6210\u5408\u5e76\u5f02\u5e38", (Throwable)e);
        }
    }

    public String getTaskPoolType() {
        return "GC_ASYNCTASK_FINISHCALC";
    }

    private void bindSubject(FinishCalcAsyncTaskDTO finishCalcAsyncTaskDTO) {
        SimpleSession session = new SimpleSession();
        session.setId((Serializable)((Object)finishCalcAsyncTaskDTO.getLoginToken()));
        SimplePrincipalMap simplePrincipalMap = new SimplePrincipalMap();
        simplePrincipalMap.put("userName", (Object)finishCalcAsyncTaskDTO.getUserName());
        MyWebDelegatingSubject myWebDelegatingSubject = new MyWebDelegatingSubject((PrincipalCollection)simplePrincipalMap, true, null, (Session)session, true, null, null, this.securityManager);
        ShiroUtil.bindSubject((Subject)myWebDelegatingSubject);
    }
}

