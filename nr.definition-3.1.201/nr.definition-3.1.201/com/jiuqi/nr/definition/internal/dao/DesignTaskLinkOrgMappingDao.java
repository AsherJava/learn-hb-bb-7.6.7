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
import com.jiuqi.nr.definition.facade.DesignTaskLinkOrgMappingDefine;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.DesignTaskLinkOrgMappingDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignTaskLinkOrgMappingDao
extends BaseDao {
    private Class<DesignTaskLinkOrgMappingDefineImpl> orgMappingDefineClass = DesignTaskLinkOrgMappingDefineImpl.class;
    private static String ATTR_TASK_LINK_KEY = "taskLinkKey";

    public Class<?> getClz() {
        return this.orgMappingDefineClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public void insertOrgMappingRule(DesignTaskLinkOrgMappingDefine[] defines) throws DBParaException {
        super.insert((Object[])defines);
    }

    public void deleteOrgMappingRule(DesignTaskLinkOrgMappingDefine[] defines) throws DBParaException {
        super.delete((Object[])defines);
    }

    public void deleteOrgMappingRuleByKey(String taskLinkKey) throws DBParaException {
        this.deleteBy(new String[]{ATTR_TASK_LINK_KEY}, new Object[]{taskLinkKey});
    }

    public List<DesignTaskLinkOrgMappingDefine> getByTaskLinkKey(String taskLinkKey) {
        return this.list(new String[]{ATTR_TASK_LINK_KEY}, new Object[]{taskLinkKey}, this.orgMappingDefineClass);
    }
}

