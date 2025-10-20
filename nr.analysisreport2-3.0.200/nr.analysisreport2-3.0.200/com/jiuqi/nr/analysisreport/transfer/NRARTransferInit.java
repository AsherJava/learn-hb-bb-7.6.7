/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.TransferFactoryManager
 *  com.jiuqi.bi.transfer.engine.TransferModule
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.np.core.application.ApplicationInitialization
 */
package com.jiuqi.nr.analysisreport.transfer;

import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.TransferFactoryManager;
import com.jiuqi.bi.transfer.engine.TransferModule;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.np.core.application.ApplicationInitialization;
import com.jiuqi.nr.analysisreport.transfer.NRARTransferFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NRARTransferInit
implements ApplicationInitialization {
    private static final Logger LOGGER = LoggerFactory.getLogger(NRARTransferInit.class);
    @Autowired
    private NRARTransferFactory NRARTransferFactory;

    public void init(boolean isSysTenant) {
        try {
            LOGGER.info("\u5f00\u59cb\u5411\u5973\u5a32\u5bfc\u5165\u5bfc\u51fa\u6ce8\u518c\u5206\u6790\u62a5\u544a\u6a21\u5757");
            TransferModule module = TransferFactoryManager.getInstance().getModule("nr_analysisreport_module");
            if (module != null) {
                LOGGER.info("\u627e\u5230\u5230\u6a21\u5757- {} \u540d\u79f0\u4e3a {}", (Object)module.getId(), (Object)module.getTitle());
            } else {
                module = new TransferModule("nr_analysisreport_module", "\u5206\u6790\u62a5\u544a", 128);
                TransferFactoryManager.getInstance().register(module);
                LOGGER.info("\u6ce8\u518c\u6a21\u5757- {} \u540d\u79f0\u4e3a {}", (Object)module.getId(), (Object)module.getTitle());
            }
            TransferFactoryManager.getInstance().register((TransferFactory)this.NRARTransferFactory);
            LOGGER.info("\u6ce8\u518c\u5de5\u5382- \u5206\u6790\u62a5\u544a\u5b8c\u6210");
        }
        catch (TransferException e) {
            LOGGER.error("\u5206\u6790\u62a5\u544a\u5bfc\u5165\u5bfc\u51fa\u6a21\u5757\u6ce8\u518c\u5931\u8d25", e);
        }
    }
}

