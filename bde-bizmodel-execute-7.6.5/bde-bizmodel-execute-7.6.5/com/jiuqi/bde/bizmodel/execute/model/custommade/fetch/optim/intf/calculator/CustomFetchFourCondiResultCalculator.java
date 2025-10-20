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
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.CustomFetchFourCondiResult;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomFetchFourCondiResultCalculator
extends AbstractCustomFetchResultCalculator {
    public CustomFetchFourCondiResultCalculator(CustomBizModelDTO bizModel, AbstractCustomFetchResult fetchResult) {
        super(bizModel, fetchResult);
    }

    @Override
    public CustomFetchCalcResult doCalc(CustomFetchExecuteSetting fetchSetting) {
        CustomFetchFourCondiResult fetchResult = (CustomFetchFourCondiResult)this.getFetchResult();
        if (fetchResult.getDataMap() == null || fetchResult.getDataMap().isEmpty()) {
            return new CustomFetchCalcResult(this.getBizModel(), fetchSetting, ResultColumnType.STRING);
        }
        ResultColumnType colType = this.getColType(fetchResult, fetchSetting.getFetchType());
        CustomFetchCalcResult result = new CustomFetchCalcResult(this.getBizModel(), fetchSetting, colType);
        HashMap<String, CustomCondition> directFilterMap = new HashMap<String, CustomCondition>();
        for (Map.Entry<String, CustomCondition> custCondiEntry : fetchSetting.getCustomConditionMap().entrySet()) {
            if (fetchResult.getCondiKey1().equals(custCondiEntry.getKey()) || fetchResult.getCondiKey2().equals(custCondiEntry.getKey()) || fetchResult.getCondiKey3().equals(custCondiEntry.getKey()) || fetchResult.getCondiKey4().equals(custCondiEntry.getKey())) continue;
            directFilterMap.put(custCondiEntry.getKey(), custCondiEntry.getValue());
        }
        CustomFetchCalcStepResult setpResult = null;
        for (Map.Entry<String, Map<String, Map<String, Map<String, List<Object[]>>>>> condiKeyEntry1 : fetchResult.getDataMap().entrySet()) {
            if (!this.matchRule(fetchSetting.getCustomConditionMap().get(fetchResult.getCondiKey1()), condiKeyEntry1.getKey())) continue;
            for (Map.Entry<String, Map<String, Map<String, List<Object[]>>>> condiKeyEntry2 : condiKeyEntry1.getValue().entrySet()) {
                if (!this.matchRule(fetchSetting.getCustomConditionMap().get(fetchResult.getCondiKey2()), condiKeyEntry2.getKey())) continue;
                for (Map.Entry<String, Map<String, List<Object[]>>> condiKeyEntry3 : condiKeyEntry2.getValue().entrySet()) {
                    if (!this.matchRule(fetchSetting.getCustomConditionMap().get(fetchResult.getCondiKey3()), condiKeyEntry3.getKey())) continue;
                    for (Map.Entry<String, List<Object[]>> condiKeyEntry4 : condiKeyEntry3.getValue().entrySet()) {
                        if (!this.matchRule(fetchSetting.getCustomConditionMap().get(fetchResult.getCondiKey4()), condiKeyEntry4.getKey())) continue;
                        block5: for (Object[] row : condiKeyEntry4.getValue()) {
                            if (!directFilterMap.isEmpty()) {
                                for (Map.Entry custCondiEntry : directFilterMap.entrySet()) {
                                    if (this.matchRule((CustomCondition)custCondiEntry.getValue(), fetchResult.getData(row, (String)custCondiEntry.getKey()))) continue;
                                    continue block5;
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
                }
            }
        }
        result.calcVal();
        return result;
    }
}

