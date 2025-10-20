/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.TreeVO
 *  com.jiuqi.va.domain.datamodel.DataModelGroupExternalDO
 *  com.jiuqi.va.domain.datamodel.DataModelGroupExternalDTO
 */
package com.jiuqi.va.datamodel.service;

import com.jiuqi.va.datamodel.domain.DataModelGroupDO;
import com.jiuqi.va.datamodel.domain.DataModelGroupDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.TreeVO;
import com.jiuqi.va.domain.datamodel.DataModelGroupExternalDO;
import com.jiuqi.va.domain.datamodel.DataModelGroupExternalDTO;
import java.util.List;

public interface VaDataModelGroupService {
    public int add(DataModelGroupDO var1);

    public int delete(DataModelGroupDO var1);

    public int update(DataModelGroupDO var1);

    public List<DataModelGroupDO> list(DataModelGroupDTO var1);

    public boolean exist(DataModelGroupDO var1);

    public PageVO<TreeVO<DataModelGroupDO>> tree(DataModelGroupDTO var1);

    public R externalAdd(DataModelGroupExternalDTO var1);

    public DataModelGroupExternalDO externalGet(DataModelGroupExternalDTO var1);

    public List<DataModelGroupExternalDO> externalList(DataModelGroupExternalDTO var1);
}

