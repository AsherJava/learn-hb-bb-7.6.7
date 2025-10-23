/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.singlequeryimport.intf.service;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.singlequeryimport.intf.bean.IMultCheckItemBase;
import com.jiuqi.nr.singlequeryimport.intf.bean.SearchModelItem;

public interface IModelQueryCheckService {
    public SearchModelItem hasQueryData(String var1, DimensionValue var2) throws Exception;

    public IMultCheckItemBase getCheckModel(String var1, String var2) throws Exception;
}

