/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.update.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.np.definition.internal.update.UpdateFd;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class UpdateFdDao
extends BaseDao {
    @Override
    public Class<?> getClz() {
        return UpdateFd.class;
    }

    public List<UpdateFd> list() throws Exception {
        return this.list(UpdateFd.class);
    }
}

