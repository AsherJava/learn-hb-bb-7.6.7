/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSDataProviderFactory
 *  com.jiuqi.bi.dataset.model.DSModelFactory
 *  com.jiuqi.bi.dataset.model.DSModelFactoryManager
 */
package com.jiuqi.nr.query.dataset.init;

import com.jiuqi.bi.dataset.model.DSDataProviderFactory;
import com.jiuqi.bi.dataset.model.DSModelFactory;
import com.jiuqi.bi.dataset.model.DSModelFactoryManager;
import com.jiuqi.nr.query.dataset.impl.NrQueryDSDataProviderFactory;
import com.jiuqi.nr.query.dataset.impl.NrQueryDSModelFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrQueryDataSetRegister
implements InitializingBean {
    @Autowired
    private NrQueryDSDataProviderFactory DSDataProviderFactory;
    @Autowired
    private NrQueryDSModelFactory DSModelFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        DSModelFactoryManager.getInstance().registerFactory((DSModelFactory)this.DSModelFactory);
        DSModelFactoryManager.getInstance().registerDataProviderFactory((DSDataProviderFactory)this.DSDataProviderFactory);
    }
}

