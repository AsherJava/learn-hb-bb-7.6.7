/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IMonitor
 */
package com.jiuqi.nr.data.engine.version;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.nr.data.engine.version.DataVersion;
import java.util.List;

public interface DataVersionManager {
    public void createVersion(String var1, DataVersion var2, IMonitor var3) throws Exception;

    public void overwriteDefaultVersion(String var1, DataVersion var2, IMonitor var3) throws Exception;

    public void overwriteDefaultVersionOfFormList(List<String> var1, String var2, DataVersion var3, IMonitor var4) throws Exception;

    public void modifyVersion(String var1, DataVersion var2) throws Exception;

    public void deleteVersion(String var1, String var2) throws Exception;

    public void batchDeleteVersion(String var1, String ... var2) throws Exception;

    public List<DataVersion> queryVersion(DimensionValueSet var1, String var2) throws Exception;
}

