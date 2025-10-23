/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.events.helper;

import com.jiuqi.nr.workflow2.events.enumeration.CurrencyType;
import java.util.List;

public class CurrencyFilterCondition {
    private CurrencyType currencyType;
    private List<String> customCurrency;
    private boolean mdCurrencyReferEntity;

    public CurrencyType getCurrencyType() {
        return this.currencyType;
    }

    public List<String> getCustomCurrency() {
        return this.customCurrency;
    }

    public boolean isMdCurrencyReferEntity() {
        return this.mdCurrencyReferEntity;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public void setCustomCurrency(List<String> customCurrency) {
        this.customCurrency = customCurrency;
    }

    public void setMdCurrencyReferEntity(boolean mdCurrencyReferEntity) {
        this.mdCurrencyReferEntity = mdCurrencyReferEntity;
    }
}

