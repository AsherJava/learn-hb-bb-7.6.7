/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IFormulaRunner
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionState
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.env.ExectuteFormula
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.authority.user.service.NvwaUserService
 *  com.jiuqi.nvwa.authority.user.vo.UserDTORes
 *  com.jiuqi.nvwa.authority.user.vo.UserQueryReq
 *  com.jiuqi.util.StringUtils
 *  org.apache.ibatis.jdbc.SQL
 *  org.quartz.JobKey
 *  org.quartz.Scheduler
 *  org.quartz.TriggerKey
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.reminder.scheduler;

import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.env.ExectuteFormula;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.reminder.bean.DimensionCacheKey;
import com.jiuqi.nr.reminder.bean.ReminderBatchConditionMonitor;
import com.jiuqi.nr.reminder.bean.ReminderContext;
import com.jiuqi.nr.reminder.bean.ReminderJobEntity;
import com.jiuqi.nr.reminder.impl.ReminderEntityFmlExecEnvironment;
import com.jiuqi.nr.reminder.impl.ReminderVariableExectuteFormula;
import com.jiuqi.nr.reminder.infer.ReminderJobInfoDao;
import com.jiuqi.nr.reminder.infer.ReminderRepository;
import com.jiuqi.nr.reminder.internal.ManualReminder;
import com.jiuqi.nr.reminder.internal.Reminder;
import com.jiuqi.nr.reminder.scheduler.ReminderJob;
import com.jiuqi.nr.reminder.scheduler.SendMsg;
import com.jiuqi.nr.reminder.untils.EntityHelper;
import com.jiuqi.nr.reminder.untils.EntityQueryManager;
import com.jiuqi.nvwa.authority.user.service.NvwaUserService;
import com.jiuqi.nvwa.authority.user.vo.UserDTORes;
import com.jiuqi.nvwa.authority.user.vo.UserQueryReq;
import com.jiuqi.util.StringUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.ibatis.jdbc.SQL;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class ScheduledJob
extends ReminderJob {
    private static final Logger log = LoggerFactory.getLogger(ScheduledJob.class);
    @Autowired
    ReminderRepository reminderRepository;
    private String id;
    private String groupId;
    private String cornExpression;
    private String startTime;
    private String endTime;
    private String executeTime;
    @Autowired
    private NamedParameterJdbcTemplate template;
    private static final String COL_REM_ID = "rem_id";
    private static final String COL_EXECUTENUMS = "executeNums";
    private static final String COL_REGULAR_TIME = "regularTime";
    private static final String COL_NEXTSEND_TIME = "nextSendTime";
    private static final String COL_FREQUENCY_MODE = "frequencyMode";
    private static final String COL_SHOW_SEND_TIME = "showSendTime";
    private static final String COL_VALID_START_TIME = "validStartTime";
    private static final String COL_VALID_END_TIME = "validEndTime";
    private static final String COL_PERIOD_TYPE = "periodType";
    private static final String TABLE_REMINDER = "nr_reminder";
    private static final String COL_REPEAT_TIME = "repeat_mode";
    private static final String TABLE_REMINDER_UNIT = "nr_reminder_unit";
    private static final String TABLE_REMINDER_FORM = "nr_reminder_form";
    private static final String COL_UNIT_ID = "unit_id";
    private static final String COL_FORMKEY = "formKey";
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ReminderJobInfoDao reminderJobInfoDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    protected DefinitionAuthorityProvider definitionAuthorityProvider;
    @Autowired
    private IDataentryFlowService dataFlowService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private NpApplication npApplication;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    EntityHelper entityHelper;
    @Autowired
    private EntityQueryManager entityQueryManager;
    @Autowired
    EntityIdentityService entityIdentityService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private NvwaUserService nvwaUserService;
    @Autowired
    private IDataDefinitionRuntimeController tbRtCtl;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledJob.class);
    @Autowired
    private SendMsg sendMsg;

    public ScheduledJob() {
    }

    public void setRunTimeViewController(IRunTimeViewController runTimeViewController) {
        this.runTimeViewController = runTimeViewController;
    }

    public void setEntityMetaService(IEntityMetaService entityMetaService) {
        this.entityMetaService = entityMetaService;
    }

    public void setDataAccessProvider(IDataAccessProvider dataAccessProvider) {
        this.dataAccessProvider = dataAccessProvider;
    }

    public void setEntityViewRunTimeController(IEntityViewRunTimeController entityViewRunTimeController) {
        this.entityViewRunTimeController = entityViewRunTimeController;
    }

    public void setTbRtCtl(IDataDefinitionRuntimeController tbRtCtl) {
        this.tbRtCtl = tbRtCtl;
    }

    public ScheduledJob(String id, String groupId, String cornExpression, String endTime, String startTime) {
        this.id = id;
        this.groupId = groupId;
        this.cornExpression = cornExpression;
        this.endTime = endTime;
        this.startTime = startTime;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getGroupId() {
        return this.groupId;
    }

    @Override
    public String getCronExpression() {
        return this.cornExpression;
    }

    @Override
    public String getEndTime() {
        return this.endTime;
    }

    @Override
    public String getStarTime() {
        return this.startTime;
    }

    @Override
    public String getExecuteTime() {
        return this.executeTime;
    }

    @Override
    public void sendMsg(List<Reminder> reminder, NpContextImpl context) {
        try {
            this.sendMsg.send(reminder, context);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void updateState(String reminderId, String startTime, String endTime) {
        this.updateAutoReminder(reminderId, startTime, endTime);
    }

    private void updateAutoReminder(String remId, String startTime, String endTime) {
        int status = this.compareTime(startTime, endTime, remId);
        if (2 == status) {
            this.reminderRepository.updateExecuteStatus(remId, status);
        } else if (3 == status) {
            this.reminderRepository.updateExecuteStatus(remId, status);
        }
    }

    private int compareTime(String startTime, String endTime, String remId) {
        Calendar instance = Calendar.getInstance();
        Date currTime = instance.getTime();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (endTime != null && startTime != null) {
                Date startDate = df.parse(startTime);
                Date endDate = df2.parse(endTime);
                if (startDate.before(currTime)) {
                    return 2;
                }
            }
        }
        catch (Exception e) {
            LogHelper.error((String)"\u50ac\u62a5\u7ba1\u7406", (String)"\u50ac\u62a5\u5931\u8d25\u539f\u56e0\uff1a", (String)("\u539f\u56e0:" + e.getMessage() + ", \u50ac\u62a5id\uff1a " + remId));
            log.error(e.getMessage(), e);
        }
        return 3;
    }

    @Override
    public Reminder find(String remId) {
        return this.reminderRepository.find(remId);
    }

    @Override
    public List<String> findUnitIds(String remId) {
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT(COL_UNIT_ID)).FROM(TABLE_REMINDER_UNIT)).WHERE("rem_id=:rem_id")).toString();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(COL_REM_ID, (Object)remId);
        return this.template.query(sql, (SqlParameterSource)parameterSource, (rs, rowNum) -> rs.getString(COL_UNIT_ID));
    }

    @Override
    public List<String> findFormKeys(String remId) {
        MapSqlParameterSource parameterSource;
        ArrayList<String> result = new ArrayList<String>();
        List formKeys = new ArrayList();
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT(COL_FORMKEY)).FROM(TABLE_REMINDER_FORM)).WHERE(new String[]{"rem_id=:rem_id", "formKey is not null"})).toString();
        formKeys = this.template.query(sql, (SqlParameterSource)(parameterSource = new MapSqlParameterSource().addValue(COL_REM_ID, (Object)remId)), (rs, rowNum) -> rs.getString(COL_FORMKEY));
        if (formKeys != null && !formKeys.isEmpty()) {
            String[] formKeyArray;
            String temp = (String)formKeys.get(0);
            for (String formKey : formKeyArray = temp.split(";")) {
                result.add(formKey);
            }
        }
        return result;
    }

    private Reminder create(ResultSet rs) throws SQLException {
        ManualReminder reminder = new ManualReminder();
        reminder.setExecuteNums(rs.getInt(COL_EXECUTENUMS));
        reminder.setNextSendTime(rs.getString(COL_NEXTSEND_TIME));
        reminder.setRegularTime(rs.getString(COL_REGULAR_TIME));
        reminder.setFrequencyMode(rs.getInt(COL_FREQUENCY_MODE));
        reminder.setShowSendTime(rs.getString(COL_SHOW_SEND_TIME));
        reminder.setValidStartTime(rs.getString(COL_VALID_START_TIME));
        reminder.setValidEndTime(rs.getString(COL_VALID_END_TIME));
        reminder.setPeriodType(rs.getString(COL_PERIOD_TYPE));
        return reminder;
    }

    @Override
    public String nextTime(String currTime, int frequencyMode) {
        int oldMonth;
        SimpleDateFormat formatterToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatterToDate.parse(currTime, pos);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (frequencyMode == 9) {
            calendar.add(5, 1);
        } else if (frequencyMode == 1) {
            oldMonth = calendar.get(2);
            int oldDay = calendar.get(5);
            calendar.add(1, 1);
            calendar.set(2, oldMonth);
            calendar.set(5, oldDay);
        } else if (frequencyMode == 2) {
            oldMonth = calendar.get(5);
            calendar.add(2, 1);
            calendar.set(5, oldMonth);
        } else if (frequencyMode == 4) {
            calendar.add(7, 7);
        }
        Date time = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(time);
        return dateString;
    }

    public String nextTimeRepeat(String currTime) {
        SimpleDateFormat formatterToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatterToDate.parse(currTime, pos);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, 1);
        Date time = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(time);
        return dateString;
    }

    @Override
    public void updateExecuteNums(Integer nums, String nextTime, String remId) {
        String sql = ((SQL)((SQL)((SQL)((SQL)new SQL().UPDATE(TABLE_REMINDER)).SET("executeNums=:executeNums")).SET("nextSendTime=:nextSendTime")).WHERE("rem_id=:rem_id")).toString();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue(COL_EXECUTENUMS, (Object)nums).addValue(COL_NEXTSEND_TIME, (Object)nextTime).addValue(COL_REM_ID, (Object)remId);
        this.template.update(sql, (SqlParameterSource)parameterSource);
    }

    @Override
    public void updateReguLarAutoReminder(String remId, String sendTime, String nextTime, String validStartTime, String validEndTime, int frequencyMode, String periodType) {
        int status = this.compareTime(sendTime, nextTime, validStartTime, validEndTime, frequencyMode, periodType, remId);
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

    @Override
    public void deleteQuick(String remId) {
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

    private int compareTime(String sendTime, String nextTime, String validStartTime, String validEndTime, int frequencyMode, String periodType, String remId) {
        Calendar instance = Calendar.getInstance();
        Date currTime = instance.getTime();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat df2 = null;
            SimpleDateFormat df3 = null;
            if ("MONTH".equals(periodType)) {
                df2 = new SimpleDateFormat("yyyy-MM");
                df3 = new SimpleDateFormat("yyyy-MM");
            } else if ("YEAR".equals(periodType)) {
                df2 = new SimpleDateFormat("yyyy");
                df3 = new SimpleDateFormat("yyyy");
            } else {
                df2 = new SimpleDateFormat("yyyy-MM-dd");
                df3 = new SimpleDateFormat("yyyy-MM-dd");
            }
            if (sendTime != null) {
                Date sendDate = df.parse(sendTime);
                Date validStartDate = null;
                Date validEndDate = null;
                Date nextDate = null;
                if (validStartTime != null) {
                    validStartDate = df2.parse(validStartTime);
                }
                if (validEndTime != null) {
                    validEndDate = df3.parse(validEndTime);
                }
                if (nextTime != null) {
                    nextDate = df.parse(nextTime);
                }
                if (frequencyMode == 0 && sendDate.before(currTime)) {
                    return 2;
                }
                if (validEndDate != null && validEndDate.before(nextDate)) {
                    this.deleteQuick(remId);
                    return 2;
                }
                if (sendDate.before(currTime) && frequencyMode != 0) {
                    return 1;
                }
                if (currTime.before(sendDate)) {
                    return 4;
                }
            }
        }
        catch (Exception e) {
            LogHelper.error((String)"\u50ac\u62a5\u7ba1\u7406", (String)"\u50ac\u62a5\u5931\u8d25\u539f\u56e0\uff1a", (String)("\u539f\u56e0:" + e.getMessage() + ", \u50ac\u62a5id\uff1a " + remId));
            log.error(e.getMessage(), e);
        }
        this.deleteQuick(remId);
        return 3;
    }

    @Override
    protected Map<String, Set<String>> getCanFormGroupUploadIdentityMaps(List<String> userIds, String workFlowType, String groupRange, String formRange, String formSchemeKey, List<String> formKeys, NpContextIdentity contextIdentity, ContextUser user) {
        HashMap<String, Set<String>> maps;
        block11: {
            NpContextImpl context;
            block9: {
                block10: {
                    context = new NpContextImpl();
                    context.setUser(user);
                    context.setIdentity((ContextIdentity)contextIdentity);
                    maps = new HashMap<String, Set<String>>();
                    if (!workFlowType.equals(WorkFlowType.GROUP.name())) break block9;
                    if (!"1".equals(groupRange)) break block10;
                    AtomicReference allFormGroups = new AtomicReference();
                    this.npApplication.runAsContext((NpContext)context, () -> allFormGroups.set(this.runTimeAuthViewController.getAllFormGroupsInFormScheme(formSchemeKey)));
                    if (allFormGroups.get() == null || ((List)allFormGroups.get()).size() == 0) break block11;
                    for (FormGroupDefine allFormGroup : (List)allFormGroups.get()) {
                        HashSet<String> userSet = new HashSet<String>(userIds);
                        Set canUserId = this.definitionAuthorityProvider.getCanFormGroupUploadIdentityKeys(allFormGroup.getKey());
                        userSet.retainAll(canUserId);
                        maps.put(allFormGroup.getKey(), userSet);
                    }
                    break block11;
                }
                if (formKeys == null || formKeys.size() == 0) break block11;
                for (String filterGroupKey : formKeys) {
                    HashSet<String> userSet = new HashSet<String>(userIds);
                    Set canUserId = this.definitionAuthorityProvider.getCanFormGroupUploadIdentityKeys(filterGroupKey);
                    userSet.retainAll(canUserId);
                    maps.put(filterGroupKey, userSet);
                }
                break block11;
            }
            if (workFlowType.equals(WorkFlowType.FORM.name())) {
                AtomicReference allForms = new AtomicReference();
                if ("1".equals(formRange)) {
                    this.npApplication.runAsContext((NpContext)context, () -> {
                        try {
                            allForms.set(this.runTimeAuthViewController.queryAllFormDefinesByFormScheme(formSchemeKey));
                        }
                        catch (Exception e) {
                            log.error(e.getMessage(), e);
                        }
                    });
                    List defines = (List)allForms.get();
                    if (defines == null) {
                        return maps;
                    }
                    for (FormDefine define : defines) {
                        HashSet<String> userSet = new HashSet<String>(userIds);
                        Set canFormUserId = this.definitionAuthorityProvider.getCanFormUploadIdentityKeys(define.getKey());
                        userSet.retainAll(canFormUserId);
                        maps.put(define.getKey(), userSet);
                    }
                } else if (formKeys != null && formKeys.size() != 0) {
                    for (String formKey : formKeys) {
                        HashSet<String> userSet = new HashSet<String>(userIds);
                        Set canUserId = this.definitionAuthorityProvider.getCanFormUploadIdentityKeys(formKey);
                        userSet.retainAll(canUserId);
                        maps.put(formKey, userSet);
                    }
                }
            }
        }
        return maps;
    }

    @Override
    protected Map<String, Map<String, ArrayList<String>>> filterUserIdByWorkFlowType(List<String> userIds, String period, List<String> unitIds, String workFlowType, String formSchemeKey, String taskId, List<String> formKeys, NpContextIdentity contextIdentity, ContextUser user, Map<String, Set<String>> identityMaps) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        LinkedHashMap<String, DimensionValue> dimMap = new LinkedHashMap<String, DimensionValue>();
        DimensionValue dv = new DimensionValue();
        dv.setName("DATATIME");
        dv.setValue(period);
        dimMap.put("DATATIME", dv);
        NpContextImpl context = new NpContextImpl();
        context.setUser(user);
        context.setIdentity((ContextIdentity)contextIdentity);
        HashMap mapAllUnitIds = new HashMap();
        HashMap mapAllFormKeys = new HashMap();
        ArrayList allFilterUnitIds = new ArrayList();
        ArrayList allFilterFormKeys = new ArrayList();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String dw = formScheme.getDw();
        if (dw == null) {
            logger.warn("\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848\uff0c\u50ac\u62a5\u7ed3\u675f {}", (Object)dw);
        }
        String entityName = this.entityMetaService.getDimensionName(dw);
        HashMap mapUserForUnitIds = new HashMap();
        HashMap mapUserForFormKeys = new HashMap();
        HashMap<String, Map<String, ArrayList<String>>> mapUserForUnitIdsAndFormKeys = new HashMap<String, Map<String, ArrayList<String>>>();
        AtomicReference entityIds = new AtomicReference();
        if (userIds.size() != 0 && !userIds.isEmpty()) {
            DimensionValueSet dim = new DimensionValueSet();
            for (String userId : userIds) {
                AtomicBoolean allUpLoadAtomic = new AtomicBoolean(true);
                ArrayList unitIdList = new ArrayList();
                ArrayList filterListKeys = new ArrayList();
                this.npApplication.runAsContext((NpContext)context, () -> {
                    UserQueryReq userQueryReq = new UserQueryReq();
                    userQueryReq.setUserId(userId);
                    ArrayList<String> orgCodes = new ArrayList<String>();
                    try {
                        List users = this.nvwaUserService.queryUser(userQueryReq);
                        for (UserDTORes oneUser : users) {
                            orgCodes.add(oneUser.getOrgCode());
                        }
                    }
                    catch (JQException e) {
                        log.error(e.getMessage(), e);
                    }
                    entityIds.set(new ArrayList(orgCodes));
                    ((ArrayList)entityIds.get()).retainAll(unitIds);
                });
                for (String entity : (ArrayList)entityIds.get()) {
                    DataEntryParam param = new DataEntryParam();
                    dim.setValue(entityName, (Object)entity);
                    dim.setValue("DATATIME", (Object)period);
                    for (String filterKey : formKeys) {
                        Set<String> canUserIds = identityMaps.get(filterKey);
                        if (canUserIds == null || !canUserIds.contains(userId)) continue;
                        AtomicBoolean ifCondition = new AtomicBoolean(false);
                        if (!formKeys.isEmpty() && !workFlowType.equals(WorkFlowType.ENTITY.name())) {
                            dim.setValue("FORMID", (Object)filterKey);
                            ArrayList<String> formKeyList = new ArrayList<String>();
                            formKeyList.add(filterKey);
                            if (workFlowType.equals(WorkFlowType.GROUP.name())) {
                                param.setGroupKey(filterKey);
                                param.setGroupKeys(formKeyList);
                            } else if (workFlowType.equals(WorkFlowType.FORM.name())) {
                                param.setFormKey(filterKey);
                                param.setFormKeys(formKeyList);
                            }
                        }
                        param.setDim(dim);
                        param.setFormSchemeKey(formSchemeKey);
                        this.npApplication.runAsContext((NpContext)context, () -> {
                            ActionStateBean actionState = this.dataFlowService.queryReportState(param);
                            if (!actionState.getCode().equals("UPLOADED") && !actionState.getCode().equals("CONFIRMED")) {
                                allUpLoadAtomic.set(false);
                                if (workFlowType.equals(WorkFlowType.GROUP.name())) {
                                    FormGroupDefine selectGroup = null;
                                    try {
                                        selectGroup = this.runTimeAuthViewController.queryFormGroup(filterKey);
                                    }
                                    catch (Exception e) {
                                        log.error(e.getMessage(), e);
                                    }
                                    List formDefines = null;
                                    try {
                                        formDefines = this.runTimeViewController.getAllFormsInGroupWithoutOrder(selectGroup.getKey(), false);
                                    }
                                    catch (Exception e) {
                                        log.error(e.getMessage(), e);
                                    }
                                    if (!this.filterByGroupConditionResult(dimensionValueSet, formSchemeKey, taskId, selectGroup, entity, dimMap)) {
                                        ifCondition.set(false);
                                    } else {
                                        try {
                                            ifCondition.set(this.filterByFromConditionInGroup(dimensionValueSet, formSchemeKey, taskId, formDefines, entity, dimMap));
                                        }
                                        catch (Exception e) {
                                            log.error(e.getMessage(), e);
                                        }
                                    }
                                } else if (workFlowType.equals(WorkFlowType.FORM.name())) {
                                    FormDefine selectForm = this.runTimeAuthViewController.queryFormById(filterKey);
                                    ifCondition.set(this.filterByFormConditionResult(dimensionValueSet, formSchemeKey, taskId, selectForm, entity, dimMap));
                                }
                                if (!filterListKeys.contains(filterKey) && ifCondition.get()) {
                                    filterListKeys.add(filterKey);
                                    allFilterFormKeys.add(filterKey);
                                }
                                if (!unitIdList.contains(entity) && ifCondition.get()) {
                                    unitIdList.add(entity);
                                    allFilterUnitIds.add(entity);
                                }
                            }
                        });
                    }
                }
                if (allUpLoadAtomic.get()) continue;
                mapUserForUnitIds.put(userId, unitIdList);
                mapUserForFormKeys.put(userId, filterListKeys);
            }
        }
        mapUserForUnitIdsAndFormKeys.put("formKeys", mapUserForFormKeys);
        mapUserForUnitIdsAndFormKeys.put("unitIds", mapUserForUnitIds);
        mapAllUnitIds.put("allFilterUnitIds", allFilterUnitIds);
        mapUserForUnitIdsAndFormKeys.put("allFilterUnitIds", mapAllUnitIds);
        mapAllFormKeys.put("allFilterFormKeys", allFilterFormKeys);
        mapUserForUnitIdsAndFormKeys.put("allFilterFormKeys", mapAllFormKeys);
        return mapUserForUnitIdsAndFormKeys;
    }

    public boolean filterByFromConditionInGroup(DimensionValueSet dimensionValueSet, String formSchemeKey, String taskKey, List<FormDefine> formDefines, String unitId, Map<String, DimensionValue> dimMap) throws Exception {
        Map<DimensionCacheKey, Set<String>> result = null;
        ReminderContext context = new ReminderContext();
        context.setFormKey("");
        context.setTaskKey(taskKey);
        context.setFormSchemeKey(formSchemeKey);
        ArrayList<String> conditions = new ArrayList<String>();
        try {
            ExecutorContext executorContext = this.getExecutorContext(context);
            FormulaCallBack callback = new FormulaCallBack();
            for (FormDefine formDefine : formDefines) {
                conditions.add(formDefine.getFormCondition());
            }
            if (conditions != null && conditions.size() != 0) {
                ArrayList<Formula> formulas = new ArrayList<Formula>();
                for (FormDefine formDefine : formDefines) {
                    Formula formula = new Formula();
                    formula.setId(formDefine.getKey());
                    formula.setFormKey(formDefine.getKey());
                    formula.setFormula(formDefine.getFormCondition());
                    formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
                    formulas.add(formula);
                }
                callback.getFormulas().addAll(formulas);
                IFormulaRunner iFormulaRunner = this.dataAccessProvider.newFormulaRunner(callback);
                ReminderBatchConditionMonitor monitor = new ReminderBatchConditionMonitor();
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
                String dw = formScheme.getDw();
                String entityName = this.entityMetaService.getDimensionName(dw);
                dimensionValueSet.setValue(entityName, (Object)unitId);
                monitor.setDimensionValueMap(dimMap);
                iFormulaRunner.prepareCheck(executorContext, dimensionValueSet, (IMonitor)monitor);
                iFormulaRunner.run((IMonitor)monitor);
                result = monitor.getConditionResultList();
                if (result.size() == 0) {
                    return true;
                }
                Object[] arrayKey = result.keySet().toArray();
                DimensionCacheKey key = (DimensionCacheKey)arrayKey[0];
                return formDefines.size() != result.get(key).size();
            }
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public boolean filterByGroupConditionResult(DimensionValueSet dimensionValueSet, String formSchemeKey, String taskKey, FormGroupDefine formGroupDefine, String unitId, Map<String, DimensionValue> dimMap) {
        Map<DimensionCacheKey, Set<String>> result = null;
        ReminderContext excutorJtableContext = new ReminderContext();
        excutorJtableContext.setFormKey(formGroupDefine.getKey());
        excutorJtableContext.setTaskKey(taskKey);
        excutorJtableContext.setFormSchemeKey(formSchemeKey);
        try {
            ExecutorContext executorContext = this.getExecutorContext(excutorJtableContext);
            FormulaCallBack callback = new FormulaCallBack();
            if (StringUtils.isNotEmpty((String)formGroupDefine.getCondition())) {
                ArrayList<Formula> formulas = new ArrayList<Formula>();
                Formula formula = new Formula();
                formula.setId(formGroupDefine.getKey());
                formula.setFormKey(formGroupDefine.getKey());
                formula.setFormula(formGroupDefine.getCondition());
                formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
                formulas.add(formula);
                callback.getFormulas().addAll(formulas);
                IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callback);
                ReminderBatchConditionMonitor monitor = new ReminderBatchConditionMonitor();
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
                String dw = formScheme.getDw();
                String entityName = this.entityMetaService.getDimensionName(dw);
                dimensionValueSet.setValue(entityName, (Object)unitId);
                monitor.setDimensionValueMap(dimMap);
                runner.prepareCheck(executorContext, dimensionValueSet, (IMonitor)monitor);
                runner.run((IMonitor)monitor);
                result = monitor.getConditionResultList();
                return result.size() == 0;
            }
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public boolean filterByFormConditionResult(DimensionValueSet dimensionValueSet, String formSchemeKey, String taskKey, FormDefine formDefine, String unitId, Map<String, DimensionValue> dimMap) {
        Map<DimensionCacheKey, Set<String>> result = null;
        ReminderContext excutorJtableContext = new ReminderContext();
        excutorJtableContext.setFormKey(formDefine.getKey());
        excutorJtableContext.setTaskKey(taskKey);
        excutorJtableContext.setFormSchemeKey(formSchemeKey);
        try {
            ExecutorContext executorContext = this.getExecutorContext(excutorJtableContext);
            FormulaCallBack callback = new FormulaCallBack();
            if (StringUtils.isNotEmpty((String)formDefine.getFormCondition())) {
                ArrayList<Formula> formulas = new ArrayList<Formula>();
                Formula formula = new Formula();
                formula.setId(formDefine.getKey());
                formula.setFormKey(formDefine.getKey());
                formula.setFormula(formDefine.getFormCondition());
                formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
                formulas.add(formula);
                callback.getFormulas().addAll(formulas);
                IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callback);
                ReminderBatchConditionMonitor monitor = new ReminderBatchConditionMonitor();
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
                String dw = formScheme.getDw();
                String entityName = this.entityMetaService.getDimensionName(dw);
                dimensionValueSet.setValue(entityName, (Object)unitId);
                monitor.setDimensionValueMap(dimMap);
                runner.prepareCheck(executorContext, dimensionValueSet, (IMonitor)monitor);
                runner.run((IMonitor)monitor);
                result = monitor.getConditionResultList();
                return result.size() == 0;
            }
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    protected Map<List<String>, Map<List<String>, String>> msgContent(String formSchemeKey, String tempcontent, List<String> userIds, String workFlowType, Map<String, Map<String, ArrayList<String>>> map, NpContextIdentity contextIdentity, ContextUser user, List<String> unitIds, Map<List<String>, Map<List<String>, String>> userAndContents) {
        HashMap userAndContent = new HashMap();
        Map<String, ArrayList<String>> allFilterFormMap = map.get("allFilterFormKeys");
        List allFilterForms = allFilterFormMap.get("allFilterFormKeys");
        LinkedHashSet hashSetForms = new LinkedHashSet(allFilterForms);
        ArrayList allFilterFormsWithoutDuplicates = new ArrayList(hashSetForms);
        try {
            NpContextImpl context = new NpContextImpl();
            context.setUser(user);
            context.setIdentity((ContextIdentity)contextIdentity);
            this.npApplication.runAsContext((NpContext)context, () -> {
                DimensionValueSet buildDimension = this.entityHelper.buildDimension(formSchemeKey, unitIds);
                EntityViewDefine entityViewDefine = this.entityHelper.getParentEntityViewDefine(formSchemeKey);
                IEntityTable entityQuerySet = this.entityQueryManager.entityQuerySet(entityViewDefine, buildDimension, formSchemeKey);
                List allRows = entityQuerySet.getAllRows();
                if (allRows.size() > 0) {
                    ArrayList titleList = new ArrayList();
                    ArrayList<String> userList = new ArrayList<String>();
                    StringBuilder userTitles = new StringBuilder();
                    StringBuilder formKeyTitles = new StringBuilder();
                    HashSet<String> ids = new HashSet<String>();
                    for (IEntityRow iEntityRow : allRows) {
                        int i;
                        String title = iEntityRow.getTitle();
                        String entityKeyData = iEntityRow.getEntityKeyData();
                        AbstractData orgCode = iEntityRow.getValue("orgcode");
                        UserQueryReq userQueryReq = new UserQueryReq();
                        userQueryReq.setOrgCode(orgCode.getAsString());
                        List users = null;
                        try {
                            users = this.nvwaUserService.queryUser(userQueryReq);
                        }
                        catch (JQException e) {
                            log.error(e.getMessage(), e);
                        }
                        for (UserDTORes oneUser : users) {
                            ids.add(oneUser.getId());
                        }
                        for (String userId : userIds) {
                            if (!ids.contains(userId)) continue;
                            userList.add(userId);
                        }
                        String[] strs = tempcontent.split("]");
                        String string = strs[0];
                        String string2 = strs[1];
                        ArrayList<String> formGroupTitles = new ArrayList<String>();
                        if (WorkFlowType.GROUP.name().equals(workFlowType)) {
                            for (String formKey : allFilterFormsWithoutDuplicates) {
                                FormGroupDefine formGroupDefine = null;
                                try {
                                    formGroupDefine = this.runTimeAuthViewController.queryFormGroup(formKey);
                                }
                                catch (Exception e) {
                                    log.error(e.getMessage(), e);
                                }
                                String groupTitle = formGroupDefine.getTitle();
                                formGroupTitles.add(groupTitle);
                            }
                        } else if (WorkFlowType.FORM.name().equals(workFlowType)) {
                            for (String formKey : allFilterFormsWithoutDuplicates) {
                                FormDefine formDefine = this.runTimeAuthViewController.queryFormById(formKey);
                                String formTitle = formDefine.getTitle();
                                formGroupTitles.add(formTitle);
                            }
                        }
                        if (formGroupTitles.size() <= 3) {
                            for (i = 0; i < formGroupTitles.size(); ++i) {
                                if (i == formGroupTitles.size() - 1) {
                                    formKeyTitles.append((String)formGroupTitles.get(i));
                                    continue;
                                }
                                formKeyTitles.append((String)formGroupTitles.get(i)).append("\u3001");
                            }
                        } else {
                            for (i = 0; i < 3; ++i) {
                                if (i == 2) {
                                    if (WorkFlowType.FORM.name().equals(workFlowType)) {
                                        formKeyTitles.append((String)formGroupTitles.get(i)).append(" \u7b49\u5171").append(formGroupTitles.size()).append("\u5f20\u62a5\u8868");
                                        continue;
                                    }
                                    formKeyTitles.append((String)formGroupTitles.get(i)).append(" \u7b49\u5171").append(formGroupTitles.size()).append("\u4e2a\u5206\u7ec4");
                                    continue;
                                }
                                formKeyTitles.append((String)formGroupTitles.get(i)).append("\u3001");
                            }
                        }
                        String temp = string + "]" + string2 + "][" + title + " [" + formKeyTitles + "]" + strs[2];
                        userAndContent.put(userList, temp);
                        userAndContents.put(unitIds, userAndContent);
                    }
                }
            });
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return userAndContents;
    }

    public List<String> findCurrencyId(String code, String time) {
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT("CURRENCYID")).FROM("MD_ORG_CORPORATE")).WHERE("CODE=:CODE AND VALIDTIME < :TIME AND INVALIDTIME >:TIME")).toString();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("CODE", (Object)code).addValue("TIME", (Object)Timestamp.valueOf(time));
        return this.template.query(sql, (SqlParameterSource)parameterSource, (rs, rowNum) -> rs.getString("CURRENCYID"));
    }

    public List<String> findAdjTypeIds(String code, String time) {
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT("ADJTYPEIDS")).FROM("MD_ORG_CORPORATE")).WHERE("CODE=:CODE AND VALIDTIME < :TIME AND INVALIDTIME >:TIME")).toString();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("CODE", (Object)code).addValue("TIME", (Object)Timestamp.valueOf(time));
        return this.template.query(sql, (SqlParameterSource)parameterSource, (rs, rowNum) -> rs.getString("ADJTYPEIDS"));
    }

    public List<String> findOrgTypeId(String code, String time) {
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT("ORGTYPEID")).FROM("MD_ORG_CORPORATE")).WHERE("CODE=:CODE AND VALIDTIME < :TIME AND INVALIDTIME >:TIME")).toString();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("CODE", (Object)code).addValue("TIME", (Object)Timestamp.valueOf(time));
        return this.template.query(sql, (SqlParameterSource)parameterSource, (rs, rowNum) -> rs.getString("ORGTYPEID"));
    }

    @Override
    protected List<String> filterUnitIds(String formSchemeKey, List<String> unitIds, String period, NpContextIdentity contextIdentity, ContextUser user) {
        NpContextImpl context = new NpContextImpl();
        context.setUser(user);
        context.setIdentity((ContextIdentity)contextIdentity);
        ArrayList<String> filterUnitIds = new ArrayList<String>();
        this.npApplication.runAsContext((NpContext)context, () -> {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            String dw = formScheme.getDw();
            String entityName = this.entityMetaService.getDimensionName(dw);
            for (String unitId : unitIds) {
                DimensionValueSet dim = new DimensionValueSet();
                DataEntryParam param = new DataEntryParam();
                dim.setValue(entityName, (Object)unitId);
                dim.setValue("DATATIME", (Object)period);
                param.setDim(dim);
                param.setFormSchemeKey(formSchemeKey);
                ActionState actionStates = this.dataFlowService.queryState(param);
                if (actionStates.getUnitState().getCode().equals("UPLOADED") || actionStates.getUnitState().getCode().equals("CONFIRMED")) continue;
                filterUnitIds.add(unitId);
            }
        });
        return filterUnitIds;
    }

    public ExecutorContext getExecutorContext(ReminderContext reminderContext) {
        FormDefine form;
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)reminderContext.getFormKey()) && !reminderContext.getFormKey().contains(";") && (form = this.runTimeViewController.queryFormById(reminderContext.getFormKey())) != null) {
            executorContext.setDefaultGroupName(form.getFormCode());
        }
        executorContext.setJQReportModel(true);
        ReminderVariableExectuteFormula variableExectuteFormula = new ReminderVariableExectuteFormula(reminderContext, null);
        ReportFmlExecEnvironment environment = null;
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)reminderContext.getFormSchemeKey())) {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(reminderContext.getFormSchemeKey());
            String dw = formScheme.getDw();
            if (dw == null) {
                logger.warn("\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848\uff0c\u50ac\u62a5\u7ed3\u675f {}", (Object)reminderContext.getFormSchemeKey());
            }
            String entityName = this.entityMetaService.getDimensionName(dw);
            environment = new ReminderEntityFmlExecEnvironment(this.runTimeViewController, this.tbRtCtl, this.entityViewRunTimeController, reminderContext.getFormSchemeKey(), entityName);
        } else {
            environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.tbRtCtl, this.entityViewRunTimeController, reminderContext.getFormSchemeKey(), (ExectuteFormula)variableExectuteFormula, null);
        }
        executorContext.setEnv((IFmlExecEnvironment)environment);
        return executorContext;
    }
}

