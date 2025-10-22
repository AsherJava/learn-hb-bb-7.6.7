/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.task.common.TaskPeriodUtils
 *  com.jiuqi.gcreport.common.task.vo.OptionVO
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedTaskChangedEvent
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskInfoVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.np.cache.NedisCacheManager
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
 */
package com.jiuqi.gcreport.consolidatedsystem.service.task.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.task.common.TaskPeriodUtils;
import com.jiuqi.gcreport.common.task.vo.OptionVO;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.dao.task.ConsolidatedTaskDao;
import com.jiuqi.gcreport.consolidatedsystem.dao.task.ConsolidatedTaskDataDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.task.ConsolidatedTaskDataEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.task.ConsolidatedTaskEO;
import com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedTaskChangedEvent;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskCacheService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskInfoVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.np.cache.NedisCacheManager;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class ConsolidatedTaskCacheServiceImpl
implements ConsolidatedTaskCacheService,
ApplicationListener<ConsolidatedTaskChangedEvent> {
    private NedisCacheManager cacheManger;
    @Autowired
    private ConsolidatedTaskDao consolidatedTaskDao;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private ConsolidatedTaskDataDao consolidatedTaskDataDao;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private RunTimeViewController runTimeViewController;

    public ConsolidatedTaskCacheServiceImpl(NedisCacheManager cacheManger) {
        this.cacheManger = cacheManger;
    }

    @Override
    public void onApplicationEvent(ConsolidatedTaskChangedEvent event) {
        if (null == event.getConsolidatedTaskChangedInfo()) {
            return;
        }
        this.cacheManger.getCache("gcreport:conTask").clear();
    }

    @Override
    public ConsolidatedTaskVO getTaskBySchemeId(String schemeId, String periodString) {
        if (ObjectUtils.isEmpty(schemeId)) {
            return null;
        }
        if (StringUtils.isEmpty((String)periodString)) {
            return null;
        }
        ConsolidatedTaskVO taskVO = (ConsolidatedTaskVO)this.cacheManger.getCache("gcreport:conTask").get(schemeId + periodString, () -> this.valueLoader(schemeId, periodString));
        return taskVO;
    }

    @Override
    public ConsolidatedTaskVO getTaskByTaskId(String taskKey, String periodString) {
        if (ObjectUtils.isEmpty(taskKey)) {
            return null;
        }
        if (StringUtils.isEmpty((String)periodString)) {
            return null;
        }
        ConsolidatedTaskVO taskVO = (ConsolidatedTaskVO)this.cacheManger.getCache("gcreport:conTask").get(taskKey + periodString, () -> this.getConTaskByTaskValueLoader(taskKey, periodString));
        return taskVO;
    }

    @Override
    public String getSystemIdByTaskId(String taskId, String periodString) {
        ConsolidatedTaskVO taskVO = this.getTaskByTaskId(taskId, periodString);
        return null == taskVO ? null : taskVO.getSystemId();
    }

    @Override
    public List<ConsolidatedTaskVO> listConsolidatedTaskBySystemIdAndPeriod(String systemId, String period) {
        if (StringUtils.isEmpty((String)systemId)) {
            return null;
        }
        if (StringUtils.isEmpty((String)period)) {
            return null;
        }
        return (List)this.cacheManger.getCache("gcreport:conTask").get("taskList|" + systemId + period, () -> this.getConsolidatedTasksValueLoader(systemId, period));
    }

    private ConsolidatedTaskVO valueLoader(String schemeId, String periodString) {
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(schemeId);
        if (formScheme == null) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u4efb\u52a1\u65b9\u6848\u3002");
        }
        List<ConsolidatedTaskEO> eos = this.consolidatedTaskDao.getTaskByTaskKeyAndPeriodStr(formScheme.getTaskKey(), periodString);
        if (!CollectionUtils.isEmpty(eos)) {
            ConsolidatedTaskEO taskEO = eos.get(0);
            return this.convertEO2VO(taskEO);
        }
        return null;
    }

    private ConsolidatedTaskVO getConTaskByTaskValueLoader(String taskKey, String periodString) {
        List<ConsolidatedTaskEO> eos = this.consolidatedTaskDao.getTaskByTaskKeyAndPeriodStr(taskKey, periodString);
        if (!org.springframework.util.CollectionUtils.isEmpty(eos)) {
            ConsolidatedTaskEO taskEO = eos.get(0);
            return this.convertEO2VO(taskEO);
        }
        return null;
    }

    private List<ConsolidatedTaskVO> getConsolidatedTasksValueLoader(String systemId, String dataTime) {
        ConsolidatedTaskEO templet = new ConsolidatedTaskEO();
        templet.setSystemId(systemId);
        List eos = this.consolidatedTaskDao.selectList((BaseEntity)templet);
        List consolidatedTasks = eos.stream().sorted(Comparator.comparing(ConsolidatedTaskEO::getSortOrder)).map(eo -> this.convertEO2VO((ConsolidatedTaskEO)((Object)eo))).collect(Collectors.toList());
        if (org.springframework.util.CollectionUtils.isEmpty(consolidatedTasks)) {
            return Collections.emptyList();
        }
        ArrayList<ConsolidatedTaskVO> result = new ArrayList<ConsolidatedTaskVO>();
        for (ConsolidatedTaskVO taskVO : consolidatedTasks) {
            if (!StringUtils.isEmpty((String)taskVO.getFromPeriod()) && dataTime.compareTo(taskVO.getFromPeriod()) < 0 || !StringUtils.isEmpty((String)taskVO.getToPeriod()) && dataTime.compareTo(taskVO.getToPeriod()) > 0) continue;
            result.add(taskVO);
        }
        return result;
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

    private TaskDefine queryTask(String taskId) {
        try {
            return this.iRunTimeViewController.queryTaskDefine(taskId);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
}

