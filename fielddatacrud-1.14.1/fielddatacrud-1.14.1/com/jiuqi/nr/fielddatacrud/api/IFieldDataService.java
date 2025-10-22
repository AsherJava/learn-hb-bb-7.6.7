/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 */
package com.jiuqi.nr.fielddatacrud.api;

import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.fielddatacrud.FieldSaveInfo;
import com.jiuqi.nr.fielddatacrud.IFieldQueryInfo;
import com.jiuqi.nr.fielddatacrud.TableUpdater;
import com.jiuqi.nr.fielddatacrud.spi.IDataReader;

public interface IFieldDataService {
    public void queryTableData(IFieldQueryInfo var1, IDataReader var2) throws CrudException;

    public TableUpdater getTableUpdater(FieldSaveInfo var1);
}

