/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 */
package com.jiuqi.gcreport.invest.formula.function.rule.invest.compreequityratio;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.gcreport.invest.formula.function.rule.invest.compreequityratio.InvestCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class InvestCacheCalculator {
    private Logger logger = LoggerFactory.getLogger(InvestCacheCalculator.class);
    private InvestCalculator investCalculator;
    private String baseUnitCode;
    private boolean doCalcFail;

    public InvestCalculator getInvestCalculator() {
        return this.investCalculator;
    }

    public void setInvestCalculator(InvestCalculator investCalculator) {
        Assert.isNotEmpty((String)this.baseUnitCode, (String)"\u8bf7\u5148\u8bbe\u7f6e\u672c\u90e8\u5355\u4f4d", (Object[])new Object[0]);
        investCalculator.setBaseUnitId(this.baseUnitCode);
        this.investCalculator = investCalculator;
    }

    public void setBaseUnitCode(String baseUnitCode) {
        this.baseUnitCode = baseUnitCode;
    }

    public String getBaseUnitCode() {
        return this.baseUnitCode;
    }

    public boolean isDoCalcFail() {
        return this.doCalcFail;
    }
}

