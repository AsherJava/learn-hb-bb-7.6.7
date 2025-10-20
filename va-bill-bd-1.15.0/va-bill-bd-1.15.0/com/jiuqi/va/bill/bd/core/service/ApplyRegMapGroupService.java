/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.TreeVO
 */
package com.jiuqi.va.bill.bd.core.service;

import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapConfigItemDTO;
import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapGroupDO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.TreeVO;

public interface ApplyRegMapGroupService {
    public R delete(ApplyRegMapGroupDO var1);

    public PageVO<TreeVO<ApplyRegMapConfigItemDTO>> tree(ApplyRegMapGroupDO var1);

    public R update(ApplyRegMapGroupDO var1);

    public R add(ApplyRegMapGroupDO var1);
}

