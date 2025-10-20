/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormGroupDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormSchemeDefineDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunTimeFormGroupDefineService {
    @Autowired
    private RunTimeFormGroupDefineDao formGroupDao;
    @Autowired
    private RunTimeFormSchemeDefineDao formSchemeDao;

    public void setFormGroupDao(RunTimeFormGroupDefineDao formGroupDao) {
        this.formGroupDao = formGroupDao;
    }

    public void setFormSchemeDao(RunTimeFormSchemeDefineDao formSchemeDao) {
        this.formSchemeDao = formSchemeDao;
    }

    public void updateFormGroupDefine(FormGroupDefine define) throws Exception {
        this.formGroupDao.update(define);
    }

    public List<FormGroupDefine> queryFormGroupDefineByGroupId(String id) throws Exception {
        return this.formGroupDao.listByGroup(id);
    }

    public FormGroupDefine queryFormGroupDefine(String id) throws Exception {
        return this.formGroupDao.getDefineByKey(id);
    }

    public FormGroupDefine queryFormGroupDefineByCode(String code) throws Exception {
        return this.formGroupDao.queryDefinesByCode(code);
    }

    public List<FormGroupDefine> queryAllFormGroupDefine() throws Exception {
        return this.formGroupDao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.formGroupDao.delete(keys);
    }

    public void delete(String key) throws Exception {
        this.formGroupDao.delete(key);
    }

    public void deleteByScheme(String Schemekey) throws Exception {
        this.formGroupDao.deleteByScheme(Schemekey);
    }

    public void deleteAllFormGroups() throws Exception {
        this.formGroupDao.deleteAll();
    }

    public List<FormGroupDefine> queryFormGroupDefinesByScheme(String formSchemeId) throws Exception {
        return this.formGroupDao.queryDefinesByFormScheme(formSchemeId);
    }

    public List<FormGroupDefine> queryFormGroupDefinesByScheme(String formSchemeKey, String formGroupTitle) throws Exception {
        return this.formGroupDao.queryDefinesByFormScheme(formSchemeKey, formGroupTitle);
    }

    public List<FormGroupDefine> queryFormGroupDefinesByTitle(String formGroupTitle) throws Exception {
        return this.formGroupDao.queryDefinesByTitle(formGroupTitle);
    }

    public List<FormGroupDefine> queryFormGroupDefinesBySchemeAndParent(String formSchemeKey, String parentKey) throws Exception {
        return this.formGroupDao.queryDefinesByFormSchemeAndParent(formSchemeKey, parentKey);
    }

    public List<FormGroupDefine> queryDefinesByParent(String parentKey) throws Exception {
        return this.formGroupDao.queryDefinesByParent(parentKey);
    }

    public List<FormGroupDefine> queryDefinesByParent(String parentKey, boolean isRecursion) throws Exception {
        ArrayList<FormGroupDefine> list = new ArrayList<FormGroupDefine>();
        List<FormGroupDefine> list2 = this.formGroupDao.queryDefinesByParent(parentKey);
        list.addAll(list2);
        if (isRecursion) {
            for (FormGroupDefine group : list2) {
                this.queryDefinesByParentToList(list, group.getKey());
            }
        }
        return list;
    }

    private void queryDefinesByParentToList(List<FormGroupDefine> list, String parentKey) throws Exception {
        List<FormGroupDefine> list2 = this.formGroupDao.queryDefinesByParent(parentKey);
        list.addAll(list2);
        if (list2.size() > 0) {
            for (FormGroupDefine group : list2) {
                this.queryDefinesByParentToList(list, group.getKey());
            }
        }
    }

    public List<FormGroupDefine> queryFormGroupDefinesBySchemes(String[] formSchemeIds) throws Exception {
        ArrayList<FormGroupDefine> list = new ArrayList<FormGroupDefine>();
        for (String id : formSchemeIds) {
            list.addAll(this.queryFormGroupDefinesByScheme(id));
        }
        return list;
    }

    public List<FormGroupDefine> queryFormGroupDefinesByTask(String taskKey) throws Exception {
        List<FormSchemeDefine> list = this.formSchemeDao.listByTask(taskKey);
        List<FormGroupDefine> defines = this.queryFormGroupDefinesBySchemes(list.toArray(new String[0]));
        return defines;
    }

    public FormGroupDefine queryFormGroupDefinesByCode(String code) throws Exception {
        return this.formGroupDao.queryDefinesByCode(code);
    }

    public void updateFormGroupDefines(FormGroupDefine[] defines) throws Exception {
        this.formGroupDao.update(defines);
    }

    public List<FormGroupDefine> queryFormGroupDefines(String[] keys) throws Exception {
        ArrayList<FormGroupDefine> list = new ArrayList<FormGroupDefine>();
        for (String key : keys) {
            FormGroupDefine define = this.formGroupDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public List<FormGroupDefine> getAllGroupsFromForm(String formKey) throws Exception {
        List<FormGroupDefine> list = this.formGroupDao.getAllGroupsFromForm(formKey);
        return list;
    }

    public List<FormGroupDefine> getAllFormGroupLink() throws Exception {
        List<FormGroupDefine> list = this.formGroupDao.getAllGroupsFromLink();
        return list;
    }

    public void insertFormGroupDefine(FormGroupDefine formGroupDefine) throws Exception {
        this.formGroupDao.update(formGroupDefine);
    }

    public void insertFormGroupDefines(FormGroupDefine[] formGroupDefines) throws Exception {
        this.formGroupDao.update(formGroupDefines);
    }

    public List<FormGroupDefine> listGhostGroup() {
        return this.formGroupDao.listGhostGroup();
    }
}

