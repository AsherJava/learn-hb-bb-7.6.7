/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.internal.impl.AuditTypeImpl;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AuditTypeDefineDao
extends BaseDao {
    private Class<AuditTypeImpl> implClass = AuditTypeImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public List<AuditType> list() throws Exception {
        return this.list(this.implClass);
    }

    public void updateAuditType(AuditType data) throws Exception {
        this.update(data);
    }
}

