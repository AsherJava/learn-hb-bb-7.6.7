/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.TransferFactoryManager
 *  com.jiuqi.bi.transfer.engine.TransferModule
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 */
package com.jiuqi.gcreport.consolidatedsystem.transfermodule;

import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.TransferFactoryManager;
import com.jiuqi.bi.transfer.engine.TransferModule;
import com.jiuqi.gcreport.consolidatedsystem.transfermodule.ConsolidataSystemTransferFactory;
import com.jiuqi.gcreport.consolidatedsystem.transfermodule.ConsolidataSystemTransferModule;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsolidataSystemTransferFactoryRegister
implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(ConsolidataSystemTransferFactoryRegister.class);
    @Autowired
    private ConsolidataSystemTransferModule module;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            TransferModule transferModule = new TransferModule(this.module.getModuleId(), this.module.getTitle(), 0);
            TransferFactoryManager.getInstance().register(transferModule);
        }
        catch (Exception e) {
            logger.error("register transfer module error", e);
            return;
        }
        List<VaParamTransferCategory> categories = this.module.getCategorys();
        if (categories == null || categories.isEmpty()) {
            return;
        }
        for (VaParamTransferCategory category : categories) {
            ConsolidataSystemTransferFactory factory = new ConsolidataSystemTransferFactory();
            factory.setModule(this.module);
            factory.setId(category.getName());
            factory.setTitle(category.getTitle());
            factory.setSupportExport(category.isSupportExport());
            factory.setSupportExportData(category.isSupportExportData());
            TransferFactoryManager.getInstance().register((TransferFactory)factory);
            logger.info("register transfer factory: id={}, title={}", (Object)factory.getId(), (Object)factory.getTitle());
        }
    }
}

