/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 */
package com.jiuqi.va.datamodel.service;

import com.jiuqi.va.datamodel.domain.DataModelMaintainDO;
import com.jiuqi.va.datamodel.domain.DataModelPublishDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import java.util.List;

public interface VaDataModelMaintainService {
    public DataModelMaintainDO get(DataModelDTO var1);

    public int count(DataModelDTO var1);

    public List<DataModelMaintainDO> list(DataModelDTO var1);

    public R add(DataModelDTO var1);

    public R update(DataModelDTO var1);

    public int modify(DataModelMaintainDO var1);

    public int remove(DataModelMaintainDO var1);

    public List<String> invalidRelateList(List<DataModelColumn> var1);

    public List<DataModelPublishDTO> listMaintain(DataModelDTO var1);

    public R publishModel(List<DataModelDTO> var1);

    public R publish(List<DataModelPublishDTO> var1);

    public R merge(DataModelDTO var1);
}

