/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import com.jiuqi.nr.definition.internal.dao.DesignTaskLinkDefineDao;
import com.jiuqi.nr.definition.internal.service.DesignTaskLinkOrgMapService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignTaskLinkDefineService {
    @Autowired
    private DesignTaskLinkDefineDao taskLinkDao;
    @Autowired
    private DesignTaskLinkOrgMapService designOrgMapService;

    public void setTaskLinkDao(DesignTaskLinkDefineDao taskLinkDao) {
        this.taskLinkDao = taskLinkDao;
    }

    public String insertTaskLinkDefine(DesignTaskLinkDefine define) throws Exception {
        if (define.getKey() == null) {
            define.setKey(UUIDUtils.getKey());
        }
        this.taskLinkDao.insert(define);
        this.designOrgMapService.insertOrgMappings(define.getKey(), define.getOrgMappingRules());
        return define.getKey();
    }

    public void updateTaskLinkDefine(DesignTaskLinkDefine define) throws Exception {
        this.taskLinkDao.update(define);
        HashMap<String, List<TaskLinkOrgMappingRule>> mappingMap = new HashMap<String, List<TaskLinkOrgMappingRule>>();
        mappingMap.put(define.getKey(), define.getOrgMappingRules());
        this.designOrgMapService.updateOrgMappings(mappingMap);
    }

    public DesignTaskLinkDefine queryTaskLinkDefine(String id) throws Exception {
        DesignTaskLinkDefine define = this.taskLinkDao.getDefineByKey(id);
        this.setTaskLinkOrgMapping(Arrays.asList(define));
        return define;
    }

    public List<DesignTaskLinkDefine> queryAllTaskLinkDefine() throws Exception {
        return this.taskLinkDao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.taskLinkDao.delete(keys);
        this.designOrgMapService.deleteByTaskLinkKeys(keys);
    }

    public void delete(String key) throws Exception {
        this.taskLinkDao.delete(key);
        this.designOrgMapService.deleteByTaskLinkKeys(new String[]{key});
    }

    public void deleteByCurrentFormSchemeKey(String currentFormSchemeKey) throws Exception {
        List<DesignTaskLinkDefine> designTaskLinkDefines = this.taskLinkDao.queryDefinesByCurrentFormScheme(currentFormSchemeKey);
        List<String> taskLinkKeys = designTaskLinkDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        this.designOrgMapService.deleteByTaskLinkKeys(taskLinkKeys.toArray(new String[taskLinkKeys.size()]));
        this.taskLinkDao.deleteByCurrentFormScheme(currentFormSchemeKey);
    }

    public void deleteByCurrentFormSchemeKeyAndNumber(String currentFormSchemeKey, String serialNumber) throws Exception {
        this.taskLinkDao.deleteByCurrentFormSchemeAndNumber(currentFormSchemeKey, serialNumber);
    }

    public List<String> insertTaskLinkDefines(DesignTaskLinkDefine[] defines) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        HashMap<String, List<TaskLinkOrgMappingRule>> map = new HashMap<String, List<TaskLinkOrgMappingRule>>();
        for (int i = 0; i < defines.length; ++i) {
            if (StringUtils.isEmpty((String)defines[i].getKey())) {
                String key = UUIDUtils.getKey();
                defines[i].setKey(key);
            }
            result.add(defines[i].getKey());
            if (defines[i].getOrgMappingRules() == null) continue;
            ArrayList<TaskLinkOrgMappingRule> orgMappingRules = new ArrayList<TaskLinkOrgMappingRule>();
            orgMappingRules.addAll(defines[i].getOrgMappingRules());
            map.put(defines[i].getKey(), orgMappingRules);
        }
        this.taskLinkDao.insert(defines);
        this.designOrgMapService.insertOrgMappings(map);
        return result;
    }

    public void updateTaskLinkDefines(DesignTaskLinkDefine[] defines) throws Exception {
        this.taskLinkDao.update(defines);
        HashMap<String, List<TaskLinkOrgMappingRule>> map = new HashMap<String, List<TaskLinkOrgMappingRule>>();
        for (DesignTaskLinkDefine define : defines) {
            List<TaskLinkOrgMappingRule> taskLinkOrgMappings = define.getOrgMappingRules();
            map.put(define.getKey(), taskLinkOrgMappings);
        }
        this.designOrgMapService.updateOrgMappings(map);
    }

    public List<DesignTaskLinkDefine> queryTaskLinkDefines(String[] keys) throws Exception {
        ArrayList<DesignTaskLinkDefine> list = new ArrayList<DesignTaskLinkDefine>();
        for (String key : keys) {
            DesignTaskLinkDefine define = this.taskLinkDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public List<DesignTaskLinkDefine> queryDefinesByCurrentFormSchemeKey(String currentFormSchemeKey) throws Exception {
        List<DesignTaskLinkDefine> designTaskLinkDefines = this.taskLinkDao.queryDefinesByCurrentFormScheme(currentFormSchemeKey);
        this.setTaskLinkOrgMapping(designTaskLinkDefines);
        return designTaskLinkDefines;
    }

    public DesignTaskLinkDefine queryDefinesByCurrentFormSchemeKeyAndNumber(String currentFormSchemeKey, String serialNumber) throws Exception {
        DesignTaskLinkDefine define = this.taskLinkDao.queryDefinesByCurrentFormSchemeAndNumber(currentFormSchemeKey, serialNumber);
        this.setTaskLinkOrgMapping(Arrays.asList(define));
        return define;
    }

    public List<DesignTaskLinkDefine> queryLinksByRelatedTaskCode(String relatedTaskCode) throws Exception {
        List<DesignTaskLinkDefine> designTaskLinkDefines = this.taskLinkDao.queryLinksByRelatedTaskCode(relatedTaskCode);
        this.setTaskLinkOrgMapping(designTaskLinkDefines);
        return designTaskLinkDefines;
    }

    public void setTaskLinkOrgMapping(List<DesignTaskLinkDefine> defines) {
        for (DesignTaskLinkDefine define : defines) {
            if (define == null) continue;
            List<TaskLinkOrgMappingRule> orgMappings = this.designOrgMapService.getOrgMappingByTaskLinkKey(define.getKey());
            define.setOrgMappingRule(orgMappings);
        }
    }
}

