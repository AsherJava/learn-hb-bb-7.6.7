/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 */
package com.jiuqi.nr.bql.intf;

import com.jiuqi.nr.bql.intf.CalibreDimTable;
import com.jiuqi.nr.datascheme.api.DataScheme;
import java.util.Date;
import java.util.List;

public interface ICalibreDimTableProvider {
    public List<CalibreDimTable> getCalibreDimTableList(DataScheme var1);

    public CalibreDimTable findByGatherScheme(String var1);

    public Date getLastUpdateTime(DataScheme var1);
}

