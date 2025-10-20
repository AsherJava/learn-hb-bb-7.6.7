/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.TransferFactoryManager
 *  com.jiuqi.bi.transfer.engine.TransferModule
 */
package com.jiuqi.budget.transfer;

import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.TransferFactoryManager;
import com.jiuqi.bi.transfer.engine.TransferModule;
import com.jiuqi.budget.components.ProductNameUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RegisterTransferModule
implements ApplicationRunner {
    @Autowired
    private List<TransferFactory> factoryList;
    private static final Logger logger = LoggerFactory.getLogger(RegisterTransferModule.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        TransferFactoryManager.getInstance().register(new TransferModule("com.jiuqi.budget", ProductNameUtil.getProductName(), 0));
        for (TransferFactory bean : this.factoryList) {
            String moduleId = bean.getModuleId();
            if (!"com.jiuqi.budget".equals(moduleId)) continue;
            TransferFactoryManager.getInstance().register(bean);
            logger.info("{}\u7cfb\u7edf\u6ce8\u518c\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u5de5\u5382[{}]\u6210\u529f", (Object)ProductNameUtil.getProductName(), (Object)bean.getTitle());
        }
    }
}

