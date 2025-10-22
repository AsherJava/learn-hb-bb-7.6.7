/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.definition.internal.impl.RunTimeTaskDefineImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeTaskDefineDao
extends BaseDao {
    private static String ATTR_GROUP = "ownerGroupID";
    private static String ATTR_CODE = "taskCode";
    private static String ATTR_FilePrefix = "filePrefix";
    private static String ATTR_DATASCHEME = "dataScheme";
    private static String ATTR_FILTER_TEMPLATE = "filterTemplate";
    private Class<RunTimeTaskDefineImpl> implClass = RunTimeTaskDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public TaskDefine getDefineByKey(String id) {
        return (TaskDefine)this.getByKey(id, this.implClass);
    }

    public List<TaskDefine> listByGroup(String id) throws Exception {
        return this.list(new String[]{ATTR_GROUP}, new Object[]{id}, this.implClass);
    }

    public List<TaskDefine> listByCode(String code) throws Exception {
        return this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
    }

    public List<TaskDefine> list() {
        return this.list(this.implClass);
    }

    public TaskDefine queryTableDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (TaskDefine)defines.get(0);
        }
        return null;
    }

    public TaskDefine queryTaskDefinesByFilePrefix(String filePrefix) throws Exception {
        List defines = this.list(new String[]{ATTR_FilePrefix}, new Object[]{filePrefix}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (TaskDefine)defines.get(0);
        }
        return null;
    }

    public List<TaskDefine> getAllTasksInGroup(String taskGroupKey) {
        DesignTaskGroupLink link = new DesignTaskGroupLink();
        link.setGroupKey(taskGroupKey);
        return this.listByForeign(link, new String[]{"groupKey"}, this.implClass);
    }

    public List<TaskDefine> getRooTasks() {
        return super.list(" TK_KEY NOT IN (SELECT TL_TASK_KEY FROM NR_PARAM_TASKGROUPLINK_DES) ", null, this.implClass);
    }

    public List<TaskDefine> getByDataSchemeKey(String dataSchemeKey) {
        return super.list(new String[]{ATTR_DATASCHEME}, new Object[]{dataSchemeKey}, this.implClass);
    }

    public List<RunTimeTaskDefineImpl> listHasFilterExpressionTasks() {
        return super.list("TK_FILTER_EXPRESSION IS NOT NULL AND TK_FILTER_EXPRESSION != '' ", new Object[0], this.implClass);
    }

    public Map<String, Date> getAllTaskDeployTime() {
        String sql = "SELECT T.TK_KEY, MAX(P.TPP_PUBLISHDATE) FROM NR_PARAM_TASK T LEFT JOIN NR_TASK_PLANPUBLISH P ON T.TK_KEY = P.TPP_TASKKEY GROUP BY T.TK_KEY ";
        return (Map)this.jdbcTemplate.query(sql, (ResultSetExtractor)new ResultSetExtractor<Map<String, Date>>(){

            public Map<String, Date> extractData(ResultSet rs) throws SQLException, DataAccessException {
                HashMap<String, Date> deployTime = new HashMap<String, Date>();
                while (rs.next()) {
                    String taskKey = rs.getString(1);
                    Timestamp time = rs.getTimestamp(2);
                    deployTime.put(taskKey, null == time ? null : new Date(time.getTime()));
                }
                return deployTime;
            }
        });
    }
}

