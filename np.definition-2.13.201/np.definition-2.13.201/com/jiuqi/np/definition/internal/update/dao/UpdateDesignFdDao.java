/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.update.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.np.definition.internal.update.UpdateDesignFd;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class UpdateDesignFdDao
extends BaseDao {
    @Override
    public Class<?> getClz() {
        return UpdateDesignFd.class;
    }

    public List<UpdateDesignFd> list() throws Exception {
        return this.list(UpdateDesignFd.class);
    }
}

