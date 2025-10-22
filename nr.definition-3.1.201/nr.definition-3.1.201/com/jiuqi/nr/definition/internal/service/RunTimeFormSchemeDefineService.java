/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormSchemeDefineDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunTimeFormSchemeDefineService {
    @Autowired
    private RunTimeFormSchemeDefineDao formSchemeDao;

    public List<FormSchemeDefine> queryFormSchemeDefineByGroupId(String id) throws Exception {
        return this.formSchemeDao.listByGroup(id);
    }

    public List<FormSchemeDefine> queryFormSchemeDefineByTaskKey(String taskKey) throws Exception {
        return this.formSchemeDao.listByTask(taskKey);
    }

    public FormSchemeDefine queryFormSchemeDefine(String id) {
        return this.formSchemeDao.getDefineByKey(id);
    }

    public FormSchemeDefine queryFormSchemeDefineByCode(String code) throws Exception {
        return this.formSchemeDao.queryDefinesByCode(code);
    }

    public FormSchemeDefine queryFormSchemeDefineByfilePrefix(String filePrefix) throws Exception {
        return this.formSchemeDao.queryDefinesByfilePrefix(filePrefix);
    }

    public List<FormSchemeDefine> queryAllFormSchemeDefine() throws Exception {
        return this.formSchemeDao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.formSchemeDao.delete(keys);
    }

    public void delete(String key) throws Exception {
        this.formSchemeDao.delete(key);
    }

    public void deleteByTask(String taskKey) throws Exception {
        this.formSchemeDao.deleteByTask(taskKey);
    }

    public FormSchemeDefine queryFormSchemeDefinesByCode(String code) throws Exception {
        return this.formSchemeDao.queryDefinesByCode(code);
    }

    public void updateFormSchemeDefines(FormSchemeDefine[] defines) throws Exception {
        this.formSchemeDao.update(defines);
    }

    public List<FormSchemeDefine> queryFormSchemeDefines(String[] keys) throws Exception {
        ArrayList<FormSchemeDefine> list = new ArrayList<FormSchemeDefine>();
        for (String key : keys) {
            FormSchemeDefine define = this.formSchemeDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public void updateFormSchemeDefine(FormSchemeDefine define) throws Exception {
        this.formSchemeDao.update(define);
    }

    public void insertFormSchemeDefine(FormSchemeDefine formSchemeDefine) throws Exception {
        this.formSchemeDao.update(formSchemeDefine);
    }

    public void insertFormSchemeDefines(FormSchemeDefine[] formSchemeDefines) throws Exception {
        this.formSchemeDao.update(formSchemeDefines);
    }

    public List<FormSchemeDefine> listGhostSchemes() {
        return this.formSchemeDao.listGhostSchemes();
    }
}

