/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.bi.transfer.engine.TransferEngine
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.ITransferContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.transfer.TransferContext
 */
package com.jiuqi.nr.nrdx.param.task.service;

import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.bi.transfer.engine.TransferEngine;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.ITransferContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.nrdx.param.task.service.INvwaService;
import com.jiuqi.nvwa.transfer.TransferContext;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class NvwaServiceImpl
implements INvwaService {
    private static Logger logger = LoggerFactory.getLogger(NvwaServiceImpl.class);

    @Override
    public List<ResItem> getParamRelatedBusinesses(List<ResItem> items) {
        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }
        String userId = NpContextHolder.getContext().getUserId();
        TransferContext context = new TransferContext(userId);
        TransferEngine engine = new TransferEngine();
        List result = null;
        try {
            result = engine.getRelatedBusinesses((ITransferContext)context, items);
        }
        catch (TransferException e) {
            logger.error("\u6dfb\u52a0\u5173\u8054\u8d44\u6e90\u9519\u8bef\uff01", e);
        }
        return result;
    }
}

