/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.api;

import com.jiuqi.nr.datacrud.IClearInfo;
import com.jiuqi.nr.datacrud.ISaveInfo;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveReturnRes;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;

public interface IDataService {
    public ReturnRes clearRegionData(IClearInfo var1) throws CrudException, CrudOperateException;

    public SaveReturnRes saveRegionData(ISaveInfo var1) throws CrudException, CrudOperateException;
}

