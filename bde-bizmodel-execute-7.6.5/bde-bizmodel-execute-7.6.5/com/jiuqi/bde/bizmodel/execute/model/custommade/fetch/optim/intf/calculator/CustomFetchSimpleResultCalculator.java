/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.calculator;

import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchCalcResult;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchCalcStepResult;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchExecuteSetting;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.ResultColumnType;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.calculator.AbstractCustomFetchResultCalculator;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.AbstractCustomFetchResult;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.CustomFetchSimpleResult;
import com.jiuqi.common.base.util.CollectionUtils;
import java.math.BigDecimal;

public class CustomFetchSimpleResultCalculator
extends AbstractCustomFetchResultCalculator {
    public CustomFetchSimpleResultCalculator(CustomBizModelDTO bizModel, AbstractCustomFetchResult fetchResult) {
        super(bizModel, fetchResult);
    }

    @Override
    public CustomFetchCalcResult doCalc(CustomFetchExecuteSetting fetchSetting) {
        CustomFetchSimpleResult fetchResult = (CustomFetchSimpleResult)this.getFetchResult();
        if (CollectionUtils.isEmpty(fetchResult.getRowDatas())) {
            return new CustomFetchCalcResult(this.getBizModel(), fetchSetting, ResultColumnType.STRING);
        }
        ResultColumnType colType = this.getColType(fetchResult, fetchSetting.getFetchType());
        CustomFetchCalcResult result = new CustomFetchCalcResult(this.getBizModel(), fetchSetting, colType);
        CustomFetchCalcStepResult setpResult = null;
        for (Object[] row : fetchResult.getRowDatas()) {
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
        result.calcVal();
        return result;
    }
}

