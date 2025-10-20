/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.va.paramsync.transfer.VaParamTransferFactory
 */
package com.jiuqi.dc.base.common.nvwaTransfer;

import com.jiuqi.dc.base.common.nvwaTransfer.DcTransferModule;
import com.jiuqi.dc.base.common.nvwaTransfer.TransferModuleFactory;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.va.paramsync.transfer.VaParamTransferFactory;
import java.util.List;

public class DcTransferModuleFactory
extends VaParamTransferFactory {
    public List<String> getDependenceFactoryIds() {
        String categoryName = this.getId();
        DcTransferModule transferModule = ((TransferModuleFactory)SpringBeanUtils.getBean(TransferModuleFactory.class)).getTransferModule(categoryName);
        return transferModule.getDependenceFactoryIds(categoryName);
    }
}

