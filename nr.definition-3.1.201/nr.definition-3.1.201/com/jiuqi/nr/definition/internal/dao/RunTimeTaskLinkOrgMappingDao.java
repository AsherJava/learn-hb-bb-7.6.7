/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.TaskLinkOrgMappingDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeTaskLinkOrgMappingDao
extends BaseDao {
    private Class<TaskLinkOrgMappingDefineImpl> orgMappingDefineClass = TaskLinkOrgMappingDefineImpl.class;
    private static String ATTR_TASK_LINK_KEY = "taskLinkKey";

    public Class<?> getClz() {
        return this.orgMappingDefineClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<TaskLinkOrgMappingDefine> getByTaskLinkKey(String taskLinkKey) {
        return this.list(new String[]{ATTR_TASK_LINK_KEY}, new Object[]{taskLinkKey}, this.orgMappingDefineClass);
    }
}

