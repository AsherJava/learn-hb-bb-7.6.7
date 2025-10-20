/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.offset.aging.mq;

import com.jiuqi.gc.financialcubes.offset.aging.service.FinancialCubesOffsetCalcAgingService;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import com.jiuqi.gc.financialcubes.offset.service.AbstractFinancialCubesOffsetCalcHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialCubesOffsetAgingCalcHandler
extends AbstractFinancialCubesOffsetCalcHandler {
    public static final String HANDLER_NAME = "FinancialCubesOffsetAgingCalcHandler";
    @Autowired
    private FinancialCubesOffsetCalcAgingService offsetCalcService;

    @Override
    public String calcFinancialCubes(FinancialCubesOffsetTaskDto offsetTaskDto) {
        return this.offsetCalcService.calcFinancialCubesAging(offsetTaskDto);
    }

    public String getName() {
        return HANDLER_NAME;
    }

    public String getTitle() {
        return "\u591a\u7ef4\u8d26\u9f84\u5e95\u7a3f\u8c03\u6574\u62b5\u9500\u5206\u5f55\u8ba1\u7b97\u4efb\u52a1";
    }
}

