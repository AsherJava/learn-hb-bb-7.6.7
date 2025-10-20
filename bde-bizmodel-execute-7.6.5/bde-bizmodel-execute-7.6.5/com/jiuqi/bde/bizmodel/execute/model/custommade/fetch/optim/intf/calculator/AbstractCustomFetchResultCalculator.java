/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum
 *  com.jiuqi.bde.bizmodel.client.vo.CustomCondition
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.calculator;

import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.ResultColumnType;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.calculator.ICustomFetchResultCalculator;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.AbstractCustomFetchResult;

public abstract class AbstractCustomFetchResultCalculator
implements ICustomFetchResultCalculator {
    private CustomBizModelDTO bizModel;
    private AbstractCustomFetchResult fetchResult;

    public AbstractCustomFetchResultCalculator(CustomBizModelDTO bizModel, AbstractCustomFetchResult fetchResult) {
        this.bizModel = bizModel;
        this.fetchResult = fetchResult;
    }

    public CustomBizModelDTO getBizModel() {
        return this.bizModel;
    }

    public AbstractCustomFetchResult getFetchResult() {
        return this.fetchResult;
    }

    protected ResultColumnType getColType(AbstractCustomFetchResult fetchResult, String fetchType) {
        ResultColumnType colType = fetchResult.findColType(fetchType);
        if (colType != null) {
            return colType;
        }
        return ResultColumnType.INT;
    }

    protected boolean matchRule(CustomCondition customCondi, Object condiValueObj) {
        if (customCondi == null) {
            return true;
        }
        if (condiValueObj == null) {
            condiValueObj = "";
        }
        String condiValueStr = "";
        condiValueStr = condiValueObj instanceof String ? (String)condiValueObj : condiValueObj.toString();
        return MatchingRuleEnum.getEnumByCode((String)customCondi.getRuleCode()).match(condiValueStr, customCondi.getValue());
    }
}

