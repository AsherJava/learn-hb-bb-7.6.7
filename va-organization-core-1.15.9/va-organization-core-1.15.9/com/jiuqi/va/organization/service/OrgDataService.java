/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgBatchOptDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 */
package com.jiuqi.va.organization.service;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgBatchOptDTO;
import com.jiuqi.va.domain.org.OrgCategoryDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.organization.common.OrgConstants;
import com.jiuqi.va.organization.domain.OrgDataRefAddDTO;
import java.util.List;

public interface OrgDataService {
    public OrgDO get(OrgDTO var1);

    public int count(OrgDTO var1);

    public PageVO<OrgDO> list(OrgDTO var1);

    public R add(OrgDTO var1);

    @Deprecated
    public R relAdd(OrgDTO var1);

    public R refAdd(OrgDataRefAddDTO var1);

    public R update(OrgDTO var1);

    public R changeState(OrgDTO var1);

    public R remove(OrgDTO var1);

    public R recovery(OrgDTO var1);

    public R upOrDown(OrgDTO var1, OrgConstants.UpOrDown var2);

    public R move(OrgDTO var1);

    public R checkIsLeaf(OrgDTO var1);

    public R checkUnique(OrgDTO var1);

    public R batchUpdate(List<OrgDTO> var1, OrgDTO var2);

    public R resetParents(OrgDTO var1);

    public R batchRemove(OrgBatchOptDTO var1);

    public R sync(OrgBatchOptDTO var1);

    public R fastUpDown(OrgDTO var1);

    public List<OrgDO> verDiffList(OrgDTO var1);

    public R initCache(OrgCategoryDTO var1);

    public R syncCache(OrgCategoryDTO var1);

    public R cleanCache(OrgCategoryDTO var1);
}

