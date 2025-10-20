/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class DesignTaskGroupDefineDao
extends BaseDao {
    private static String ATTR_CODE = "taskCode";
    private Class<DesignTaskGroupDefineImpl> implClass = DesignTaskGroupDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<DesignTaskGroupDefine> listByGroup(String id) throws Exception {
        if (StringUtils.hasLength(id)) {
            return this.list(new String[]{"parentKey"}, new Object[]{id}, this.implClass);
        }
        return this.list("fl_parent_key is null or fl_parent_key = ''", null, this.implClass);
    }

    public List<DesignTaskGroupDefine> list() throws Exception {
        return this.list(this.implClass);
    }

    public DesignTaskGroupDefine queryDefinesByCode(String code) throws Exception {
        List defines = this.list(new String[]{ATTR_CODE}, new Object[]{code}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (DesignTaskGroupDefine)defines.get(0);
        }
        return null;
    }

    public DesignTaskGroupDefine getDefineByKey(String id) throws Exception {
        return (DesignTaskGroupDefine)this.getByKey(id, this.implClass);
    }

    public List<DesignTaskGroupDefine> getAllGroupsFromTask(String taskKey) throws Exception {
        DesignTaskGroupLink link = new DesignTaskGroupLink();
        link.setTaskKey(taskKey);
        return this.listByForeign(link, new String[]{"taskKey"}, DesignTaskGroupDefineImpl.class);
    }
}

