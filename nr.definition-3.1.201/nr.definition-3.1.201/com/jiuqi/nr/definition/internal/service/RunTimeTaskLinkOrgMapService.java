/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingDefine;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import com.jiuqi.nr.definition.internal.dao.RunTimeTaskLinkOrgMappingDao;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class RunTimeTaskLinkOrgMapService {
    @Autowired
    private RunTimeTaskLinkOrgMappingDao taskLinkOrgMappingDao;

    public List<TaskLinkOrgMappingRule> getByTaskLinkKey(String taskLinkKey) {
        List<TaskLinkOrgMappingDefine> mappingDefines = this.taskLinkOrgMappingDao.getByTaskLinkKey(taskLinkKey);
        if (!CollectionUtils.isEmpty(mappingDefines)) {
            return mappingDefines.stream().map(TaskLinkOrgMappingRule::valueOf).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

