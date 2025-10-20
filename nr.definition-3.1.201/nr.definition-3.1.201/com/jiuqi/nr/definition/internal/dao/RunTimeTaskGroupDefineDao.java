/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeTaskGroupDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeTaskGroupLink;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeTaskGroupDefineDao
extends BaseDao {
    private static String ATTR_GROUP = "groupName";
    private static String ATTR_CODE = "taskCode";
    private Class<RunTimeTaskGroupDefineImpl> implClass = RunTimeTaskGroupDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<TaskGroupDefine> listByGroup(String id) throws Exception {
        return this.list(new String[]{ATTR_GROUP}, new Object[]{id}, this.implClass);
    }

    public List<TaskGroupDefine> list() {
        return this.list(this.implClass);
    }

    public TaskGroupDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (TaskGroupDefine)defines.get(0);
        }
        return null;
    }

    public TaskGroupDefine getDefineByKey(String id) throws Exception {
        return (TaskGroupDefine)this.getByKey(id, this.implClass);
    }

    public List<TaskGroupDefine> getAllGroupsFromTask(String taskKey) throws Exception {
        RunTimeTaskGroupLink link = new RunTimeTaskGroupLink();
        link.setTaskKey(taskKey);
        return this.listByForeign(link, new String[]{"taskKey"}, RunTimeTaskGroupDefineImpl.class);
    }
}

