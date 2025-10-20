/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskInfoVO
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller2.RunTimeViewController
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package com.jiuqi.gcreport.consolidatedsystem.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskInfoVO;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller2.RunTimeViewController;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.DataModelClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ConsolidatedSystemUtils {
    public static String getSchemeIdByTaskIdAndPeriod(String taskKey, String periodStr) {
        if (StringUtils.isEmpty((String)taskKey)) {
            return null;
        }
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = runTimeViewController.querySchemePeriodLinkByPeriodAndTask(periodStr, taskKey);
            return schemePeriodLinkDefine == null ? null : schemePeriodLinkDefine.getSchemeKey();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
    }

    public static Set<String> listSchemeIdSetByTaskIdListAndPeriod(List<String> manageTaskKeys, String periodStr) {
        if (CollectionUtils.isEmpty(manageTaskKeys)) {
            return null;
        }
        HashSet<String> manageSchemeSet = new HashSet<String>(manageTaskKeys.size());
        for (String manageTaskKey : manageTaskKeys) {
            if (StringUtils.isEmpty((String)manageTaskKey)) continue;
            manageSchemeSet.add(ConsolidatedSystemUtils.getSchemeIdByTaskIdAndPeriod(manageTaskKey, periodStr));
        }
        return manageSchemeSet;
    }

    public static List<String> listAllInputSchemeByConTaskVO(ConsolidatedTaskVO vo) {
        if (vo == null) {
            return Collections.emptyList();
        }
        String taskKey = vo.getTaskKey();
        if (StringUtils.isEmpty((String)taskKey)) {
            return Collections.emptyList();
        }
        return new ArrayList<String>(ConsolidatedSystemUtils.listSchemeKeyByTaskAndPeriodRange(taskKey, vo.getFromPeriod(), vo.getToPeriod()));
    }

    public static List<String> listAllManageSchemeByConTaskVO(ConsolidatedTaskVO vo) {
        if (vo == null) {
            return Collections.emptyList();
        }
        List manageTaskKeys = vo.getManageTaskKeys();
        if (CollectionUtils.isEmpty((Collection)manageTaskKeys)) {
            return Collections.emptyList();
        }
        HashSet<String> schemeSet = new HashSet<String>();
        for (String manageTaskKey : manageTaskKeys) {
            schemeSet.addAll(ConsolidatedSystemUtils.listSchemeKeyByTaskAndPeriodRange(manageTaskKey, vo.getFromPeriod(), vo.getToPeriod()));
        }
        return new ArrayList<String>(schemeSet);
    }

    public static Set<String> listSchemeKeyByTaskAndPeriodRange(String taskKey, String fromPeriod, String toPeriod) {
        List schemePeriodLinkDefines;
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        try {
            schemePeriodLinkDefines = runTimeViewController.querySchemePeriodLinkByTask(taskKey);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        boolean hasFrom = !StringUtils.isEmpty((String)fromPeriod);
        boolean hasTo = !StringUtils.isEmpty((String)toPeriod);
        return schemePeriodLinkDefines.stream().filter(item -> {
            if (hasFrom && item.getPeriodKey().compareTo(fromPeriod) < 0) {
                return false;
            }
            return !hasTo || item.getPeriodKey().compareTo(toPeriod) <= 0;
        }).map(SchemePeriodLinkDefine::getSchemeKey).collect(Collectors.toSet());
    }

    public static List<TaskInfoVO> listTaskInfoVO(List<TaskDefine> taskDefineList) {
        RunTimeViewController runTimeViewController = (RunTimeViewController)SpringContextUtils.getBean(RunTimeViewController.class);
        ArrayList<TaskInfoVO> taskInfoVOList = new ArrayList<TaskInfoVO>();
        if (CollectionUtils.isEmpty(taskDefineList)) {
            return taskInfoVOList;
        }
        taskDefineList.forEach(taskDefine -> {
            TaskOrgLinkListStream taskOrgLinkListStream = runTimeViewController.listTaskOrgLinkStreamByTask(taskDefine.getKey());
            List taskOrgLinkList = taskOrgLinkListStream.auth().i18n().getList();
            TaskInfoVO taskInfoVO = new TaskInfoVO();
            if (taskOrgLinkList != null && taskOrgLinkList.size() > 1) {
                taskInfoVO.setEnableMultiOrg(Integer.valueOf(1));
            }
            taskInfoVO.setTaskDefine(taskDefine);
            taskInfoVOList.add(taskInfoVO);
        });
        return taskInfoVOList;
    }

    public static Map<String, String> getMultilingualNamesTitle() {
        HashMap<String, String> multilingualNamesTitle = new HashMap<String, String>();
        DataModelClient dataModelClient = (DataModelClient)SpringContextUtils.getBean(DataModelClient.class);
        DataModelDTO param = new DataModelDTO();
        param.setName("MD_GCSUBJECT");
        DataModelDO dataModel = dataModelClient.get(param);
        if (dataModel != null) {
            List columns = dataModel.getColumns();
            for (DataModelColumn column : columns) {
                if (!column.getColumnName().toLowerCase().startsWith("name_")) continue;
                multilingualNamesTitle.put(column.getColumnName().toLowerCase(), column.getColumnTitle());
            }
        }
        return multilingualNamesTitle;
    }
}

