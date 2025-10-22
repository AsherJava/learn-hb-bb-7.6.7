/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl.crud;

import com.jiuqi.nr.datacrud.IClearInfo;
import com.jiuqi.nr.datacrud.ISaveInfo;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveReturnRes;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;

public interface IRegionDataSetUpdatableStrategy {
    public ReturnRes clearData(IClearInfo var1, RegionRelation var2) throws CrudOperateException;

    public SaveReturnRes saveData(ISaveInfo var1, RegionRelation var2) throws CrudOperateException;
}

