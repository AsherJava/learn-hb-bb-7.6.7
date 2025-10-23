/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.TransferFactoryManager
 *  com.jiuqi.bi.transfer.engine.TransferModule
 *  com.jiuqi.bi.transfer.engine.listener.TransferListenerManager
 *  com.jiuqi.bi.transfer.engine.listener.packet.IPacketImportListener
 *  com.jiuqi.np.core.application.ApplicationInitialization
 */
package com.jiuqi.nr.nrdx.adapter;

import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.TransferFactoryManager;
import com.jiuqi.bi.transfer.engine.TransferModule;
import com.jiuqi.bi.transfer.engine.listener.TransferListenerManager;
import com.jiuqi.bi.transfer.engine.listener.packet.IPacketImportListener;
import com.jiuqi.np.core.application.ApplicationInitialization;
import com.jiuqi.nr.nrdx.adapter.TaskParamDataFactory;
import com.jiuqi.nr.nrdx.adapter.listener.NrdxImportListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskParamDataInit
implements ApplicationInitialization {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskParamDataInit.class);
    @Autowired
    private TaskParamDataFactory taskParamDataFactory;
    @Autowired
    private NrdxImportListener nrdxImportListener;

    public void init(boolean isSysTenant) {
        try {
            LOGGER.info("\u5f00\u59cb\u5411\u5973\u5a32\u5bfc\u5165\u5bfc\u51fa\u6ce8\u518c\u62a5\u8868\u6a21\u5757");
            TransferModule module = TransferFactoryManager.getInstance().getModule("NR");
            if (module != null) {
                LOGGER.info("\u627e\u5230\u5230\u6a21\u5757- {} \u540d\u79f0\u4e3a {}", (Object)module.getId(), (Object)module.getTitle());
            } else {
                module = new TransferModule("NR", "\u62a5\u8868", 128);
                TransferFactoryManager.getInstance().register(module);
                LOGGER.info("\u6ce8\u518c\u6a21\u5757- {} \u540d\u79f0\u4e3a {}", (Object)module.getId(), (Object)module.getTitle());
            }
            TransferFactoryManager.getInstance().register((TransferFactory)this.taskParamDataFactory);
            LOGGER.info("\u6ce8\u518c\u5de5\u5382- NRDX \u5de5\u5382\u6ce8\u518c\u5b8c\u6210");
        }
        catch (Exception e) {
            LOGGER.error("\u6ce8\u518c\u5de5\u5382- NRDX \u5de5\u5382\u6ce8\u518c\u5931\u8d25", e);
        }
        try {
            TransferListenerManager.registerPacketListener((IPacketImportListener)this.nrdxImportListener);
            LOGGER.info("NRDX\u5bfc\u5165\u76d1\u542c\u6ce8\u518c\u5b8c\u6210\uff01");
        }
        catch (Exception e) {
            LOGGER.error("NRDX\u5bfc\u5165\u76d1\u542c\u6ce8\u518c\u5931\u8d25\uff01{}", (Object)e.getMessage(), (Object)e);
        }
    }
}

