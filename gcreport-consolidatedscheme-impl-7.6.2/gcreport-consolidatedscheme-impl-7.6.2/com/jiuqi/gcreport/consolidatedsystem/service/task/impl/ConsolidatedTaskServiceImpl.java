/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.task.common.TaskPeriodUtils
 *  com.jiuqi.gcreport.common.task.vo.OptionVO
 *  com.jiuqi.gcreport.common.util.FormSchemePeriodGcUtils
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.OrderGenerator
 *  com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedTaskChangedEvent
 *  com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedTaskChangedEvent$ConsolidatedTaskChangedInfo
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskInfoVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.controller2.RunTimeViewController
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  javax.validation.constraints.NotNull
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.consolidatedsystem.service.task.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.task.common.TaskPeriodUtils;
import com.jiuqi.gcreport.common.task.vo.OptionVO;
import com.jiuqi.gcreport.common.util.FormSchemePeriodGcUtils;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.OrderGenerator;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.dao.task.ConsolidatedTaskDao;
import com.jiuqi.gcreport.consolidatedsystem.dao.task.ConsolidatedTaskDataDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.task.ConsolidatedTaskDataEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.task.ConsolidatedTaskEO;
import com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedTaskChangedEvent;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskInfoVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.controller2.RunTimeViewController;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class ConsolidatedTaskServiceImpl
implements ConsolidatedTaskService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ConsolidatedTaskDao consolidatedTaskDao;
    @Autowired
    private ConsolidatedSystemDao consolidatedSystemDao;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ConsolidatedTaskDataDao consolidatedTaskDataDao;
    @Autowired
    private ConsolidatedTaskCacheService taskCacheService;
    @Autowired
    private RunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;

    @Override
    public List<ConsolidatedTaskVO> getConsolidatedTasks(String systemId) {
        ConsolidatedTaskEO templet = new ConsolidatedTaskEO();
        templet.setSystemId(systemId);
        List eos = this.consolidatedTaskDao.selectList((BaseEntity)templet);
        return eos.stream().sorted(Comparator.comparing(ConsolidatedTaskEO::getSortOrder)).map(eo -> this.convertEO2VO((ConsolidatedTaskEO)((Object)eo))).collect(Collectors.toList());
    }

    @Override
    public List<ConsolidatedTaskVO> getConsolidatedTasksBySchemeId(String schemeId) {
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(schemeId);
        if (formScheme == null) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u4efb\u52a1\u65b9\u6848\u3002");
        }
        List<ConsolidatedTaskEO> eos = this.consolidatedTaskDao.getTaskByTaskKey(formScheme.getTaskKey());
        if (CollectionUtils.isEmpty(eos)) {
            return Collections.emptyList();
        }
        List<ConsolidatedTaskVO> vos = eos.stream().sorted(Comparator.comparing(ConsolidatedTaskEO::getSortOrder)).map(eo -> this.convertEO2VO((ConsolidatedTaskEO)((Object)eo))).collect(Collectors.toList());
        return vos;
    }

    @Override
    public void bindConsolidatedTask(ConsolidatedTaskVO consolidatedTaskReqVO) {
        Assert.isNotEmpty((String)consolidatedTaskReqVO.getSystemId(), (String)"\u5408\u5e76\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        ConsolidatedSystemEO consolidatedSystemEO = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)consolidatedTaskReqVO.getSystemId()));
        NpContext context = NpContextHolder.getContext();
        ConsolidatedTaskEO eo = new ConsolidatedTaskEO();
        BeanUtils.copyProperties(consolidatedTaskReqVO, (Object)eo);
        eo.setModifiedUser(context.getUserName());
        eo.setModifiedTime(new Date());
        eo.setMoreInfo(consolidatedTaskReqVO.manualGetMoreInfo());
        eo.setManageTaskKeys(CollectionUtils.isEmpty((Collection)consolidatedTaskReqVO.getManageTaskKeys()) ? null : StringUtils.join((Object[])consolidatedTaskReqVO.getManageTaskKeys().toArray(), (String)","));
        eo.setManageEntity(CollectionUtils.isEmpty((Collection)consolidatedTaskReqVO.getManageEntityList()) ? null : StringUtils.join((Object[])consolidatedTaskReqVO.getManageEntityList().toArray(), (String)","));
        List<ConsolidatedTaskEO> taskEOS = this.getAllBoundTasksSamePeriod(consolidatedTaskReqVO);
        String taskId = this.sameSchemeCurrPeriod(consolidatedTaskReqVO, taskEOS);
        TaskDefine queryTask = this.queryTask(taskId);
        Assert.isTrue((boolean)StringUtils.isEmpty((String)taskId), (String)String.format("\u76f8\u540c\u65f6\u671f\u8303\u56f4\u5185\uff0c%s\u62a5\u8868\u4efb\u52a1\u4e0d\u5141\u8bb8\u591a\u6b21\u6dfb\u52a0", queryTask == null ? "" : queryTask.getTitle()), (Object[])new Object[0]);
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(eo.getTaskKey());
        String taskTitle = taskDefine == null ? eo.getTaskKey() : taskDefine.getTitle();
        ConsolidatedTaskDataEO consolidatedTaskDataEO = new ConsolidatedTaskDataEO();
        if (!StringUtils.isEmpty((String)eo.getId())) {
            consolidatedTaskDataEO.setConsTaskId(eo.getId());
            List consolidatedTaskDataEOS = this.consolidatedTaskDataDao.selectList((BaseEntity)consolidatedTaskDataEO);
            if (!CollectionUtils.isEmpty((Collection)consolidatedTaskDataEOS)) {
                consolidatedTaskDataEO = (ConsolidatedTaskDataEO)((Object)consolidatedTaskDataEOS.get(0));
            }
        }
        LinkedHashSet manageCalcUnitCodes = consolidatedTaskReqVO.getManageCalcUnitCodes();
        HashMap<String, LinkedHashSet> jsonObject = new HashMap<String, LinkedHashSet>();
        jsonObject.put("manageCalcUnitCodes", manageCalcUnitCodes);
        consolidatedTaskDataEO.setTaskData(JsonUtils.writeValueAsString(jsonObject));
        if (StringUtils.isEmpty((String)consolidatedTaskReqVO.getId()) || null == this.consolidatedTaskDao.get((Serializable)((Object)consolidatedTaskReqVO.getId()))) {
            eo.setSortOrder(OrderGenerator.newOrderShort());
            this.consolidatedTaskDao.save(eo);
            consolidatedTaskDataEO.setConsTaskId(eo.getId());
            this.consolidatedTaskDataDao.save(consolidatedTaskDataEO);
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u4f53\u7cfb", (String)("\u4fee\u6539-" + consolidatedSystemEO.getSystemName() + "\u5408\u5e76\u4f53\u7cfb-\u6dfb\u52a0" + taskTitle + "\u4efb\u52a1"), (String)("\u4fee\u6539\u540e\u4fe1\u606f:" + JsonUtils.writeValueAsString((Object)((Object)eo)) + "\n \u6570\u636e\u4fe1\u606f\uff1a" + JsonUtils.writeValueAsString((Object)((Object)consolidatedTaskDataEO))));
        } else {
            this.consolidatedTaskDao.update((BaseEntity)eo);
            consolidatedTaskDataEO.setConsTaskId(eo.getId());
            if (StringUtils.isEmpty((String)consolidatedTaskDataEO.getId())) {
                this.consolidatedTaskDataDao.save(consolidatedTaskDataEO);
            } else {
                this.consolidatedTaskDataDao.update((BaseEntity)consolidatedTaskDataEO);
            }
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u4f53\u7cfb", (String)("\u4fee\u6539-" + consolidatedSystemEO.getSystemName() + "\u5408\u5e76\u4f53\u7cfb-\u4fee\u6539" + taskTitle + "\u4efb\u52a1"), (String)("\u4fee\u6539\u540e\u4fe1\u606f:" + JsonUtils.writeValueAsString((Object)((Object)eo)) + "\n \u6570\u636e\u4fe1\u606f\uff1a" + JsonUtils.writeValueAsString((Object)((Object)consolidatedTaskDataEO))));
        }
        this.executorService.execute(() -> this.applicationContext.publishEvent((ApplicationEvent)new ConsolidatedTaskChangedEvent(new ConsolidatedTaskChangedEvent.ConsolidatedTaskChangedInfo(), context)));
    }

    @Override
    public void bindConsolidatedTask(List<ConsolidatedTaskVO> consolidatedTaskReqVOs) {
        if (null == consolidatedTaskReqVOs) {
            return;
        }
        for (ConsolidatedTaskVO consolidatedTaskReqVO : consolidatedTaskReqVOs) {
            this.bindConsolidatedTask(consolidatedTaskReqVO);
        }
    }

    @Override
    public void unbindConsolidatedTask(String[] recids) {
        Assert.isNotNull((Object)recids, (String)"\u627e\u4e0d\u5230\u4efb\u52a1.", (Object[])new Object[0]);
        ConsolidatedSystemEO consolidatedSystemEO = null;
        for (String recid : recids) {
            TaskDefine taskDefine;
            ConsolidatedTaskEO consolidatedTaskEO = (ConsolidatedTaskEO)this.consolidatedTaskDao.get((Serializable)((Object)recid));
            if (consolidatedSystemEO == null) {
                consolidatedSystemEO = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)consolidatedTaskEO.getSystemId()));
            }
            String taskTitle = (taskDefine = this.queryTask(consolidatedTaskEO.getTaskKey())) == null ? consolidatedTaskEO.getTaskKey() : taskDefine.getTitle();
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u4f53\u7cfb", (String)("\u4fee\u6539-" + consolidatedSystemEO.getSystemName() + "\u5408\u5e76\u4f53\u7cfb-\u89e3\u7ed1" + taskTitle + "\u4efb\u52a1"), (String)"");
            ConsolidatedTaskEO eo = new ConsolidatedTaskEO();
            eo.setId(recid);
            this.consolidatedTaskDao.delete((BaseEntity)eo);
        }
        NpContext context = NpContextHolder.getContext();
        this.executorService.execute(() -> this.applicationContext.publishEvent((ApplicationEvent)new ConsolidatedTaskChangedEvent(new ConsolidatedTaskChangedEvent.ConsolidatedTaskChangedInfo(), context)));
    }

    @Override
    public ConsolidatedSystemEO getConsolidatedSystemBySchemeId(String schemeId, String periodString) {
        String systemId = this.getConsolidatedSystemIdBySchemeId(schemeId, periodString);
        if (null != systemId) {
            return (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)systemId));
        }
        return null;
    }

    @Override
    public String getConsolidatedSystemIdBySchemeId(String schemeId, String periodString) {
        ConsolidatedTaskVO taskVO = this.getTaskBySchemeId(schemeId, periodString);
        if (null != taskVO) {
            return taskVO.getSystemId();
        }
        return null;
    }

    @Override
    public String getSystemIdByTaskIdAndPeriodStr(String taskId, String periodStr) {
        ConsolidatedTaskVO taskVO = this.taskCacheService.getTaskByTaskId(taskId, periodStr);
        if (null != taskVO) {
            return taskVO.getSystemId();
        }
        return null;
    }

    @Override
    public Set<String> getRelateTaskIdsByTaskId(String taskId, @NotNull String periodStr) {
        ConsolidatedTaskVO taskVO = this.taskCacheService.getTaskByTaskId(taskId, periodStr);
        HashSet<String> allTaskIds = new HashSet<String>(16);
        allTaskIds.add(taskId);
        if (null == taskVO) {
            return allTaskIds;
        }
        allTaskIds.add(taskVO.getTaskKey());
        allTaskIds.addAll(taskVO.getManageTaskKeys());
        allTaskIds.remove(null);
        return allTaskIds;
    }

    @Override
    public ConsolidatedTaskVO getTaskBySchemeId(String schemeId, String periodString) {
        return this.taskCacheService.getTaskBySchemeId(schemeId, periodString);
    }

    @Override
    public boolean isCorporate(String taskId, String periodString, String orgType) {
        if (StringUtils.isEmpty((String)taskId)) {
            return false;
        }
        ConsolidatedTaskVO taskVO = this.getTaskByTaskKeyAndPeriodStr(taskId, periodString);
        if (null == taskVO) {
            return false;
        }
        if (!taskId.equals(taskVO.getTaskKey())) {
            return false;
        }
        List taskOrgLinkDefineList = this.runTimeViewController.listTaskOrgLinkByTask(taskId);
        if (taskOrgLinkDefineList.size() <= 1) {
            return true;
        }
        if (StringUtils.isEmpty((String)orgType)) {
            return false;
        }
        if (StringUtils.isEmpty((String)taskVO.getCorporateEntity())) {
            TaskDefine taskDefine = this.queryTask(taskId);
            this.logger.error("\u5224\u65ad\u662f\u5426\u6cd5\u4eba\u53e3\u5f84\u5931\u8d25\uff0c\u4efb\u52a1\u3010{}\u3011\u672a\u8bbe\u7f6e\u91c7\u96c6\u53e3\u5f84", (Object)(taskDefine == null ? taskId : taskDefine.getTitle()));
            throw new BusinessRuntimeException("\u5224\u65ad\u662f\u5426\u6cd5\u4eba\u53e3\u5f84\u5931\u8d25\uff0c\u4efb\u52a1\u3010 " + (taskDefine == null ? taskId : taskDefine.getTitle()) + "\u3011\u672a\u8bbe\u7f6e\u91c7\u96c6\u53e3\u5f84");
        }
        return orgType.equals(taskVO.getCorporateEntity());
    }

    @Override
    public List<String> getAllBoundTasks() {
        List<ConsolidatedTaskEO> taskEOS = this.consolidatedTaskDao.getAllBoundTasks();
        LinkedHashSet uuidSet = new LinkedHashSet();
        taskEOS.forEach(eo -> {
            uuidSet.add(eo.getTaskKey());
            String manageTaskKeys = eo.getManageTaskKeys();
            if (!StringUtils.isEmpty((String)manageTaskKeys)) {
                uuidSet.addAll(Arrays.asList(manageTaskKeys.split(",")));
            }
        });
        return new ArrayList<String>(uuidSet);
    }

    @Override
    public List<ConsolidatedTaskVO> getAllBoundTaskVOs() {
        List taskVOs = this.consolidatedTaskDao.getAllBoundTasks().stream().map(eo -> this.convertEO2VO((ConsolidatedTaskEO)((Object)eo))).collect(Collectors.toList());
        return new ArrayList<ConsolidatedTaskVO>(taskVOs);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void unbindBySystemId(String systemId) {
        ConsolidatedSystemEO consolidatedSystemEO = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)systemId));
        List<ConsolidatedTaskVO> tasks = this.getConsolidatedTasks(systemId);
        for (ConsolidatedTaskVO task : tasks) {
            this.consolidatedTaskDataDao.deleteByConsTaskId(task.getId());
        }
        this.consolidatedTaskDao.deleteTasksBySystem(systemId);
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u4f53\u7cfb", (String)("\u4fee\u6539-" + consolidatedSystemEO.getSystemName() + "\u5408\u5e76\u4f53\u7cfb-\u89e3\u7ed1\u5168\u90e8\u4efb\u52a1"), (String)"");
        this.executorService.execute(() -> this.applicationContext.publishEvent((ApplicationEvent)new ConsolidatedTaskChangedEvent(new ConsolidatedTaskChangedEvent.ConsolidatedTaskChangedInfo(), NpContextHolder.getContext())));
    }

    @Override
    @Deprecated
    public List<ConsolidatedTaskVO> getConsolidatedTaskByTaskId(String taskId) {
        List<ConsolidatedTaskEO> eos = this.consolidatedTaskDao.getTaskByTaskKey(taskId);
        ArrayList<ConsolidatedTaskVO> taskVOS = new ArrayList<ConsolidatedTaskVO>();
        for (ConsolidatedTaskEO taskEO : eos) {
            if (null == taskEO) continue;
            taskVOS.add(this.convertEO2VO(taskEO));
        }
        return taskVOS;
    }

    @Override
    public List<String> getRelevancySystemsBySchemeIds(List<String> schemeIds) {
        return this.getRelevancySchemeIds(schemeIds, false);
    }

    private List<String> getRelevancySchemeIds(List<String> schemeIds, boolean isOnlyInputScheme) {
        List<ConsolidatedTaskVO> taskVOs = this.getAllBoundTaskVOs();
        HashSet<String> relevancySchemeIds = new HashSet<String>();
        for (ConsolidatedTaskVO vo : taskVOs) {
            List<String> allManageSchemeList;
            relevancySchemeIds.addAll(ConsolidatedSystemUtils.listAllInputSchemeByConTaskVO(vo));
            if (isOnlyInputScheme || CollectionUtils.isEmpty(allManageSchemeList = ConsolidatedSystemUtils.listAllManageSchemeByConTaskVO(vo))) continue;
            relevancySchemeIds.addAll(allManageSchemeList);
        }
        ArrayList<String> result = new ArrayList<String>();
        for (String schemeId : schemeIds) {
            if (StringUtils.isEmpty((String)schemeId) || !relevancySchemeIds.contains(schemeId)) continue;
            result.add(schemeId);
        }
        return result;
    }

    @Override
    public List<String> getRelevancySystemsInputSchemeIds(List<String> schemeIds) {
        return this.getRelevancySchemeIds(schemeIds, true);
    }

    @Override
    public void exchangeSort(String opNodeId, int step) {
        ConsolidatedTaskEO opeNode = (ConsolidatedTaskEO)this.consolidatedTaskDao.get((Serializable)((Object)opNodeId));
        if (null == opeNode) {
            return;
        }
        ConsolidatedTaskEO exeNode = step < 0 ? this.consolidatedTaskDao.getPreNodeBySystemIdAndOrder(opeNode.getSystemId(), opeNode.getSortOrder()) : this.consolidatedTaskDao.getNextNodeBySystemIdAndOrder(opeNode.getSystemId(), opeNode.getSortOrder());
        if (null == exeNode) {
            throw new BusinessRuntimeException(step < 0 ? "\u4e0d\u80fd\u518d\u79fb\u4e86\uff0c\u5df2\u7ecf\u4e3a\u7b2c\u4e00\u6761\u4e86" : "\u4e0d\u80fd\u518d\u79fb\u4e86\uff0c\u5df2\u7ecf\u4e3a\u6700\u540e\u4e00\u6761\u4e86");
        }
        String tempSort = opeNode.getSortOrder();
        opeNode.setSortOrder(exeNode.getSortOrder());
        exeNode.setSortOrder(tempSort);
        this.consolidatedTaskDao.update((BaseEntity)opeNode);
        this.consolidatedTaskDao.update((BaseEntity)exeNode);
    }

    private ConsolidatedTaskVO convertEO2VO(ConsolidatedTaskEO eo) {
        String taskData;
        Map jsonObject;
        List manageCalcUnitCodeList;
        ConsolidatedTaskDataEO consolidatedTaskDataEO = new ConsolidatedTaskDataEO();
        consolidatedTaskDataEO.setConsTaskId(eo.getId());
        List consolidatedTaskDataEOS = this.consolidatedTaskDataDao.selectList((BaseEntity)consolidatedTaskDataEO);
        LinkedHashSet manageCalcUnitCodes = new LinkedHashSet();
        if (!CollectionUtils.isEmpty((Collection)consolidatedTaskDataEOS) && null != (manageCalcUnitCodeList = (List)(jsonObject = (Map)JsonUtils.readValue((String)(taskData = (consolidatedTaskDataEO = (ConsolidatedTaskDataEO)((Object)consolidatedTaskDataEOS.get(0))).getTaskData()), Map.class)).get("manageCalcUnitCodes"))) {
            manageCalcUnitCodes.addAll(manageCalcUnitCodeList);
        }
        ConsolidatedTaskVO vo = new ConsolidatedTaskVO();
        BeanUtils.copyProperties((Object)eo, vo);
        vo.setManageCalcUnitCodes(manageCalcUnitCodes);
        TaskDefine taskDefine = this.queryTask(eo.getTaskKey());
        if (null != taskDefine) {
            vo.setDataScheme(taskDefine.getDataScheme());
            vo.setTaskTitle(taskDefine.getTitle());
            vo.setPeriodTypeTitle(taskDefine.getPeriodType().title());
            vo.setInputTaskInfo(this.getTaskInfoVO(taskDefine.getKey()));
        } else {
            vo.setTaskTitle(eo.getTaskKey());
        }
        List manageTaskKeys = eo.getManageTaskKeys() == null ? Collections.emptyList() : StringUtils.split((String)eo.getManageTaskKeys(), (String)",");
        vo.setManageTaskKeys(manageTaskKeys);
        vo.manualSetMoreInfo(eo.getMoreInfo());
        try {
            List manageTaskList = manageTaskKeys.stream().filter(taskKey -> !StringUtils.isEmpty((String)taskKey)).map(this::getTaskInfoVO).collect(Collectors.toList());
            vo.setManageTaskInfos(manageTaskList);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        TaskOrgLinkListStream taskOrgLinkListStream = this.runTimeViewController.listTaskOrgLinkStreamByTask(eo.getTaskKey());
        List taskOrgLinkList = taskOrgLinkListStream.auth().i18n().getList();
        if (taskOrgLinkList == null || taskOrgLinkList.size() <= 1) {
            vo.setTaskVersion2_0(false);
            return vo;
        }
        vo.setTaskVersion2_0(true);
        ArrayList<OrgTypeVO> taskOrgTypeVoList = new ArrayList<OrgTypeVO>();
        IEntityMetaService iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkList) {
            String entity = taskOrgLinkDefine.getEntity();
            OrgTypeVO orgTypeVO = new OrgTypeVO();
            if (StringUtils.isEmpty((String)entity)) continue;
            TableModelDefine orgTableModelDefine = this.entityMetaService.getTableModel(entity);
            orgTypeVO.setName(orgTableModelDefine.getName());
            if (!StringUtils.isEmpty((String)taskOrgLinkDefine.getEntityAlias())) {
                orgTypeVO.setTitle(taskOrgLinkDefine.getEntityAlias());
            } else {
                orgTypeVO.setTitle(iEntityMetaService.queryEntity(taskOrgLinkDefine.getEntity()).getTitle());
            }
            taskOrgTypeVoList.add(orgTypeVO);
        }
        List manageEntityList = eo.getManageEntity() == null ? Collections.emptyList() : StringUtils.split((String)eo.getManageEntity(), (String)",");
        vo.setManageEntityList(manageEntityList);
        vo.setTaskOrgTypeVoList(taskOrgTypeVoList);
        return vo;
    }

    private TaskInfoVO getTaskInfoVO(String taskKey) {
        TaskDefine taskDefine = this.queryTask(taskKey);
        if (taskDefine == null) {
            TaskInfoVO taskInfoVO = new TaskInfoVO();
            taskInfoVO.setTaskTitle(taskKey);
            taskInfoVO.setTask(new OptionVO((Object)taskKey, taskKey));
            return taskInfoVO;
        }
        TaskInfoVO taskInfoVO = new TaskInfoVO();
        taskInfoVO.setTaskTitle(taskDefine.getTitle());
        taskInfoVO.setTask(new OptionVO((Object)taskDefine.getKey(), taskDefine.getTitle()));
        ArrayList<String> defines = new ArrayList<String>();
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringContextUtils.getBean(IEntityMetaService.class);
        TableModelDefine orgTableModelDefine = entityMetaService.getTableModel(taskDefine.getDw());
        if (!StringUtils.isEmpty((String)orgTableModelDefine.getName()) && orgTableModelDefine.getName().toUpperCase().startsWith("MD_ORG_")) {
            taskInfoVO.setUnitDefine(orgTableModelDefine.getName());
            taskInfoVO.setUnitTitle(orgTableModelDefine.getTitle());
        }
        if (taskDefine.getDims() != null) {
            String[] entityIds;
            for (String entityId : entityIds = taskDefine.getDims().split(";")) {
                TableModelDefine designTableDefine = entityMetaService.getTableModel(entityId);
                if (designTableDefine == null) continue;
                String mTableName = designTableDefine.getName();
                if ("MD_CURRENCY".equals(mTableName)) {
                    taskInfoVO.setCurrencyDefine(mTableName);
                    continue;
                }
                if ("MD_GCORGTYPE".equals(mTableName)) {
                    taskInfoVO.setGcorgtypeDefine(mTableName);
                    continue;
                }
                defines.add(designTableDefine.getName());
            }
        }
        this.setTaskTimeByTaskDefine(taskInfoVO, taskDefine);
        PeriodWrapper currentPeriod = TaskPeriodUtils.getCurrentPeriod((int)taskInfoVO.getPeriodType());
        taskInfoVO.setDflYear(Integer.valueOf(currentPeriod.getYear()));
        taskInfoVO.setDflPeriod(Integer.valueOf(currentPeriod.getPeriod()));
        taskInfoVO.setDefines(defines);
        return taskInfoVO;
    }

    private void setTaskTimeByTaskDefine(TaskInfoVO taskInfoVO, TaskDefine taskDefine) {
        int i;
        List schemePeriodLinkDefines;
        try {
            schemePeriodLinkDefines = this.iRunTimeViewController.querySchemePeriodLinkByTask(taskDefine.getKey());
            schemePeriodLinkDefines.sort(Comparator.comparing(SchemePeriodLinkDefine::getPeriodKey));
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        taskInfoVO.setPeriodType(Integer.valueOf(taskDefine.getPeriodType().type()));
        if (CollectionUtils.isEmpty((Collection)schemePeriodLinkDefines)) {
            return;
        }
        ArrayList<OptionVO> acctYearList = new ArrayList<OptionVO>();
        ArrayList<OptionVO> acctPeriodList = new ArrayList<OptionVO>();
        Integer fromAcctYear = null;
        Integer fromAcctPeriod = null;
        Integer toAcctYear = null;
        Integer toAcctPeriod = null;
        String fromPeriod = ((SchemePeriodLinkDefine)schemePeriodLinkDefines.get(0)).getPeriodKey();
        String toPeriod = ((SchemePeriodLinkDefine)schemePeriodLinkDefines.get(schemePeriodLinkDefines.size() - 1)).getPeriodKey();
        if (!StringUtils.isEmpty((String)fromPeriod)) {
            fromAcctYear = Integer.valueOf(fromPeriod.substring(0, 4));
        }
        if (!StringUtils.isEmpty((String)toPeriod)) {
            toAcctYear = Integer.valueOf(toPeriod.substring(0, 4));
        }
        char periodTypeChar = (char)taskDefine.getPeriodType().code();
        fromAcctPeriod = 1;
        toAcctPeriod = TaskPeriodUtils.getPeriodNum((char)periodTypeChar);
        Calendar date = Calendar.getInstance();
        int year = date.get(1);
        fromAcctYear = fromAcctYear == null ? year - 5 : fromAcctYear;
        toAcctYear = toAcctYear == null ? year + 5 : toAcctYear;
        for (i = fromAcctYear.intValue(); i <= toAcctYear; ++i) {
            acctYearList.add(new OptionVO((Object)i, i + ""));
        }
        for (i = fromAcctPeriod.intValue(); i <= toAcctPeriod; ++i) {
            acctPeriodList.add(new OptionVO((Object)i, i + ""));
        }
        taskInfoVO.setPeriodTypeChar(String.valueOf(periodTypeChar));
        taskInfoVO.setAcctYear(acctYearList);
        taskInfoVO.setAcctPeriod(acctPeriodList);
        taskInfoVO.setFromPeriod(fromPeriod);
        taskInfoVO.setToPeriod(toPeriod);
    }

    private List<FormSchemeDefine> queryTaskSchemeByTaskIdsAndPeriod(List<String> taskKeys, String fromPeriod, String toPeriod) {
        if (CollectionUtils.isEmpty(taskKeys)) {
            return Collections.emptyList();
        }
        ArrayList<FormSchemeDefine> matchSchemeDefines = new ArrayList<FormSchemeDefine>();
        taskKeys.stream().forEach(taskKey -> {
            try {
                List schemes = this.iRunTimeViewController.queryFormSchemeByTask(taskKey);
                if (CollectionUtils.isEmpty((Collection)schemes)) {
                    return;
                }
                List currentMatchSchemeDefines = schemes.stream().filter(scheme -> {
                    String[] fromToPeriodByFormSchemeKey = FormSchemePeriodGcUtils.getFromToPeriodByFormSchemeKey((String)scheme.getKey());
                    return fromPeriod.compareTo(fromToPeriodByFormSchemeKey[0]) <= 0 && toPeriod.compareTo(fromToPeriodByFormSchemeKey[1]) >= 0;
                }).collect(Collectors.toList());
                matchSchemeDefines.addAll(currentMatchSchemeDefines);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException((Throwable)e);
            }
        });
        return matchSchemeDefines;
    }

    private TaskDefine queryTask(String taskId) {
        try {
            return this.iRunTimeViewController.queryTaskDefine(taskId);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String sameSchemeCurrPeriod(ConsolidatedTaskVO consolidatedTaskReqVO, List<ConsolidatedTaskEO> taskEOS) {
        HashSet<String> taskIdSet = new HashSet<String>(16);
        for (ConsolidatedTaskEO taskEO : taskEOS) {
            if (taskEO.getId().equals(consolidatedTaskReqVO.getId()) || StringUtils.isEmpty((String)taskEO.getTaskKey())) continue;
            taskIdSet.add(taskEO.getTaskKey());
        }
        if (taskIdSet.contains(consolidatedTaskReqVO.getTaskKey())) {
            return consolidatedTaskReqVO.getTaskKey();
        }
        return null;
    }

    private List<ConsolidatedTaskEO> getAllBoundTasksSamePeriod(ConsolidatedTaskVO consolidatedTaskReqVO) {
        List<ConsolidatedTaskEO> allTaskEOS = this.consolidatedTaskDao.getAllBoundTasks();
        return allTaskEOS.stream().filter(eo -> this.isSamePeriod(consolidatedTaskReqVO, (ConsolidatedTaskEO)((Object)eo))).collect(Collectors.toList());
    }

    private boolean isSamePeriod(ConsolidatedTaskVO consolidatedTaskReqVO, ConsolidatedTaskEO eo) {
        Date newFromPeriodDate = StringUtils.isEmpty((String)consolidatedTaskReqVO.getFromPeriod()) ? null : YearPeriodUtil.transform(null, (String)consolidatedTaskReqVO.getFromPeriod()).getEndDate();
        Date newToPeriodDate = StringUtils.isEmpty((String)consolidatedTaskReqVO.getToPeriod()) ? null : YearPeriodUtil.transform(null, (String)consolidatedTaskReqVO.getToPeriod()).getEndDate();
        Date oldFromPeriodDate = StringUtils.isEmpty((String)eo.getFromPeriod()) ? null : YearPeriodUtil.transform(null, (String)eo.getFromPeriod()).getEndDate();
        Date oldToPeriodDate = StringUtils.isEmpty((String)eo.getToPeriod()) ? null : YearPeriodUtil.transform(null, (String)eo.getToPeriod()).getEndDate();
        return this.existSamePeriod(newFromPeriodDate, newToPeriodDate, oldFromPeriodDate, oldToPeriodDate);
    }

    private Boolean existSamePeriod(Date groupOneFromDate, Date groupOneToDate, Date groupTwoFromDate, Date groupTwoToDate) {
        if (this.compare(groupOneFromDate, groupTwoToDate) > 0 || this.compare(groupTwoFromDate, groupOneToDate) > 0) {
            return false;
        }
        return true;
    }

    private int compare(Date oneDate, Date anotherDate) {
        if (oneDate == null || anotherDate == null) {
            return -1;
        }
        return oneDate.compareTo(anotherDate);
    }

    @Override
    public List<ConsolidatedTaskVO> getAllDataCollectorScheme() {
        List<ConsolidatedTaskEO> consolidatedTasks = this.consolidatedTaskDao.getAllBoundTasks();
        return consolidatedTasks.stream().map(eo -> this.convertEO2VO((ConsolidatedTaskEO)((Object)eo))).collect(Collectors.toList());
    }

    @Override
    public List<FormSchemeDefine> listBoundSchemeVos(String taskId) throws Exception {
        List<ConsolidatedTaskVO> taskVOs = this.getTaskVOByTaskKey(taskId);
        HashSet schemeIdSet = new HashSet();
        taskVOs.forEach(item -> {
            schemeIdSet.addAll(ConsolidatedSystemUtils.listAllInputSchemeByConTaskVO(item));
            schemeIdSet.addAll(ConsolidatedSystemUtils.listAllManageSchemeByConTaskVO(item));
        });
        List formSchemeDefines = this.iRunTimeViewController.queryFormSchemeByTask(taskId);
        return formSchemeDefines.stream().filter(item -> schemeIdSet.contains(item.getKey())).collect(Collectors.toList());
    }

    @Override
    public List<ConsolidatedTaskVO> getTaskVOByTaskKey(String taskId) {
        List<ConsolidatedTaskEO> consolidatedTaskEOs = this.consolidatedTaskDao.getTaskByTaskKey(taskId);
        List<ConsolidatedTaskVO> consolidatedTaskVOs = consolidatedTaskEOs.stream().map(eo -> this.convertEO2VO((ConsolidatedTaskEO)((Object)eo))).collect(Collectors.toList());
        return consolidatedTaskVOs;
    }

    @Override
    public ConsolidatedTaskVO getTaskByTaskKeyAndPeriodStr(String taskId, String periodString) {
        return this.taskCacheService.getTaskByTaskId(taskId, periodString);
    }

    @Override
    public List<ConsolidatedTaskVO> listConsolidatedTaskBySystemIdAndPeriod(String systemId, String periodString) {
        return this.taskCacheService.listConsolidatedTaskBySystemIdAndPeriod(systemId, periodString);
    }

    @Override
    public boolean isEntryScheme(String schemeId, @NotNull String periodStr) {
        if (ObjectUtils.isEmpty(schemeId)) {
            return false;
        }
        ConsolidatedTaskVO taskVO = this.getTaskBySchemeId(schemeId, periodStr);
        return null != taskVO && schemeId.equals(ConsolidatedSystemUtils.getSchemeIdByTaskIdAndPeriod(taskVO.getTaskKey(), periodStr));
    }

    @Override
    public boolean managementCanCalc(String schemeId, String periodStr, String orgCode) {
        boolean isManagementScheme;
        ConsolidatedTaskVO taskVO = this.getTaskBySchemeId(schemeId, periodStr);
        Set<String> manageSchemeSet = ConsolidatedSystemUtils.listSchemeIdSetByTaskIdListAndPeriod(taskVO.getManageTaskKeys(), periodStr);
        boolean bl = isManagementScheme = taskVO != null && !org.springframework.util.CollectionUtils.isEmpty(manageSchemeSet) && manageSchemeSet.contains(schemeId);
        if (!isManagementScheme) {
            return false;
        }
        return !org.springframework.util.CollectionUtils.isEmpty(taskVO.getManageCalcUnitCodes()) && taskVO.getManageCalcUnitCodes().contains(orgCode);
    }

    @Override
    public String getSystemIdBySchemeId(String schemeId, String periodString) {
        ConsolidatedTaskVO taskVO = this.getTaskBySchemeId(schemeId, periodString);
        return null == taskVO ? null : taskVO.getSystemId();
    }

    @Override
    public String getSystemIdByTaskId(String taskId, String periodString) {
        ConsolidatedTaskVO taskVO = this.getTaskByTaskKeyAndPeriodStr(taskId, periodString);
        return null == taskVO ? null : taskVO.getSystemId();
    }

    @Override
    public Set<String> getRelevancySchemeKeys(List<String> schemeIds) {
        List<ConsolidatedTaskVO> taskVOs = this.getAllBoundTaskVOs();
        HashSet<String> result = new HashSet<String>();
        for (ConsolidatedTaskVO vo : taskVOs) {
            if (!schemeIds.contains(vo.getId())) continue;
            result.addAll(ConsolidatedSystemUtils.listAllInputSchemeByConTaskVO(vo));
        }
        return result;
    }

    @Override
    public List<String> listOrgTypeByTaskId(String taskId) {
        HashSet orgTypes = new HashSet();
        List<ConsolidatedTaskEO> consolidatedTaskEOList = this.consolidatedTaskDao.getTaskByTaskKey(taskId);
        consolidatedTaskEOList.forEach(item -> {
            if (item.getTaskKey().equals(taskId)) {
                orgTypes.add(item.getCorporateEntity());
                orgTypes.addAll(item.getManageEntity() == null ? Collections.emptySet() : StringUtils.split((String)item.getManageEntity(), (String)","));
            } else {
                TaskDefine taskDefine = this.queryTask(taskId);
                if (taskDefine != null) {
                    orgTypes.add(taskDefine.getDw().replace("@ORG", ""));
                }
            }
        });
        return new ArrayList<String>(orgTypes);
    }
}

