/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeTaskLinkDefineDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunTimeTaskLinkDefineService {
    @Autowired
    private RunTimeTaskLinkDefineDao taskLinkDao;

    public void setTaskLinkDao(RunTimeTaskLinkDefineDao taskLinkDao) {
        this.taskLinkDao = taskLinkDao;
    }

    public void insertTaskLinkDefine(TaskLinkDefine define) throws Exception {
        this.taskLinkDao.insert(define);
    }

    public void insertTaskLinkDefine(TaskLinkDefine[] define) throws Exception {
        this.taskLinkDao.insert(define);
    }

    public void updateTaskLinkDefine(TaskLinkDefine define) throws Exception {
        this.taskLinkDao.update(define);
    }

    public void updateTaskLinkDefine(TaskLinkDefine[] define) throws Exception {
        this.taskLinkDao.update(define);
    }

    public TaskLinkDefine queryTaskLinkDefine(String id) throws Exception {
        return this.taskLinkDao.getDefineByKey(id);
    }

    public List<TaskLinkDefine> queryAllTaskLinkDefine() throws Exception {
        return this.taskLinkDao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.taskLinkDao.delete(keys);
    }

    public void delete(String key) throws Exception {
        this.taskLinkDao.delete(key);
    }

    public void deleteByCurrentFormSchemeKey(String currentFormSchemeKey) throws Exception {
        this.taskLinkDao.deleteByCurrentFormScheme(currentFormSchemeKey);
    }

    public void deleteByCurrentFormSchemeKeyAndNumber(String currentFormSchemeKey, String serialNumber) throws Exception {
        this.taskLinkDao.deleteByCurrentFormSchemeAndNumber(currentFormSchemeKey, serialNumber);
    }

    public List<TaskLinkDefine> queryTaskLinkDefines(String[] keys) throws Exception {
        ArrayList<TaskLinkDefine> list = new ArrayList<TaskLinkDefine>();
        for (String key : keys) {
            TaskLinkDefine define = this.taskLinkDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public List<TaskLinkDefine> queryDefinesByCurrentFormSchemeKey(String currentFormSchemeKey) throws Exception {
        return this.taskLinkDao.queryDefinesByCurrentFormScheme(currentFormSchemeKey);
    }

    public TaskLinkDefine queryDefinesByCurrentFormSchemeKeyAndNumber(String currentFormSchemeKey, String serialNumber) throws Exception {
        return this.taskLinkDao.queryDefinesByCurrentFormSchemeAndNumber(currentFormSchemeKey, serialNumber);
    }

    public List<TaskLinkDefine> queryLinksByRelatedTaskCode(String relatedTaskCode) throws Exception {
        return this.taskLinkDao.queryLinksByRelatedTaskCode(relatedTaskCode);
    }
}

