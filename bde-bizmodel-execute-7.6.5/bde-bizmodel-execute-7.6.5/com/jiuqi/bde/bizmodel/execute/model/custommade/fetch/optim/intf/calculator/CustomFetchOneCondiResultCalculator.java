/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.vo.CustomCondition
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.calculator;

import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchCalcResult;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchCalcStepResult;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchExecuteSetting;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.ResultColumnType;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.calculator.AbstractCustomFetchResultCalculator;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.AbstractCustomFetchResult;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.CustomFetchOneCondiResult;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomFetchOneCondiResultCalculator
extends AbstractCustomFetchResultCalculator {
    public CustomFetchOneCondiResultCalculator(CustomBizModelDTO bizModel, AbstractCustomFetchResult fetchResult) {
        super(bizModel, fetchResult);
    }

    @Override
    public CustomFetchCalcResult doCalc(CustomFetchExecuteSetting fetchSetting) {
        CustomFetchOneCondiResult fetchResult = (CustomFetchOneCondiResult)this.getFetchResult();
        if (fetchResult.getDataMap() == null || fetchResult.getDataMap().isEmpty()) {
            return new CustomFetchCalcResult(this.getBizModel(), fetchSetting, ResultColumnType.STRING);
        }
        ResultColumnType colType = this.getColType(fetchResult, fetchSetting.getFetchType());
        CustomFetchCalcResult result = new CustomFetchCalcResult(this.getBizModel(), fetchSetting, colType);
        HashMap<String, CustomCondition> directFilterMap = new HashMap<String, CustomCondition>();
        for (Map.Entry<String, CustomCondition> custCondiEntry : fetchSetting.getCustomConditionMap().entrySet()) {
            if (fetchResult.getCondiKey().equals(custCondiEntry.getKey())) continue;
            directFilterMap.put(custCondiEntry.getKey(), custCondiEntry.getValue());
        }
        CustomFetchCalcStepResult setpResult = null;
        for (Map.Entry<String, List<Object[]>> condiKeyEntry1 : fetchResult.getDataMap().entrySet()) {
            if (!this.matchRule(fetchSetting.getCustomConditionMap().get(fetchResult.getCondiKey()), condiKeyEntry1.getKey())) continue;
            block2: for (Object[] row : condiKeyEntry1.getValue()) {
                if (!directFilterMap.isEmpty()) {
                    for (Map.Entry custCondiEntry : directFilterMap.entrySet()) {
                        if (this.matchRule((CustomCondition)custCondiEntry.getValue(), fetchResult.getData(row, (String)custCondiEntry.getKey()))) continue;
                        continue block2;
                    }
                }
                if (result.isCtCol()) {
                    setpResult = new CustomFetchCalcStepResult().setVal(colType, BigDecimal.ZERO);
                    result.addStepVal(setpResult);
                } else {
                    Object data = fetchResult.getData(row, result.getFetchType());
                    setpResult = new CustomFetchCalcStepResult().setVal(colType, data);
                    result.addStepVal(setpResult);
                }
                if (!result.includeCt()) continue;
                setpResult.setStepCt(new BigDecimal(fetchResult.getData(row, "FETCH_EXE_CT_FIELD").toString()));
            }
        }
        result.calcVal();
        return result;
    }
}

