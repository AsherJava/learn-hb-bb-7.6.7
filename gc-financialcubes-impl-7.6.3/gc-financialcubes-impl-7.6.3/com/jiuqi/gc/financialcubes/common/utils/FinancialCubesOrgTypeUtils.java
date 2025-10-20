/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskCacheService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskInfoVO
 */
package com.jiuqi.gc.financialcubes.common.utils;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskCacheService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskInfoVO;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialCubesOrgTypeUtils {
    @Autowired
    private ConsolidatedTaskCacheService taskCacheService;

    public Set<String> listOrgType(String orgType, String systemId, String dataTime) {
        if (!"NONE".equals(orgType)) {
            return Collections.singleton(orgType);
        }
        HashSet<String> orgTypeSet = new HashSet<String>();
        List consolidatedTasks = this.taskCacheService.listConsolidatedTaskBySystemIdAndPeriod(systemId, dataTime);
        for (ConsolidatedTaskVO taskVO : consolidatedTasks) {
            TaskInfoVO inputTaskInfo = taskVO.getInputTaskInfo();
            if (inputTaskInfo != null && !StringUtils.isEmpty((String)inputTaskInfo.getUnitDefine())) {
                orgTypeSet.add(inputTaskInfo.getUnitDefine());
            }
            if (CollectionUtils.isEmpty((Collection)taskVO.getManageTaskInfos())) continue;
            for (TaskInfoVO manageTask : taskVO.getManageTaskInfos()) {
                if (manageTask == null || StringUtils.isEmpty((String)manageTask.getUnitDefine())) continue;
                orgTypeSet.add(manageTask.getUnitDefine());
            }
        }
        return orgTypeSet;
    }
}

