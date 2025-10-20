/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.TreeVO
 */
package com.jiuqi.va.basedata.service;

import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.TreeVO;

public interface BaseDataGroupService {
    public R exist(BaseDataGroupDTO var1);

    public BaseDataGroupDO get(BaseDataGroupDTO var1);

    public PageVO<BaseDataGroupDO> list(BaseDataGroupDTO var1);

    public PageVO<TreeVO<BaseDataGroupDO>> tree(BaseDataGroupDTO var1);

    public R add(BaseDataGroupDTO var1);

    public R update(BaseDataGroupDTO var1);

    public R delete(BaseDataGroupDTO var1);
}

