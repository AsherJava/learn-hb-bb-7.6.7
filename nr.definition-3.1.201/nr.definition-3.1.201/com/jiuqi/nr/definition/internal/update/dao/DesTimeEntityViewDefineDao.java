/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.update.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.internal.update.dao.EntityViewDefineDesUp;
import com.jiuqi.nr.definition.internal.update.dao.EntityViewDefineUp;
import org.springframework.stereotype.Repository;

@Repository
public class DesTimeEntityViewDefineDao
extends BaseDao {
    public Class<?> getClz() {
        return EntityViewDefineDesUp.class;
    }

    public EntityViewDefineUp getDefineByKey(String id) throws Exception {
        return (EntityViewDefineUp)this.getByKey(id, EntityViewDefineDesUp.class);
    }
}

