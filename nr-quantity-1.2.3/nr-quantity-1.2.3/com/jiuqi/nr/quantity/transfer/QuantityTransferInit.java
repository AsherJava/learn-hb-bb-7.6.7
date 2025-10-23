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
package com.jiuqi.nr.quantity.transfer;

import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.TransferFactoryManager;
import com.jiuqi.bi.transfer.engine.TransferModule;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.np.core.application.ApplicationInitialization;
import com.jiuqi.nr.quantity.transfer.QuantityTransferFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuantityTransferInit
implements ApplicationInitialization {
    private static final Logger logger = LoggerFactory.getLogger(QuantityTransferInit.class);
    @Autowired
    private QuantityTransferFactory quantityTransferFactory;

    public void init(boolean isSysTenant) {
        try {
            logger.info("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u2014\u2014\u5f00\u59cb\u5728\u62a5\u8868\u5206\u7ec4\u4e0b\u6ce8\u518c\u91cf\u7eb2");
            TransferModule module = TransferFactoryManager.getInstance().getModule("com.jiuqi.nr");
            if (module != null) {
                logger.info("\u627e\u5230\u5230\u6a21\u5757- {} \u540d\u79f0\u4e3a {}", (Object)module.getId(), (Object)module.getTitle());
            } else {
                module = new TransferModule("com.jiuqi.nr", "\u62a5\u8868", 128);
                TransferFactoryManager.getInstance().register((TransferFactory)this.quantityTransferFactory);
                logger.info("\u6ce8\u518c\u6a21\u5757- {} \u540d\u79f0\u4e3a {}", (Object)module.getId(), (Object)module.getTitle());
            }
            logger.info("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u2014\u2014\u91cf\u7eb2\u6ce8\u518c\u5b8c\u6210");
        }
        catch (TransferException e) {
            logger.error("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u2014\u2014\u91cf\u7eb2\u6ce8\u518c\u5931\u8d25", e);
        }
    }
}

