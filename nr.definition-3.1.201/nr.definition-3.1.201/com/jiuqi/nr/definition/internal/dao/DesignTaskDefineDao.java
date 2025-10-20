/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignTaskDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import java.util.List;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Repository;

@Repository
public class DesignTaskDefineDao
extends BaseDao {
    private static String FIELD_KEY = "tk_key";
    private static String ATTR_GROUP = "groupName";
    private static String ATTR_CODE = "taskCode";
    private static String ATTR_FilePrefix = "taskFilePrefix";
    private static String ATTR_Title = "title";
    private static String ATTR_TYPE = "taskType";
    private static String ATTR_DATASCHEME = "dataScheme";
    private static String ATTR_FILTER_TEMPLATE = "filterTemplate";
    private static String FIELD_EXPRESSION = "tk_filter_expression";
    private static String FIELD_UPDATE_TIME = "tk_updatetime";
    private static String TABLE_NAME = "NR_PARAM_TASK";
    private Class<DesignTaskDefineImpl> implClass = DesignTaskDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DesignTaskDefine> listByGroup(String id) throws Exception {
        return this.list(new String[]{ATTR_GROUP}, new Object[]{id}, this.implClass);
    }

    public List<DesignTaskDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public DesignTaskDefine queryTaskDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignTaskDefine)defines.get(0);
        }
        return null;
    }

    public List<DesignTaskDefine> queryTaskDefinesByType(TaskType type) throws Exception {
        return this.list(new String[]{ATTR_TYPE}, new Object[]{type}, this.implClass);
    }

    public DesignTaskDefine queryTaskDefinesByfilePrefix(String filePrefix) throws Exception {
        List defines = this.list(new String[]{ATTR_FilePrefix}, new Object[]{filePrefix}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignTaskDefine)defines.get(0);
        }
        return null;
    }

    public DesignTaskDefine queryTaskDefineByTaskTitle(String taskTitle) throws Exception {
        List defines = this.list(new String[]{ATTR_Title}, new Object[]{taskTitle}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignTaskDefine)defines.get(0);
        }
        return null;
    }

    public DesignTaskDefine getDefineByKey(String id) throws Exception {
        return (DesignTaskDefine)this.getByKey(id, this.implClass);
    }

    public List<DesignTaskDefine> getAllTasksInGroup(String taskGroupKey) throws Exception {
        if (taskGroupKey == null) {
            return this.getRooTasks();
        }
        DesignTaskGroupLink link = new DesignTaskGroupLink();
        link.setGroupKey(taskGroupKey);
        List designTaskDefines = this.listByForeign(link, new String[]{"groupKey"}, DesignTaskDefineImpl.class);
        return designTaskDefines;
    }

    public List<DesignTaskDefine> checkTaskNameAvailable(String taskKey, String taskName) throws Exception {
        if (StringUtils.isNotEmpty((String)taskKey) && StringUtils.isNotEmpty((String)taskName)) {
            String sql = "TK_TITLE=? and TK_KEY<>?";
            Object[] values = new String[]{taskName, taskKey};
            return super.list(sql, values, this.implClass);
        }
        throw new Exception("taskKey\u6216taskName\u4e3a\u7a7a");
    }

    public List<DesignTaskDefine> getByDataSchemeKey(String dataSchemeKey) {
        return super.list(new String[]{ATTR_DATASCHEME}, new Object[]{dataSchemeKey}, this.implClass);
    }

    public List<DesignTaskDefine> getRooTasks() {
        return super.list(" TK_KEY NOT IN (SELECT TL_TASK_KEY FROM NR_PARAM_TASKGROUPLINK_DES) ", null, this.implClass);
    }

    public List<DesignTaskDefine> getRelFtTasks(String filterTemplateID) {
        Assert.notNull((Object)filterTemplateID, (String)"filterTemplateID must not be null");
        return super.list(new String[]{ATTR_FILTER_TEMPLATE}, new Object[]{filterTemplateID}, this.implClass);
    }

    public List<DesignTaskDefine> listHasFilterExpressionTasks() {
        return super.list("TK_FILTER_EXPRESSION IS NOT NULL AND TK_FILTER_EXPRESSION != '' ", new Object[0], this.implClass);
    }

    public void updateDwExpression(DesignTaskDefine taskDefine) throws DBParaException {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", TABLE_NAME, FIELD_EXPRESSION, FIELD_UPDATE_TIME, FIELD_KEY);
        Object[] arg = new Object[]{taskDefine.getFilterExpression(), taskDefine.getUpdateTime(), taskDefine.getKey()};
        this.jdbcTemplate.update(sql, arg);
    }
}

