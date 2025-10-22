/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.component.currency;

import com.jiuqi.nr.entity.component.currency.dto.CurrencyCheckDTO;

public interface OrgCurrencyService {
    public CurrencyCheckDTO existCurrencyAttribute(String var1);

    public void generatorCurrency(String var1);
}

