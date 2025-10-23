/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.message.constants.HandleModeEnum
 *  com.jiuqi.np.message.constants.MessageTypeEnum
 *  com.jiuqi.np.message.constants.ParticipantTypeEnum
 *  com.jiuqi.np.message.pojo.MessageDTO
 *  com.jiuqi.np.message.service.MessagePipelineService
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.dto.UserDTO
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.message.domain.VaMessageChannelDTO
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.domain.VaMessageSendDTO
 *  com.jiuqi.va.message.feign.client.VaMessageClient
 *  com.jiuqi.va.message.service.VaMessageService
 *  com.jiuqi.va.social.mail.domain.SendMailCustomDTO
 *  com.jiuqi.va.social.mail.service.VaMailService
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.reminder.plan.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.message.constants.HandleModeEnum;
import com.jiuqi.np.message.constants.MessageTypeEnum;
import com.jiuqi.np.message.constants.ParticipantTypeEnum;
import com.jiuqi.np.message.pojo.MessageDTO;
import com.jiuqi.np.message.service.MessagePipelineService;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.dto.UserDTO;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.reminder.plan.CbPlanDTO;
import com.jiuqi.nr.reminder.plan.ChannelParam;
import com.jiuqi.nr.reminder.plan.MessageMerge;
import com.jiuqi.nr.reminder.plan.ReminderMessage;
import com.jiuqi.nr.reminder.plan.TaskInfo;
import com.jiuqi.nr.reminder.plan.TreeNode;
import com.jiuqi.nr.reminder.plan.common.CbUtils;
import com.jiuqi.nr.reminder.plan.common.ErrorEnumImpl;
import com.jiuqi.nr.reminder.plan.dao.CbExecLogDAO;
import com.jiuqi.nr.reminder.plan.dao.CbPlanDAO;
import com.jiuqi.nr.reminder.plan.dao.CbPlanDwDAO;
import com.jiuqi.nr.reminder.plan.dao.CbPlanFDAO;
import com.jiuqi.nr.reminder.plan.dao.CbPlanLogDAO;
import com.jiuqi.nr.reminder.plan.dao.CbPlanTimeDAO;
import com.jiuqi.nr.reminder.plan.dao.CbPlanToDAO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbExecLogDO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanDO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanDwDO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanFormDO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanLogDO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanTimeDO;
import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanToDO;
import com.jiuqi.nr.reminder.plan.job.CbPlanScheduleJobManager;
import com.jiuqi.nr.reminder.plan.service.CbPlanService;
import com.jiuqi.nr.reminder.plan.service.ParticipantService;
import com.jiuqi.nr.reminder.plan.web.CbPlanVO;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.message.domain.VaMessageChannelDTO;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.domain.VaMessageSendDTO;
import com.jiuqi.va.message.feign.client.VaMessageClient;
import com.jiuqi.va.message.service.VaMessageService;
import com.jiuqi.va.social.mail.domain.SendMailCustomDTO;
import com.jiuqi.va.social.mail.service.VaMailService;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class CbPlanServiceImpl
implements CbPlanService {
    private static final Logger logger = LoggerFactory.getLogger(CbPlanServiceImpl.class);
    @Autowired
    private CbExecLogDAO cbExecLogDAO;
    @Autowired
    private CbPlanDAO cbPlanDAO;
    @Autowired
    private CbPlanDwDAO cbPlanDwDAO;
    @Autowired
    private CbPlanFDAO cbPlanFDAO;
    @Autowired
    private CbPlanToDAO cbPlanToDAO;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityViewRunTimeController viewRtCtl;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IDataentryFlowService iDataentryFlowService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private IPeriodEntityAdapter iPeriodEntityAdapter;
    @Autowired
    private NvwaUserClient nvwaUserClient;
    @Autowired
    private MessagePipelineService messagePipelineService;
    @Autowired
    private VaMailService vaMailService;
    @Autowired
    private VaMessageClient vaMessageClient;
    @Autowired
    private DataPermissionEvaluatorFactory dataPermissionEvaluatorFactory;
    @Autowired
    private DimensionProviderFactory dimensionProviderFactory;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CbPlanTimeDAO cbPlanTimeDAO;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;
    @Autowired
    private CbPlanScheduleJobManager cbPlanScheduleJobManager;
    @Autowired
    private CbPlanLogDAO cbPlanLogDAO;
    @Autowired
    private VaMessageService vaMessageService;
    @Autowired
    private DataEntityFullService dataEntityFullService;
    @Autowired
    private IRuntimeFormSchemePeriodService runtimeFormSchemePeriodService;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String addCbPlan(CbPlanDTO cbPlanDTO) throws JQException {
        this.check(cbPlanDTO);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(cbPlanDTO.getTaskId());
        cbPlanDTO.setDw(CbUtils.getContextMainDimId(taskDefine.getDw()));
        cbPlanDTO.setPlanId(OrderGenerator.newOrder());
        CbPlanDO cbPlanDO = cbPlanDTO.toCbPlanDO();
        this.cbPlanDAO.insertCbPlan(cbPlanDO);
        List<CbPlanDwDO> dwList = cbPlanDTO.toDwList();
        this.cbPlanDwDAO.batchInsert(dwList);
        List<CbPlanToDO> toList = cbPlanDTO.toToList();
        this.cbPlanToDAO.batchInsert(toList);
        List<CbPlanFormDO> forms = cbPlanDTO.toFormList();
        this.cbPlanFDAO.batchInsert(forms);
        if (cbPlanDTO.isEnabled()) {
            if (cbPlanDTO.getKind() == 1) {
                this.execPlan(cbPlanDO.getId());
            } else if (cbPlanDTO.getKind() == 2) {
                List<CbPlanTimeDO> timeList = cbPlanDTO.getTimes();
                if (CollectionUtils.isEmpty(timeList)) {
                    throw new JQException((ErrorEnum)new ErrorEnumImpl("\u672a\u8bbe\u7f6e\u50ac\u62a5\u65f6\u95f4"));
                }
                this.cbPlanTimeDAO.batchInsert(timeList);
                this.resScheduleCb(cbPlanDTO);
            }
        }
        return cbPlanDO.getId();
    }

    private void check(CbPlanDTO cbPlanDTO) throws JQException {
        String execUnit = cbPlanDTO.getExecUnit();
        if (!StringUtils.hasLength(execUnit)) {
            throw new JQException((ErrorEnum)new ErrorEnumImpl("\u8bf7\u9009\u62e9\u50ac\u62a5\u5355\u4f4d"));
        }
        if (cbPlanDTO.getExecUser() == null) {
            cbPlanDTO.setExecUser(NpContextHolder.getContext().getUserId());
        }
        if (cbPlanDTO.getCreateUser() == null) {
            cbPlanDTO.setCreateUser(NpContextHolder.getContext().getUserId());
        }
        Instant now = Instant.now();
        if (cbPlanDTO.getCreateTime() == null) {
            cbPlanDTO.setCreateTime(Timestamp.from(now));
        }
        if (cbPlanDTO.getUpdateTime() == null) {
            cbPlanDTO.setUpdateTime(Timestamp.from(now));
        }
        if (cbPlanDTO.getEffectiveStartTime() == null) {
            cbPlanDTO.setEffectiveStartTime(new Date());
        }
        if (cbPlanDTO.getEffectiveEndTime() == null) {
            Instant plus = now.plus(Duration.ofSeconds(31556952L));
            cbPlanDTO.setEffectiveEndTime(Timestamp.from(plus));
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateCbPlan(CbPlanDTO cbPlanDTO) throws JQException {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(cbPlanDTO.getTaskId());
        cbPlanDTO.setDw(CbUtils.getContextMainDimId(taskDefine.getDw()));
        String planId = cbPlanDTO.getPlanId();
        CbPlanDTO old = this.queryByPlanId(planId);
        if (old == null) {
            throw new JQException((ErrorEnum)new ErrorEnumImpl("\u4fee\u6539\u5931\u8d25\uff0c\u672a\u627e\u5230\u539f\u50ac\u62a5\u8bb0\u5f55"));
        }
        cbPlanDTO.setExecUser(NpContextHolder.getContext().getUserId());
        cbPlanDTO.setUpdateTime(new Date());
        cbPlanDTO.setCreateTime(old.getCreateTime());
        cbPlanDTO.setUpdateUser(NpContextHolder.getContext().getUserId());
        CbPlanDO cbPlanDO = cbPlanDTO.toCbPlanDO();
        this.cbPlanDAO.updateCbPlanById(cbPlanDO);
        List<CbPlanDwDO> dwList = cbPlanDTO.toDwList();
        List<CbPlanDwDO> oldDwList = old.toDwList();
        if (dwList.size() != oldDwList.size() || !new HashSet<CbPlanDwDO>(dwList).containsAll(oldDwList)) {
            this.cbPlanDwDAO.deleteByPlanId(planId);
            this.cbPlanDwDAO.batchInsert(dwList);
        }
        List<CbPlanFormDO> formList = cbPlanDTO.toFormList();
        List<CbPlanFormDO> oldFormList = old.toFormList();
        if (formList.size() != oldFormList.size() || !new HashSet<CbPlanFormDO>(formList).containsAll(oldFormList)) {
            this.cbPlanFDAO.deleteByPlanId(planId);
            this.cbPlanFDAO.batchInsert(formList);
        }
        List<CbPlanToDO> toList = cbPlanDTO.toToList();
        List<CbPlanToDO> oldToList = old.toToList();
        if (toList.size() != oldToList.size() || !new HashSet<CbPlanToDO>(toList).containsAll(oldToList)) {
            this.cbPlanToDAO.deleteByPlanId(planId);
            this.cbPlanToDAO.batchInsert(toList);
        }
        if (cbPlanDTO.isEnabled() && cbPlanDTO.getKind() == 2) {
            this.cbPlanTimeDAO.deleteByPlanId(planId);
            List<CbPlanTimeDO> timeList = cbPlanDTO.getTimes();
            if (CollectionUtils.isEmpty(timeList)) {
                throw new JQException((ErrorEnum)new ErrorEnumImpl("\u672a\u8bbe\u7f6e\u50ac\u62a5\u65f6\u95f4"));
            }
            this.cbPlanTimeDAO.batchInsert(timeList);
            this.resScheduleCb(cbPlanDTO);
        }
    }

    @Override
    public void execPlan(String planId) {
        CbPlanDTO cbPlanDTO = this.queryByPlanId(planId);
        if (cbPlanDTO == null) {
            return;
        }
        CbExecLogDO cbExecLogDO = this.beginLog(cbPlanDTO);
        try {
            if (!this.confirmPeriod(cbPlanDTO, cbExecLogDO)) {
                return;
            }
            this.expandUnit(cbPlanDTO);
            this.expandForm(cbPlanDTO);
            boolean check = this.check(cbPlanDTO, cbExecLogDO);
            if (!check) {
                return;
            }
            List<MessageMerge> messageMerges = this.execCb(cbPlanDTO);
            this.execSend(cbPlanDTO, messageMerges);
            this.endLog(cbExecLogDO);
        }
        catch (Exception e) {
            logger.error("\u50ac\u62a5\u975e\u6b63\u5e38\u6267\u884c\u7ed3\u675f", e);
            cbExecLogDO.setStatus(0);
            cbExecLogDO.setMessage("\u50ac\u62a5\u975e\u6b63\u5e38\u6267\u884c\u7ed3\u675f:" + e.getMessage());
            cbExecLogDO.setEndTime(Timestamp.from(Instant.now()));
            this.cbExecLogDAO.update(cbExecLogDO);
        }
    }

    private void endLog(CbExecLogDO cbExecLogDO) {
        cbExecLogDO.setStatus(1);
        cbExecLogDO.setMessage("\u6b63\u5e38\u6267\u884c\u7ed3\u675f");
        cbExecLogDO.setEndTime(Timestamp.from(Instant.now()));
        this.cbExecLogDAO.update(cbExecLogDO);
    }

    private boolean check(CbPlanDTO cbPlanDTO, CbExecLogDO cbExecLogDO) {
        if (cbPlanDTO.getCurrPeriod() == null) {
            cbExecLogDO.setStatus(0);
            cbExecLogDO.setMessage("\u672a\u786e\u8ba4\u50ac\u62a5\u65f6\u671f\uff0c\u6267\u884c\u7ed3\u675f");
            cbExecLogDO.setEndTime(Timestamp.from(Instant.now()));
            this.cbExecLogDAO.update(cbExecLogDO);
            logger.info(cbPlanDTO.getLogId() + ",\u672a\u786e\u8ba4\u50ac\u62a5\u65f6\u671f\uff0c\u6267\u884c\u7ed3\u675f");
            return false;
        }
        if (CollectionUtils.isEmpty(cbPlanDTO.getUnitMap())) {
            cbExecLogDO.setStatus(0);
            cbExecLogDO.setMessage("\u672a\u786e\u8ba4\u50ac\u62a5\u5355\u4f4d\uff0c\u6267\u884c\u7ed3\u675f");
            cbExecLogDO.setEndTime(Timestamp.from(Instant.now()));
            this.cbExecLogDAO.update(cbExecLogDO);
            logger.info(cbPlanDTO.getLogId() + ",\u672a\u786e\u8ba4\u50ac\u62a5\u5355\u4f4d\uff0c\u6267\u884c\u7ed3\u675f");
            return false;
        }
        if (cbPlanDTO.getWorkFlowType() == null) {
            cbExecLogDO.setStatus(0);
            cbExecLogDO.setMessage("\u672a\u786e\u8ba4\u62a5\u8868\u65b9\u6848\u50ac\u62a5\u6d41\u7a0b\uff0c\u65e0\u6cd5\u8fdb\u884c\u50ac\u62a5\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570");
            cbExecLogDO.setEndTime(Timestamp.from(Instant.now()));
            this.cbExecLogDAO.update(cbExecLogDO);
            logger.info(cbPlanDTO.getLogId() + ",\u672a\u786e\u8ba4\u62a5\u8868\u65b9\u6848\u50ac\u62a5\u6d41\u7a0b\uff0c\u6267\u884c\u7ed3\u675f");
            return false;
        }
        if (cbPlanDTO.getWorkFlowType() == WorkFlowType.FORM && CollectionUtils.isEmpty(cbPlanDTO.getFormMap())) {
            cbExecLogDO.setStatus(0);
            cbExecLogDO.setMessage("\u672a\u67e5\u627e\u5230\u50ac\u62a5\u8868\u5355\uff0c\u6267\u884c\u7ed3\u675f");
            cbExecLogDO.setEndTime(Timestamp.from(Instant.now()));
            this.cbExecLogDAO.update(cbExecLogDO);
            logger.info(cbPlanDTO.getLogId() + ",\u672a\u67e5\u627e\u5230\u50ac\u62a5\u8868\u5355\uff0c\u6267\u884c\u7ed3\u675f");
            return false;
        }
        if (cbPlanDTO.getWorkFlowType() == WorkFlowType.GROUP && CollectionUtils.isEmpty(cbPlanDTO.getGroupMap())) {
            cbExecLogDO.setStatus(0);
            cbExecLogDO.setMessage("\u672a\u67e5\u627e\u5230\u50ac\u62a5\u5206\u7ec4\uff0c\u6267\u884c\u7ed3\u675f");
            cbExecLogDO.setEndTime(Timestamp.from(Instant.now()));
            this.cbExecLogDAO.update(cbExecLogDO);
            logger.info(cbPlanDTO.getLogId() + ",\u672a\u67e5\u627e\u5230\u50ac\u62a5\u5206\u7ec4\uff0c\u6267\u884c\u7ed3\u675f");
            return false;
        }
        return true;
    }

    private CbExecLogDO beginLog(CbPlanDTO cbPlanDTO) {
        cbPlanDTO.setLogId(UUIDUtils.getKey());
        CbExecLogDO cbExecLogDO = new CbExecLogDO();
        cbExecLogDO.setLogId(cbPlanDTO.getLogId());
        cbExecLogDO.setExecUser(NpContextHolder.getContext().getUserId());
        cbExecLogDO.setPlanId(cbPlanDTO.getPlanId());
        cbExecLogDO.setStartTime(Timestamp.from(Instant.now()));
        this.cbExecLogDAO.insert(cbExecLogDO);
        logger.info(cbPlanDTO.getLogId() + ",\u5f00\u59cb\u6267\u884c\u50ac\u62a5\u8ba1\u5212:" + cbPlanDTO.getTitle());
        if (cbPlanDTO.getMode() == 1) {
            logger.info(cbPlanDTO.getLogId() + ",\u5f53\u524d\u6267\u884c\u6a21\u5f0f\u4e3a\u9884\u68c0\u67e5\u6a21\u5f0f\uff0c\u53ea\u6267\u884c\u50ac\u62a5\u4e1a\u52a1\uff0c\u4e0d\u53d1\u9001\u6d88\u606f\uff0c\u7528\u4e8e\u8bbe\u7f6e\u50ac\u62a5\u540e\u68c0\u67e5\u50ac\u62a5\u73af\u5883\u662f\u5426\u6b63\u786e");
        }
        return cbExecLogDO;
    }

    private void execSend(CbPlanDTO cbPlanDTO, List<MessageMerge> messageMerges) {
        IPeriodProvider periodProvider = this.iPeriodEntityAdapter.getPeriodProvider(cbPlanDTO.getDataTime());
        boolean removeCC = false;
        HashSet<String> ccUserIds = new HashSet<String>();
        if (cbPlanDTO.getCcUserIds() != null) {
            ccUserIds.addAll(cbPlanDTO.getCcUserIds());
        }
        if (cbPlanDTO.getCcRoleIds() != null) {
            for (String ccRoleId : cbPlanDTO.getCcRoleIds()) {
                List identityIdByRole = this.roleService.getIdentityIdByRole(ccRoleId);
                ccUserIds.addAll(identityIdByRole);
            }
        }
        cbPlanDTO.setCcUserIds(new ArrayList<String>(ccUserIds));
        for (MessageMerge merge : messageMerges) {
            List<ReminderMessage> messages = this.getReminderMessage(merge, cbPlanDTO, (IPeriodAdapter)periodProvider);
            for (ReminderMessage message : messages) {
                this.getUserCache(cbPlanDTO, message);
                this.removeNotEnableUser(cbPlanDTO, message, removeCC);
                removeCC = true;
                this.sendTodo(cbPlanDTO, message);
                this.sendMail(cbPlanDTO, message);
                this.sendNvwaMessage(cbPlanDTO, message);
            }
        }
    }

    private void removeNotEnableUser(CbPlanDTO cbPlanDTO, ReminderMessage message, boolean removeCC) {
        List<String> userIds = message.getUserIds();
        Iterator<String> iterator = userIds.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            User user = cbPlanDTO.getUserMap().get(next);
            if (user != null && user.isEnabled()) continue;
            iterator.remove();
        }
        if (removeCC) {
            return;
        }
        List<String> ccUserIds = cbPlanDTO.getCcUserIds();
        if (ccUserIds == null) {
            return;
        }
        iterator = ccUserIds.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            User user = cbPlanDTO.getUserMap().get(next);
            if (user != null && user.isEnabled()) continue;
            iterator.remove();
        }
    }

    private void getUserCache(CbPlanDTO cbPlanDTO, ReminderMessage message) {
        List<String> userIds = message.getUserIds();
        ArrayList<String> unCacheUser = new ArrayList<String>();
        for (String string : userIds) {
            User user = cbPlanDTO.getUserMap().get(string);
            if (user != null) continue;
            unCacheUser.add(string);
        }
        List<String> ccUserIds = cbPlanDTO.getCcUserIds();
        if (ccUserIds != null) {
            for (String ccUserId : ccUserIds) {
                User user = cbPlanDTO.getUserMap().get(ccUserId);
                if (user != null) continue;
                unCacheUser.add(ccUserId);
            }
        }
        List list = this.nvwaUserClient.get(unCacheUser);
        for (UserDTO userDTO : list) {
            cbPlanDTO.getUserMap().put(userDTO.getId(), (User)userDTO);
        }
    }

    private List<ReminderMessage> getReminderMessage(MessageMerge value, CbPlanDTO cbPlanDTO, IPeriodAdapter periodAdapter) {
        String content = cbPlanDTO.getContent();
        ArrayList<ReminderMessage> messages = new ArrayList<ReminderMessage>();
        if (content.contains("{\u6536\u4fe1\u4eba}") || content.contains("{recipient}")) {
            Map<String, User> userMap = cbPlanDTO.getUserMap();
            for (String userId : value.getUserIds()) {
                ReminderMessage message = this.structureMessage(value, cbPlanDTO, periodAdapter);
                message.setUserIds(Collections.singletonList(userId));
                content = message.getContent();
                String fullname = userMap.get(userId).getFullname();
                content = content.replace("{recipient}", fullname);
                content = content.replace("{\u6536\u4fe1\u4eba}", fullname);
                message.setContent(content);
                messages.add(message);
            }
        } else {
            ReminderMessage message = this.structureMessage(value, cbPlanDTO, periodAdapter);
            message.setUserIds(value.getUserIds());
            messages.add(message);
        }
        return messages;
    }

    private ReminderMessage structureMessage(MessageMerge value, CbPlanDTO cbPlanDTO, IPeriodAdapter periodAdapter) {
        String content = cbPlanDTO.getContent();
        ReminderMessage message = new ReminderMessage();
        IEntityRow iEntityRow = cbPlanDTO.getUnitMap().get(value.getUnitId());
        message.setSendChannels(cbPlanDTO.getSendChannels());
        Set<String> careKeys = value.getCareKeys();
        message.setCareKeys(careKeys);
        message.setUnitId(value.getUnitId());
        if (content.contains("{\u5355\u4f4d}") || content.contains("{company}") || content.contains("{unitId}")) {
            StringBuilder careTitleBur = new StringBuilder();
            careTitleBur.append(iEntityRow.getTitle());
            int max = 3;
            if (!CollectionUtils.isEmpty(careKeys)) {
                careTitleBur.append(",");
                for (String careKey : careKeys) {
                    if (max == 0) break;
                    String careTitle = null;
                    if (cbPlanDTO.getWorkFlowType() == WorkFlowType.FORM) {
                        careTitle = cbPlanDTO.getFormMap().get(careKey).getTitle();
                    } else if (cbPlanDTO.getWorkFlowType() == WorkFlowType.GROUP) {
                        careTitle = cbPlanDTO.getGroupMap().get(careKey).getTitle();
                    }
                    if (careTitle == null) continue;
                    careTitleBur.append(careTitle).append("\u3001");
                    max = (byte)(max - 1);
                }
                careTitleBur.setLength(careTitleBur.length() - 1);
                if (careKeys.size() > max) {
                    careTitleBur.append(" \u7b49\u5171").append(careKeys.size());
                    if (cbPlanDTO.getWorkFlowType() == WorkFlowType.FORM) {
                        careTitleBur.append("\u5f20\u62a5\u8868");
                    } else if (cbPlanDTO.getWorkFlowType() == WorkFlowType.GROUP) {
                        careTitleBur.append("\u4e2a\u5206\u7ec4");
                    }
                }
            }
            String careTitle = careTitleBur.toString();
            content = content.replace("{unitId}", careTitle);
            content = content.replace("{\u5355\u4f4d}", careTitle);
            content = content.replace("{company}", careTitle);
        }
        if (content.contains("{\u65f6\u671f}") || content.contains("{period}")) {
            String periodTitle;
            String period = cbPlanDTO.getCurrPeriod().toString();
            try {
                periodTitle = periodAdapter.getPeriodTitle(period);
            }
            catch (Exception e) {
                periodTitle = period;
                logger.warn("{},\u6267\u884c\u50ac\u62a5\u8ba1\u5212\u9047\u5230\u975e\u4e2d\u65ad\u9519\u8bef", (Object)cbPlanDTO.getLogId(), (Object)e);
            }
            content = content.replace("{period}", periodTitle);
            content = content.replace("{\u65f6\u671f}", periodTitle);
        }
        message.setContent(content);
        return message;
    }

    private boolean confirmPeriod(CbPlanDTO cbPlanDTO, CbExecLogDO cbExecLogDO) {
        String formSchemeId = cbPlanDTO.getFormSchemeId();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeId);
        String dw = CbUtils.getContextMainDimId(formScheme.getDw());
        cbPlanDTO.setDw(dw);
        cbPlanDTO.setDataTime(formScheme.getDateTime());
        IPeriodProvider periodProvider = this.iPeriodEntityAdapter.getPeriodProvider(cbPlanDTO.getDataTime());
        PeriodWrapper currPeriod = null;
        if (cbPlanDTO.getKind() == 2) {
            IPeriodRow curPeriod = periodProvider.getCurPeriod();
            String code = curPeriod.getCode();
            currPeriod = new PeriodWrapper(code);
            int periodOffset = formScheme.getPeriodOffset();
            if (periodOffset != 0) {
                PeriodModifier offset = PeriodModifier.parse((String)(String.valueOf(periodOffset) + (char)PeriodConsts.typeToCode((int)currPeriod.getType())));
                periodProvider.modify(currPeriod, offset);
            }
        } else {
            try {
                String execPeriod = cbPlanDTO.getExecPeriod();
                currPeriod = new PeriodWrapper(execPeriod);
            }
            catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        }
        boolean cbFlag = false;
        if (currPeriod != null) {
            cbPlanDTO.setCurrPeriod(currPeriod);
            logger.info("{},\u786e\u8ba4\u50ac\u62a5\u65f6\u671f:{}", (Object)cbPlanDTO.getLogId(), (Object)currPeriod);
            try {
                Date[] currPeriodDate = periodProvider.getPeriodDateRegion(currPeriod.toString());
                cbPlanDTO.setCurrPeriodDate(currPeriodDate);
                if (currPeriodDate != null && currPeriodDate.length > 1) {
                    cbFlag = true;
                }
            }
            catch (ParseException e) {
                logger.warn(e.getMessage(), e);
            }
        }
        if (!cbFlag) {
            logger.info("{},\u672a\u67e5\u627e\u5230\u53ef\u6267\u884c\u50ac\u62a5\u7684\u65f6\u671f {}", (Object)cbPlanDTO.getLogId(), (Object)cbPlanDTO.getCurrPeriod());
            cbExecLogDO.setStatus(0);
            cbExecLogDO.setMessage("\u672a\u786e\u8ba4\u50ac\u62a5\u65f6\u671f\uff0c\u6267\u884c\u7ed3\u675f");
            cbExecLogDO.setEndTime(Timestamp.from(Instant.now()));
            this.cbExecLogDAO.update(cbExecLogDO);
            logger.info("{},\u672a\u786e\u8ba4\u50ac\u62a5\u65f6\u671f\uff0c\u6267\u884c\u7ed3\u675f", (Object)cbPlanDTO.getLogId());
            return false;
        }
        return true;
    }

    private void expandUnit(CbPlanDTO cbPlanDTO) {
        IEntityTable iEntityTable;
        logger.info(cbPlanDTO.getLogId() + ",\u5f00\u59cb\u786e\u8ba4\u50ac\u62a5\u5355\u4f4d");
        int unitScop = cbPlanDTO.getUnitScop();
        if (2 == unitScop || 1 == unitScop) {
            String execUnit = cbPlanDTO.getExecUnit();
            try {
                boolean canReadRoot = this.entityAuthorityService.canReadEntity(cbPlanDTO.getDw(), execUnit, cbPlanDTO.getCurrPeriodDate()[0], cbPlanDTO.getCurrPeriodDate()[1]);
                if (!canReadRoot) {
                    logger.info("{},\u5bf9\u50ac\u62a5\u6267\u884c\u5355\u4f4d\u65e0\u6743\u9650", (Object)cbPlanDTO.getLogId());
                    return;
                }
            }
            catch (UnauthorizedEntityException e) {
                throw new RuntimeException(e);
            }
        }
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setAuthorityOperations(AuthorityType.Read);
        DimensionValueSet masterKeys = new DimensionValueSet();
        PeriodWrapper currPeriod = cbPlanDTO.getCurrPeriod();
        masterKeys.setValue("DATATIME", (Object)currPeriod.toString());
        query.setMasterKeys(masterKeys);
        String formSchemeId = cbPlanDTO.getFormSchemeId();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeId);
        EntityViewDefine viewDefine = this.runTimeViewController.getViewByFormSchemeKey(cbPlanDTO.getFormSchemeId());
        query.setEntityView(viewDefine);
        try {
            iEntityTable = this.executeEntityReader(query, viewDefine, this.executorContext(formScheme), formScheme);
        }
        catch (Exception e) {
            logger.error("\u786e\u8ba4\u50ac\u62a5\u5355\u4f4d\u5931\u8d25", e);
            return;
        }
        Map<String, Object> map = new HashMap<String, IEntityRow>();
        List cbUnits = null;
        if (2 == unitScop) {
            cbUnits = iEntityTable.getAllChildRows(cbPlanDTO.getExecUnit());
        } else if (1 == unitScop) {
            cbUnits = iEntityTable.getChildRows(cbPlanDTO.getExecUnit());
        } else {
            map = iEntityTable.quickFindByEntityKeys(new HashSet<String>(cbPlanDTO.getUnitIds()));
        }
        if (cbUnits != null) {
            for (IEntityRow cbUnit : cbUnits) {
                map.put(cbUnit.getEntityKeyData(), cbUnit);
            }
        }
        cbPlanDTO.setUnitMap(map);
        logger.info(cbPlanDTO.getLogId() + ",\u786e\u8ba4\u50ac\u62a5\u5355\u4f4d" + map.size() + "\u5bb6");
    }

    private IEntityTable executeEntityReader(IEntityQuery entityQuery, EntityViewDefine entityView, ExecutorContext executorContext, FormSchemeDefine formScheme) {
        try {
            String formSchemeKey = null;
            if (formScheme != null) {
                formSchemeKey = formScheme.getKey();
            }
            return this.dataEntityFullService.executeEntityReader(entityQuery, executorContext, entityView, formSchemeKey).getEntityTable();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void expandForm(CbPlanDTO cbPlanDTO) {
        if (cbPlanDTO.getWorkFlowType() == null || cbPlanDTO.getWorkFlowType() == WorkFlowType.ENTITY) {
            return;
        }
        logger.info(cbPlanDTO.getLogId() + ",\u5f00\u59cb\u786e\u8ba4\u50ac\u62a5\u5206\u7ec4/\u8868\u5355");
        if (cbPlanDTO.getWorkFlowType() == WorkFlowType.FORM) {
            int formScop = cbPlanDTO.getFormScop();
            HashSet<String> formKeySet = new HashSet<String>();
            if (formScop == 2) {
                List<String> formIds = cbPlanDTO.getFormIds();
                formKeySet.addAll(formIds);
            }
            List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(cbPlanDTO.getFormSchemeId());
            formDefines.sort((o1, o2) -> {
                if (o1.getOrder() == null) {
                    return 1;
                }
                return o1.getOrder().compareTo(o2.getOrder());
            });
            LinkedHashMap<String, FormDefine> formDefineMap = new LinkedHashMap<String, FormDefine>();
            for (FormDefine formDefine : formDefines) {
                if (formScop == 2) {
                    if (!formKeySet.contains(formDefine.getKey())) continue;
                    formDefineMap.put(formDefine.getKey(), formDefine);
                    continue;
                }
                formDefineMap.put(formDefine.getKey(), formDefine);
            }
            cbPlanDTO.setFormMap(formDefineMap);
            logger.info(cbPlanDTO.getLogId() + ",\u50ac\u62a5\u8868\u5355\u5171" + formDefineMap.size() + "\u4e2a");
        } else if (cbPlanDTO.getWorkFlowType() == WorkFlowType.GROUP) {
            int formScop = cbPlanDTO.getFormScop();
            HashSet<String> formKeySet = new HashSet<String>();
            if (formScop == 2) {
                List<String> formIds = cbPlanDTO.getFormIds();
                formKeySet.addAll(formIds);
            }
            List formGroupDefines = this.runTimeViewController.getAllFormGroupsInFormScheme(cbPlanDTO.getFormSchemeId());
            formGroupDefines.sort((o1, o2) -> {
                if (o1.getOrder() == null) {
                    return 1;
                }
                return o1.getOrder().compareTo(o2.getOrder());
            });
            LinkedHashMap<String, FormGroupDefine> formDefineMap = new LinkedHashMap<String, FormGroupDefine>();
            for (FormGroupDefine groupDefine : formGroupDefines) {
                if (formScop == 2) {
                    if (!formKeySet.contains(groupDefine.getKey())) continue;
                    formDefineMap.put(groupDefine.getKey(), groupDefine);
                    continue;
                }
                formDefineMap.put(groupDefine.getKey(), groupDefine);
            }
            cbPlanDTO.setGroupMap(formDefineMap);
            HashMap<String, List<String>> group2FormMap = new HashMap<String, List<String>>();
            for (FormGroupDefine value : formDefineMap.values()) {
                try {
                    List forms = this.runTimeViewController.getAllFormsInGroup(value.getKey());
                    group2FormMap.put(value.getKey(), forms.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
                }
                catch (Exception e) {
                    logger.error("\u50ac\u62a5\u67e5\u8be2\u5206\u7ec4\u4e0b\u8868\u5355\u51fa\u73b0\u95ee\u9898\uff0c\u7ec8\u6b62\u50ac\u62a5", e);
                    throw new RuntimeException(e);
                }
            }
            cbPlanDTO.setGroup2FormMap(group2FormMap);
            logger.info(cbPlanDTO.getLogId() + ",\u50ac\u62a5\u5206\u7ec4\u5171" + formDefineMap.size() + "\u4e2a");
        }
    }

    private ExecutorContext executorContext(FormSchemeDefine formScheme) {
        ExecutorContext context = new ExecutorContext(this.runtimeController);
        context.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.runTimeViewController, this.runtimeController, this.viewRtCtl, formScheme.getKey()));
        context.setPeriodView(formScheme.getDateTime());
        return context;
    }

    private List<MessageMerge> execCb(CbPlanDTO cbPlanDTO) {
        logger.info(cbPlanDTO.getLogId() + ",\u5f00\u59cb\u6267\u884c\u50ac\u62a5\u4e1a\u52a1\u5224\u65ad");
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(cbPlanDTO.getFormSchemeId());
        String contextMainDimId = CbUtils.getContextMainDimId(formScheme.getDw());
        String dwDimName = this.entityMetaService.getDimensionName(contextMainDimId);
        cbPlanDTO.setDwDimName(dwDimName);
        DimensionValueSet dim = new DimensionValueSet();
        dim.setValue("DATATIME", (Object)cbPlanDTO.getCurrPeriod().toString());
        ArrayList<MessageMerge> list = new ArrayList<MessageMerge>();
        DimensionProviderData dimensionProviderData = new DimensionProviderData();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        dimensionProviderData.setDataSchemeKey(taskDefine.getDataScheme());
        VariableDimensionValueProvider provider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDIMBYDW", dimensionProviderData);
        String dims = formScheme.getDims();
        LinkedHashMap<String, String> entityDImNameMap = new LinkedHashMap<String, String>();
        if (StringUtils.hasLength(dims)) {
            String[] dimsArr;
            for (String dimStr : dimsArr = dims.split(";")) {
                entityDImNameMap.put(dimStr, this.getDimensionName(dimStr));
            }
        }
        cbPlanDTO.setDimsNameMap(entityDImNameMap);
        cbPlanDTO.setCanCache(new HashMap<String, Set<String>>());
        for (Map.Entry<String, IEntityRow> unitEntry : cbPlanDTO.getUnitMap().entrySet()) {
            String unitId = unitEntry.getKey();
            dim.setValue(dwDimName, (Object)unitId);
            Collection<MessageMerge> messageMerges = this.cbByWorkFlowType(cbPlanDTO, dim, unitId, provider);
            list.addAll(messageMerges);
        }
        logger.info(cbPlanDTO.getLogId() + ",\u6536\u96c6\u5230" + list.size() + "\u6761\u50ac\u62a5\u6d88\u606f\u53d1\u9001\u4efb\u52a1");
        return list;
    }

    private Collection<MessageMerge> cbByWorkFlowType(CbPlanDTO cbPlanDTO, DimensionValueSet dim, String unitId, VariableDimensionValueProvider provider) {
        List<String> careKeys;
        IEntityRow execUnit = cbPlanDTO.getUnitMap().get(unitId);
        DataEntryParam param = new DataEntryParam();
        param.setDim(dim);
        param.setFormSchemeKey(cbPlanDTO.getFormSchemeId());
        if (cbPlanDTO.getWorkFlowType() == WorkFlowType.GROUP) {
            Map<String, FormGroupDefine> groupMap = cbPlanDTO.getGroupMap();
            careKeys = new LinkedList<String>(groupMap.keySet());
        } else if (cbPlanDTO.getWorkFlowType() == WorkFlowType.FORM) {
            Map<String, FormDefine> formMap = cbPlanDTO.getFormMap();
            careKeys = new LinkedList<String>(formMap.keySet());
        } else if (cbPlanDTO.getWorkFlowType() == WorkFlowType.ENTITY) {
            careKeys = Collections.singletonList(cbPlanDTO.getFormSchemeId());
        } else {
            return Collections.emptyList();
        }
        if (cbPlanDTO.getWorkFlowType() != WorkFlowType.ENTITY) {
            List<String> formIds;
            DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
            for (int i = 0; i < dim.size(); ++i) {
                if (cbPlanDTO.getDwDimName().equals(dim.getName(i))) {
                    builder.setEntityValue(dim.getName(i), cbPlanDTO.getDw(), new Object[]{dim.getValue(i)});
                    continue;
                }
                builder.setEntityValue(dim.getName(i), "", new Object[]{dim.getValue(i)});
            }
            Map<String, String> dimsNameMap = cbPlanDTO.getDimsNameMap();
            dimsNameMap.forEach((entityId, entityName) -> builder.addVariableDimension(entityName, entityId, provider));
            if (cbPlanDTO.getWorkFlowType() == WorkFlowType.FORM) {
                formIds = careKeys;
            } else if (cbPlanDTO.getWorkFlowType() == WorkFlowType.GROUP) {
                HashSet<String> formSetIds = new HashSet<String>();
                Map<String, List<String>> group2FormMap = cbPlanDTO.getGroup2FormMap();
                for (String careKey : careKeys) {
                    List<String> formKeys = group2FormMap.get(careKey);
                    if (formKeys == null) continue;
                    formSetIds.addAll(formKeys);
                }
                formIds = new ArrayList<String>(formSetIds);
            } else {
                throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u7684\u50ac\u62a5\u65b9\u5f0f");
            }
            DimensionCollection collection = builder.getCollection();
            EvaluatorParam evaluatorParam = new EvaluatorParam();
            evaluatorParam.setFormSchemeId(cbPlanDTO.getFormSchemeId());
            evaluatorParam.setTaskId(cbPlanDTO.getTaskId());
            DataPermissionEvaluator evaluator = this.dataPermissionEvaluatorFactory.createEvaluator(evaluatorParam, collection, formIds);
            List dimensionCombinations = collection.getDimensionCombinations();
            Iterator<String> iterator = careKeys.iterator();
            while (iterator.hasNext()) {
                DimensionCombination combination;
                String careKey = iterator.next();
                if (cbPlanDTO.getWorkFlowType() == WorkFlowType.GROUP) {
                    Map<String, List<String>> group2FormMap = cbPlanDTO.getGroup2FormMap();
                    List<String> formKeys = group2FormMap.get(careKey);
                    boolean canAccess = false;
                    block3: for (String formKey : formKeys) {
                        for (DimensionCombination combination2 : dimensionCombinations) {
                            canAccess = evaluator.haveAccess(combination2, formKey, AuthType.READABLE);
                            if (!canAccess) continue;
                            break block3;
                        }
                    }
                    if (canAccess) continue;
                    this.addSendUnitLog(cbPlanDTO, execUnit.getEntityKeyData(), careKey, "\u5206\u7ec4\u4e0d\u53ef\u89c1");
                    iterator.remove();
                    continue;
                }
                boolean canAccess = false;
                Iterator iterator2 = dimensionCombinations.iterator();
                while (iterator2.hasNext() && !(canAccess = evaluator.haveAccess(combination = (DimensionCombination)iterator2.next(), careKey, AuthType.READABLE))) {
                }
                if (canAccess) continue;
                this.addSendUnitLog(cbPlanDTO, execUnit.getEntityKeyData(), careKey, "\u8868\u5355\u4e0d\u53ef\u89c1");
                iterator.remove();
            }
        }
        HashMap<String, MessageMerge> undoMessage = new HashMap<String, MessageMerge>();
        for (String careKey : careKeys) {
            if (cbPlanDTO.getWorkFlowType() == WorkFlowType.GROUP) {
                param.setGroupKey(careKey);
            } else if (cbPlanDTO.getWorkFlowType() == WorkFlowType.FORM) {
                param.setFormKey(careKey);
            }
            ActionStateBean actionState = this.iDataentryFlowService.queryReportState(param);
            if (actionState == null) {
                this.addSendUnitLog(cbPlanDTO, execUnit.getEntityKeyData(), careKey, "\u6d41\u7a0b\u72b6\u6001\u672a\u5f00\u542f\uff0c\u8df3\u8fc7\u50ac\u62a5");
                continue;
            }
            if (!"UPLOADED".equals(actionState.getCode()) && !"CONFIRMED".equals(actionState.getCode())) {
                Set<String> tempUser = null;
                int userType = cbPlanDTO.getUserType();
                if (2 == userType) {
                    tempUser = this.participantService.collectUserId(cbPlanDTO, null, unitId, careKey, cbPlanDTO.getCanCache());
                } else if (1 == userType) {
                    tempUser = this.participantService.collectUserId(cbPlanDTO, unitId, careKey, cbPlanDTO.getCanCache());
                } else if (8 == userType) {
                    tempUser = this.participantService.collectUserId(cbPlanDTO, cbPlanDTO.getRoleIds(), unitId, careKey, cbPlanDTO.getCanCache());
                }
                if (CollectionUtils.isEmpty(tempUser)) {
                    this.addSendUnitLog(cbPlanDTO, execUnit.getEntityKeyData(), careKey, "\u672a\u5339\u914d\u5230\u53ef\u4ee5\u63a5\u6536\u50ac\u62a5\u7684\u7528\u6237\uff0c\u8df3\u8fc7\u50ac\u62a5");
                    continue;
                }
                for (String userId : tempUser) {
                    MessageMerge temp = (MessageMerge)undoMessage.get(userId);
                    if (temp == null) {
                        temp = new MessageMerge();
                        temp.setUserId(userId);
                        temp.setUnitId(unitId);
                        temp.setCareKeys(new LinkedHashSet<String>());
                        undoMessage.put(userId, temp);
                    }
                    if (cbPlanDTO.getWorkFlowType() == WorkFlowType.ENTITY) continue;
                    temp.getCareKeys().add(careKey);
                }
                continue;
            }
            this.addSendUnitLog(cbPlanDTO, execUnit.getEntityKeyData(), careKey, "\u5df2\u4e0a\u62a5\uff0c\u8df3\u8fc7\u50ac\u62a5");
        }
        return MessageMerge.merge(undoMessage.values());
    }

    private void addSendUnitLog(CbPlanDTO cbPlanDTO, String unitId, String careKey, String message) {
        CbPlanLogDO log = CbPlanServiceImpl.buildLogDO(cbPlanDTO, unitId, careKey, null, null, 0, message);
        this.cbPlanLogDAO.insert(log);
    }

    @NotNull
    private static CbPlanLogDO buildLogDO(CbPlanDTO cbPlanDTO, String unitId, String careKey, String userId, String channel, int status, String message) {
        String careTitle = null;
        if (cbPlanDTO.getWorkFlowType() == WorkFlowType.GROUP) {
            FormGroupDefine groupDefine = cbPlanDTO.getGroupMap().get(careKey);
            careTitle = groupDefine.getTitle();
        } else if (cbPlanDTO.getWorkFlowType() == WorkFlowType.FORM) {
            FormDefine formDefine = cbPlanDTO.getFormMap().get(careKey);
            careTitle = formDefine.getTitle();
        }
        IEntityRow iEntityRow = cbPlanDTO.getUnitMap().get(unitId);
        String unitTitle = iEntityRow.getTitle();
        CbPlanLogDO log = new CbPlanLogDO();
        log.setLogId(cbPlanDTO.getLogId());
        log.setId(UUIDUtils.getKey());
        log.setDataTime(cbPlanDTO.getCurrPeriod().toString());
        log.setUnit(unitTitle);
        log.setUnitCode(unitId);
        log.setForm(careTitle);
        log.setFormKey(careKey);
        log.setStatus(status);
        log.setMessage(message);
        if (userId != null) {
            User user = cbPlanDTO.getUserMap().get(userId);
            if (user != null) {
                String name = user.getNickname();
                log.setRecipientId(name);
            } else {
                log.setRecipientId(userId);
            }
        }
        log.setSendTime(Timestamp.from(Instant.now()));
        log.setChannel(channel);
        log.setOrder(OrderGenerator.newOrder());
        return log;
    }

    private void sendMail(CbPlanDTO cbPlanDTO, ReminderMessage message) {
        UserDO userDO;
        User user;
        if (!cbPlanDTO.getSendChannels().contains(VaMessageOption.MsgChannel.EMAIL.toString()) && !cbPlanDTO.getSendChannels().contains("2")) {
            return;
        }
        if (cbPlanDTO.getMode() == 1) {
            return;
        }
        SendMailCustomDTO sendMailCustomDTO = new SendMailCustomDTO();
        sendMailCustomDTO.setTitle("\u50ac\u62a5\u901a\u77e5");
        sendMailCustomDTO.setContent(message.getContent());
        ArrayList<UserDO> users = new ArrayList<UserDO>();
        ArrayList<UserDO> ccUsers = new ArrayList<UserDO>();
        for (String userId : message.getUserIds()) {
            user = cbPlanDTO.getUserMap().get(userId);
            userDO = new UserDO();
            userDO.setId(user.getId());
            userDO.setName(user.getName());
            userDO.setUsername(user.getName());
            userDO.setTelephone(user.getTelephone());
            userDO.setEmail(user.getEmail());
            users.add(userDO);
        }
        if (cbPlanDTO.getCcUserIds() != null) {
            for (String userId : cbPlanDTO.getCcUserIds()) {
                user = cbPlanDTO.getUserMap().get(userId);
                userDO = new UserDO();
                userDO.setId(user.getId());
                userDO.setName(user.getName());
                userDO.setUsername(user.getName());
                userDO.setTelephone(user.getTelephone());
                userDO.setEmail(user.getEmail());
                ccUsers.add(userDO);
            }
        }
        sendMailCustomDTO.setUsers(users);
        sendMailCustomDTO.setCcUsers(ccUsers);
        try {
            R r = this.vaMailService.sendMessageCustom(sendMailCustomDTO);
            int code = r.getCode();
            if (code != 0) {
                this.addSendUnitLog(cbPlanDTO, message.getUserIds(), message.getCareKeys(), message.getUnitId(), VaMessageOption.MsgChannel.EMAIL.toString(), 0, r.getMsg());
            } else {
                this.addSendUnitLog(cbPlanDTO, message.getUserIds(), message.getCareKeys(), message.getUnitId(), VaMessageOption.MsgChannel.EMAIL.toString(), 1, null);
            }
        }
        catch (Exception e) {
            logger.warn("\u53d1\u9001\u5f02\u5e38", e);
            this.addSendUnitLog(cbPlanDTO, message.getUserIds(), message.getCareKeys(), message.getUnitId(), VaMessageOption.MsgChannel.EMAIL.toString(), 0, e.getMessage());
        }
    }

    private StringBuilder getUserTitles(CbPlanDTO cbPlanDTO, List<String> userIds) {
        StringBuilder userTitle = new StringBuilder();
        for (String userId : userIds) {
            User user = cbPlanDTO.getUserMap().get(userId);
            userTitle.append(user.getName()).append(",");
        }
        return userTitle;
    }

    private void sendTodo(CbPlanDTO cbPlanDTO, ReminderMessage message) {
        if (!cbPlanDTO.getSendChannels().contains(VaMessageOption.MsgChannel.PC.toString()) && !cbPlanDTO.getSendChannels().contains("1")) {
            return;
        }
        if (cbPlanDTO.getMode() == 1) {
            return;
        }
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(UUIDUtils.getKey());
        messageDTO.setType(Integer.valueOf(CbUtils.MSG_TYPE));
        messageDTO.setTitle("\u50ac\u62a5\u901a\u77e5");
        messageDTO.setParticipantType(ParticipantTypeEnum.USER.getCode());
        messageDTO.setParticipants(message.getUserIds());
        messageDTO.setContent(message.getContent());
        messageDTO.setCcParticipantType(ParticipantTypeEnum.USER.getCode());
        messageDTO.setType(MessageTypeEnum.TODO.getCode());
        messageDTO.setHandleMode(Collections.singletonList(HandleModeEnum.SYSTEM.getCode()));
        messageDTO.setValidTime(Instant.now());
        try {
            boolean send = this.messagePipelineService.send(messageDTO);
            if (!send) {
                this.addSendUnitLog(cbPlanDTO, message.getUserIds(), message.getCareKeys(), message.getUnitId(), VaMessageOption.MsgChannel.PC.toString(), 0, "\u53d1\u9001\u5f85\u529e\u5f02\u5e38");
            } else {
                this.addSendUnitLog(cbPlanDTO, message.getUserIds(), message.getCareKeys(), message.getUnitId(), VaMessageOption.MsgChannel.PC.toString(), 1, "\u5f85\u529e");
            }
        }
        catch (Exception e) {
            logger.error("\u53d1\u9001\u901a\u9053\u5f02\u5e38", e);
            this.addSendUnitLog(cbPlanDTO, message.getUserIds(), message.getCareKeys(), message.getUnitId(), VaMessageOption.MsgChannel.PC.toString(), 0, "\u53d1\u9001\u5f85\u529e\u5f02\u5e38" + e.getMessage());
        }
    }

    private void addSendUnitLog(CbPlanDTO cbPlanDTO, List<String> userIds, Set<String> careKeys, String unitId, String channel, int status, String msg) {
        ArrayList<CbPlanLogDO> list = new ArrayList<CbPlanLogDO>();
        for (String userId : userIds) {
            if (CollectionUtils.isEmpty(careKeys)) {
                CbPlanLogDO cbPlanLogDO = CbPlanServiceImpl.buildLogDO(cbPlanDTO, unitId, null, userId, channel, status, msg);
                list.add(cbPlanLogDO);
                continue;
            }
            for (String careKey : careKeys) {
                CbPlanLogDO cbPlanLogDO = CbPlanServiceImpl.buildLogDO(cbPlanDTO, unitId, careKey, userId, channel, status, msg);
                list.add(cbPlanLogDO);
            }
        }
        this.cbPlanLogDAO.batchInsert(list);
    }

    private void sendNvwaMessage(CbPlanDTO cbPlanDTO, ReminderMessage message) {
        for (String channel : cbPlanDTO.getSendChannels()) {
            this.sendNvwaMessage(cbPlanDTO, message, channel);
        }
    }

    private void sendNvwaMessage(CbPlanDTO cbPlanDTO, ReminderMessage message, String channel) {
        VaMessageOption.MsgChannel msgChannel;
        try {
            msgChannel = VaMessageOption.MsgChannel.valueOf((String)channel);
            if (VaMessageOption.MsgChannel.EMAIL.equals((Object)msgChannel)) {
                return;
            }
        }
        catch (Exception e) {
            return;
        }
        if (cbPlanDTO.getMode() == 1) {
            return;
        }
        ChannelParam param = new ChannelParam();
        param.setPlanId(cbPlanDTO.getPlanId());
        param.setTaskId(cbPlanDTO.getTaskId());
        param.setFormSchemeId(cbPlanDTO.getFormSchemeId());
        param.setExecUnit(message.getUnitId());
        param.setExecPeriod(cbPlanDTO.getExecPeriod());
        param.setExecFormIds(message.getCareKeys());
        param.setSource(cbPlanDTO.getSource());
        String paramStr = JSONUtil.toJSONString((Object)param);
        VaMessageSendDTO dto = new VaMessageSendDTO();
        dto.setParam(paramStr);
        dto.setGrouptype("\u901a\u77e5");
        dto.setMsgtype("\u50ac\u62a5\u901a\u77e5");
        List<String> userIds = message.getUserIds();
        dto.setReceiveUserIds(userIds);
        dto.setMsgChannel(msgChannel);
        String content = message.getContent();
        String removeTagMsg = CbUtils.removeTag(content);
        if (removeTagMsg.length() > 200) {
            removeTagMsg = removeTagMsg.substring(0, 190) + "......";
        }
        dto.setContent(content);
        dto.setTitle(removeTagMsg);
        try {
            R r = this.vaMessageClient.addMsg(dto);
            int code = r.getCode();
            if (code != 0) {
                this.addSendUnitLog(cbPlanDTO, message.getUserIds(), message.getCareKeys(), message.getUnitId(), channel, 0, r.getMsg());
            } else {
                this.addSendUnitLog(cbPlanDTO, message.getUserIds(), message.getCareKeys(), message.getUnitId(), channel, 1, null);
            }
        }
        catch (Exception e) {
            logger.warn("\u53d1\u9001\u5f02\u5e38", e);
            this.addSendUnitLog(cbPlanDTO, message.getUserIds(), message.getCareKeys(), message.getUnitId(), channel, 0, e.getMessage());
        }
    }

    public String getDimensionName(String entityKey) {
        String dimensionName = "ADJUST".equals(entityKey) ? "ADJUST" : this.entityMetaService.getDimensionName(entityKey);
        return dimensionName;
    }

    @Override
    public CbPlanDTO queryByPlanId(String planId) {
        CbPlanDO cbPlanDO = this.cbPlanDAO.queryCbPlanById(planId);
        if (cbPlanDO == null) {
            return null;
        }
        CbPlanDTO cbPlanDTO = new CbPlanDTO();
        cbPlanDTO.fromCbPlanDO(cbPlanDO);
        cbPlanDTO.fromDwList(this.cbPlanDwDAO.queryByPlanId(planId));
        if (cbPlanDTO.getWorkFlowType() != WorkFlowType.ENTITY) {
            cbPlanDTO.fromFormList(this.cbPlanFDAO.queryByPlanId(planId));
        }
        cbPlanDTO.fromToList(this.cbPlanToDAO.queryByPlanId(planId));
        if (cbPlanDO.getKind() == 2) {
            List<CbPlanTimeDO> list = this.cbPlanTimeDAO.queryByPlanId(planId);
            cbPlanDTO.setTimes(list);
        }
        return cbPlanDTO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void enablePlan(String planId) throws JQException {
        CbPlanDTO cbPlanDTO = this.queryByPlanId(planId);
        if (cbPlanDTO != null) {
            CbPlanDO cbPlanDO = cbPlanDTO.toCbPlanDO();
            cbPlanDO.setEnabled(1);
            cbPlanDO.setUpdateUser(NpContextHolder.getContext().getUserId());
            this.cbPlanDAO.updateCbPlanById(cbPlanDO);
            if (cbPlanDO.getKind() == 2) {
                try {
                    this.resScheduleCb(cbPlanDTO);
                }
                catch (Exception e) {
                    logger.error("\u4fee\u6539\u50ac\u62a5\u8ba1\u5212\u4efb\u52a1\u5931\u8d25", e);
                    throw new JQException((ErrorEnum)new ErrorEnumImpl("\u542f\u7528\u50ac\u62a5\u8ba1\u5212\u4efb\u52a1\u5931\u8d25"), (Throwable)e);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void disablePlan(String planId) throws JQException {
        CbPlanDO cbPlanDO = this.cbPlanDAO.queryCbPlanById(planId);
        if (cbPlanDO != null) {
            cbPlanDO.setEnabled(0);
            cbPlanDO.setUpdateUser(NpContextHolder.getContext().getUserId());
            this.cbPlanDAO.updateCbPlanById(cbPlanDO);
            if (cbPlanDO.getKind() == 2) {
                try {
                    this.cbPlanScheduleJobManager.deleteJob(planId);
                }
                catch (Exception e) {
                    logger.error("\u4fee\u6539\u50ac\u62a5\u8ba1\u5212\u4efb\u52a1\u5931\u8d25", e);
                    throw new JQException((ErrorEnum)new ErrorEnumImpl("\u505c\u7528\u50ac\u62a5\u8ba1\u5212\u4efb\u52a1\u5931\u8d25"), (Throwable)e);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deletePlan(String planId) throws JQException {
        this.disablePlan(planId);
        this.cbPlanDAO.deleteCbPlanById(planId);
        this.cbPlanDwDAO.deleteByPlanId(planId);
        this.cbPlanFDAO.deleteByPlanId(planId);
        this.cbPlanToDAO.deleteByPlanId(planId);
        List<CbExecLogDO> cbExecLogDOS = this.cbExecLogDAO.queryLogByPlanId(planId);
        for (CbExecLogDO cbExecLogDO : cbExecLogDOS) {
            this.cbPlanLogDAO.deleteByLogId(cbExecLogDO.getLogId());
        }
        this.cbExecLogDAO.deleteByPlanId(planId);
    }

    private void resScheduleCb(CbPlanDTO cbPlanDTO) throws JQException {
        this.cbPlanScheduleJobManager.deleteJob(cbPlanDTO.getPlanId());
        this.cbPlanScheduleJobManager.schedule(cbPlanDTO);
    }

    @Override
    public List<CbPlanVO> queryByPlan(PagerInfo pagerInfo) {
        List<CbPlanDO> cbPlanDOS = this.cbPlanDAO.queryCbPlans(pagerInfo.getOffset() * pagerInfo.getLimit(), (pagerInfo.getOffset() + 1) * pagerInfo.getLimit());
        ArrayList<CbPlanVO> list = new ArrayList<CbPlanVO>(cbPlanDOS.size());
        for (CbPlanDO cbPlanDO : cbPlanDOS) {
            list.add(this.convertVO(cbPlanDO));
        }
        return list;
    }

    private CbPlanVO convertVO(CbPlanDO cbPlanDO) {
        List<Object> list;
        CbPlanVO vo = new CbPlanVO(cbPlanDO);
        int count = this.cbExecLogDAO.countByPlanId(vo.getPlanId());
        vo.setPlanExeCount(count);
        CbExecLogDO cbExecLogDO = this.cbExecLogDAO.queryLatestLogByPlanId(vo.getPlanId());
        if (cbExecLogDO != null) {
            Timestamp startTime = cbExecLogDO.getStartTime();
            vo.setPreExeTime(startTime);
        }
        if (cbPlanDO.getUnitScop() == 8) {
            list = this.cbPlanDwDAO.queryByPlanId(vo.getPlanId());
            vo.fromDwList(list);
        }
        try {
            String execUnit = cbPlanDO.getExecUnit();
            IEntityQuery query = this.entityDataService.newEntityQuery();
            String formSchemeId = cbPlanDO.getFormSchemeId();
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeId);
            EntityViewDefine viewDefine = this.runTimeViewController.getViewByFormSchemeKey(formSchemeId);
            query.setEntityView(viewDefine);
            IEntityTable iEntityTable = query.executeReader((IContext)this.executorContext(formScheme));
            IEntityRow byEntityKey = iEntityTable.findByEntityKey(execUnit);
            if (byEntityKey != null) {
                vo.setUnitName(byEntityKey.getTitle());
            }
        }
        catch (Exception e) {
            logger.error("\u6267\u884c\u5355\u4f4d\u672a\u627e\u5230", e);
        }
        if (cbPlanDO.getWorkFlowType() != WorkFlowType.ENTITY.getValue() && cbPlanDO.getFormScop() == 2) {
            list = this.cbPlanFDAO.queryByPlanId(vo.getPlanId());
            vo.fromFormList(list);
        }
        if (cbPlanDO.getKind() == 2) {
            list = this.cbPlanTimeDAO.queryByPlanId(cbPlanDO.getId());
            list.removeIf(next -> next.getPeriodType() == 0);
            vo.fromTimeList(list);
        }
        List<CbPlanToDO> list2 = this.cbPlanToDAO.queryByPlanId(vo.getPlanId());
        vo.fromToList(list2);
        List<String> ccUserIds = vo.getCcUserIds();
        List<String> ccRoleIds = vo.getCcRoleIds();
        List<String> ccInfos = vo.getCcInfos();
        if (!CollectionUtils.isEmpty(ccUserIds)) {
            List userList = this.nvwaUserClient.get(ccUserIds);
            for (UserDTO userDTO : userList) {
                ccInfos.add(userDTO.getId() + ":" + userDTO.getNickname() + ":0");
            }
        }
        if (!CollectionUtils.isEmpty(ccRoleIds)) {
            List roles = this.roleService.getByIds(ccRoleIds);
            for (Role role : roles) {
                ccInfos.add(role.getId() + ":" + role.getTitle() + ":1");
            }
        }
        return vo;
    }

    @Override
    public int countPlan() {
        return this.cbPlanDAO.countPlan();
    }

    @Override
    public TaskInfo queryTaskInfo(String formSchemeId) {
        TaskInfo taskInfo = new TaskInfo();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeId);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        List taskOrgLinks = this.iRunTimeViewController.listTaskOrgLinkByTask(taskDefine.getKey());
        boolean showOrg = false;
        if (!CollectionUtils.isEmpty(taskOrgLinks)) {
            if (taskOrgLinks.size() == 1) {
                if (!taskDefine.getDw().equals(((TaskOrgLinkDefine)taskOrgLinks.get(0)).getEntity())) {
                    showOrg = true;
                }
            } else {
                showOrg = true;
            }
        }
        taskInfo.setShowOrg(showOrg);
        taskInfo.setTaskId(taskDefine.getKey());
        taskInfo.setTaskTitle(taskDefine.getTitle());
        taskInfo.setFormSchemeTitle(formScheme.getTitle());
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        if (flowsSetting != null) {
            String name = flowsSetting.getWordFlowType().name();
            taskInfo.setWorkFlowType(name);
        } else {
            taskInfo.setWorkFlowType(WorkFlowType.ENTITY.name());
        }
        String dw = CbUtils.getContextMainDimId(taskDefine.getDw());
        taskInfo.setEntityId(dw);
        PeriodWrapper currPeriod = CbUtils.getCurrPeriod(formScheme);
        if (currPeriod != null) {
            taskInfo.setCurrPeriod(currPeriod.toString());
        }
        if (this.vaMessageService == null) {
            this.vaMessageService = (VaMessageService)BeanUtil.getBean(VaMessageService.class);
        }
        ArrayList<VaMessageChannelDTO> select = new ArrayList<VaMessageChannelDTO>();
        String value = this.nvwaSystemOptionService.get("start-reminder", "REMINDER_MSG_CHANNEL");
        List vaMessageChannels = this.vaMessageService.listChannel();
        if (value.contains(VaMessageOption.MsgChannel.PC.toString())) {
            select.add(new VaMessageChannelDTO(VaMessageOption.MsgChannel.PC.toString(), "\u7ad9\u5185\u4fe1"));
        }
        for (VaMessageChannelDTO vaMessageChannel : vaMessageChannels) {
            if (VaMessageOption.MsgChannel.PC.toString().equals(vaMessageChannel.getName()) || !value.contains(vaMessageChannel.getName())) continue;
            select.add(vaMessageChannel);
        }
        taskInfo.setMsgChannels(select);
        taskInfo.setMsgChannel(value);
        return taskInfo;
    }

    @Override
    public List<TreeNode> task() {
        ArrayList<TreeNode> list = null;
        TreeNode parent = new TreeNode();
        parent.setType(1);
        while (parent != null) {
            ArrayList<TreeNode> childrens = new ArrayList<TreeNode>();
            if (list == null) {
                list = childrens;
            } else {
                parent.setChildren(childrens);
            }
            if (parent.getType() == 1) {
                TreeNode treeNode;
                List groups = this.runTimeViewController.getChildTaskGroups(parent.getKey(), false);
                List tasks = this.runTimeViewController.getAllRunTimeTasksInGroup(parent.getKey());
                if (!CollectionUtils.isEmpty(groups)) {
                    for (TaskGroupDefine taskGroupDefine : groups) {
                        treeNode = new TreeNode();
                        treeNode.setKey(taskGroupDefine.getKey());
                        treeNode.setTitle(taskGroupDefine.getTitle());
                        treeNode.setChildren(Collections.emptyList());
                        treeNode.setType(1);
                        childrens.add(treeNode);
                    }
                }
                if (!CollectionUtils.isEmpty(tasks)) {
                    for (TaskDefine taskDefine : tasks) {
                        treeNode = new TreeNode();
                        treeNode.setKey(taskDefine.getKey());
                        treeNode.setTitle(taskDefine.getTitle());
                        treeNode.setChildren(Collections.emptyList());
                        treeNode.setType(2);
                        childrens.add(treeNode);
                    }
                }
                if (!CollectionUtils.isEmpty(childrens)) {
                    parent = (TreeNode)childrens.get(0);
                    continue;
                }
                parent = null;
                continue;
            }
            if (parent.getType() != 2) continue;
            childrens.addAll(this.task(parent));
            parent = null;
        }
        return list;
    }

    @Override
    public List<TreeNode> task(String formSchemeId) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeId);
        TaskDefine taskDef = formScheme == null ? this.runTimeViewController.queryTaskDefine(formSchemeId) : this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        List groupByTask = this.runTimeViewController.getGroupByTask(taskDef.getKey());
        ArrayList<Node> paths = new ArrayList<Node>();
        Node task = new Node(taskDef.getKey(), 2);
        paths.add(task);
        if (!CollectionUtils.isEmpty(groupByTask)) {
            ArrayList<String> groupPaths = null;
            block2: for (TaskGroupDefine taskGroupDefine : groupByTask) {
                groupPaths = new ArrayList<String>();
                groupPaths.add(taskGroupDefine.getKey());
                String parentKey = taskGroupDefine.getParentKey();
                while (StringUtils.hasLength(parentKey)) {
                    groupPaths.add(parentKey);
                    TaskGroupDefine parentGroup = this.runTimeViewController.queryTaskGroupDefine(parentKey);
                    if (parentGroup == null) continue block2;
                    parentKey = parentGroup.getParentKey();
                }
            }
            for (String groupPath : groupPaths) {
                Node node = new Node(groupPath, 1);
                paths.add(node);
            }
        }
        paths.add(new Node(null, 1));
        Collections.reverse(paths);
        List<TreeNode> roots = Collections.emptyList();
        List<TreeNode> parents = new ArrayList<TreeNode>();
        for (Node parent : paths) {
            List formSchemeDefines;
            List<TreeNode> nodes;
            if (CollectionUtils.isEmpty(parents)) {
                nodes = roots = parents;
            } else {
                nodes = new ArrayList<TreeNode>();
                for (TreeNode treeNode : parents) {
                    if (!parent.getKey().equals(treeNode.getKey())) continue;
                    treeNode.setExpand(true);
                    treeNode.setChildren(nodes);
                }
                parents = nodes;
            }
            if (parent.getType() == 1) {
                TreeNode treeNode;
                List groups = this.runTimeViewController.getChildTaskGroups(parent.getKey(), false);
                List tasks = this.runTimeViewController.getAllRunTimeTasksInGroup(parent.getKey());
                if (!CollectionUtils.isEmpty(groups)) {
                    for (TaskGroupDefine taskGroupDefine : groups) {
                        treeNode = new TreeNode();
                        treeNode.setKey(taskGroupDefine.getKey());
                        treeNode.setTitle(taskGroupDefine.getTitle());
                        treeNode.setChildren(Collections.emptyList());
                        treeNode.setParentKey(parent.getKey());
                        treeNode.setType(1);
                        nodes.add(treeNode);
                    }
                }
                if (CollectionUtils.isEmpty(tasks)) continue;
                for (TaskDefine taskDefine : tasks) {
                    treeNode = new TreeNode();
                    treeNode.setKey(taskDefine.getKey());
                    treeNode.setTitle(taskDefine.getTitle());
                    treeNode.setChildren(Collections.emptyList());
                    treeNode.setType(2);
                    treeNode.setParentKey(parent.getKey());
                    nodes.add(treeNode);
                }
                continue;
            }
            if (parent.getType() != 2) continue;
            try {
                formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(parent.getKey());
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (CollectionUtils.isEmpty(formSchemeDefines)) continue;
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                TreeNode treeNode = new TreeNode();
                treeNode.setKey(formSchemeDefine.getKey());
                treeNode.setParentKey(parent.getKey());
                treeNode.setTitle(formSchemeDefine.getTitle());
                treeNode.setChildren(Collections.emptyList());
                treeNode.setType(3);
                if (formSchemeId.equals(formSchemeDefine.getKey())) {
                    treeNode.setLoading(null);
                }
                nodes.add(treeNode);
            }
        }
        return roots;
    }

    @Override
    public List<TreeNode> task(TreeNode parent) {
        ArrayList<TreeNode> nodeList = new ArrayList<TreeNode>();
        try {
            if (parent.getType() == 2) {
                List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(parent.getKey());
                if (!formSchemeDefines.isEmpty()) {
                    for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                        TreeNode treeNode = new TreeNode();
                        treeNode.setKey(formSchemeDefine.getKey());
                        treeNode.setTitle(formSchemeDefine.getTitle());
                        treeNode.setExpand(false);
                        treeNode.setLoading(null);
                        treeNode.setType(3);
                        PeriodWrapper currPeriod = CbUtils.getCurrPeriod(formSchemeDefine);
                        List schemePeriodLinkDefines = this.runtimeFormSchemePeriodService.querySchemePeriodLinkByScheme(formSchemeDefine.getKey());
                        if (currPeriod != null) {
                            treeNode.setPeriod(currPeriod.toString());
                        }
                        String minPeriod = formSchemeDefine.getFromPeriod();
                        String maxPeriod = formSchemeDefine.getToPeriod();
                        if (!CollectionUtils.isEmpty(schemePeriodLinkDefines)) {
                            for (SchemePeriodLinkDefine schemePeriodLinkDefine : schemePeriodLinkDefines) {
                                String periodKey = schemePeriodLinkDefine.getPeriodKey();
                                if (minPeriod == null) {
                                    minPeriod = periodKey;
                                }
                                if (maxPeriod == null) {
                                    maxPeriod = periodKey;
                                }
                                if (periodKey == null) continue;
                                if (periodKey.compareTo(minPeriod) < 0) {
                                    minPeriod = periodKey;
                                }
                                if (periodKey.compareTo(minPeriod) <= 0) continue;
                                maxPeriod = periodKey;
                            }
                        }
                        treeNode.setFromPeriod(minPeriod);
                        treeNode.setToPeriod(maxPeriod);
                        String contextMainDimId = CbUtils.getContextMainDimId(formSchemeDefine.getDw());
                        treeNode.setEntityKey(contextMainDimId);
                        treeNode.setParentKey(parent.getKey());
                        nodeList.add(treeNode);
                    }
                }
            } else if (parent.getType() == 1) {
                TreeNode treeNode;
                List taskGroupDefines = this.runTimeViewController.getChildTaskGroups(parent.getKey(), false);
                List taskDefines = this.runTimeViewController.getAllRunTimeTasksInGroup(parent.getKey());
                if (!taskGroupDefines.isEmpty()) {
                    for (TaskGroupDefine taskGroupDefine : taskGroupDefines) {
                        treeNode = new TreeNode();
                        treeNode.setKey(taskGroupDefine.getKey());
                        treeNode.setTitle(taskGroupDefine.getTitle());
                        treeNode.setExpand(false);
                        treeNode.setType(1);
                        treeNode.setChildren(Collections.emptyList());
                        nodeList.add(treeNode);
                    }
                }
                if (!taskDefines.isEmpty()) {
                    for (TaskDefine taskDefine : taskDefines) {
                        treeNode = new TreeNode();
                        treeNode.setKey(taskDefine.getKey());
                        treeNode.setTitle(taskDefine.getTitle());
                        treeNode.setExpand(false);
                        treeNode.setType(2);
                        treeNode.setChildren(Collections.emptyList());
                        nodeList.add(treeNode);
                    }
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return nodeList;
    }

    private static class Node {
        private String key;
        private int type;

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Node(String key, int type) {
            this.key = key;
            this.type = type;
        }
    }
}

