/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dao.impl;

import com.jiuqi.nr.zb.scheme.internal.dao.IPropLinkDao;
import com.jiuqi.nr.zb.scheme.internal.dao.impl.ZbSchemeBaseDao;
import com.jiuqi.nr.zb.scheme.internal.entity.PropLinkDO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ZbSchemePropDAO
extends ZbSchemeBaseDao<PropLinkDO>
implements IPropLinkDao {
    @Override
    public Class<PropLinkDO> getClz() {
        return PropLinkDO.class;
    }

    @Override
    public List<PropLinkDO> listByScheme(String schemeKey) {
        return this.list(new String[]{"schemeKey"}, (Object[])new String[]{schemeKey}, this.getClz());
    }

    @Override
    public void deleteByScheme(String schemeKey) throws Exception {
        super.deleteBy(new String[]{"schemeKey"}, new String[]{schemeKey});
    }
}

