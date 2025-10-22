/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.impl.TaskOrgLinkDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TaskOrgLinkDao
extends BaseDao {
    private static String ATTR_TASK_KEY = "task";
    private static String ATTR_ENTITY_KEY = "entity";
    private Class<TaskOrgLinkDefineImpl> implClass = TaskOrgLinkDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public TaskOrgLinkDefine getByKey(String taskOrgLinkKey) {
        return (TaskOrgLinkDefine)this.getByKey(taskOrgLinkKey, this.implClass);
    }

    public List<TaskOrgLinkDefine> list() {
        return super.list(this.implClass);
    }

    public List<TaskOrgLinkDefine> getByTask(String task) {
        return this.list(new String[]{ATTR_TASK_KEY}, new String[]{task}, this.implClass);
    }

    public TaskOrgLinkDefine getByTaskAndEntity(String task, String entity) {
        return (TaskOrgLinkDefine)this.getBy("TO_TASK = ? AND TO_ENTITY = ?", new Object[]{task, entity}, this.implClass);
    }

    public void insert(TaskOrgLinkDefine[] orgLinkDefines) throws DBParaException {
        super.insert((Object[])orgLinkDefines);
    }

    public void delete(String[] keys) throws DBParaException {
        super.delete((Object[])keys);
    }

    public void deleteByTask(String task) throws DBParaException {
        super.deleteBy(new String[]{ATTR_TASK_KEY}, (Object[])new String[]{task});
    }

    public void update(TaskOrgLinkDefine[] orgLinkDefines) throws DBParaException {
        super.update((Object[])orgLinkDefines);
    }
}

