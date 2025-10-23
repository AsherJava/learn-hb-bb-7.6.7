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
package com.jiuqi.nr.param.transfer.formtype;

import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.TransferFactoryManager;
import com.jiuqi.bi.transfer.engine.TransferModule;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.np.core.application.ApplicationInitialization;
import com.jiuqi.nr.param.transfer.formtype.FormTypeTransferFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormTypeTransferInit
implements ApplicationInitialization {
    private static final Logger logger = LoggerFactory.getLogger(FormTypeTransferInit.class);
    @Autowired
    private FormTypeTransferFactory formTypeTransferFactory;

    public void init(boolean isSysTenant) {
        try {
            logger.info("\u5f00\u59cb\u5411\u5973\u5a32\u6ce8\u518c\u62a5\u8868\u7c7b\u578b\u5bfc\u5165\u5bfc\u51fa\u6a21\u5757");
            TransferModule module = TransferFactoryManager.getInstance().getModule("com.jiuqi.nr");
            if (module != null) {
                logger.info("\u627e\u5230\u5230\u6a21\u5757- {} \u540d\u79f0\u4e3a {}", (Object)module.getId(), (Object)module.getTitle());
            } else {
                module = new TransferModule("com.jiuqi.nr", "\u62a5\u8868\u7c7b\u578b", 129);
                TransferFactoryManager.getInstance().register(module);
                logger.info("\u6ce8\u518c\u6a21\u5757- {} \u540d\u79f0\u4e3a {}", (Object)module.getId(), (Object)module.getTitle());
            }
            TransferFactoryManager.getInstance().register((TransferFactory)this.formTypeTransferFactory);
            logger.info("\u6ce8\u518c\u5de5\u5382- \u62a5\u8868\u65f6\u671f\u5b8c\u6210");
        }
        catch (TransferException e) {
            logger.error("\u62a5\u8868\u65f6\u671f\u5bfc\u5165\u5bfc\u51fa\u6a21\u5757\u6ce8\u518c\u5931\u8d25", e);
        }
    }
}

