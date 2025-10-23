/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dao.impl;

import com.jiuqi.nr.zb.scheme.internal.dao.IZbSchemeDao;
import com.jiuqi.nr.zb.scheme.internal.dao.impl.ZbSchemeBaseDao;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbSchemeDO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ZbSchemeDAO
extends ZbSchemeBaseDao<ZbSchemeDO>
implements IZbSchemeDao {
    @Override
    public Class<ZbSchemeDO> getClz() {
        return ZbSchemeDO.class;
    }

    @Override
    public List<ZbSchemeDO> listByParent(String parent) {
        return super.list(new String[]{"parentKey"}, (Object[])new String[]{parent}, this.getClz());
    }

    @Override
    public void deleteByKey(String key) {
        super.delete(key);
    }
}

