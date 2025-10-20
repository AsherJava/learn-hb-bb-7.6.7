/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.distributeds.DistributeDimValueFindException
 *  com.jiuqi.bi.distributeds.DistributeDsDimValue
 *  com.jiuqi.bi.distributeds.IDistributeDsValueProvider
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.dataset.sql.DistributeDsValueProviderFactory;
import com.jiuqi.bi.distributeds.DistributeDimValueFindException;
import com.jiuqi.bi.distributeds.DistributeDsDimValue;
import com.jiuqi.bi.distributeds.IDistributeDsValueProvider;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import java.util.ArrayList;
import java.util.List;

public class DefaultDistributeDsValueProviderFactory
extends DistributeDsValueProviderFactory {
    @Override
    public IDistributeDsValueProvider createDistributeDsValueProvider(IParameterEnv env) {
        return new IDistributeDsValueProvider(){

            public List<DistributeDsDimValue> findDimValues(String dimName) throws DistributeDimValueFindException {
                return new ArrayList<DistributeDsDimValue>();
            }
        };
    }
}

