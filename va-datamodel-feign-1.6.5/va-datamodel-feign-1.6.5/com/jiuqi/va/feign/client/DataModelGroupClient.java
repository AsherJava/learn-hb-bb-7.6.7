/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.feign.client;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelGroupExternalDO;
import com.jiuqi.va.domain.datamodel.DataModelGroupExternalDTO;
import java.util.List;

public interface DataModelGroupClient {
    public DataModelGroupExternalDO get(DataModelGroupExternalDTO var1);

    public List<DataModelGroupExternalDO> list(DataModelGroupExternalDTO var1);

    public R add(DataModelGroupExternalDTO var1);
}

