/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeTaskDefineDao;
import com.jiuqi.nr.definition.internal.impl.RunTimeTaskDefineImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Deprecated
public class RunTimeTaskDefineService {
    @Autowired
    private RunTimeTaskDefineDao taskDao;

    public void setTaskDao(RunTimeTaskDefineDao taskDao) {
        this.taskDao = taskDao;
    }

    public void updateTaskDefine(TaskDefine define) throws Exception {
        this.taskDao.update(define);
    }

    public List<TaskDefine> queryTaskDefineByGroupId(String id) throws Exception {
        return this.taskDao.listByGroup(id);
    }

    public TaskDefine queryTaskDefine(String id) throws Exception {
        return (TaskDefine)this.taskDao.getByKey(id, RunTimeTaskDefineImpl.class);
    }

    public List<TaskDefine> queryAllTaskDefine() throws Exception {
        return this.taskDao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.taskDao.delete(keys);
    }

    public TaskDefine queryTaskDefinesByCode(String code) throws Exception {
        return this.taskDao.queryTableDefinesByCode(code);
    }

    public TaskDefine queryTaskDefinesByFilePrefix(String filePrefix) throws Exception {
        return this.taskDao.queryTaskDefinesByFilePrefix(filePrefix);
    }

    public void updateTaskDefines(TaskDefine[] defines) throws Exception {
        this.taskDao.update(defines);
    }

    public List<TaskDefine> querTaskDefines(String[] keys) throws Exception {
        ArrayList<TaskDefine> list = new ArrayList<TaskDefine>();
        for (String key : keys) {
            TaskDefine define = (TaskDefine)this.taskDao.getByKey(key, RunTimeTaskDefineImpl.class);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public void insertTaskDefine(TaskDefine taskDefine) throws Exception {
        this.taskDao.insert(taskDefine);
    }

    public void insertTaskDefines(TaskDefine[] taskDefines) throws Exception {
        this.taskDao.insert(taskDefines);
    }

    public List<TaskDefine> getByDataSchemeKey(String dataSchemeKey) {
        return this.taskDao.getByDataSchemeKey(dataSchemeKey);
    }
}

