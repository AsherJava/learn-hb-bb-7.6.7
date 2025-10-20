/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.common.SumValCalculator
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.bde.plugin.va6.penetrate.balance;

import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.SumValCalculator;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.plugin.va6.penetrate.balance.AbstractVa6BalanceContentProvider;
import com.jiuqi.va.domain.common.PageVO;

public abstract class AbstractVa6CflBalanceContentProvider
extends AbstractVa6BalanceContentProvider {
    protected PageVO<PenetrateBalance> processResult(PenetrateBaseDTO condi, PageVO<PenetrateBalance> queryResult) {
        SumValCalculator sumValCacl = new SumValCalculator(condi, queryResult.getRows());
        return sumValCacl.cacl();
    }
}

