/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dao.impl;

import com.jiuqi.nr.zb.scheme.internal.dao.IZbSchemeGroupDao;
import com.jiuqi.nr.zb.scheme.internal.dao.impl.ZbSchemeBaseDao;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbSchemeGroupDO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ZbSchemeGroupDAO
extends ZbSchemeBaseDao<ZbSchemeGroupDO>
implements IZbSchemeGroupDao {
    @Override
    public Class<ZbSchemeGroupDO> getClz() {
        return ZbSchemeGroupDO.class;
    }

    @Override
    public List<ZbSchemeGroupDO> listByParent(String parent) {
        return super.list(new String[]{"parentKey"}, (Object[])new String[]{parent}, this.getClz());
    }

    @Override
    public void insert(ZbSchemeGroupDO data) throws Exception {
        super.insert(data);
    }
}

