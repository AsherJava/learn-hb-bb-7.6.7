/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.nr.definition.auth.authz2.ResourceType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.domain.VaMessageSendDTO
 *  com.jiuqi.va.message.feign.client.VaMessageClient
 *  org.quartz.CronScheduleBuilder
 *  org.quartz.JobBuilder
 *  org.quartz.JobDataMap
 *  org.quartz.JobDetail
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Scheduler
 *  org.quartz.Trigger
 *  org.quartz.TriggerBuilder
 */
package com.jiuqi.nr.designer.planpublish.service.impl;

import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.nr.definition.auth.authz2.ResourceType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao;
import com.jiuqi.nr.designer.planpublish.job.JobCommon;
import com.jiuqi.nr.designer.planpublish.job.PlanPublishSendMessageJob;
import com.jiuqi.nr.designer.planpublish.model.PlanPublishMessageObj;
import com.jiuqi.nr.designer.planpublish.model.TaskPlanPublishObj;
import com.jiuqi.nr.designer.planpublish.service.SendMessageService;
import com.jiuqi.nr.designer.planpublish.util.DateUtils;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.domain.VaMessageSendDTO;
import com.jiuqi.va.message.feign.client.VaMessageClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMessageServiceImpl
implements SendMessageService {
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private TaskPlanPublishDao innerTaskPlanPublishDao;
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private VaMessageClient messageClient;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;

    @Override
    public void sendMessageToUser(String message, String taskKey) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            return;
        }
        String title = taskDefine.getTitle();
        Set identityKeys = this.privilegeService.getHasAuthIdentities("task_privilege_read", (Object)ResourceType.TASK.toResourceId(taskKey));
        if (identityKeys.isEmpty()) {
            return;
        }
        VaMessageSendDTO dto = new VaMessageSendDTO();
        dto.setMsgtype("\u53d1\u5e03\u4efb\u52a1");
        dto.setGrouptype("\u901a\u77e5");
        dto.setTitle("\u3010" + title + "\u3011\u9884\u7ea6\u53d1\u5e03\u4efb\u52a1");
        dto.setContent(message);
        dto.setMsgChannel(VaMessageOption.MsgChannel.PC);
        dto.setReceiveUserIds(new ArrayList(identityKeys));
        this.messageClient.addMsg(dto);
    }

    @Override
    public void createSendMessageJobs(String planKey, TaskPlanPublishObj taskPlanPublishObj) throws Exception {
        List<PlanPublishMessageObj> messageObjs = taskPlanPublishObj.getMessageObjs();
        if (messageObjs != null) {
            String jobKeys = "";
            for (int i = 0; i < messageObjs.size(); ++i) {
                String jobKey = JobCommon.getMessageJobId();
                jobKeys = jobKeys + jobKey + ";";
                String taskKey = taskPlanPublishObj.getTaskID();
                String date = messageObjs.get(i).getDate();
                String message = messageObjs.get(i).getMessge();
                this.createJob(jobKey, planKey, taskKey, message, date);
            }
            this.innerTaskPlanPublishDao.updateMessageJobKeys(planKey, jobKeys);
        }
    }

    private void createJob(String jobKey, String planKey, String taskKey, String message, String date) throws Exception {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("planKey", planKey);
        jobDataMap.put("taskKey", taskKey);
        jobDataMap.put("message", message);
        Class<PlanPublishSendMessageJob> jobClass = PlanPublishSendMessageJob.class;
        String jobGroupId = JobCommon.getMessageJobGroupId();
        String cronExpression = DateUtils.getCron(date);
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey, jobGroupId).setJobData(jobDataMap).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobKey, jobGroupId).startNow().withSchedule((ScheduleBuilder)CronScheduleBuilder.cronSchedule((String)cronExpression)).build();
        this.scheduler.scheduleJob(jobDetail, trigger);
        if (!this.scheduler.isShutdown()) {
            this.scheduler.start();
        }
    }
}

