/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.datamapping.impl.gather;

import com.jiuqi.dc.datamapping.impl.intf.IDataRefChecker;
import java.util.List;

public interface IDataRefCheckerGather {
    public List<IDataRefChecker> getCheckerList();

    public IDataRefChecker getByTableName(String var1);
}

