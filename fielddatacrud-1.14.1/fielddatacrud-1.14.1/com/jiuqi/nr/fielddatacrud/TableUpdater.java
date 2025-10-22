/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 *  com.jiuqi.nr.datacrud.spi.TypeParseStrategy
 */
package com.jiuqi.nr.fielddatacrud;

import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.datacrud.spi.TypeParseStrategy;
import com.jiuqi.nr.fielddatacrud.SaveRes;
import java.util.List;

public interface TableUpdater {
    public void installParseStrategy();

    public void setRowByDw(boolean var1);

    public void registerParseStrategy(int var1, TypeParseStrategy var2);

    public ReturnRes addRow(List<Object> var1) throws CrudOperateException;

    public ReturnRes addRow(Object[] var1) throws CrudOperateException;

    public void commit() throws CrudOperateException;

    public SaveRes getSaveRes();
}

