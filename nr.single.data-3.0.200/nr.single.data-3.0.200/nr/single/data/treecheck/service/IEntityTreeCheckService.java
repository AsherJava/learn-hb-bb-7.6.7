/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package nr.single.data.treecheck.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import java.util.List;
import nr.single.data.bean.CheckResultNode;

public interface IEntityTreeCheckService {
    public List<CheckResultNode> CheckTreeNodeByTask(String var1, String var2, AsyncTaskMonitor var3) throws Exception;

    public List<CheckResultNode> CheckTreeNodeByTask(String var1, String var2, boolean var3, AsyncTaskMonitor var4) throws Exception;

    public List<CheckResultNode> CheckTreeNodeByTask(String var1, String var2, String var3, boolean var4, AsyncTaskMonitor var5) throws Exception;
}

