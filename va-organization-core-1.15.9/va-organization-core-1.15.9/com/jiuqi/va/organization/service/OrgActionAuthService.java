/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.organization.service;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.organization.domain.OrgActionAuthDO;
import com.jiuqi.va.organization.domain.OrgActionAuthDTO;
import com.jiuqi.va.organization.domain.OrgActionAuthVO;
import java.util.List;
import java.util.Set;

public interface OrgActionAuthService {
    public PageVO<OrgActionAuthVO> listDetail(OrgActionAuthDTO var1);

    public R updateDetail(List<OrgActionAuthDO> var1);

    public Set<String> getUserAuth(OrgActionAuthDTO var1);
}

