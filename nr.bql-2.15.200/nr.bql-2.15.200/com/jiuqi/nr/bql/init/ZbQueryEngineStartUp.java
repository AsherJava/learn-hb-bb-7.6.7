/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.ext.intf.IProviderFactory
 *  com.jiuqi.bi.adhoc.ext.intf.ProviderFactoryManager
 *  com.jiuqi.bi.util.time.IFormatProvider
 *  com.jiuqi.bi.util.time.TimeFormatManager
 *  com.jiuqi.nvwa.bql.datasource.INvwaDataSource
 *  com.jiuqi.nvwa.bql.datasource.NvwaDataSourceManager
 */
package com.jiuqi.nr.bql.init;

import com.jiuqi.bi.adhoc.ext.intf.IProviderFactory;
import com.jiuqi.bi.adhoc.ext.intf.ProviderFactoryManager;
import com.jiuqi.bi.util.time.IFormatProvider;
import com.jiuqi.bi.util.time.TimeFormatManager;
import com.jiuqi.nr.bql.datasource.DataSchemeDataSource;
import com.jiuqi.nr.bql.datasource.format.NrPeriodFormatProvider;
import com.jiuqi.nr.bql.dsv.DataSchemeDSVProviderFactory;
import com.jiuqi.nvwa.bql.datasource.INvwaDataSource;
import com.jiuqi.nvwa.bql.datasource.NvwaDataSourceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class ZbQueryEngineStartUp {
    @Autowired
    private DataSchemeDSVProviderFactory providerFactory;
    @Autowired
    private DataSchemeDataSource ds;
    @Autowired
    private NrPeriodFormatProvider nrPeriodFormatProvider;

    public void init() throws Exception {
        ProviderFactoryManager.getInstance().register((IProviderFactory)this.providerFactory);
        NvwaDataSourceManager.getInstance().registDataSource((INvwaDataSource)this.ds);
        TimeFormatManager.registerProvider((IFormatProvider)this.nrPeriodFormatProvider);
    }

    public void initWhenStarted() throws Exception {
    }
}

