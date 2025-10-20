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
package com.jiuqi.gcreport.bde.fetchsetting.impl.transfermodule;

import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.TransferFactoryManager;
import com.jiuqi.bi.transfer.engine.TransferModule;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.gcreport.bde.fetchsetting.impl.transfermodule.FetchSettingTransferFactory;
import com.jiuqi.gcreport.bde.fetchsetting.impl.transfermodule.FetchSettingTransferModule;
import com.jiuqi.np.core.application.ApplicationInitialization;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FetchSettingTransferFactoryRegister
implements ApplicationInitialization {
    private static final Logger LOG = LoggerFactory.getLogger(FetchSettingTransferFactoryRegister.class);
    @Autowired
    private FetchSettingTransferModule module;

    public void register() {
        LOG.info("\u5f00\u59cb\u5411\u5973\u5a32\u5bfc\u5165\u5bfc\u51fa\u6ce8\u518c\u300cBDE\u53d6\u6570\u8bbe\u7f6e\u300d\u6a21\u5757");
        try {
            TransferModule transferModule = new TransferModule(this.module.getModuleId(), this.module.getTitle(), -998);
            TransferFactoryManager.getInstance().register(transferModule);
        }
        catch (Exception e) {
            LOG.error("\u6ce8\u518c\u300cBDE\u53d6\u6570\u8bbe\u7f6e\u300d\u6a21\u5757\u5931\u8d25\uff1a", e);
            return;
        }
        List<VaParamTransferCategory> categories = this.module.getCategorys();
        if (categories == null || categories.isEmpty()) {
            return;
        }
        for (VaParamTransferCategory category : categories) {
            FetchSettingTransferFactory factory = new FetchSettingTransferFactory();
            factory.setModule(this.module);
            factory.setId(category.getName());
            factory.setTitle(category.getTitle());
            factory.setSupportExport(category.isSupportExport());
            factory.setSupportExportData(category.isSupportExportData());
            try {
                TransferFactoryManager.getInstance().register((TransferFactory)factory);
            }
            catch (TransferException e) {
                LOG.error("\u6ce8\u518c\u300cBDE\u53d6\u6570\u8bbe\u7f6e\u300d\u6a21\u5757\u5de5\u5382\u5931\u8d25\uff1a", e);
            }
            LOG.info("\u6ce8\u518c\u300cBDE\u53d6\u6570\u8bbe\u7f6e\u300d\u6a21\u5757\u5de5\u5382\u6210\u529f: id={}, title={}", (Object)factory.getId(), (Object)factory.getTitle());
        }
    }

    public void init(boolean isSysTenant) {
        this.register();
    }
}

