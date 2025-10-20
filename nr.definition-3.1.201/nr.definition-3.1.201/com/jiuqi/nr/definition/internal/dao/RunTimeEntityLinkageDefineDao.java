/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.EntityLinkageDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeEntityLinkageDefineImpl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeEntityLinkageDefineDao
extends BaseDao {
    private Class<RunTimeEntityLinkageDefineImpl> implClass = RunTimeEntityLinkageDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public EntityLinkageDefine getDefineByKey(String elKey) {
        return (EntityLinkageDefine)this.getByKey(elKey, this.implClass);
    }

    public List<EntityLinkageDefine> list() {
        return this.list(this.implClass);
    }
}

