/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeTaskLinkDefineImpl;
import com.jiuqi.nr.definition.internal.service.RunTimeTaskLinkOrgMapService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeTaskLinkDefineDao
extends BaseDao {
    private static String ATTR_CurentFormSchemeKey = "currentFormSchemeKey";
    private static String ATTR_RelatedTaskCode = "relatedTaskCode";
    private static String ATTR_linkAlias = "linkAlias";
    private Class<RunTimeTaskLinkDefineImpl> implClass = RunTimeTaskLinkDefineImpl.class;
    @Autowired
    private RunTimeTaskLinkOrgMapService orgMapService;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<TaskLinkDefine> list() {
        return this.list(this.implClass);
    }

    public void deleteByCurrentFormScheme(String currentFormSchemeKey) throws Exception {
        this.deleteBy(new String[]{ATTR_CurentFormSchemeKey}, new Object[]{currentFormSchemeKey});
    }

    public void deleteByCurrentFormSchemeAndNumber(String currentFormSchemeKey, String linkAlia) throws Exception {
        this.deleteBy(new String[]{ATTR_CurentFormSchemeKey, ATTR_linkAlias}, new Object[]{currentFormSchemeKey, linkAlia});
    }

    public TaskLinkDefine getDefineByKey(String id) throws Exception {
        return (TaskLinkDefine)this.getByKey(id, this.implClass);
    }

    public List<TaskLinkDefine> queryDefinesByCurrentTask(String currentTaskKey) {
        RunTimeFormSchemeDefineImpl formSchemeObj = new RunTimeFormSchemeDefineImpl();
        formSchemeObj.setTaskKey(currentTaskKey);
        return super.listByForeign((Object)formSchemeObj, new String[]{"taskKey"}, this.implClass);
    }

    public List<TaskLinkDefine> queryDefinesByCurrentFormScheme(String currentFormSchemeKey) {
        List taskLinkDefines = this.list(new String[]{ATTR_CurentFormSchemeKey}, new Object[]{currentFormSchemeKey}, this.implClass);
        for (TaskLinkDefine taskLinkDefine : taskLinkDefines) {
            RunTimeTaskLinkDefineImpl taskLinkDefineImpl = (RunTimeTaskLinkDefineImpl)taskLinkDefine;
            taskLinkDefineImpl.setOrgMappingRule(this.orgMapService.getByTaskLinkKey(taskLinkDefineImpl.getKey()));
        }
        return taskLinkDefines;
    }

    public TaskLinkDefine queryDefinesByCurrentFormSchemeAndNumber(String currentFormSchemeKey, String linkAlia) throws Exception {
        List defines = this.list(new String[]{ATTR_CurentFormSchemeKey, ATTR_linkAlias}, new Object[]{currentFormSchemeKey, linkAlia}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (TaskLinkDefine)defines.get(0);
        }
        return null;
    }

    public List<TaskLinkDefine> queryLinksByRelatedTaskCode(String relatedTaskCode) throws Exception {
        return this.list(new String[]{ATTR_RelatedTaskCode}, new Object[]{relatedTaskCode}, this.implClass);
    }
}

