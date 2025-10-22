/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormSchemeDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeFormSchemeDefineDao
extends BaseDao {
    private static String ATTR_GROUP = "groupName";
    private static String ATTR_TaskKey = "taskKey";
    private static String ATTR_CODE = "formSchemeCode";
    private static String ATTR_FilePrefix = "filePrefix";
    private Class<RunTimeFormSchemeDefineImpl> implClass = RunTimeFormSchemeDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<FormSchemeDefine> listByGroup(String id) throws Exception {
        return this.list(new String[]{ATTR_GROUP}, new Object[]{id}, this.implClass);
    }

    public List<FormSchemeDefine> listByTask(String id) {
        return this.list(new String[]{ATTR_TaskKey}, new Object[]{id}, this.implClass);
    }

    public List<FormSchemeDefine> list() {
        return this.list(this.implClass);
    }

    public void deleteByTask(String taskKey) throws Exception {
        this.deleteBy(new String[]{ATTR_TaskKey}, new Object[]{taskKey});
    }

    public FormSchemeDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (FormSchemeDefine)defines.get(0);
        }
        return null;
    }

    public FormSchemeDefine queryDefinesByfilePrefix(String filePrefix) throws Exception {
        List defines = this.list(new String[]{ATTR_FilePrefix}, new Object[]{filePrefix}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (FormSchemeDefine)defines.get(0);
        }
        return null;
    }

    public FormSchemeDefine getDefineByKey(String id) {
        return (FormSchemeDefine)this.getByKey(id, this.implClass);
    }

    public List<FormSchemeDefine> listGhostSchemes() {
        String sqlWhere = " not exists (select 1 from NR_PARAM_TASK tk where FC_TASK_KEY = tk.TK_KEY)";
        return this.list(sqlWhere, null, this.implClass);
    }
}

