/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSDataProviderFactory
 *  com.jiuqi.bi.dataset.model.DSModelFactory
 *  com.jiuqi.bi.dataset.model.DSModelFactoryManager
 *  com.jiuqi.nr.zbquery.dataset.ZBQueryDSDataProviderFactory
 *  com.jiuqi.nr.zbquery.dataset.ZBQueryDSModelFactory
 *  com.jiuqi.nr.zbquery.workbench.myanalysis.dataset.ZBQueryWBDSDataProviderFactory
 *  com.jiuqi.nr.zbquery.workbench.myanalysis.dataset.ZBQueryWBDSModelFactory
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.zbquery.init;

import com.jiuqi.bi.dataset.model.DSDataProviderFactory;
import com.jiuqi.bi.dataset.model.DSModelFactory;
import com.jiuqi.bi.dataset.model.DSModelFactoryManager;
import com.jiuqi.nr.zbquery.dataset.ZBQueryDSDataProviderFactory;
import com.jiuqi.nr.zbquery.dataset.ZBQueryDSModelFactory;
import com.jiuqi.nr.zbquery.workbench.myanalysis.dataset.ZBQueryWBDSDataProviderFactory;
import com.jiuqi.nr.zbquery.workbench.myanalysis.dataset.ZBQueryWBDSModelFactory;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;

public class ZBQueryInit
implements ModuleInitiator {
    public void init(ServletContext context) throws Exception {
        DSModelFactoryManager.getInstance().registerFactory((DSModelFactory)new ZBQueryDSModelFactory());
        DSModelFactoryManager.getInstance().registerDataProviderFactory((DSDataProviderFactory)new ZBQueryDSDataProviderFactory());
        DSModelFactoryManager.getInstance().registerFactory((DSModelFactory)new ZBQueryWBDSModelFactory());
        DSModelFactoryManager.getInstance().registerDataProviderFactory((DSDataProviderFactory)new ZBQueryWBDSDataProviderFactory());
    }

    public void initWhenStarted(ServletContext context) throws Exception {
    }
}

