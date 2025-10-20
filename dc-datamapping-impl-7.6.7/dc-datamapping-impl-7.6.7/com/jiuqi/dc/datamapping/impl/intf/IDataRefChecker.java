/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.utils.Pair
 *  com.jiuqi.dc.datamapping.client.dto.RefChangeDTO
 */
package com.jiuqi.dc.datamapping.impl.intf;

import com.jiuqi.dc.base.common.utils.Pair;
import com.jiuqi.dc.datamapping.client.dto.RefChangeDTO;

public interface IDataRefChecker {
    public String getTableName();

    public Pair<String, Integer> checkHasRef(RefChangeDTO var1);
}

