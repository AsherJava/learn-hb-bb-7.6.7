/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormSchemeDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignFormSchemeDefineDao
extends BaseDao {
    private static String ATTR_GROUP = "groupName";
    private static String ATTR_TASKKEY = "taskKey";
    private static String ATTR_CODE = "formSchemeCode";
    private static String ATTR_FILEPREFIX = "filePrefix";
    private static String ATTR_TASKPREFIX = "taskPrefix";
    private Class<DesignFormSchemeDefineImpl> implClass = DesignFormSchemeDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DesignFormSchemeDefine> listByGroup(String id) throws Exception {
        return this.list(new String[]{ATTR_GROUP}, new Object[]{id}, this.implClass);
    }

    public List<DesignFormSchemeDefine> listByTask(String taskKey) {
        return this.list(new String[]{ATTR_TASKKEY}, new Object[]{taskKey}, this.implClass);
    }

    public List<DesignFormSchemeDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public void deleteByTask(String taskKey) throws Exception {
        this.deleteBy(new String[]{ATTR_TASKKEY}, new Object[]{taskKey});
    }

    public DesignFormSchemeDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignFormSchemeDefine)defines.get(0);
        }
        return null;
    }

    public DesignFormSchemeDefine queryDefinesByfilePrefix(String filePrefix) throws Exception {
        List defines = this.list(new String[]{ATTR_FILEPREFIX}, new Object[]{filePrefix}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignFormSchemeDefine)defines.get(0);
        }
        return null;
    }

    public DesignFormSchemeDefine queryDefinesByTaskPrefix(String taskPrefix) throws Exception {
        List defines = this.list(new String[]{ATTR_TASKPREFIX}, new Object[]{taskPrefix}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignFormSchemeDefine)defines.get(0);
        }
        return null;
    }

    public DesignFormSchemeDefine getDefineByKey(String id) {
        return (DesignFormSchemeDefine)this.getByKey(id, this.implClass);
    }

    public List<DesignFormSchemeDefine> listGhostSchemes() {
        String sqlWhere = " not exists (select 1 from NR_PARAM_TASK_DES tk where FC_TASK_KEY = tk.TK_KEY)";
        return this.list(sqlWhere, null, this.implClass);
    }
}

