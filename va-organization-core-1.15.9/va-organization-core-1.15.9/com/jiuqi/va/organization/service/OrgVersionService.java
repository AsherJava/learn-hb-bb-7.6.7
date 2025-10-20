/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 */
package com.jiuqi.va.organization.service;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import java.util.List;

public interface OrgVersionService {
    public OrgVersionDO get(OrgVersionDTO var1);

    public PageVO<OrgVersionDO> list(OrgVersionDTO var1);

    public R add(OrgVersionDO var1);

    public R split(OrgVersionDO var1);

    public R update(OrgVersionDO var1);

    public R remove(OrgVersionDO var1);

    public R changeStatus(List<OrgVersionDO> var1);

    public R syncCache(OrgVersionDO var1);

    public List<OrgVersionDO> listCache(OrgVersionDTO var1);
}

