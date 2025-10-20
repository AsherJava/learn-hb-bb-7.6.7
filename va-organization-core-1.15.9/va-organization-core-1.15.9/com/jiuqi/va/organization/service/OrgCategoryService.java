/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 */
package com.jiuqi.va.organization.service;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.organization.domain.ZBDTO;
import java.util.List;

public interface OrgCategoryService {
    public OrgCategoryDO get(OrgCategoryDO var1);

    public PageVO<OrgCategoryDO> list(OrgCategoryDO var1);

    public R add(OrgCategoryDO var1);

    public R update(OrgCategoryDO var1);

    public R remove(OrgCategoryDO var1);

    public R moveCategory(List<OrgCategoryDO> var1);

    public R syncCache(OrgCategoryDO var1);

    public List<ZBDTO> listZB(OrgCategoryDO var1);

    public R addZB(ZBDTO var1);

    public R updateZB(ZBDTO var1);

    public R removeZB(List<ZBDTO> var1);

    public R moveZB(List<ZBDTO> var1);
}

