/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSDataProviderFactory
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.IDataSetProvider
 */
package com.jiuqi.bi.dataset.calibersum;

import com.jiuqi.bi.dataset.calibersum.CaliberSumDSProvider;
import com.jiuqi.bi.dataset.calibersum.CaliberSumQueryExecutor;
import com.jiuqi.bi.dataset.calibersum.model.CaliberSumDSModel;
import com.jiuqi.bi.dataset.model.DSDataProviderFactory;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.IDataSetProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CaliberSumDSDataProviderFactory
extends DSDataProviderFactory {
    @Autowired
    private CaliberSumQueryExecutor caliberSumExecutor;

    public IDataSetProvider createDataSetProvider(DSModel dsModel) {
        return new CaliberSumDSProvider(this.caliberSumExecutor, (CaliberSumDSModel)dsModel);
    }

    public String getType() {
        return "CaliberSumDataSet";
    }
}

