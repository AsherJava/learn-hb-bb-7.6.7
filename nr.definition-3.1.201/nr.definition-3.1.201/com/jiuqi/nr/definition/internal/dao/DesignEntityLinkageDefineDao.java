/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.DesignEntityLinkageDefine;
import com.jiuqi.nr.definition.internal.impl.DesignEntityLinkageDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DesignEntityLinkageDefineDao
extends BaseDao {
    private static String ATTR_TASKKEY = "taskKey";
    private Class<DesignEntityLinkageDefineImpl> implClass = DesignEntityLinkageDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public List<DesignEntityLinkageDefine> queryDesignEntityLinkageDefine(String taskKey) throws Exception {
        return this.list(new String[]{ATTR_TASKKEY}, new Object[]{taskKey}, this.implClass);
    }
}

