/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.snapshot;

import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.snapshot.DataVersion;
import java.util.List;

public interface SnapshotService {
    public void createVersion(DataVersion var1) throws Exception;

    public void overwriteDefaultVersion(DataVersion var1) throws Exception;

    public void overwriteDefaultVersionOfFormList(List<String> var1, DataVersion var2) throws Exception;

    public void modifyVersion(DataVersion var1) throws Exception;

    public void deleteVersion(String var1, String var2) throws Exception;

    public void batchDeleteVersion(String var1, String ... var2) throws Exception;

    public List<DataVersion> queryVersion(DimensionCombination var1, String var2) throws Exception;

    public void overwriteDefaultVersionOfFormList(List<String> var1, DataVersion var2, IMonitor var3) throws Exception;
}

