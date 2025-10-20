/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.TransferFactoryManager
 *  com.jiuqi.bi.transfer.engine.TransferModule
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent
 *  com.jiuqi.va.paramsync.config.VaParamSyncConfig
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 *  com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf
 */
package com.jiuqi.va.paramsync.transfer;

import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.TransferFactoryManager;
import com.jiuqi.bi.transfer.engine.TransferModule;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent;
import com.jiuqi.va.paramsync.config.VaParamSyncConfig;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import com.jiuqi.va.paramsync.transfer.VaParamTransferFactory;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class VaParamTransferInit
implements ApplicationListener<StorageSyncFinishedEvent> {
    private static Logger logger = LoggerFactory.getLogger(VaParamTransferInit.class);

    @Override
    public void onApplicationEvent(StorageSyncFinishedEvent event) {
        try {
            if (EnvConfig.getRedisEnable()) {
                EnvConfig.sendRedisMsg((String)VaParamSyncConfig.getParamTransferCollectorMsg(), (String)"1");
            }
        }
        catch (Exception e) {
            logger.error("\u53c2\u6570\u540c\u6b65\u53d1\u9001\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u6a21\u5757\u6536\u96c6\u6d88\u606f\u5f02\u5e38\uff1a", e);
        }
    }

    public synchronized void registModules(List<VaParamTransferModuleIntf> modules) {
        if (modules == null || modules.isEmpty()) {
            return;
        }
        for (VaParamTransferModuleIntf module : modules) {
            List categorys;
            try {
                if (!StringUtils.hasText(module.getModuleId())) {
                    TransferModule transModule = TransferFactoryManager.getInstance().getModule(module.getName());
                    if (transModule != null) continue;
                    transModule = new TransferModule(module.getName(), module.getTitle(), 0);
                    TransferFactoryManager.getInstance().register(transModule);
                }
            }
            catch (Throwable e) {
                logger.error("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u6a21\u5757\uff1a{}\u6ce8\u518c\u5931\u8d25", (Object)module.getName(), (Object)e);
            }
            if ((categorys = module.getCategorys()) == null || categorys.isEmpty()) continue;
            for (VaParamTransferCategory category : categorys) {
                try {
                    String moduleName = StringUtils.hasText(module.getModuleId()) ? module.getModuleId() : module.getName();
                    List list = TransferFactoryManager.getInstance().getModuleFactorys(moduleName);
                    boolean flag = true;
                    if (list != null && !list.isEmpty()) {
                        for (Object factory : list) {
                            TransferFactory temp = (TransferFactory)factory;
                            if (!Objects.nonNull(temp) || !temp.getId().equals(category.getName())) continue;
                            flag = false;
                            break;
                        }
                    }
                    if (!flag) continue;
                    VaParamTransferFactory factory = new VaParamTransferFactory();
                    factory.setModule(module);
                    factory.setId(category.getName());
                    factory.setTitle(category.getTitle());
                    factory.setSupportExport(category.isSupportExport());
                    factory.setSupportExportData(category.isSupportExportData());
                    TransferFactoryManager.getInstance().register((TransferFactory)factory);
                }
                catch (Throwable e) {
                    logger.error("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u6a21\u5757\u5de5\u5382\uff1a{}\uff0c\u6ce8\u518c\u5931\u8d25", (Object)category.getName(), (Object)e);
                }
            }
        }
    }
}

