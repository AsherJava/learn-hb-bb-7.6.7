/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.TransferFactoryManager
 *  com.jiuqi.bi.transfer.engine.TransferModule
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager
 *  com.jiuqi.nvwa.framework.parameter.server.NvwaParameterStorageProvider
 *  com.jiuqi.nvwa.framework.parameter.server.migrate.ParameterMigrater
 *  com.jiuqi.nvwa.framework.parameter.server.transfer.ParameterTransferFactory
 *  com.jiuqi.nvwa.framework.parameter.storage.IParameterStorageProvider
 *  com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageManager
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nvwa.framework.parameter.server.init;

import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.TransferFactoryManager;
import com.jiuqi.bi.transfer.engine.TransferModule;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.server.NvwaParameterStorageProvider;
import com.jiuqi.nvwa.framework.parameter.server.migrate.ParameterMigrater;
import com.jiuqi.nvwa.framework.parameter.server.transfer.ParameterTransferFactory;
import com.jiuqi.nvwa.framework.parameter.storage.IParameterStorageProvider;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageManager;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ParameterServiceInit
implements ModuleInitiator {
    private static final Logger logger = LoggerFactory.getLogger(ParameterServiceInit.class);
    @Autowired
    private List<AbstractParameterDataSourceFactory> datasourceFactories;
    @Autowired
    private ParameterMigrater migrater;

    public void init(ServletContext context) throws Exception {
        ParameterStorageManager.getInstance().registerStorageProvider((IParameterStorageProvider)SpringBeanUtils.getBean(NvwaParameterStorageProvider.class));
        if (this.datasourceFactories != null) {
            this.datasourceFactories.sort(new Comparator<AbstractParameterDataSourceFactory>(){

                @Override
                public int compare(AbstractParameterDataSourceFactory o1, AbstractParameterDataSourceFactory o2) {
                    return o1.getOrder() - o2.getOrder();
                }
            });
            this.datasourceFactories.forEach(factory -> {
                logger.debug("\u6ce8\u518c\u6269\u5c55\u53c2\u6570\u6765\u6e90->" + factory.title());
                ParameterDataSourceManager.getInstance().registerDataSourceFactory(factory);
            });
        }
        TransferFactoryManager.getInstance().register(new TransferModule("com.jiuqi.nvwa.framework.parameter", "\u516c\u5171\u53c2\u6570", 2));
        ApplicationContext appCxt = SpringBeanUtils.getApplicationContext();
        ParameterTransferFactory bean = appCxt.getBean(ParameterTransferFactory.class);
        TransferFactoryManager.getInstance().register((TransferFactory)bean);
        if (this.migrater.isNeedMigrate()) {
            this.migrater.migrateParameterFolder();
            List params = this.migrater.migrateParameter();
            logger.info("\u53c2\u6570\u6a21\u578b\u6570\u636e\u8fc1\u79fb\u5b8c\u6210\uff0c\u5171\u8fc1\u79fb\u53c2\u6570\u6a21\u578b\uff1a" + params.size());
        }
    }

    public void initWhenStarted(ServletContext context) throws Exception {
    }
}

