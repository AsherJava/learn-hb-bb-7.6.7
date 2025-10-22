/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.message.constants.HandleModeEnum
 *  com.jiuqi.np.message.constants.MessageTypeEnum
 *  com.jiuqi.np.message.constants.ParticipantTypeEnum
 *  com.jiuqi.np.message.pojo.MessageDTO
 *  com.jiuqi.np.message.service.MessagePipelineService
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.util.Guid
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.domain.VaMessageSendDTO
 *  com.jiuqi.va.message.feign.client.VaMessageClient
 *  com.jiuqi.va.social.mail.domain.SendMailCustomDTO
 *  com.jiuqi.va.social.mail.service.VaMailService
 *  javax.annotation.Resource
 *  org.quartz.JobKey
 *  org.quartz.Scheduler
 *  org.quartz.TriggerKey
 */
package com.jiuqi.nr.reminder.impl;

import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.message.constants.HandleModeEnum;
import com.jiuqi.np.message.constants.MessageTypeEnum;
import com.jiuqi.np.message.constants.ParticipantTypeEnum;
import com.jiuqi.np.message.pojo.MessageDTO;
import com.jiuqi.np.message.service.MessagePipelineService;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.reminder.bean.AutoUserEntityElement;
import com.jiuqi.nr.reminder.bean.AutoUserEntitys;
import com.jiuqi.nr.reminder.bean.DropTreeResult;
import com.jiuqi.nr.reminder.bean.ReminderJobEntity;
import com.jiuqi.nr.reminder.bean.TreeNodeImpl;
import com.jiuqi.nr.reminder.infer.Constants;
import com.jiuqi.nr.reminder.infer.ParticipantHelper;
import com.jiuqi.nr.reminder.infer.ReminderJobInfoDao;
import com.jiuqi.nr.reminder.infer.ReminderRepository;
import com.jiuqi.nr.reminder.infer.ReminderService;
import com.jiuqi.nr.reminder.internal.CreateReminderCommand;
import com.jiuqi.nr.reminder.internal.ManualReminder;
import com.jiuqi.nr.reminder.internal.Reminder;
import com.jiuqi.nr.reminder.scheduler.AutoScheduleImpl;
import com.jiuqi.nr.reminder.scheduler.ScheduledJob;
import com.jiuqi.nr.reminder.scheduler.SendMsg;
import com.jiuqi.nr.reminder.spi.ReminderMessageListener;
import com.jiuqi.nr.reminder.untils.BeanConverter;
import com.jiuqi.nr.reminder.untils.CommonMethod;
import com.jiuqi.nr.reminder.untils.EntityHelper;
import com.jiuqi.nr.reminder.untils.EntityQueryManager;
import com.jiuqi.nr.reminder.untils.EntityUtil;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.util.Guid;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.domain.VaMessageSendDTO;
import com.jiuqi.va.message.feign.client.VaMessageClient;
import com.jiuqi.va.social.mail.domain.SendMailCustomDTO;
import com.jiuqi.va.social.mail.service.VaMailService;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import javax.annotation.Resource;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ReminderServiceImpl
implements ReminderService {
    public static final String SYS_ADMIN = "sys_user_admin";
    public static final byte UNIT_ALL_SUBORDINATE = 0;
    public static final byte UNIT_DIRECT_SUBORDINATE = 1;
    public static final int CC_TYPE_ROLE = 1;
    public static final int CC_TYPE_USER = 0;
    private final ReminderRepository reminderRepository;
    private final MessagePipelineService pipelineService;
    private final EntityHelper entityHelper;
    private final ParticipantHelper participantHelper;
    private static final Logger logger = LoggerFactory.getLogger(ReminderServiceImpl.class);
    @Autowired
    EntityIdentityService entityIdentityService;
    @Resource
    private IDataDefinitionRuntimeController tbRtCtl;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private EntityQueryManager entityQueryManager;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private AutoScheduleImpl autoSchedule;
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ReminderJobInfoDao reminderJobInfoDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    protected DefinitionAuthorityProvider definitionAuthorityProvider;
    @Autowired
    private IDataentryFlowService dataFlowService;
    @Autowired(required=false)
    private ReminderMessageListener reminderMessageListener;
    @Autowired
    public IEntityViewRunTimeController npRuntimeEntityController;
    @Autowired
    public IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private VaMessageClient messageClient;
    @Autowired
    private VaMailService vaMailService;
    @Autowired
    private AuthUserClient userClient;
    @Autowired
    private IDataAccessProvider dataAccessProvider;

    public ReminderServiceImpl(ReminderRepository reminderRepository, MessagePipelineService pipelineService, EntityHelper entityHelper, ParticipantHelper participantHelper) {
        this.reminderRepository = reminderRepository;
        this.pipelineService = pipelineService;
        this.entityHelper = entityHelper;
        this.participantHelper = participantHelper;
    }

    @Override
    public boolean send(Reminder reminder) throws Exception {
        if (reminder.getUserIds() == null) {
            throw new Exception("\u6ca1\u6709\u53ef\u50ac\u62a5\u7684\u5bf9\u8c61");
        }
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(Guid.newGuid());
        messageDTO.setType(Integer.valueOf(Constants.MSG_TYPE));
        messageDTO.setTitle("\u50ac\u62a5\u901a\u77e5");
        messageDTO.setParticipantType(ParticipantTypeEnum.USER.getCode());
        ArrayList<String> userIdList = new ArrayList<String>();
        for (String userId : reminder.getUserIds()) {
            boolean isSendUser = this.reminderRepository.findUserState(userId);
            if (!isSendUser) continue;
            userIdList.add(userId);
        }
        messageDTO.setParticipants(userIdList);
        messageDTO.setContent(reminder.getContent());
        messageDTO.setCcParticipantType(ParticipantTypeEnum.USER.getCode());
        messageDTO.setCcParticipants(reminder.getCcParticipants());
        ArrayList<Integer> handleMethod = new ArrayList<Integer>(reminder.getHandleMethod());
        if (userIdList.isEmpty()) {
            LogHelper.warn((String)"\u50ac\u62a5\u7ba1\u7406", (String)"\u81ea\u52a8\u50ac\u62a5\u53d1\u9001\u90ae\u4ef6\u60c5\u51b5", (String)("\u5355\u4f4d\u3010" + reminder.getUnitId() + "\u3011\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7528\u6237"));
            return true;
        }
        if (this.reminderMessageListener != null) {
            this.reminderMessageListener.sendMessageBefore(reminder, messageDTO);
        }
        for (Integer mode : handleMethod) {
            VaMessageSendDTO dto;
            if (HandleModeEnum.MAIL.getCode().equals(mode) || HandleModeEnum.COMPLEX_MAIL.getCode().equals(mode)) {
                SendMailCustomDTO customDTO = new SendMailCustomDTO();
                customDTO.setTitle("\u50ac\u62a5\u901a\u77e5");
                customDTO.setContent(reminder.getContent());
                customDTO.setUsers(this.getUsers(userIdList));
                if (customDTO.getUsers().isEmpty()) continue;
                List<String> ccParticipants = reminder.getCcParticipants();
                if (!CollectionUtils.isEmpty(ccParticipants)) {
                    customDTO.setCcUsers(this.getUsers(ccParticipants));
                }
                this.vaMailService.sendMessageCustom(customDTO);
            }
            if (HandleModeEnum.SYSTEM.getCode().equals(mode)) {
                dto = new VaMessageSendDTO();
                dto.setGrouptype("\u901a\u77e5");
                dto.setMsgtype("\u50ac\u62a5\u901a\u77e5");
                dto.setReceiveUserIds(userIdList);
                dto.setMsgChannel(VaMessageOption.MsgChannel.PC);
                String content = reminder.getContent();
                String removeTagMsg = SendMsg.removeTag(content);
                if (removeTagMsg.length() > 200) {
                    removeTagMsg = removeTagMsg.substring(0, 190) + "......";
                }
                dto.setContent(content);
                dto.setTitle(removeTagMsg);
                this.messageClient.addMsg(dto);
            }
            if (!HandleModeEnum.SHORT_MESSAGE.getCode().equals(mode)) continue;
            dto = new VaMessageSendDTO();
            dto.setGrouptype("\u901a\u77e5");
            dto.setMsgtype("\u50ac\u62a5\u901a\u77e5");
            dto.setReceiveUserIds(userIdList);
            dto.setMsgChannel(VaMessageOption.MsgChannel.SMS);
            dto.setContent(reminder.getContent());
            dto.setTitle("\u50ac\u62a5\u901a\u77e5");
            this.messageClient.addMsg(dto);
        }
        handleMethod.clear();
        handleMethod.add(HandleModeEnum.SYSTEM.getCode());
        messageDTO.setHandleMode(handleMethod);
        messageDTO.setValidTime(Instant.now());
        messageDTO.setType(MessageTypeEnum.TODO.getCode());
        return this.pipelineService.send(messageDTO);
    }

    private List<UserDO> getUsers(List<String> userIdList) {
        if (CollectionUtils.isEmpty(userIdList)) {
            return Collections.emptyList();
        }
        UserDTO userParam = new UserDTO();
        userParam.setUserIds(userIdList.toArray(new String[0]));
        userParam.setLockflag(Integer.valueOf(0));
        userParam.setStopflag(Integer.valueOf(0));
        PageVO userList = this.userClient.list(userParam);
        if (userList == null) {
            return Collections.emptyList();
        }
        if (userList.getRows() == null) {
            return Collections.emptyList();
        }
        return userList.getRows();
    }

    @Override
    public void createReminder(CreateReminderCommand command) throws Exception {
        if (command.getType() == 1) {
            this.createManualReminder(command);
        } else if (command.getType() == 0) {
            this.createAotuManualReminder(command);
        } else {
            LogHelper.error((String)"\u50ac\u62a5\u7ba1\u7406", (String)"\u50ac\u62a5\u5931\u8d25\u539f\u56e0\uff1a", (String)("\u539f\u56e0:Unsupported reminder type: " + command.getType() + ", \u50ac\u62a5id\uff1a " + command.getId()));
            throw new Exception("Unsupported reminder type: " + command.getType());
        }
    }

    @Override
    public void deleteReminder(String remId) {
        this.reminderRepository.delete(remId);
        this.deleteQuick(remId);
    }

    @Override
    public void batchDelete(List<String> remIds) {
        this.reminderRepository.batchDelete(remIds);
        if (remIds != null && remIds.size() > 0) {
            for (String remId : remIds) {
                this.deleteQuick(remId);
            }
        }
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
            logger.error(e.getMessage(), e);
        }
    }

    private void createManualReminder(CreateReminderCommand command) throws Exception {
        List<String> formKeys;
        ManualReminder reminder = new ManualReminder();
        HashMap<String, List<String>> userMaps = new HashMap<String, List<String>>();
        HashMap<String, List<String>> ccUserMaps = new HashMap<String, List<String>>();
        HashMap<String, List<String>> userErrorMaps = new HashMap<String, List<String>>();
        HashMap<String, List<String>> ccUserErrorMaps = new HashMap<String, List<String>>();
        ArrayList<String> unitIdsSuccess = new ArrayList<String>();
        ArrayList<String> unitIdsError = new ArrayList<String>();
        EntityViewDefine entityViewDefine = this.entityHelper.getEntityViewDefine(command.getFormSchemeId());
        command.setViewKey(entityViewDefine.getEntityId());
        BeanConverter.convert(command, (Reminder)reminder);
        List<String> unitIds = command.computeUnitId(this.entityHelper, this.participantHelper);
        EntityViewDefine parentEntityViewDefine = this.entityHelper.getParentEntityViewDefine(command.getFormSchemeId());
        command.setViewKey(parentEntityViewDefine.getEntityId());
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(command.getFormSchemeId());
        String dw = formScheme.getDw();
        if (dw == null) {
            logger.warn("\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848\uff0c\u50ac\u62a5\u7ed3\u675f {}", (Object)dw);
            return;
        }
        String entityName = this.entityMetaService.getDimensionName(dw);
        ArrayList<String> careKeys = new ArrayList<String>();
        boolean isGroup = command.getWorkFlowType().equals(WorkFlowType.GROUP.name());
        boolean isTable = command.getWorkFlowType().equals(WorkFlowType.FORM.name());
        if (isGroup) {
            if ("1".equals(command.getGroupRange())) {
                List allFormGroups = this.runTimeAuthViewController.getAllFormGroupsInFormScheme(command.getFormSchemeId());
                for (FormGroupDefine groups : allFormGroups) {
                    careKeys.add(groups.getKey());
                }
            } else {
                formKeys = command.getFormKeys();
                if (CollectionUtils.isEmpty(formKeys)) {
                    return;
                }
                careKeys.addAll(formKeys);
            }
        } else if (isTable) {
            if ("1".equals(command.getFormRange())) {
                List allForms = this.runTimeAuthViewController.queryAllFormDefinesByFormScheme(command.getFormSchemeId());
                for (FormDefine forms : allForms) {
                    careKeys.add(forms.getKey());
                }
            } else {
                formKeys = command.getFormKeys();
                if (CollectionUtils.isEmpty(formKeys)) {
                    return;
                }
                careKeys.addAll(formKeys);
            }
        }
        for (String unitId : unitIds) {
            Reminder reminderContent;
            boolean filter;
            DimensionValue dv;
            LinkedHashMap<String, DimensionValue> dimMap;
            DimensionValueSet dimensionValueSet;
            ScheduledJob scheduledJob;
            Set canUpload;
            DimensionValueSet dim = new DimensionValueSet();
            DataEntryParam param = new DataEntryParam();
            dim.setValue(entityName, (Object)unitId);
            dim.setValue("DATATIME", (Object)command.getPeriod());
            param.setDim(dim);
            param.setFormSchemeKey(command.getFormSchemeId());
            boolean unUp = false;
            ArrayList<String> unitids = new ArrayList<String>();
            unitids.add(unitId);
            Set<String> userIds = new HashSet<String>(this.getUserIdsByUnitIds(command, unitids, parentEntityViewDefine));
            if (userIds.isEmpty()) continue;
            if (isGroup) {
                for (String careKey : careKeys) {
                    try {
                        canUpload = this.definitionAuthorityProvider.getCanFormGroupUploadIdentityKeys(careKey);
                        canUpload.retainAll(userIds);
                        if (canUpload.isEmpty()) continue;
                        userIds = canUpload;
                        scheduledJob = new ScheduledJob();
                        scheduledJob.setEntityMetaService(this.entityMetaService);
                        scheduledJob.setDataAccessProvider(this.dataAccessProvider);
                        scheduledJob.setTbRtCtl(this.tbRtCtl);
                        scheduledJob.setEntityViewRunTimeController(this.entityViewRunTimeController);
                        scheduledJob.setRunTimeViewController(this.runTimeViewController);
                        dimensionValueSet = new DimensionValueSet();
                        dimensionValueSet.setValue("DATATIME", (Object)reminder.getPeriod());
                        dimMap = new LinkedHashMap<String, DimensionValue>();
                        dv = new DimensionValue();
                        dv.setName("DATATIME");
                        dv.setValue(reminder.getPeriod());
                        dimMap.put("DATATIME", dv);
                        FormGroupDefine define = this.runTimeAuthViewController.queryFormGroup(careKey);
                        filter = scheduledJob.filterByGroupConditionResult(dimensionValueSet, reminder.getFormSchemeId(), reminder.getTaskId(), define, unitId, dimMap);
                        if (!filter) continue;
                        List formDefines = this.runTimeViewController.getAllFormsInGroupWithoutOrder(careKey, false);
                        filter = scheduledJob.filterByFromConditionInGroup(dimensionValueSet, reminder.getFormSchemeId(), reminder.getTaskId(), formDefines, unitId, dimMap);
                        if (!filter) continue;
                        param.setGroupKey(careKey);
                        ActionStateBean actionStateBean = this.dataFlowService.queryReportState(param);
                        if (actionStateBean == null || "UPLOADED".equals(actionStateBean.getCode()) || "CONFIRMED".equals(actionStateBean.getCode())) continue;
                        unUp = true;
                        break;
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            } else if (isTable) {
                for (String careKey : careKeys) {
                    try {
                        canUpload = this.definitionAuthorityProvider.getCanFormUploadIdentityKeys(careKey);
                        canUpload.retainAll(userIds);
                        if (canUpload.isEmpty()) continue;
                        userIds = canUpload;
                        scheduledJob = new ScheduledJob();
                        scheduledJob.setEntityMetaService(this.entityMetaService);
                        scheduledJob.setDataAccessProvider(this.dataAccessProvider);
                        scheduledJob.setTbRtCtl(this.tbRtCtl);
                        scheduledJob.setEntityViewRunTimeController(this.entityViewRunTimeController);
                        scheduledJob.setRunTimeViewController(this.runTimeViewController);
                        dimensionValueSet = new DimensionValueSet();
                        dimensionValueSet.setValue("DATATIME", (Object)reminder.getPeriod());
                        dimMap = new LinkedHashMap();
                        dv = new DimensionValue();
                        dv.setName("DATATIME");
                        dv.setValue(reminder.getPeriod());
                        dimMap.put("DATATIME", dv);
                        FormDefine formDefine = this.runTimeAuthViewController.queryFormById(careKey);
                        filter = scheduledJob.filterByFormConditionResult(dimensionValueSet, reminder.getFormSchemeId(), reminder.getTaskId(), formDefine, unitId, dimMap);
                        if (!filter) continue;
                        param.setFormKey(careKey);
                        ActionStateBean actionStateBean = this.dataFlowService.queryReportState(param);
                        if (actionStateBean == null || "UPLOADED".equals(actionStateBean.getCode()) || "CONFIRMED".equals(actionStateBean.getCode())) continue;
                        unUp = true;
                        break;
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            } else {
                Set canUpSet = this.definitionAuthorityProvider.getCanUploadIdentityKeys(reminder.getFormSchemeId());
                canUpSet.retainAll(userIds);
                if (canUpSet.isEmpty()) continue;
                userIds = canUpSet;
                ActionStateBean actionStateBean = this.dataFlowService.queryReportState(param);
                if (actionStateBean == null) continue;
                if (!"UPLOADED".equals(actionStateBean.getCode()) && !"CONFIRMED".equals(actionStateBean.getCode())) {
                    unUp = true;
                }
            }
            if (!unUp) continue;
            reminder.setUserIds(new ArrayList<String>(userIds));
            reminder.setUnitIds(unitids);
            if (command.getCcInfos() != null && !command.getCcInfos().isEmpty()) {
                reminder.setCcInfos(command.getCcInfos());
                this.setCcParticipants(command, reminder, unitids);
            }
            if ((reminderContent = this.getReminder(reminder, command, unitids)) == null) continue;
            if (this.send(reminderContent)) {
                userMaps.put(unitId, reminderContent.getUserIds());
                ccUserMaps.put(unitId, reminderContent.getCcParticipants());
                unitIdsSuccess.add(unitId);
                continue;
            }
            userErrorMaps.put(unitId, reminderContent.getUserIds());
            ccUserErrorMaps.put(unitId, reminderContent.getCcParticipants());
            unitIdsError.add(unitId);
        }
        reminder.setUserIds(null);
        reminder.setContent(command.getContent());
        reminder.setUnitIds(unitIds);
        this.reminderRepository.save(reminder);
        this.LogSendEmail(command, unitIdsSuccess, unitIdsError, userMaps, ccUserMaps, userErrorMaps, ccUserErrorMaps, parentEntityViewDefine);
    }

    private void LogSendEmail(CreateReminderCommand command, List<String> unitIdsSuccess, List<String> unitIdsError, Map<String, List<String>> userMaps, Map<String, List<String>> ccUserMaps, Map<String, List<String>> userErrorMaps, Map<String, List<String>> ccUserErrorMaps, EntityViewDefine entityViewDefine) {
        String ccUserName;
        String userName;
        List<String> ccUserIdList;
        List<String> userIdList;
        List allRows;
        IEntityTable entityQuerySet;
        DimensionValueSet buildDimension;
        ArrayList<String> unitIdd;
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringErrorBuilder = new StringBuilder();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(command.getFormSchemeId());
        if (unitIdsSuccess == null || unitIdsSuccess.size() == 0) {
            stringBuilder.append("\u65e0, ");
        }
        for (String unitId : unitIdsSuccess) {
            unitIdd = new ArrayList<String>();
            unitIdd.add(unitId);
            buildDimension = this.entityHelper.buildDimension(command.getFormSchemeId(), unitIdd);
            entityQuerySet = this.entityQueryManager.entityQuerySet(entityViewDefine, buildDimension, command.getFormSchemeId());
            allRows = entityQuerySet.getAllRows();
            stringBuilder.append("\u5355\u4f4d\u540d\u79f0\uff1a").append(((IEntityRow)allRows.get(0)).getTitle()).append(", ");
            userIdList = userMaps.get(unitId);
            ccUserIdList = ccUserMaps.get(unitId);
            stringBuilder.append("\u6536\u4ef6\u7528\u6237\u540d\u79f0\uff1a");
            if (userIdList == null || userIdList.size() == 0) {
                stringBuilder.append("\u65e0, ");
            } else {
                for (String userId : userIdList) {
                    userName = this.reminderRepository.findUserName(userId);
                    stringBuilder.append(userName).append(", ");
                }
            }
            stringBuilder.append("\u6284\u9001\u7528\u6237\u540d\u79f0\uff1a");
            if (ccUserIdList == null || ccUserIdList.size() == 0) {
                stringBuilder.append("\u65e0, ");
                continue;
            }
            for (String ccUserId : ccUserIdList) {
                ccUserName = this.reminderRepository.findUserName(ccUserId);
                stringBuilder.append(ccUserName).append(", ");
            }
        }
        if (unitIdsError == null || unitIdsError.size() == 0) {
            stringErrorBuilder.append("\u65e0, ");
        } else {
            for (String unitId : unitIdsError) {
                String userEmail;
                unitIdd = new ArrayList();
                unitIdd.add(unitId);
                buildDimension = this.entityHelper.buildDimension(command.getFormSchemeId(), unitIdd);
                entityQuerySet = this.entityQueryManager.entityQuerySet(entityViewDefine, buildDimension, command.getFormSchemeId());
                allRows = entityQuerySet.getAllRows();
                stringErrorBuilder.append("\u5355\u4f4d\u540d\u79f0\uff1a").append(((IEntityRow)allRows.get(0)).getTitle()).append(", ");
                userIdList = userErrorMaps.get(unitId);
                ccUserIdList = ccUserErrorMaps.get(unitId);
                stringErrorBuilder.append("\u6536\u4ef6\u7528\u6237\u540d\u79f0\uff1a");
                if (userIdList == null || userIdList.size() == 0) {
                    stringErrorBuilder.append("\u65e0, ");
                } else {
                    for (String userId : userIdList) {
                        userName = this.reminderRepository.findUserName(userId);
                        stringErrorBuilder.append(userName).append(", ");
                        userEmail = this.reminderRepository.findUserEmail(userId);
                        stringErrorBuilder.append("\u90ae\u7bb1\uff1a ").append(userEmail).append(", ");
                    }
                }
                stringErrorBuilder.append("\u6284\u9001\u7528\u6237\u540d\u79f0\uff1a");
                if (ccUserIdList == null || ccUserIdList.size() == 0) {
                    stringErrorBuilder.append("\u65e0, ");
                    continue;
                }
                for (String ccUserId : ccUserIdList) {
                    ccUserName = this.reminderRepository.findUserName(ccUserId);
                    stringErrorBuilder.append(ccUserName).append(", ");
                    userEmail = this.reminderRepository.findUserEmail(ccUserId);
                    stringErrorBuilder.append("\u90ae\u7bb1\uff1a ").append(userEmail).append(", ");
                }
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sendTime = formatter.format(new Date());
        LogHelper.info((String)"\u50ac\u62a5\u7ba1\u7406", (String)"\u624b\u52a8\u50ac\u62a5\u53d1\u9001\u90ae\u4ef6\u60c5\u51b5", (String)("\u50ac\u62a5\u65b9\u6848\uff1a " + formScheme.getTitle() + " , \u53d1\u9001\u90ae\u4ef6\u6210\u529f\u7684\u5355\u4f4d: " + stringBuilder + " \u53d1\u9001\u90ae\u4ef6\u5931\u8d25\u7684\u5355\u4f4d: " + stringErrorBuilder + " \u53d1\u4ef6\u65f6\u95f4: " + sendTime), new HashMap());
    }

    private Reminder getReminder(ManualReminder reminder, CreateReminderCommand command, List<String> unitIds) {
        String content = reminder.getContent();
        DimensionValueSet buildDimension = this.entityHelper.buildDimension(command.getFormSchemeId(), unitIds);
        EntityViewDefine entityViewDefine = this.entityHelper.getParentEntityViewDefine(command.getFormSchemeId());
        IEntityTable entityQuerySet = this.entityQueryManager.entityQuerySet(entityViewDefine, buildDimension, command.getFormSchemeId());
        List allRows = entityQuerySet.getAllRows();
        ManualReminder reminderContent = new ManualReminder();
        if (allRows.size() > 0) {
            for (IEntityRow iEntityRow : allRows) {
                String title = iEntityRow.getTitle();
                String entityKeyData = iEntityRow.getEntityKeyData();
                String[] strs = content.split("]");
                String string = strs[0];
                String string2 = strs[1];
                BeanConverter.convert((Reminder)reminder, (Reminder)reminderContent);
                String contents = string + "]" + string2 + "][" + title + "]" + strs[2];
                reminderContent.setContent(contents);
            }
        }
        return reminderContent;
    }

    private void setCcParticipants(CreateReminderCommand command, Reminder reminder, List<String> unitIds) {
        ArrayList<String> ccParticipants = new ArrayList<String>();
        List<String> ccInfos = command.getCcInfos();
        ArrayList<String> ccRoles = new ArrayList<String>();
        if (ccInfos != null && !ccInfos.isEmpty()) {
            for (String ccInfo : ccInfos) {
                String[] ccs = ccInfo.split(":");
                if (Integer.parseInt(ccs[1]) == 1) {
                    ccRoles.add(ccs[0]);
                }
                if (Integer.parseInt(ccs[1]) != 0) continue;
                ccParticipants.add(ccs[0]);
            }
        }
        if (!ccRoles.isEmpty()) {
            List<String> ccRoleUsers = this.participantHelper.collectUserIdOnlyRole(ccRoles);
            ccParticipants.addAll(ccRoleUsers);
        }
        reminder.setCcParticipants(ccParticipants);
    }

    private void createAotuManualReminder(CreateReminderCommand command) throws Exception {
        String validStartTime;
        SimpleDateFormat simpleDateFormat;
        Date parse;
        SimpleDateFormat simpleDateFormat2;
        Date parse2;
        TimeZone tz;
        SimpleDateFormat sdf;
        SimpleDateFormat format;
        String validEndTime;
        ManualReminder reminder = new ManualReminder();
        EntityViewDefine entityViewDefine = this.entityHelper.getEntityViewDefine(command.getFormSchemeId());
        command.setViewKey(entityViewDefine.getEntityId());
        BeanConverter.convert(command, (Reminder)reminder);
        List<String> userIdsAll = null;
        List<String> userid = null;
        List<String> unitIds = command.computeUnitId(this.entityHelper);
        EntityViewDefine parentEntityViewDefine = this.entityHelper.getParentEntityViewDefine(command.getFormSchemeId());
        command.setViewKey(parentEntityViewDefine.getEntityId());
        reminder.setUnitIds(unitIds);
        command.setUnitIds(unitIds);
        reminder.setOriginalContent(command.getContent());
        command.setOriginalContent(command.getContent());
        userIdsAll = this.getUserIdsByUnitIds(command, unitIds, parentEntityViewDefine);
        Map<List<String>, Map<List<String>, String>> contents = new HashMap<List<String>, Map<List<String>, String>>();
        for (String unitId : unitIds) {
            Iterator unitIdSingle = new ArrayList<String>();
            unitIdSingle.add(unitId);
            userid = this.getUserIdsByUnitIds(command, (List<String>)((Object)unitIdSingle), parentEntityViewDefine);
            LinkedHashSet<String> hashSet = new LinkedHashSet<String>(userid);
            ArrayList<String> userIdsWithoutDuplicates = new ArrayList<String>(hashSet);
            contents = this.msgContent(command.getFormSchemeId(), command.getPeriod(), (List<String>)userIdsWithoutDuplicates, (List<String>)((Object)unitIdSingle), command.getContent(), contents);
        }
        if (userIdsAll != null) {
            LinkedHashSet<String> hashSet = new LinkedHashSet<String>(userIdsAll);
            ArrayList<String> userIdsWithoutDuplicates = new ArrayList<String>(hashSet);
            command.setUserIds(userIdsWithoutDuplicates);
            reminder.setUserIds(userIdsWithoutDuplicates);
        }
        if (command.getWorkFlowType().equals(WorkFlowType.GROUP.name())) {
            if ("1".equals(command.getGroupRange())) {
                ArrayList<String> groupKeys = new ArrayList<String>();
                List allFormGroups = this.runTimeAuthViewController.getAllFormGroupsInFormScheme(command.getFormSchemeId());
                for (FormGroupDefine groups : allFormGroups) {
                    groupKeys.add(groups.getKey());
                }
                command.setFormKeys(groupKeys);
                reminder.setFormKeys(groupKeys);
            }
        } else if (command.getWorkFlowType().equals(WorkFlowType.FORM.name()) && "1".equals(command.getFormRange())) {
            ArrayList<String> formKeys = new ArrayList<String>();
            List allForms = this.runTimeAuthViewController.queryAllFormDefinesByFormScheme(command.getFormSchemeId());
            for (FormDefine forms : allForms) {
                formKeys.add(forms.getKey());
            }
            command.setFormKeys(formKeys);
            reminder.setFormKeys(formKeys);
        }
        if (command.getCcInfos() != null && !command.getCcInfos().isEmpty()) {
            reminder.setCcInfos(command.getCcInfos());
            this.setCcParticipants(command, reminder, unitIds);
            command.setCcParticipants(reminder.getCcParticipants());
        }
        if (command.getValidEndTime() != null && !command.getValidEndTime().equals("") && command.getValidEndTime().contains("Z")) {
            validEndTime = command.getValidEndTime().replace("Z", " UTC");
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            String validEndTimeFormat = format.parse(validEndTime).toString();
            sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            tz = TimeZone.getTimeZone("GMT+8");
            sdf.setTimeZone(tz);
            parse2 = sdf.parse(validEndTimeFormat);
            simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String newValidEndTime = simpleDateFormat2.format(parse2);
            reminder.setValidEndTime(newValidEndTime);
            command.setValidEndTime(newValidEndTime);
        } else if (command.getValidEndTime() != null && !command.getValidEndTime().equals("")) {
            validEndTime = command.getValidEndTime();
            format = new SimpleDateFormat("yyyy-MM");
            parse = format.parse(validEndTime);
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String newValidEndTime = simpleDateFormat.format(parse);
            reminder.setValidEndTime(newValidEndTime);
            command.setValidEndTime(newValidEndTime);
        }
        if (command.getValidStartTime() != null && !command.getValidStartTime().equals("") && command.getValidStartTime().contains("Z")) {
            validStartTime = command.getValidStartTime().replace("Z", " UTC");
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            String validStartTimeFormat = format.parse(validStartTime).toString();
            sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            tz = TimeZone.getTimeZone("GMT+8");
            sdf.setTimeZone(tz);
            parse2 = sdf.parse(validStartTimeFormat);
            simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String newValidStartTime = simpleDateFormat2.format(parse2);
            reminder.setValidStartTime(newValidStartTime);
            command.setValidStartTime(newValidStartTime);
        } else if (command.getValidStartTime() != null && !command.getValidStartTime().equals("")) {
            validStartTime = command.getValidStartTime();
            format = new SimpleDateFormat("yyyy-MM");
            parse = format.parse(validStartTime);
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String newValidStartTime = simpleDateFormat.format(parse);
            reminder.setValidStartTime(newValidStartTime);
            command.setValidStartTime(newValidStartTime);
        }
        if (command.getRegularTime() != null && !command.getRegularTime().equals("") && command.getRegularTime().contains("Z")) {
            String regularTime = command.getRegularTime().replace("Z", " UTC");
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            String regularTimeFormat = format.parse(regularTime).toString();
            sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            tz = TimeZone.getTimeZone("GMT+8");
            sdf.setTimeZone(tz);
            parse2 = sdf.parse(regularTimeFormat);
            simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String newRegularTime = simpleDateFormat2.format(parse2);
            reminder.setRegularTime(newRegularTime);
            command.setRegularTime(newRegularTime);
        }
        if (command.getShowSendTime().equals("regularTime")) {
            String trigerCorn = this.ConvertTrigerCorn(command);
            reminder.setTrigerCorn(trigerCorn);
            Date sendDate = null;
            if (command.getFrequencyMode() != 0) {
                SimpleDateFormat simpleDateFormat3;
                Date validDate;
                String[] timeContent = command.getRegularTime().split(":");
                Calendar calendar = Calendar.getInstance();
                Calendar instance = Calendar.getInstance();
                Date currTime = instance.getTime();
                calendar.setTime(currTime);
                if (command.getFrequencyMode() == 1) {
                    calendar.set(2, Integer.parseInt(command.getRegularMonth()) - 1);
                    calendar.set(5, Integer.parseInt(command.getRegularDay()));
                    calendar.set(11, Integer.parseInt(timeContent[0]));
                    calendar.set(12, Integer.parseInt(timeContent[1]));
                    calendar.set(13, Integer.parseInt(timeContent[2]));
                    if (currTime.after(calendar.getTime())) {
                        calendar.add(1, 1);
                    }
                    sendDate = calendar.getTime();
                    if (command.getValidStartTime() != null && !command.getValidStartTime().equals("") && (validDate = (simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(command.getValidStartTime())).after(sendDate)) {
                        Calendar calendarY = Calendar.getInstance();
                        calendarY.setTime(validDate);
                        if ("YEAR".equals(command.getPeriodType())) {
                            calendar.set(1, calendarY.get(1));
                        } else if (calendarY.get(1) > calendar.get(1)) {
                            calendar.set(1, calendarY.get(1));
                            if (calendarY.getTime().after(calendar.getTime())) {
                                calendar.add(1, 1);
                            }
                        } else {
                            calendar.add(1, 1);
                        }
                        sendDate = calendar.getTime();
                        if (validDate.after(sendDate)) {
                            calendar.add(1, 1);
                            sendDate = calendar.getTime();
                        }
                    }
                } else if (command.getFrequencyMode() == 4) {
                    SimpleDateFormat simpleDateFormat4;
                    Date validDate2;
                    int currentWeekDay = calendar.get(7);
                    int regularWeek = Integer.parseInt(command.getRegularWeek());
                    if (regularWeek == 1) {
                        regularWeek = 8;
                    }
                    if (currentWeekDay == 1) {
                        currentWeekDay = 8;
                    }
                    if (currentWeekDay < regularWeek) {
                        calendar.add(5, regularWeek - currentWeekDay);
                        calendar.set(11, Integer.parseInt(timeContent[0]));
                        calendar.set(12, Integer.parseInt(timeContent[1]));
                        calendar.set(13, Integer.parseInt(timeContent[2]));
                    } else if (currentWeekDay > regularWeek) {
                        calendar.add(5, regularWeek + 7 - currentWeekDay);
                        calendar.set(11, Integer.parseInt(timeContent[0]));
                        calendar.set(12, Integer.parseInt(timeContent[1]));
                        calendar.set(13, Integer.parseInt(timeContent[2]));
                    } else if (currentWeekDay == regularWeek) {
                        calendar.set(11, Integer.parseInt(timeContent[0]));
                        calendar.set(12, Integer.parseInt(timeContent[1]));
                        calendar.set(13, Integer.parseInt(timeContent[2]));
                        if (currTime.after(calendar.getTime())) {
                            calendar.add(7, 7);
                        }
                    }
                    sendDate = calendar.getTime();
                    if (command.getValidStartTime() != null && !command.getValidStartTime().equals("") && (validDate2 = (simpleDateFormat4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(command.getValidStartTime())).after(sendDate)) {
                        Calendar calendarW = Calendar.getInstance();
                        calendarW.setTime(validDate2);
                        if (regularWeek > calendarW.get(7)) {
                            int gap = regularWeek - calendarW.get(7);
                            calendarW.add(5, gap);
                        } else if (regularWeek < calendarW.get(7)) {
                            calendarW.add(5, regularWeek + 7 - calendarW.get(7));
                        }
                        calendarW.set(11, Integer.parseInt(timeContent[0]));
                        calendarW.set(12, Integer.parseInt(timeContent[1]));
                        calendarW.set(13, Integer.parseInt(timeContent[2]));
                        sendDate = calendarW.getTime();
                    }
                } else if (command.getFrequencyMode() == 2) {
                    calendar.set(5, Integer.parseInt(command.getRegularDay()));
                    calendar.set(11, Integer.parseInt(timeContent[0]));
                    calendar.set(12, Integer.parseInt(timeContent[1]));
                    calendar.set(13, Integer.parseInt(timeContent[2]));
                    if (currTime.after(calendar.getTime())) {
                        calendar.add(2, 1);
                    }
                    sendDate = calendar.getTime();
                    if (command.getValidStartTime() != null && !command.getValidStartTime().equals("") && (validDate = (simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(command.getValidStartTime())).after(sendDate)) {
                        Calendar calendarY = Calendar.getInstance();
                        calendarY.setTime(validDate);
                        if ("YEAR".equals(command.getPeriodType()) || "MONTH".equals(command.getPeriodType())) {
                            calendar.set(2, calendarY.get(2));
                            if (calendarY.get(1) > calendar.get(1)) {
                                calendar.set(1, calendarY.get(1));
                            }
                        } else if (calendarY.get(1) > calendar.get(1)) {
                            calendar.set(1, calendarY.get(1));
                            calendar.set(2, calendarY.get(2));
                        } else if (calendarY.get(2) > calendar.get(2)) {
                            calendar.set(2, calendarY.get(2));
                            if (calendarY.get(5) > calendar.get(5)) {
                                calendar.add(2, 1);
                            }
                        } else {
                            calendar.add(2, 1);
                        }
                        if (validDate.after(sendDate = calendar.getTime())) {
                            calendar.add(2, 1);
                            sendDate = calendar.getTime();
                        }
                    }
                } else if (command.getFrequencyMode() == 9) {
                    calendar.set(11, Integer.parseInt(timeContent[0]));
                    calendar.set(12, Integer.parseInt(timeContent[1]));
                    calendar.set(13, Integer.parseInt(timeContent[2]));
                    if (currTime.after(calendar.getTime())) {
                        calendar.add(5, 1);
                    }
                    sendDate = calendar.getTime();
                    if (command.getValidStartTime() != null && !command.getValidStartTime().equals("") && (validDate = (simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(command.getValidStartTime())).after(sendDate)) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(validDate);
                        cal.set(11, Integer.parseInt(timeContent[0]));
                        cal.set(12, Integer.parseInt(timeContent[1]));
                        cal.set(13, Integer.parseInt(timeContent[2]));
                        sendDate = cal.getTime();
                    }
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String newRegularTime = formatter.format(sendDate);
                reminder.setRegularTime(newRegularTime);
                command.setRegularTime(newRegularTime);
            }
        }
        if (reminder.getId() != null) {
            this.reminderRepository.updateReminder(reminder);
            command.setEdit(true);
        } else {
            String remId = this.reminderRepository.save(reminder);
            command.setId(remId);
            command.setEdit(false);
        }
        this.autoSchedule.asyncBaseJob(command, contents, this.participantHelper);
    }

    private String ConvertTrigerCorn(CreateReminderCommand command) {
        int frequencyMode = command.getFrequencyMode();
        String trigerCorn = null;
        if (frequencyMode == 1) {
            trigerCorn = "0 m h d M ? *";
            trigerCorn = trigerCorn.replace("d", command.getRegularDay());
            trigerCorn = trigerCorn.replace("M", command.getRegularMonth());
        } else if (frequencyMode == 2) {
            trigerCorn = "0 m h d * ?";
            trigerCorn = trigerCorn.replace("d", command.getRegularDay());
        } else if (frequencyMode == 4) {
            trigerCorn = "0 m h ? * w";
            trigerCorn = trigerCorn.replace("w", command.getRegularWeek());
        } else if (frequencyMode == 9) {
            trigerCorn = "0 m h * * ?";
        }
        return trigerCorn;
    }

    @Override
    public AutoUserEntitys getUserEntitys(String formSchemeKey) {
        AutoUserEntitys userEntitys = new AutoUserEntitys();
        try {
            String currUserId = this.getCurrUserId();
            FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeKey);
            String entitiesKey = formScheme.getMasterEntitiesKey();
            String[] entitiesKeyArr = entitiesKey.split(";");
            Map<String, List<String>> queryUserEntitys = this.queryUserEntitys(currUserId, entitiesKeyArr);
            return this.queryUserEntitys(queryUserEntitys, formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return userEntitys;
        }
    }

    public Map<String, List<String>> queryUserEntitys(String identityId, String[] entitiesKey) {
        Map grantedEntityKeys = this.entityIdentityService.getGrantedEntityKeys(identityId);
        HashMap<String, List<String>> para = new HashMap<String, List<String>>(18);
        try {
            for (String entityKey : entitiesKey) {
                TableModelDefine tableModel = this.entityMetaService.getTableModel(entityKey);
                boolean periodView = this.periodEntityAdapter.isPeriodEntity(entityKey);
                if (periodView || grantedEntityKeys.size() <= 0) continue;
                for (Map.Entry entityKeys : grantedEntityKeys.entrySet()) {
                    if (!tableModel.getID().equals(entityKeys.getKey())) continue;
                    para.put(tableModel.getID(), new ArrayList((Collection)grantedEntityKeys.get(tableModel.getID())));
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return para;
    }

    public AutoUserEntitys queryUserEntitys(Map<String, List<String>> entityIds, String formSchemeKey) {
        AutoUserEntitys userEntitys = new AutoUserEntitys();
        try {
            ArrayList<AutoUserEntityElement> userEntityElements = new ArrayList<AutoUserEntityElement>();
            userEntitys.setUserEntityElements(userEntityElements);
            for (Map.Entry<String, List<String>> entryId : entityIds.entrySet()) {
                String tableKey = entryId.getKey();
                TableDefine tableDefine = this.tbRtCtl.queryTableDefine(tableKey);
                if (tableDefine == null) continue;
                AutoUserEntityElement userEntityElement = new AutoUserEntityElement(tableDefine);
                userEntityElements.add(userEntityElement);
                EntityViewDefine entityViewDefine = this.entityHelper.getParentEntityViewDefine(formSchemeKey);
                List<IEntityRow> iEntityRows = this.entityUtil.getentityRow(entryId.getValue(), entityViewDefine, formSchemeKey);
                ArrayList<AutoUserEntityElement> childrenEntityElements = new ArrayList<AutoUserEntityElement>();
                userEntityElement.setChildren(childrenEntityElements);
                for (IEntityRow iEntityRow : iEntityRows) {
                    AutoUserEntityElement children = new AutoUserEntityElement(iEntityRow);
                    children.setParentKey(tableKey);
                    childrenEntityElements.add(children);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return userEntitys;
    }

    public List<String> getUserIdsByUnitIds(CreateReminderCommand command, List<String> unitIds, EntityViewDefine entityViewDefine) throws Exception {
        List<Object> userIds = new ArrayList();
        DimensionValueSet buildDimension = this.entityHelper.buildDimension(command.getFormSchemeId(), unitIds);
        IEntityTable entityQuerySet = this.entityQueryManager.entityQuerySet(entityViewDefine, buildDimension, command.getFormSchemeId());
        List allRows = entityQuerySet.getAllRows();
        if (command.getRoleType() == 8) {
            userIds = this.participantHelper.collectUserId(command.getViewKey(), command.getFormSchemeId(), unitIds, command.getRoleIds(), allRows);
        } else if (command.getRoleType() == 1) {
            userIds = this.participantHelper.collectCommitUserId(command.getViewKey(), command.getFormSchemeId(), unitIds, command, allRows);
        } else if (command.getRoleType() == 0) {
            userIds = this.participantHelper.collectUserId(command.getViewKey(), command.getFormSchemeId(), unitIds, null, allRows);
        } else {
            throw new Exception("\u4e0d\u652f\u6301\u7684\u89d2\u8272\u8303\u56f4\u7c7b\u578b:" + command.getRoleType());
        }
        return userIds;
    }

    private Map<List<String>, Map<List<String>, String>> msgContent(String formSchemeKey, String period, List<String> UserIds, List<String> unitids, String content, Map<List<String>, Map<List<String>, String>> userAndContents) {
        HashMap<List<String>, String> userAndContent = new HashMap<List<String>, String>();
        String tempcontent = content;
        String temp = null;
        try {
            DimensionValueSet buildDimension = this.entityHelper.buildDimension(formSchemeKey, unitids);
            EntityViewDefine entityViewDefine = this.entityHelper.getParentEntityViewDefine(formSchemeKey);
            IEntityTable entityQuerySet = this.entityQueryManager.entityQuerySet(entityViewDefine, buildDimension, formSchemeKey);
            List allRows = entityQuerySet.getAllRows();
            if (allRows.size() > 0) {
                for (IEntityRow iEntityRow : allRows) {
                    String title = iEntityRow.getTitle();
                    String[] strs = tempcontent.split("]");
                    String string = strs[0];
                    String string2 = strs[1];
                    temp = string + "]" + string2 + "][" + title + "]" + strs[2];
                    userAndContent.put(UserIds, temp);
                    userAndContents.put(unitids, userAndContent);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return userAndContents;
    }

    private String getCurrUserId() {
        return NpContextHolder.getContext().getIdentityId();
    }

    @Override
    public List<DropTreeResult> queryDropTreeData(String formSchemeKey, String period) {
        ArrayList<DropTreeResult> dropTreeList = new ArrayList();
        String currUserId = this.getCurrUserId();
        dropTreeList = this.getAdminData(formSchemeKey, period);
        return dropTreeList;
    }

    private List<DropTreeResult> getAdminData(String formSchemeKey, String period) {
        List<DropTreeResult> entityResult = new ArrayList<DropTreeResult>();
        List<EntityViewDefine> queryAdminEntitieList = this.queryAdminEntitieList(formSchemeKey);
        for (EntityViewDefine entityViewDefine : queryAdminEntitieList) {
            IEntityTable entityCount = this.getEntityTable(entityViewDefine, period, formSchemeKey);
            List allRows = entityCount.getRootRows();
            if (allRows.size() <= 0) continue;
            entityResult = this.getEntityResult(allRows, entityCount);
        }
        return entityResult;
    }

    private List<DropTreeResult> getEntityResult(List<IEntityRow> entityData, IEntityTable entityTable) {
        ArrayList<DropTreeResult> resultList = new ArrayList<DropTreeResult>();
        DropTreeResult dropTreeResult = null;
        for (IEntityRow iEntityRow : entityData) {
            dropTreeResult = new DropTreeResult();
            dropTreeResult.setKey(iEntityRow.getEntityKeyData());
            dropTreeResult.setTitle(iEntityRow.getTitle());
            List<IEntityRow> drictData = this.getDrictData(entityTable, iEntityRow.getEntityKeyData());
            if (drictData.size() > 0) {
                List<DropTreeResult> entityResult = this.getEntityResult(drictData, entityTable);
                dropTreeResult.setChildren(entityResult);
            }
            resultList.add(dropTreeResult);
        }
        return resultList;
    }

    private List<DropTreeResult> getUserResultData(List<AutoUserEntityElement> userEntityElements) {
        ArrayList<DropTreeResult> resultList = new ArrayList<DropTreeResult>();
        DropTreeResult dropTreeResult = null;
        if (userEntityElements.size() > 0) {
            for (AutoUserEntityElement autoUserEntityElement : userEntityElements) {
                dropTreeResult = new DropTreeResult();
                dropTreeResult.setKey(autoUserEntityElement.getKey());
                dropTreeResult.setTitle(autoUserEntityElement.getTitle());
                List<AutoUserEntityElement> children = autoUserEntityElement.getChildren();
                if (children != null && children.size() > 0) {
                    List<DropTreeResult> userData = this.getUserResultData(children);
                    dropTreeResult.setChildren(userData);
                }
                resultList.add(dropTreeResult);
            }
        }
        return resultList;
    }

    @Override
    public List<EntityViewDefine> queryAdminEntitieList(String formSchemeKey) {
        ArrayList<EntityViewDefine> entityList = new ArrayList<EntityViewDefine>();
        try {
            String[] entitiesKeyArr;
            FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeKey);
            String entitiesKey = formScheme.getFlowsSetting().getDesignTableDefines();
            for (String entityKey : entitiesKeyArr = entitiesKey.split(";")) {
                boolean periodView = this.periodEntityAdapter.isPeriodEntity(entityKey);
                EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityKey);
                if (periodView || entityViewDefine == null) continue;
                entityList.add(entityViewDefine);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return entityList;
    }

    private IEntityTable getEntityTable(EntityViewDefine entityView, String period, String formSchemeKey) {
        try {
            IEntityTable entityTable;
            DimensionValueSet dimValueSet = new DimensionValueSet();
            if (period != null) {
                dimValueSet.setValue("DATATIME", (Object)period);
            }
            if ((entityTable = this.entityQueryManager.entityQuerySet(entityView, dimValueSet, formSchemeKey)) != null) {
                return entityTable;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private List<IEntityRow> getDrictData(IEntityTable entityQuerySet, String entityKey) {
        List<Object> allRows = new ArrayList<IEntityRow>();
        try {
            if (entityQuerySet != null) {
                allRows = entityQuerySet.getChildRows(entityKey);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return allRows;
    }

    @Override
    public String queryUnitName(String formSchemeKey, String unitId) {
        List<IEntityRow> iEntityRows;
        String unitName = null;
        ArrayList<String> unitIds = new ArrayList<String>();
        unitIds.add(unitId);
        EntityViewDefine entityViewDefine = this.entityHelper.getParentEntityViewDefine(formSchemeKey);
        if (entityViewDefine != null && (iEntityRows = this.entityUtil.getentityRow(unitIds, entityViewDefine, formSchemeKey)) != null && iEntityRows.size() > 0) {
            for (IEntityRow iEntityRow : iEntityRows) {
                unitName = iEntityRow.getTitle();
            }
        }
        return unitName;
    }

    @Override
    public List<TreeNodeImpl> getReportTask() throws Exception {
        ArrayList<TreeNodeImpl> nodeList = new ArrayList<TreeNodeImpl>();
        List taskDefines = this.runTimeAuthViewController.getAllTaskDefinesByType(TaskType.TASK_TYPE_DEFAULT);
        if (taskDefines != null) {
            for (TaskDefine taskDefine : taskDefines) {
                TreeNodeImpl treeNode = new TreeNodeImpl();
                treeNode.setKey(taskDefine.getKey());
                treeNode.setTitle(taskDefine.getTitle());
                treeNode.setExpand(false);
                nodeList.add(treeNode);
            }
        }
        return nodeList;
    }

    @Override
    public List<TreeNodeImpl> getFormSchemeList(TaskDefine taskDefine) throws Exception {
        ArrayList<TreeNodeImpl> nodeList = new ArrayList<TreeNodeImpl>();
        if (taskDefine.getKey() == null) {
            return null;
        }
        List<FormSchemeDefine> formSchemes = this.getFormSchemes(taskDefine.getKey());
        if (formSchemes.size() > 0) {
            for (FormSchemeDefine formSchemeDefine : formSchemes) {
                TreeNodeImpl treeNode = new TreeNodeImpl();
                treeNode.setKey(formSchemeDefine.getKey());
                treeNode.setTitle(formSchemeDefine.getTitle());
                treeNode.setChildren(null);
                treeNode.setExpand(false);
                treeNode.setTaskId(taskDefine.getKey());
                treeNode.setEntityKey(formSchemeDefine.getDw());
                PeriodWrapper currPeriod = CommonMethod.getCurrPeriod(formSchemeDefine);
                treeNode.setPeriod(currPeriod.toString());
                nodeList.add(treeNode);
            }
        }
        return nodeList;
    }

    private List<FormSchemeDefine> getFormSchemes(String taskKey) throws Exception {
        ArrayList<FormSchemeDefine> schemeList = new ArrayList<FormSchemeDefine>();
        if (taskKey == null) {
            return null;
        }
        List queryFormSchemeByTask = this.runTimeAuthViewController.queryFormSchemeByTask(taskKey);
        if (queryFormSchemeByTask != null && !queryFormSchemeByTask.isEmpty()) {
            for (FormSchemeDefine formSchemeDefine : queryFormSchemeByTask) {
                schemeList.add(formSchemeDefine);
            }
        }
        return schemeList;
    }
}

