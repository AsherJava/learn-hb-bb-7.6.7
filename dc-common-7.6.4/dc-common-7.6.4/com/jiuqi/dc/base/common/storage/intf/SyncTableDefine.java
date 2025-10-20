/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelIndex
 */
package com.jiuqi.dc.base.common.storage.intf;

import com.jiuqi.va.domain.datamodel.DataModelIndex;
import java.util.List;

public interface SyncTableDefine {
    public String getTableName();

    public List<DataModelIndex> getIdxModels();
}

