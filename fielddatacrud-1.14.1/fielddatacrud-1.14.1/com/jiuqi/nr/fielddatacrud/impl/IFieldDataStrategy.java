/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 */
package com.jiuqi.nr.fielddatacrud.impl;

import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.IFieldQueryInfo;
import com.jiuqi.nr.fielddatacrud.spi.IDataReader;

public interface IFieldDataStrategy {
    public void queryTableData(IFieldQueryInfo var1, FieldRelation var2, IDataReader var3) throws CrudException;
}

