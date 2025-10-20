/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.BizDataModelEnum
 */
package com.jiuqi.bde.bizmodel.execute.datamodel.aging;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.datamodel.AbstractFinBizDataModelLoader;
import com.jiuqi.bde.bizmodel.execute.model.aging.AgingBalanceCondition;
import com.jiuqi.bde.common.constant.BizDataModelEnum;

public abstract class AbstractAgingDataLoader
extends AbstractFinBizDataModelLoader<AgingBalanceCondition, FetchData> {
    public String getBizDataModelCode() {
        return BizDataModelEnum.AGINGMODEL.name();
    }
}

