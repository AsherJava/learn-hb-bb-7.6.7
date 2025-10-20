/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.dc.base.common.nvwaTransfer;

import com.jiuqi.dc.base.common.nvwaTransfer.DcTransferModule;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferModuleFactory {
    @Autowired(required=false)
    List<DcTransferModule> dcTransferModules;
    private final ConcurrentHashMap<String, DcTransferModule> transferModuleMap = new ConcurrentHashMap();

    public List<DcTransferModule> getTransferModules() {
        return this.dcTransferModules;
    }

    @PostConstruct
    public void init() {
        if (this.dcTransferModules == null) {
            return;
        }
        this.dcTransferModules.forEach(transferModule -> transferModule.getCategorys().forEach(category -> this.transferModuleMap.put(category.getName(), (DcTransferModule)transferModule)));
    }

    public DcTransferModule getTransferModule(String moduleName) {
        if (this.transferModuleMap.containsKey(moduleName)) {
            return this.transferModuleMap.get(moduleName);
        }
        return null;
    }
}

