/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.time.setting.de.DeSetTimeProvide
 *  com.jiuqi.nr.time.setting.de.TimeCommon
 *  com.jiuqi.xlib.utils.GUID
 *  javax.annotation.Resource
 *  org.quartz.JobKey
 *  org.quartz.Scheduler
 *  org.quartz.TriggerKey
 */
package com.jiuqi.nr.reminder.scheduler;

import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.reminder.bean.ReminderJobEntity;
import com.jiuqi.nr.reminder.infer.ParticipantHelper;
import com.jiuqi.nr.reminder.infer.ReminderJobInfoDao;
import com.jiuqi.nr.reminder.infer.ReminderRepository;
import com.jiuqi.nr.reminder.internal.CreateReminderCommand;
import com.jiuqi.nr.reminder.internal.ManualReminder;
import com.jiuqi.nr.reminder.internal.Reminder;
import com.jiuqi.nr.reminder.scheduler.CronUtils;
import com.jiuqi.nr.reminder.scheduler.ScheduledJob;
import com.jiuqi.nr.reminder.scheduler.SchedulerImpl;
import com.jiuqi.nr.reminder.untils.BeanConverter;
import com.jiuqi.nr.reminder.untils.EntityHelper;
import com.jiuqi.nr.reminder.untils.EntityQueryManager;
import com.jiuqi.nr.time.setting.de.DeSetTimeProvide;
import com.jiuqi.nr.time.setting.de.TimeCommon;
import com.jiuqi.xlib.utils.GUID;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutoScheduleImpl {
    private static final Logger log = LoggerFactory.getLogger(AutoScheduleImpl.class);
    public static final String CORN_DATA = "";
    public static final String CORN_TWO_TIMW = "0 m h ? * MON-FRI";
    public static final String RELATIVE_TIME = "relativeTime";
    public static final String REGULAR_TIME = "regularTime";
    @Autowired
    ReminderRepository reminderRepository;
    @Autowired
    EntityIdentityService entityIdentityService;
    @Resource
    private IDataDefinitionRuntimeController tbRtCtl;
    @Resource
    private IEntityViewRunTimeController viewRtCtl;
    @Autowired
    EntityHelper entityHelper;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private EntityQueryManager entityQueryManager;
    @Autowired
    private SchedulerImpl schedulerServiceImpl;
    @Autowired
    private DeSetTimeProvide deSetTimeProvide;
    @Autowired
    private ReminderJobInfoDao reminderJobInfoDao;
    @Autowired
    private TimeCommon timeCommon;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private Scheduler scheduler;

    public int asyncBaseJob(CreateReminderCommand command, Map<List<String>, Map<List<String>, String>> contents, ParticipantHelper participantHelper) throws JQException, Exception {
        if (command.getShowSendTime().equals(RELATIVE_TIME)) {
            int sendTime = command.getSendTime();
            Boolean befored = this.deadlineBefore(sendTime);
            try {
                ManualReminder reminder = new ManualReminder();
                BeanConverter.convert(command, (Reminder)reminder);
                String formSchemeKey = command.getFormSchemeId();
                String period = command.getPeriod();
                List<String> unitIds = command.getUnitIds();
                Map<String, String> unitDeadline = this.unitDeadline(formSchemeKey, unitIds, period);
                List<String> userIds = null;
                Map<String, List<String>> sortUnit = this.sortUnit(unitDeadline);
                EntityViewDefine parentEntityViewDefine = this.entityHelper.getParentEntityViewDefine(command.getFormSchemeId());
                DimensionValueSet buildDimension = this.entityHelper.buildDimension(command.getFormSchemeId(), unitIds);
                IEntityTable entityQuerySet = this.entityQueryManager.entityQuerySet(parentEntityViewDefine, buildDimension, command.getFormSchemeId());
                List allRows = entityQuerySet.getAllRows();
                for (Map.Entry<String, List<String>> deadlineObj : sortUnit.entrySet()) {
                    String endTime = deadlineObj.getKey();
                    List<String> unitList = deadlineObj.getValue();
                    if (command.getRoleType() == 8) {
                        userIds = participantHelper.collectUserId(command.getViewKey(), command.getFormSchemeId(), unitList, command.getRoleIds(), allRows);
                    } else if (command.getRoleType() == 1) {
                        userIds = participantHelper.collectCommitUserId(command.getViewKey(), command.getFormSchemeId(), unitList, command, allRows);
                    } else if (command.getRoleType() == 0) {
                        userIds = participantHelper.collectUserId(command.getViewKey(), command.getFormSchemeId(), unitList, null, allRows);
                    } else {
                        throw new Exception("\u4e0d\u652f\u6301\u7684\u89d2\u8272\u8303\u56f4\u7c7b\u578b:" + command.getRoleType());
                    }
                    reminder.setUserIds(userIds);
                    this.excuteScheduledJob(command, endTime, befored, reminder, contents);
                }
            }
            catch (Exception e) {
                LogHelper.error((String)"\u50ac\u62a5\u7ba1\u7406", (String)"\u50ac\u62a5\u5931\u8d25\u539f\u56e0\uff1a", (String)("\u539f\u56e0:" + e.getMessage() + ", \u50ac\u62a5id\uff1a " + command.getId()));
                log.error(e.getMessage(), e);
            }
            return 0;
        }
        if (command.getShowSendTime().equals(REGULAR_TIME)) {
            ManualReminder reminder = new ManualReminder();
            BeanConverter.convert(command, (Reminder)reminder);
            String sendTime = command.getRegularTime();
            this.excuteScheduledJob(command, sendTime, reminder, contents);
            return 0;
        }
        return 0;
    }

    private void excuteScheduledJob(CreateReminderCommand command, String sendTime, ManualReminder reminder, Map<List<String>, Map<List<String>, String>> contents) throws Exception {
        ScheduledJob scheduledJob = null;
        Object startTime = null;
        Date cornExpress = null;
        String endTime = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = formatter.parse(sendTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int minute = calendar.get(12);
        int hour = calendar.get(11);
        if (command.getFrequencyMode() == 0) {
            cornExpress = this.date(sendTime);
            if (cornExpress.compareTo(new Date()) < 0) {
                endTime = formatter.format(new Date());
            }
            scheduledJob = this.buildScheduledJob(CronUtils.getCron(cornExpress), sendTime, sendTime, contents, reminder);
        } else if (command.getFrequencyMode() == 9) {
            String CORN_REGU_DAY = "0 m h * * ?";
            CORN_REGU_DAY = CORN_REGU_DAY.replace("m", Integer.toString(minute));
            CORN_REGU_DAY = CORN_REGU_DAY.replace("h", Integer.toString(hour));
            scheduledJob = this.buildScheduledJob(CORN_REGU_DAY, sendTime, endTime, contents, reminder);
        } else if (command.getFrequencyMode() == 2) {
            String CORN_REGU_MONTH = "0 m h d * ?";
            CORN_REGU_MONTH = CORN_REGU_MONTH.replace("m", Integer.toString(minute));
            CORN_REGU_MONTH = CORN_REGU_MONTH.replace("h", Integer.toString(hour));
            CORN_REGU_MONTH = CORN_REGU_MONTH.replace("d", command.getRegularDay());
            scheduledJob = this.buildScheduledJob(CORN_REGU_MONTH, sendTime, endTime, contents, reminder);
        } else if (command.getFrequencyMode() == 1) {
            String CORN_REGU_YEAR = "0 m h d M ? *";
            CORN_REGU_YEAR = CORN_REGU_YEAR.replace("m", Integer.toString(minute));
            CORN_REGU_YEAR = CORN_REGU_YEAR.replace("h", Integer.toString(hour));
            CORN_REGU_YEAR = CORN_REGU_YEAR.replace("d", command.getRegularDay());
            CORN_REGU_YEAR = CORN_REGU_YEAR.replace("M", command.getRegularMonth());
            scheduledJob = this.buildScheduledJob(CORN_REGU_YEAR, sendTime, endTime, contents, reminder);
        } else if (command.getFrequencyMode() == 4) {
            String CORN_REGU_WEEK = "0 m h ? * w";
            CORN_REGU_WEEK = CORN_REGU_WEEK.replace("m", Integer.toString(minute));
            CORN_REGU_WEEK = CORN_REGU_WEEK.replace("h", Integer.toString(hour));
            CORN_REGU_WEEK = CORN_REGU_WEEK.replace("w", command.getRegularWeek());
            scheduledJob = this.buildScheduledJob(CORN_REGU_WEEK, sendTime, endTime, contents, reminder);
        }
        if (command.getValidEndTime() == null || command.getValidEndTime().equals(CORN_DATA) || command.getValidEndTime().compareTo(scheduledJob.getStarTime()) >= 0 || command.getFrequencyMode() == 0) {
            this.schedulerServiceImpl.asyncBaseJob(scheduledJob, command);
        }
        this.updateReguLarAutoReminder(command, sendTime);
    }

    private void excuteScheduledJob(CreateReminderCommand command, String endTime, boolean befored, ManualReminder reminder, Map<List<String>, Map<List<String>, String>> contents) {
        try {
            String startTime = null;
            Date cornExpress = null;
            String sendTime = reminder.getRegularTime();
            if (befored) {
                startTime = this.moveFormDate(befored, endTime, command.getDayBeforeDeadline(), reminder.getRegularTime());
            } else {
                startTime = this.moveFormDate(befored, endTime, command.getDayAfterDeadline(), reminder.getRegularTime());
                endTime = AutoScheduleImpl.date(startTime, 1);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = formatter.parse(startTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            ScheduledJob scheduledJob = null;
            cornExpress = this.date(startTime);
            if (cornExpress.compareTo(new Date()) < 0) {
                endTime = formatter.format(new Date());
            }
            scheduledJob = this.buildScheduledJob(CronUtils.getCron(cornExpress), startTime, endTime, contents, reminder);
            if (command.getValidEndTime() == null || command.getValidEndTime().equals(CORN_DATA) || command.getValidEndTime().compareTo(scheduledJob.getStarTime()) >= 0) {
                this.schedulerServiceImpl.asyncBaseJob(scheduledJob, command);
            }
            this.updateAutoReminder(command, startTime, endTime);
        }
        catch (Exception e) {
            LogHelper.error((String)"\u50ac\u62a5\u7ba1\u7406", (String)"\u50ac\u62a5\u5931\u8d25\u539f\u56e0\uff1a", (String)("\u539f\u56e0:" + e.getMessage() + ", \u50ac\u62a5id\uff1a " + command.getId()));
            log.error(e.getMessage(), e);
        }
    }

    private void updateAutoReminder(CreateReminderCommand command, String startTime, String endTime) {
        int status = this.compareTime(startTime, endTime, command);
        if (1 == status) {
            this.reminderRepository.updateExecuteStatus(command.getId(), status);
        } else if (2 == status) {
            this.reminderRepository.updateExecuteStatus(command.getId(), status);
        } else if (4 == status) {
            this.reminderRepository.updateExecuteStatus(command.getId(), status);
        } else if (3 == status) {
            this.reminderRepository.updateExecuteStatus(command.getId(), status);
        }
        this.reminderRepository.updateRegularTime(command.getId(), startTime);
    }

    private void updateReguLarAutoReminder(CreateReminderCommand command, String startTime) {
        String remId = command.getId();
        int status = this.compareTime(command, startTime);
        if (1 == status) {
            this.reminderRepository.updateExecuteStatus(remId, status);
        } else if (2 == status) {
            this.reminderRepository.updateExecuteStatus(remId, status);
        } else if (4 == status) {
            this.reminderRepository.updateExecuteStatus(remId, status);
        } else if (3 == status) {
            this.reminderRepository.updateExecuteStatus(remId, status);
        }
    }

    private int compareTime(String startTime, String endTime, CreateReminderCommand command) {
        Calendar instance = Calendar.getInstance();
        Date currTime = instance.getTime();
        String validEndTime = command.getValidEndTime();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (endTime != null && startTime != null) {
                Date startDate = df.parse(startTime);
                Date endDate = df2.parse(endTime);
                if (endDate.before(currTime) || validEndTime != null && !validEndTime.equals(CORN_DATA) && validEndTime.compareTo(startTime) < 0) {
                    return 2;
                }
                if (startDate.before(currTime) && currTime.before(endDate)) {
                    return 1;
                }
                if (currTime.before(startDate)) {
                    return 4;
                }
            }
        }
        catch (Exception e) {
            LogHelper.error((String)"\u50ac\u62a5\u7ba1\u7406", (String)"\u50ac\u62a5\u5931\u8d25\u539f\u56e0\uff1a", (String)("\u539f\u56e0:" + e.getMessage() + ", \u50ac\u62a5id\uff1a " + command.getId()));
            log.error(e.getMessage(), e);
        }
        return 3;
    }

    private int compareTime(CreateReminderCommand command, String sendTime) {
        String validStartTime = command.getValidStartTime();
        String validEndTime = command.getValidEndTime();
        Calendar instance = Calendar.getInstance();
        Date currTime = instance.getTime();
        String periodType = command.getPeriodType();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currTime);
        try {
            SimpleDateFormat df2 = null;
            SimpleDateFormat df3 = null;
            if ("MONTH".equals(periodType)) {
                df2 = new SimpleDateFormat("yyyy-MM");
                df3 = new SimpleDateFormat("yyyy-MM");
            }
            if ("YEAR".equals(periodType)) {
                df2 = new SimpleDateFormat("yyyy");
                df3 = new SimpleDateFormat("yyyy");
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date sendDate = df.parse(sendTime);
            if (sendTime != null) {
                if (command.getFrequencyMode() == 0 && currTime.after(sendDate) || validEndTime != null && !validEndTime.equals(CORN_DATA) && validEndTime.compareTo(sendTime) < 0 && command.getFrequencyMode() != 0) {
                    return 2;
                }
                if (currTime.before(sendDate)) {
                    return 4;
                }
                if (currTime.after(sendDate) && command.getFrequencyMode() != 0) {
                    return 1;
                }
            }
        }
        catch (Exception e) {
            LogHelper.error((String)"\u50ac\u62a5\u7ba1\u7406", (String)"\u50ac\u62a5\u5931\u8d25\u539f\u56e0\uff1a", (String)(e.getMessage() + ", \u50ac\u62a5id\uff1a " + command.getId()));
            log.error(e.getMessage(), e);
        }
        return 3;
    }

    private ScheduledJob buildScheduledJob(String cornExpression, String startTime, String endTime, Map<List<String>, Map<List<String>, String>> content, ManualReminder reminder) {
        if (startTime == null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startTime = formatter.format(new Date());
        }
        String identityId = NpContextHolder.getContext().getIdentityId();
        NpContextIdentity contextIdentity = new NpContextIdentity();
        contextIdentity.setId(identityId);
        ContextUser user = NpContextHolder.getContext().getUser();
        ScheduledJob scheduledJob = new ScheduledJob(GUID.randomID().toString(), GUID.randomID().toString(), cornExpression, endTime, startTime);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", reminder.getId());
        map.put("userIds", reminder.getUserIds());
        map.put("content", content);
        map.put("method", reminder.getHandleMethod());
        map.put("formSchemeKey", reminder.getFormSchemeId());
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("cc", reminder.getCcParticipants());
        map.put("taskId", reminder.getTaskId());
        map.put("period", reminder.getPeriod());
        map.put("contextIdentity", contextIdentity);
        map.put("contextUser", user);
        map.put("originalContent", reminder.getOriginalContent());
        map.put("workFlowType", reminder.getWorkFlowType());
        map.put("groupRange", reminder.getGroupRange());
        map.put("formRange", reminder.getFormRange());
        scheduledJob.setDataMap(map);
        if (reminder.isEdit()) {
            this.deleteQuick(reminder.getId());
        }
        this.saveJob(scheduledJob, reminder.getId());
        return scheduledJob;
    }

    private void deleteQuick(String remId) {
        try {
            ArrayList<String> ids = new ArrayList<String>();
            List<ReminderJobEntity> allById = this.reminderJobInfoDao.getAllById(remId);
            if (allById.size() > 0) {
                for (ReminderJobEntity reminderJob : allById) {
                    TriggerKey triggerKey = TriggerKey.triggerKey((String)reminderJob.getJobId(), (String)reminderJob.getJobGroupId());
                    this.scheduler.pauseTrigger(triggerKey);
                    this.scheduler.unscheduleJob(triggerKey);
                    this.scheduler.deleteJob(JobKey.jobKey((String)reminderJob.getId(), (String)reminderJob.getJobGroupId()));
                    ids.add(reminderJob.getId());
                }
            }
            this.reminderJobInfoDao.deleteById(ids);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void saveJob(ScheduledJob scheduledJob, String reminderId) {
        ReminderJobEntity reminderJobEntity = new ReminderJobEntity();
        reminderJobEntity.setId(GUID.randomID().toString());
        reminderJobEntity.setJobId(scheduledJob.getId());
        reminderJobEntity.setJobGroupId(scheduledJob.getGroupId());
        reminderJobEntity.setJobCronExpression(scheduledJob.getCronExpression());
        reminderJobEntity.setJobParam(scheduledJob.getDataMap());
        reminderJobEntity.setReminderId(reminderId);
        this.reminderJobInfoDao.create(reminderJobEntity);
    }

    private boolean deadlineBefore(int sendTime) {
        boolean befored = false;
        if (sendTime == 1) {
            befored = true;
        } else if (sendTime == 0) {
            befored = false;
        }
        return befored;
    }

    private String moveFormDate(boolean befored, String currTime, int n, String sendTime) {
        String dateString = null;
        try {
            int i;
            SimpleDateFormat formatterToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            Date date = formatterToDate.parse(currTime, pos);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (befored) {
                for (i = 0; i < n; ++i) {
                    calendar.add(5, -1);
                    if (!AutoScheduleImpl.checkHoliday(calendar)) continue;
                    --i;
                }
            } else {
                for (i = 0; i < n; ++i) {
                    calendar.add(5, 1);
                    if (!AutoScheduleImpl.checkHoliday(calendar)) continue;
                    --i;
                }
            }
            String[] timeContent = sendTime.split(":");
            calendar.set(11, Integer.parseInt(timeContent[0]));
            calendar.set(12, Integer.parseInt(timeContent[1]));
            calendar.set(13, Integer.parseInt(timeContent[2]));
            Date time = calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateString = formatter.format(time);
            return dateString;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return dateString;
        }
    }

    private static String date(String currTime, int n) {
        SimpleDateFormat formatterToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatterToDate.parse(currTime, pos);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, n);
        Date time = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(time);
        return dateString;
    }

    private Date date(String currTime) {
        SimpleDateFormat formatterToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatterToDate.parse(currTime, pos);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTime();
    }

    private Map<String, String> unitDeadline(String formSchemeKey, List<String> unitIds, String period) {
        HashMap<String, String> deadTimeMap = new HashMap<String, String>();
        if (unitIds != null && unitIds.size() > 0) {
            for (String unitId : unitIds) {
                DimensionValueSet dim = new DimensionValueSet();
                dim.setValue("DATATIME", (Object)period);
                dim.setValue(this.entityHelper.getMainDimName(formSchemeKey), (Object)unitId);
                Date queryTime = this.deSetTimeProvide.queryTime(formSchemeKey, dim);
                if (queryTime == null) continue;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = sdf.format(queryTime);
                deadTimeMap.put(unitId, format);
            }
        }
        return deadTimeMap;
    }

    private Map<String, List<String>> sortUnit(Map<String, String> deadTimeMap) {
        HashMap<String, List<String>> unitMap = new HashMap<String, List<String>>();
        try {
            for (Map.Entry<String, String> timeMap : deadTimeMap.entrySet()) {
                String unitId = timeMap.getKey();
                String time = timeMap.getValue();
                ArrayList<String> list = (ArrayList<String>)unitMap.get(time);
                if (list == null) {
                    list = new ArrayList<String>();
                    list.add(unitId);
                    unitMap.put(time, list);
                    continue;
                }
                list.add(unitId);
                unitMap.put(time, list);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return unitMap;
    }

    public static boolean checkHoliday(Calendar calendar) throws Exception {
        return calendar.get(7) == 1 || calendar.get(7) == 7;
    }
}

