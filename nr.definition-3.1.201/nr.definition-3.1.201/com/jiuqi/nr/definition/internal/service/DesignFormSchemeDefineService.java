/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.event.ParamChangeEvent;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.dao.DesignFormSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.DesignSchemePeriodLinkDao;
import com.jiuqi.nr.definition.internal.service.AbstractParamService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignFormSchemeDefineService
extends AbstractParamService {
    public static final String BIG_ANALYSIS_SCHEME_PARAM = "ANALYSIS_PARAM";
    @Autowired
    private DesignFormSchemeDefineDao formSchemeDao;
    @Autowired
    private DesignSchemePeriodLinkDao designSchemePeriodLinkDao;

    public void setFormSchemedao(DesignFormSchemeDefineDao formSchemeDao) {
        this.formSchemeDao = formSchemeDao;
    }

    public void setDesignSchemePeriodLinkDao(DesignSchemePeriodLinkDao designSchemePeriodLinkDao) {
        this.designSchemePeriodLinkDao = designSchemePeriodLinkDao;
    }

    public String insertFormSchemeDefine(DesignFormSchemeDefine define) throws Exception {
        if (define.getKey() == null) {
            define.setKey(UUIDUtils.getKey());
        }
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        this.formSchemeDao.insert(define);
        return define.getKey();
    }

    public void updateFormSchemeDefine(DesignFormSchemeDefine define) throws Exception {
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        this.formSchemeDao.update(define);
    }

    public List<DesignFormSchemeDefine> queryFormSchemeDefineByGroupId(String id) throws Exception {
        return this.formSchemeDao.listByGroup(id);
    }

    public List<DesignFormSchemeDefine> queryFormSchemeDefineByTaskKey(String taskKey) throws Exception {
        return this.formSchemeDao.listByTask(taskKey);
    }

    public DesignFormSchemeDefine queryFormSchemeDefine(String id) throws Exception {
        return this.formSchemeDao.getDefineByKey(id);
    }

    public DesignFormSchemeDefine queryFormSchemeDefineByCode(String code) throws Exception {
        return this.formSchemeDao.queryDefinesByCode(code);
    }

    public DesignFormSchemeDefine queryFormSchemeDefineByfilePrefix(String filePrefix) throws Exception {
        return this.formSchemeDao.queryDefinesByfilePrefix(filePrefix);
    }

    public DesignFormSchemeDefine queryFormSchemeDefineByTaskPrefix(String taskPrefix) throws Exception {
        return this.formSchemeDao.queryDefinesByTaskPrefix(taskPrefix);
    }

    public List<DesignFormSchemeDefine> queryAllFormSchemeDefine() throws Exception {
        return this.formSchemeDao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.formSchemeDao.delete(keys);
    }

    public void delete(String key) throws Exception {
        this.formSchemeDao.delete(key);
    }

    public DesignFormSchemeDefine queryFormSchemeDefinesByCode(String code) throws Exception {
        return this.formSchemeDao.queryDefinesByCode(code);
    }

    public List<String> insertFormSchemeDefines(DesignFormSchemeDefine[] defines) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < defines.length; ++i) {
            if (defines[i].getKey() == null) {
                defines[i].setKey(UUIDUtils.getKey());
            }
            if (defines[i].getUpdateTime() == null) {
                defines[i].setUpdateTime(new Date());
            }
            result.add(defines[i].getKey());
        }
        this.formSchemeDao.insert(defines);
        return result;
    }

    public void updateFormSchemeDefines(DesignFormSchemeDefine[] defines) throws Exception {
        for (DesignFormSchemeDefine define : defines) {
            if (define.getUpdateTime() != null) continue;
            define.setUpdateTime(new Date());
        }
        this.formSchemeDao.update(defines);
    }

    public List<DesignFormSchemeDefine> queryFormSchemeDefines(String[] keys) throws Exception {
        ArrayList<DesignFormSchemeDefine> list = new ArrayList<DesignFormSchemeDefine>();
        for (String key : keys) {
            DesignFormSchemeDefine define = this.formSchemeDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public List<DesignFormSchemeDefine> listGhostSchemes() throws Exception {
        return this.formSchemeDao.listGhostSchemes();
    }

    public List<DesignSchemePeriodLinkDefine> querySchemePeriodLinkByScheme(String scheme) throws Exception {
        return this.designSchemePeriodLinkDao.queryByScheme(scheme);
    }

    public List<DesignSchemePeriodLinkDefine> querySchemePeriodLinkByTask(String task) throws Exception {
        return this.designSchemePeriodLinkDao.queryByTask(task);
    }

    public DesignSchemePeriodLinkDefine querySchemePeriodLinkByPeriodAndScheme(String period, String scheme) throws Exception {
        DesignFormSchemeDefine queryFormSchemeDefine = this.queryFormSchemeDefine(scheme);
        return this.designSchemePeriodLinkDao.queryLinkByPeriodAndTask(period, queryFormSchemeDefine.getTaskKey());
    }

    public DesignSchemePeriodLinkDefine querySchemePeriodLinkByPeriodAndTask(String period, String task) throws Exception {
        return this.designSchemePeriodLinkDao.queryLinkByPeriodAndTask(period, task);
    }

    public void deleteSchemePeriodLink(List<DesignSchemePeriodLinkDefine> defines) throws Exception {
        this.designSchemePeriodLinkDao.delete(defines.toArray(new DesignSchemePeriodLinkDefine[defines.size()]));
        this.onFormSchemePeriodChanged(ParamChangeEvent.ChangeType.DELETE, defines.stream().map(SchemePeriodLinkDefine::getSchemeKey).distinct().collect(Collectors.toList()));
    }

    public void deleteByScheme(String scheme) throws Exception {
        this.designSchemePeriodLinkDao.deleteDataByScheme(scheme);
        this.onFormSchemePeriodChanged(ParamChangeEvent.ChangeType.DELETE, Collections.singletonList(scheme));
    }

    public void inserSchemePeriodLink(List<DesignSchemePeriodLinkDefine> defines) throws Exception {
        this.designSchemePeriodLinkDao.insert(defines.toArray(new DesignSchemePeriodLinkDefine[defines.size()]));
        this.onFormSchemePeriodChanged(ParamChangeEvent.ChangeType.ADD, defines.stream().map(SchemePeriodLinkDefine::getSchemeKey).distinct().collect(Collectors.toList()));
    }
}

