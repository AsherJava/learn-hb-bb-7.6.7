/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.common.util.FormSchemePeriodGcUtils
 *  com.jiuqi.gcreport.consolidatedsystem.api.task.ConsolidatedTaskClient
 *  com.jiuqi.gcreport.consolidatedsystem.vo.ConsolidatedSystemVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskInfoVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskSchemeVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nHelper
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  javax.validation.Valid
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.consolidatedsystem.web.task;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.common.util.FormSchemePeriodGcUtils;
import com.jiuqi.gcreport.consolidatedsystem.api.task.ConsolidatedTaskClient;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.ConsolidatedSystemVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskInfoVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskSchemeVO;
import com.jiuqi.gcreport.i18n.util.GcI18nHelper;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class ConsolidatedTaskController
implements ConsolidatedTaskClient {
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ConsolidatedSystemService consolidatedSystemService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<TaskDefine>> getConsolidatesdTasks() {
        List allTaskDefines = this.iRunTimeViewController.getAllTaskDefines();
        List result = allTaskDefines.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return BusinessResponseEntity.ok(result);
    }

    public BusinessResponseEntity<List<TaskInfoVO>> listTask() {
        List allTaskDefines = this.iRunTimeViewController.getAllTaskDefines();
        List<TaskDefine> result = allTaskDefines.stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<TaskInfoVO> taskInfoVOList = ConsolidatedSystemUtils.listTaskInfoVO(result);
        return BusinessResponseEntity.ok(taskInfoVOList);
    }

    public BusinessResponseEntity<ConsolidatedSystemVO> getSystemBySchemeId(String schemeId, String periodStr) {
        ConsolidatedSystemEO systemEO = this.consolidatedTaskService.getConsolidatedSystemBySchemeId(schemeId, periodStr);
        Assert.isNotNull((Object)((Object)systemEO), (String)"\u627e\u4e0d\u5230\u5408\u5e76\u4f53\u7cfb", (Object[])new Object[0]);
        String systemName = ((GcI18nHelper)SpringBeanUtils.getBean(GcI18nHelper.class)).getMessage(systemEO.getId());
        if (!StringUtils.isNull((String)systemName)) {
            systemEO.setSystemName(systemName);
        }
        return BusinessResponseEntity.ok((Object)this.consolidatedSystemService.convertEO2VO(systemEO));
    }

    public BusinessResponseEntity<ConsolidatedTaskVO> getSystemByTaskId(String taskId, String periodStr) {
        ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskByTaskKeyAndPeriodStr(taskId, periodStr);
        return BusinessResponseEntity.ok((Object)consolidatedTaskVO);
    }

    public BusinessResponseEntity<List<ConsolidatedSystemVO>> getAllRelateSystemBySchemeId(String schemeId) {
        List<ConsolidatedTaskVO> consolidatedTaskVOS = this.consolidatedTaskService.getConsolidatedTasksBySchemeId(schemeId);
        List<String> systemIds = consolidatedTaskVOS.stream().map(ConsolidatedTaskVO::getSystemId).collect(Collectors.toList());
        return BusinessResponseEntity.ok(this.consolidatedSystemService.getSystemsByIds(systemIds));
    }

    public BusinessResponseEntity<List<String>> getAllBoundTasks() {
        List<String> taskIds = this.consolidatedTaskService.getAllBoundTasks();
        return BusinessResponseEntity.ok(taskIds);
    }

    public BusinessResponseEntity<List<TaskDefine>> listAllBoundTaskVos() {
        List<String> taskIds = this.consolidatedTaskService.getAllBoundTasks();
        if (CollectionUtils.isEmpty(taskIds)) {
            return BusinessResponseEntity.ok();
        }
        List allTaskDefines = this.iRunTimeViewController.getAllTaskDefines();
        List result = allTaskDefines.stream().filter(taskDefine -> taskDefine != null && taskIds.contains(taskDefine.getKey())).collect(Collectors.toList());
        return BusinessResponseEntity.ok(result);
    }

    public BusinessResponseEntity<List<FormSchemeDefine>> listBoundSchemeVos(String taskId) throws Exception {
        return BusinessResponseEntity.ok(this.consolidatedTaskService.listBoundSchemeVos(taskId));
    }

    public BusinessResponseEntity<List<ConsolidatedTaskVO>> getConsolidatesdTasks(@PathVariable(value="systemId") String systemId) {
        return BusinessResponseEntity.ok(this.consolidatedTaskService.getConsolidatedTasks(systemId));
    }

    public BusinessResponseEntity<List<ConsolidatedTaskVO>> listConsolidatesdTasks(String systemId) {
        List<ConsolidatedTaskVO> consolidatedTasks = this.consolidatedTaskService.getConsolidatedTasks(systemId);
        for (ConsolidatedTaskVO consolidatedTask : consolidatedTasks) {
            try {
                List formSchemeDefines = this.iRunTimeViewController.queryFormSchemeByTask(consolidatedTask.getTaskKey()).stream().filter(formSchemeDefine -> this.authorityProvider.canReadFormScheme(formSchemeDefine.getKey())).collect(Collectors.toList());
                List<Object> beginPeriods = new ArrayList<String>();
                List<Object> endPeriods = new ArrayList<String>();
                for (FormSchemeDefine schemeDefine : formSchemeDefines) {
                    String[] fromToPeriodByFormSchemeKey = FormSchemePeriodGcUtils.getFromToPeriodByFormSchemeKey((String)schemeDefine.getKey());
                    beginPeriods.add(fromToPeriodByFormSchemeKey[0]);
                    endPeriods.add(fromToPeriodByFormSchemeKey[1]);
                }
                beginPeriods = beginPeriods.stream().filter(period -> !StringUtils.isEmpty((String)period)).sorted().collect(Collectors.toList());
                endPeriods = endPeriods.stream().filter(period -> !StringUtils.isEmpty((String)period)).sorted().collect(Collectors.toList());
                HashMap<String, String> enableRange = new HashMap<String, String>();
                enableRange.put("begin", CollectionUtils.isEmpty(beginPeriods) ? null : (String)beginPeriods.get(0));
                enableRange.put("end", CollectionUtils.isEmpty(endPeriods) ? null : (String)endPeriods.get(endPeriods.size() - 1));
                consolidatedTask.setEnableRange(Collections.singletonList(enableRange));
            }
            catch (Exception e) {
                throw new BusinessRuntimeException((Throwable)e);
            }
        }
        return BusinessResponseEntity.ok(consolidatedTasks);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> bindConsolidatesdTask(@Valid @RequestBody ConsolidatedTaskVO consolidatedTaskVO) {
        this.consolidatedTaskService.bindConsolidatedTask(consolidatedTaskVO);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f\uff01");
    }

    public BusinessResponseEntity<String> bindConsolidatesdTask(@Valid List<ConsolidatedTaskVO> consolidatedTaskVOs) {
        this.consolidatedTaskService.bindConsolidatedTask(consolidatedTaskVOs);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f\uff01");
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> unbindConsolidatesdTask(@PathVariable(value="ids") @RequestBody String[] ids) {
        this.consolidatedTaskService.unbindConsolidatedTask(ids);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f\uff01");
    }

    public BusinessResponseEntity<List<BaseDataVO>> getSchemeTree() throws Exception {
        ArrayList<BaseDataVO> results = new ArrayList<BaseDataVO>(16);
        List taskDefines = this.iRunTimeViewController.getAllTaskDefines();
        for (TaskDefine taskDefine : taskDefines) {
            BaseDataVO taskBaseDataVO = new BaseDataVO();
            taskBaseDataVO.setId(taskDefine.getKey());
            taskBaseDataVO.setCode(taskDefine.getTaskCode());
            taskBaseDataVO.setTitle(taskDefine.getTitle());
            taskBaseDataVO.setChildren(new ArrayList());
            List formSchemeDefines = this.iRunTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                TaskSchemeVO schemeBaseDataVO = new TaskSchemeVO();
                schemeBaseDataVO.setId(formSchemeDefine.getKey());
                schemeBaseDataVO.setCode(formSchemeDefine.getFormSchemeCode());
                schemeBaseDataVO.setTitle(formSchemeDefine.getTitle());
                schemeBaseDataVO.setParentid(taskBaseDataVO.getCode());
                schemeBaseDataVO.setTaskTitle(taskDefine.getTitle());
                schemeBaseDataVO.setTaskId(taskDefine.getKey());
                taskBaseDataVO.getChildren().add(schemeBaseDataVO);
            }
            results.add(taskBaseDataVO);
        }
        return BusinessResponseEntity.ok(results);
    }

    public BusinessResponseEntity<ConsolidatedTaskVO> getTaskBySchemeId(String schemeId, String periodStr) {
        return BusinessResponseEntity.ok((Object)this.consolidatedTaskService.getTaskBySchemeId(schemeId, periodStr));
    }

    public BusinessResponseEntity<List<String>> getRelevancySystemsBySchemeIds(@RequestBody List<String> schemeIds) {
        if (CollectionUtils.isEmpty(schemeIds)) {
            return BusinessResponseEntity.ok();
        }
        return BusinessResponseEntity.ok(this.consolidatedTaskService.getRelevancySystemsBySchemeIds(schemeIds));
    }

    public BusinessResponseEntity<List<String>> getRelevancySystemsInputSchemeIds(List<String> schemeIds) {
        if (CollectionUtils.isEmpty(schemeIds)) {
            return BusinessResponseEntity.ok();
        }
        return BusinessResponseEntity.ok(this.consolidatedTaskService.getRelevancySystemsInputSchemeIds(schemeIds));
    }

    public BusinessResponseEntity<String> exchangeSort(String opNodeId, int step) {
        try {
            this.consolidatedTaskService.exchangeSort(opNodeId, step);
            return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f");
        }
        catch (BusinessRuntimeException e) {
            return BusinessResponseEntity.error((String)e.getMessage());
        }
    }

    public BusinessResponseEntity<List<ConsolidatedTaskVO>> getAllDataCollectorScheme() {
        return BusinessResponseEntity.ok(this.consolidatedTaskService.getAllDataCollectorScheme());
    }

    public String getAllKey2TitleOfTask() {
        List allTaskDefines = this.iRunTimeViewController.getAllTaskDefines();
        List taskDefineList = allTaskDefines.stream().filter(taskDefine -> taskDefine != null).collect(Collectors.toList());
        StringBuilder result = new StringBuilder(32);
        result.append("[");
        for (TaskDefine taskDefine2 : taskDefineList) {
            result.append("{\"taskTitle\": \"").append(taskDefine2.getTitle()).append("\", \"taskKey\": \"").append(taskDefine2.getKey()).append("\"},");
        }
        if (result.length() > 1) {
            result.setLength(result.length() - 1);
        }
        result.append("]");
        return result.toString();
    }

    public BusinessResponseEntity<Set<String>> getRelevancySchemeKeys(List<String> schemeIds) {
        return BusinessResponseEntity.ok(this.consolidatedTaskService.getRelevancySchemeKeys(schemeIds));
    }
}

