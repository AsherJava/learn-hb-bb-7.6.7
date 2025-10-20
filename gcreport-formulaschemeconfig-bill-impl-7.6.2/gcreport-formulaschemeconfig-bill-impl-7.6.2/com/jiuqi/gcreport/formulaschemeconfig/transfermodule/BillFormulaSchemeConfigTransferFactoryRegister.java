/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.TransferFactoryManager
 *  com.jiuqi.bi.transfer.engine.TransferModule
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 *  com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf
 *  com.jiuqi.va.paramsync.transfer.VaParamTransferFactory
 */
package com.jiuqi.gcreport.formulaschemeconfig.transfermodule;

import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.TransferFactoryManager;
import com.jiuqi.bi.transfer.engine.TransferModule;
import com.jiuqi.gcreport.formulaschemeconfig.transfermodule.BillFormulaSchemeConfigTransferModule;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import com.jiuqi.va.paramsync.transfer.VaParamTransferFactory;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillFormulaSchemeConfigTransferFactoryRegister
implements InitializingBean {
    private static final Logger LOG = LoggerFactory.getLogger(BillFormulaSchemeConfigTransferFactoryRegister.class);
    @Autowired
    private BillFormulaSchemeConfigTransferModule module;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            TransferModule transferModule = new TransferModule(this.module.getModuleId(), this.module.getTitle(), 0);
            TransferFactoryManager.getInstance().register(transferModule);
        }
        catch (Exception e) {
            LOG.info("register transfer module info\uff1a\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u5bfc\u5165\u5bfc\u51fa\u62a5\u8868\u4e0e\u5355\u636e\u5171\u7528\u4e00\u4e2a\u6a21\u5757ID");
        }
        List<VaParamTransferCategory> categories = this.module.getCategorys();
        if (categories == null || categories.isEmpty()) {
            return;
        }
        for (VaParamTransferCategory category : categories) {
            VaParamTransferFactory factory = new VaParamTransferFactory();
            factory.setModule((VaParamTransferModuleIntf)this.module);
            factory.setId(category.getName());
            factory.setTitle(category.getTitle());
            factory.setSupportExport(category.isSupportExport());
            factory.setSupportExportData(category.isSupportExportData());
            TransferFactoryManager.getInstance().register((TransferFactory)factory);
            LOG.info("register transfer factory: id={}, title={}", (Object)factory.getId(), (Object)factory.getTitle());
        }
    }
}

