/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 */
package com.jiuqi.nr.entity.adapter.impl.org.data;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;

public interface OrgDataSource {
    public boolean enable();

    public double order();

    public PageVO<OrgDO> list(OrgDTO var1);

    public OrgDO get(OrgDTO var1);

    public PageVO<OrgDO> listSubordinate(OrgDTO var1);

    public int count(OrgDTO var1);
}

