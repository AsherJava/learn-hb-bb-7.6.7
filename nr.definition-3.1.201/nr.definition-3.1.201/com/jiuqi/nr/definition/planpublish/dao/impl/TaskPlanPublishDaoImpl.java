/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.planpublish.dao.impl;

import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao;
import com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TaskPlanPublishDaoImpl
extends BaseDao
implements TaskPlanPublishDao {
    private final Class<TaskPlanPublish> implClass = TaskPlanPublish.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    @Override
    public TaskPlanPublish queryWorkPlan(String taskKey) throws Exception {
        return (TaskPlanPublish)super.getBy("TPP_TASKKEY=? AND TPP_WORKSTATUS=?", new Object[]{taskKey, "do"}, this.implClass);
    }

    @Override
    public List<TaskPlanPublish> queryAllProtectingWorkPlan() throws Exception {
        return super.list(" TPP_WORKSTATUS=? AND (TPP_PUBLISHSTATUS=? OR TPP_PUBLISHSTATUS=?) ", new Object[]{"do", "publishing", "protecting"}, this.implClass);
    }

    @Override
    public List<TaskPlanPublish> queryAllPublishing() throws Exception {
        return super.list("TPP_PUBLISHSTATUS=? AND TPP_WORKSTATUS=?", new Object[]{"publishing", "do"}, this.implClass);
    }

    @Override
    public void insert(TaskPlanPublish taskPlanPublish) throws Exception {
        super.insert((Object)taskPlanPublish);
    }

    @Override
    public void update(TaskPlanPublish taskPlanPublish) throws Exception {
        super.update((Object)taskPlanPublish);
    }

    @Override
    public TaskPlanPublish query(String key) throws Exception {
        if (!StringUtils.isEmpty((String)key)) {
            return (TaskPlanPublish)super.getByKey((Object)key, this.implClass);
        }
        return null;
    }

    @Override
    public void undoOtherPlan(String planKey, String taskKey) throws Exception {
        String sql = "UPDATE NR_TASK_PLANPUBLISH SET TPP_WORKSTATUS=? WHERE TPP_TASKKEY= ? AND TPP_KEY!= ?";
        this.jdbcTemplate.update(sql, pss -> {
            pss.setString(1, "undo");
            pss.setString(2, taskKey);
            pss.setString(3, planKey);
        });
    }

    @Override
    public TaskPlanPublish getByTask(String key) {
        return (TaskPlanPublish)super.getBy("TPP_TASKKEY=? ORDER BY TPP_PUBLISHDATE DESC", new Object[]{key}, TaskPlanPublish.class);
    }

    @Override
    public List<TaskPlanPublish> listTask(String key) {
        return super.list("TPP_TASKKEY=? ORDER BY TPP_UPDATETIME DESC", new Object[]{key}, this.implClass);
    }

    @Override
    public void deleteByTaskKey(String taskKey) {
        String sql = "DELETE FROM NR_TASK_PLANPUBLISH WHERE TPP_TASKKEY = ?";
        this.jdbcTemplate.update(sql, new Object[]{taskKey});
    }
}

