/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 */
package com.jiuqi.nr.data.excel.service.internal;

import com.jiuqi.nr.data.excel.obj.RegionValidateResult;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import java.util.List;

public interface IRegionDataValidator {
    public RegionValidateResult validate(IRegionDataSet var1);

    public boolean validateForm(List<RegionValidateResult> var1);
}

