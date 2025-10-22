/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.definition.facade.DesignTaskLinkOrgMappingDefine;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import com.jiuqi.nr.definition.internal.dao.DesignTaskLinkOrgMappingDao;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DesignTaskLinkOrgMapService {
    @Autowired
    private DesignTaskLinkOrgMappingDao taskLinkOrgMappingDao;

    public void insertOrgMappings(Map<String, List<TaskLinkOrgMappingRule>> map) throws DBParaException {
        ArrayList<TaskLinkOrgMappingRule> needInsertMappings = new ArrayList<TaskLinkOrgMappingRule>();
        for (String taskLinkKey : map.keySet()) {
            this.checkOrgMappings(taskLinkKey, map.get(taskLinkKey));
            needInsertMappings.addAll((Collection<TaskLinkOrgMappingRule>)map.get(taskLinkKey));
        }
        this.doInsertOrgMappings(needInsertMappings);
    }

    public void insertOrgMappings(String taskLinkKey, List<TaskLinkOrgMappingRule> mappingRules) throws DBParaException {
        if (CollectionUtils.isEmpty(mappingRules)) {
            return;
        }
        this.checkOrgMappings(taskLinkKey, mappingRules);
        this.doInsertOrgMappings(mappingRules);
    }

    private void checkOrgMappings(String taskLinkKey, List<TaskLinkOrgMappingRule> mappingRules) {
        for (TaskLinkOrgMappingRule mapping : mappingRules) {
            if (!StringUtils.hasText(mapping.getTaskLinkKey())) {
                mapping.setTaskLinkKey(taskLinkKey);
            }
            if (StringUtils.hasText(mapping.getOrder())) continue;
            mapping.setOrder(OrderGenerator.newOrder());
        }
    }

    private void doInsertOrgMappings(List<TaskLinkOrgMappingRule> mappingRules) throws DBParaException {
        List<DesignTaskLinkOrgMappingDefine> designTaskLinkOrgMappingDefines = mappingRules.stream().map(TaskLinkOrgMappingRule::toDesignDefine).collect(Collectors.toList());
        this.taskLinkOrgMappingDao.insertOrgMappingRule(designTaskLinkOrgMappingDefines.toArray(new DesignTaskLinkOrgMappingDefine[designTaskLinkOrgMappingDefines.size()]));
    }

    public void deleteByTaskLinkKeys(String[] taskLinks) throws DBParaException {
        for (int i = 0; i < taskLinks.length; ++i) {
            this.taskLinkOrgMappingDao.deleteOrgMappingRuleByKey(taskLinks[i]);
        }
    }

    public void updateOrgMappings(Map<String, List<TaskLinkOrgMappingRule>> map) throws DBParaException {
        for (String taskLinkKey : map.keySet()) {
            this.taskLinkOrgMappingDao.deleteOrgMappingRuleByKey(taskLinkKey);
            if (CollectionUtils.isEmpty((Collection)map.get(taskLinkKey))) continue;
            this.insertOrgMappings(taskLinkKey, map.get(taskLinkKey));
        }
    }

    public List<TaskLinkOrgMappingRule> getOrgMappingByTaskLinkKey(String taskLinkKey) {
        List<DesignTaskLinkOrgMappingDefine> orgMappings = this.taskLinkOrgMappingDao.getByTaskLinkKey(taskLinkKey);
        if (!CollectionUtils.isEmpty(orgMappings)) {
            return orgMappings.stream().map(TaskLinkOrgMappingRule::valueOf).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

