/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSDataProviderFactory
 *  com.jiuqi.bi.dataset.model.DSModelFactory
 *  com.jiuqi.bi.dataset.model.DSModelFactoryManager
 *  com.jiuqi.bi.parameter.manager.DataSourceFactoryManager
 *  com.jiuqi.bi.parameter.manager.DataSourceModelFactory
 *  com.jiuqi.bi.quickreport.writeback.WritebackFactory
 *  com.jiuqi.bi.quickreport.writeback.WritebackFactoryManager
 *  com.jiuqi.nvwa.bap.parameter.extend.compatible.INvwaDataSourceModelProvider
 *  com.jiuqi.nvwa.bap.parameter.extend.compatible.NvWaDataSourceModelFactory
 *  com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.bi.init;

import com.jiuqi.bi.dataset.model.DSDataProviderFactory;
import com.jiuqi.bi.dataset.model.DSModelFactory;
import com.jiuqi.bi.dataset.model.DSModelFactoryManager;
import com.jiuqi.bi.parameter.manager.DataSourceFactoryManager;
import com.jiuqi.bi.parameter.manager.DataSourceModelFactory;
import com.jiuqi.bi.publicparam.compatible.NvWaDimDataSourceModelProvider;
import com.jiuqi.bi.publicparam.compatible.NvWaPeriodDataSourceModelProvider;
import com.jiuqi.bi.quickreport.NrWritebackFactory;
import com.jiuqi.bi.quickreport.writeback.WritebackFactory;
import com.jiuqi.bi.quickreport.writeback.WritebackFactoryManager;
import com.jiuqi.nvwa.bap.parameter.extend.compatible.INvwaDataSourceModelProvider;
import com.jiuqi.nvwa.bap.parameter.extend.compatible.NvWaDataSourceModelFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import java.util.List;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrBiAdaptRegister
implements ModuleInitiator {
    @Autowired
    private List<DSDataProviderFactory> DSDataProviderFactorys;
    @Autowired
    private List<DSModelFactory> DSModelFactorys;
    @Autowired
    private List<AbstractParameterDataSourceFactory> dataSourceFactorys;
    @Autowired
    private NrWritebackFactory nrWritebackFactory;

    public void init(ServletContext context) throws Exception {
        for (DSModelFactory dSModelFactory : this.DSModelFactorys) {
            DSModelFactoryManager.getInstance().registerFactory(dSModelFactory);
        }
        for (DSDataProviderFactory dSDataProviderFactory : this.DSDataProviderFactorys) {
            DSModelFactoryManager.getInstance().registerDataProviderFactory(dSDataProviderFactory);
        }
        for (AbstractParameterDataSourceFactory abstractParameterDataSourceFactory : this.dataSourceFactorys) {
            ParameterDataSourceManager.getInstance().registerDataSourceFactory(abstractParameterDataSourceFactory);
        }
        WritebackFactoryManager.registerFactory((String)this.nrWritebackFactory.getType(), (WritebackFactory)this.nrWritebackFactory);
        NvWaDimDataSourceModelProvider dimDataProvider = new NvWaDimDataSourceModelProvider();
        DataSourceFactoryManager.registDataSourceModelFactory((String)"com.jiuqi.publicparam.datasource.dimension", (DataSourceModelFactory)new NvWaDataSourceModelFactory((INvwaDataSourceModelProvider)dimDataProvider));
        NvWaPeriodDataSourceModelProvider nvWaPeriodDataSourceModelProvider = new NvWaPeriodDataSourceModelProvider();
        DataSourceFactoryManager.registDataSourceModelFactory((String)"com.jiuqi.publicparam.datasource.date", (DataSourceModelFactory)new NvWaDataSourceModelFactory((INvwaDataSourceModelProvider)nvWaPeriodDataSourceModelProvider));
    }

    public void initWhenStarted(ServletContext context) throws Exception {
    }
}

