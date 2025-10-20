/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.TransferFactoryManager
 *  com.jiuqi.bi.transfer.engine.TransferModule
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.np.core.application.ApplicationInitialization
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 */
package com.jiuqi.bde.bizmodel.impl.dimension.transfermodule;

import com.jiuqi.bde.bizmodel.impl.dimension.transfermodule.AssistExtendDimTransferFactory;
import com.jiuqi.bde.bizmodel.impl.dimension.transfermodule.AssistExtendDimTransferModule;
import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.TransferFactoryManager;
import com.jiuqi.bi.transfer.engine.TransferModule;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.np.core.application.ApplicationInitialization;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssistExtendDimTransferFactoryRegister
implements ApplicationInitialization {
    private static final Logger LOG = LoggerFactory.getLogger(AssistExtendDimTransferFactoryRegister.class);
    private final AssistExtendDimTransferModule module;

    public AssistExtendDimTransferFactoryRegister(@Autowired AssistExtendDimTransferModule assistExtendDimTransferModule) {
        this.module = assistExtendDimTransferModule;
    }

    public void register() {
        LOG.info("\u5f00\u59cb\u5411\u5973\u5a32\u5bfc\u5165\u5bfc\u51fa\u6ce8\u518c\u300c\u7ef4\u5ea6\u5c5e\u6027\u300d\u6a21\u5757");
        try {
            TransferModule transferModule = new TransferModule(this.module.getModuleId(), this.module.getTitle(), -999);
            TransferFactoryManager.getInstance().register(transferModule);
        }
        catch (Exception e) {
            LOG.error("\u6ce8\u518c\u300c\u7ef4\u5ea6\u5c5e\u6027\u300d\u6a21\u5757\u5931\u8d25\uff1a", e);
            return;
        }
        List<VaParamTransferCategory> categories = this.module.getCategorys();
        if (categories == null || categories.isEmpty()) {
            return;
        }
        for (VaParamTransferCategory category : categories) {
            AssistExtendDimTransferFactory factory = new AssistExtendDimTransferFactory();
            factory.setModule(this.module);
            factory.setId(category.getName());
            factory.setTitle(category.getTitle());
            factory.setSupportExport(category.isSupportExport());
            factory.setSupportExportData(category.isSupportExportData());
            try {
                TransferFactoryManager.getInstance().register((TransferFactory)factory);
            }
            catch (TransferException e) {
                LOG.error("\u6ce8\u518c\u300c\u7ef4\u5ea6\u5c5e\u6027\u300d\u6a21\u5757\u5de5\u5382\u5931\u8d25\uff1a", e);
            }
            LOG.info("\u6ce8\u518c\u300c\u7ef4\u5ea6\u5c5e\u6027\u300d\u6a21\u5757\u5de5\u5382\u6210\u529f: id={}, title={}", (Object)factory.getId(), (Object)factory.getTitle());
        }
    }

    public void init(boolean isSysTenant) {
        this.register();
    }
}

