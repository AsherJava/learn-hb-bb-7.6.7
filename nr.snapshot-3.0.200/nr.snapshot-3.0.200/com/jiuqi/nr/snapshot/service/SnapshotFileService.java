/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.snapshot.service;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

public interface SnapshotFileService {
    public String createSnapshotFile(String var1, DimensionCollection var2, List<String> var3);
}

