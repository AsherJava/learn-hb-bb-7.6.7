/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.manager.DataSourceFactoryManager
 *  com.jiuqi.bi.parameter.manager.DataSourceModelFactory
 *  com.jiuqi.nvwa.bap.parameter.extend.compatible.INvwaDataSourceModelProvider
 *  com.jiuqi.nvwa.bap.parameter.extend.compatible.NvWaDataSourceModelFactory
 *  com.jiuqi.nvwa.bap.parameter.extend.compatible.basedata.NvwaBDDataSourceModelProvider
 */
package com.jiuqi.nvwa.bap.parameter.extend.config;

import com.jiuqi.bi.parameter.manager.DataSourceFactoryManager;
import com.jiuqi.bi.parameter.manager.DataSourceModelFactory;
import com.jiuqi.nvwa.bap.parameter.extend.compatible.INvwaDataSourceModelProvider;
import com.jiuqi.nvwa.bap.parameter.extend.compatible.NvWaDataSourceModelFactory;
import com.jiuqi.nvwa.bap.parameter.extend.compatible.basedata.NvwaBDDataSourceModelProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"com.jiuqi.nvwa.bap.parameter"})
@Configuration
public class ParameterExtendConfig {
    static {
        NvwaBDDataSourceModelProvider baseDataProvider = new NvwaBDDataSourceModelProvider();
        DataSourceFactoryManager.registDataSourceModelFactory((String)"com.jiuqi.nvwa.parameter.ds.basedata", (DataSourceModelFactory)new NvWaDataSourceModelFactory((INvwaDataSourceModelProvider)baseDataProvider));
        NvwaBDDataSourceModelProvider orgProvider = new NvwaBDDataSourceModelProvider();
        DataSourceFactoryManager.registDataSourceModelFactory((String)"com.jiuqi.nvwa.parameter.ds.organization", (DataSourceModelFactory)new NvWaDataSourceModelFactory((INvwaDataSourceModelProvider)orgProvider));
    }
}

