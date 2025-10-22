/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSDataProviderFactory
 *  com.jiuqi.bi.dataset.model.DSModelFactory
 *  com.jiuqi.bi.dataset.model.DSModelFactoryManager
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.snapshot.dataset.init;

import com.jiuqi.bi.dataset.model.DSDataProviderFactory;
import com.jiuqi.bi.dataset.model.DSModelFactory;
import com.jiuqi.bi.dataset.model.DSModelFactoryManager;
import com.jiuqi.nr.snapshot.dataset.SnapshotDSDataProviderFactory;
import com.jiuqi.nr.snapshot.dataset.SnapshotDSModelFactory;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;

public class SnapshotDatasetInit
implements ModuleInitiator {
    public void init(ServletContext context) throws Exception {
        DSModelFactoryManager.getInstance().registerFactory((DSModelFactory)new SnapshotDSModelFactory());
        DSModelFactoryManager.getInstance().registerDataProviderFactory((DSDataProviderFactory)new SnapshotDSDataProviderFactory());
    }

    public void initWhenStarted(ServletContext context) throws Exception {
    }
}

