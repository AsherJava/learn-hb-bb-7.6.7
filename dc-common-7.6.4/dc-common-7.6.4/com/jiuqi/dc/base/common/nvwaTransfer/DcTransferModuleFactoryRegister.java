/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ITransferFactory
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.TransferFactoryManager
 *  com.jiuqi.bi.transfer.engine.TransferModule
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.np.core.application.ApplicationInitialization
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 */
package com.jiuqi.dc.base.common.nvwaTransfer;

import com.jiuqi.bi.transfer.engine.ITransferFactory;
import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.TransferFactoryManager;
import com.jiuqi.bi.transfer.engine.TransferModule;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.dc.base.common.nvwaTransfer.DcTransferModuleFactory;
import com.jiuqi.dc.base.common.nvwaTransfer.DcTransferModuleIntf;
import com.jiuqi.np.core.application.ApplicationInitialization;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DcTransferModuleFactoryRegister
implements ApplicationInitialization {
    @Autowired
    private DcTransferModuleIntf module;
    Logger logger = LoggerFactory.getLogger(DcTransferModuleFactoryRegister.class);

    public void init(boolean isSysTenant) {
        this.register();
    }

    public void register() {
        TransferModule hasRegisterModule = TransferFactoryManager.getInstance().getModule(this.module.getModuleId());
        try {
            if (hasRegisterModule == null) {
                hasRegisterModule = new TransferModule(this.module.getModuleId(), this.module.getTitle(), -998);
                TransferFactoryManager.getInstance().register(hasRegisterModule);
            }
        }
        catch (Exception e) {
            this.logger.error("{}\u6a21\u5757\u5bfc\u5165\u5bfc\u51fa\u6ce8\u518c\u5931\u8d25,\u5931\u8d25\u539f\u56e0\uff1a{}", (Object)this.module.getModuleId(), (Object)e.getMessage());
            return;
        }
        List factoryIds = TransferFactoryManager.getInstance().getModuleFactorys(hasRegisterModule.getId()).stream().map(ITransferFactory::getId).collect(Collectors.toList());
        List<VaParamTransferCategory> categories = this.module.getCategorys();
        if (categories == null || categories.isEmpty()) {
            return;
        }
        for (VaParamTransferCategory category : categories) {
            DcTransferModuleFactory factory = new DcTransferModuleFactory();
            factory.setModule(this.module);
            factory.setId(category.getName());
            factory.setTitle(category.getTitle());
            factory.setSupportExport(category.isSupportExport());
            factory.setSupportExportData(category.isSupportExportData());
            try {
                if (factoryIds.contains(category.getName())) {
                    TransferFactoryManager.getInstance().unregisterFactory(category.getName());
                }
                TransferFactoryManager.getInstance().register((TransferFactory)factory);
            }
            catch (TransferException e) {
                this.logger.error("{}\u5bfc\u5165\u5bfc\u51fa\u5de5\u5382\u6ce8\u518c\u5931\u8d25,\u5931\u8d25\u539f\u56e0\uff1a{}", (Object)category.getName(), (Object)e.getMessage());
                return;
            }
        }
    }
}

