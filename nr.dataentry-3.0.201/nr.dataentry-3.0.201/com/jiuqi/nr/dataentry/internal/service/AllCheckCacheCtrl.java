/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.logic.facade.extend.IAllCheckCacheCtrl
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.data.logic.facade.extend.IAllCheckCacheCtrl;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllCheckCacheCtrl
implements IAllCheckCacheCtrl {
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;

    public boolean cache(int count) {
        int allCheckCount = Integer.parseInt(this.nvwaSystemOptionService.get("nr-audit-group", "ALL_CHECK_COUNT"));
        return count < allCheckCount;
    }
}

